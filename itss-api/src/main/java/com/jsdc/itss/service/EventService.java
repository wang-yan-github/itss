package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.EventDao;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.enums.EventStatusEnums;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.enums.QuestionStatusEnums;
import com.jsdc.itss.exception.CustomException;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.utils.*;
import com.jsdc.itss.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@SuppressWarnings("ALL")
public class EventService extends BaseService<EventDao, Event> {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventAssistMapper eventAssistMapper;

    @Autowired
    private EventAssetsMapper eventAssetsMapper;

    @Autowired
    private AssetsManageService assetsManageService;

    @Autowired
    private EventRelationMapper eventRelationMapper;

    @Autowired
    private EventQuestionRelationMapper questionRelationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private EventChangeRelationMapper changeRelationMapper;

    @Autowired
    private ChangeInfoService changeInfoService;

    @Autowired
    private EventServiceManageMapper eventServiceManageMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private EventSourceMapper eventSourceMapper;

    @Autowired
    private EventCategoryMapper eventCategoryMapper;

    @Autowired
    private SlaEventUrgentMapper urgentMapper;

    @Autowired
    private SlaEventFirstMapper eventFirstMapper;

    @Autowired
    private EventSatisfactionMapper satisfactionMapper;

    @Autowired
    private EventRequestTypeMapper requestTypeMapper;

    @Autowired
    private SchemeClassificationMapper schemeClassificationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SlaEventImpactMapper slaEventImpactMapper;

    @Autowired
    SysUserService sysUserService;
    @Autowired
    private SysUserEventGroupMapper sysUserEventGroupMapper;
    @Autowired
    private EventBillingDesignateService eventBillingDesignateService;
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private EventFileMapper eventFileMapper;
    @Autowired
    private OperateNodeService operateNodeService;
    @Autowired
    EventServiceManageService eventServiceManageService;
    @Autowired
    private EventTimesService eventTimesService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SlaEventFirstMapper slaEventFirstMapper;

    @Autowired
    private SlaEventService slaEventService;

    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;

    @Autowired
    private SlaEventConfigService slaEventConfigService;

    @Autowired
    private EventSolutionTypeMapper eventSolutionTypeMapper;

    @Autowired
    private EventTimesMapper eventTimesMapper;

    @Autowired
    private EventSolutionTypeService eventSolutionTypeService;

    @Autowired
    private SlaEventUrgentMapper slaEventUrgentMapper;

    @Autowired
    private EventRequestTypeMapper eventRequestTypeMapper;

    @Autowired
    private EventBillingDesignateMapper eventBillingDesignateMapper;

    @Value("${file.upload-path}")
    private String uploadPath;
    @Value("${file.upload-path-app}")
    private String appUploadPath;


    /**
     * 新增开单接口
     * Author wzn
     * Date 2022/3/14 11:16
     */
    public ResultInfo addEvent(EventVo eventVo, List<MultipartFile> files, List<MultipartFile> files1) {
//        Event event = eventVo.getEvent();
        Event event = new Event();
        BeanUtils.copyProperties(eventVo, event);
        event.setIs_del("0");
        event.setRequest_type(Integer.valueOf(eventVo.getRequest_type()));
        event.setEffect_degree(Integer.valueOf(eventVo.getEffect_degree()));
        event.setStatus("1");
        event.setCreate_time(new Date());

        Integer userId = null;
        //1 定时生成工单标识
        if (Base.notEmpty(eventVo.getTimeTaskStatus()) && eventVo.getTimeTaskStatus().equals("1")) {
            userId = eventVo.getCreate_user();
        } else {
            SysUser userData = sysUserService.getUserData(eventVo.getUserId());
            userId = userData.getId();
        }
        event.setCreate_user(userId);
        eventMapper.insert(event);
        if (null != event.getHandler()) {
            //添加operate_node
            OperateNode operateNode = new OperateNode();
            operateNode.setBill_id(event.getId());
            operateNode.setBill_type(GlobalData.EVENT);
            operateNode.setOperate_user(event.getHandler());
            operateNode.setOperate_type(GlobalData.EVENT_STATUS_DXY);
            operateNode.setIs_complete(GlobalData.IS_COMPLETE_NO);
            operateNode.setTiming_start(new Date());
            operateNode.setTime_spent(0);
            operateNode.setCreate_time(new Date());
            operateNode.setUpdate_time(new Date());
            operateNodeService.insert(operateNode);
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventAssist1())) {
            for (SysUserPageVo e : eventVo.getEventAssist1()) {
                int id = e.getId();
                EventAssist eventAssist = new EventAssist();
                eventAssist.setUser_id(id);
                eventAssist.setEvent_id(event.getId());
                eventAssist.setIs_del("0");
                eventAssist.setCreate_time(new Date());
                eventAssist.setCreate_user(userId);
                eventAssistMapper.insert(eventAssist);
            }
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventAssets1())) {
            for (AssetsManage e : eventVo.getEventAssets1()) {
                int id = e.getId();
                EventAssets eventAssets = new EventAssets();
                eventAssets.setAssets_id(id);
                eventAssets.setEvent_id(event.getId());
                eventAssets.setCreate_time(new Date());
                eventAssets.setCreate_user(userId);
                eventAssets.setIs_del("0");
                eventAssetsMapper.insert(eventAssets);
            }
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventRelation1())) {
            for (Event e : eventVo.getEventRelation1()) {
                int id = e.getId();
                EventRelation eventRelation = new EventRelation();
                eventRelation.setEvent_id(id);
                eventRelation.setParent_event_id(event.getId());
                eventRelation.setCreate_time(new Date());
                eventRelation.setCreate_user(userId);
                eventRelation.setIs_del("0");
                eventRelationMapper.insert(eventRelation);
            }
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(id);
                eventFile.setType("1");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(userId);
                eventFile.setIs_del("0");
                eventFileMapper.insert(eventFile);
            }
        }

        if (!CollectionUtils.isEmpty(files1)) {
            for (MultipartFile file : files1) {
                int id = fileManageService.fileUpload(file);
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(id);
                eventFile.setType("2");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(userId);
                eventFile.setIs_del("0");
                eventFileMapper.insert(eventFile);
            }
        }
        //事件ID
        EventTimes times = new EventTimes();
        times.setEvent_id(event.getId());//事件ID
        times.setResponse_times(0);
        times.setResponse_exceed_times(0);
        times.setHandle_times(0);
        times.setHandle_exceed_times(0);
        times.setIs_del("0");
        eventTimesService.insert(times);

        //生成日志
        SysLog sysLog = new SysLog();
        sysLog.setBill_id(event.getId());
        sysLog.setTitle("开单");
        sysLog.setContent("");
        sysLog.setOperate_time(new Date());
        sysLog.setModule(LogEnums.LOG_GZT.getValue());
        sysLogService.insert(sysLog);
        return ResultInfo.success(event, "开单成功!");
    }

    /**
     * 备注操作接口
     * Author wzn
     * Date 2022/3/14 14:48
     */
    public ResultInfo updateRemark(Event event) {
        eventMapper.updateById(event);
        if(null != event.getLogin_id()){
            return ResultInfo.success("备注成功!", new LogVo(event.getLogin_id(), event.getId(), "备注", event.getRemark()));
        }
        return ResultInfo.success("备注成功!", new LogVo(event.getId(), "备注", event.getRemark()));
    }

    /**
     * 保存并关单
     * Author wzn
     * Date 2022/3/14 15:10
     */
    public void closeSave(EventVo eventVo) {
        Event event = new Event();
        SysUser userData = sysUserService.getUserData(eventVo.getUserId());
        BeanUtils.copyProperties(eventVo, event);
        event.setIs_del("0");
        event.setStatus("6");
        event.setCreate_time(new Date());
        event.setCreate_user(userData.getId());
        if(null != eventMapper.selectById(event.getId())){
            eventMapper.updateById(event);
        }else{
            eventMapper.insert(event);
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventAssist1())) {
            for (SysUserPageVo e : eventVo.getEventAssist1()) {
                int id = e.getId();
                EventAssist eventAssist = new EventAssist();
                eventAssist.setUser_id(id);
                eventAssist.setEvent_id(event.getId());
                eventAssist.setIs_del("0");
                eventAssist.setCreate_time(new Date());
                eventAssist.setCreate_user(userData.getId());
                eventAssistMapper.insert(eventAssist);
            }
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventAssets1())) {
            for (AssetsManage e : eventVo.getEventAssets1()) {
                int id = e.getId();
                EventAssets eventAssets = new EventAssets();
                eventAssets.setAssets_id(id);
                eventAssets.setEvent_id(event.getId());
                eventAssets.setCreate_time(new Date());
                eventAssets.setCreate_user(userData.getId());
                eventAssets.setIs_del("0");
                eventAssetsMapper.insert(eventAssets);
            }
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventRelation1())) {
            for (Event e : eventVo.getEventRelation1()) {
                int id = e.getId();
                EventRelation eventRelation = new EventRelation();
                eventRelation.setEvent_id(id);
                eventRelation.setParent_event_id(event.getId());
                eventRelation.setCreate_time(new Date());
                eventRelation.setCreate_user(userData.getId());
                eventRelation.setIs_del("0");
                eventRelationMapper.insert(eventRelation);
            }
        }
    }

    /**
     * 响应接口
     * Author wzn
     * Date 2022/3/14 15:22
     */
    public ResultInfo response(Event event) {
        if (!"1".equals(event.getStatus())) {
            ResultInfo.success("事件状态必须为待响应");
        }
        event.setStatus("2");
        event.setResponseTime(new Date());
        Integer currentId = null == event.getLogin_id() ? sysUserService.getUser().getId() : event.getLogin_id();
        event.setHandler(currentId);
        event.setResponse_user(currentId);
        if (eventMapper.updateById(event) > 0) {
            SysUser sysUser = new SysUser();
            if (null != event.getUser_id()) {
                sysUser.setId(event.getUser_id());
            } else {
                sysUser = sysUserService.getUser();
            }
            Event original = selectById(event.getId());
            if (null == original.getHandler()) {//未经指派，工程师接单
                //添加operate_node
                OperateNode operateNode = new OperateNode();
                operateNode.setBill_id(event.getId());
                operateNode.setBill_type(GlobalData.EVENT);
                operateNode.setOperate_user(sysUser.getId());
                operateNode.setOperate_type(GlobalData.EVENT_STATUS_DXY);
                operateNode.setIs_complete(GlobalData.IS_COMPLETE_YES);
                operateNode.setTiming_start(new Date());
                operateNode.setTime_spent(0);
                operateNode.setCreate_time(new Date());
                operateNode.setUpdate_time(new Date());
                operateNodeService.insert(operateNode);
            } else {
                OperateNode operateNode = operateNodeService.selectOne(Wrappers.<OperateNode>lambdaQuery().eq(OperateNode::getBill_id, original.getId())
                        .eq(OperateNode::getBill_type, GlobalData.EVENT).eq(OperateNode::getOperate_type, GlobalData.EVENT_STATUS_DXY)
                        .eq(OperateNode::getIs_complete, GlobalData.IS_COMPLETE_NO)
                );
                if (null != operateNode) {
                    EventServiceManage eventServiceManage = eventServiceManageService.selectOne(Wrappers.<EventServiceManage>lambdaQuery().eq(EventServiceManage::getId, original.getService_groups_id()));
                    String isTimeout = "0";
                    Integer timeSpent = TimeUtils.computeMinute(operateNode.getTiming_start(), new Date());
                    if (null != eventServiceManage.getOla_time_out() && timeSpent > eventServiceManage.getOla_time_out()) {
                        isTimeout = "1";
                    }
                    operateNode.setTime_spent(timeSpent);
                    operateNode.setIs_timeout(isTimeout);
                    operateNode.setUpdate_time(new Date());
                    operateNode.setIs_complete(GlobalData.IS_COMPLETE_YES);
                    operateNodeService.updateById(operateNode);
                }
            }
            //响应后生成处理的OLA
            OperateNode operateNode = new OperateNode();
            operateNode.setBill_id(event.getId());
            operateNode.setBill_type(GlobalData.EVENT);
            operateNode.setOperate_user(sysUser.getId());
            operateNode.setOperate_type(GlobalData.EVENT_STATUS_CLZ);
            operateNode.setIs_complete(GlobalData.IS_COMPLETE_NO);
            operateNode.setTiming_start(new Date());
            operateNode.setCreate_time(new Date());
            operateNode.setUpdate_time(new Date());
            operateNodeService.insert(operateNode);
        }
        if(null == event.getLogin_id()){
            return ResultInfo.success("响应成功", new LogVo(event.getId(), "响应", StringUtils.EMPTY));
        }
        return ResultInfo.success("响应成功", new LogVo(currentId, event.getId(), "响应", StringUtils.EMPTY));
    }

    /**
     * 指派操作
     * 群组内的工程师/服务台可以对待响应状态的事件工单执行指派操作；
     * 指派后状态为： 待响应
     * Author wzn
     * Date 2022/3/15 9:40
     */
    public ResultInfo designate(Event event) {
        SysUser sysUser = new SysUser();
        if (null != event.getUser_id()) {
            sysUser.setId(event.getUser_id());
        } else {
            sysUser = sysUserService.getUser();
        }
        if (!"1".equals(event.getStatus())) {
            throw new CustomException("事件状态不是待响应");
        }
        event.setAssigner(sysUser.getId());
        event.setAssigner_time(new Date());
        event.setUpdate_time(new Date());
        if (eventMapper.updateById(event) > 0) {
            List<OperateNode> operateNodeList = operateNodeService.selectList(Wrappers.<OperateNode>lambdaQuery().eq(OperateNode::getBill_id, event.getId())
                    .eq(OperateNode::getBill_type, GlobalData.EVENT).eq(OperateNode::getOperate_type, GlobalData.EVENT_STATUS_DXY).eq(OperateNode::getIs_complete, GlobalData.IS_COMPLETE_NO)
            );
            if (!CollectionUtils.isEmpty(operateNodeList)) {
                OperateNode operateNode = operateNodeList.get(0);
                if (null != operateNode) {
                    EventServiceManage eventServiceManage = eventServiceManageService.selectOne(Wrappers.<EventServiceManage>lambdaQuery().eq(EventServiceManage::getId, event.getService_groups_id()));
                    String isTimeout = "0";
                    Integer timeSpent = TimeUtils.computeMinute(operateNode.getTiming_start(), new Date());
                    if (null != eventServiceManage.getOla_time_out() && timeSpent > eventServiceManage.getOla_time_out()) {
                        isTimeout = "1";
                    }
                    operateNode.setTime_spent(timeSpent);
                    operateNode.setIs_timeout(isTimeout);
                    operateNode.setUpdate_time(new Date());
                    operateNode.setIs_complete(GlobalData.IS_COMPLETE_YES);
                    operateNodeService.updateById(operateNode);
                }
            }

            //添加operate_node
            OperateNode operateNodeNew = new OperateNode();
            operateNodeNew.setBill_id(event.getId());
            operateNodeNew.setBill_type(GlobalData.EVENT);
            operateNodeNew.setOperate_user(event.getHandler());
            operateNodeNew.setOperate_type(GlobalData.EVENT_STATUS_DXY);
            operateNodeNew.setTiming_start(new Date());
            operateNodeNew.setIs_complete(GlobalData.IS_COMPLETE_NO);
            operateNodeNew.setCreate_time(new Date());
            operateNodeNew.setUpdate_time(new Date());
            operateNodeService.insert(operateNodeNew);
            if (null != event.getFileIds()) {
                if(StringUtils.isNotBlank(event.getFileIds())){
                    String[] ids = event.getFileIds().split(",");
                    for (int i = 0; i < ids.length; i++) {
                        EventFile eventFile = new EventFile();
                        eventFile.setEvent_id(event.getId());
                        eventFile.setFile_id(Integer.parseInt(ids[i]));
                        eventFile.setIs_del("0");
                        eventFile.setType("3");
                        eventFile.setCreate_time(new Date());
                        eventFile.setCreate_user(sysUserService.getUser().getId());
                        eventFileMapper.insert(eventFile);
                    }
                }
            }
            if(null != event.getLogin_id()){
                return ResultInfo.success("指派成功", new LogVo(event.getLogin_id(),event.getId(), "指派", StringUtils.EMPTY));
            }
            return ResultInfo.success("指派成功", new LogVo(event.getId(), "指派", StringUtils.EMPTY));
        }

        return ResultInfo.error("指派失败");
    }

    /**
     * 转单
     * Author wzn
     * Date 2022/3/15 14:38
     */
    public ResultInfo transferOrder(Event eventVo) {
        if (!EventStatusEnums.PROCESSING.getType().equals(eventVo.getStatus())) {
            throw new CustomException("事件状态不是处理中");
        }
        updateEventInfo(eventVo, null, null);
        List<OperateNode> operateNodeList = operateNodeService.selectList(Wrappers.<OperateNode>lambdaQuery().eq(OperateNode::getBill_id, eventVo.getId())
                .eq(OperateNode::getBill_type, GlobalData.EVENT).eq(OperateNode::getOperate_type, GlobalData.EVENT_STATUS_CLZ).eq(OperateNode::getIs_complete, GlobalData.IS_COMPLETE_NO)
        );
        if (!CollectionUtils.isEmpty(operateNodeList)) {
            OperateNode operateNode = operateNodeList.get(0);
            if (null != operateNode) {
                EventServiceManage eventServiceManage = eventServiceManageService.selectOne(Wrappers.<EventServiceManage>lambdaQuery().eq(EventServiceManage::getId, eventVo.getService_groups_id()));
                String isTimeout = "0";
                Integer timeSpent = TimeUtils.computeMinute(operateNode.getTiming_start(), new Date());
                if (null != eventServiceManage.getOla_time_out() && timeSpent > eventServiceManage.getOla_time_out()) {
                    isTimeout = "1";
                }
                operateNode.setTime_spent(timeSpent);
                operateNode.setIs_timeout(isTimeout);
                operateNode.setUpdate_time(new Date());
                operateNode.setIs_complete(GlobalData.IS_COMPLETE_YES);
                operateNodeService.updateById(operateNode);
            }
        }

        //添加operate_node
        OperateNode operateNodeNew = new OperateNode();
        operateNodeNew.setBill_id(eventVo.getId());
        operateNodeNew.setBill_type(GlobalData.EVENT);
        operateNodeNew.setOperate_user(eventVo.getHandler());
        operateNodeNew.setOperate_type(GlobalData.EVENT_STATUS_CLZ);
        operateNodeNew.setTiming_start(new Date());
        operateNodeNew.setIs_complete(GlobalData.IS_COMPLETE_NO);
        operateNodeNew.setCreate_time(new Date());
        operateNodeNew.setUpdate_time(new Date());
        operateNodeService.insert(operateNodeNew);
        return ResultInfo.success("转单成功", new LogVo(eventVo.getUserId(), eventVo.getId(), "转单", StringUtils.EMPTY));
    }

    /**
     * 全部时间列表查询
     */
    /**
     * 处理接口
     * Author wzn
     * Date 2022/3/16 9:06
     */
    public ResultInfo process(Event event, List<MultipartFile> filesHandle) {
        if (!"2".equals(event.getStatus())) {
            throw new CustomException("事件状态不是处理中");
        }

        updateEventInfo(event, null, filesHandle);
        return ResultInfo.success("处理成功", new LogVo(event.getId(), "处理", StringUtils.EMPTY));
    }

    /**
     * 处理接口保存并关单
     *
     * @param event
     */
    public void processClose(Event event) {
        if (!"2".equals(event.getStatus())) {
            throw new CustomException("事件状态不是处理中");
        }
        event.setStatus("5");
        event.setCloseTime(new Date());//关单时间
        eventMapper.updateById(event);
        event = eventMapper.selectById(event.getId());
        List<OperateNode> operateNodeList = operateNodeService.selectList(Wrappers.<OperateNode>lambdaQuery().eq(OperateNode::getBill_id, event.getId())
                .eq(OperateNode::getBill_type, GlobalData.EVENT).eq(OperateNode::getOperate_type, GlobalData.EVENT_STATUS_CLZ).eq(OperateNode::getIs_complete, GlobalData.IS_COMPLETE_NO)
        );
        if (!CollectionUtils.isEmpty(operateNodeList)) {
            OperateNode operateNode = operateNodeList.get(0);
            if (null != operateNode) {
                EventServiceManage eventServiceManage = eventServiceManageService.selectOne(Wrappers.<EventServiceManage>lambdaQuery().eq(EventServiceManage::getId, event.getService_groups_id()));
                String isTimeout = "0";
                Integer timeSpent = TimeUtils.computeMinute(operateNode.getTiming_start(), new Date());
                if (null != eventServiceManage.getOla_time_out() && timeSpent > eventServiceManage.getOla_time_out()) {
                    isTimeout = "1";
                }
                operateNode.setTime_spent(timeSpent);
                operateNode.setIs_timeout(isTimeout);
                operateNode.setUpdate_time(new Date());
                operateNode.setIs_complete(GlobalData.IS_COMPLETE_YES);
                operateNodeService.updateById(operateNode);
            }
        }
        if (null != event.getFileIds()) {
            String[] ids = event.getFileIds().split(",");
            for (int i = 0; i < ids.length; i++) {
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(Integer.parseInt(ids[i]));
                eventFile.setIs_del("0");
                eventFile.setType("2");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(sysUserService.getUser().getId());
                eventFileMapper.insert(eventFile);
            }
        }


    }

    /**
     * 撤销
     * Author wzn
     * Date 2022/3/16 9:12
     */
    public void revoke(Event event) {
        event.setRevokeTime(new Date());
        event.setStatus(EventStatusEnums.REVOKED.getType());
        eventMapper.updateById(event);
    }


    /**
     * 暂停接口
     * Author wzn
     * Date 2022/3/16 9:19
     */
    public ResultInfo pause(Event event) {
        if (!"2".equals(event.getStatus())) {
            return ResultInfo.error("状态必须为处理中");
        }
        event.setStatus("3");
        //设置暂停时间
        event.setPauseTime(new Date());

        EventTimes eventTimes = eventTimesService.selectOne(Wrappers.<EventTimes>lambdaQuery()
                .eq(EventTimes::getEvent_id, event.getId()).eq(EventTimes::getIs_del, GlobalData.ISDEL_NO)
//                .last("limit 1")
        );
        if (null == eventTimes) {
            return ResultInfo.error("事件时间不存在");
        }

        eventMapper.updateById(event);
        eventTimes.setLast_pause_time(new Date());
        eventTimes.setLast_pause_type("1");
        eventTimesService.updateById(eventTimes);
        if(null != event.getLogin_id()){
            return ResultInfo.success("暂停成功", new LogVo(event.getLogin_id(),event.getId(), "暂停", StringUtils.EMPTY));
        }
        return ResultInfo.success("暂停成功", new LogVo(event.getId(), "暂停", event.getPauseRemark()));
    }


    /**
     * 恢复接口
     * Author wzn
     * Date 2022/3/16 9:26
     */
    public ResultInfo restore(Event event) {
        if (!"3".equals(event.getStatus())) {
            return ResultInfo.error("状态必须为暂停");
        }
        event.setStatus("2");
        EventTimes eventTimes = eventTimesService.selectOne(Wrappers.<EventTimes>lambdaQuery().eq(EventTimes::getEvent_id, event.getId()).eq(EventTimes::getIs_del, GlobalData.ISDEL_NO));
        if (null == eventTimes) {
            return ResultInfo.error("事件时间不存在");
        }
        eventMapper.updateById(event);
        if(StringUtils.equals(eventTimes.getLast_pause_type(), "1")){
            int timelong = TimeUtils.computeMinute(eventTimes.getLast_pause_time(), new Date());
            timelong = timelong + (null == eventTimes.getManual_pause_times()?0:eventTimes.getManual_pause_times());
            eventTimes.setManual_pause_times(timelong);
        }else if(StringUtils.equals(eventTimes.getLast_pause_type(), "2")){
            int timelong = TimeUtils.computeMinute(eventTimes.getLast_pause_time(), new Date());
            timelong = timelong + (null == eventTimes.getAuto_pause_times()?0:eventTimes.getAuto_pause_times());
            eventTimes.setAuto_pause_times(timelong);
        }
        eventTimesService.updateById(eventTimes);
        if(null != event.getLogin_id()){
            return ResultInfo.success("恢复成功", new LogVo(event.getLogin_id(),event.getId(), "恢复", StringUtils.EMPTY));
        }
        return ResultInfo.success("恢复成功", new LogVo(event.getId(), "恢复", StringUtils.EMPTY));
    }


    /**
     * 全部时间列表查询
     */
    public PageInfo<Event> toList(Integer pageIndex, Integer pageSize, Event event) {
        // 根据用户id得到服务群组
        List<Integer> serviceGroups = new ArrayList<>();
        try {
            serviceGroups = eventBillingDesignateService.getServiceIdByCurrentUser(event.getWX_userId());
            // 如果找不到服务群组，就返回空集合
            if (CollectionUtils.isEmpty(serviceGroups)) {
                serviceGroups.add(-1);
            }
        } catch (Exception e) {
            serviceGroups.add(-1);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        long startTime = System.currentTimeMillis();
        Date newEndTime = null;
        if (null != event.getCreate_time_end()) {
            newEndTime = org.apache.commons.lang.time.DateUtils.addDays(event.getCreate_time_end(), 1);
        }
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<String> statusList = new ArrayList<>();
        if(StringUtils.isNotBlank(event.getStatus())){
            statusList = Arrays.asList(event.getStatus().split(","));
        }
        //
        List<Event> eventList = eventMapper.selectList(Wrappers.<Event>lambdaQuery()
                .in(null != event.getService_groups_type() && 1 == event.getService_groups_type(), Event::getService_groups_id, serviceGroups)
                .eq(Event::getIs_del, String.valueOf(0))
                // 高级查询条件 关键字
                .like(StringUtils.isNotBlank(event.getTitle()), Event::getTitle, event.getTitle())
                // 高级查询条件 id
                .eq(null != event.getId(), Event::getId, event.getId())
                // 高级查询条件 状态
                .in(!CollectionUtils.isEmpty(statusList), Event::getStatus, statusList)
                // 高级查询条件 用户id
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // 高级查询条件 开单人
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // 高级查询条件 处理人
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // 高级查询条件 来源
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // 高级查询条件 部门
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // 高级查询条件 工单类别
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // 高级查询条件 优先级
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // 高级查询条件 开单起始时间
                .ge(null != event.getCreate_time_start(), Event::getCreate_time, event.getCreate_time_start())
                // 高级查询条件 开单结束时间
                .le(null != newEndTime, Event::getCreate_time, newEndTime)
                // 高级查询条件 服务群组
                .eq(null != event.getService_groups_id(), Event::getService_groups_id, event.getService_groups_id())
                // 高级查询条件 解决工程师
                .eq(null != event.getSolve_user_id(), Event::getSolve_user_id, event.getSolve_user_id())
                // 高级查询条件 响应人
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // 高级查询条件 用户满意度 event_satisfaction
                .eq(null != event.getEvent_satisfaction(), Event::getEvent_satisfaction, event.getEvent_satisfaction())
                // 高级查询条件 方案类别 programme_type
                .eq(null != event.getProgramme_type(), Event::getProgramme_type, event.getProgramme_type())
                // 高级查询条件 符合SLA
                .eq(StringUtils.isNotBlank(event.getMeetResponseSLA()), Event::getMeetResponseSLA, event.getMeetResponseSLA())
                // 高级查询条件 关单开始时间
                .ge(null != event.getCloseStartTime(), Event::getCloseTime, event.getCloseStartTime())
                // 高级查询条件 关单结束时间
                .le(null != event.getCloseEndTime(), Event::getCloseTime, event.getCloseEndTime())
                // 高级查询条件 解决方式
                .eq(null != event.getSolve_type(), Event::getSolve_type, event.getSolve_type())
                // 高级查询条件 费用开始
                .ge(StringUtils.isNotBlank(event.getExpenses_start()), Event::getExpenses, StringUtils.isNotBlank(event.getExpenses_start()) ? new BigDecimal(event.getExpenses_start()) : null)
                // 高级查询条件 费用结束
                .le(StringUtils.isNotBlank(event.getExpenses_end()), Event::getExpenses, StringUtils.isNotBlank(event.getExpenses_end()) ? new BigDecimal(event.getExpenses_end()) : null)
                // 高级查询条件 请求类型
                .eq(null != event.getRequest_type(), Event::getRequest_type, event.getRequest_type())
        );
        // 得到耗时
        long endTime = System.currentTimeMillis();
        log.info("查询耗时：{}", endTime - startTime);
        // 得到用户map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // 得到工单类别map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // 得到服务组map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        // 得到当前登录人id
//        Integer currentUserId = sysUserService.getUser().getId();
        // 得到Priority_id的集合
        List<Integer> priorityIds = eventList.stream().map(Event::getPriority_id).collect(Collectors.toList());
        Map<Integer, String> slaEventFirstMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(priorityIds)) {
            List<SlaEventFirst> slaEventFirsts = slaEventFirstMapper.selectList(Wrappers.<SlaEventFirst>lambdaQuery().in(SlaEventFirst::getId, priorityIds));
            // 转换为map集合,key为id,value为first_name
            slaEventFirstMap = slaEventFirsts.stream().collect(Collectors.toMap(SlaEventFirst::getId, SlaEventFirst::getFirst_name, (key1, key2) -> key2));
        }
        // 得到查询剩余时间耗时
        long endTime3 = System.currentTimeMillis();
        log.info("查询字典耗时：{}", endTime3 - endTime);
        for (Event member : eventList) {
            // 单次开始时间
            long fortime1 = System.currentTimeMillis();
            // 状态名称
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // 优先级名称
            member.setPriority_name("");
            if (null != member.getPriority_id() && !CollectionUtils.isEmpty(priorityIds)) {
                member.setPriority_name(slaEventFirstMap.get(member.getPriority_id()));
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // 用户名称
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // 开单人名称
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // 处理人名称
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // 工单类别名称
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // 服务组名称
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // 单次结束时间
            long fortime2 = System.currentTimeMillis();
            log.info("单次循环耗时：{}", fortime2 - fortime1);
        }
        // 结束时间
        long end = System.currentTimeMillis();
        // 打印耗时
        log.info("查询工单列表耗时：{}ms", end - startTime);

//        List<Event> list = eventMapper.toList(beanParam);
//        for (Event member : list) {
//            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
//            member.setPriority_name("");
//            if (null != member.getPriority_id() && null != slaEventFirstMapper.selectById(member.getPriority_id())) {
//                member.setPriority_name(slaEventFirstMapper.selectById(member.getPriority_id()).getFirst_name());
//            }
//            // 满意度
//            member.setEvent_satisfaction(null == member.getEvent_satisfaction() ? 0 : member.getEvent_satisfaction());
////            EventSatisfaction satisfaction = satisfactionMapper.selectOne(Wrappers.<EventSatisfaction>lambdaQuery()
////                    .eq(EventSatisfaction::getEvent_id, member.getId())
////                    .last(" limit 1")
////            );
////            member.setEvent_satisfaction(null == satisfaction?0:satisfaction.getEvent_satisfaction());
//        }
        PageInfo<Event> page = new PageInfo<>(eventList);

        return page;
    }

    public List<Event> toList(Event event) {
        // 根据用户id得到服务群组
        List<Integer> serviceGroups = new ArrayList<>();
        try {
            serviceGroups = eventBillingDesignateService.getServiceIdByCurrentUser();
            // 如果找不到服务群组，就返回空集合
            if (CollectionUtils.isEmpty(serviceGroups)) {
                serviceGroups.add(-1);
            }
        } catch (Exception e) {
            serviceGroups.add(-1);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        long startTime = System.currentTimeMillis();
        Date newEndTime = null;
        if (null != event.getCreate_time_end()) {
            newEndTime = org.apache.commons.lang.time.DateUtils.addDays(event.getCreate_time_end(), 1);
        }
        List<String> statusList = new ArrayList<>();
        if(StringUtils.isNotBlank(event.getStatus())){
            statusList = Arrays.asList(event.getStatus().split(","));
        }
        List<Event> eventList = eventMapper.selectList(Wrappers.<Event>lambdaQuery()
                .in(null != event.getService_groups_type() && 1 == event.getService_groups_type(), Event::getService_groups_id, serviceGroups)
                .eq(Event::getIs_del, String.valueOf(0))
                // 高级查询条件 关键字
                .like(StringUtils.isNotBlank(event.getTitle()), Event::getTitle, event.getTitle())
                // 高级查询条件 id
                .eq(null != event.getId(), Event::getId, event.getId())
                // 高级查询条件 状态
                .in(!CollectionUtils.isEmpty(statusList), Event::getStatus, statusList)
                // 高级查询条件 用户id
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // 高级查询条件 开单人
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // 高级查询条件 处理人
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // 高级查询条件 来源
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // 高级查询条件 部门
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // 高级查询条件 工单类别
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // 高级查询条件 优先级
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // 高级查询条件 开单起始时间
                .ge(null != event.getCreate_time_start(), Event::getCreate_time, event.getCreate_time_start())
                // 高级查询条件 开单结束时间
                .le(null != newEndTime, Event::getCreate_time, newEndTime)
                // 高级查询条件 服务群组
                .eq(null != event.getService_groups_id(), Event::getService_groups_id, event.getService_groups_id())
                // 高级查询条件 解决工程师
                .eq(null != event.getSolve_user_id(), Event::getSolve_user_id, event.getSolve_user_id())
                // 高级查询条件 响应人
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // 高级查询条件 用户满意度 event_satisfaction
                .eq(null != event.getEvent_satisfaction(), Event::getEvent_satisfaction, event.getEvent_satisfaction())
                // 高级查询条件 方案类别 programme_type
                .eq(null != event.getProgramme_type(), Event::getProgramme_type, event.getProgramme_type())
                // 高级查询条件 符合SLA
                .eq(StringUtils.isNotBlank(event.getMeetResponseSLA()), Event::getMeetResponseSLA, event.getMeetResponseSLA())
                // 高级查询条件 关单开始时间
                .ge(null != event.getCloseStartTime(), Event::getCloseTime, event.getCloseStartTime())
                // 高级查询条件 关单结束时间
                .le(null != event.getCloseEndTime(), Event::getCloseTime, event.getCloseEndTime())
                // 高级查询条件 解决方式
                .eq(null != event.getSolve_type(), Event::getSolve_type, event.getSolve_type())
                // 高级查询条件 费用开始
                .ge(StringUtils.isNotBlank(event.getExpenses_start()), Event::getExpenses, event.getExpenses_start())
                // 高级查询条件 费用结束
                .le(StringUtils.isNotBlank(event.getExpenses_end()), Event::getExpenses, event.getExpenses_end())
        );
        // 得到耗时
        long endTime = System.currentTimeMillis();
        log.info("查询耗时：{}", endTime - startTime);
        // 得到用户map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // 得到工单类别map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // 得到服务组map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        // 得到当前登录人id
//        Integer currentUserId = sysUserService.getUser().getId();
        // 得到Priority_id的集合
        List<Integer> priorityIds = eventList.stream().map(Event::getPriority_id).collect(Collectors.toList());
        Map<Integer, String> slaEventFirstMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(priorityIds)) {
            List<SlaEventFirst> slaEventFirsts = slaEventFirstMapper.selectList(Wrappers.<SlaEventFirst>lambdaQuery().in(SlaEventFirst::getId, priorityIds));
            // 转换为map集合,key为id,value为first_name
            slaEventFirstMap = slaEventFirsts.stream().collect(Collectors.toMap(SlaEventFirst::getId, SlaEventFirst::getFirst_name, (key1, key2) -> key2));
        }
        // 得到查询剩余时间耗时
        long endTime3 = System.currentTimeMillis();
        log.info("查询字典耗时：{}", endTime3 - endTime);
        for (Event member : eventList) {
            // 单次开始时间
            long fortime1 = System.currentTimeMillis();
            // 状态名称
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // 优先级名称
            member.setPriority_name("");
            if (null != member.getPriority_id() && !CollectionUtils.isEmpty(priorityIds)) {
                member.setPriority_name(slaEventFirstMap.get(member.getPriority_id()));
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // 用户名称
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // 开单人名称
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // 处理人名称
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // 工单类别名称
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // 服务组名称
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // 单次结束时间
            long fortime2 = System.currentTimeMillis();
            log.info("单次循环耗时：{}", fortime2 - fortime1);
        }
        // 结束时间
        long end = System.currentTimeMillis();
        // 打印耗时
        log.info("查询工单列表耗时：{}ms", end - startTime);

        return eventList;
    }

    public List<Event> toLists(EventVo beanParam) {
        List<Event> list = eventMapper.toList(beanParam);
        return list;
    }


    /**
     * 全部时间列表查询
     */
    public List<Event> getList(EventVo beanParam) {
        List<Event> list = eventMapper.toList(beanParam);
        for (Event member : list) {
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            member.setPriority_name("");
            if (null != member.getPriority_id() && null != slaEventFirstMapper.selectById(member.getPriority_id())) {
                member.setPriority_name(slaEventFirstMapper.selectById(member.getPriority_id()).getFirst_name());
            }


//            EventSatisfaction satisfaction = satisfactionMapper.selectOne(Wrappers.<EventSatisfaction>lambdaQuery()
//                    .eq(EventSatisfaction::getEvent_id, member.getId()).orderByDesc(EventSatisfaction::getCreate_time)
//                    .last(" limit 1")
//            );
//            member.setEvent_satisfaction(null == satisfaction?0:satisfaction.getEvent_satisfaction());
        }
        return list;
    }

    /**
     * 全部事件 导出操作
     */
    public ResultInfo exportEventExcel(HttpServletResponse response) {
        List<Event> eventList = eventMapper.selectList(null);

        if (CollectionUtils.isEmpty(eventList)) {
            return ResultInfo.error("数据不正确");
        }
        //得到服务群组
        Map<Integer, EventServiceManage> serviceManageMap = getServiceManage();
        //得到用户组
        Map<Integer, SysUser> userMap = getUserNameMap();
        if (CollectionUtils.isEmpty(userMap)) {
            return ResultInfo.error("用户错误,请检查用户");
        }
        //得到部门
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        //得到来源
        Map<Integer, EventSource> sourceMap = getSourceMap();
        //得到工单类型
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        //得到紧急度
        Map<Integer, SlaEventUrgent> urgentMap = getUrgentMap();
        //得到优先级
        Map<Integer, SlaEventFirst> eventFirstMap = getEventFirstMap();
        //请求类型
        Map<Integer, EventRequestType> requestTypeMap = getRequestTypeMap();
        //方案分类
        Map<Integer, SchemeClassification> schemeClassificationMap = getProgrammeTypeMap();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Event member : eventList) {
            Map<String, Object> row = new LinkedHashMap<>();

            row.put("编号", member.getId());
            //匹配服务群组
            String serviceName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(serviceManageMap) && null != member.getService_groups_id()
                    && null != serviceManageMap.get(member.getService_groups_id())) {
                serviceName = serviceManageMap.get(member.getService_groups_id()).getName();
            }
            row.put("服务群组", serviceName);

            // 得到解决工程师
            String solveUser = StringUtils.EMPTY;
            if (null != member.getSolve_user_id() && null != userMap.get(member.getSolve_user_id())) {
                solveUser = userMap.get(member.getSolve_user_id()).getName();
            }
            row.put("解决工程师", solveUser);
            // 得到部门
            String deptName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(deptMap) && null != member.getDepartment()
                    && null != deptMap.get(member.getDepartment())) {
                deptName = deptMap.get(member.getDepartment()).getName();
            }
            row.put("部门名称", deptName);
            row.put("状态", null == member.getStatus() ? StringUtils.EMPTY : EventStatusEnums.getType(member.getStatus()));
            String sourceName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(sourceMap) && null != member.getSource_id()
                    && null != sourceMap.get(member.getSource_id())) {
                sourceName = sourceMap.get(member.getSource_id()).getResource_name();
            }
            row.put("来源名称", sourceName);
            row.put("标题", StringUtils.isBlank(member.getTitle()) ? StringUtils.EMPTY : member.getTitle());
            row.put("描述", StringUtils.isBlank(member.getDescription()) ? StringUtils.EMPTY : member.getDescription());
            String catetory = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(categoryMap) && null != member.getEvent_category_id()
                    && null != categoryMap.get(member.getEvent_category_id())) {
                catetory = categoryMap.get(member.getEvent_category_id()).getName();
            }
            row.put("工单类别", catetory);
            row.put("影响度", member.getEffect_degree());

            String urgencyDegree = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(urgentMap) && null != member.getUrgency_degree_id()
                    && null != urgentMap.get(member.getUrgency_degree_id())) {
                urgencyDegree = urgentMap.get(member.getUrgency_degree_id()).getUrgent_name();
            }
            row.put("紧急度", urgencyDegree);

            String priority = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(eventFirstMap) && null != member.getPriority_id()
                    && null != eventFirstMap.get(member.getPriority_id())) {
                priority = eventFirstMap.get(member.getPriority_id()).getFirst_name();
            }
            row.put("优先级", priority);

            String createUser = StringUtils.EMPTY;
            if (null != member.getCreate_user() && null != userMap.get(member.getCreate_user())) {
                createUser = userMap.get(member.getCreate_user()).getName();
            }
            row.put("开单人", createUser);
            row.put("开单时间", member.getCreate_time());

            String handlerUser = StringUtils.EMPTY;
            if (null != member.getHandler() && null != userMap.get(member.getHandler())) {
                handlerUser = userMap.get(member.getHandler()).getName();
            }
            row.put("处理人", handlerUser);
            row.put("上次更新时间", member.getUpdate_time());

            String assignerUser = StringUtils.EMPTY;
            if (null != member.getAssigner() && null != userMap.get(member.getAssigner())) {
                assignerUser = userMap.get(member.getAssigner()).getName();
            }
            row.put("指派人", assignerUser);
            row.put("指派时间", member.getAssigner_time());
            row.put("响应时间", member.getResponseTime());
            row.put("上次暂停时间", member.getPauseTime());
            row.put("解决时间", member.getEx_solve_time());
            row.put("关单时间", member.getCloseTime());
            row.put("解决方式", member.getSolve_type());
            row.put("解决方案", member.getSolution());

            EventSatisfaction satisfaction = satisfactionMapper.selectOne(Wrappers.<EventSatisfaction>lambdaQuery()
                    .eq(EventSatisfaction::getEvent_id, member.getId())
                    .orderByDesc(EventSatisfaction::getCreate_time));
            row.put("用户满意度", null == satisfaction ? StringUtils.EMPTY : satisfaction.getEvent_satisfaction());
            row.put("用户建议", null == satisfaction ? StringUtils.EMPTY : satisfaction.getFeedback());
            row.put("满意度调查时间", null == satisfaction ? StringUtils.EMPTY : satisfaction.getCreate_time());
            row.put("满意度评价状态", null == satisfaction ? "未评价" : "已评价");
            String username = StringUtils.EMPTY;
            String userPhone = StringUtils.EMPTY;
            String landline = StringUtils.EMPTY;
            SysUser user = null;
            if (null != member.getUser_id() && null != userMap.get(member.getUser_id())) {
                user = userMap.get(member.getUser_id());
            }
            row.put("用户", null == user ? StringUtils.EMPTY : user.getName());
            row.put("用户username", null == user ? StringUtils.EMPTY : user.getUser_name());
            row.put("用户电话", null == user ? StringUtils.EMPTY : user.getTelephone());
            row.put("用户座机", null == user ? StringUtils.EMPTY : user.getLandline());
            row.put("用户邮箱", null == user ? StringUtils.EMPTY : user.getMailbox());

            String userDeptName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(deptMap) && null != user
                    && null != deptMap.get(user.getId())) {
                userDeptName = deptMap.get(user.getId()).getName();
            }
            row.put("用户部门", userDeptName);
            row.put("vip", null == user ? StringUtils.EMPTY : user.getIs_vip());
            row.put("用户职位", null == user ? StringUtils.EMPTY : user.getPosition());
            row.put("预计解决时间", null == member.getEx_solve_time() ? StringUtils.EMPTY : member.getEx_solve_time());
            row.put("费用", null == member.getExpenses() ? StringUtils.EMPTY : member.getExpenses());
            row.put("重开次数", null == member.getNumberOfRestarts() ? StringUtils.EMPTY : member.getNumberOfRestarts());
            row.put("办公位置", null == user ? StringUtils.EMPTY : user.getLocation());

            String requesTypeName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(requestTypeMap) && null != member.getRequest_type()
                    && null != requestTypeMap.get(member.getRequest_type())) {
                requesTypeName = requestTypeMap.get(member.getRequest_type()).getRequest_type_name();
            }
            row.put("请求类型", requesTypeName);
            row.put("事件单号", member.getId());

            String programme_type_name = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(schemeClassificationMap) && null != member.getProgramme_type()
                    && null != schemeClassificationMap.get(member.getProgramme_type())) {
                programme_type_name = schemeClassificationMap.get(member.getProgramme_type()).getName();
            }
            row.put("方案分类", programme_type_name);

            row.put("暂停总时间", StringUtils.EMPTY);
            row.put("协查人", StringUtils.EMPTY);
            row.put("关联的资产", StringUtils.EMPTY);
            row.put("关联的工单", StringUtils.EMPTY);
            row.put("符合响应SLA", StringUtils.EMPTY);
            row.put("响应超时时间", StringUtils.EMPTY);
            row.put("符合解决SLA", StringUtils.EMPTY);
            row.put("解决超时时间数", StringUtils.EMPTY);
            row.put("响应总时间", StringUtils.EMPTY);
            row.put("响应时限", StringUtils.EMPTY);
            row.put("解决时限", StringUtils.EMPTY);
            row.put("响应SLA", StringUtils.EMPTY);
            row.put("解决SLA", StringUtils.EMPTY);
            row.put("解决总时间", StringUtils.EMPTY);
            row.put("附件", StringUtils.EMPTY);
            row.put("工单大类", StringUtils.EMPTY);
            row.put("公司", StringUtils.EMPTY);
            row.put("手动暂停总时间", StringUtils.EMPTY);
            row.put("影响可用性", StringUtils.EMPTY);
            row.put("保障类别", StringUtils.EMPTY);
            row.put("响应人", StringUtils.EMPTY);
            list.add(row);
        }


        ExcelWriter writer = ExcelUtil.getWriter();
//        ExcelWriter writer = ExcelUtil.getWriter("d:/test/writeBeanTest.xlsx");

//        writer.addHeaderAlias("id", "编号");
//        writer.addHeaderAlias("service_groups_name", "服务群组");
//        writer.addHeaderAlias("solve_user.user_name", "解决工程师");

        writer.setOnlyAlias(true);
        writer.write(list, true);
//        writer.close();

        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }

    /**
     * 全部事件 导入模板
     */
    public ResultInfo exportTemplatesEventExcel(HttpServletResponse response) {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();

        row.put("用户", StringUtils.EMPTY);
        row.put("标题", StringUtils.EMPTY);
        row.put("描述", StringUtils.EMPTY);
        row.put("来源", StringUtils.EMPTY);
        row.put("工单类型", StringUtils.EMPTY);
        row.put("请求类型", StringUtils.EMPTY);
        row.put("紧急度", StringUtils.EMPTY);
        row.put("影响度", StringUtils.EMPTY);
        row.put("影响可用性", StringUtils.EMPTY);

        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.setOnlyAlias(true);

        //设置下拉数据 从第几行开始
        int firstRow = 1;
        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();

        //用户
        String [] sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getIs_del, GlobalData.ISDEL_NO)
                .eq(SysUser::getStatus, GlobalData.IS_ENABLE_YES)
        ).stream().map(SysUser::getName).toArray(String[]::new);
        //来源
        String [] eventSources = eventSourceMapper.selectList(Wrappers.<EventSource>lambdaQuery()
                .eq(EventSource::getIs_del, GlobalData.ISDEL_NO)
                .eq(EventSource::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(EventSource::getResource_name).toArray(String[]::new);
        //工单类型
        String [] eventCategorys = eventCategoryMapper.selectList(Wrappers.<EventCategory>lambdaQuery()
                .eq(EventCategory::getIs_del, GlobalData.ISDEL_NO)
                .eq(EventCategory::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(EventCategory::getName).toArray(String[]::new);
        //请求类型
        String [] eventRequestTypes = eventRequestTypeMapper.selectList(Wrappers.<EventRequestType>lambdaQuery()
                .eq(EventRequestType::getIs_del, GlobalData.ISDEL_NO)
                .eq(EventRequestType::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(EventRequestType::getRequest_type_name).toArray(String[]::new);
        //紧急度
        String [] slaEventUrgents = slaEventUrgentMapper.selectList(Wrappers.<SlaEventUrgent>lambdaQuery()
                .eq(SlaEventUrgent::getIs_del, GlobalData.ISDEL_NO)
                .eq(SlaEventUrgent::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(SlaEventUrgent::getUrgent_name).toArray(String[]::new);
        //影响度
        String [] slaEventImpacts = slaEventImpactMapper.selectList(Wrappers.<SlaEventImpact>lambdaQuery()
                .eq(SlaEventImpact::getIs_del, GlobalData.ISDEL_NO)
                .eq(SlaEventImpact::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(SlaEventImpact::getImpact_name).toArray(String[]::new);
        //影响可用性
        String[] is_effect = {"是", "否"};
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, sysUsers, firstRow, 0));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, eventSources, firstRow, 3));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, eventCategorys, firstRow, 4));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, eventRequestTypes, firstRow, 5));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, slaEventUrgents, firstRow, 6));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, slaEventImpacts, firstRow, 7));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, is_effect, firstRow, 8));

        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }

    /**
     * 全部事件 导入execl
     */
    public ResultInfo importExcel(MultipartFile file) {
        try {
            //fileName 文件名
            String fileName = file.getOriginalFilename();
            boolean xlsx = fileName.endsWith(".xlsx");
            if (!xlsx) {
                return ResultInfo.error("请上传以.xlsx结尾的文件");
            }
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
            List<List<Object>> data = reader.read();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = data.size();

            List<Event> success = new ArrayList<>();
            List<Event> error = new ArrayList<>();
            List<Event> all = new ArrayList<>();


            if (size > 1) {
                for (int i = 1; i < size; i++) {
                    Event event = new Event();
                    List<Object> line = data.get(i);
                    if (com.jsdc.itss.utils.StringUtils.isEmpty(line.get(0).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(1).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(2).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(3).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(4).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(5).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(6).toString()) || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(7).toString())
                            || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(8).toString())
                    ) {

                        event.setUser_name(line.get(0).toString());
                        event.setTitle(line.get(1).toString());
                        event.setDescription(line.get(2).toString());
                        event.setSource_name(line.get(3).toString());
                        event.setEvent_category_name(line.get(4).toString());
                        event.setRequest_type_name(line.get(5).toString());
                        event.setUrgency_degree_name(line.get(6).toString());
                        event.setEffect_degree_name(line.get(7).toString());
                        event.setIs_effect_name(line.get(8).toString());

                        error.add(event);
                        all.add(event);
                    } else {
                        //用户
                        Map<String,Integer> sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                                .eq(SysUser::getIs_del, GlobalData.ISDEL_NO)
                                .eq(SysUser::getStatus, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(SysUser::getName,SysUser::getId));
                        //来源
                        Map<String,Integer> eventSources = eventSourceMapper.selectList(Wrappers.<EventSource>lambdaQuery()
                                .eq(EventSource::getIs_del, GlobalData.ISDEL_NO)
                                .eq(EventSource::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(EventSource::getResource_name,EventSource::getId));
                        //工单类型
                        Map<String,Integer> eventCategorys = eventCategoryMapper.selectList(Wrappers.<EventCategory>lambdaQuery()
                                .eq(EventCategory::getIs_del, GlobalData.ISDEL_NO)
                                .eq(EventCategory::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(EventCategory::getName,EventCategory::getId));
                        //请求类型
                        Map<String,Integer> eventRequestTypes = eventRequestTypeMapper.selectList(Wrappers.<EventRequestType>lambdaQuery()
                                .eq(EventRequestType::getIs_del, GlobalData.ISDEL_NO)
                                .eq(EventRequestType::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(EventRequestType::getRequest_type_name,EventRequestType::getId));
                        //紧急度
                        Map<String,Integer> slaEventUrgents = slaEventUrgentMapper.selectList(Wrappers.<SlaEventUrgent>lambdaQuery()
                                .eq(SlaEventUrgent::getIs_del, GlobalData.ISDEL_NO)
                                .eq(SlaEventUrgent::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(SlaEventUrgent::getUrgent_name,SlaEventUrgent::getId));
                        //影响度
                        Map<String,Integer> slaEventImpacts = slaEventImpactMapper.selectList(Wrappers.<SlaEventImpact>lambdaQuery()
                                .eq(SlaEventImpact::getIs_del, GlobalData.ISDEL_NO)
                                .eq(SlaEventImpact::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(SlaEventImpact::getImpact_name,SlaEventImpact::getId));
                        //影响可用性
                        Map<String, String> isEffects = ImmutableMap.of("是", "1", "否", "0");

                        //用户
                        event.setUser_id_name(line.get(0).toString());
                        event.setUser_id(sysUsers.get(line.get(0).toString()));
                        //标题
                        event.setTitle(line.get(1).toString());
                        //描述
                        event.setDescription(line.get(2).toString());
                        //来源
                        event.setSource_name(line.get(3).toString());
                        event.setSource_id(eventSources.get(line.get(3).toString()));
                        //工单类型
                        event.setEvent_category_name(line.get(4).toString());
                        event.setEvent_category_id(eventCategorys.get(line.get(4).toString()));
                        //请求类型
                        event.setRequest_type_name(line.get(5).toString());
                        event.setRequest_type(eventRequestTypes.get(line.get(5).toString()));
                        //紧急度
                        event.setUrgency_degree_name(line.get(6).toString());
                        event.setUrgency_degree_id(slaEventUrgents.get(line.get(6).toString()));
                        //影响度
                        event.setEffect_degree_name(line.get(7).toString());
                        event.setEffect_degree(slaEventImpacts.get(line.get(7).toString()));
                        //影响可用性
                        event.setIs_effect_name(line.get(8).toString());
                        event.setIs_effect(isEffects.get(line.get(8).toString()));

                        // 删除状态
                        event.setIs_del(String.valueOf(0));
                        // 创建时间
                        event.setCreate_time(new Date());
                        // 创建人
                        event.setCreate_user(sysUserService.getUser().getId());
                        // 问题单发起后，状态为“待鉴定”，可在【问题处理】中查看到，指定的鉴定人可对该问题进行鉴定。
                        event.setStatus(EventStatusEnums.PENDING_RESPONSE.getType());

                        success.add(event);
                        all.add(event);

                        eventMapper.insert(event);
                    }
                }
            }
            Event e = new Event();
            e.setSuccess(success);
            e.setError(error);
            e.setAll(all);
            return ResultInfo.success(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("导入失败！！！");
    }

    /**
     * 全部事件,重开操作
     * <p>
     * 可将已关单的和已撤销的事件单重开。
     * 重开逻辑如下：
     * **若事件已经被响应过，则状态变为“处理中”，如果未响应过，则状态变为“待响应”；
     * 若事件单关单时符合处理sla，则重开后sla继续计时；
     * 若事件单关单时已超时，则重开后仍显示为超时。
     */
    public ResultInfo reopenEvent(EventVo eventVo) {
        if (null == eventVo || null == eventVo.getId()) {
            return ResultInfo.error("非法入参!");
        }
        Event event = selectById(eventVo.getId());
        if (StringUtils.isBlank(event.getStatus())) {
            return ResultInfo.error("原状态异常");
        }
        if (EventStatusEnums.CLOSED.getType().equals(event.getStatus())) {
            event.setStatus(EventStatusEnums.PROCESSING.getType());
        } else if (EventStatusEnums.REVOKED.getType().equals(event.getStatus())) {
            event.setStatus(EventStatusEnums.PENDING_RESPONSE.getType());
        } else if (EventStatusEnums.COMMENT.getType().equals(event.getStatus())) {
            event.setStatus(EventStatusEnums.PENDING_RESPONSE.getType());
        } else {
            return ResultInfo.error("不符合重开规则");
        }

        event.setNumberOfRestarts(null == event.getNumberOfRestarts() ? 0 : event.getNumberOfRestarts() + 1);
        event.setUpdate_time(new Date());
        event.setUpdate_user(null == eventVo.getCurrent_user() ? sysUserService.getUser().getId() : eventVo.getCurrent_user());
        updateById(event);
        return ResultInfo.success("重开成功", new LogVo(event.getId(), "重开", eventVo.getRemark()));
    }

    /**
     * 全部事件的修改操作
     */
    public ResultInfo editEvent(Event event, List<MultipartFile> files, List<MultipartFile> filesHandle) {
        if (null == event || null == event.getId()) {
            return ResultInfo.error("入参异常!");
        }
        updateEventInfo(event, files, filesHandle);

        return ResultInfo.success("修改成功", new LogVo(event.getId(), "修改", StringUtils.EMPTY));
    }

    /**
     * 修改操作
     *
     * @param event
     * @throws Exception
     */
    private void updateEventInfo(Event event, List<MultipartFile> files, List<MultipartFile> filesHandle) {
        Integer userId = null;
        if(null == event.getUserId()){
            userId = sysUserService.getUser().getId();
        }else{
            userId = event.getUserId();
        }
        event.setUpdate_time(new Date());

        event.setUpdate_user(userId);
        updateById(event);

        //附件上传 保存
        //属性信息 是否存在文件删除
        String uploadedFiles = event.getUploadedFiles();
        List<FileManage> fileManages = eventFileMapper.getFileManageByType(event.getId(), 1);
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<EventFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(EventFile::getEvent_id, event.getId())
                    .in(EventFile::getFile_id, original);
            eventFileMapper.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(id);
                eventFile.setType("1");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(userId);
                eventFile.setIs_del("0");
                eventFileMapper.insert(eventFile);
            }
        }
        //处理信息 是否存在文件删除
        String uploadedHandleFiles = event.getUploadedHandleFiles();
        List<FileManage> fileManagesHandle = eventFileMapper.getFileManageByType(event.getId(), 2);
        List<String> originalHandle = fileManagesHandle.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedHandleFiles)) {
            List<String> ids = Arrays.asList(uploadedHandleFiles.split(","));
            originalHandle.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(originalHandle)) {
            LambdaUpdateWrapper<EventFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(EventFile::getEvent_id, event.getId())
                    .in(EventFile::getFile_id, originalHandle);
            eventFileMapper.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(filesHandle)) {
            for (MultipartFile file : filesHandle) {
                int id = fileManageService.fileUpload(file);
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(id);
                eventFile.setType("2");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(userId);
                eventFile.setIs_del("0");
                eventFileMapper.insert(eventFile);
            }
        }

        // 关联的协查人,删除后,重新添加
        eventAssistMapper.update(null, Wrappers.<EventAssist>lambdaUpdate()
                .set(EventAssist::getIs_del, String.valueOf(1)).eq(EventAssist::getEvent_id, event.getId()));
        if (!CollectionUtils.isEmpty(event.getEventAssistUsers())) {
            for (SysUserPageVo member : event.getEventAssistUsers()) {
                EventAssist eventAssist = new EventAssist();
                eventAssist.setEvent_id(event.getId());
                eventAssist.setUser_id(member.getId());
                eventAssist.setCreate_time(new Date());
                eventAssist.setCreate_user(userId);
                eventAssist.setIs_del(String.valueOf(0));
                eventAssistMapper.insert(eventAssist);
            }
        }

        // 关联的配置项,删除后,重新添加
        eventAssetsMapper.update(null, Wrappers.<EventAssets>lambdaUpdate()
                .set(EventAssets::getIs_del, String.valueOf(1)).eq(EventAssets::getEvent_id, event.getId()));
        if (!CollectionUtils.isEmpty(event.getAssetsManages())) {
            for (AssetsManage member : event.getAssetsManages()) {
                EventAssets eventAssets = new EventAssets();
                eventAssets.setAssets_id(member.getId());
                eventAssets.setEvent_id(event.getId());
                eventAssets.setCreate_time(new Date());
                eventAssets.setCreate_user(userId);
                eventAssets.setIs_del(String.valueOf(0));
                eventAssetsMapper.insert(eventAssets);
            }
        }

        // 关联的工单,删除后,重新添加
        eventRelationMapper.update(null, Wrappers.<EventRelation>lambdaUpdate()
                .set(EventRelation::getIs_del, String.valueOf(1)).eq(EventRelation::getParent_event_id, event.getId()));
        if (!CollectionUtils.isEmpty(event.getEventList())) {
            for (Event member : event.getEventList()) {
                EventRelation eventRelation = new EventRelation();
                eventRelation.setEvent_id(member.getId());
                eventRelation.setParent_event_id(event.getId());
                eventRelation.setCreate_time(new Date());
                eventRelation.setCreate_user(userId);
                eventRelation.setIs_del(String.valueOf(0));
                eventRelationMapper.insert(eventRelation);
            }
        }

        // 关联的问题,删除后,重新添加
        questionRelationMapper.update(null, Wrappers.<EventQuestionRelation>lambdaUpdate()
                .set(EventQuestionRelation::getIs_del, String.valueOf(1)).eq(EventQuestionRelation::getEvent_id, event.getId()));
        if (!CollectionUtils.isEmpty(event.getQuestionList())) {
            for (QuestionVo member : event.getQuestionList()) {
                EventQuestionRelation eventQuestionRelation = new EventQuestionRelation();
                eventQuestionRelation.setQuestion_id(member.getId());
                eventQuestionRelation.setEvent_id(event.getId());
                eventQuestionRelation.setCreate_time(new Date());
                eventQuestionRelation.setCreate_user(userId);
                eventQuestionRelation.setIs_del(String.valueOf(0));
                questionRelationMapper.insert(eventQuestionRelation);
            }
        }

        // 关联的变更,删除后,重新添加
        changeRelationMapper.update(null, Wrappers.<EventChangeRelation>lambdaUpdate()
                .set(EventChangeRelation::getIs_del, String.valueOf(1)).eq(EventChangeRelation::getEvent_id, event.getId()));
        if (!CollectionUtils.isEmpty(event.getChangeList())) {
            for (ChangeVo member : event.getChangeList()) {
                EventChangeRelation eventChangeRelation = new EventChangeRelation();
                eventChangeRelation.setChange_id(member.getId());
                eventChangeRelation.setEvent_id(event.getId());
                eventChangeRelation.setCreate_time(new Date());
                eventChangeRelation.setCreate_user(userId);
                eventChangeRelation.setIs_del(String.valueOf(0));
                changeRelationMapper.insert(eventChangeRelation);
            }
        }
    }

    /**
     * 全部事件 删除操作
     * <p>
     * 未被关联的事件单可删除。同理修改，由于事件单作为工程师的工作记录，不建议随意删除，除非出现测试数据或重复数据。
     * 未被关联：如果工单关联了“协查人”“工单”“配置项”或过程中生成了问题，生成了变更或生成了知识，都属于被关联的状态。
     */
    public ResultInfo deleteEvent(EventVo eventVo) {
        if (null == eventVo || null == eventVo.getId()) {
            return ResultInfo.error("入参数据错误!");
        }
        Event event = selectById(eventVo.getId());
        // 根据权限,判断用户是否可以删除
        SysUser user = new SysUser();
        if (null != eventVo.getUserId()) {
            user = sysUserMapper.selectById(eventVo.getUserId());
        }else{
            user = sysUserService.getUser();
        }
        // sysUserService.getUser();

        //查询关联操作
        List<EventAssets> eventAssetsList = eventAssetsMapper.selectList(Wrappers.<EventAssets>lambdaQuery()
                .eq(EventAssets::getEvent_id, event.getId()).eq(EventAssets::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssetsList)) {
            return ResultInfo.error("已绑定关联关系,不能随意删除!");
        }
        List<EventRelation> eventRelationList = eventRelationMapper.selectList(Wrappers.<EventRelation>lambdaQuery()
                .eq(EventRelation::getParent_event_id, event.getId()).eq(EventRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventRelationList)) {
            return ResultInfo.error("已绑定关联关系,不能随意删除!");
        }
        List<EventAssist> eventAssistList = eventAssistMapper.selectList(Wrappers.<EventAssist>lambdaQuery()
                .eq(EventAssist::getEvent_id, event.getId()).eq(EventAssist::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssistList)) {
            return ResultInfo.error("已绑定关联关系,不能随意删除!");
        }
        List<EventQuestionRelation> questionRelations = questionRelationMapper.selectList(Wrappers.<EventQuestionRelation>lambdaQuery()
                .eq(EventQuestionRelation::getEvent_id, event.getId()).eq(EventQuestionRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(questionRelations)) {
            return ResultInfo.error("已绑定关联关系,不能随意删除!");
        }
        List<EventChangeRelation> changeRelationList = changeRelationMapper.selectList(Wrappers.<EventChangeRelation>lambdaQuery()
                .eq(EventChangeRelation::getEvent_id, event.getId()).eq(EventChangeRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(changeRelationList)) {
            return ResultInfo.error("已绑定关联关系,不能随意删除!");
        }

        event.setUpdate_time(new Date());
        event.setUpdate_user(user.getId());
        event.setIs_del(String.valueOf(1));

        updateById(event);
        if(null != eventVo.getUserId()){
            return ResultInfo.success("删除成功!", new LogVo(eventVo.getUserId(), event.getId(), "删除事件单", ""));
        }

        return ResultInfo.success();
    }

    /**
     * 撤销操作
     */
    public ResultInfo revokeEvent(EventVo eventVo) {
        if (null == eventVo || null == eventVo.getId()) {
            return ResultInfo.error("非法入参");
        }

        Event event = selectById(eventVo.getId());
        if (EventStatusEnums.CLOSED.getType().equals(event.getStatus())) {
            return ResultInfo.error("已关单无法撤销");
        }

        event.setUpdate_user(sysUserService.getUser().getId());
        event.setUpdate_time(new Date());
        event.setStatus(EventStatusEnums.REVOKED.getType());
        return ResultInfo.success(updateById(event));
    }

    /**
     * 全部事件 批量关单操作
     */
    public ResultInfo closingBatch(EventVo eventVo) {
        if (CollectionUtils.isEmpty(eventVo.getIds())) {
            return ResultInfo.error("非法入参");
        }
        int result = update(null, Wrappers.<Event>lambdaUpdate()
                .set(Event::getStatus, EventStatusEnums.CLOSED.getType())
                .set(Event::getUpdate_time, new Date())
                .set(Event::getUser_name, null == eventVo.getCurrent_user() ? sysUserService.getUser().getId() : eventVo.getCurrent_user())
                .set(Event::getCloseTime, new Date())
                .set(Event::getEx_solve_time, new Date())

                .in(Event::getId, eventVo.getIds())
        );
        if (result > 0) {
            return ResultInfo.success("关单成功");
        }
        return ResultInfo.error("关单失败");
    }

    /**
     * 根据id查询事件信息,包含关联信息
     */
    public ResultInfo getById(Integer id) {
        if (null == id) {
            return ResultInfo.error("非法入参");
        }
        Map<Integer, SysUser> users = getUserNameMap();
        if (CollectionUtils.isEmpty(users)) {
            return ResultInfo.error("用户错误");
        }
        Event event = selectById(id);
        //得到用户姓名
        event.setUser_id_name(null == users.get(event.getUser_id()) ? StringUtils.EMPTY : users.get(event.getUser_id()).getName());
        event.setUser_id_loginName(null == users.get(event.getUser_id()) ? StringUtils.EMPTY : users.get(event.getUser_id()).getUser_name());
        event.setCreate_user_name(null == users.get(event.getCreate_user()) ? StringUtils.EMPTY : users.get(event.getCreate_user()).getName());
        event.setHandler_name(null == users.get(event.getHandler()) ? StringUtils.EMPTY : users.get(event.getHandler()).getName());
        event.setAssigner_name(null == users.get(event.getAssigner()) ? StringUtils.EMPTY : users.get(event.getAssigner()).getName());
        event.setSolve_user_name(null == users.get(event.getSolve_user_id()) ? StringUtils.EMPTY : users.get(event.getSolve_user_id()).getName());
        event.setClose_user_name(null == users.get(event.getClose_users()) ? StringUtils.EMPTY : users.get(event.getClose_users()).getName());
        event.setResponse_user_name(null == users.get(event.getResponse_user()) ? StringUtils.EMPTY : users.get(event.getResponse_user()).getName());
        //方案分类
        Map<Integer, SchemeClassification> schemeClassificationMap = getProgrammeTypeMap();
        if (Base.notEmpty(event.getProgramme_type())) {
            event.setProgramme_type_name(schemeClassificationMap.get(event.getProgramme_type()).getName());
        }
        // 解决方式
        if (null != event.getSolve_type()) {
            EventSolutionType solutionType = eventSolutionTypeMapper.selectById(event.getSolve_type());
            if (null != solutionType) {
                event.setSolve_type_name(solutionType.getSolution_name());
            }
        }

        //得到部门
        event.setDeptName(StringUtils.EMPTY);
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        if (!CollectionUtils.isEmpty(deptMap)) {
            event.setDeptName(null == deptMap.get(event.getDepartment()) ? StringUtils.EMPTY : deptMap.get(event.getDepartment()).getName());
        }
        //得到服务群组
        Map<Integer, EventServiceManage> serviceMap = getServiceManage();
        if (!CollectionUtils.isEmpty(serviceMap)) {
            event.setService_name(null == serviceMap.get(event.getService_groups_id()) ? StringUtils.EMPTY : serviceMap.get(event.getService_groups_id()).getName());
        }
        //得到来源名称
        Map<Integer, EventSource> sourceMap = getSourceMap();
        if (!CollectionUtils.isEmpty(sourceMap)) {
            event.setSource_name(null == sourceMap.get(event.getSource_id()) ? StringUtils.EMPTY : sourceMap.get(event.getSource_id()).getResource_name());
        }
        //单据状态
        event.setStatusName(EventStatusEnums.getValue(event.getStatus()));
        //请求类型
        Map<Integer, EventRequestType> requestMap = getRequestTypeMap();
        if (!CollectionUtils.isEmpty(requestMap)) {
            event.setRequest_type_name(null == requestMap.get(event.getRequest_type()) ? StringUtils.EMPTY : requestMap.get(event.getRequest_type()).getRequest_type_name());
        }
        //得到工单类别
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        if (!CollectionUtils.isEmpty(categoryMap)) {
            event.setEvent_category_name(null == categoryMap.get(event.getEvent_category_id()) ? StringUtils.EMPTY : categoryMap.get(event.getEvent_category_id()).getName());
        }
        //得到事件优先级
        Map<Integer, SlaEventFirst> firstMap = getEventFirstMap();
        if (!CollectionUtils.isEmpty(firstMap)) {
            event.setPriority_name(null == firstMap.get(event.getPriority_id()) ? StringUtils.EMPTY : firstMap.get(event.getPriority_id()).getFirst_name());
        }
        //得到紧急度
        Map<Integer, SlaEventUrgent> urgentMap = getUrgentMap();
        if (!CollectionUtils.isEmpty(urgentMap)) {
            event.setUrgency_degree_name(null == urgentMap.get(event.getUrgency_degree_id()) ? StringUtils.EMPTY : urgentMap.get(event.getUrgency_degree_id()).getUrgent_name());
        }
        //得到影响度
        Map<Integer, SlaEventImpact> eventImpactMap = getEventImpactMap();
        if (!CollectionUtils.isEmpty(eventImpactMap)) {
            event.setEffect_degree_name(null == eventImpactMap.get(event.getEffect_degree()) ? StringUtils.EMPTY : eventImpactMap.get(event.getEffect_degree()).getImpact_name());
        }
        //得到解决方式
        EventSolutionType solutionType = eventSolutionTypeService.selectById(event.getSolve_type());
        if (null != solutionType) {
            event.setSolve_type_name(solutionType.getSolution_name());
        }

        //查询协查人关联
        List<EventAssist> eventAssists = eventAssistMapper.selectList(Wrappers.<EventAssist>lambdaQuery()
                .eq(EventAssist::getEvent_id, event.getId())
                .eq(EventAssist::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssists)) {
            List<Integer> ids = eventAssists.stream().map(EventAssist::getUser_id).collect(Collectors.toList());
            SysUser sysUser = new SysUser();
            sysUser.setIds(ids);
            event.setEventAssistUsers(sysUserService.getList(sysUser));
        }
        //查询配置项关联
        List<EventAssets> eventAssets = eventAssetsMapper.selectList(Wrappers.<EventAssets>lambdaQuery()
                .eq(EventAssets::getEvent_id, event.getId())
                .eq(EventAssets::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssets)) {
            List<Integer> ids = eventAssets.stream().map(EventAssets::getAssets_id).collect(Collectors.toList());
            AssetsManage assetsManage2 = new AssetsManage();
            assetsManage2.setIds(ids);
            event.setAssetsManages(assetsManageService.getList(assetsManage2));
//            event.setAssetsManages(CollectionUtils.isEmpty(ids) ? Collections.EMPTY_LIST : assetsManageMapper.selectList(
//                    Wrappers.<AssetsManage>lambdaQuery().eq(AssetsManage::getIs_del, String.valueOf(0)).in(AssetsManage::getId, ids)
//            ));
        }
        //查询工单关联
        List<EventRelation> eventRelations = eventRelationMapper.selectList(Wrappers.<EventRelation>lambdaQuery()
                .eq(EventRelation::getParent_event_id, event.getId())
                .eq(EventRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventRelations)) {
            List<Integer> ids = eventRelations.stream().map(EventRelation::getEvent_id).collect(Collectors.toList());
            EventVo eventVo = new EventVo();
            eventVo.setIds(ids);
            event.setEventList(getList(eventVo));
//            event.setEventList(CollectionUtils.isEmpty(ids) ? Collections.EMPTY_LIST : eventMapper.selectList(
//                    Wrappers.<Event>lambdaQuery().eq(Event::getIs_del, String.valueOf(0)).in(Event::getId, ids)
//            ));
        }
        //查询问题关联
        List<EventQuestionRelation> questions = questionRelationMapper.selectList(Wrappers.<EventQuestionRelation>lambdaQuery()
                .eq(EventQuestionRelation::getEvent_id, event.getId())
                .eq(EventQuestionRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(questions)) {
            List<Integer> ids = questions.stream().map(EventQuestionRelation::getQuestion_id).collect(Collectors.toList());
            QuestionVo questionVo = new QuestionVo();
            questionVo.setIds(ids);
            event.setQuestionList(questionManageService.getList(questionVo));
//            event.setQuestionList(CollectionUtils.isEmpty(ids) ? Collections.EMPTY_LIST : questionMapper.selectList(
//                    Wrappers.<Question>lambdaQuery().eq(Question::getIs_del, String.valueOf(0)).in(Question::getId, ids)
//            ));
        }
        //查询变更关联
        List<EventChangeRelation> changeReasons = changeRelationMapper.selectList(Wrappers.<EventChangeRelation>lambdaQuery()
                .eq(EventChangeRelation::getEvent_id, event.getId())
                .eq(EventChangeRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(changeReasons)) {
            List<Integer> ids = changeReasons.stream().map(EventChangeRelation::getChange_id).collect(Collectors.toList());
            ChangeVo changeVo = new ChangeVo();
            changeVo.setIds(ids);
            event.setChangeList(changeInfoService.getList(changeVo));

//            List<ChangeInfo> infos = changeInfoService.selectList(Wrappers.<ChangeInfo>lambdaQuery().eq(ChangeInfo::getIs_del, String.valueOf(0)).in(ChangeInfo::getId, ids));
//            List<ChangeVo> list = new ArrayList<>();
//            try {
//                for (ChangeInfo member : infos) {
//                    ChangeVo changeVo = new ChangeVo();
//                    changeVo.setId(member.getId());
//                    changeVo.setChange_no(member.getChange_no());
//                    changeVo.setTitle(member.getTitle());
//                    changeVo.setStart_time(member.getStart_time());
//                    changeVo.setEnd_time(member.getEnd_time());
//                    changeVo.setChange_type(member.getChange_type());
//                    changeVo.setCreate_user(member.getCreate_user());
//                    changeVo.setStatus(member.getStatus());
//                    list.add(changeVo);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            event.setChangeList(list);
        }
        //查询SLA事件、满意度
        EventTimes times = eventTimesService.selectById(event.getId());
        if (null == times) {
            times = new EventTimes();
        }
        event.setEventTimes(times);
        // 相关日志信息
        List<SysLog> sysLogs = sysLogService.selectList(Wrappers.<SysLog>lambdaQuery().
                eq(SysLog::getBill_id, event.getId()).eq(SysLog::getModule, LogEnums.LOG_GZT.getValue())
                .orderByDesc(SysLog::getOperate_time)
        );
        if (!CollectionUtils.isEmpty(sysLogs)) {
            event.setSysLogs(sysLogs);
        }

        //  附件
        List<EventFile> files = eventFileMapper.selectList(Wrappers.<EventFile>lambdaQuery()
                .eq(EventFile::getEvent_id, event.getId()).eq(EventFile::getType, 1).eq(EventFile::getIs_del, "0"));
        if (Base.notEmpty(files) && files.size() > 0) {
            //工单信息附件
            List<FileManage> fileManages1 = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery()
                    .in(FileManage::getId, files.stream().map(EventFile::getFile_id).collect(Collectors.toList()))
            );
            fileManages1.forEach(x -> {
                String path = uploadPath + File.separator + x.getFile_name();
                File file = new File(path);
                if (file.exists()) {
                    String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                    x.setBase64(base64);
                } else {
                    x.setBase64("");
                }
                x.setFile_path(appUploadPath + x.getFile_name());
                x.setUrl(appUploadPath + x.getFile_name());
                x.setName(x.getFile_name());
            });
            event.setFiles(fileManages1);
        }

        //  附件
        List<EventFile> files2 = eventFileMapper.selectList(Wrappers.<EventFile>lambdaQuery()
                .eq(EventFile::getEvent_id, event.getId()).eq(EventFile::getType, 2).eq(EventFile::getIs_del, "0"));
        if (Base.notEmpty(files2) && files2.size() > 0) {
            //处理信息附件
            List<FileManage> fileManages2 = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery()
                    .in(FileManage::getId, files2.stream().map(EventFile::getFile_id).collect(Collectors.toList()))
            );
            fileManages2.forEach(x -> {
                String path = uploadPath + File.separator + x.getFile_name();
                File file = new File(path);
                if (file.exists()) {
                    String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                    x.setBase64(base64);
                } else {
                    x.setBase64("");
                }
                x.setFile_path(appUploadPath + x.getFile_name());
                x.setUrl(appUploadPath + x.getFile_name());
                x.setName(x.getFile_name());
            });
            event.setFiles1(fileManages2);
        }

        return ResultInfo.success(event);
    }

    /**
     * 开单列表
     * Author wzn
     * Date 2022/3/18 9:16
     */
    public ResultInfo getPage(Event event, int pageIndex, int pageSize) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        if (event.getHandler() != null) {
            queryWrapper.eq("handler", event.getHandler());
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<Event> list = eventMapper.selectList(queryWrapper);
        PageInfo<Event> pageInfo = new PageInfo<Event>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 自助服务 - 修改接口
     */
    public ResultInfo editEventById(Event event) {
        if (null == event || null == event.getId()) {
            return ResultInfo.error("非法入参");
        }

        event.setUpdate_time(new Date());
        event.setUpdate_user(sysUserService.getUser().getId());
        // 关联的配置项,删除后,重新添加
        eventAssetsMapper.update(null, Wrappers.<EventAssets>lambdaUpdate()
                .set(EventAssets::getIs_del, String.valueOf(1)).eq(EventAssets::getEvent_id, event.getId()));
        if (!CollectionUtils.isEmpty(event.getAssetsManages())) {
            for (AssetsManage member : event.getAssetsManages()) {
                EventAssets eventAssets = new EventAssets();
                eventAssets.setAssets_id(member.getId());
                eventAssets.setEvent_id(event.getId());
                eventAssets.setCreate_time(new Date());
                eventAssets.setCreate_user(sysUserService.getUser().getId());
                eventAssets.setIs_del(String.valueOf(0));
                eventAssetsMapper.insert(eventAssets);
            }
        }
        return ResultInfo.success(updateById(event));
    }

    @Autowired
    private AssetsInventoryMapper assetsInventoryMapper;

    /**
     * 自助服务 - 资产盘点
     */
    public ResultInfo propertyAssessmentList() {
        SysUser user = sysUserService.getUser();

        return null;
    }

    /**
     * 用户评价
     */
    public ResultInfo evaluateUser(Event event) {
//        if (null == eventVo || null == eventVo.getId() || null == eventVo.getEventSatisfaction()) {
//            return ResultInfo.error("非法入参");
//        }
//        EventSatisfaction bean = eventVo.getEventSatisfaction();
//        bean.setEvent_id(eventVo.getId());
//        bean.setCreate_user(sysUserService.getUser().getId());
//        bean.setCreate_time(new Date());
//        satisfactionMapper.insert(bean);
        Integer count = eventMapper.updateById(event);
        SysUser sysUser = sysUserService.getUser();
        if (count > 0) {
            return ResultInfo.success("评价成功", new LogVo(event.getId(), "用户评价", sysUser.getName() + "(" + sysUser.getUser_name() + ") 评价"));
        } else {
            return ResultInfo.error("操作失败");
        }
    }

    /**
     * 影响度字典
     */
    private Map<Integer, SlaEventImpact> getEventImpactMap() {
        List<SlaEventImpact> list = slaEventImpactMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SlaEventImpact::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 方案分类字典
     */
    private Map<Integer, SchemeClassification> getProgrammeTypeMap() {
        List<SchemeClassification> list = schemeClassificationMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SchemeClassification::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 请求类型字典
     */
    private Map<Integer, EventRequestType> getRequestTypeMap() {
        List<EventRequestType> list = requestTypeMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(EventRequestType::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 优先级字典
     */
    private Map<Integer, SlaEventFirst> getEventFirstMap() {
        List<SlaEventFirst> list = eventFirstMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SlaEventFirst::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 紧急度字典
     */
    private Map<Integer, SlaEventUrgent> getUrgentMap() {
        List<SlaEventUrgent> list = urgentMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SlaEventUrgent::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 工单字典
     */
    private Map<Integer, EventCategory> getCategoryMap() {
        List<EventCategory> list = eventCategoryMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(EventCategory::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 得到来源字典
     */
    private Map<Integer, EventSource> getSourceMap() {
        List<EventSource> sourceList = eventSourceMapper.selectList(null);
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyMap();
        }
        return sourceList.stream().collect(Collectors.toMap(EventSource::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 得到部门的key,value
     */
    private Map<Integer, SysDepartment> getDeptMap() {
        List<SysDepartment> departmentList = sysDepartmentMapper.selectList(null);
        if (CollectionUtils.isEmpty(departmentList)) {
            return Collections.emptyMap();
        }
        return departmentList.stream().collect(Collectors.toMap(SysDepartment::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 得到服务群组的key,value
     */
    private Map<Integer, EventServiceManage> getServiceManage() {
        List<EventServiceManage> serviceManageList = eventServiceManageMapper.selectList(null);
        if (CollectionUtils.isEmpty(serviceManageList)) {
            return Collections.emptyMap();
        }
        return serviceManageList.stream().collect(Collectors.toMap(EventServiceManage::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 得到用户的键值对Map<id,SysUser>
     */
    public Map<Integer, SysUser> getUserNameMap() {
        List<SysUser> users = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIs_del, "0"));
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity(), (key1, key2) -> key2));
    }


    /**
     * 工作台首页信息
     */
    public PageInfo<Event> getAllByPage(Event event, Integer pageIndex, Integer pageSize) {
        // 运行时间
        long startTime = System.currentTimeMillis();

        // 根据用户id得到服务群组
        List<Integer> serviceGroups = new ArrayList<>();
        QueryWrapper<EventBillingDesignate> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("engineer_userid", event.getWX_userId()==null?sysUserService.getUser().getId():event.getWX_userId());
        queryWrapper3.eq("is_del", "0");
        queryWrapper3.isNotNull("service_management_id");
        List<EventBillingDesignate> groups = eventBillingDesignateService.selectList(queryWrapper3);
        groups.forEach(x -> {
            serviceGroups.add(x.getService_management_id());
        });
        // 如果找不到服务群组，就返回空集合
        if (CollectionUtils.isEmpty(serviceGroups)) {
            serviceGroups.add(-1);
        }
        Date newEndTime = null;
        if (null != event.getCreate_time_end()) {
            newEndTime = org.apache.commons.lang.time.DateUtils.addDays(event.getCreate_time_end(), 1);
        }
        if (pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        }
        List<Event> eventList = eventMapper.selectList(Wrappers.<Event>lambdaQuery()
                // 工作台条件必选字段
                // and (service_group_id in (1,2,3) or create_user_id = 1)
                .and(null == event.getUser_id(), i -> i.in(Event::getService_groups_id, serviceGroups).or().eq(Event::getCreate_user, event.getWX_userId()==null?sysUserService.getUser().getId():event.getWX_userId()))
                .eq(Event::getIs_del, String.valueOf(0))
                // 高级查询id
                .eq(null != event.getId(), Event::getId, event.getId())
                // 高级查询解决人
                .eq(null != event.getSolve_user_id(), Event::getSolve_user_id, event.getSolve_user_id())
                // 高级查询条件 关键字
                .like(StringUtils.isNotBlank(event.getTitle()), Event::getTitle, event.getTitle())
                // 高级查询条件 id
                .eq(null != event.getId(), Event::getId, event.getId())
                // 高级查询条件 状态
                .eq(StringUtils.isNotBlank(event.getStatus()), Event::getStatus, event.getStatus())
                // 高级查询条件 用户id
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // 高级查询条件 开单人
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // 高级查询条件 处理人
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // 高级查询条件 响应人
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // 高级查询条件 来源
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // 高级查询条件 部门
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // 高级查询条件 工单类别
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // 高级查询条件 优先级
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // 高级查询条件 开单起始时间
                .ge(null != event.getCreate_time_start(), Event::getCreate_time, event.getCreate_time_start())
                // 高级查询条件 开单结束时间 +1 天
                .le(null != newEndTime, Event::getCreate_time, newEndTime)
                // 高级查询条件 开单时间
                .between(null != event.getCreate_time() && null != event.getCreate_time_end(), Event::getCreate_time, event.getCreate_time(), event.getCreate_time_end())
                // 高级查询条件 服务群组
                .eq(null != event.getService_groups_id(), Event::getService_groups_id, event.getService_groups_id())
                // 高级查询条件 请求类型
                .eq(null != event.getRequest_type(), Event::getRequest_type, event.getRequest_type())
                // 高级查询条件 用户满意度
                .eq(null != event.getEvent_satisfaction(), Event::getEvent_satisfaction, event.getEvent_satisfaction())
                // 高级查询条件 方案分类
                .eq(null != event.getProgramme_type(), Event::getProgramme_type, event.getProgramme_type())
                // 高级查询条件 解决类型
                .eq(null != event.getSolve_type(), Event::getSolve_type, event.getSolve_type())
                // 高级查询条件 费用开始
                .ge(StringUtils.isNotBlank(event.getExpenses_start()), Event::getExpenses, event.getExpenses_start())
                // 高级查询条件 费用结束
                .le(StringUtils.isNotBlank(event.getExpenses_end()), Event::getExpenses, event.getExpenses_end())
                // 高级查询条件 符合sla
                .eq(StringUtils.isNotBlank(event.getMeetResponseSLA()), Event::getMeetResponseSLA, event.getMeetResponseSLA())
                //状态集合
                .in(!CollectionUtils.isEmpty(event.getStatusList()),Event::getStatus , event.getStatusList())
        );
        // 得到耗时
        long endTime = System.currentTimeMillis();
        log.info("查询耗时：{}", endTime - startTime);
        // 得到用户map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // 得到工单类别map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // 得到服务组map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        // 得到当前登录人id
        Integer currentUserId = event.getWX_userId()==null?sysUserService.getUser().getId():event.getWX_userId();
        // 得到Priority_id的集合
        List<Integer> priorityIds = eventList.stream().map(Event::getPriority_id).collect(Collectors.toList());
        Map<Integer, String> slaEventFirstMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(priorityIds)) {
            List<SlaEventFirst> slaEventFirsts = slaEventFirstMapper.selectList(Wrappers.<SlaEventFirst>lambdaQuery().in(SlaEventFirst::getId, priorityIds));
            // 转换为map集合,key为id,value为first_name
            slaEventFirstMap = slaEventFirsts.stream().collect(Collectors.toMap(SlaEventFirst::getId, SlaEventFirst::getFirst_name, (key1, key2) -> key2));
        }
        // 得到查询剩余时间耗时
        long endTime3 = System.currentTimeMillis();
        log.info("查询字典耗时：{}", endTime3 - endTime);
        for (Event member : eventList) {
            // 单次开始时间
            long fortime1 = System.currentTimeMillis();
            // 状态名称
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // 优先级名称
            member.setPriority_name("");
            if (null != member.getPriority_id() && !CollectionUtils.isEmpty(priorityIds)) {
                member.setPriority_name(slaEventFirstMap.get(member.getPriority_id()));
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // 用户名称
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // 开单人名称
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // 处理人名称
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // 工单类别名称
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // 服务组名称
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // 得到剩余时间
            member.setSurplusMinutes(getSurplusMinutes(member));
            // 判断当前登录人是否是处理人
            member.setIsHandler(currentUserId.equals(member.getHandler()));
            // 判断当前登录人是否是开单人
            member.setIsCreateUser(currentUserId.equals(member.getCreate_user()));
            // 单次结束时间
            long fortime2 = System.currentTimeMillis();
            log.info("单次循环耗时：{}", fortime2 - fortime1);
        }
        // 结束时间
        long end = System.currentTimeMillis();
        // 打印耗时
        log.info("查询工单列表耗时：{}ms", end - startTime);
        return new PageInfo<>(eventList);
    }

    /**
     * pagehelper手动分页
     *
     * @param currentPage 当前页
     * @param pageSize
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * currentPage;
            if (toIndex > total) {
                toIndex = total;
            }
            if (pageSize * (currentPage - 1) < toIndex) {
                list = list.subList(pageSize * (currentPage - 1), toIndex);
            } else {
                list = new ArrayList<>();
            }
        }
        Page<T> page = new Page<>(currentPage, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);

        PageInfo<T> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    /**
     * 得到剩余时间
     */
    private String getSurplusMinutes(Event temp) {
        SlaEventConfig slaEventConfig = getSlaEventConfig(temp.getId());
        if (null != slaEventConfig) {
            List<EventTimes> timeList = eventTimesService.selectList(Wrappers.<EventTimes>lambdaQuery()
                    .eq(EventTimes::getEvent_id, temp.getId()).orderByDesc(EventTimes::getCreate_time).last("limit 1")
            );
            if (CollectionUtils.isEmpty(timeList)) {
                return "";
            }
            EventTimes eventTimes = timeList.get(0);

            Date now = new Date();
            String time = "";
            long xctime = 0;
            if (EventStatusEnums.PENDING_RESPONSE.getType().equals(temp.getStatus())) {
                //slaEventConfig
                time = slaEventConfig.getResponse_time();
//                xctime = getTimeDifference(now, temp.getCreate_time());
                // 得到响应时间
                xctime = eventTimes.getResponse_times();
            } else {
                time = slaEventConfig.getSolve_time();
//                xctime = null == temp.getResponseTime()?0:getTimeDifference(now, temp.getResponseTime());
                xctime = eventTimes.getHandle_times();
            }
            // string 转为long
            long timeLong = Long.parseLong(time);
            // 计算timeLong与xctime的差值
            long diff = timeLong - xctime;
            // 如果差值小于0，说明超时,diff转为正数
            if (diff < 0) {
                diff = -diff;
                String tempTime = "";
                // diff 为分钟, 如果超过60分钟,tempTime设置为小时分钟,超过24小时,tempTime设置为天数
                if (diff > 60 * 24) {
                    long day = diff / (60 * 24);
                    long hours = diff % (60 * 24) / 60;
                    long minutes = diff % (60 * 24) % 60;
                    String hoursStr = hours < 1 ? "" : hours + "小时";
                    tempTime = day + "天" + hoursStr + minutes + "分钟";
                } else if (diff > 60) {
                    tempTime = diff / 60 + "小时" + diff % 60 + "分钟";
                } else {
                    tempTime = diff + "分钟";
                }
                return "超时: " + tempTime;
            } else {
                return "剩余: " + diff + "分钟";
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得两个时间差,转换为分钟(int)
     */

    private long getTimeDifference(Date now, Date createTime) {
        long diff = now.getTime() - createTime.getTime();
        long minutes = diff / (1000 * 60);
        return minutes;
    }


    /**
     * 获得剩余时间
     *
     * @param event_id
     * @return
     */
    public  SlaEventConfig getSlaEventConfig(Integer event_id) {
        //查询类别对应的SLA
        List<SlaEvent> slaByEventCategory = slaEventService.getSlaByEventCategory(null);
        HashMap<Integer, SlaEvent> slaMap = new HashMap();
        slaByEventCategory.forEach(x -> slaMap.put(x.getEventCategoryId(), x));
        //查询待响应、待处理的工单
        Event event = eventMapper.selectById(event_id);
        //根据类型获取对应合同的SLA
        if (null == event) {
            return null;
        }
        SlaEvent slaEvent = slaMap.get(event.getEvent_category_id());
        if (slaEvent == null) {
            return null;
        }
        //查询详细SLA配置
        QueryWrapper<SlaEventConfig> qw = new QueryWrapper<>();
        qw.eq("sla_event_id", slaEvent.getId());
        qw.eq("is_del", "0");
        if ("1".equals(slaEvent.getIs_first())) {
            //查询优先级
            SlaEventImpactUrgent seiu = slaEventImpactUrgentService.selectOne(new QueryWrapper<SlaEventImpactUrgent>().eq("is_del", "0").eq("impact_id", event.getEffect_degree()).eq("urgent_id", event.getUrgency_degree_id()));
            if (null != seiu) {
                qw.eq("first_id", seiu.getFirst_id());
            }
        }
        if ("1".equals(slaEvent.getIs_request())) {
            qw.eq("request_type_id", event.getRequest_type());
        }
        List<SlaEventConfig> configs = slaEventConfigService.selectList(qw);
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        }
        SlaEventConfig slaEventConfig = configs.get(0);
        return slaEventConfig;
    }

    public String getUserNameById(Integer id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser != null) {
            String name = sysUser.getName();
            return name;
        }
        return "";
    }

    /**
     * 组内全部事件
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    public PageInfo<Event> toMyGroupList(Integer pageIndex, Integer pageSize, EventVo beanParam) {
        // 根据用户id得到服务群组
        List<Integer> serviceGroups = new ArrayList<>();
        QueryWrapper<EventBillingDesignate> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("engineer_userid", beanParam.getWX_userId()==null?sysUserService.getUser().getId():beanParam.getWX_userId());
        queryWrapper3.eq("is_del", "0");
        queryWrapper3.isNotNull("service_management_id");
        List<EventBillingDesignate> groups = eventBillingDesignateService.selectList(queryWrapper3);
        groups.forEach(x -> {
            serviceGroups.add(x.getService_management_id());
        });
        if (CollectionUtils.isEmpty(serviceGroups)) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<Event> list = eventMapper.toMyGroupList(beanParam, serviceGroups);
        for (Event member : list) {
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
//            EventSatisfaction satisfaction = satisfactionMapper.selectOne(Wrappers.<EventSatisfaction>lambdaQuery()
//                    .eq(EventSatisfaction::getEvent_id, member.getId()).orderByDesc(EventSatisfaction::getCreate_time)
//                    .last(" limit 1")
//            );
//            member.setEvent_satisfaction(null == satisfaction ? 0 : satisfaction.getEvent_satisfaction());
        }
        PageInfo<Event> page = new PageInfo<>(list);

        return page;
    }

    /**
     * 我参与的
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    public PageInfo<Event> toMyParticipationList(Integer pageIndex, Integer pageSize, EventVo beanParam) {
        Integer user = beanParam.getCurrent_user() == null ? sysUserService.getUser().getId() : beanParam.getCurrent_user();
        beanParam.setCreate_user(user);//开单人
        beanParam.setSolve_user_id(user); //解决工程师
        beanParam.setHandler(user);  //处理人

        PageHelper.startPage(pageIndex, pageSize);
        List<Event> list = eventMapper.toMyParticipationList(beanParam);
        for (Event member : list) {
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
//            EventSatisfaction satisfaction = satisfactionMapper.selectOne(Wrappers.<EventSatisfaction>lambdaQuery()
//                    .eq(EventSatisfaction::getEvent_id, member.getId()).orderByDesc(EventSatisfaction::getCreate_time)
//                    .last(" limit 1")
//            );
//            member.setEvent_satisfaction(null == satisfaction?0:satisfaction.getEvent_satisfaction());
        }
        PageInfo<Event> page = new PageInfo<>(list);

        return page;
    }

    /**
     * 用户历史请求
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    public PageInfo<Event> userHistory(EventVo beanParam) {
        PageHelper.startPage(beanParam.getPageIndex(), beanParam.getPageSize());
        List<Event> list = eventMapper.participationList(beanParam);
        for (Event member : list) {
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
        }
        return new PageInfo<>(list);
    }

    /**
     * 协查事件
     *
     * @param pageIndex
     * @param pageSize
     * @param event
     * @return
     */
    public PageInfo<Event> toXieChaList(Integer pageIndex, Integer pageSize, EventVo event) {
        SysUser user = sysUserService.getUser();
        //得到当前用户的协查事件id
        List<EventAssist> eventIdList = eventAssistMapper.selectList(Wrappers.<EventAssist>lambdaQuery()
                .eq(EventAssist::getUser_id, user.getId())
                .eq(EventAssist::getIs_del, "0")
        );
        if (CollectionUtils.isEmpty(eventIdList)) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<Event> list = eventMapper.selectList(Wrappers.<Event>lambdaQuery()
                .in(Event::getId, eventIdList.stream().map(EventAssist::getEvent_id).collect(Collectors.toList()))
                // 关键字
                .like(StringUtils.isNotBlank(event.getKeywords()), Event::getTitle, event.getKeywords())
                // 编号
                .like(null != event.getId(), Event::getId, event.getId())
                // 状态
                .eq(StringUtils.isNotBlank(event.getStatus()), Event::getStatus, event.getStatus())
                // 用户
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // 开单人
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // 处理人
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // 响应人
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // 来源
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // 服务群组
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // 部门
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // 工单类别
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // 优先级
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // 开单时间
                .ge(StringUtils.isNotBlank(event.getCreate_start_time()), Event::getCreate_time, event.getCreate_start_time())
                .le(StringUtils.isNotBlank(event.getCreate_end_time()), Event::getCreate_time, event.getCreate_end_time())
                // 费用
                .like(StringUtils.isNotBlank(event.getExpenses()), Event::getExpenses, event.getExpenses())

                .eq(Event::getIs_del, "0")
        );
        // 得到用户map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // 得到工单类别map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // 得到服务组map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        for (Event member : list) {
            // 状态名称
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // 优先级名称
            member.setPriority_name("");
            if (null != member.getPriority_id() && null != slaEventFirstMapper.selectById(member.getPriority_id())) {
                member.setPriority_name(slaEventFirstMapper.selectById(member.getPriority_id()).getFirst_name());
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // 用户名称
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // 开单人名称
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // 处理人名称
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // 工单类别名称
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // 服务组名称
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // 得到剩余时间
            member.setSurplusMinutes(getSurplusMinutes(member));

        }

        return new PageInfo<>(list);
    }


    //图片查询
    public List<FileManage> eventFileList(Integer id) {
        List<FileManage> fileManages = new ArrayList<>();
        List<EventFile> files = eventFileMapper.selectList(Wrappers.<EventFile>lambdaQuery().eq(EventFile::getEvent_id, id).eq(EventFile::getType, 1).eq(EventFile::getIs_del, "0"));
        if (Base.notEmpty(files) && files.size() > 0) {
            fileManages = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery().in(FileManage::getId, files.stream().map(EventFile::getFile_id).collect(Collectors.toList())));
            fileManages.forEach(x -> {
                String path = uploadPath + File.separator + x.getFile_name();
                File file = new File(path);
                if (file.exists()) {
                    String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                    x.setBase64(base64);
                } else {
                    x.setBase64("");
                }
                x.setFile_path(appUploadPath + x.getFile_name());
                x.setUrl(appUploadPath + x.getFile_name());
                x.setName(x.getFile_name());
            });
        }
        return fileManages;
    }

    //我要开单功能
    public ResultInfo saveWantToBill(EventVo eventVo, List<MultipartFile> files) {
        //获取用户信息
        SysUser user = null ;
        if(null == eventVo.getUserId()){
             user = sysUserService.getUser();
        }else {
             user = sysUserService.selectById(eventVo.getUserId());
        }

        Event event = new Event();
        BeanUtils.copyProperties(eventVo, event);
        if (null != user) {
            event.setUser_id(user.getId());
            event.setUser_type(1);//用户开单
            event.setUser_name(user.getUser_name());
            event.setDepartment(user.getDepartment_id());
            event.setPosition(user.getPosition());
            event.setLocation(user.getLocation());
            event.setPhone(user.getTelephone());
            event.setLandline(user.getLandline());
            event.setEmail(user.getMailbox());
        }
        event.setIs_del("0");
        event.setRequest_type(Integer.valueOf(eventVo.getRequest_type()));
        event.setEffect_degree(Integer.valueOf(eventVo.getEffect_degree()));
        event.setStatus("1");
        event.setCreate_time(new Date());
        event.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
        eventMapper.insert(event);
        if (null != event.getHandler()) {
            //添加operate_node
            OperateNode operateNode = new OperateNode();
            operateNode.setBill_id(event.getId());
            operateNode.setBill_type(GlobalData.EVENT);
            operateNode.setOperate_user(event.getHandler());
            operateNode.setOperate_type(GlobalData.EVENT_STATUS_DXY);
            operateNode.setIs_complete(GlobalData.IS_COMPLETE_NO);
            operateNode.setTiming_start(new Date());
            operateNode.setTime_spent(0);
            operateNode.setCreate_time(new Date());
            operateNode.setUpdate_time(new Date());
            operateNodeService.insert(operateNode);
        }
        if (!CollectionUtils.isEmpty(eventVo.getEventAssets1())) {
            for (AssetsManage e : eventVo.getEventAssets1()) {
                int id = e.getId();
                EventAssets eventAssets = new EventAssets();
                eventAssets.setAssets_id(id);
                eventAssets.setEvent_id(event.getId());
                eventAssets.setCreate_time(new Date());
                eventAssets.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
                eventAssets.setIs_del("0");
                eventAssetsMapper.insert(eventAssets);
            }
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(id);
                eventFile.setType("1");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
                eventFile.setIs_del("0");
                eventFileMapper.insert(eventFile);
            }
        }
        if (null != eventVo.getFileIds()) {
            String[] ids = eventVo.getFileIds().split(",");
            for (int i = 0; i < ids.length; i++) {
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(Integer.parseInt(ids[i]));
                eventFile.setIs_del("0");
                eventFile.setType("1");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
                eventFileMapper.insert(eventFile);
            }
        }
        //事件ID
        EventTimes times = new EventTimes();
        times.setEvent_id(event.getId());//事件ID
        times.setResponse_times(0);
        times.setResponse_exceed_times(0);
        times.setHandle_times(0);
        times.setHandle_exceed_times(0);
        times.setIs_del("0");
        eventTimesService.insert(times);

        if (null == eventVo.getUserId()){
            return ResultInfo.success("开单成功!", new LogVo(event.getId(), "我要开单", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }else {
            return ResultInfo.success("开单成功!", new LogVo(eventVo.getUserId(),event.getId(), "我要开单", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }


    }

    //修改
    public ResultInfo updateWantToBill(EventVo eventVo, List<MultipartFile> files) {
        //获取用户信息
        Event event = selectById(eventVo.getId());
        BeanUtils.copyProperties(eventVo, event);
        event.setRequest_type(Integer.valueOf(eventVo.getRequest_type()));
        event.setEffect_degree(Integer.valueOf(eventVo.getEffect_degree()));
        event.setUpdate_time(new Date());
        event.setUpdate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
        eventMapper.updateById(event);

        if (!CollectionUtils.isEmpty(eventVo.getEventAssets1())) {
            //关联配置项
            List<EventAssets> list = eventAssetsMapper.selectList(new QueryWrapper<EventAssets>().eq("event_id", event.getId()));
            if (null != list) {
                list.forEach(a -> {
                    a.setIs_del("1");
                    Integer count = eventAssetsMapper.updateById(a);
                });
            }
            for (AssetsManage e : eventVo.getEventAssets1()) {
                int id = e.getId();
                EventAssets eventAssets = new EventAssets();
                eventAssets.setAssets_id(id);
                eventAssets.setEvent_id(event.getId());
                eventAssets.setCreate_time(new Date());
                eventAssets.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
                eventAssets.setIs_del("0");
                eventAssetsMapper.insert(eventAssets);
            }
        }
        //属性信息 是否存在文件删除
//        LambdaUpdateWrapper<EventFile> wrapper = new LambdaUpdateWrapper<>();
//        wrapper.set(EventFile::getIs_del, GlobalData.ISDEL_YES)
//                .eq(EventFile::getEvent_id, event.getId())
//                .eq(EventFile::getType, "1");
//        eventFileMapper.update(null, wrapper);

        String uploadedFiles = eventVo.getUploadedFiles();
        List<EventFile> fileManages = eventFileMapper.selectList(Wrappers.<EventFile>lambdaQuery().eq(EventFile::getEvent_id, event.getId()).eq(EventFile::getType, 1).eq(EventFile::getIs_del, "0"));
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<EventFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(EventFile::getEvent_id, event.getId())
                    .in(EventFile::getFile_id, original);
            eventFileMapper.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(id);
                eventFile.setType("1");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
                eventFile.setIs_del("0");
                eventFileMapper.insert(eventFile);
            }
        }

        if (null != eventVo.getFileIds()) {
            String[] ids = eventVo.getFileIds().split(",");
            for (int i = 0; i < ids.length; i++) {
                EventFile eventFile = new EventFile();
                eventFile.setEvent_id(event.getId());
                eventFile.setFile_id(Integer.parseInt(ids[i]));
                eventFile.setIs_del("0");
                eventFile.setType("1");
                eventFile.setCreate_time(new Date());
                eventFile.setCreate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
                eventFileMapper.insert(eventFile);
            }
        }
        if (null == eventVo.getUserId()){
            return ResultInfo.success("修改成功!", new LogVo(event.getId(), "我要开单", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }else {
            return ResultInfo.success("修改成功!", new LogVo(eventVo.getUserId(),event.getId(), "我要开单", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }

    }

    /**
     *  主页统计
     * @param start_time
     * @param end_time
     * @return
     */
    public Map statistics(String start_time, String end_time) {
        return eventMapper.statistics(start_time,end_time);
    }

    public List<SysUser> getSynergiaUsers(Integer id) {
        Event event = eventMapper.selectById(id);
        //处理工程师
        List<SysUser> sysUsers = new ArrayList<>();
        QueryWrapper<EventBillingDesignate> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("service_management_id", event.getService_groups_id());
        queryWrapper2.eq("type", "2");
        List<EventBillingDesignate> eventBillingDesignateList = eventBillingDesignateMapper.selectList(queryWrapper2);
        if (!org.springframework.util.CollectionUtils.isEmpty(eventBillingDesignateList)) {
            List<Integer> userIds = eventBillingDesignateList.stream().map(EventBillingDesignate::getEngineer_userid).distinct().collect(Collectors.toList());
            String ids = userIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            sysUsers = sysUserService.getUserList(ids);
        }
        return sysUsers;
    }
}
