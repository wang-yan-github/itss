package com.jsdc.itss.controller;

import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.AssetsInventory;
import com.jsdc.itss.model.AssetsInventoryDetails;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.EventSource;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.model.vo.TreeDataVo;
import com.jsdc.itss.service.AssetsInventoryDetailsService;
import com.jsdc.itss.service.AssetsInventoryService;
import com.jsdc.itss.service.EventService;
import com.jsdc.itss.service.EventSourceService;
import com.jsdc.itss.service.*;
import com.jsdc.itss.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 事件管理
 */
@RestController
@RequestMapping("/app/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private AssetsInventoryDetailsService assetsInventoryDetailsService;
    @Autowired
    private AssetsInventoryService assetsInventoryService;
    @Autowired
    private EventSolutionTypeService eventSolutionTypeService;
    @Autowired
    private SchemeClassificationService schemeClassificationService;
    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EventSourceService eventSourceService;
    @Autowired
    private AssetsTypeService assetsTypeService;
    @Autowired
    private AssetsChildrenService assetsChildrenService;
    @Autowired
    private FileManageService fileManageService;
    @Value("${file.upload-path}")
    private String uploadPath;
    @Value("${file.upload-path-app}")
    private String appUploadPath;
    @Autowired
    private SlaEventUrgentService slaEventUrgentService;
    @Autowired
    private SlaEventImpactService slaEventImpactService;
    @Autowired
    private EventServiceCategoryService eventServiceCategoryService;
    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;
    @Autowired
    private SlaEventService slaEventService;
    @Autowired
    private SlaEventConfigService configService;
    @Autowired
    private EventAssetsService eventAssetsService;
    @Autowired
    private AssetsManageService assetsManageService;
    @Autowired
    private EventRequestTypeService eventRequestTypeService;

    /**
     * 开单
     */
    @RequestMapping(value = "saveWantToBill", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveWantToBill(@RequestBody EventVo eventVo) {
        if (eventVo != null) {
            if (null != eventVo.getId()) {
                return eventService.updateWantToBill(eventVo, null);
            } else {
                return eventService.saveWantToBill(eventVo, null);
            }
        } else {
            return ResultInfo.error("请填写必填信息！！！");
        }
    }

    /**
     * 修改页面数据回显
     */
    @RequestMapping(value = "updateWantToBill", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateWantToBill(Integer id) {
        Event event = eventService.selectById(id);
        if (null != event) {
            //服务类别
            EventServiceCategory category = eventServiceCategoryService.selectById(event.getService_category());
            //服务类别名称
            if (Base.notEmpty(category)) {
                event.setParent_type_name(category.getName());
            }
            //关联配置项
            List<EventAssets> assetsList = eventAssetsService.selectList(Wrappers.<EventAssets>lambdaQuery()
                    .eq(EventAssets::getEvent_id, event.getId()).eq(EventAssets::getIs_del, "0"));
            if (!CollectionUtils.isEmpty(assetsList)) {
                EventAssets assets = assetsList.get(0);
                AssetsManage manage = assetsManageService.selectOne(new QueryWrapper<AssetsManage>().eq("id", assets.getAssets_id()).eq("is_del", "0"));
                if (null != manage) {
                    //关联项配置名称
                    event.setEventAssetName(null == manage.getAssets_number() ? "" : manage.getAssets_number());
                    event.setEventAssetId(manage.getId());
                }
            }
            //请求类型
            EventRequestType requestType = eventRequestTypeService.selectById(event.getRequest_type());
            if (Base.notEmpty(requestType)) {
                event.setRequest_type_name(requestType.getRequest_type_name());
            }
            //紧急程度
//            SlaEventUrgent urgent = slaEventUrgentService.selectById(event.getEffect_degree());
            SlaEventUrgent urgent = slaEventUrgentService.selectById(event.getUrgency_degree_id());
            if (Base.notEmpty(urgent)) {
                event.setUrgency_degree_name(urgent.getUrgent_name());
            }
            //影响度
//            SlaEventImpact slaevent = slaEventImpactService.selectById(event.getEffect_degree());
            SlaEventImpact slaevent = slaEventImpactService.selectById(event.getEffect_degree());
            if (Base.notEmpty(slaevent)) {
                event.setEffect_degree_name(slaevent.getImpact_name());
            }
            //优先级
            SlaEventImpactUrgent slaEventImpactUrgent = new SlaEventImpactUrgent();
            slaEventImpactUrgent.setImpact_id(event.getUrgency_degree_id());
            slaEventImpactUrgent.setUrgent_id(event.getEffect_degree());
            SlaEventImpactUrgentVo first = slaEventImpactUrgentService.getFirst(slaEventImpactUrgent);
            if (Base.notEmpty(first)) {
                event.setPriority_name(first.getFirst_name());
                event.setPriority_id(first.getFirst_id());
            }
            //响应SLA/ 解决SLA
            SlaEventConfig config = new SlaEventConfig();
            config.setEventCategoryId(event.getEvent_category_id()); //工单类别
            config.setRequest_type_id(event.getRequest_type());  //事件请求类型id
            config.setFirst_id(event.getPriority_id());  //事件优先级id
            if (Base.notEmpty(config.getEventCategoryId()) && Base.notEmpty(config.getRequest_type_id()) && Base.notEmpty(config.getFirst_id())) {
                List<SlaEvent> list = slaEventService.getSlaByEventCategory(config.getEventCategoryId());
                HashMap<Integer, SlaEvent> slaMap = new HashMap();
                if (list.size() != 0) {
                    list.forEach(x -> slaMap.put(x.getEventCategoryId(), x));
                    //根据类型获取对应合同的SLA
                    SlaEvent slaEvent = slaMap.get(config.getEventCategoryId());
                    //查询详细SLA配置
                    QueryWrapper<SlaEventConfig> qw = new QueryWrapper<>();
                    qw.eq("sla_event_id", slaEvent.getId());
                    qw.eq("is_del", "0");
                    if ("1".equals(slaEvent.getIs_first())) {
                        qw.eq("first_id", config.getFirst_id());
                    }
                    if ("1".equals(slaEvent.getIs_request())) {
                        qw.eq("request_type_id", config.getRequest_type_id());
                    }
                    SlaEventConfig slaEventConfig = configService.selectOne(qw);
                    if (Base.notEmpty(slaEventConfig)) {
                        event.setResponseSLA(slaEventConfig.getResponse_time() + "分钟 / " + slaEventConfig.getSolve_time() + "分钟");
                    } else {
                        event.setResponseSLA("暂无");
                    }
                } else {
                    event.setResponseSLA("暂无");
                }
            }
            //附件图片
            event.setFiles(eventService.eventFileList(event.getId()));
        }
        return ResultInfo.success(event);
    }

    //添加备注
    @RequestMapping(value = "updateRemark", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo updateRemark(Integer id, String remark, Integer userId) {
        Event event = eventService.selectById(id);
        event.setRemark(remark);
        event.setLogin_id(userId);
        return eventService.updateRemark(event);
    }

    //响应事件
    @RequestMapping(value = "response", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo response(Integer id, Integer userId) {
        Event event = eventService.selectById(id);
        if (!"1".equals(event.getStatus())) {
            return ResultInfo.error("状态须为待响应");
        }
        event.setLogin_id(userId);
        return eventService.response(event);
    }

    //事件查看
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id) {
        return ResultInfo.success(eventService.getById(id));
    }

    //指派事件
    @RequestMapping(value = "designate", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo designate(@RequestBody Event event) {
        Event event1 = eventService.selectById(event.getId());
        if (!"1".equals(event1.getStatus())) {
            return ResultInfo.error("状态须为待响应");
        }
        event1.setService_groups_id(event.getService_groups_id());
        event1.setHandler(event.getHandler());
        event1.setAppointRemark(event.getAppointRemark());
        event1.setFileIds(event.getFileIds());
        event1.setLogin_id(event.getUserId());
        event1.setUserId(event.getUserId());
        return eventService.designate(event1);
    }

    //转单
    @RequestMapping(value = "transferOrder", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo transferOrder(@RequestBody Event eventVo) {
        Event event = eventService.selectById(eventVo.getId());
        if (!"2".equals(event.getStatus())) {
            return ResultInfo.error("状态须为处理中");
        }
        event.setService_groups_id(eventVo.getService_groups_id());
        event.setHandler(eventVo.getHandler());
        event.setTransferRemark(eventVo.getTransferRemark());
        event.setUserId(eventVo.getUserId());
        return eventService.transferOrder(event);
    }

    //处理事件并关单
    @RequestMapping(value = "processClose", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo processClose(@RequestBody Event event) {
        Event event1 = eventService.selectById(event.getId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null != event.getEx_time()) {
            try {
                event1.setEx_solve_time(simpleDateFormat.parse(event.getEx_time()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        event1.setSolve_type(event.getSolve_type());
        event1.setProgramme_type(event.getProgramme_type());
        event1.setExpenses(event.getExpenses());
        event1.setSolution(event.getSolution());
        event1.setFileIds(event.getFileIds());
        eventService.processClose(event1);
        return ResultInfo.success("成功", new LogVo(event.getId(), "处理保存并关单", ""));
    }

    //处理事件保存
    @RequestMapping(value = "process", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo process(@RequestBody Event event) {
        Event event1 = eventService.selectById(event.getId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null != event.getEx_time()) {
            try {
                event1.setEx_solve_time(simpleDateFormat.parse(event.getEx_time()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        event1.setSolve_type(event.getSolve_type());
        event1.setProgramme_type(event.getProgramme_type());
        event1.setExpenses(event.getExpenses());
        event1.setSolution(event.getSolution());
        return eventService.process(event1, null);
    }

    //撤单
    @RequestMapping(value = "revoke", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo revoke(Integer id, String remark, Integer userId) {
        Event event = eventService.selectById(id);
//        //待响应、处理中、已暂停
//        if (!"1".equals(event.getStatus()) && !"2".equals(event.getStatus()) && !"3".equals(event.getStatus())) {
//            return ResultInfo.error("事件状态不匹配");
//        }
        event.setRevokeRemark(remark);
        eventService.revoke(event);
        return ResultInfo.success("撤销成功", new LogVo(userId, event.getId(), "撤销", event.getRevokeRemark()));
    }

    //暂停事件
    @RequestMapping(value = "pause.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo pause(Integer id, String remark, Integer userId) {
        Event event = eventService.selectById(id);
        if (!"2".equals(event.getStatus())) {
            return ResultInfo.error("状态须为处理中");
        }
        event.setId(id);
        event.setPauseRemark(remark);
        event.setLogin_id(userId);
        return eventService.pause(event);
    }

    //恢复事件
    @RequestMapping(value = "restore.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo restore(Integer id, String remark, Integer userId) {
        Event event = eventService.selectById(id);
        if (!"3".equals(event.getStatus())) {
            return ResultInfo.error("状态必须为暂停");
        }
        event.setId(id);
        event.setReturnRemark(remark);
        event.setLogin_id(userId);
        return eventService.restore(event);
    }

    //待我处理（此功能为筛选条件，勾选后列出组内全部待本人处理工单）
    @RequestMapping(value = "toHandleList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toHandleList(Event event, @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Event> pageInfo = eventService.getAllByPage(event, pageIndex, pageSize);
        return ResultInfo.success(pageInfo);
    }

    //参与的：（展示本人参与响应、处理过的事件工单）
    @RequestMapping(value = "toMyParticipationList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toMyParticipationList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            EventVo event) {
        PageInfo<Event> page = eventService.toMyParticipationList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }

    //组内事件
    @RequestMapping(value = "toMyGroupList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toMyGroupList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    EventVo event, Integer userId) {
        event.setWX_userId(userId);
        PageInfo<Event> page = eventService.toMyGroupList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }


    //重开事件
    @RequestMapping(value = "toReopen.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toReopen(EventVo eventVo) {
        return eventService.reopenEvent(eventVo);
    }

    //删除事件
    @RequestMapping(value = "toDelete.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toDelete(Integer id, Integer userId) {
        return eventService.deleteEvent(EventVo.builder().id(id).userId(userId).build());
    }

    //撤销事件
    @RequestMapping(value = "toRevoke.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toRevoke(Integer id) {
        return eventService.revokeEvent(EventVo.builder().id(id).build());
    }

    //修改
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toEdit(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> fileHandle) {
        Event event = JSON.parseObject(body, Event.class);
        return eventService.editEvent(event, file, fileHandle);
    }

    //添加备注

    //批量关单（保存）
    @RequestMapping(value = "closingBatch.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo closingBatch(@RequestBody EventVo eventVo) {
        if (null == eventVo || CollectionUtils.isEmpty(eventVo.getIds())) {
            return ResultInfo.error("非法入参");
        }
        return eventService.closingBatch(eventVo);
    }

    //用户确认评价
    @RequestMapping(value = "evaluateUser.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo evaluateUser(Integer id, Integer event_satisfaction) {
        Event event = eventService.selectById(id);
        //单据状态 字典(1.待响应 2.处理中  3暂停 4.处理完成 5.待评价 6.已关闭 7.已撤销)
        if (!"5".equals(event.getStatus())) {
            return ResultInfo.error("事件状态不匹配");
        }
        //用户满意度【最新】
        event.setEvent_satisfaction(event_satisfaction);
        event.setStatus("6");
        return eventService.evaluateUser(event);
    }

    //资产盘点列表
    @RequestMapping(value = "assetsInventoryList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo assetsInventoryList(@RequestParam(defaultValue = "1") Integer pageIndex,
                                          @RequestParam(defaultValue = "10") Integer pageSize, AssetsInventoryDetailsVo beanParam) {
        PageInfo<AssetsInventoryDetailsVo> page = assetsInventoryDetailsService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }

    //资产盘点查看详情
    @RequestMapping(value = "getInventoryDetails.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getInventoryDetails(AssetsInventoryDetailsVo beanParam) {
        return assetsInventoryDetailsService.getInventoryDetails(beanParam);
    }

    //资产盘点确认
    @RequestMapping(value = "toConfirm.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toConfirm(AssetsInventoryDetails assetsInventoryDetails) {
        return assetsInventoryDetailsService.toConfirm(assetsInventoryDetails);
    }

    //资产盘点处理
    @RequestMapping(value = "toConfirmHandle.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toConfirmHandle(AssetsInventoryDetails assetsInventoryDetails) {
        return assetsInventoryDetailsService.toDeal(assetsInventoryDetails);
    }

    //资产盘点处理
    @RequestMapping(value = "toDeal.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toDeal(AssetsInventoryDetails assetsInventoryDetails) {
        return assetsInventoryDetailsService.toDeal(assetsInventoryDetails);
    }

    //资产盘点删除
    @RequestMapping(value = "delBatch.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delBatch(String ids) {
        assetsInventoryDetailsService.delBatch(ids);
        return ResultInfo.success();
    }


    /**
     * 查询
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsInventory beanParam) {
        PageInfo<AssetsInventory> page = assetsInventoryService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * ID查询
     */
    @RequestMapping(value = "getToryById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getToryById(Integer id) {
        return assetsInventoryService.getById(id);
    }

    /**
     * 所属盘点期
     */
    @RequestMapping(value = "getAllPanDianQi.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllPanDianQi() {
        return assetsInventoryService.getAllPanDianQi();
    }

    /**
     * 配置项列表
     */
    @RequestMapping(value = "getAllAssetsType.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAll() {
        JSONObject jsonObject = new JSONObject();
        List<AssetsType> list = assetsTypeService.getAll();
        jsonObject.put("list", list);
        List<String> nameList = new ArrayList<>();
        list.forEach(x -> {
            nameList.add(x.getName());
        });
        jsonObject.put("nameList", nameList);
        return ResultInfo.success(jsonObject);
    }

    /**
     * 配置项子类列表
     */
    @RequestMapping(value = "getACByAssetsTypeId.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getACByAssetsTypeId(AssetsChildren beanParam) {
        return assetsChildrenService.getACByAssetsTypeId(beanParam);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "addTory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsInventory(AssetsInventory assetsInventory) {
        if (null == assetsInventory.getId()) {
            return assetsInventoryService.addAssetsInventory(assetsInventory);
        } else {
            return assetsInventoryService.editAssetsInventory(assetsInventory);
        }
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "editTory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsInventory(AssetsInventory assetsInventory) {
        return assetsInventoryService.editAssetsInventory(assetsInventory);
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "toDelAssetsInventory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toDelAssetsInventory(AssetsInventory assetsInventory) {
        return assetsInventoryService.delAssetsInventory(assetsInventory);
    }

    @RequestMapping(value = "toryDelBatch.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toryDelBatch(String ids) {
        assetsInventoryService.delBatch(ids);
        return ResultInfo.success();
    }


    /**
     * 工作台列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @author wh
     */
    @RequestMapping(value = "toWorkBaseList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toWorkBaseList(Event event,
                                     @RequestParam(defaultValue = "1") Integer pageIndex,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     Integer userId) {
        event.setWX_userId(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null != event.getBillStartTime()) {
            try {
                event.setCreate_time_start(simpleDateFormat.parse(event.getBillStartTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (null != event.getBillEndTime()) {
            try {
                event.setCreate_time_end(simpleDateFormat.parse(event.getBillEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println(event);
        PageInfo<Event> pageInfo = eventService.getAllByPage(event, pageIndex, pageSize);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 全部事件
     */
    @RequestMapping(value = "toAllEvent.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAllEvent(@RequestParam(defaultValue = "1") Integer pageIndex,
                                 @RequestParam(defaultValue = "10") Integer pageSize, Event eventVo) {
        PageInfo<Event> pageInfo = eventService.toList(pageIndex, pageSize, eventVo);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 得到来源
     */
    @RequestMapping(value = "getAllSource.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllSource(EventSource eventSource) {
        return ResultInfo.success(eventSourceService.getList(eventSource));
    }

    /**
     * 所有解决方式
     */
    @RequestMapping("getAllTypeInfo")
    @ResponseBody
    public ResultInfo getAllTypeInfo() {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<EventSolutionType> list = eventSolutionTypeService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    /**
     * 所有方案分类
     */
    @RequestMapping("getAllSchemeClassification")
    @ResponseBody
    public ResultInfo getAllSchemeClassification() {
        QueryWrapper<SchemeClassification> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", "0");
        List<SchemeClassification> fileCatalogs = schemeClassificationService.selectList(wrapper);
        return ResultInfo.success(fileCatalogs);
    }

    /**
     * 获取所有服务群组
     *
     * @return
     */
    @RequestMapping(value = "getAllManageInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllManageInfo() {
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<EventServiceManage> list = eventServiceManageService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping(value = "getAllUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllUser() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<SysUser> list = sysUserService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }


    /**
     * 获取所有来源
     */
    @RequestMapping(value = "getAllSource", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllSource() {
        QueryWrapper<EventSource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<EventSource> sourceList = eventSourceService.selectList(queryWrapper);
        return ResultInfo.success(sourceList);
    }


    /**
     * @param file
     * @param request
     * @return com.jsdc.itss.vo.ResultInfo
     * @description 文件上传
     * @author wp
     * @date 2022/3/21
     */
    @RequestMapping("uploadFile.do")
    public ResultInfo fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Integer id = fileManageService.fileUpload(file);
        FileManage fileManage = fileManageService.selectById(id);
        fileManage.setFile_path(appUploadPath + fileManage.getFile_name());
        return ResultInfo.success(fileManage);
    }

    /**
     * 查询资产盘点的配置项信息,根据id
     */
    @RequestMapping(value = "getAssetsConfig.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAssetsConfig(Integer id) {
        AssetsInventoryDetailsVo assetsInventoryDetailsVo = new AssetsInventoryDetailsVo();
        assetsInventoryDetailsVo.setId(id);
        return assetsInventoryDetailsService.getInventoryDetails(assetsInventoryDetailsVo);
    }

    /**
     * 事件紧急度列表查询
     */
    @RequestMapping(value = "urgencyGetAllList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo urgencyGetAllList(SlaEventUrgent slaEventUrgent) {
        JSONObject jsonObject = new JSONObject();
        List<SlaEventUrgent> list = slaEventUrgentService.getList(slaEventUrgent);
        jsonObject.put("list", list);
        List<String> nameList = new ArrayList<>();
        list.forEach(x -> {
            nameList.add(x.getUrgent_name());
        });
        jsonObject.put("nameList", nameList);
        return ResultInfo.success(jsonObject);
    }

    /**
     * 事件影响度查询
     */
    @RequestMapping(value = "affectGetAllList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo affectGetAllList(SlaEventImpact slaEventImpact) {
        JSONObject jsonObject = new JSONObject();
        List<SlaEventImpact> list = slaEventImpactService.getList(slaEventImpact);
        jsonObject.put("list", list);
        List<String> nameList = new ArrayList<>();
        list.forEach(x -> {
            nameList.add(x.getImpact_name());
        });
        jsonObject.put("nameList", nameList);
        return ResultInfo.success(jsonObject);
    }

    /**
     * 自助服务类别  树形结构  列表接口
     */
    @RequestMapping(value = "tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo treeInfo() {
        TreeDataVo bean = new TreeDataVo();
        bean.setName("全部服务类别");
        bean.setId(0);
        List<TreeDataVo> list = eventServiceCategoryService.tree2();
        bean.setChildren(list);
        return ResultInfo.success(bean);
    }

    /**
     * 根据紧急度、影响度查询优先级
     */
    @RequestMapping(value = "getFirst.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getFirst(SlaEventImpactUrgent slaEventImpactUrgent) {
        return ResultInfo.success(slaEventImpactUrgentService.getFirst(slaEventImpactUrgent));
    }

    /**
     * 根据工单类别id查询
     * 根据优先级、请求类型查询SLA的结果
     *
     * @return
     */
    @RequestMapping(value = "categoryList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo categoryList(SlaEventConfig config) {
        List<SlaEvent> list = slaEventService.getSlaByEventCategory(config.getEventCategoryId());
        HashMap<Integer, SlaEvent> slaMap = new HashMap();
        if (list.size() != 0) {
            list.forEach(x -> slaMap.put(x.getEventCategoryId(), x));
            //根据类型获取对应合同的SLA
            SlaEvent slaEvent = slaMap.get(config.getEventCategoryId());
            //查询详细SLA配置
            QueryWrapper<SlaEventConfig> qw = new QueryWrapper<>();
            qw.eq("sla_event_id", slaEvent.getId());
            qw.eq("is_del", "0");
            if ("1".equals(slaEvent.getIs_first())) {
                qw.eq("first_id", config.getFirst_id());
            }
            if ("1".equals(slaEvent.getIs_request())) {
                qw.eq("request_type_id", config.getRequest_type_id());
            }
            return ResultInfo.success(configService.selectOne(qw));
        } else {
            return ResultInfo.success("1");
        }
    }


    /**
     * 新增开单接口
     * Author wzn
     * Date 2022/3/14 11:16
     */
    @RequestMapping(value = "addEvent.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addEvent(@RequestBody EventVo eventVo) {
        if (Base.notEmpty(eventVo)) {
            return eventService.addEvent(eventVo, null, null);
        } else {
            return ResultInfo.error("请填写必填信息！！！");
        }
    }

    /**
     * 保存并关单
     * Author wzn
     * Date 2022/3/14 15:10
     */
    @RequestMapping(value = "closeSave.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo closeSave(@RequestBody EventVo eventVo) {
        eventService.closeSave(eventVo);
        return ResultInfo.success();
    }
}
