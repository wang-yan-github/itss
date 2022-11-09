package com.jsdc.itss.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
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
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.service.*;
import com.jsdc.itss.vo.LogVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("event")
public class EventController {
    @Value("${file.upload-path}")
    private String uploadPath;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventCategoryService eventCategoryService;
    @Autowired
    private EventTemplateService templateService;
    @Autowired
    private SysDepartmentService departmentService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSqlService sysSqlService;
    @Autowired
    private SlaEventService slaEventService;
    @Autowired
    private SlaEventConfigService configService;
    @Autowired
    private EventServiceCategoryService eventServiceCategoryService;
    @Autowired
    private EventAssetsService eventAssetsService;
    @Autowired
    private AssetsManageService assetsManageService;

    //    @Autowired
//    private EventCategoryService eventCategoryService;
    @Autowired
    ChangeFileService changeFileService;

    /**
     * 新增开单接口
     * Author wzn
     * Date 2022/3/14 11:16
     */
    @RequestMapping(value = "addEvent", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addEvent(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> file1) {
        EventVo eventVo = JSON.parseObject(body, EventVo.class);
        if (Base.notEmpty(eventVo)) {
            return eventService.addEvent(eventVo, file, file1);
        } else {
            return ResultInfo.error("请填写必填信息！！！");
        }
    }

    /**
     * 备注操作接口
     * Author wzn
     * Date 2022/3/14 14:48
     */
    @RequestMapping(value = "updateRemark", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo updateRemark(@RequestBody Event event) {
        return eventService.updateRemark(event);
    }

    /**
     * 保存并关单
     * Author wzn
     * Date 2022/3/14 15:10
     */
    @RequestMapping(value = "closeSave", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult closeSave(@RequestBody EventVo eventVo) {
        eventService.closeSave(eventVo);
        return AjaxResult.success();
    }

    /**
     * 响应接口
     * Author wzn
     * Date 2022/3/14 15:22
     */
    @RequestMapping(value = "response", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo response(@RequestBody Event event) {

        return eventService.response(event);
    }

    /**
     * 指派操作
     * Author wzn
     * Date 2022/3/15 9:40
     */
    @RequestMapping(value = "designate", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo designate(@RequestBody Event event) {

        return eventService.designate(event);
    }

    /**
     * 转单操作
     * Author wzn
     * Date 2022/3/15 9:40
     */
    @RequestMapping(value = "transferOrder", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo transferOrder(@RequestBody Event eventVo) {
        return eventService.transferOrder(eventVo);
    }


    /**
     * 处理保存接口
     * Author wzn
     * Date 2022/3/16 9:16
     */
    @RequestMapping(value = "process", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo process(@RequestParam String body, @RequestParam List<MultipartFile> fileHandle) {
        Event event = JSON.parseObject(body, Event.class);
        return eventService.process(event, fileHandle);
    }

    /**
     * 处理保存并关单接口
     * Author wzn
     * Date 2022/3/16 9:16
     */
    @RequestMapping(value = "processClose", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo processClose(@RequestBody Event event) {
        event.setSolve_user_id(sysUserService.getUser().getId());
        event.setEx_solve_time(new Date());
        eventService.processClose(event);
        return ResultInfo.success("成功", new LogVo(event.getId(), "处理保存并关单", ""));
    }

    /**
     * 撤销接口
     * Author wzn
     * Date 2022/3/16 9:16
     */
    @RequestMapping(value = "revoke", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo revoke(@RequestBody Event event) {
        eventService.revoke(event);
        return ResultInfo.success("撤销成功", new LogVo(event.getId(), "撤销", event.getRevokeRemark()));
    }


    /**
     * 列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @author wh
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(Event event,
                             @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<Event> pageInfo = eventService.getAllByPage(event, pageIndex, pageSize);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 我参与的
     */
    @RequestMapping(value = "toMyParticipationList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toMyParticipationList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, EventVo event) {
        PageInfo<Event> page = eventService.toMyParticipationList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }

    /**
     * 组内事件的
     */
    @RequestMapping(value = "toMyGroupList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toMyGroupList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, EventVo event) {
        PageInfo<Event> page = eventService.toMyGroupList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }

    @RequestMapping(value = "getInfoById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getInfoById(Integer id) {
        return ResultInfo.success(eventService.getById(id));
    }

    /**
     * 暂停事件
     *
     * @param event
     * @return
     */
    @RequestMapping(value = "pause.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo pause(@RequestBody Event event) {
        return eventService.pause(event);
    }

    /**
     * 恢复暂停事件
     *
     * @param event
     * @return
     */
    @RequestMapping(value = "restore.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo restore(@RequestBody Event event) {
        return eventService.restore(event);
    }


    /**
     * 按年统计事件
     */
    @RequestMapping(value = "eventByYear.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo eventByYear(@RequestBody Event event) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.orderByAsc("create_time");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        List<Event> list = eventService.selectList(queryWrapper);
        List<Integer> yearList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (Event temp : list) {
            Integer year = temp.getCreate_time().getYear() + 1900;
            System.out.println(year);

            int count = 0;
            for (int i = 0; i < yearList.size(); i++) {
                if (yearList.get(i).intValue() == year) {
                    Integer value = map.get(yearList.get(i));
                    value++;
                    map.put(yearList.get(i), value);
                    count++;
                }
            }
            if (count == 0) {
                yearList.add(year);
                map.put(year, 1);
            }

        }

        List<Integer> numList = new ArrayList<>();
        for (Integer temp : yearList) {
            Integer val = map.get(temp);
            numList.add(val);
        }
        EventVo eventVo = new EventVo();
        eventVo.setNumList(numList);
        eventVo.setYearList(yearList);
        return ResultInfo.success(eventVo);
    }

    /**
     * 按月统计事件
     *
     * @return
     */
    @RequestMapping(value = "eventByMonth.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo eventByMonth(@RequestBody Event event) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.orderByAsc("create_time");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        List<Event> list = eventService.selectList(queryWrapper);
        List<String> monthList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        for (Event temp : list) {
            String month = simpleDateFormat.format(temp.getCreate_time());

            int count = 0;
            for (int i = 0; i < monthList.size(); i++) {
                if (monthList.get(i).equals(month)) {
                    count++;
                    Integer value = map.get(monthList.get(i));
                    value++;
                    map.put(monthList.get(i), value);
                }
            }
            if (count == 0) {
                monthList.add(month);
                map.put(month, 1);
            }
        }
        List<Integer> numList = new ArrayList<>();
        for (String temp : monthList) {
            Integer val = map.get(temp);
            numList.add(val);
        }
        EventVo eventVo = new EventVo();
        eventVo.setNumList(numList);
        eventVo.setMonthList(monthList);
        return ResultInfo.success(eventVo);

    }

    /**
     * 统计
     */
    @RequestMapping(value = "statis.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo statis(@RequestBody Event event) {
        List<EventVo> list = new ArrayList<>();

        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count1 = eventService.selectCount(queryWrapper);
        EventVo eventVo1 = EventVo.builder().title("事件个数").num(count1.intValue()).build();
        list.add(eventVo1);

        queryWrapper.in("status", "4", "5", "6", "7");
        Long count2 = eventService.selectCount(queryWrapper);
        EventVo eventVo2 = EventVo.builder().title("完成数").num(count2.intValue()).build();
        list.add(eventVo2);

        QueryWrapper<Event> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("is_del", "0");
        queryWrapper1.eq("status", "1");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper1.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count3 = eventService.selectCount(queryWrapper1);
        EventVo eventVo3 = EventVo.builder().title("待响应").num(count3.intValue()).build();
        list.add(eventVo3);


        QueryWrapper<Event> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("is_del", "0");
        queryWrapper2.eq("status", "2");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper2.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count4 = eventService.selectCount(queryWrapper2);
        EventVo eventVo4 = EventVo.builder().title("处理中").num(count4.intValue()).build();
        list.add(eventVo4);


        QueryWrapper<Event> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.eq("is_del", "0");
        queryWrapper5.eq("status", "3");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper5.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count5 = eventService.selectCount(queryWrapper5);
        EventVo eventVo5 = EventVo.builder().title("暂停数").num(count5.intValue()).build();
        list.add(eventVo5);

        QueryWrapper<Event> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.eq("is_del", "0");
        queryWrapper6.eq("status", "7");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper6.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count6 = eventService.selectCount(queryWrapper6);
        EventVo eventVo6 = EventVo.builder().title("撤单数").num(count6.intValue()).build();
        list.add(eventVo6);

        QueryWrapper<Event> queryWrapper7 = new QueryWrapper<>();
        queryWrapper7.eq("is_del", "0");
        queryWrapper7.gt("numberOfRestarts", 0);
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper7.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count7 = eventService.selectCount(queryWrapper7);
        EventVo eventVo7 = EventVo.builder().title("重开数").num(count7.intValue()).build();
        list.add(eventVo7);


        QueryWrapper<Event> queryWrapper8 = new QueryWrapper<>();
        queryWrapper8.eq("is_del", "0");
        queryWrapper8.eq("meetResponseSLA", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper8.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count8 = eventService.selectCount(queryWrapper8);
        EventVo eventVo8 = EventVo.builder().title("不符合响应SLA").num(count8.intValue()).build();
        list.add(eventVo8);

        Long count9 = count1 - count8;
        EventVo eventVo9 = EventVo.builder().title("符合响应SLA").num(count9.intValue()).build();
        list.add(eventVo9);

        QueryWrapper<Event> queryWrapper10 = new QueryWrapper<>();
        queryWrapper10.eq("is_del", "0");
        queryWrapper10.eq("meetProcessingSLAs", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper10.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long count10 = eventService.selectCount(queryWrapper10);
        EventVo eventVo10 = EventVo.builder().title("不符合处理SLA").num(count10.intValue()).build();
        list.add(eventVo10);

        Long count11 = count1 - count10;
        EventVo eventVo11 = EventVo.builder().title("符合处理SLA").num(count11.intValue()).build();
        list.add(eventVo11);


        QueryWrapper<Event> queryWrapper11 = new QueryWrapper<>();
        queryWrapper11.eq("is_del", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper11.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }

        List<Event> tempEvent = eventService.selectList(queryWrapper11);
        BigDecimal realcost = new BigDecimal(0);
        for (Event temp : tempEvent) {
            String expenses = temp.getExpenses();
            if (null != expenses && !"".equals(expenses)) {
                realcost = realcost.add(new BigDecimal(expenses));
            }
        }
        EventVo eventVo12 = EventVo.builder().title("费用").num(Integer.parseInt(realcost.toString())).build();
        list.add(eventVo12);


        return ResultInfo.success(list);

    }

    /**
     * 统计事件与类别
     */
    @RequestMapping(value = "statisCate.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo statisCate(@RequestBody Event event) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        List<Event> list = eventService.selectList(queryWrapper);
        List<Integer> idList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (Event temp : list) {
            Integer id = temp.getEvent_category_id();
            if (null != id && !"".equals(id)) {
                int count = 0;
                for (int i = 0; i < idList.size(); i++) {
                    if (idList.get(i).intValue() == id) {
                        count++;
                        Integer value = map.get(id);
                        value++;
                        map.put(id, value);
                    }
                }
                if (count == 0) {
                    idList.add(id);
                    map.put(id, 1);
                }
            }
        }
        List<String> categoryList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (Integer temp : idList) {
            EventCategory eventCategory = eventCategoryService.selectById(temp.intValue());
            if (null != eventCategory) {
                categoryList.add(eventCategory.getName());
                Integer value = map.get(temp);
                numList.add(value);
            }
        }
        EventVo eventVo = new EventVo();
        eventVo.setNumList(numList);
        eventVo.setCategoryName(categoryList);
        return ResultInfo.success(eventVo);
    }


    /**
     * 统计工单完成率 响应SLA满足率 处理SLA满足率
     */
    @RequestMapping(value = "statisFinish.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo statisFinish(@RequestBody Event event) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long allCount = eventService.selectCount(queryWrapper);
        queryWrapper.in("status", "4", "5", "6", "7");
        Long finishCount = eventService.selectCount(queryWrapper);
        EventVo eventVo = new EventVo();
        if (allCount != 0) {
            Long val = finishCount * 100 / allCount;
            eventVo.setFinish(val.intValue());
        } else {
            eventVo.setFinish(100);
        }


        QueryWrapper<Event> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("is_del", "0");
        queryWrapper1.eq("meetResponseSLA", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper1.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long resCount = eventService.selectCount(queryWrapper1);
        if (allCount != 0) {
            resCount = allCount - resCount;
            Long count = resCount * 100 / allCount;
            eventVo.setResSla(count.intValue());
        } else {
            eventVo.setResSla(100);
        }


        QueryWrapper<Event> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("is_del", "0");
        queryWrapper2.eq("meetProcessingSLAs", "0");
        if (null != event.getCreate_time_start() && null != event.getCreate_time_relend()) {
            queryWrapper2.between("create_time", event.getCreate_time_start(), event.getCreate_time_relend());
        }
        Long resCountTemp = eventService.selectCount(queryWrapper2);
        if (allCount != 0) {
            resCountTemp = allCount - resCountTemp;
            Long countTemp = resCountTemp * 100 / allCount;
            eventVo.setDealSla(countTemp.intValue());
        } else {
            eventVo.setDealSla(100);
        }

        return ResultInfo.success(eventVo);


    }


    /**
     * 用户历史记录
     *
     * @return
     */
    @RequestMapping(value = "userHistory", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo userHistory(@RequestBody EventVo vo) {
        return ResultInfo.success(eventService.userHistory(vo));
    }


    /**
     * 点击模板，填写内容
     *
     * @return
     */
    @RequestMapping(value = "mouldList", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo mouldList(Integer id) {
        JSONObject object = new JSONObject();
        EventTemplate eventTemplate = templateService.eventTemplateInfo(id);
        SysUser sysUser = sysUserService.selectById(eventTemplate.getUser_id());
        if (null != sysUser && null != sysUser.getDepartment_id()) {
            SysDepartment dt = departmentService.selectById(sysUser.getDepartment_id());
            sysUser.setDepartmentName(dt.getName());
        }
        //处理人
        SysUser clrUser = sysUserService.selectById(eventTemplate.getHandler());
        object.put("clrUser", clrUser);
        object.put("sysUser", sysUser);
        object.put("eventTemplate", eventTemplate);
        return ResultInfo.success(object);
    }

    /**
     * 查询与事件关联得syssql信息
     * 扩展报表
     */
    @RequestMapping(value = "getSysSql.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getSysSql() {
        QueryWrapper<SysSql> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("module", "1");
        queryWrapper.eq("is_del", "0");
        List<SysSql> list = sysSqlService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    /**
     * 根据工单类别id查询
     * 根据优先级、请求类型查询SLA的结果
     *
     * @return
     */
    @RequestMapping(value = "categoryList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo categoryList(@RequestBody SlaEventConfig config) {
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


    //我要开单
    @RequestMapping(value = "SaveWantToBill.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo wantToBill(@RequestBody Event event) {
        return ResultInfo.success();
    }

    @RequestMapping(value = "saveWantToBill", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveWantToBill(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        EventVo eventVo = JSON.parseObject(body, EventVo.class);
        if (eventVo != null) {
            SysUser sysUser_old = sysUserService.getUser();
            SysUser sysUser = sysUserService.selectById(sysUser_old.getId());
            sysUser.setServiceTypeId(eventVo.getService_category());
            sysUser.updateById();
            if (null != eventVo.getId()) {
                return eventService.updateWantToBill(eventVo, file);
            } else {
                return eventService.saveWantToBill(eventVo, file);
            }
        } else {
            return ResultInfo.error("请填写必填信息！！！");
        }
    }

    //修改开单内容
    @RequestMapping(value = "updateWantToBill", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateWantToBill(String id) {
        Event event = eventService.selectById(id);
        if (null != event) {
            //服务类别
            EventServiceCategory category = eventServiceCategoryService.selectById(event.getService_category());
            //服务类别名称
            if (Base.notEmpty(category)) {
                event.setParent_type_name(category.getName());
            }

            //关联配置项
//            EventAssets assets = eventAssetsService.selectOne(new QueryWrapper<EventAssets>().eq("event_id", event.getId()));
            List<EventAssets> assetsList = eventAssetsService.selectList(Wrappers.<EventAssets>lambdaQuery()
                    .eq(EventAssets::getEvent_id, event.getId()).eq(EventAssets::getIs_del, "0"));
            if (!CollectionUtils.isEmpty(assetsList)) {
                EventAssets assets = assetsList.get(0);
                AssetsManage manage = assetsManageService.selectOne(new QueryWrapper<AssetsManage>().eq("id", assets.getAssets_id()).eq("is_del", "0"));
                if (null != manage) {
                    //关联项配置名称
                    event.setEventAssetName(manage.getAssets_number());
                }

            }
            event.setFiles(eventService.eventFileList(event.getId()));
        }
        return ResultInfo.success(event);
    }

    //用户评价
    @RequestMapping(value = "evaluateUser.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo evaluateUser(@RequestBody Event event) {
        event.setStatus("6");
        return eventService.evaluateUser(event);
    }

    @Autowired
    private EventParamConfigService eventParamConfigService;

    /**
     * 判断该工单能否重开
     *
     * @return
     */

    //用户评价
    @RequestMapping(value = "isReopen", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo isReopen(Integer eventId) {
        EventParamConfig eventParamConfig = eventParamConfigService.selectById(1);
        //如果启用重开时效性，则判断时间返回是否允许重开
        if ("1".equals(eventParamConfig.getIs_reopen())) {
            Event event = eventService.selectById(eventId);
            long betweenDay = DateUtil.between(event.getCreate_time(), new Date(), DateUnit.DAY);
            if (eventParamConfig.getReopen_day() != null && betweenDay >= eventParamConfig.getReopen_day()) {
                return ResultInfo.error("已经超过重开时效！");
            } else {
                return ResultInfo.success();
            }
        } else {
            return ResultInfo.success();
        }
    }

    /**
     * 处理页面 协同处理人 服务群组【处理工程师】
     */
    @RequestMapping("getSynergiaUsers.do")
    @ResponseBody
    public ResultInfo getSynergiaUsers(Integer id) {
        List<SysUser> list = eventService.getSynergiaUsers(id);
        return ResultInfo.success(list);
    }

}
