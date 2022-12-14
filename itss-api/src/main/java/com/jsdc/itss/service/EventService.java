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
     * ??????????????????
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
        //1 ????????????????????????
        if (Base.notEmpty(eventVo.getTimeTaskStatus()) && eventVo.getTimeTaskStatus().equals("1")) {
            userId = eventVo.getCreate_user();
        } else {
            SysUser userData = sysUserService.getUserData(eventVo.getUserId());
            userId = userData.getId();
        }
        event.setCreate_user(userId);
        eventMapper.insert(event);
        if (null != event.getHandler()) {
            //??????operate_node
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
        //??????ID
        EventTimes times = new EventTimes();
        times.setEvent_id(event.getId());//??????ID
        times.setResponse_times(0);
        times.setResponse_exceed_times(0);
        times.setHandle_times(0);
        times.setHandle_exceed_times(0);
        times.setIs_del("0");
        eventTimesService.insert(times);

        //????????????
        SysLog sysLog = new SysLog();
        sysLog.setBill_id(event.getId());
        sysLog.setTitle("??????");
        sysLog.setContent("");
        sysLog.setOperate_time(new Date());
        sysLog.setModule(LogEnums.LOG_GZT.getValue());
        sysLogService.insert(sysLog);
        return ResultInfo.success(event, "????????????!");
    }

    /**
     * ??????????????????
     * Author wzn
     * Date 2022/3/14 14:48
     */
    public ResultInfo updateRemark(Event event) {
        eventMapper.updateById(event);
        if(null != event.getLogin_id()){
            return ResultInfo.success("????????????!", new LogVo(event.getLogin_id(), event.getId(), "??????", event.getRemark()));
        }
        return ResultInfo.success("????????????!", new LogVo(event.getId(), "??????", event.getRemark()));
    }

    /**
     * ???????????????
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
     * ????????????
     * Author wzn
     * Date 2022/3/14 15:22
     */
    public ResultInfo response(Event event) {
        if (!"1".equals(event.getStatus())) {
            ResultInfo.success("??????????????????????????????");
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
            if (null == original.getHandler()) {//??????????????????????????????
                //??????operate_node
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
            //????????????????????????OLA
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
            return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", StringUtils.EMPTY));
        }
        return ResultInfo.success("????????????", new LogVo(currentId, event.getId(), "??????", StringUtils.EMPTY));
    }

    /**
     * ????????????
     * ?????????????????????/?????????????????????????????????????????????????????????????????????
     * ????????????????????? ?????????
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
            throw new CustomException("???????????????????????????");
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

            //??????operate_node
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
                return ResultInfo.success("????????????", new LogVo(event.getLogin_id(),event.getId(), "??????", StringUtils.EMPTY));
            }
            return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", StringUtils.EMPTY));
        }

        return ResultInfo.error("????????????");
    }

    /**
     * ??????
     * Author wzn
     * Date 2022/3/15 14:38
     */
    public ResultInfo transferOrder(Event eventVo) {
        if (!EventStatusEnums.PROCESSING.getType().equals(eventVo.getStatus())) {
            throw new CustomException("???????????????????????????");
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

        //??????operate_node
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
        return ResultInfo.success("????????????", new LogVo(eventVo.getUserId(), eventVo.getId(), "??????", StringUtils.EMPTY));
    }

    /**
     * ????????????????????????
     */
    /**
     * ????????????
     * Author wzn
     * Date 2022/3/16 9:06
     */
    public ResultInfo process(Event event, List<MultipartFile> filesHandle) {
        if (!"2".equals(event.getStatus())) {
            throw new CustomException("???????????????????????????");
        }

        updateEventInfo(event, null, filesHandle);
        return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", StringUtils.EMPTY));
    }

    /**
     * ???????????????????????????
     *
     * @param event
     */
    public void processClose(Event event) {
        if (!"2".equals(event.getStatus())) {
            throw new CustomException("???????????????????????????");
        }
        event.setStatus("5");
        event.setCloseTime(new Date());//????????????
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
     * ??????
     * Author wzn
     * Date 2022/3/16 9:12
     */
    public void revoke(Event event) {
        event.setRevokeTime(new Date());
        event.setStatus(EventStatusEnums.REVOKED.getType());
        eventMapper.updateById(event);
    }


    /**
     * ????????????
     * Author wzn
     * Date 2022/3/16 9:19
     */
    public ResultInfo pause(Event event) {
        if (!"2".equals(event.getStatus())) {
            return ResultInfo.error("????????????????????????");
        }
        event.setStatus("3");
        //??????????????????
        event.setPauseTime(new Date());

        EventTimes eventTimes = eventTimesService.selectOne(Wrappers.<EventTimes>lambdaQuery()
                .eq(EventTimes::getEvent_id, event.getId()).eq(EventTimes::getIs_del, GlobalData.ISDEL_NO)
//                .last("limit 1")
        );
        if (null == eventTimes) {
            return ResultInfo.error("?????????????????????");
        }

        eventMapper.updateById(event);
        eventTimes.setLast_pause_time(new Date());
        eventTimes.setLast_pause_type("1");
        eventTimesService.updateById(eventTimes);
        if(null != event.getLogin_id()){
            return ResultInfo.success("????????????", new LogVo(event.getLogin_id(),event.getId(), "??????", StringUtils.EMPTY));
        }
        return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", event.getPauseRemark()));
    }


    /**
     * ????????????
     * Author wzn
     * Date 2022/3/16 9:26
     */
    public ResultInfo restore(Event event) {
        if (!"3".equals(event.getStatus())) {
            return ResultInfo.error("?????????????????????");
        }
        event.setStatus("2");
        EventTimes eventTimes = eventTimesService.selectOne(Wrappers.<EventTimes>lambdaQuery().eq(EventTimes::getEvent_id, event.getId()).eq(EventTimes::getIs_del, GlobalData.ISDEL_NO));
        if (null == eventTimes) {
            return ResultInfo.error("?????????????????????");
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
            return ResultInfo.success("????????????", new LogVo(event.getLogin_id(),event.getId(), "??????", StringUtils.EMPTY));
        }
        return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", StringUtils.EMPTY));
    }


    /**
     * ????????????????????????
     */
    public PageInfo<Event> toList(Integer pageIndex, Integer pageSize, Event event) {
        // ????????????id??????????????????
        List<Integer> serviceGroups = new ArrayList<>();
        try {
            serviceGroups = eventBillingDesignateService.getServiceIdByCurrentUser(event.getWX_userId());
            // ????????????????????????????????????????????????
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
                // ?????????????????? ?????????
                .like(StringUtils.isNotBlank(event.getTitle()), Event::getTitle, event.getTitle())
                // ?????????????????? id
                .eq(null != event.getId(), Event::getId, event.getId())
                // ?????????????????? ??????
                .in(!CollectionUtils.isEmpty(statusList), Event::getStatus, statusList)
                // ?????????????????? ??????id
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // ?????????????????? ?????????
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // ?????????????????? ?????????
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // ?????????????????? ??????
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // ?????????????????? ??????
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // ?????????????????? ????????????
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // ?????????????????? ?????????
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // ?????????????????? ??????????????????
                .ge(null != event.getCreate_time_start(), Event::getCreate_time, event.getCreate_time_start())
                // ?????????????????? ??????????????????
                .le(null != newEndTime, Event::getCreate_time, newEndTime)
                // ?????????????????? ????????????
                .eq(null != event.getService_groups_id(), Event::getService_groups_id, event.getService_groups_id())
                // ?????????????????? ???????????????
                .eq(null != event.getSolve_user_id(), Event::getSolve_user_id, event.getSolve_user_id())
                // ?????????????????? ?????????
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // ?????????????????? ??????????????? event_satisfaction
                .eq(null != event.getEvent_satisfaction(), Event::getEvent_satisfaction, event.getEvent_satisfaction())
                // ?????????????????? ???????????? programme_type
                .eq(null != event.getProgramme_type(), Event::getProgramme_type, event.getProgramme_type())
                // ?????????????????? ??????SLA
                .eq(StringUtils.isNotBlank(event.getMeetResponseSLA()), Event::getMeetResponseSLA, event.getMeetResponseSLA())
                // ?????????????????? ??????????????????
                .ge(null != event.getCloseStartTime(), Event::getCloseTime, event.getCloseStartTime())
                // ?????????????????? ??????????????????
                .le(null != event.getCloseEndTime(), Event::getCloseTime, event.getCloseEndTime())
                // ?????????????????? ????????????
                .eq(null != event.getSolve_type(), Event::getSolve_type, event.getSolve_type())
                // ?????????????????? ????????????
                .ge(StringUtils.isNotBlank(event.getExpenses_start()), Event::getExpenses, StringUtils.isNotBlank(event.getExpenses_start()) ? new BigDecimal(event.getExpenses_start()) : null)
                // ?????????????????? ????????????
                .le(StringUtils.isNotBlank(event.getExpenses_end()), Event::getExpenses, StringUtils.isNotBlank(event.getExpenses_end()) ? new BigDecimal(event.getExpenses_end()) : null)
                // ?????????????????? ????????????
                .eq(null != event.getRequest_type(), Event::getRequest_type, event.getRequest_type())
        );
        // ????????????
        long endTime = System.currentTimeMillis();
        log.info("???????????????{}", endTime - startTime);
        // ????????????map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // ??????????????????map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // ???????????????map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        // ?????????????????????id
//        Integer currentUserId = sysUserService.getUser().getId();
        // ??????Priority_id?????????
        List<Integer> priorityIds = eventList.stream().map(Event::getPriority_id).collect(Collectors.toList());
        Map<Integer, String> slaEventFirstMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(priorityIds)) {
            List<SlaEventFirst> slaEventFirsts = slaEventFirstMapper.selectList(Wrappers.<SlaEventFirst>lambdaQuery().in(SlaEventFirst::getId, priorityIds));
            // ?????????map??????,key???id,value???first_name
            slaEventFirstMap = slaEventFirsts.stream().collect(Collectors.toMap(SlaEventFirst::getId, SlaEventFirst::getFirst_name, (key1, key2) -> key2));
        }
        // ??????????????????????????????
        long endTime3 = System.currentTimeMillis();
        log.info("?????????????????????{}", endTime3 - endTime);
        for (Event member : eventList) {
            // ??????????????????
            long fortime1 = System.currentTimeMillis();
            // ????????????
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // ???????????????
            member.setPriority_name("");
            if (null != member.getPriority_id() && !CollectionUtils.isEmpty(priorityIds)) {
                member.setPriority_name(slaEventFirstMap.get(member.getPriority_id()));
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // ????????????
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // ???????????????
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // ???????????????
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // ??????????????????
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // ???????????????
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // ??????????????????
            long fortime2 = System.currentTimeMillis();
            log.info("?????????????????????{}", fortime2 - fortime1);
        }
        // ????????????
        long end = System.currentTimeMillis();
        // ????????????
        log.info("???????????????????????????{}ms", end - startTime);

//        List<Event> list = eventMapper.toList(beanParam);
//        for (Event member : list) {
//            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
//            member.setPriority_name("");
//            if (null != member.getPriority_id() && null != slaEventFirstMapper.selectById(member.getPriority_id())) {
//                member.setPriority_name(slaEventFirstMapper.selectById(member.getPriority_id()).getFirst_name());
//            }
//            // ?????????
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
        // ????????????id??????????????????
        List<Integer> serviceGroups = new ArrayList<>();
        try {
            serviceGroups = eventBillingDesignateService.getServiceIdByCurrentUser();
            // ????????????????????????????????????????????????
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
                // ?????????????????? ?????????
                .like(StringUtils.isNotBlank(event.getTitle()), Event::getTitle, event.getTitle())
                // ?????????????????? id
                .eq(null != event.getId(), Event::getId, event.getId())
                // ?????????????????? ??????
                .in(!CollectionUtils.isEmpty(statusList), Event::getStatus, statusList)
                // ?????????????????? ??????id
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // ?????????????????? ?????????
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // ?????????????????? ?????????
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // ?????????????????? ??????
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // ?????????????????? ??????
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // ?????????????????? ????????????
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // ?????????????????? ?????????
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // ?????????????????? ??????????????????
                .ge(null != event.getCreate_time_start(), Event::getCreate_time, event.getCreate_time_start())
                // ?????????????????? ??????????????????
                .le(null != newEndTime, Event::getCreate_time, newEndTime)
                // ?????????????????? ????????????
                .eq(null != event.getService_groups_id(), Event::getService_groups_id, event.getService_groups_id())
                // ?????????????????? ???????????????
                .eq(null != event.getSolve_user_id(), Event::getSolve_user_id, event.getSolve_user_id())
                // ?????????????????? ?????????
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // ?????????????????? ??????????????? event_satisfaction
                .eq(null != event.getEvent_satisfaction(), Event::getEvent_satisfaction, event.getEvent_satisfaction())
                // ?????????????????? ???????????? programme_type
                .eq(null != event.getProgramme_type(), Event::getProgramme_type, event.getProgramme_type())
                // ?????????????????? ??????SLA
                .eq(StringUtils.isNotBlank(event.getMeetResponseSLA()), Event::getMeetResponseSLA, event.getMeetResponseSLA())
                // ?????????????????? ??????????????????
                .ge(null != event.getCloseStartTime(), Event::getCloseTime, event.getCloseStartTime())
                // ?????????????????? ??????????????????
                .le(null != event.getCloseEndTime(), Event::getCloseTime, event.getCloseEndTime())
                // ?????????????????? ????????????
                .eq(null != event.getSolve_type(), Event::getSolve_type, event.getSolve_type())
                // ?????????????????? ????????????
                .ge(StringUtils.isNotBlank(event.getExpenses_start()), Event::getExpenses, event.getExpenses_start())
                // ?????????????????? ????????????
                .le(StringUtils.isNotBlank(event.getExpenses_end()), Event::getExpenses, event.getExpenses_end())
        );
        // ????????????
        long endTime = System.currentTimeMillis();
        log.info("???????????????{}", endTime - startTime);
        // ????????????map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // ??????????????????map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // ???????????????map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        // ?????????????????????id
//        Integer currentUserId = sysUserService.getUser().getId();
        // ??????Priority_id?????????
        List<Integer> priorityIds = eventList.stream().map(Event::getPriority_id).collect(Collectors.toList());
        Map<Integer, String> slaEventFirstMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(priorityIds)) {
            List<SlaEventFirst> slaEventFirsts = slaEventFirstMapper.selectList(Wrappers.<SlaEventFirst>lambdaQuery().in(SlaEventFirst::getId, priorityIds));
            // ?????????map??????,key???id,value???first_name
            slaEventFirstMap = slaEventFirsts.stream().collect(Collectors.toMap(SlaEventFirst::getId, SlaEventFirst::getFirst_name, (key1, key2) -> key2));
        }
        // ??????????????????????????????
        long endTime3 = System.currentTimeMillis();
        log.info("?????????????????????{}", endTime3 - endTime);
        for (Event member : eventList) {
            // ??????????????????
            long fortime1 = System.currentTimeMillis();
            // ????????????
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // ???????????????
            member.setPriority_name("");
            if (null != member.getPriority_id() && !CollectionUtils.isEmpty(priorityIds)) {
                member.setPriority_name(slaEventFirstMap.get(member.getPriority_id()));
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // ????????????
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // ???????????????
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // ???????????????
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // ??????????????????
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // ???????????????
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // ??????????????????
            long fortime2 = System.currentTimeMillis();
            log.info("?????????????????????{}", fortime2 - fortime1);
        }
        // ????????????
        long end = System.currentTimeMillis();
        // ????????????
        log.info("???????????????????????????{}ms", end - startTime);

        return eventList;
    }

    public List<Event> toLists(EventVo beanParam) {
        List<Event> list = eventMapper.toList(beanParam);
        return list;
    }


    /**
     * ????????????????????????
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
     * ???????????? ????????????
     */
    public ResultInfo exportEventExcel(HttpServletResponse response) {
        List<Event> eventList = eventMapper.selectList(null);

        if (CollectionUtils.isEmpty(eventList)) {
            return ResultInfo.error("???????????????");
        }
        //??????????????????
        Map<Integer, EventServiceManage> serviceManageMap = getServiceManage();
        //???????????????
        Map<Integer, SysUser> userMap = getUserNameMap();
        if (CollectionUtils.isEmpty(userMap)) {
            return ResultInfo.error("????????????,???????????????");
        }
        //????????????
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        //????????????
        Map<Integer, EventSource> sourceMap = getSourceMap();
        //??????????????????
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        //???????????????
        Map<Integer, SlaEventUrgent> urgentMap = getUrgentMap();
        //???????????????
        Map<Integer, SlaEventFirst> eventFirstMap = getEventFirstMap();
        //????????????
        Map<Integer, EventRequestType> requestTypeMap = getRequestTypeMap();
        //????????????
        Map<Integer, SchemeClassification> schemeClassificationMap = getProgrammeTypeMap();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Event member : eventList) {
            Map<String, Object> row = new LinkedHashMap<>();

            row.put("??????", member.getId());
            //??????????????????
            String serviceName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(serviceManageMap) && null != member.getService_groups_id()
                    && null != serviceManageMap.get(member.getService_groups_id())) {
                serviceName = serviceManageMap.get(member.getService_groups_id()).getName();
            }
            row.put("????????????", serviceName);

            // ?????????????????????
            String solveUser = StringUtils.EMPTY;
            if (null != member.getSolve_user_id() && null != userMap.get(member.getSolve_user_id())) {
                solveUser = userMap.get(member.getSolve_user_id()).getName();
            }
            row.put("???????????????", solveUser);
            // ????????????
            String deptName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(deptMap) && null != member.getDepartment()
                    && null != deptMap.get(member.getDepartment())) {
                deptName = deptMap.get(member.getDepartment()).getName();
            }
            row.put("????????????", deptName);
            row.put("??????", null == member.getStatus() ? StringUtils.EMPTY : EventStatusEnums.getType(member.getStatus()));
            String sourceName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(sourceMap) && null != member.getSource_id()
                    && null != sourceMap.get(member.getSource_id())) {
                sourceName = sourceMap.get(member.getSource_id()).getResource_name();
            }
            row.put("????????????", sourceName);
            row.put("??????", StringUtils.isBlank(member.getTitle()) ? StringUtils.EMPTY : member.getTitle());
            row.put("??????", StringUtils.isBlank(member.getDescription()) ? StringUtils.EMPTY : member.getDescription());
            String catetory = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(categoryMap) && null != member.getEvent_category_id()
                    && null != categoryMap.get(member.getEvent_category_id())) {
                catetory = categoryMap.get(member.getEvent_category_id()).getName();
            }
            row.put("????????????", catetory);
            row.put("?????????", member.getEffect_degree());

            String urgencyDegree = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(urgentMap) && null != member.getUrgency_degree_id()
                    && null != urgentMap.get(member.getUrgency_degree_id())) {
                urgencyDegree = urgentMap.get(member.getUrgency_degree_id()).getUrgent_name();
            }
            row.put("?????????", urgencyDegree);

            String priority = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(eventFirstMap) && null != member.getPriority_id()
                    && null != eventFirstMap.get(member.getPriority_id())) {
                priority = eventFirstMap.get(member.getPriority_id()).getFirst_name();
            }
            row.put("?????????", priority);

            String createUser = StringUtils.EMPTY;
            if (null != member.getCreate_user() && null != userMap.get(member.getCreate_user())) {
                createUser = userMap.get(member.getCreate_user()).getName();
            }
            row.put("?????????", createUser);
            row.put("????????????", member.getCreate_time());

            String handlerUser = StringUtils.EMPTY;
            if (null != member.getHandler() && null != userMap.get(member.getHandler())) {
                handlerUser = userMap.get(member.getHandler()).getName();
            }
            row.put("?????????", handlerUser);
            row.put("??????????????????", member.getUpdate_time());

            String assignerUser = StringUtils.EMPTY;
            if (null != member.getAssigner() && null != userMap.get(member.getAssigner())) {
                assignerUser = userMap.get(member.getAssigner()).getName();
            }
            row.put("?????????", assignerUser);
            row.put("????????????", member.getAssigner_time());
            row.put("????????????", member.getResponseTime());
            row.put("??????????????????", member.getPauseTime());
            row.put("????????????", member.getEx_solve_time());
            row.put("????????????", member.getCloseTime());
            row.put("????????????", member.getSolve_type());
            row.put("????????????", member.getSolution());

            EventSatisfaction satisfaction = satisfactionMapper.selectOne(Wrappers.<EventSatisfaction>lambdaQuery()
                    .eq(EventSatisfaction::getEvent_id, member.getId())
                    .orderByDesc(EventSatisfaction::getCreate_time));
            row.put("???????????????", null == satisfaction ? StringUtils.EMPTY : satisfaction.getEvent_satisfaction());
            row.put("????????????", null == satisfaction ? StringUtils.EMPTY : satisfaction.getFeedback());
            row.put("?????????????????????", null == satisfaction ? StringUtils.EMPTY : satisfaction.getCreate_time());
            row.put("?????????????????????", null == satisfaction ? "?????????" : "?????????");
            String username = StringUtils.EMPTY;
            String userPhone = StringUtils.EMPTY;
            String landline = StringUtils.EMPTY;
            SysUser user = null;
            if (null != member.getUser_id() && null != userMap.get(member.getUser_id())) {
                user = userMap.get(member.getUser_id());
            }
            row.put("??????", null == user ? StringUtils.EMPTY : user.getName());
            row.put("??????username", null == user ? StringUtils.EMPTY : user.getUser_name());
            row.put("????????????", null == user ? StringUtils.EMPTY : user.getTelephone());
            row.put("????????????", null == user ? StringUtils.EMPTY : user.getLandline());
            row.put("????????????", null == user ? StringUtils.EMPTY : user.getMailbox());

            String userDeptName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(deptMap) && null != user
                    && null != deptMap.get(user.getId())) {
                userDeptName = deptMap.get(user.getId()).getName();
            }
            row.put("????????????", userDeptName);
            row.put("vip", null == user ? StringUtils.EMPTY : user.getIs_vip());
            row.put("????????????", null == user ? StringUtils.EMPTY : user.getPosition());
            row.put("??????????????????", null == member.getEx_solve_time() ? StringUtils.EMPTY : member.getEx_solve_time());
            row.put("??????", null == member.getExpenses() ? StringUtils.EMPTY : member.getExpenses());
            row.put("????????????", null == member.getNumberOfRestarts() ? StringUtils.EMPTY : member.getNumberOfRestarts());
            row.put("????????????", null == user ? StringUtils.EMPTY : user.getLocation());

            String requesTypeName = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(requestTypeMap) && null != member.getRequest_type()
                    && null != requestTypeMap.get(member.getRequest_type())) {
                requesTypeName = requestTypeMap.get(member.getRequest_type()).getRequest_type_name();
            }
            row.put("????????????", requesTypeName);
            row.put("????????????", member.getId());

            String programme_type_name = StringUtils.EMPTY;
            if (!CollectionUtils.isEmpty(schemeClassificationMap) && null != member.getProgramme_type()
                    && null != schemeClassificationMap.get(member.getProgramme_type())) {
                programme_type_name = schemeClassificationMap.get(member.getProgramme_type()).getName();
            }
            row.put("????????????", programme_type_name);

            row.put("???????????????", StringUtils.EMPTY);
            row.put("?????????", StringUtils.EMPTY);
            row.put("???????????????", StringUtils.EMPTY);
            row.put("???????????????", StringUtils.EMPTY);
            row.put("????????????SLA", StringUtils.EMPTY);
            row.put("??????????????????", StringUtils.EMPTY);
            row.put("????????????SLA", StringUtils.EMPTY);
            row.put("?????????????????????", StringUtils.EMPTY);
            row.put("???????????????", StringUtils.EMPTY);
            row.put("????????????", StringUtils.EMPTY);
            row.put("????????????", StringUtils.EMPTY);
            row.put("??????SLA", StringUtils.EMPTY);
            row.put("??????SLA", StringUtils.EMPTY);
            row.put("???????????????", StringUtils.EMPTY);
            row.put("??????", StringUtils.EMPTY);
            row.put("????????????", StringUtils.EMPTY);
            row.put("??????", StringUtils.EMPTY);
            row.put("?????????????????????", StringUtils.EMPTY);
            row.put("???????????????", StringUtils.EMPTY);
            row.put("????????????", StringUtils.EMPTY);
            row.put("?????????", StringUtils.EMPTY);
            list.add(row);
        }


        ExcelWriter writer = ExcelUtil.getWriter();
//        ExcelWriter writer = ExcelUtil.getWriter("d:/test/writeBeanTest.xlsx");

//        writer.addHeaderAlias("id", "??????");
//        writer.addHeaderAlias("service_groups_name", "????????????");
//        writer.addHeaderAlias("solve_user.user_name", "???????????????");

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
     * ???????????? ????????????
     */
    public ResultInfo exportTemplatesEventExcel(HttpServletResponse response) {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();

        row.put("??????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("?????????", StringUtils.EMPTY);
        row.put("?????????", StringUtils.EMPTY);
        row.put("???????????????", StringUtils.EMPTY);

        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.setOnlyAlias(true);

        //?????????????????? ??????????????????
        int firstRow = 1;
        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();

        //??????
        String [] sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getIs_del, GlobalData.ISDEL_NO)
                .eq(SysUser::getStatus, GlobalData.IS_ENABLE_YES)
        ).stream().map(SysUser::getName).toArray(String[]::new);
        //??????
        String [] eventSources = eventSourceMapper.selectList(Wrappers.<EventSource>lambdaQuery()
                .eq(EventSource::getIs_del, GlobalData.ISDEL_NO)
                .eq(EventSource::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(EventSource::getResource_name).toArray(String[]::new);
        //????????????
        String [] eventCategorys = eventCategoryMapper.selectList(Wrappers.<EventCategory>lambdaQuery()
                .eq(EventCategory::getIs_del, GlobalData.ISDEL_NO)
                .eq(EventCategory::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(EventCategory::getName).toArray(String[]::new);
        //????????????
        String [] eventRequestTypes = eventRequestTypeMapper.selectList(Wrappers.<EventRequestType>lambdaQuery()
                .eq(EventRequestType::getIs_del, GlobalData.ISDEL_NO)
                .eq(EventRequestType::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(EventRequestType::getRequest_type_name).toArray(String[]::new);
        //?????????
        String [] slaEventUrgents = slaEventUrgentMapper.selectList(Wrappers.<SlaEventUrgent>lambdaQuery()
                .eq(SlaEventUrgent::getIs_del, GlobalData.ISDEL_NO)
                .eq(SlaEventUrgent::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(SlaEventUrgent::getUrgent_name).toArray(String[]::new);
        //?????????
        String [] slaEventImpacts = slaEventImpactMapper.selectList(Wrappers.<SlaEventImpact>lambdaQuery()
                .eq(SlaEventImpact::getIs_del, GlobalData.ISDEL_NO)
                .eq(SlaEventImpact::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(SlaEventImpact::getImpact_name).toArray(String[]::new);
        //???????????????
        String[] is_effect = {"???", "???"};
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
     * ???????????? ??????execl
     */
    public ResultInfo importExcel(MultipartFile file) {
        try {
            //fileName ?????????
            String fileName = file.getOriginalFilename();
            boolean xlsx = fileName.endsWith(".xlsx");
            if (!xlsx) {
                return ResultInfo.error("????????????.xlsx???????????????");
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
                        //??????
                        Map<String,Integer> sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                                .eq(SysUser::getIs_del, GlobalData.ISDEL_NO)
                                .eq(SysUser::getStatus, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(SysUser::getName,SysUser::getId));
                        //??????
                        Map<String,Integer> eventSources = eventSourceMapper.selectList(Wrappers.<EventSource>lambdaQuery()
                                .eq(EventSource::getIs_del, GlobalData.ISDEL_NO)
                                .eq(EventSource::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(EventSource::getResource_name,EventSource::getId));
                        //????????????
                        Map<String,Integer> eventCategorys = eventCategoryMapper.selectList(Wrappers.<EventCategory>lambdaQuery()
                                .eq(EventCategory::getIs_del, GlobalData.ISDEL_NO)
                                .eq(EventCategory::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(EventCategory::getName,EventCategory::getId));
                        //????????????
                        Map<String,Integer> eventRequestTypes = eventRequestTypeMapper.selectList(Wrappers.<EventRequestType>lambdaQuery()
                                .eq(EventRequestType::getIs_del, GlobalData.ISDEL_NO)
                                .eq(EventRequestType::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(EventRequestType::getRequest_type_name,EventRequestType::getId));
                        //?????????
                        Map<String,Integer> slaEventUrgents = slaEventUrgentMapper.selectList(Wrappers.<SlaEventUrgent>lambdaQuery()
                                .eq(SlaEventUrgent::getIs_del, GlobalData.ISDEL_NO)
                                .eq(SlaEventUrgent::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(SlaEventUrgent::getUrgent_name,SlaEventUrgent::getId));
                        //?????????
                        Map<String,Integer> slaEventImpacts = slaEventImpactMapper.selectList(Wrappers.<SlaEventImpact>lambdaQuery()
                                .eq(SlaEventImpact::getIs_del, GlobalData.ISDEL_NO)
                                .eq(SlaEventImpact::getIs_use, GlobalData.IS_ENABLE_YES)
                        ).stream().collect(Collectors.toMap(SlaEventImpact::getImpact_name,SlaEventImpact::getId));
                        //???????????????
                        Map<String, String> isEffects = ImmutableMap.of("???", "1", "???", "0");

                        //??????
                        event.setUser_id_name(line.get(0).toString());
                        event.setUser_id(sysUsers.get(line.get(0).toString()));
                        //??????
                        event.setTitle(line.get(1).toString());
                        //??????
                        event.setDescription(line.get(2).toString());
                        //??????
                        event.setSource_name(line.get(3).toString());
                        event.setSource_id(eventSources.get(line.get(3).toString()));
                        //????????????
                        event.setEvent_category_name(line.get(4).toString());
                        event.setEvent_category_id(eventCategorys.get(line.get(4).toString()));
                        //????????????
                        event.setRequest_type_name(line.get(5).toString());
                        event.setRequest_type(eventRequestTypes.get(line.get(5).toString()));
                        //?????????
                        event.setUrgency_degree_name(line.get(6).toString());
                        event.setUrgency_degree_id(slaEventUrgents.get(line.get(6).toString()));
                        //?????????
                        event.setEffect_degree_name(line.get(7).toString());
                        event.setEffect_degree(slaEventImpacts.get(line.get(7).toString()));
                        //???????????????
                        event.setIs_effect_name(line.get(8).toString());
                        event.setIs_effect(isEffects.get(line.get(8).toString()));

                        // ????????????
                        event.setIs_del(String.valueOf(0));
                        // ????????????
                        event.setCreate_time(new Date());
                        // ?????????
                        event.setCreate_user(sysUserService.getUser().getId());
                        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
        return ResultInfo.error("?????????????????????");
    }

    /**
     * ????????????,????????????
     * <p>
     * ???????????????????????????????????????????????????
     * ?????????????????????
     * **?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ?????????????????????????????????sla???????????????sla???????????????
     * ??????????????????????????????????????????????????????????????????
     */
    public ResultInfo reopenEvent(EventVo eventVo) {
        if (null == eventVo || null == eventVo.getId()) {
            return ResultInfo.error("????????????!");
        }
        Event event = selectById(eventVo.getId());
        if (StringUtils.isBlank(event.getStatus())) {
            return ResultInfo.error("???????????????");
        }
        if (EventStatusEnums.CLOSED.getType().equals(event.getStatus())) {
            event.setStatus(EventStatusEnums.PROCESSING.getType());
        } else if (EventStatusEnums.REVOKED.getType().equals(event.getStatus())) {
            event.setStatus(EventStatusEnums.PENDING_RESPONSE.getType());
        } else if (EventStatusEnums.COMMENT.getType().equals(event.getStatus())) {
            event.setStatus(EventStatusEnums.PENDING_RESPONSE.getType());
        } else {
            return ResultInfo.error("?????????????????????");
        }

        event.setNumberOfRestarts(null == event.getNumberOfRestarts() ? 0 : event.getNumberOfRestarts() + 1);
        event.setUpdate_time(new Date());
        event.setUpdate_user(null == eventVo.getCurrent_user() ? sysUserService.getUser().getId() : eventVo.getCurrent_user());
        updateById(event);
        return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", eventVo.getRemark()));
    }

    /**
     * ???????????????????????????
     */
    public ResultInfo editEvent(Event event, List<MultipartFile> files, List<MultipartFile> filesHandle) {
        if (null == event || null == event.getId()) {
            return ResultInfo.error("????????????!");
        }
        updateEventInfo(event, files, filesHandle);

        return ResultInfo.success("????????????", new LogVo(event.getId(), "??????", StringUtils.EMPTY));
    }

    /**
     * ????????????
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

        //???????????? ??????
        //???????????? ????????????????????????
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
        //???????????? ????????????????????????
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

        // ??????????????????,?????????,????????????
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

        // ??????????????????,?????????,????????????
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

        // ???????????????,?????????,????????????
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

        // ???????????????,?????????,????????????
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

        // ???????????????,?????????,????????????
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
     * ???????????? ????????????
     * <p>
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    public ResultInfo deleteEvent(EventVo eventVo) {
        if (null == eventVo || null == eventVo.getId()) {
            return ResultInfo.error("??????????????????!");
        }
        Event event = selectById(eventVo.getId());
        // ????????????,??????????????????????????????
        SysUser user = new SysUser();
        if (null != eventVo.getUserId()) {
            user = sysUserMapper.selectById(eventVo.getUserId());
        }else{
            user = sysUserService.getUser();
        }
        // sysUserService.getUser();

        //??????????????????
        List<EventAssets> eventAssetsList = eventAssetsMapper.selectList(Wrappers.<EventAssets>lambdaQuery()
                .eq(EventAssets::getEvent_id, event.getId()).eq(EventAssets::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssetsList)) {
            return ResultInfo.error("?????????????????????,??????????????????!");
        }
        List<EventRelation> eventRelationList = eventRelationMapper.selectList(Wrappers.<EventRelation>lambdaQuery()
                .eq(EventRelation::getParent_event_id, event.getId()).eq(EventRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventRelationList)) {
            return ResultInfo.error("?????????????????????,??????????????????!");
        }
        List<EventAssist> eventAssistList = eventAssistMapper.selectList(Wrappers.<EventAssist>lambdaQuery()
                .eq(EventAssist::getEvent_id, event.getId()).eq(EventAssist::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssistList)) {
            return ResultInfo.error("?????????????????????,??????????????????!");
        }
        List<EventQuestionRelation> questionRelations = questionRelationMapper.selectList(Wrappers.<EventQuestionRelation>lambdaQuery()
                .eq(EventQuestionRelation::getEvent_id, event.getId()).eq(EventQuestionRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(questionRelations)) {
            return ResultInfo.error("?????????????????????,??????????????????!");
        }
        List<EventChangeRelation> changeRelationList = changeRelationMapper.selectList(Wrappers.<EventChangeRelation>lambdaQuery()
                .eq(EventChangeRelation::getEvent_id, event.getId()).eq(EventChangeRelation::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(changeRelationList)) {
            return ResultInfo.error("?????????????????????,??????????????????!");
        }

        event.setUpdate_time(new Date());
        event.setUpdate_user(user.getId());
        event.setIs_del(String.valueOf(1));

        updateById(event);
        if(null != eventVo.getUserId()){
            return ResultInfo.success("????????????!", new LogVo(eventVo.getUserId(), event.getId(), "???????????????", ""));
        }

        return ResultInfo.success();
    }

    /**
     * ????????????
     */
    public ResultInfo revokeEvent(EventVo eventVo) {
        if (null == eventVo || null == eventVo.getId()) {
            return ResultInfo.error("????????????");
        }

        Event event = selectById(eventVo.getId());
        if (EventStatusEnums.CLOSED.getType().equals(event.getStatus())) {
            return ResultInfo.error("?????????????????????");
        }

        event.setUpdate_user(sysUserService.getUser().getId());
        event.setUpdate_time(new Date());
        event.setStatus(EventStatusEnums.REVOKED.getType());
        return ResultInfo.success(updateById(event));
    }

    /**
     * ???????????? ??????????????????
     */
    public ResultInfo closingBatch(EventVo eventVo) {
        if (CollectionUtils.isEmpty(eventVo.getIds())) {
            return ResultInfo.error("????????????");
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
            return ResultInfo.success("????????????");
        }
        return ResultInfo.error("????????????");
    }

    /**
     * ??????id??????????????????,??????????????????
     */
    public ResultInfo getById(Integer id) {
        if (null == id) {
            return ResultInfo.error("????????????");
        }
        Map<Integer, SysUser> users = getUserNameMap();
        if (CollectionUtils.isEmpty(users)) {
            return ResultInfo.error("????????????");
        }
        Event event = selectById(id);
        //??????????????????
        event.setUser_id_name(null == users.get(event.getUser_id()) ? StringUtils.EMPTY : users.get(event.getUser_id()).getName());
        event.setUser_id_loginName(null == users.get(event.getUser_id()) ? StringUtils.EMPTY : users.get(event.getUser_id()).getUser_name());
        event.setCreate_user_name(null == users.get(event.getCreate_user()) ? StringUtils.EMPTY : users.get(event.getCreate_user()).getName());
        event.setHandler_name(null == users.get(event.getHandler()) ? StringUtils.EMPTY : users.get(event.getHandler()).getName());
        event.setAssigner_name(null == users.get(event.getAssigner()) ? StringUtils.EMPTY : users.get(event.getAssigner()).getName());
        event.setSolve_user_name(null == users.get(event.getSolve_user_id()) ? StringUtils.EMPTY : users.get(event.getSolve_user_id()).getName());
        event.setClose_user_name(null == users.get(event.getClose_users()) ? StringUtils.EMPTY : users.get(event.getClose_users()).getName());
        event.setResponse_user_name(null == users.get(event.getResponse_user()) ? StringUtils.EMPTY : users.get(event.getResponse_user()).getName());
        //????????????
        Map<Integer, SchemeClassification> schemeClassificationMap = getProgrammeTypeMap();
        if (Base.notEmpty(event.getProgramme_type())) {
            event.setProgramme_type_name(schemeClassificationMap.get(event.getProgramme_type()).getName());
        }
        // ????????????
        if (null != event.getSolve_type()) {
            EventSolutionType solutionType = eventSolutionTypeMapper.selectById(event.getSolve_type());
            if (null != solutionType) {
                event.setSolve_type_name(solutionType.getSolution_name());
            }
        }

        //????????????
        event.setDeptName(StringUtils.EMPTY);
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        if (!CollectionUtils.isEmpty(deptMap)) {
            event.setDeptName(null == deptMap.get(event.getDepartment()) ? StringUtils.EMPTY : deptMap.get(event.getDepartment()).getName());
        }
        //??????????????????
        Map<Integer, EventServiceManage> serviceMap = getServiceManage();
        if (!CollectionUtils.isEmpty(serviceMap)) {
            event.setService_name(null == serviceMap.get(event.getService_groups_id()) ? StringUtils.EMPTY : serviceMap.get(event.getService_groups_id()).getName());
        }
        //??????????????????
        Map<Integer, EventSource> sourceMap = getSourceMap();
        if (!CollectionUtils.isEmpty(sourceMap)) {
            event.setSource_name(null == sourceMap.get(event.getSource_id()) ? StringUtils.EMPTY : sourceMap.get(event.getSource_id()).getResource_name());
        }
        //????????????
        event.setStatusName(EventStatusEnums.getValue(event.getStatus()));
        //????????????
        Map<Integer, EventRequestType> requestMap = getRequestTypeMap();
        if (!CollectionUtils.isEmpty(requestMap)) {
            event.setRequest_type_name(null == requestMap.get(event.getRequest_type()) ? StringUtils.EMPTY : requestMap.get(event.getRequest_type()).getRequest_type_name());
        }
        //??????????????????
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        if (!CollectionUtils.isEmpty(categoryMap)) {
            event.setEvent_category_name(null == categoryMap.get(event.getEvent_category_id()) ? StringUtils.EMPTY : categoryMap.get(event.getEvent_category_id()).getName());
        }
        //?????????????????????
        Map<Integer, SlaEventFirst> firstMap = getEventFirstMap();
        if (!CollectionUtils.isEmpty(firstMap)) {
            event.setPriority_name(null == firstMap.get(event.getPriority_id()) ? StringUtils.EMPTY : firstMap.get(event.getPriority_id()).getFirst_name());
        }
        //???????????????
        Map<Integer, SlaEventUrgent> urgentMap = getUrgentMap();
        if (!CollectionUtils.isEmpty(urgentMap)) {
            event.setUrgency_degree_name(null == urgentMap.get(event.getUrgency_degree_id()) ? StringUtils.EMPTY : urgentMap.get(event.getUrgency_degree_id()).getUrgent_name());
        }
        //???????????????
        Map<Integer, SlaEventImpact> eventImpactMap = getEventImpactMap();
        if (!CollectionUtils.isEmpty(eventImpactMap)) {
            event.setEffect_degree_name(null == eventImpactMap.get(event.getEffect_degree()) ? StringUtils.EMPTY : eventImpactMap.get(event.getEffect_degree()).getImpact_name());
        }
        //??????????????????
        EventSolutionType solutionType = eventSolutionTypeService.selectById(event.getSolve_type());
        if (null != solutionType) {
            event.setSolve_type_name(solutionType.getSolution_name());
        }

        //?????????????????????
        List<EventAssist> eventAssists = eventAssistMapper.selectList(Wrappers.<EventAssist>lambdaQuery()
                .eq(EventAssist::getEvent_id, event.getId())
                .eq(EventAssist::getIs_del, String.valueOf(0)));
        if (!CollectionUtils.isEmpty(eventAssists)) {
            List<Integer> ids = eventAssists.stream().map(EventAssist::getUser_id).collect(Collectors.toList());
            SysUser sysUser = new SysUser();
            sysUser.setIds(ids);
            event.setEventAssistUsers(sysUserService.getList(sysUser));
        }
        //?????????????????????
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
        //??????????????????
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
        //??????????????????
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
        //??????????????????
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
        //??????SLA??????????????????
        EventTimes times = eventTimesService.selectById(event.getId());
        if (null == times) {
            times = new EventTimes();
        }
        event.setEventTimes(times);
        // ??????????????????
        List<SysLog> sysLogs = sysLogService.selectList(Wrappers.<SysLog>lambdaQuery().
                eq(SysLog::getBill_id, event.getId()).eq(SysLog::getModule, LogEnums.LOG_GZT.getValue())
                .orderByDesc(SysLog::getOperate_time)
        );
        if (!CollectionUtils.isEmpty(sysLogs)) {
            event.setSysLogs(sysLogs);
        }

        //  ??????
        List<EventFile> files = eventFileMapper.selectList(Wrappers.<EventFile>lambdaQuery()
                .eq(EventFile::getEvent_id, event.getId()).eq(EventFile::getType, 1).eq(EventFile::getIs_del, "0"));
        if (Base.notEmpty(files) && files.size() > 0) {
            //??????????????????
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

        //  ??????
        List<EventFile> files2 = eventFileMapper.selectList(Wrappers.<EventFile>lambdaQuery()
                .eq(EventFile::getEvent_id, event.getId()).eq(EventFile::getType, 2).eq(EventFile::getIs_del, "0"));
        if (Base.notEmpty(files2) && files2.size() > 0) {
            //??????????????????
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
     * ????????????
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
     * ???????????? - ????????????
     */
    public ResultInfo editEventById(Event event) {
        if (null == event || null == event.getId()) {
            return ResultInfo.error("????????????");
        }

        event.setUpdate_time(new Date());
        event.setUpdate_user(sysUserService.getUser().getId());
        // ??????????????????,?????????,????????????
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
     * ???????????? - ????????????
     */
    public ResultInfo propertyAssessmentList() {
        SysUser user = sysUserService.getUser();

        return null;
    }

    /**
     * ????????????
     */
    public ResultInfo evaluateUser(Event event) {
//        if (null == eventVo || null == eventVo.getId() || null == eventVo.getEventSatisfaction()) {
//            return ResultInfo.error("????????????");
//        }
//        EventSatisfaction bean = eventVo.getEventSatisfaction();
//        bean.setEvent_id(eventVo.getId());
//        bean.setCreate_user(sysUserService.getUser().getId());
//        bean.setCreate_time(new Date());
//        satisfactionMapper.insert(bean);
        Integer count = eventMapper.updateById(event);
        SysUser sysUser = sysUserService.getUser();
        if (count > 0) {
            return ResultInfo.success("????????????", new LogVo(event.getId(), "????????????", sysUser.getName() + "(" + sysUser.getUser_name() + ") ??????"));
        } else {
            return ResultInfo.error("????????????");
        }
    }

    /**
     * ???????????????
     */
    private Map<Integer, SlaEventImpact> getEventImpactMap() {
        List<SlaEventImpact> list = slaEventImpactMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SlaEventImpact::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ??????????????????
     */
    private Map<Integer, SchemeClassification> getProgrammeTypeMap() {
        List<SchemeClassification> list = schemeClassificationMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SchemeClassification::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ??????????????????
     */
    private Map<Integer, EventRequestType> getRequestTypeMap() {
        List<EventRequestType> list = requestTypeMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(EventRequestType::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ???????????????
     */
    private Map<Integer, SlaEventFirst> getEventFirstMap() {
        List<SlaEventFirst> list = eventFirstMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SlaEventFirst::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ???????????????
     */
    private Map<Integer, SlaEventUrgent> getUrgentMap() {
        List<SlaEventUrgent> list = urgentMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(SlaEventUrgent::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ????????????
     */
    private Map<Integer, EventCategory> getCategoryMap() {
        List<EventCategory> list = eventCategoryMapper.selectList(null);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(EventCategory::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ??????????????????
     */
    private Map<Integer, EventSource> getSourceMap() {
        List<EventSource> sourceList = eventSourceMapper.selectList(null);
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyMap();
        }
        return sourceList.stream().collect(Collectors.toMap(EventSource::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ???????????????key,value
     */
    private Map<Integer, SysDepartment> getDeptMap() {
        List<SysDepartment> departmentList = sysDepartmentMapper.selectList(null);
        if (CollectionUtils.isEmpty(departmentList)) {
            return Collections.emptyMap();
        }
        return departmentList.stream().collect(Collectors.toMap(SysDepartment::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ?????????????????????key,value
     */
    private Map<Integer, EventServiceManage> getServiceManage() {
        List<EventServiceManage> serviceManageList = eventServiceManageMapper.selectList(null);
        if (CollectionUtils.isEmpty(serviceManageList)) {
            return Collections.emptyMap();
        }
        return serviceManageList.stream().collect(Collectors.toMap(EventServiceManage::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ????????????????????????Map<id,SysUser>
     */
    public Map<Integer, SysUser> getUserNameMap() {
        List<SysUser> users = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIs_del, "0"));
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity(), (key1, key2) -> key2));
    }


    /**
     * ?????????????????????
     */
    public PageInfo<Event> getAllByPage(Event event, Integer pageIndex, Integer pageSize) {
        // ????????????
        long startTime = System.currentTimeMillis();

        // ????????????id??????????????????
        List<Integer> serviceGroups = new ArrayList<>();
        QueryWrapper<EventBillingDesignate> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("engineer_userid", event.getWX_userId()==null?sysUserService.getUser().getId():event.getWX_userId());
        queryWrapper3.eq("is_del", "0");
        queryWrapper3.isNotNull("service_management_id");
        List<EventBillingDesignate> groups = eventBillingDesignateService.selectList(queryWrapper3);
        groups.forEach(x -> {
            serviceGroups.add(x.getService_management_id());
        });
        // ????????????????????????????????????????????????
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
                // ???????????????????????????
                // and (service_group_id in (1,2,3) or create_user_id = 1)
                .and(null == event.getUser_id(), i -> i.in(Event::getService_groups_id, serviceGroups).or().eq(Event::getCreate_user, event.getWX_userId()==null?sysUserService.getUser().getId():event.getWX_userId()))
                .eq(Event::getIs_del, String.valueOf(0))
                // ????????????id
                .eq(null != event.getId(), Event::getId, event.getId())
                // ?????????????????????
                .eq(null != event.getSolve_user_id(), Event::getSolve_user_id, event.getSolve_user_id())
                // ?????????????????? ?????????
                .like(StringUtils.isNotBlank(event.getTitle()), Event::getTitle, event.getTitle())
                // ?????????????????? id
                .eq(null != event.getId(), Event::getId, event.getId())
                // ?????????????????? ??????
                .eq(StringUtils.isNotBlank(event.getStatus()), Event::getStatus, event.getStatus())
                // ?????????????????? ??????id
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // ?????????????????? ?????????
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // ?????????????????? ?????????
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // ?????????????????? ?????????
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // ?????????????????? ??????
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // ?????????????????? ??????
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // ?????????????????? ????????????
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // ?????????????????? ?????????
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // ?????????????????? ??????????????????
                .ge(null != event.getCreate_time_start(), Event::getCreate_time, event.getCreate_time_start())
                // ?????????????????? ?????????????????? +1 ???
                .le(null != newEndTime, Event::getCreate_time, newEndTime)
                // ?????????????????? ????????????
                .between(null != event.getCreate_time() && null != event.getCreate_time_end(), Event::getCreate_time, event.getCreate_time(), event.getCreate_time_end())
                // ?????????????????? ????????????
                .eq(null != event.getService_groups_id(), Event::getService_groups_id, event.getService_groups_id())
                // ?????????????????? ????????????
                .eq(null != event.getRequest_type(), Event::getRequest_type, event.getRequest_type())
                // ?????????????????? ???????????????
                .eq(null != event.getEvent_satisfaction(), Event::getEvent_satisfaction, event.getEvent_satisfaction())
                // ?????????????????? ????????????
                .eq(null != event.getProgramme_type(), Event::getProgramme_type, event.getProgramme_type())
                // ?????????????????? ????????????
                .eq(null != event.getSolve_type(), Event::getSolve_type, event.getSolve_type())
                // ?????????????????? ????????????
                .ge(StringUtils.isNotBlank(event.getExpenses_start()), Event::getExpenses, event.getExpenses_start())
                // ?????????????????? ????????????
                .le(StringUtils.isNotBlank(event.getExpenses_end()), Event::getExpenses, event.getExpenses_end())
                // ?????????????????? ??????sla
                .eq(StringUtils.isNotBlank(event.getMeetResponseSLA()), Event::getMeetResponseSLA, event.getMeetResponseSLA())
                //????????????
                .in(!CollectionUtils.isEmpty(event.getStatusList()),Event::getStatus , event.getStatusList())
        );
        // ????????????
        long endTime = System.currentTimeMillis();
        log.info("???????????????{}", endTime - startTime);
        // ????????????map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // ??????????????????map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // ???????????????map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        // ?????????????????????id
        Integer currentUserId = event.getWX_userId()==null?sysUserService.getUser().getId():event.getWX_userId();
        // ??????Priority_id?????????
        List<Integer> priorityIds = eventList.stream().map(Event::getPriority_id).collect(Collectors.toList());
        Map<Integer, String> slaEventFirstMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(priorityIds)) {
            List<SlaEventFirst> slaEventFirsts = slaEventFirstMapper.selectList(Wrappers.<SlaEventFirst>lambdaQuery().in(SlaEventFirst::getId, priorityIds));
            // ?????????map??????,key???id,value???first_name
            slaEventFirstMap = slaEventFirsts.stream().collect(Collectors.toMap(SlaEventFirst::getId, SlaEventFirst::getFirst_name, (key1, key2) -> key2));
        }
        // ??????????????????????????????
        long endTime3 = System.currentTimeMillis();
        log.info("?????????????????????{}", endTime3 - endTime);
        for (Event member : eventList) {
            // ??????????????????
            long fortime1 = System.currentTimeMillis();
            // ????????????
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // ???????????????
            member.setPriority_name("");
            if (null != member.getPriority_id() && !CollectionUtils.isEmpty(priorityIds)) {
                member.setPriority_name(slaEventFirstMap.get(member.getPriority_id()));
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // ????????????
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // ???????????????
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // ???????????????
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // ??????????????????
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // ???????????????
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // ??????????????????
            member.setSurplusMinutes(getSurplusMinutes(member));
            // ???????????????????????????????????????
            member.setIsHandler(currentUserId.equals(member.getHandler()));
            // ???????????????????????????????????????
            member.setIsCreateUser(currentUserId.equals(member.getCreate_user()));
            // ??????????????????
            long fortime2 = System.currentTimeMillis();
            log.info("?????????????????????{}", fortime2 - fortime1);
        }
        // ????????????
        long end = System.currentTimeMillis();
        // ????????????
        log.info("???????????????????????????{}ms", end - startTime);
        return new PageInfo<>(eventList);
    }

    /**
     * pagehelper????????????
     *
     * @param currentPage ?????????
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
     * ??????????????????
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
                // ??????????????????
                xctime = eventTimes.getResponse_times();
            } else {
                time = slaEventConfig.getSolve_time();
//                xctime = null == temp.getResponseTime()?0:getTimeDifference(now, temp.getResponseTime());
                xctime = eventTimes.getHandle_times();
            }
            // string ??????long
            long timeLong = Long.parseLong(time);
            // ??????timeLong???xctime?????????
            long diff = timeLong - xctime;
            // ??????????????????0???????????????,diff????????????
            if (diff < 0) {
                diff = -diff;
                String tempTime = "";
                // diff ?????????, ????????????60??????,tempTime?????????????????????,??????24??????,tempTime???????????????
                if (diff > 60 * 24) {
                    long day = diff / (60 * 24);
                    long hours = diff % (60 * 24) / 60;
                    long minutes = diff % (60 * 24) % 60;
                    String hoursStr = hours < 1 ? "" : hours + "??????";
                    tempTime = day + "???" + hoursStr + minutes + "??????";
                } else if (diff > 60) {
                    tempTime = diff / 60 + "??????" + diff % 60 + "??????";
                } else {
                    tempTime = diff + "??????";
                }
                return "??????: " + tempTime;
            } else {
                return "??????: " + diff + "??????";
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * ?????????????????????,???????????????(int)
     */

    private long getTimeDifference(Date now, Date createTime) {
        long diff = now.getTime() - createTime.getTime();
        long minutes = diff / (1000 * 60);
        return minutes;
    }


    /**
     * ??????????????????
     *
     * @param event_id
     * @return
     */
    public  SlaEventConfig getSlaEventConfig(Integer event_id) {
        //?????????????????????SLA
        List<SlaEvent> slaByEventCategory = slaEventService.getSlaByEventCategory(null);
        HashMap<Integer, SlaEvent> slaMap = new HashMap();
        slaByEventCategory.forEach(x -> slaMap.put(x.getEventCategoryId(), x));
        //????????????????????????????????????
        Event event = eventMapper.selectById(event_id);
        //?????????????????????????????????SLA
        if (null == event) {
            return null;
        }
        SlaEvent slaEvent = slaMap.get(event.getEvent_category_id());
        if (slaEvent == null) {
            return null;
        }
        //????????????SLA??????
        QueryWrapper<SlaEventConfig> qw = new QueryWrapper<>();
        qw.eq("sla_event_id", slaEvent.getId());
        qw.eq("is_del", "0");
        if ("1".equals(slaEvent.getIs_first())) {
            //???????????????
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
     * ??????????????????
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    public PageInfo<Event> toMyGroupList(Integer pageIndex, Integer pageSize, EventVo beanParam) {
        // ????????????id??????????????????
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
     * ????????????
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    public PageInfo<Event> toMyParticipationList(Integer pageIndex, Integer pageSize, EventVo beanParam) {
        Integer user = beanParam.getCurrent_user() == null ? sysUserService.getUser().getId() : beanParam.getCurrent_user();
        beanParam.setCreate_user(user);//?????????
        beanParam.setSolve_user_id(user); //???????????????
        beanParam.setHandler(user);  //?????????

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
     * ??????????????????
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
     * ????????????
     *
     * @param pageIndex
     * @param pageSize
     * @param event
     * @return
     */
    public PageInfo<Event> toXieChaList(Integer pageIndex, Integer pageSize, EventVo event) {
        SysUser user = sysUserService.getUser();
        //?????????????????????????????????id
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
                // ?????????
                .like(StringUtils.isNotBlank(event.getKeywords()), Event::getTitle, event.getKeywords())
                // ??????
                .like(null != event.getId(), Event::getId, event.getId())
                // ??????
                .eq(StringUtils.isNotBlank(event.getStatus()), Event::getStatus, event.getStatus())
                // ??????
                .eq(null != event.getUser_id(), Event::getUser_id, event.getUser_id())
                // ?????????
                .eq(null != event.getCreate_user(), Event::getCreate_user, event.getCreate_user())
                // ?????????
                .eq(null != event.getHandler(), Event::getHandler, event.getHandler())
                // ?????????
                .eq(null != event.getResponse_user(), Event::getResponse_user, event.getResponse_user())
                // ??????
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // ????????????
                .eq(null != event.getSource_id(), Event::getSource_id, event.getSource_id())
                // ??????
                .eq(null != event.getDepartment(), Event::getDepartment, event.getDepartment())
                // ????????????
                .eq(null != event.getEvent_category_id(), Event::getEvent_category_id, event.getEvent_category_id())
                // ?????????
                .eq(null != event.getPriority_id(), Event::getPriority_id, event.getPriority_id())
                // ????????????
                .ge(StringUtils.isNotBlank(event.getCreate_start_time()), Event::getCreate_time, event.getCreate_start_time())
                .le(StringUtils.isNotBlank(event.getCreate_end_time()), Event::getCreate_time, event.getCreate_end_time())
                // ??????
                .like(StringUtils.isNotBlank(event.getExpenses()), Event::getExpenses, event.getExpenses())

                .eq(Event::getIs_del, "0")
        );
        // ????????????map
        Map<Integer, SysUser> userMap = getUserNameMap();
        // ??????????????????map
        Map<Integer, EventCategory> categoryMap = getCategoryMap();
        // ???????????????map
        Map<Integer, EventServiceManage> groupMap = getServiceManage();
        for (Event member : list) {
            // ????????????
            member.setStatusName(EventStatusEnums.getValue(member.getStatus()));
            // ???????????????
            member.setPriority_name("");
            if (null != member.getPriority_id() && null != slaEventFirstMapper.selectById(member.getPriority_id())) {
                member.setPriority_name(slaEventFirstMapper.selectById(member.getPriority_id()).getFirst_name());
            }
            if (!CollectionUtils.isEmpty(userMap)) {
                // ????????????
                member.setUser_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                member.setUser_id_name(null == userMap.get(member.getUser_id()) ? "" : userMap.get(member.getUser_id()).getName());
                // ???????????????
                member.setCreate_user_name(null == userMap.get(member.getCreate_user()) ? "" : userMap.get(member.getCreate_user()).getName());
                // ???????????????
                member.setHandler_name(null == userMap.get(member.getHandler()) ? "" : userMap.get(member.getHandler()).getName());
            }
            // ??????????????????
            if (!CollectionUtils.isEmpty(categoryMap)) {
                member.setCategory_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
                member.setEvent_category_name(null == categoryMap.get(member.getEvent_category_id()) ? "" : categoryMap.get(member.getEvent_category_id()).getName());
            }
            // ???????????????
            if (!CollectionUtils.isEmpty(groupMap)) {
                member.setService_name(null == groupMap.get(member.getService_groups_id()) ? "" : groupMap.get(member.getService_groups_id()).getName());
            }
            // ??????????????????
            member.setSurplusMinutes(getSurplusMinutes(member));

        }

        return new PageInfo<>(list);
    }


    //????????????
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

    //??????????????????
    public ResultInfo saveWantToBill(EventVo eventVo, List<MultipartFile> files) {
        //??????????????????
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
            event.setUser_type(1);//????????????
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
            //??????operate_node
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
        //??????ID
        EventTimes times = new EventTimes();
        times.setEvent_id(event.getId());//??????ID
        times.setResponse_times(0);
        times.setResponse_exceed_times(0);
        times.setHandle_times(0);
        times.setHandle_exceed_times(0);
        times.setIs_del("0");
        eventTimesService.insert(times);

        if (null == eventVo.getUserId()){
            return ResultInfo.success("????????????!", new LogVo(event.getId(), "????????????", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }else {
            return ResultInfo.success("????????????!", new LogVo(eventVo.getUserId(),event.getId(), "????????????", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }


    }

    //??????
    public ResultInfo updateWantToBill(EventVo eventVo, List<MultipartFile> files) {
        //??????????????????
        Event event = selectById(eventVo.getId());
        BeanUtils.copyProperties(eventVo, event);
        event.setRequest_type(Integer.valueOf(eventVo.getRequest_type()));
        event.setEffect_degree(Integer.valueOf(eventVo.getEffect_degree()));
        event.setUpdate_time(new Date());
        event.setUpdate_user(null == eventVo.getUserId() ? sysUserService.getUser().getId() : eventVo.getUserId());
        eventMapper.updateById(event);

        if (!CollectionUtils.isEmpty(eventVo.getEventAssets1())) {
            //???????????????
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
        //???????????? ????????????????????????
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
            return ResultInfo.success("????????????!", new LogVo(event.getId(), "????????????", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }else {
            return ResultInfo.success("????????????!", new LogVo(eventVo.getUserId(),event.getId(), "????????????", com.jsdc.itss.common.utils.StringUtils.EMPTY));
        }

    }

    /**
     *  ????????????
     * @param start_time
     * @param end_time
     * @return
     */
    public Map statistics(String start_time, String end_time) {
        return eventMapper.statistics(start_time,end_time);
    }

    public List<SysUser> getSynergiaUsers(Integer id) {
        Event event = eventMapper.selectById(id);
        //???????????????
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
