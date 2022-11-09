package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.ChangeInfoDao;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.mapper.ChangeInfoMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.ChangesOf;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.utils.*;
import com.jsdc.itss.vo.*;
import joptsimple.internal.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: ChangeInfoService
 * Description:
 * date: 2022/3/14 9:26
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeInfoService extends BaseService<ChangeInfoDao, ChangeInfo> {

    @Autowired
    ChangeInfoMapper changeInfoMapper;
    @Autowired
    ChangeNodesService changeNodesService;
    @Autowired
    ChangeImplementService changeImplementService;
    @Autowired
    ChangeImplementApproveService changeImplementApproveService;
    @Autowired
    ChangeEventService changeEventService;
    @Autowired
    ChangeQuestionService changeQuestionService;
    @Autowired
    ChangeAssetsService changeAssetsService;
    @Autowired
    ChangeProgrammeDraftService changeProgrammeDraftService;
    @Autowired
    ChangeProgrammeApproveService changeProgrammeApproveService;
    @Autowired
    ChangeCloseService changeCloseService;
    @Autowired
    SysDictService sysDictService;
    @Autowired
    ChangeTypeService changeTypeService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    EventService eventService;
    @Autowired
    AssetsManageService assetsManageService;
    @Autowired
    ChangeSourceService changeSourceService;
    @Autowired
    FileManageService fileManageService;
    @Autowired
    ChangeFileService changeFileService;
    @Autowired
    SysLogService sysLogService;


    @Autowired
    private QuestionManageService questionManageService;

    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 公共
     * 关联变更页面数据回显
     *
     * @author thr
     */
    public List<ChangeVo> getList(ChangeVo changeVo) {
        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();

        List<ChangeVo> voList = changeInfoMapper.toList(changeVo);

        voList.forEach(x -> {
            x.setCreate_user_uname(questionManageService.getUserName(sysUserMap, x.getCreate_user()));
            x.setCreate_user_name(questionManageService.getName(sysUserMap, x.getCreate_user()));
            x.setOperate_name(questionManageService.getName(sysUserMap, x.getOperate_user()));
            x.setStatus_name(sysDictService.getDict("change_status", x.getStatus()).getLabel());
            x.setType_name(sysDictService.getDict("change_type", x.getChange_type()).getLabel());
            // 审批节点
            x.setChangeNodes(changeNodesService.selectList(Wrappers.<ChangeNodes>lambdaQuery()
                    .eq(ChangeNodes::getChange_id, x.getId()).eq(ChangeNodes::getIs_del, "0")));
            // 方案拟定
            x.setChangeProgrammeDraft(changeProgrammeDraftService.selectOne(Wrappers.<ChangeProgrammeDraft>lambdaQuery()
                    .eq(ChangeProgrammeDraft::getChange_id, x.getId()).eq(ChangeProgrammeDraft::getIs_del, "0")));
            // 方案拟定评审
            x.setChangeProgrammeApprove(changeProgrammeApproveService.selectOne(Wrappers.<ChangeProgrammeApprove>lambdaQuery().
                    eq(ChangeProgrammeApprove::getChange_id, x.getId()).eq(ChangeProgrammeApprove::getIs_del, "0")));


            //方案审核人
            x.setCpa_approve_name(x.getChangeProgrammeApprove() == null || x.getChangeProgrammeApprove().getApprove_user() == null ? "" :
                    questionManageService.getName(sysUserMap, x.getChangeProgrammeApprove().getApprove_user()));
            // 方案实施
            x.setChangeImplement(changeImplementService.selectOne(Wrappers.<ChangeImplement>lambdaQuery().
                    eq(ChangeImplement::getChange_id, x.getId()).eq(ChangeImplement::getIs_del, "0")));
            // 方案实施评审
            x.setChangeImplementApprove(changeImplementApproveService.selectOne(Wrappers.<ChangeImplementApprove>lambdaQuery().
                    eq(ChangeImplementApprove::getChange_id, x.getId()).eq(ChangeImplementApprove::getIs_del, "0")));
            // 关联事件
            x.setChangeEvents(changeEventService.selectList(Wrappers.<ChangeEvent>lambdaQuery().
                    eq(ChangeEvent::getChange_id, x.getId()).eq(ChangeEvent::getIs_del, "0")));
            // 关联问题
            x.setChangeQuestions(changeQuestionService.selectList(Wrappers.<ChangeQuestion>lambdaQuery().
                    eq(ChangeQuestion::getChange_id, x.getId()).eq(ChangeQuestion::getIs_del, "0")));
            // 关联配置
            x.setChangeAssets(changeAssetsService.selectList(Wrappers.<ChangeAssets>lambdaQuery().
                    eq(ChangeAssets::getChange_id, x.getId()).eq(ChangeAssets::getIs_del, "0")));
            // 关单
            x.setChangeClose(changeCloseService.selectOne(Wrappers.<ChangeClose>lambdaQuery().
                    eq(ChangeClose::getChange_id, x.getId()).eq(ChangeClose::getIs_del, "0")));

            //关单人
            x.setClose_user_name(x.getChangeClose() == null || x.getChangeClose().getClose_user() == null ? "" :
                    questionManageService.getName(sysUserMap, x.getChangeClose().getClose_user()));
        });

        return voList;
    }

    /**
     * 我的变更
     * 我参与的
     * 分页查询
     */
    public ResultInfo toList(Integer pageIndex, Integer pageSize, ChangeVo changeVo) {
        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();

        PageHelper.startPage(pageIndex, pageSize);

        if ("1".equals(changeVo.getData_flag())) {
            //我的变更
            if (null == changeVo.getOperate_user()) {
                changeVo.setCreate_user(sysUserService.getUser().getId());
            } else {
                changeVo.setCreate_user(changeVo.getOperate_user());
            }
        } else if ("3".equals(changeVo.getData_flag())) {
            //我参与的
            if (null == changeVo.getOperate_user()) {
                changeVo.setCanyu_user_id(sysUserService.getUser().getId());
            } else {
                changeVo.setCanyu_user_id(changeVo.getOperate_user());
            }
        }

        List<ChangeVo> voList = changeInfoMapper.toList(changeVo);

        voList.forEach(x -> {
            x.setCreate_user_uname(questionManageService.getUserName(sysUserMap, x.getCreate_user()));
            x.setCreate_user_name(questionManageService.getName(sysUserMap, x.getCreate_user()));
            x.setOperate_name(questionManageService.getName(sysUserMap, x.getOperate_user()));
            x.setStatus_name(sysDictService.getDict("change_status", x.getStatus()).getLabel());
            x.setType_name(sysDictService.getDict("change_type", x.getChange_type()).getLabel());
            // 审批节点
//            x.setChangeNodes(changeNodesService.selectList(Wrappers.<ChangeNodes>lambdaQuery()
//                    .eq(ChangeNodes::getChange_id, x.getId()).eq(ChangeNodes::getIs_del, "0")));
            // 方案拟定
//            x.setChangeProgrammeDraft(changeProgrammeDraftService.selectOne(Wrappers.<ChangeProgrammeDraft>lambdaQuery()
//                    .eq(ChangeProgrammeDraft::getChange_id, x.getId()).eq(ChangeProgrammeDraft::getIs_del, "0")));
            // 方案拟定评审
//            x.setChangeProgrammeApprove(changeProgrammeApproveService.selectOne(Wrappers.<ChangeProgrammeApprove>lambdaQuery().
//                    eq(ChangeProgrammeApprove::getChange_id, x.getId()).eq(ChangeProgrammeApprove::getIs_del, "0")));


            //方案审核人
//            x.setCpa_approve_name(x.getChangeProgrammeApprove() == null || x.getChangeProgrammeApprove().getApprove_user() == null ? "" :
//                    questionManageService.getName(sysUserMap, x.getChangeProgrammeApprove().getApprove_user()));
//            // 方案实施
//            x.setChangeImplement(changeImplementService.selectOne(Wrappers.<ChangeImplement>lambdaQuery().
//                    eq(ChangeImplement::getChange_id, x.getId()).eq(ChangeImplement::getIs_del, "0")));
//            // 方案实施评审
//            x.setChangeImplementApprove(changeImplementApproveService.selectOne(Wrappers.<ChangeImplementApprove>lambdaQuery().
//                    eq(ChangeImplementApprove::getChange_id, x.getId()).eq(ChangeImplementApprove::getIs_del, "0")));
//            // 关联事件
//            x.setChangeEvents(changeEventService.selectList(Wrappers.<ChangeEvent>lambdaQuery().
//                    eq(ChangeEvent::getChange_id, x.getId()).eq(ChangeEvent::getIs_del, "0")));
//            // 关联问题
//            x.setChangeQuestions(changeQuestionService.selectList(Wrappers.<ChangeQuestion>lambdaQuery().
//                    eq(ChangeQuestion::getChange_id, x.getId()).eq(ChangeQuestion::getIs_del, "0")));
//            // 关联配置
//            x.setChangeAssets(changeAssetsService.selectList(Wrappers.<ChangeAssets>lambdaQuery().
//                    eq(ChangeAssets::getChange_id, x.getId()).eq(ChangeAssets::getIs_del, "0")));
//            // 关单
//            x.setChangeClose(changeCloseService.selectOne(Wrappers.<ChangeClose>lambdaQuery().
//                    eq(ChangeClose::getChange_id, x.getId()).eq(ChangeClose::getIs_del, "0")));
//
//            //关单人
//            x.setClose_user_name(x.getChangeClose() == null || x.getChangeClose().getClose_user() == null ? "" :
//                    questionManageService.getName(sysUserMap, x.getChangeClose().getClose_user()));
        });

        PageInfo<ChangeVo> pageInfo = new PageInfo<>(voList);

        return ResultInfo.success(pageInfo);
    }

    public List<ChangeVo> toList(ChangeVo changeVo) {
        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();

        if ("1".equals(changeVo.getData_flag())) {
            //我的变更
            if (null == changeVo.getOperate_user()) {
                changeVo.setCreate_user(sysUserService.getUser().getId());
            } else {
                changeVo.setCreate_user(changeVo.getOperate_user());
            }
        } else if ("3".equals(changeVo.getData_flag())) {
            //我参与的
            if (null == changeVo.getOperate_user()) {
                changeVo.setCanyu_user_id(sysUserService.getUser().getId());
            } else {
                changeVo.setCanyu_user_id(changeVo.getOperate_user());
            }
        }

        List<ChangeVo> voList = changeInfoMapper.toList(changeVo);

        voList.forEach(x -> {
            x.setCreate_user_uname(questionManageService.getUserName(sysUserMap, x.getCreate_user()));
            x.setCreate_user_name(questionManageService.getName(sysUserMap, x.getCreate_user()));
            x.setOperate_name(questionManageService.getName(sysUserMap, x.getOperate_user()));
            x.setStatus_name(sysDictService.getDict("change_status", x.getStatus()).getLabel());
            x.setType_name(sysDictService.getDict("change_type", x.getChange_type()).getLabel());
        });
        return voList;
    }



    public ResultInfo toNeedList(Integer pageIndex, Integer pageSize, ChangeVo changeVo) {

        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();

        PageHelper.startPage(pageIndex, pageSize);
        if (null == changeVo.getUserId()) {
            changeVo.setHandler_user(sysUserService.getUser().getId());
        } else {
            changeVo.setHandler_user(changeVo.getUserId());
        }
        String sl = "('2','5','8')";//排除掉失败状态
        changeVo.setLs_status(sl);
        List<ChangeVo> voList = changeInfoMapper.toNeedList(changeVo);

        voList.forEach(x -> {
            //下一位处理人
            x.setHandler_user_name(x.getHandler_user() == null ? "" :
                    questionManageService.getName(sysUserMap, x.getHandler_user()));

            x.setCreate_user_uname(questionManageService.getUserName(sysUserMap, x.getCreate_user()));
            x.setCreate_user_name(questionManageService.getName(sysUserMap, x.getCreate_user()));
            x.setOperate_name(questionManageService.getName(sysUserMap, x.getOperate_user()));
            x.setStatus_name(sysDictService.getDict("change_status", x.getStatus()).getLabel());
            x.setType_name(sysDictService.getDict("change_type", x.getChange_type()).getLabel());
//            // 审批节点
//            x.setChangeNodes(changeNodesService.selectList(Wrappers.<ChangeNodes>lambdaQuery()
//                    .eq(ChangeNodes::getChange_id, x.getId()).eq(ChangeNodes::getIs_del, "0")));
//            // 方案拟定
//            x.setChangeProgrammeDraft(changeProgrammeDraftService.selectOne(Wrappers.<ChangeProgrammeDraft>lambdaQuery()
//                    .eq(ChangeProgrammeDraft::getChange_id, x.getId()).eq(ChangeProgrammeDraft::getIs_del, "0")));
//            // 方案拟定人
//            x.setProgramme_user_name(x.getChangeProgrammeDraft() == null ? "" :
//                    questionManageService.getName(sysUserMap, x.getChangeProgrammeDraft().getProgramme_user()));
//            // 方案拟定评审
//            x.setChangeProgrammeApprove(changeProgrammeApproveService.selectOne(Wrappers.<ChangeProgrammeApprove>lambdaQuery().
//                    eq(ChangeProgrammeApprove::getChange_id, x.getId()).eq(ChangeProgrammeApprove::getIs_del, "0")));
//            //方案审核人
//            x.setCpa_approve_name(x.getChangeProgrammeApprove() == null ? "" :
//                    questionManageService.getName(sysUserMap, x.getChangeProgrammeApprove().getApprove_user()));
//            // 方案实施
//            x.setChangeImplement(changeImplementService.selectOne(Wrappers.<ChangeImplement>lambdaQuery().
//                    eq(ChangeImplement::getChange_id, x.getId()).eq(ChangeImplement::getIs_del, "0")));
//            // 方案实施评审
//            x.setChangeImplementApprove(changeImplementApproveService.selectOne(Wrappers.<ChangeImplementApprove>lambdaQuery().
//                    eq(ChangeImplementApprove::getChange_id, x.getId()).eq(ChangeImplementApprove::getIs_del, "0")));
//            // 关联事件
//            x.setChangeEvents(changeEventService.selectList(Wrappers.<ChangeEvent>lambdaQuery().
//                    eq(ChangeEvent::getChange_id, x.getId()).eq(ChangeEvent::getIs_del, "0")));
//            // 关联问题
//            x.setChangeQuestions(changeQuestionService.selectList(Wrappers.<ChangeQuestion>lambdaQuery().
//                    eq(ChangeQuestion::getChange_id, x.getId()).eq(ChangeQuestion::getIs_del, "0")));
//            // 关联配置
//            x.setChangeAssets(changeAssetsService.selectList(Wrappers.<ChangeAssets>lambdaQuery().
//                    eq(ChangeAssets::getChange_id, x.getId()).eq(ChangeAssets::getIs_del, "0")));
//            // 关单
//            x.setChangeClose(changeCloseService.selectOne(Wrappers.<ChangeClose>lambdaQuery().
//                    eq(ChangeClose::getChange_id, x.getId()).eq(ChangeClose::getIs_del, "0")));
//
//            //方案审核人
//            x.setClose_user_name(x.getChangeClose() == null || x.getChangeClose().getClose_user() == null ? "" :
//                    questionManageService.getName(sysUserMap, x.getChangeClose().getClose_user()));
        });

        PageInfo<ChangeVo> pageInfo = new PageInfo<>(voList);

        return ResultInfo.success(pageInfo);

    }

    public List<ChangeVo> toNeedList(ChangeVo changeVo) {
        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();
        if (null == changeVo.getUserId()) {
            changeVo.setHandler_user(sysUserService.getUser().getId());
        } else {
            changeVo.setHandler_user(changeVo.getUserId());
        }
        List<ChangeVo> voList = changeInfoMapper.toNeedList(changeVo);
        voList.forEach(x -> {
            //下一位处理人
            x.setHandler_user_name(x.getHandler_user() == null ? "" :
                    questionManageService.getName(sysUserMap, x.getHandler_user()));

            x.setCreate_user_uname(questionManageService.getUserName(sysUserMap, x.getCreate_user()));
            x.setCreate_user_name(questionManageService.getName(sysUserMap, x.getCreate_user()));
            x.setOperate_name(questionManageService.getName(sysUserMap, x.getOperate_user()));
            x.setStatus_name(sysDictService.getDict("change_status", x.getStatus()).getLabel());
            x.setType_name(sysDictService.getDict("change_type", x.getChange_type()).getLabel());
        });
        return voList;
    }

    public ResultInfo getChange(ChangeInfo changeInfo) {
        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();
        ChangeVo changeVo = new ChangeVo();
        // 属性
        changeVo.setChangeInfo(selectOne(Wrappers.<ChangeInfo>lambdaQuery().eq(ChangeInfo::getId, changeInfo.getId())));
        changeVo.setCreate_user_uname(questionManageService.getUserName(sysUserMap, changeVo.getChangeInfo().getCreate_user()));
        changeVo.setCreate_user_name(questionManageService.getName(sysUserMap, changeVo.getChangeInfo().getCreate_user()));
        changeVo.setOperate_name(questionManageService.getName(sysUserMap, changeVo.getChangeInfo().getOperate_user()));
        changeVo.setStatus_name(sysDictService.getDict("change_status", changeVo.getChangeInfo().getStatus()).getLabel());
        changeVo.setType_name(sysDictService.getDict("change_type", changeVo.getChangeInfo().getChange_type()).getLabel());
        changeVo.setChange_type(changeVo.getChangeInfo().getChange_type());
        changeVo.setApprove_name(questionManageService.getName(sysUserMap, changeVo.getChangeInfo().getApprove_user()));
        // 类别名
        changeVo.setClassify_name(changeTypeService.selectById(changeVo.getChangeInfo().getChange_classify()).getType_name());
        // 来源名
        changeVo.setSource_name(changeSourceService.selectById(changeVo.getChangeInfo().getChange_source()).getSource_name());
        // 时间节点
        changeVo.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0").orderByAsc("time_nodes").orderByAsc("sort")));
        // 下一步要执行的节点
        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_status().equals("1")) {
                changeVo.setChangeNode(itme);
                break;
            }
        }
        // 审批人
        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_nodes().equals("2")) {
                itme.setApprove_user_name(questionManageService.getName(sysUserMap, itme.getApprove_user()));
            }
        }
        //下一位处理人
        changeVo.setHandler_user_name(changeVo.getChangeInfo().getHandler_user() == null ? "" :
                questionManageService.getName(sysUserMap, changeVo.getChangeInfo().getHandler_user()));

        // 方案拟定
        changeVo.setChangeProgrammeDraft(changeProgrammeDraftService.selectOne(new QueryWrapper<ChangeProgrammeDraft>()
                .eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        // 方案拟定人
        changeVo.setProgramme_user_name(questionManageService.getName(sysUserMap, changeVo.getChangeProgrammeDraft().getProgramme_user()));
        // 方案拟定评审
        changeVo.setChangeProgrammeApprove(changeProgrammeApproveService.selectOne(new QueryWrapper<ChangeProgrammeApprove>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        // 方案审核人
        changeVo.setCpa_approve_name(changeVo.getChangeProgrammeApprove() == null || changeVo.getChangeProgrammeApprove().getApprove_user() == null ? "" :
                questionManageService.getName(sysUserMap, changeVo.getChangeProgrammeApprove().getApprove_user()));
        // 方案实施
        changeVo.setChangeImplement(changeImplementService.selectOne(new QueryWrapper<ChangeImplement>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        //方案实施人
        changeVo.setImplement_user_name(questionManageService.getName(sysUserMap, changeVo.getChangeImplement().getImplement_user()));
        // 方案实施评审
        changeVo.setChangeImplementApprove(changeImplementApproveService.selectOne(new QueryWrapper<ChangeImplementApprove>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        //事件
        changeVo.setChangeEvents(changeEventService.selectList(new QueryWrapper<ChangeEvent>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        //关联的事件工单
        if (!CollectionUtils.isEmpty(changeVo.getChangeEvents())) {
            List<Integer> idList = changeVo.getChangeEvents().stream().map(ChangeEvent::getEvent_id).collect(Collectors.toList());
            EventVo eventVo = new EventVo();
            eventVo.setIds(idList);
            changeVo.setEvents(eventService.getList(eventVo));
        }
        changeVo.setChangeQuestions(changeQuestionService.selectList(new QueryWrapper<ChangeQuestion>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        //关联的问题工单
        if (!CollectionUtils.isEmpty(changeVo.getChangeQuestions())) {
            List<Integer> ids = changeVo.getChangeQuestions().stream().map(ChangeQuestion::getQuestion_id).collect(Collectors.toList());
            QuestionVo questionVo = new QuestionVo();
            questionVo.setIds(ids);
            changeVo.setQuestionVos(questionManageService.getList(questionVo));
        }
        changeVo.setChangeAssets(changeAssetsService.selectList(new QueryWrapper<ChangeAssets>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));
        //关联的配置项
        if (!CollectionUtils.isEmpty(changeVo.getChangeAssets())) {
            List<Integer> ids = changeVo.getChangeAssets().stream().map(ChangeAssets::getAssets_id).collect(Collectors.toList());
            AssetsManage assetsManage2 = new AssetsManage();
            assetsManage2.setIds(ids);
            changeVo.setAssetsManages(assetsManageService.getList(assetsManage2));
        }
        //属性信息 附件
        List<FileManage> fileManages = changeFileService.getFileManage(changeVo.getChangeInfo().getId());
        fileManages.forEach(x -> {
            String path = uploadPath + File.separator + x.getFile_name();
            File file = new File(path);
            if (file.exists()) {
                String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                x.setBase64(base64);
            } else {
                x.setBase64("");
            }

        });
        changeVo.setFileManageInfoList(fileManages);
        // 关单
        changeVo.setChangeClose(changeCloseService.selectOne(new QueryWrapper<ChangeClose>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0")));


        // 相关日志信息
        changeVo.setSysLogs(Collections.emptyList());
        List<SysLog> sysLogs = sysLogService.selectList(Wrappers.<SysLog>lambdaQuery().
                eq(SysLog::getBill_id, changeVo.getChangeInfo().getId()).eq(SysLog::getModule, LogEnums.LOG_CHANGE.getValue()));
        if (!CollectionUtils.isEmpty(sysLogs)) {
            changeVo.setSysLogs(sysLogs);
        }

        return ResultInfo.success(changeVo);
    }


    public ResultInfo toAdd(ChangeVo changeVo, List<MultipartFile> files) {


        // 添加变更信息
        ChangeInfo changeInfo = changeVo.getChangeInfo();
        // 编号
        changeInfo.setChange_no(DataUtils.getNo(DataType.CHANGE_TYPE));
        changeInfo.setCreate_time(new Date());
        changeInfo.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId() );

        insert(changeInfo);


        // 添加节点
        changeVo.getChangeNodes().forEach(x -> {
            x.setCreate_time(new Date());
            x.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
            x.setChange_id(changeInfo.getId());
            changeNodesService.insert(x);
        });

        // 添加方案拟定
        ChangeProgrammeDraft changeProgrammeDraft = changeVo.getChangeProgrammeDraft();
        // 变更id
        changeProgrammeDraft.setChange_id(changeInfo.getId());
        // 节点id
        changeProgrammeDraft.setCreate_time(new Date());
        changeProgrammeDraft.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
        changeProgrammeDraftService.insert(changeProgrammeDraft);

        // 添加方案审核
        ChangeProgrammeApprove changeProgrammeApprove = changeVo.getChangeProgrammeApprove();
        changeProgrammeApprove.setChange_id(changeInfo.getId());
        changeProgrammeApprove.setCreate_time(new Date());
        changeProgrammeApprove.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
        changeProgrammeApprove.setChange_programme_id(changeProgrammeDraft.getId());
        changeProgrammeApproveService.insert(changeProgrammeApprove);

        // 添加方案实施
        ChangeImplement changeImplement = changeVo.getChangeImplement();
        changeImplement.setChange_id(changeInfo.getId());
        changeImplement.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
        changeImplement.setCreate_time(new Date());
        changeImplementService.insert(changeImplement);

        // 添加实施后评审人
        ChangeImplementApprove changeImplementApprove = changeVo.getChangeImplementApprove();
        changeImplementApprove.setChange_id(changeInfo.getId());
        changeImplementApprove.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
        changeImplementApprove.setCreate_time(new Date());
        changeImplementApprove.setImplement_id(changeImplement.getId());
        changeImplementApprove.insert();

        // 变更事件关联
        if (null != changeVo.getChangeEvents()) {
            changeVo.getChangeEvents().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeInfo.getId());
                changeEventService.insert(x);
            });
        }

        // 变更问题关联
        if (null != changeVo.getChangeQuestions()) {
            changeVo.getChangeQuestions().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeInfo.getId());
                changeQuestionService.insert(x);
            });
        }

        // 变更配置关联
        if (null != changeVo.getChangeAssets()) {
            changeVo.getChangeAssets().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeInfo.getId());
                changeAssetsService.insert(x);
            });
        }

        // 变更管理附件
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                ChangeFile changeFile = new ChangeFile();
                changeFile.setChange_id(changeInfo.getId());
                changeFile.setFile_id(id);

                changeFileService.add(changeFile);
            }
        }
        if(null == changeVo.getUserId()){
            return ResultInfo.success("开单成功!", new LogVo(changeInfo.getId(), "开单", StringUtils.EMPTY));
        }else {
            return ResultInfo.success("开单成功!", new LogVo(changeVo.getUserId(),changeInfo.getId(), "开单", StringUtils.EMPTY));
        }

    }


    /**
     * 修改
     * 变更创建人对处于‘待审批’、‘审批拒绝’状态的变更可进行修改操作；
     * 变更管理员可对所有流程节点的变更工单进行修改操作；
     */
    public ResultInfo toEdit(ChangeVo changeVo, List<MultipartFile> files) {
        // 基础信息
        if (null != changeVo.getChangeInfo()) {
            if (null == changeVo.getHtmlType()) {
                changeVo.getChangeInfo().setStatus("1");
            }
            changeVo.getChangeInfo().setUpdate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
            changeVo.getChangeInfo().setUpdate_time(new Date());
            updateById(changeVo.getChangeInfo());
        }

        // 节点
        if (null != changeVo.getChangeNodes()) {
            ChangeNodes changeNodes = new ChangeNodes();
            changeNodes.setIs_del("1");
            changeNodesService.update(changeNodes, new QueryWrapper<ChangeNodes>().eq("change_id", changeVo.getChangeInfo().getId()));

            changeVo.getChangeNodes().forEach(x -> {
                x.setCreate_time(new Date());
                x.setCreate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeNodesService.insert(x);
            });
        }

        // 变更方案
        if (null != changeVo.getChangeProgrammeDraft()) {
            changeProgrammeDraftService.updateById(changeVo.getChangeProgrammeDraft());
        }

        // 方案审核
        if (null != changeVo.getChangeProgrammeApprove()) {
            changeProgrammeApproveService.updateById(changeVo.getChangeProgrammeApprove());
        }

        // 实施
        if (null != changeVo.getChangeImplement()) {
            changeImplementService.updateById(changeVo.getChangeImplement());
        }

        // 实施后审核
        if (null != changeVo.getChangeImplementApprove()) {
            changeImplementApproveService.updateById(changeVo.getChangeImplementApprove());
        }

        // 关联的事件
        changeEventService.update(null, Wrappers.<ChangeEvent>lambdaUpdate()
                .set(ChangeEvent::getIs_del, String.valueOf(1)).eq(ChangeEvent::getChange_id, changeVo.getChangeInfo().getId()));
        // 变更事件关联
        if (null != changeVo.getChangeEvents()) {
            changeVo.getChangeEvents().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeEventService.insert(x);
            });
        }

        // 变更问题关联
        changeQuestionService.update(null, Wrappers.<ChangeQuestion>lambdaUpdate()
                .set(ChangeQuestion::getIs_del, String.valueOf(1)).eq(ChangeQuestion::getChange_id, changeVo.getChangeInfo().getId()));
        if (null != changeVo.getChangeQuestions()) {
            changeVo.getChangeQuestions().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeQuestionService.insert(x);
            });
        }

        // 变更配置关联
        changeAssetsService.update(null, Wrappers.<ChangeAssets>lambdaUpdate()
                .set(ChangeAssets::getIs_del, String.valueOf(1)).eq(ChangeAssets::getChange_id, changeVo.getChangeInfo().getId()));
        if (null != changeVo.getChangeAssets()) {
            changeVo.getChangeAssets().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeAssetsService.insert(x);
            });
        }

        //属性信息 是否存在文件删除
        String uploadedFiles = changeVo.getUploadedFiles();
        List<FileManage> fileManages = changeFileService.getFileManage(changeVo.getChangeInfo().getId());
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<ChangeFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(ChangeFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(ChangeFile::getChange_id, changeVo.getChangeInfo().getId())
                    .in(ChangeFile::getFile_id, original);
            changeFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                ChangeFile changeFile = new ChangeFile();
                changeFile.setChange_id(changeVo.getChangeInfo().getId());
                changeFile.setFile_id(id);
                changeFileService.add(changeFile);
            }
        }

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("修改成功", new LogVo(changeVo.getChangeInfo().getId(), "修改", ""));
        } else {
            return ResultInfo.success("修改成功", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "修改", ""));
        }
    }


    public ResultInfo exportExcel(ChangeVo changeVo, HttpServletResponse response) {
        Map<Integer, SysUser> sysUserMap = questionManageService.getUserNameMap();
        List<ChangeExcelReport> changeVos = changeInfoMapper.toExcelList(changeVo);


        changeVos.forEach(x -> {
            x.setCreate_user_uname(questionManageService.getUserName(sysUserMap, x.getCreate_user()));
            x.setCreate_user_name(questionManageService.getName(sysUserMap, x.getCreate_user()));
            x.setCpd_programme_user_uname(questionManageService.getUserName(sysUserMap, x.getCpd_programme_user()));
            x.setCpd_programme_user_name(questionManageService.getName(sysUserMap, x.getCpd_programme_user()));
            x.setCc_close_user_uname(questionManageService.getUserName(sysUserMap, x.getCc_close_user()));
            x.setCc_close_user_name(questionManageService.getName(sysUserMap, x.getCc_close_user()));
            x.setCpa_approve_user_uname(questionManageService.getUserName(sysUserMap, x.getCpa_approve_user()));
            x.setCpa_approve_user_name(questionManageService.getName(sysUserMap, x.getCpa_approve_user()));
            x.setCim_implement_user_uname(questionManageService.getUserName(sysUserMap, x.getCim_implement_user()));
            x.setCim_implement_user_name(questionManageService.getName(sysUserMap, x.getCim_implement_user()));
            x.setStatus_name(sysDictService.getDict("change_status", x.getStatus()).getLabel());
            x.setType_name(sysDictService.getDict("change_type", x.getChange_type()).getLabel());

            x.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                    eq("change_id", x.getId()).eq("time_nodes", "2").
                    eq("is_del", "0")));

            JSONArray nodes = new JSONArray();
            x.getChangeNodes().forEach(y -> {
                JSONObject jsonObject = new JSONObject();
                String approve_name = questionManageService.getName(sysUserMap, y.getApprove_user());
                jsonObject.put("approve_name", approve_name);
                String approve_status = "未审批";
                if (null != y.getApprove_status()) {
                    approve_status = y.getApprove_status().equals("1") ? "审批通过" : "审批拒绝";
                }
                jsonObject.put("approve_status", approve_status);
                jsonObject.put("approve_time", y.getApprove_time() == null ? "" : DateUtils.toDateStr(y.getApprove_time()));
                jsonObject.put("approve_remark", y.getApprove_remark());
                nodes.add(jsonObject);
            });

            x.setStr_change_nodes(nodes.toString());

        });
        ExcelWriter writer = ExcelUtil.getWriter().renameSheet(
                String.format("%s%s", DataType.change_excel, DataUtils.getToday()));
//        ExcelWriter writer = ExcelUtil.getWriter("C:\\Users\\Administrator\\Desktop\\writeBeanTest.xlsx");

        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("change_no", "变更单号");
        writer.addHeaderAlias("create_user_uname", "创建人(用户名)");
        writer.addHeaderAlias("create_user_name", "创建人(姓名)");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("status_name", "状态");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("start_time", "计划开始时间");
        writer.addHeaderAlias("end_time", "计划完成时间");
        writer.addHeaderAlias("classify_name", "类别名称");
        writer.addHeaderAlias("type_name", "变更类型");
        writer.addHeaderAlias("change_source", "变更来源*");
        writer.addHeaderAlias("change_reason", "变更原因");
        writer.addHeaderAlias("current_environment", "当前环境");
        writer.addHeaderAlias("cpd_change_programme", "变更方案");
        writer.addHeaderAlias("cpd_fallback_plan", "回退计划");
        writer.addHeaderAlias("cpd_risk_assessment", "风险评估");
        writer.addHeaderAlias("cpd_programme_user_uname", "方案拟定人(登录名)");
        writer.addHeaderAlias("cpd_programme_user_name", "方案拟定人(姓名)");
        writer.addHeaderAlias("cc_close_user_uname", "关单人(用户名)");
        writer.addHeaderAlias("cc_close_user_name", "关单人(姓名)");
        writer.addHeaderAlias("cc_close_time", "关单时间");
        writer.addHeaderAlias("cpa_approve_user_uname", "方案审核人(用户名)");
        writer.addHeaderAlias("cpa_approve_user_name", "方案审核人(姓名)");
        writer.addHeaderAlias("cpa_approve_remark", "方案审批意见");
        writer.addHeaderAlias("cpa_approve_time", "方案审批时间");
        writer.addHeaderAlias("cim_implement_user_uname", "方案实施人(用户名)");
        writer.addHeaderAlias("cim_implement_user_name", "方案实施人(姓名)");
        writer.addHeaderAlias("cim_start_time", "方案实施时间");
        writer.addHeaderAlias("cim_implement_remark", "方案实施备注");
        writer.addHeaderAlias("cia_after_implement_user_uname", "实施后评审人(用户名)");
        writer.addHeaderAlias("cia_after_implement_user_name", "实施后评审人(姓名)");
        writer.addHeaderAlias("cia_after_implement_remark", "实施后评审意见");
        writer.addHeaderAlias("cia_after_implement_time", "实施后评审时间");
        writer.addHeaderAlias("str_change_nodes", "审批信息");
        writer.addHeaderAlias("", "");
        writer.addHeaderAlias("", "");
        writer.addHeaderAlias("", "");
        writer.addHeaderAlias("", "");


        writer.setOnlyAlias(true);
        writer.write(changeVos, true);
//        writer.close();

        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition",
                    "attachment;filename=" + URLEncoder.encode(
                            String.format("%s%s.xls", DataType.change_excel, DataUtils.getToday()), "UTF-8"));
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
     * 导入excel
     *
     * @param importFile
     * @return
     */
    public ResultInfo importExcel(MultipartFile importFile) {
        List<ChangeExcelImport> changeExcelImports = ExcelUtils.read(importFile, ChangeExcelImport.class);

        List<JSONObject> errorData = new ArrayList<>();

        List<ChangeExcelImport> successData = new ArrayList<>();

        int rows = 1;

        for (ChangeExcelImport x : changeExcelImports) {
            // 必填项为空
            if (StringUtils.isEmpty(x.getClassify_name()) || StringUtils.isEmpty(x.getType_name()) ||
                    StringUtils.isEmpty(x.getTitle()) || null == x.getCreate_time() || StringUtils.isEmpty(x.getStatus_name())) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("row", rows);
                jsonObject.put("reason", "必填项为空");
                jsonObject.put("change", x);
                errorData.add(jsonObject);
                rows++;
                continue;
            }

            /*
             *  数据校验
             */
            // 变更类别校验
            ChangeType changeType = changeTypeService.selectOne(Wrappers.<ChangeType>lambdaQuery()
                    .eq(ChangeType::getType_name, x.getClassify_name()).eq(ChangeType::getIs_del, "0"));
            if (null == changeType) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("row", rows);
                jsonObject.put("reason", "变更类别不存在");
                jsonObject.put("change", x);
                errorData.add(jsonObject);
                rows++;
                continue;
            }
            // 变更类型校验
            SysDict sys_change_type = sysDictService.selectOne(Wrappers.<SysDict>lambdaQuery().
                    eq(SysDict::getDict_type, "change_type").eq(SysDict::getIs_del, "0").
                    eq(SysDict::getLabel, x.getType_name()));
            if (null == sys_change_type) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("row", rows);
                jsonObject.put("reason", "变更类型不存在");
                jsonObject.put("change", x);
                errorData.add(jsonObject);
                rows++;
                continue;
            }
            // 变更状态
            SysDict sys_change_status = sysDictService.selectOne(Wrappers.<SysDict>lambdaQuery().
                    eq(SysDict::getDict_type, "change_status").eq(SysDict::getIs_del, "0").
                    eq(SysDict::getLabel, x.getStatus_name()));
            if (null == sys_change_status) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("row", rows);
                jsonObject.put("reason", "变更状态不存在");
                jsonObject.put("change", x);
                errorData.add(jsonObject);
                rows++;
                continue;
            }

            // 创建人登录名或用户名不为空
            SysUser createUser = null;
            if (null != x.getCreate_user_uname() || null != x.getCreate_user_name()) {
                JSONObject data = onlySysUser(x.getCreate_user_name(), x.getCreate_user_uname());
                if (data.getInteger("code") == 500) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "创建人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                } else {
                    createUser = data.getObject("sysUser", SysUser.class);
                }
            }

            // 方案拟定人登录名或用户名不为空
            SysUser cpd_programme_user = null;
            if (null != x.getCpd_programme_user_name() || null != x.getCpd_programme_user_uname()) {
                JSONObject data = onlySysUser(x.getCpd_programme_user_name(), x.getCpd_programme_user_uname());
                if (data.getInteger("code") == 500) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "方案拟定人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                } else {
                    cpd_programme_user = data.getObject("sysUser", SysUser.class);
                }
            }

            //关单人登录名或用户名不为空
            SysUser cc_close_user = null;
            if (null != x.getCc_close_user_name() || null != x.getCc_close_user_uname()) {
                JSONObject data = onlySysUser(x.getCc_close_user_name(), x.getCc_close_user_uname());
                if (data.getInteger("code") == 500) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "关单人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                } else {
                    cc_close_user = data.getObject("sysUser", SysUser.class);
                }
            }

            //方案审核人登录名或用户名不为空
            SysUser cpa_approve_user = null;
            if (null != x.getCpa_approve_user_name() || null != x.getCpa_approve_user_uname()) {
                JSONObject data = onlySysUser(x.getCpa_approve_user_name(), x.getCpa_approve_user_uname());
                if (data.getInteger("code") == 500) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "方案审核人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                } else {
                    cpa_approve_user = data.getObject("sysUser", SysUser.class);
                }
            }

            //方案实施人
            SysUser cim_implement_user = null;
            if (null != x.getCim_implement_user_name() || null != x.getCim_implement_user_uname()) {
                JSONObject data = onlySysUser(x.getCim_implement_user_name(), x.getCim_implement_user_uname());
                if (data.getInteger("code") == 500) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "方案实施人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                } else {
                    cim_implement_user = data.getObject("sysUser", SysUser.class);
                }
            }

            // 实施后评审人
            SysUser cia_after_implement_user = null;
            if (null != x.getCia_after_implement_user_name() || null != x.getCia_after_implement_user_uname()) {
                JSONObject data = onlySysUser(x.getCia_after_implement_user_name(), x.getCia_after_implement_user_uname());
                if (data.getInteger("code") == 500) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "实施后评审人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                } else {
                    cia_after_implement_user = data.getObject("sysUser", SysUser.class);
                }
            }

            // 审批意见
            List<ChangeNodes> changeNodes = new ArrayList<>();
            if (StringUtils.isNotEmpty(x.getStr_change_nodes())) {
                JSONArray jsonArray = JSONArray.parseArray(x.getStr_change_nodes());

                int i;
                for (i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = isApprove(jsonArray.getJSONObject(i));
                    if (object.getInteger("code") == 500) {
                        break;
                    } else {
                        changeNodes.add(object.getObject("nodes", ChangeNodes.class));
                    }
                }

                if (jsonArray.size() != i) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row", rows);
                    jsonObject.put("reason", "评审意见审批人登录名/用户名不具备唯一性或不存在");
                    jsonObject.put("change", x);
                    errorData.add(jsonObject);
                    rows++;
                    continue;
                }
            }

            // 数据处理
            ChangeVo changeVo = new ChangeVo();
            // 变更基本信息
            ChangeInfo changeInfo = new ChangeInfo();


            changeInfo.setId(x.getId());
            // 编号
            changeInfo.setChange_no(StringUtils.isNotEmpty(x.getChange_no())
                    ? x.getChange_no() : DataUtils.getNo(DataType.CHANGE_TYPE));
            // 状态
            changeInfo.setStatus(sys_change_status.getValue());
            // 标题
            changeInfo.setTitle(x.getTitle());
            // 创建时间
            changeInfo.setCreate_time(x.getCreate_time());
            // 计划开始时间
            changeInfo.setStart_time(x.getStart_time());
            // 计划完成时间
            changeInfo.setEnd_time(x.getEnd_time());
            // 类别名称
            changeInfo.setChange_classify(String.valueOf(changeType.getId()));
            // 类型名称
            changeInfo.setChange_type(String.valueOf(sys_change_type.getValue()));
            // 变更来源
            changeInfo.setChange_source(x.getChange_source());
            // 创建人
            changeInfo.setCreate_user(createUser.getId());
            // 环境
            changeInfo.setCurrent_environment(x.getCurrent_environment());
            // 变更原因
            changeInfo.setChange_reason(x.getChange_reason());
            changeInfo.setIs_del("0");
            changeInfo.setCreate_user(sysUserService.getUser().getId());


            changeVo.setChangeInfo(changeInfo);

            // 节点重大变更需要改
            List<ChangeNodes> nodesList = new ArrayList<>();
            for (int i = 1; i < 7; i++) {
                ChangeNodes node = new ChangeNodes();
                if (i == 1) {
                    node.setTime_nodes(String.valueOf(i));
                    node.setSort(String.valueOf(i));
                    node.setTime_status(String.valueOf(0));
                    node.setIs_del(String.valueOf(0));
                    node.setCreate_user(sysUserService.getUser().getId());
                    node.setCreate_time(new Date());
                    nodesList.add(node);
                } else if (i == 2) {
                    if (changeNodes.isEmpty()) {
                        node.setTime_nodes(String.valueOf(i));
                        node.setSort(String.valueOf(i));
                        node.setTime_status(String.valueOf(1));
                        node.setIs_del(String.valueOf(0));
                        node.setCreate_user(sysUserService.getUser().getId());
                        node.setCreate_time(new Date());
                        nodesList.add(node);
                    }
                    // 不管有没有值
                    for (int j = 0; j < changeNodes.size(); j++) {

                    }

                } else {
                    node.setTime_nodes(String.valueOf(i + changeNodes.size()));
                    node.setSort(String.valueOf(i + changeNodes.size()));
                    node.setTime_status(String.valueOf(1));
                    node.setIs_del(String.valueOf(0));
                    node.setCreate_user(sysUserService.getUser().getId());
                    node.setCreate_time(new Date());
                    nodesList.add(node);
                }

            }

            // 方案拟定
            ChangeProgrammeDraft changeProgrammeDraft = new ChangeProgrammeDraft();
            changeProgrammeDraft.setIs_del("0");
            changeProgrammeDraft.setCreate_user(sysUserService.getUser().getId());
            changeProgrammeDraft.setCreate_time(new Date());
            // 变更方案
            changeProgrammeDraft.setChange_programme(x.getCpd_change_programme());
            // 回退计划
            changeProgrammeDraft.setFallback_plan(x.getCpd_fallback_plan());
            // 风险评估
            changeProgrammeDraft.setRisk_assessment(x.getCpd_risk_assessment());
            // 方案拟定人
            changeProgrammeDraft.setProgramme_user(cpd_programme_user.getId());

            changeVo.setChangeProgrammeDraft(changeProgrammeDraft);

            // 方案评审
            ChangeProgrammeApprove changeProgrammeApprove = new ChangeProgrammeApprove();
            changeProgrammeApprove.setIs_del("0");
            changeProgrammeApprove.setCreate_user(sysUserService.getUser().getId());
            changeProgrammeApprove.setCreate_time(new Date());
            // 方案审核人
            changeProgrammeApprove.setApprove_user(cpa_approve_user.getId());
            // 方案审批意见
            changeProgrammeApprove.setApprove_remark(x.getCpa_approve_remark());
            // 方案审批时间
            changeProgrammeApprove.setApprove_time(x.getCpa_approve_time());

            changeVo.setChangeProgrammeApprove(changeProgrammeApprove);


            // 方案实施
            ChangeImplement changeImplement = new ChangeImplement();
            changeImplement.setIs_del("0");
            changeImplement.setCreate_time(new Date());
            changeImplement.setCreate_user(sysUserService.getUser().getId());
            changeImplement.setImplement_user(cim_implement_user.getId());
            changeImplement.setStart_time(x.getCim_start_time());
            changeImplement.setImplement_remark(x.getCim_implement_remark());

            changeVo.setChangeImplement(changeImplement);

            // 方案实施评审
            ChangeImplementApprove changeImplementApprove = new ChangeImplementApprove();
            changeImplementApprove.setIs_del("0");
            changeImplementApprove.setCreate_time(new Date());
            changeImplementApprove.setCreate_user(sysUserService.getUser().getId());
            changeImplementApprove.setAfter_implement_user(cia_after_implement_user.getId());
            changeImplementApprove.setAfter_implement_remark(x.getCia_after_implement_remark());
            changeImplementApprove.setAfter_implement_time(x.getCia_after_implement_time());

            changeVo.setChangeImplementApprove(changeImplementApprove);

            if (x.getId() == null) {
                toAdd(changeVo, null);
            } else {
                toEdit(changeVo, null);
            }


            rows++;
        }


        System.out.println(changeExcelImports);

        return null;
    }

    /**
     * 审批信息校验
     *
     * @param jsonObject
     * @return
     */
    public JSONObject isApprove(JSONObject jsonObject) {
        ChangeNodes nodes = new ChangeNodes();

        JSONObject returnData = new JSONObject();
        if (jsonObject.containsKey("approve_name")) {
            JSONObject data = onlySysUser(jsonObject.getString("approve_name"), null);
            if (data.getInteger("code") == 500) {
                returnData.put("code", 500);
                return returnData;
            } else {
                nodes.setApprove_user(data.getObject("sysUser", SysUser.class).getId());
            }
        }

        if (jsonObject.containsKey("approve_status")) {
            if (jsonObject.getString("approve_status").equals("审批通过")) {
                nodes.setApprove_status("1");
            } else if (jsonObject.getString("approve_status").equals("审批拒绝")) {
                nodes.setApprove_status("2");
            }
        }

        if (jsonObject.containsKey("approve_time")) {
            nodes.setApprove_time(DateUtils.toStrDate(jsonObject.getString("approve_time")));
        }

        if (jsonObject.containsKey("approve_remark")) {
            nodes.setApprove_remark(jsonObject.getString("approve_remark"));
        }

        returnData.put("code", 200);
        returnData.put("nodes", nodes);
        return returnData;
    }


    /**
     * 根据姓名与用户名校验用户是否唯一
     * 邮箱，邮箱前缀，用户名，姓名
     *
     * @param name
     * @param user_name
     * @return
     */
    public JSONObject onlySysUser(String name, String user_name) {
        JSONObject jsonObject = new JSONObject();
        // 用户名匹配
        List<SysUser> unames = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").eq(user_name != null, SysUser::getUser_name, user_name));
        if (unames.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", unames.get(0));
            return jsonObject;
        }

        List<SysUser> unames1 = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").eq(name != null, SysUser::getUser_name, name));
        if (unames1.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", unames1.get(0));
            return jsonObject;
        }

        // 姓名匹配
        List<SysUser> names = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").eq(user_name != null, SysUser::getName, user_name));
        if (names.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", names.get(0));
            return jsonObject;
        }

        List<SysUser> names1 = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").eq(name != null, SysUser::getName, name));
        if (names1.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", names1.get(0));
            return jsonObject;
        }


        // 进行邮箱匹配
        List<SysUser> emails = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").eq(user_name != null, SysUser::getIs_emial, user_name));
        if (emails.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", emails.get(0));
        }

        List<SysUser> emails1 = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").eq(name != null, SysUser::getIs_emial, name));
        if (emails1.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", emails1.get(0));
        }

        // 邮箱前缀匹配
        List<SysUser> like_email = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").like(user_name != null, SysUser::getIs_emial, user_name));
        if (like_email.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", like_email.get(0));
        }

        List<SysUser> like_email1 = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().
                eq(SysUser::getIs_del, "0").like(name != null, SysUser::getIs_emial, name));
        if (like_email1.size() == 1) {
            jsonObject.put("code", 200);
            jsonObject.put("sysUser", like_email1.get(0));
        }

        jsonObject.put("code", 500);

        return jsonObject;
    }

    /**
     * 撤销
     */
    public ResultInfo toCancel(ChangeInfo changeInfo) {
        changeInfo.setUpdate_time(new Date());
        changeInfo.setUpdate_user(null == changeInfo.getUserId() ? sysUserService.getUser().getId() : changeInfo.getUserId());
        updateById(changeInfo);

        if (Base.empty(changeInfo.getUserId())) {
            return ResultInfo.success("撤销成功", new LogVo(changeInfo.getId(), "撤销", ""));
        } else {
            return ResultInfo.success("撤销成功", new LogVo(changeInfo.getUserId(), changeInfo.getId(), "撤销", ""));
        }
    }

    /**
     * 修改关联
     */
    public ResultInfo toEditRelevance(ChangeVo changeVo) {

        // 关联的事件
        changeEventService.update(null, Wrappers.<ChangeEvent>lambdaUpdate()
                .set(ChangeEvent::getIs_del, String.valueOf(1)).eq(ChangeEvent::getChange_id, changeVo.getChangeInfo().getId()));
        // 变更事件关联
        if (null != changeVo.getChangeEvents()) {
            changeVo.getChangeEvents().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeEventService.insert(x);
            });
        }

        // 变更问题关联
        changeQuestionService.update(null, Wrappers.<ChangeQuestion>lambdaUpdate()
                .set(ChangeQuestion::getIs_del, String.valueOf(1)).eq(ChangeQuestion::getChange_id, changeVo.getChangeInfo().getId()));
        if (null != changeVo.getChangeQuestions()) {
            changeVo.getChangeQuestions().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeQuestionService.insert(x);
            });
        }

        // 变更配置关联
        changeAssetsService.update(null, Wrappers.<ChangeAssets>lambdaUpdate()
                .set(ChangeAssets::getIs_del, String.valueOf(1)).eq(ChangeAssets::getChange_id, changeVo.getChangeInfo().getId()));
        if (null != changeVo.getChangeAssets()) {
            changeVo.getChangeAssets().forEach(x -> {
                x.setCreate_time(new Date());
                x.setChange_id(changeVo.getChangeInfo().getId());
                changeAssetsService.insert(x);
            });
        }

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("成功", new LogVo(changeVo.getChangeInfo().getId(), "修改关联", ""));
        } else {
            return ResultInfo.success("成功", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "修改关联", ""));
        }

    }

    /**
     * 添加备注
     */
    public ResultInfo toRemark(ChangeVo changeVo) {
        if (null != changeVo.getChangeInfo()) {
            changeVo.getChangeInfo().setUpdate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
            changeVo.getChangeInfo().setUpdate_time(new Date());
            changeVo.getChangeInfo().updateById();
        }
        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("成功", new LogVo(changeVo.getChangeInfo().getId(), "添加备注", changeVo.getChangeInfo().getRemark()));
        } else {
            return ResultInfo.success("成功", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "添加备注", changeVo.getChangeInfo().getRemark()));
        }

    }


    /**
     * 待审批
     * 审批 通过、拒绝
     */
    public ResultInfo toApprove(ChangeVo changeVo) {
        SysUser userData = sysUserService.getUserData(changeVo.getUserId());
        // 审批拒绝修改状态且记录日志 同意进行下一步
        if (changeVo.getChangeNode().getApprove_status().equals("2")) {
            changeVo.getChangeInfo().setStatus("2");
            changeVo.getChangeInfo().setUpdate_time(new Date());
            changeVo.getChangeInfo().setUpdate_user(userData.getId());
            changeVo.getChangeInfo().updateById();
            if (Base.empty(changeVo.getUserId())) {
                return ResultInfo.success("审批拒绝", new LogVo(changeVo.getChangeInfo().getId(), "审批拒绝", changeVo.getChangeNode().getApprove_remark()));
            }else{
                return ResultInfo.success("审批拒绝", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "审批拒绝", changeVo.getChangeNode().getApprove_remark()));
            }

        }
        //8	变更类型 1.紧急变更 2.一般变更 3.重大变更|紧急变更影响时间轴，影响流程
        if (Base.notEmpty(changeVo.getChangeInfo().getChange_type()) && changeVo.getChangeInfo().getChange_type().equals("1")) {
            //12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销
            changeVo.getChangeInfo().setStatus("6");
        } else {
            changeVo.getChangeInfo().setStatus("3");
        }

        changeVo.getChangeNode().setTime_status("0");
        changeVo.getChangeNode().setApprove_time(new Date());
        changeVo.getChangeNode().setUpdate_time(new Date());
        changeVo.getChangeNode().setUpdate_user(userData.getId());

        changeVo.getChangeNode().updateById();

        // 根据节点修改i下一位处理人
        changeVo.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0").orderByAsc("time_nodes").orderByAsc("sort")));

        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_status().equals("1") && itme.getId() != changeVo.getChangeNode().getId()) {
                switch (itme.getTime_nodes()) {
                    case "2":
                        changeVo.getChangeInfo().setStatus("1");
                        changeVo.getChangeInfo().setApprove_user(itme.getApprove_user());
                        changeVo.getChangeInfo().setHandler_user(itme.getApprove_user());
                        break;
                    case "3":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeDraft().getProgramme_user());
                        break;
                    case "4":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeApprove().getApprove_user());
                        break;
                    case "5":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplement().getImplement_user());
                        break;
                    case "6":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplementApprove().getAfter_implement_user());
                        break;
                    default:
                        break;
                }
                break;
            }
        }

        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(userData.getId());
        changeVo.getChangeInfo().updateById();

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("审批通过", new LogVo(changeVo.getChangeInfo().getId(), "审批通过", changeVo.getChangeNode().getApprove_remark()));
        } else {
            return ResultInfo.success("审批通过", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "审批通过", changeVo.getChangeNode().getApprove_remark()));
        }
    }

    /**
     * 方案制定
     */
    public ResultInfo toProgrammeEdit(ChangeVo changeVo) {
        SysUser userData = sysUserService.getUserData(changeVo.getUserId());
        changeVo.getChangeProgrammeDraft().setUpdate_time(new Date());
        changeVo.getChangeProgrammeDraft().setUpdate_user(userData.getId());
        changeVo.getChangeProgrammeDraft().updateById();

        if (changeVo.getChangeNode().getApprove_status().equals("0")) {
            if (Base.empty(changeVo.getUserId())) {
                return ResultInfo.success("方案保存成功！",
                        new LogVo(changeVo.getChangeInfo().getId(), "方案已保存", Strings.EMPTY));
            } else {
                return ResultInfo.success("方案保存成功！",
                        new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "方案已保存", Strings.EMPTY));
            }
        }
        changeVo.getChangeNode().setTime_status("0");
        changeVo.getChangeNode().setApprove_time(new Date());
        changeVo.getChangeNode().setUpdate_time(new Date());
        changeVo.getChangeNode().setUpdate_user(userData.getId());

        changeVo.getChangeNode().updateById();

        // 方案指定修改状态
        changeVo.getChangeInfo().setStatus("4");


        // 根据节点修改i下一位处理人
        changeVo.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0").orderByAsc("time_nodes").orderByAsc("sort")));


        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_status().equals("1") && itme.getId() != changeVo.getChangeNode().getId()) {
                switch (itme.getTime_nodes()) {
                    case "2":
                        changeVo.getChangeInfo().setHandler_user(itme.getApprove_user());
                        break;
                    case "3":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeDraft().getProgramme_user());
                        break;
                    case "4":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeApprove().getApprove_user());
                        break;
                    case "5":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplement().getImplement_user());
                        break;
                    case "6":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplementApprove().getAfter_implement_user());
                        break;
                    default:
                        break;
                }


                break;
            }
        }

        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(userData.getId());
        changeVo.getChangeInfo().updateById();

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("方案制定完成！", new LogVo(changeVo.getChangeInfo().getId(), "方案制定完成", Strings.EMPTY));
        } else {
            return ResultInfo.success("方案制定完成！", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "方案制定完成", Strings.EMPTY));
        }
    }

    /**
     * 方案审批
     */
    public ResultInfo toProgrammeApprove(ChangeVo changeVo) {
        SysUser userData = sysUserService.getUserData(changeVo.getUserId());
        changeVo.getChangeProgrammeApprove().setUpdate_time(new Date());
        changeVo.getChangeProgrammeApprove().setUpdate_user(userData.getId());
        changeVo.getChangeProgrammeApprove().setApprove_time(new Date());
        changeVo.getChangeProgrammeApprove().updateById();
        // 审批拒绝修改状态 同意进行下一步
        if (changeVo.getChangeProgrammeApprove().getStatus().equals("2")) {
            changeVo.getChangeInfo().setStatus("5");
            changeVo.getChangeInfo().setUpdate_time(new Date());
            changeVo.getChangeInfo().setUpdate_user(userData.getId());
            changeVo.getChangeInfo().updateById();

            changeNodesService.update(null, Wrappers.<ChangeNodes>lambdaUpdate().
                    set(ChangeNodes::getTime_status, "1").
                    eq(ChangeNodes::getChange_id, changeVo.getChangeInfo().getId()).
                    eq(ChangeNodes::getIs_del, "0").
                    eq(ChangeNodes::getTime_nodes, "3")
            );

            if (Base.empty(changeVo.getUserId())) {
                return ResultInfo.success("方案审批完成", new
                        LogVo(changeVo.getChangeInfo().getId(), "方案审核拒绝",
                        changeVo.getChangeProgrammeApprove().getApprove_remark()));
            } else {
                return ResultInfo.success("方案审批完成", new
                        LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "方案审核拒绝",
                        changeVo.getChangeProgrammeApprove().getApprove_remark()));
            }

        }
        changeVo.getChangeInfo().setStatus("6");


        changeVo.getChangeNode().setTime_status("0");
        changeVo.getChangeNode().setApprove_time(new Date());
        changeVo.getChangeNode().setUpdate_time(new Date());
        changeVo.getChangeNode().setUpdate_user(userData.getId());

        changeVo.getChangeNode().updateById();


        // 根据节点修改i下一位处理人
        changeVo.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0").orderByAsc("time_nodes").orderByAsc("sort")));


        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_status().equals("1") && itme.getId() != changeVo.getChangeNode().getId()) {
                switch (itme.getTime_nodes()) {
                    case "2":
                        changeVo.getChangeInfo().setHandler_user(itme.getApprove_user());
                        break;
                    case "3":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeDraft().getProgramme_user());
                        break;
                    case "4":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeApprove().getApprove_user());
                        break;
                    case "5":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplement().getImplement_user());
                        break;
                    case "6":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplementApprove().getAfter_implement_user());
                        break;
                    default:
                        break;
                }


                break;
            }
        }

        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(userData.getId());
        changeVo.getChangeInfo().updateById();

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("方案审批完成", new LogVo(changeVo.getChangeInfo().getId(), "方案审核成功", changeVo.getChangeProgrammeApprove().getApprove_remark()));
        } else {
            return ResultInfo.success("方案审批完成", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "方案审核成功", changeVo.getChangeProgrammeApprove().getApprove_remark()));
        }
    }

    /**
     * 方案实施
     */
    public ResultInfo toImplement(ChangeVo changeVo) {
        SysUser userData = sysUserService.getUserData(changeVo.getUserId());
        changeVo.getChangeImplement().setUpdate_time(new Date());
        changeVo.getChangeImplement().setUpdate_user(userData.getId());
        changeVo.getChangeImplement().updateById();


        changeVo.getChangeNode().setTime_status("0");
        changeVo.getChangeNode().setApprove_time(new Date());
        changeVo.getChangeNode().setUpdate_time(new Date());
        changeVo.getChangeNode().setUpdate_user(userData.getId());
        changeVo.getChangeNode().updateById();

        if (changeVo.getChangeImplement().getStatus().equals("2")) {
            changeVo.getChangeInfo().setStatus("8");
            changeVo.getChangeInfo().setUpdate_time(new Date());
            changeVo.getChangeInfo().setUpdate_user(userData.getId());
            changeVo.getChangeInfo().updateById();

            if (Base.empty(changeVo.getUserId())) {
                return ResultInfo.success("方案实施失败", new
                        LogVo(changeVo.getChangeInfo().getId(), "方案实施失败",
                        changeVo.getChangeImplement().getImplement_remark()));
            } else {
                return ResultInfo.success("方案实施失败", new
                        LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "方案实施失败",
                        changeVo.getChangeImplement().getImplement_remark()));
            }

        }

        // 实施成功修改状态 同意进行下一步 7.实施完成
        changeVo.getChangeInfo().setStatus("7");

        // 根据节点修改i下一位处理人
        changeVo.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0").orderByAsc("time_nodes").orderByAsc("sort")));
        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_status().equals("1") && itme.getId() != changeVo.getChangeNode().getId()) {

                switch (itme.getTime_nodes()) {
                    case "2":
                        changeVo.getChangeInfo().setHandler_user(itme.getApprove_user());
                        break;
                    case "3":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeDraft().getProgramme_user());
                        break;
                    case "4":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeApprove().getApprove_user());
                        break;
                    case "5":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplement().getImplement_user());
                        break;
                    case "6":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplementApprove().getAfter_implement_user());
                        break;
                    default:
                        break;
                }

                break;
            }
        }

        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(userData.getId());
        changeVo.getChangeInfo().updateById();

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("方案实施成功", new
                    LogVo(changeVo.getChangeInfo().getId(), "方案实施成功",
                    changeVo.getChangeImplement().getImplement_remark()));
        } else {
            return ResultInfo.success("方案实施成功", new
                    LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "方案实施成功",
                    changeVo.getChangeImplement().getImplement_remark()));
        }
    }

    /**
     * 实施后评审
     */
    public ResultInfo toImplementApprove(ChangeVo changeVo) {
        SysUser userData = sysUserService.getUserData(changeVo.getUserId());
        changeVo.getChangeImplementApprove().setUpdate_time(new Date());
        changeVo.getChangeImplementApprove().setUpdate_user(userData.getId());

        changeVo.getChangeImplementApprove().updateById();

        changeVo.getChangeNode().setTime_status("0");
        changeVo.getChangeNode().setApprove_time(new Date());
        changeVo.getChangeNode().setUpdate_time(new Date());
        changeVo.getChangeNode().setUpdate_user(userData.getId());

        changeVo.getChangeNode().updateById();

        // 9.评审完成
        changeVo.getChangeInfo().setStatus("9");


        // 根据节点修改i下一位处理人
        changeVo.setChangeNodes(changeNodesService.selectList(new QueryWrapper<ChangeNodes>().
                eq("change_id", changeVo.getChangeInfo().getId()).eq("is_del", "0").orderByAsc("time_nodes").orderByAsc("sort")));


        for (ChangeNodes itme : changeVo.getChangeNodes()) {
            if (itme.getTime_status().equals("1") && itme.getId() != changeVo.getChangeNode().getId()) {
                switch (itme.getTime_nodes()) {
                    case "2":
                        changeVo.getChangeInfo().setHandler_user(itme.getApprove_user());
                        break;
                    case "3":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeDraft().getProgramme_user());
                        break;
                    case "4":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeProgrammeApprove().getApprove_user());
                        break;
                    case "5":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplement().getImplement_user());
                        break;
                    case "6":
                        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeImplementApprove().getAfter_implement_user());
                        break;
                    default:
                        break;
                }


                break;
            }
        }

        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(userData.getId());
        //下一个操作人为工单创建人/关单人为创建人
        changeVo.getChangeInfo().setHandler_user(changeVo.getChangeInfo().getCreate_user());
        changeVo.getChangeInfo().updateById();

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("实施评审完成", new
                    LogVo(changeVo.getChangeInfo().getId(), "实施评审完成",
                    changeVo.getChangeImplementApprove().getAfter_implement_remark()));
        } else {
            return ResultInfo.success("实施评审完成", new
                    LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "实施评审完成",
                    changeVo.getChangeImplementApprove().getAfter_implement_remark()));
        }

    }

    /**
     * 转办
     */
    public ResultInfo toTransForm(ChangeVo changeVo) {

        // 修改当前处理人 审核情况下修改ChangeInfo的当前审核人
        changeVo.getChangeInfo().setHandler_user(changeVo.getTrans_form_user());
        if ("1".equals(changeVo.getChangeInfo().getStatus())) {
            changeVo.getChangeInfo().setApprove_user(changeVo.getTrans_form_user());
        }
        SysUser sysUser = sysUserService.getUserData(changeVo.getUserId());
        //8	变更类型 1.紧急变更 2.一般变更 3.重大变更|紧急变更影响时间轴，影响流程
        if (changeVo.getChange_type().equals("1")) {
            //紧急 1.开单 2.审批 3.实施 4.实施后评审 5.关单
            // 根据下一节点状态修改关联人
            switch (changeVo.getChangeNode().getTime_nodes()) {
                case "2"://审批人
                    changeVo.getChangeNode().setApprove_user(changeVo.getTrans_form_user());
                    changeVo.getChangeNode().setUpdate_time(new Date());
                    changeVo.getChangeNode().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeNode().updateById();
                    break;
                case "3"://实施人
                    changeVo.getChangeImplement().setImplement_user(changeVo.getTrans_form_user());
                    changeVo.getChangeImplement().setUpdate_time(new Date());
                    changeVo.getChangeImplement().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeImplement().updateById();
                    break;
                case "4"://实施后评审人
                    changeVo.getChangeImplementApprove().setAfter_implement_user(changeVo.getTrans_form_user());
                    changeVo.getChangeImplementApprove().setUpdate_time(new Date());
                    changeVo.getChangeImplementApprove().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeImplementApprove().updateById();
                    break;
                default:
                    break;
            }
        } else {
            //一般、重大时间轴节点 1.开单 2.审批 3.制定方案 4.方案审核 5.实施 6.实施后评审 7.关单 出现多节点的情况下用sort排序
            // 根据下一节点状态修改关联人
            switch (changeVo.getChangeNode().getTime_nodes()) {
                case "2"://审批人
                    changeVo.getChangeNode().setApprove_user(changeVo.getTrans_form_user());
                    changeVo.getChangeNode().setUpdate_time(new Date());
                    changeVo.getChangeNode().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeNode().updateById();
                    break;
                case "3"://制定方案人
                    changeVo.getChangeProgrammeDraft().setProgramme_user(changeVo.getTrans_form_user());
                    changeVo.getChangeProgrammeDraft().setUpdate_time(new Date());
                    changeVo.getChangeProgrammeDraft().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeProgrammeDraft().updateById();
                    break;
                case "4"://方案审核人
                    changeVo.getChangeProgrammeApprove().setApprove_user(changeVo.getTrans_form_user());
                    changeVo.getChangeProgrammeApprove().setUpdate_time(new Date());
                    changeVo.getChangeProgrammeApprove().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeProgrammeApprove().updateById();
                    break;
                case "5"://实施人
                    changeVo.getChangeImplement().setImplement_user(changeVo.getTrans_form_user());
                    changeVo.getChangeImplement().setUpdate_time(new Date());
                    changeVo.getChangeImplement().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeImplement().updateById();
                    break;
                case "6"://实施后评审人
                    changeVo.getChangeImplementApprove().setAfter_implement_user(changeVo.getTrans_form_user());
                    changeVo.getChangeImplementApprove().setUpdate_time(new Date());
                    changeVo.getChangeImplementApprove().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
                    changeVo.getChangeImplementApprove().updateById();
                    break;
                default:
                    break;
            }
        }

        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(null == changeVo.getUserId() ? sysUser.getId() : changeVo.getUserId());
        changeVo.getChangeInfo().updateById();

        SysUser user = sysUserService.selectById(changeVo.getTrans_form_user());

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("转办成功", new LogVo(changeVo.getChangeInfo().getId(), "转办", "转办至" + user.getName() + "(" + user.getUser_name() + ")备注："
                    + (StringUtils.isBlank(changeVo.getChangeInfo().getRemark()) ? StringUtils.EMPTY : changeVo.getChangeInfo().getRemark())));
        } else {
            return ResultInfo.success("转办成功", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "转办", "转办至" + user.getName() + "(" + user.getUser_name() + ")备注："
                    + (StringUtils.isBlank(changeVo.getChangeInfo().getRemark()) ? StringUtils.EMPTY : changeVo.getChangeInfo().getRemark())));
        }

    }

    /**
     * 转单
     */
    public ResultInfo toTransOrder(ChangeVo changeVo, List<MultipartFile> files) {

        //对于‘方案制定中’、‘方案审核拒绝’状态的变更工单，当前方案拟定人可以执行转单操作；
        //12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销
        if (changeVo.getChangeInfo().getStatus().equals("3") || changeVo.getChangeInfo().getStatus().equals("5")) {
            // 修改方案拟定人
            QueryWrapper<ChangeProgrammeDraft> queryWrapper = new QueryWrapper<>();
            if (Base.notEmpty(changeVo.getChangeInfo().getId())) {
                queryWrapper.eq("change_id", changeVo.getChangeInfo().getId());
            }
            queryWrapper.eq("is_del", "0");
            queryWrapper.orderByDesc("id");
            ChangeProgrammeDraft changeProgrammeDraft = changeProgrammeDraftService.selectOne(queryWrapper);
            changeProgrammeDraft.setProgramme_user(changeVo.getTrans_form_user());
            changeProgrammeDraftService.updateById(changeProgrammeDraft);
        } else if (changeVo.getChangeInfo().getStatus().equals("6")) {
            // 对于‘变更实施中’状态的变更工单，当前变更实施人可以执行转单操作；
            // 修改实施人
            QueryWrapper<ChangeImplement> queryWrapper = new QueryWrapper<>();
            if (Base.notEmpty(changeVo.getChangeInfo().getId())) {
                queryWrapper.eq("change_id", changeVo.getChangeInfo().getId());
            }
            queryWrapper.eq("is_del", "0");
            queryWrapper.orderByDesc("id");
            ChangeImplement changeImplement = changeImplementService.selectOne(queryWrapper);
            changeImplement.setImplement_user(changeVo.getTrans_form_user());

            changeImplementService.updateById(changeImplement);
        }

        //属性信息 是否存在文件删除
        String uploadedFiles = changeVo.getUploadedFiles();
        List<FileManage> fileManages = changeFileService.getFileManage(changeVo.getChangeInfo().getId());
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<ChangeFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(ChangeFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(ChangeFile::getChange_id, changeVo.getChangeInfo().getId())
                    .in(ChangeFile::getFile_id, original);
            changeFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                ChangeFile changeFile = new ChangeFile();
                changeFile.setChange_id(changeVo.getChangeInfo().getId());
                changeFile.setFile_id(id);

                changeFileService.add(changeFile);
            }
        }
        changeVo.getChangeInfo().setHandler_user(changeVo.getTrans_form_user());
        changeVo.getChangeInfo().setUpdate_time(new Date());
        changeVo.getChangeInfo().setUpdate_user(null == changeVo.getUserId() ? sysUserService.getUser().getId() : changeVo.getUserId());
        changeVo.getChangeInfo().updateById();

        SysUser user = sysUserService.selectById(changeVo.getTrans_form_user());

        if (Base.empty(changeVo.getUserId())) {
            return ResultInfo.success("转单成功", new LogVo(changeVo.getChangeInfo().getId(), "转单", "转单给" + user.getName() + "(" + user.getUser_name() + ")备注："
                    + (StringUtils.isBlank(changeVo.getChangeInfo().getRemark()) ? StringUtils.EMPTY : changeVo.getChangeInfo().getRemark())));
        } else {
            return ResultInfo.success("转单成功", new LogVo(changeVo.getUserId(), changeVo.getChangeInfo().getId(), "转单", "转单给" + user.getName() + "(" + user.getUser_name() + ")备注："
                    + (StringUtils.isBlank(changeVo.getChangeInfo().getRemark()) ? StringUtils.EMPTY : changeVo.getChangeInfo().getRemark())));
        }

    }

    /**
     * 挂起、恢复、关单
     * 更新方法
     */
    public ResultInfo toSuspend(ChangeInfo changeInfo) {
        String title = "";
        String content = "";

        changeInfo.setUpdate_time(new Date());
        changeInfo.setUpdate_user(null == changeInfo.getUserId() ? sysUserService.getUser().getId() : changeInfo.getUserId());
        changeInfo.updateById();

        if (Base.notEmpty(changeInfo.getStatus())) {
            //12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销
            if (changeInfo.getStatus().equals("10")) {//关单
                QueryWrapper<ChangeNodes> queryWrapper = new QueryWrapper<>();
                if (Base.notEmpty(changeInfo.getId())) {
                    queryWrapper.eq("change_id", changeInfo.getId());
                }
                queryWrapper.eq("is_del", "0");
                queryWrapper.orderByDesc("id");
                queryWrapper.last(" limit 1");
                ChangeNodes changeNodes = changeNodesService.selectOne(queryWrapper);
                //时间状态 0:完成，1.未完成
                changeNodes.setTime_status("0");
                changeNodes.setUpdate_time(new Date());
                changeNodes.setUpdate_user(null == changeInfo.getUserId() ? sysUserService.getUser().getId() : changeInfo.getUserId());
                changeNodesService.updateById(changeNodes);

                //新增关单记录
                ChangeClose changeClose = new ChangeClose();
                changeClose.setChange_id(changeInfo.getId());
                changeClose.setCreate_time(new Date());
                changeClose.setClose_time(new Date());
                changeClose.setUpdate_time(new Date());
                changeClose.setCreate_user(null == changeInfo.getUserId() ? sysUserService.getUser().getId() : changeInfo.getUserId());
                changeClose.setClose_user(null == changeInfo.getUserId() ? sysUserService.getUser().getId() : changeInfo.getUserId());
                changeClose.setUpdate_user(null == changeInfo.getUserId() ? sysUserService.getUser().getId() : changeInfo.getUserId());
                changeClose.setIs_del("0");
                changeCloseService.insert(changeClose);

                title = "关单";
                content = "";
            } else if (changeInfo.getStatus().equals("11")) {
                title = "挂起";
                content = changeInfo.getRemark();
            } else {
                //恢复
                title = "恢复";
                content = "";
            }
        }

        if (Base.empty(changeInfo.getUserId())) {
            return ResultInfo.success(title + "成功", new LogVo(changeInfo.getId(), title, content));
        } else {
            return ResultInfo.success(title + "成功", new LogVo(changeInfo.getUserId(), changeInfo.getId(), title, content));
        }

    }

    /**
     * 删除
     */
    public ResultInfo deleteChange(Integer id) {

//        if (!CollectionUtils.isEmpty(.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
//                .eq(QuestionWorkorder::getQuestion_id, id)))) {
//            return ResultInfo.error("该问题已关联工单，不能删除");
//        }

        changeInfoMapper.update(null, Wrappers.<ChangeInfo>lambdaUpdate()
                .set(ChangeInfo::getIs_del, String.valueOf(1))
                .set(ChangeInfo::getUpdate_user, sysUserService.getUser().getId())
                .set(ChangeInfo::getUpdate_time, new Date())
                .eq(ChangeInfo::getId, id));
        return ResultInfo.success("删除成功", new LogVo(id, "删除变更", "删除变更"));
    }




    public List<ChangesOf> sourcesOfStatistics(){
        return  changeInfoMapper.sourcesOfStatistics() ;
    }


    public List<ChangesOf> categoriesOfStatistical(){
        return  changeInfoMapper.categoriesOfStatistical() ;
    }


    public List<ChangesOf> typesOfStatistical(){
        return  changeInfoMapper.typesOfStatistical() ;
    }
}
