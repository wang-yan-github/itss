package com.jsdc.itss.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.service.*;
import com.jsdc.itss.utils.DateUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.QuestionVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: IndexController
 * Description:
 * date: 2022/10/31 9:53
 *
 * @author wy
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventAssistMapper eventAssistMapper;
    @Autowired
    private EventBillingDesignateService eventBillingDesignateService;
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private ChangeInfoService changeInfoService;
    @Autowired
    private PublishBasicInfoService publishBasicInfoService;
    @Autowired
    private KnowledgeInfoService knowledgeInfoService;
    @Autowired
    private AssetsSoftwareLicenceService assetsSoftwareLicenceService;
    @Autowired
    private AssetsCustomerContractMapper assetsCustomerContractMapper;
    @Autowired
    private AssetsSupplierContractMapper assetsSupplierContractMapper;
    @Autowired
    private SysAnnouncementSeeMapper sysAnnouncementSeeMapper;
    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;


    @RequestMapping(value = "statistics.do", method = RequestMethod.POST)
    public ResultInfo statistics(String start_time, String end_time) {
        SysUser user = sysUserService.getUser();
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

        // 得到当前用户的协查事件id
        List<EventAssist> eventIdList = eventAssistMapper.selectList(Wrappers.<EventAssist>lambdaQuery().eq(EventAssist::getUser_id, user.getId()).eq(EventAssist::getIs_del, "0"));

        // 已超时事件
        Long overtime = eventService.selectCount(Wrappers.<Event>lambdaQuery()
                .eq(Event::getIs_del, GlobalData.ISDEL_NO)
                .eq(Event::getMeetResponseSLA, GlobalData.IS_ENABLE_NO)
                .eq(Event::getMeetProcessingSLAs, GlobalData.IS_ENABLE_NO)
                .ge(StringUtils.isNotEmpty(start_time), Event::getCreate_time, start_time + " 00:00:00")
                .le(StringUtils.isNotEmpty(end_time), Event::getCreate_time, end_time + " 23:59:59")
        );

        // 事件协查
        Long event_assist = eventMapper.selectCount(Wrappers.<Event>lambdaQuery().in(!eventIdList.isEmpty(), Event::getId, eventIdList.stream().map(EventAssist::getEvent_id).collect(Collectors.toList()))
                .ge(StringUtils.isNotEmpty(start_time), Event::getCreate_time, start_time)
                .le(StringUtils.isNotEmpty(end_time), Event::getCreate_time, end_time));

        // 问题待办
        QuestionVo questionVo = new QuestionVo();
        questionVo.setNow_operator_user(user.getId());//当前操作人
        questionVo.setDataStatus("db");
        if (StringUtils.isNotEmpty(start_time)) {
            questionVo.setCreate_time_start(DateUtils.toStrDate(start_time + " 00:00:00"));
        }
        if (StringUtils.isNotEmpty(end_time)) {
            questionVo.setCreate_time_end(DateUtils.toStrDate(end_time + " 23:59:59"));
        }
        List<Question> questions = questionManageService.dbList(questionVo);

        // 变更待办
        ChangeVo changeVo = new ChangeVo();
        changeVo.setData_flag(GlobalData.DATAFLAG_1);
        if (StringUtils.isNotEmpty(start_time)) {
            changeVo.setCreate_start_time(DateUtils.toStrDate(start_time + " 00:00:00"));
        }
        if (StringUtils.isNotEmpty(end_time)) {
            changeVo.setCreate_end_time(DateUtils.toStrDate(end_time + " 23:59:59"));
        }
        List<ChangeVo> changes = changeInfoService.toNeedList(changeVo);

        // 发布待办
        PublishBasicInfo publishBasicInfo = new PublishBasicInfo();
        if (StringUtils.isNotEmpty(start_time)) {
            publishBasicInfo.setCreate_time_start(start_time);
        }
        if (StringUtils.isNotEmpty(end_time)) {
            publishBasicInfo.setCreate_time_end(start_time);
        }
        Integer PublishBasics = publishBasicInfoService.getDbList(publishBasicInfo).size();
//        Long PublishBasics = publishBasicInfoService.selectCount(Wrappers.<PublishBasicInfo>lambdaQuery()
//                .eq(PublishBasicInfo::getIs_del, GlobalData.ISDEL_NO)
//                .in(PublishBasicInfo::getStatus, GlobalData.PUBLISH_STATUS_APPROVE, GlobalData.PUBLISH_STATUS_TEST, GlobalData.PUBLISH_STATUS_PUB)
//                .or(p -> {
//                    p.eq(PublishBasicInfo::getPublisher, user.getId().toString()).eq(PublishBasicInfo::getTester_id, user.getId().toString()).eq(PublishBasicInfo::getApprover, user.getId().toString());
//                })
//        );

        // 知识审核
        KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
        if (StringUtils.isNotEmpty(start_time)) {
            knowledgeInfo.setStartTime(start_time);
        }
        if (StringUtils.isNotEmpty(end_time)) {
            knowledgeInfo.setEndTime(end_time);
        }
        Integer knowledges = knowledgeInfoService.count(knowledgeInfo);

        // 软件许可证到期
        Long assetsSoftwareLicences = assetsSoftwareLicenceService.selectCount(Wrappers.<AssetsSoftwareLicence>lambdaQuery().eq(AssetsSoftwareLicence::getIs_del, "0").eq(AssetsSoftwareLicence::getIs_limit, "1")
                .le(AssetsSoftwareLicence::getEnd_time, DateUtil.today())
                .ge(StringUtils.isNotEmpty(start_time), AssetsSoftwareLicence::getCreate_time, start_time + " 00:00:00")
                .le(StringUtils.isNotEmpty(end_time), AssetsSoftwareLicence::getCreate_time, end_time + " 23:59:59")
        );

        // 客户合同到期
        Long assetsCustomerContracts = assetsCustomerContractMapper.selectCount(Wrappers.<AssetsCustomerContract>lambdaQuery().eq(AssetsCustomerContract::getIs_del, "0")
                .le(AssetsCustomerContract::getEnd_time, DateUtil.today())
                .ge(StringUtils.isNotEmpty(start_time), AssetsCustomerContract::getCreate_time, start_time + " 00:00:00")
                .le(StringUtils.isNotEmpty(end_time), AssetsCustomerContract::getCreate_time, end_time + " 23:59:59")
        );

        // 供应商合同到期
        Long assetsSupplierContracts = assetsSupplierContractMapper.selectCount(Wrappers.<AssetsSupplierContract>lambdaQuery().eq(AssetsSupplierContract::getIs_del, "0")
                .le(AssetsSupplierContract::getEnd_time, DateUtil.today())
                .ge(StringUtils.isNotEmpty(start_time), AssetsSupplierContract::getCreate_time, start_time + " 00:00:00")
                .le(StringUtils.isNotEmpty(end_time), AssetsSupplierContract::getCreate_time, end_time + " 23:59:59")
        );

        Map map = new HashMap();
        map.put("overtime", overtime);
        map.put("event_assist", event_assist);
        map.put("questions", questions.size());
        map.put("changes", changes.size());
        map.put("PublishBasics", PublishBasics);
        map.put("knowledges", knowledges);
        map.put("assetsSoftwareLicences", assetsSoftwareLicences);
        map.put("assetsCustomerContracts", assetsCustomerContracts);
        map.put("assetsSupplierContracts", assetsSupplierContracts);
        return ResultInfo.success(map);
    }

    @RequestMapping(value = "eventStatistics.do", method = RequestMethod.POST)
    public ResultInfo eventStatistics(String start_time, String end_time) {
        Map map = new HashMap();
        map.put("event", eventService.statistics(start_time, end_time));
        return ResultInfo.success(map);
    }

    @RequestMapping(value = "getLogs.do", method = RequestMethod.POST)
    public ResultInfo getLogs(String start_time, String end_time) {
        List<SysLog> sysLogs = sysLogService.selectList(Wrappers.<SysLog>lambdaQuery()
                .eq(SysLog::getUser_id, sysUserService.getUser().getId())
                        .ge(StringUtils.isNotEmpty(start_time), SysLog::getOperate_time, start_time + " 00:00:00")
                        .le(StringUtils.isNotEmpty(end_time), SysLog::getOperate_time, end_time + " 23:59:59")
                        .orderByDesc(SysLog::getOperate_time)
                        .last("limit 0,50")
        );
        for (SysLog sysLog : sysLogs) {
            if (StringUtils.isNotEmpty(sysLog.getModule())) {
                switch (sysLog.getModule()) {
                    case "1":
                        sysLog.setModule(LogEnums.LOG_EVENT.getDesc());
                        break;
                    case "2":
                        sysLog.setModule(LogEnums.LOG_QUESTION.getDesc());
                        break;
                    case "3":
                        sysLog.setModule(LogEnums.LOG_CHANGE.getDesc());
                        break;
                    case "4":
                        sysLog.setModule(LogEnums.LOG_PUBLISH.getDesc());
                        break;
                    case "5":
                        sysLog.setModule(LogEnums.LOG_LOGOUT.getDesc());
                        break;
                    case "6":
                        sysLog.setModule(LogEnums.LOG_GZT.getDesc());
                        break;
                    case "7":
                        sysLog.setModule(LogEnums.LOG_Schedule.getDesc());
                        break;
                    case "8":
                        sysLog.setModule(LogEnums.LOG_APP_QUESTION.getDesc());
                        break;
                }
            }
        }
        return ResultInfo.success(sysLogs);
    }

    @RequestMapping(value = "getNotices.do", method = RequestMethod.POST)
    public ResultInfo getNotices(String start_time, String end_time) {
        // 得到当前登录人
        SysUser loginUser = sysUserService.getUser();

        List<SysAnnouncementSee> sysAnnouncementSees = sysAnnouncementSeeMapper.selectList(Wrappers.<SysAnnouncementSee>lambdaQuery().eq(SysAnnouncementSee::getIs_del, "0")
                // 判断如果visual_type = 0 则是用户,匹配用户id,如果是1 则是角色,匹配角色id,如果是2 则是部门,匹配部门id
                .and(i -> i.eq(SysAnnouncementSee::getVisual_type, "0").eq(SysAnnouncementSee::getVisual_id, loginUser.getId())
                        .or().eq(SysAnnouncementSee::getVisual_type, "1").inSql(SysAnnouncementSee::getVisual_id, "select role_id from sys_user_role where user_id = " + loginUser.getId())
                        .or().eq(SysAnnouncementSee::getVisual_type, "2").eq(SysAnnouncementSee::getVisual_id, loginUser.getDepartment_id())
                )
        );
        if (CollectionUtils.isEmpty(sysAnnouncementSees)) {
            return ResultInfo.success();
        }
        List<SysAnnouncement> list = sysAnnouncementMapper.selectList(Wrappers.<SysAnnouncement>lambdaQuery()
                .eq(SysAnnouncement::getIs_del, GlobalData.ISDEL_NO)
                .eq(SysAnnouncement::getIs_use, GlobalData.IS_ENABLE_YES)
                .ge(com.jsdc.itss.utils.StringUtils.isNotEmpty(start_time), SysAnnouncement::getCreate_time, start_time + " 00:00:00")
                .le(com.jsdc.itss.utils.StringUtils.isNotEmpty(end_time), SysAnnouncement::getCreate_time, end_time + " 23:59:59")
                // 可见范围 0 公开  1 指定人员 角色 部门  scope
                // 判断 scope 是否为1, 如果是1,则需要判断是否在可见范围内
                .and(i -> i.eq(SysAnnouncement::getScope, "0")
                        .or()
                        .eq(SysAnnouncement::getScope, "1").in(SysAnnouncement::getId, sysAnnouncementSees.stream().map(SysAnnouncementSee::getAnnouncement_id).distinct().collect(Collectors.toList()))
                )
                .orderByDesc(SysAnnouncement::getCreate_time)
                .last("limit 0,10")
        );
        return ResultInfo.success(list);
    }

}
