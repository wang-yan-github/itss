package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.QuestionDao;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.enums.QuestionStatusEnums;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.utils.DataUtils;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.*;
import jdk.nashorn.internal.runtime.Undefined;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ClassName: QuestionManageService
 * Description: 问题管理Service
 *
 * @author zhangdequan
 */
@Service
@Transactional
public class QuestionManageService extends BaseService<QuestionDao, Question> {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QuestionWorkorderService questionWorkorderService;
    @Autowired
    private QuestionChangeService questionChangeService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ChangeInfoService changeInfoService;
    @Autowired
    private QuestionReviewerService questionReviewerService;
    @Autowired
    private QuestionCategoryReviewerService questionCategoryReviewerService;
    @Autowired
    private KnowledgeInfoService knowledgeInfoService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private QuestionSourceMapper questionSourceMapper;
    @Autowired
    private SlaQuestionFirstMapper slaQuestionFirstMapper;
    @Autowired
    private QuestionCategoryMapper questionCategoryMapper;
    @Autowired
    private EventCategoryMapper eventCategoryMapper;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private QuestionFileService questionFileService;
    @Autowired
    private SlaQuestionService slaQuestionService;
    @Autowired
    private SlaQuestionConfigService slaQuestionConfigService;
    @Autowired
    private SysLogMapper sysLogMapper;
    @Value("${file.upload-path}")
    private String uploadPath;


    /**
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exportQuestionManExcel(HttpServletResponse response) {


        QuestionVo beanParam = new QuestionVo();
        List<QuestionVo> list = questionMapper.toList(beanParam);
        for (QuestionVo questionVo : list) {
            questionVo.setWorkorders(questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                    .eq(QuestionWorkorder::getQuestion_id, questionVo.getId())));
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("code", "问题单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("status_name", "状态");
        writer.addHeaderAlias("now_operator_user_name", "当前操作人");
        writer.addHeaderAlias("solve_time", "解决时间");
        writer.addHeaderAlias("create_user_name", "创建人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.setOnlyAlias(true);
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


    public PageInfo<QuestionVo> toList(Integer pageIndex, Integer pageSize, QuestionVo beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<QuestionVo> questionVos = questionMapper.toList(beanParam);
        for (QuestionVo questionVo : questionVos) {
            questionVo.setWorkorders(questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                    .eq(QuestionWorkorder::getQuestion_id, questionVo.getId())));
            String tempTime = tempTimes(questionVo.getId(), questionVo.getSla_time(), questionVo.getSla_exceed_time(), questionVo.getIsFitSla());
            questionVo.setSla_surplus_time(tempTime);
        }
        PageInfo<QuestionVo> page = new PageInfo<>(questionVos);

        return page;
    }

    public List<QuestionVo> toList(QuestionVo beanParam) {
        List<QuestionVo> questionVos = questionMapper.toList(beanParam);
        for (QuestionVo questionVo : questionVos) {
            questionVo.setWorkorders(questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                    .eq(QuestionWorkorder::getQuestion_id, questionVo.getId())));
            String tempTime = tempTimes(questionVo.getId(), questionVo.getSla_time(), questionVo.getSla_exceed_time(), questionVo.getIsFitSla());
            questionVo.setSla_surplus_time(tempTime);
        }
        return questionVos;
    }

    public List<QuestionVo> getList(QuestionVo beanParam) {
        List<QuestionVo> list = questionMapper.toList(beanParam);
//        for (QuestionVo questionVo : questionVos) {
//            questionVo.setWorkorders(questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
//                    .eq(QuestionWorkorder::getQuestion_id, questionVo.getId())));
//        }
        return list;
    }

    public ResultInfo getById(Integer id, Integer user_id) {
        SysUser currentUser = sysUserService.getUserData(user_id);
        QuestionVo questionVo = new QuestionVo();
        Map<Integer, SysUser> map = getUserNameMap();
        Question question = selectById(id);

        BeanUtils.copyProperties(question, questionVo);
        //状态名称
        questionVo.setStatus_name(QuestionStatusEnums.getValue(question.getStatus()));
        //获取用户名称
        questionVo.setCreate_user_name(getName(map, question.getCreate_user()));
        questionVo.setHandle_user_name(getName(map, question.getHandle_user()));
        questionVo.setAppraisal_user_name(getName(map, question.getAppraisal_user()));
        questionVo.setSolve_user_name(getName(map, question.getSolve_user()));
        questionVo.setClearance_user_name(getName(map, question.getClearance_user()));
        //当前操作人名称
        questionVo.setNow_operator_user_name(getName(map, question.getNow_operator_user()));
        //类别名称
        QuestionCategory questionCategory = questionCategoryMapper.selectById(question.getCategory_id());
        if (Base.notEmpty(questionCategory)) {
            questionVo.setCategory_name(questionCategory.getName());
        }
        //优先级名称
        SlaQuestionFirst slaQuestionFirst = slaQuestionFirstMapper.selectById(question.getPriority_id());
        if (Base.notEmpty(slaQuestionFirst)) {
            questionVo.setPriority_name(slaQuestionFirst.getFirst_name());
        }
        //来源名称
        QuestionSource questionSource = questionSourceMapper.selectById(question.getSource_id());
        if (Base.notEmpty(questionSource)) {
            questionVo.setSource_name(questionSource.getName());
        }

        //关联审核人
        questionVo.setQuestionReviewers(Collections.emptyList());
        List<QuestionReviewer> questionReviewerList = questionReviewerService.selectList(Wrappers.<QuestionReviewer>lambdaQuery()
                .eq(QuestionReviewer::getQuestion_id, id));
        Integer count = 0;
        if (!CollectionUtils.isEmpty(questionReviewerList)) {
            for (QuestionReviewer questionReviewer : questionReviewerList) {
                SysUser user = sysUserService.selectById(questionReviewer.getReview_user());
                questionReviewer.setReview_user_name(null == user ? "" : user.getName());
                if (null != questionReviewer.getUpdate_time()) {
                    count++;
                }
            }
            questionVo.setQuestionReviewers(questionReviewerList);
            questionVo.setQuestionReviewerCount(count);
        }

        //关联工单事件表,拿出列表数据
        List<QuestionWorkorder> workorders = questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                .eq(QuestionWorkorder::getQuestion_id, id));
        questionVo.setEvents(Collections.emptyList());
        if (!CollectionUtils.isEmpty(workorders)) {
            List<Integer> idList = workorders.stream().map(QuestionWorkorder::getEvent_id).collect(Collectors.toList());
            EventVo eventVo = new EventVo();
            eventVo.setIds(idList);
            questionVo.setEvents(eventService.getList(eventVo));
        }

        //关联变更表,拿出变更表数据
        List<QuestionChange> changes = questionChangeService.selectList(Wrappers.<QuestionChange>lambdaQuery()
                .eq(QuestionChange::getQuestion_id, id));
        questionVo.setChangeInfos(Collections.emptyList());
        if (!CollectionUtils.isEmpty(changes)) {
            List<Integer> idList = changes.stream().map(QuestionChange::getChange_id).collect(Collectors.toList());
            ChangeVo changeVo = new ChangeVo();
            changeVo.setIds(idList);
            questionVo.setChangeInfos(changeInfoService.getList(changeVo));
        }

        //关联日志
        questionVo.setLogs(Collections.emptyList());
        List<SysLog> sysLogs = sysLogMapper.selectList(Wrappers.<SysLog>lambdaQuery().
                eq(SysLog::getBill_id, id).eq(SysLog::getModule, LogEnums.LOG_QUESTION.getValue()));
        if (!CollectionUtils.isEmpty(sysLogs)) {
            questionVo.setLogs(sysLogs);
        }
//        QuestionLog questionLog = new QuestionLog();
//        questionLog.setQuestion_id(id);
//        List<QuestionLog> questionLogList = questionLogService.getList(questionLog);
//
//        questionVo.setQuestionLogs(questionLogList);

        //属性信息 附件
        List<FileManage> fileManages = questionFileService.getFileManageByType(id, 1);
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
        questionVo.setFileManageInfoList(fileManages);

        //处理信息 附件
        List<FileManage> fileManages1 = questionFileService.getFileManageByType(id, 2);
        fileManages1.forEach(x -> {
            String path = uploadPath + File.separator + x.getFile_name();
            File file = new File(path);
            if (file.exists()) {
                String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                x.setBase64(base64);
            } else {
                x.setBase64("");
            }
        });
        questionVo.setFileManageHandleList(fileManages1);

        questionVo.setAudit_user_name(currentUser.getName());
        questionVo.setAudit_user(currentUser.getId());
        return ResultInfo.success(questionVo);
    }


    /**
     * 添加我的问题
     */
    public ResultInfo addMyQuestion(QuestionVo bean, List<MultipartFile> files) {
        if (null == bean) {
            return ResultInfo.error("入参数据为空!");
        }
        Question question = new Question();

        BeanUtils.copyProperties(bean, question);

        //获取用户信息
        SysUser sysUser = null ;
        if(null == bean.getUserId()){
            sysUser = sysUserService.getUser();
        }else {
            sysUser = sysUserService.selectById(bean.getUserId());
        }
        question.setCode(DataUtils.getNo(DataType.QUESTION_TYPE));
        // 删除状态
        question.setIs_del(String.valueOf(0));

        // 创建时间
        question.setCreate_time(new Date());
        // 创建者
        question.setCreate_user(sysUser.getId());
        //鉴定人 问题类别-7.4.2项规定了问题的鉴定人、审核人（可多级）
        QuestionCategory questionCategory = questionCategoryMapper.selectById(bean.getCategory_id());
        if (null != questionCategory) {
            question.setAppraisal_user(questionCategory.getAppraisal_user());
        }
        //当前操作人
        question.setNow_operator_user(null == questionCategory ? sysUser.getId() : questionCategory.getAppraisal_user());
        // 问题单发起后，状态为“待鉴定”，可在【问题处理】中查看到，指定的鉴定人可对该问题进行鉴定。
        question.setStatus(QuestionStatusEnums.STATUS_IDENT.getType());
        //期望解决时间
        if (Base.notEmpty(bean.getHope_solve_time())) {
            question.setHope_solve_time(new DateTime(bean.getHope_solve_time()).toString("yyyy-MM-dd"));
        }
        //设置sla初始时间0
        question.setSla_time(0);
        question.setSla_time(0);
        question.setIsFitSla("1");
        //根据用户 查询 处理服务群组: *事件工程师
        insert(question);

        //根据问题类别查询 问题类别审核人关联表
        QuestionCategoryReviewer questionCategoryReviewer = new QuestionCategoryReviewer();
        questionCategoryReviewer.setCategory_id(bean.getCategory_id());
        List<QuestionCategoryReviewer> questionCategoryReviewerList = questionCategoryReviewerService.getList(questionCategoryReviewer);
        for (QuestionCategoryReviewer questionCategoryReviewer1 : questionCategoryReviewerList) {
            //新增问题审核人  问题审核人关联表
            QuestionReviewer questionReviewer = new QuestionReviewer();
            //2	问题id
            questionReviewer.setQuestion_id(question.getId());
            //3	审核人 用户表id
            questionReviewer.setReview_user(questionCategoryReviewer1.getReview_user());
            //排序
            questionReviewer.setSort(questionCategoryReviewer1.getSort());
            questionReviewerService.addQuestionReviewer(questionReviewer);
        }

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                QuestionFile questionFile = new QuestionFile();
                questionFile.setQuestion_id(question.getId());
                questionFile.setFile_id(id);
                //类型 1问题新增附件 2问题处理附件
                questionFile.setType(1);
                questionFileService.addQuestionFile(questionFile);
            }
        }

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("添加成功!", new LogVo(question.getId(), "开单", StringUtils.EMPTY));
        } else {
            return ResultInfo.success("添加成功!", new LogVo(question.getUserId(), question.getId(), "开单", StringUtils.EMPTY));
        }

    }

    /**
     * 修改我的问题
     * 对于处于‘鉴定拒绝’、‘待鉴定’状态的问题，
     * 当问题创建人执行修改操作并保存后，问题工单进入‘待鉴定’状态且调用修改后的问题类别中配置的问题鉴定人和审核人执行业务流转；
     * 管理员对问题进行修改操作后，仅影响问题内容信息，但不会对问题业务节点和流向造成影响；
     */
    public ResultInfo editMyQuestion(QuestionVo bean, List<MultipartFile> files, List<MultipartFile> filesHandle) {
        if (null == bean) {
            return ResultInfo.error("入参数据为空!");
        }
        Question question2 = selectById(bean.getId());
        Question question = new Question();

        BeanUtils.copyProperties(bean, question);

        SysUser sysUser = sysUserService.getUser();
        //当问题创建人执行修改操作并保存后，问题工单进入‘待鉴定’状态
//        if (sysUser.getSysRoles().indexOf("") > 0) {
//            question.setStatus("1");
//        }

        //鉴定人 问题类别-7.4.2项规定了问题的鉴定人、审核人（可多级）
        QuestionCategory questionCategory = questionCategoryMapper.selectById(bean.getCategory_id());
        question.setAppraisal_user(questionCategory.getAppraisal_user());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //期望解决时间
        if (Base.notEmpty(bean.getHope_solve_time())) {
            question.setHope_solve_time(new DateTime(bean.getHope_solve_time()).toString("yyyy-MM-dd"));
        }
        updateById(question);

        //若问题类别发生变更，则修改问题审核人
        if (!question.getCategory_id().equals(question2.getCategory_id())) {
            //删除之前的问题关联审核人
            if (Base.notEmpty(question.getId())) {
                QueryWrapper<QuestionReviewer> questionReviewerQueryWrapper = new QueryWrapper<>();
                questionReviewerQueryWrapper.eq("question_id", question.getId());
                questionReviewerService.delete(questionReviewerQueryWrapper);
            }

            //根据问题类别查询 问题类别审核人关联表
            QuestionCategoryReviewer questionCategoryReviewer = new QuestionCategoryReviewer();
            questionCategoryReviewer.setCategory_id(bean.getCategory_id());
            List<QuestionCategoryReviewer> questionCategoryReviewerList = questionCategoryReviewerService.getList(questionCategoryReviewer);
            for (QuestionCategoryReviewer questionCategoryReviewer1 : questionCategoryReviewerList) {
                //新增问题审核人  问题审核人关联表
                QuestionReviewer questionReviewer = new QuestionReviewer();
                //2	问题id
                questionReviewer.setQuestion_id(bean.getId());
                //3	审核人 用户表id
                questionReviewer.setReview_user(questionCategoryReviewer1.getReview_user());
                //排序
                questionReviewer.setSort(questionCategoryReviewer1.getSort());
                questionReviewerService.addQuestionReviewer(questionReviewer);
            }
        }

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        //附件上传 保存
        //属性信息 是否存在文件删除
        String uploadedFiles = bean.getUploadedFiles();
        List<FileManage> fileManages = questionFileService.getFileManageByType(bean.getId(), 1);
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<QuestionFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(QuestionFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(QuestionFile::getQuestion_id, bean.getId())
                    .in(QuestionFile::getFile_id, original);
            questionFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                QuestionFile questionFile = new QuestionFile();
                questionFile.setQuestion_id(question.getId());
                questionFile.setFile_id(id);
                //类型 1问题新增附件 2问题处理附件
                questionFile.setType(1);
                questionFileService.addQuestionFile(questionFile);
            }
        }
        //处理信息 是否存在文件删除
        String uploadedHandleFiles = bean.getUploadedHandleFiles();
        List<FileManage> fileManagesHandle = questionFileService.getFileManageByType(bean.getId(), 2);
        List<String> originalHandle = fileManagesHandle.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedHandleFiles)) {
            List<String> ids = Arrays.asList(uploadedHandleFiles.split(","));
            originalHandle.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(originalHandle)) {
            LambdaUpdateWrapper<QuestionFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(QuestionFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(QuestionFile::getQuestion_id, bean.getId())
                    .in(QuestionFile::getFile_id, originalHandle);
            questionFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(filesHandle)) {
            for (MultipartFile file : filesHandle) {
                int id = fileManageService.fileUpload(file);
                QuestionFile questionFile = new QuestionFile();
                questionFile.setQuestion_id(question.getId());
                questionFile.setFile_id(id);
                //类型 1问题新增附件 2问题处理附件
                questionFile.setType(2);
                questionFileService.addQuestionFile(questionFile);
            }
        }

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("修改成功", new LogVo(question.getId(), "修改", "问题"));
        } else {
            return ResultInfo.success("修改成功", new LogVo(question.getUserId(), question.getId(), "修改", "问题"));
        }


    }

    /**
     * 撤销操作
     * “待鉴定”和“鉴定拒绝”状态下的问题单可被撤销。
     * 撤消后状态变为“已撤销”，可在“我的问题”和“已结束问题”中查看
     * 已撤销（问题创建人功能范围：备注；问题管理员功能范围：备注、修改、删除、生成知识、打印、修改关联；）
     */
    public ResultInfo editStatusRevoke(QuestionVo bean) {
        Question question = selectById(bean.getId());

        SysUser sysUser = sysUserService.getUserData(bean.getUserId());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //6已撤销
        question.setStatus("6");
        //当前操作人
//        question.setNow_operator_user();
        updateById(question);

        if (Base.empty(bean.getUserId())) {
            return ResultInfo.success("撤销成功", new LogVo(question.getId(), "撤销", bean.getRemark()));
        } else {
            return ResultInfo.success("撤销成功", new LogVo(bean.getUserId(), question.getId(), "撤销", bean.getRemark()));
        }

    }

    /**
     * 导出我的问题
     */
    public ResultInfo exportMyQuestions(HttpServletResponse response) {
        Map<Integer, SysUser> users = getUserNameMap();
        List<QuestionVo> list = questionMapper.toExportList();
        if (!CollectionUtils.isEmpty(list)) {
            for (QuestionVo member : list) {
                member.setStatus_name(QuestionStatusEnums.getValue(member.getStatus()));
                member.setCreate_user_name(getUserName(users, member.getCreate_user()));
                member.setHandle_user_name(getUserName(users, member.getHandle_user()));
                member.setSolve_user_name(getUserName(users, member.getSolve_user()));
                member.setAppraisal_user_name(getUserName(users, member.getAppraisal_user()));
                member.setAudit_user_name(getUserName(users, member.getAudit_user()));
                member.setClearance_user_name(getUserName(users, member.getClearance_user()));

                //关联工单事件表,拿出列表数据
                List<QuestionWorkorder> workorders = questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                        .eq(QuestionWorkorder::getQuestion_id, member.getId()));
                if (!CollectionUtils.isEmpty(workorders)) {
                    member.setQuestionWorkorderIds(workorders.stream().map(QuestionWorkorder::getEvent_id).collect(Collectors.toList()));
                }

                //关联变更表,拿出变更表数据
                List<QuestionChange> changes = questionChangeService.selectList(Wrappers.<QuestionChange>lambdaQuery()
                        .eq(QuestionChange::getQuestion_id, member.getId()));
                if (!CollectionUtils.isEmpty(changes)) {
                    member.setQuestionChangeIds(changes.stream().map(QuestionChange::getChange_id).collect(Collectors.toList()));
                }
            }

        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("status_name", "状态");
        writer.addHeaderAlias("code", "问题单号");
        writer.addHeaderAlias("create_user", "创建人");
        writer.addHeaderAlias("create_user_name", "创建人名称");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("handle_user", "处理人");
        writer.addHeaderAlias("handle_user_name", "处理人名称");
        writer.addHeaderAlias("source_name", "来源");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("description", "描述");
        writer.addHeaderAlias("solve_user", "解决人");
        writer.addHeaderAlias("solve_user_name", "解决人名称");
        writer.addHeaderAlias("solve_content", "解决方案");
        writer.addHeaderAlias("solve_time", "解决时间");
        writer.addHeaderAlias("appraisal_user", "鉴定人");
        writer.addHeaderAlias("appraisal_user_name", "鉴定人名称");
        writer.addHeaderAlias("appraisal_time", "鉴定时间");
        writer.addHeaderAlias("appraisal_opinion", "鉴定意见");
        writer.addHeaderAlias("audit_user", "审核人");
        writer.addHeaderAlias("audit_user_name", "审核人名称");
        writer.addHeaderAlias("audit_time", "审核时间");
        writer.addHeaderAlias("audit_content", "审核意见");
        writer.addHeaderAlias("clearance_time", "关单时间");
        writer.addHeaderAlias("clearance_user", "关单人");
        writer.addHeaderAlias("clearance_user_name", "关单人名称");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        //字段未关联
        writer.addHeaderAlias("title", "已生成知识");
        writer.addHeaderAlias("sla_time", "sla");
        writer.addHeaderAlias("time_limit", "关单时限");
        writer.addHeaderAlias("resolution_minutes", "解决分钟数");
        //字段未关联
//        writer.addHeaderAlias("title", "是否符合sla");
        writer.addHeaderAlias("questionWorkorderIds", "关联工单");
        writer.addHeaderAlias("questionChangeIds", "关联变更");
        writer.addHeaderAlias("questionLogs", "日志");
        writer.setOnlyAlias(true);
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
     * 用户名称获得方法
     */
    public String getUserName(Map<Integer, SysUser> map, Integer id) {
        if (null == id || CollectionUtils.isEmpty(map)) {
            return StringUtils.EMPTY;
        }
        if (null == map.get(id)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(map.get(id).getUser_name())) {
            return StringUtils.EMPTY;
        }
        return map.get(id).getUser_name();

    }

    /**
     * 姓名获得方法
     */
    public String getName(Map<Integer, SysUser> map, Integer id) {
        if (null == id || CollectionUtils.isEmpty(map)) {
            return StringUtils.EMPTY;
        }
        if (null == map.get(id)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(map.get(id).getName())) {
            return StringUtils.EMPTY;
        }
        return map.get(id).getName();

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
     * 得到问题类别的键值对Map
     */
    public Map<Integer, QuestionCategory> getQuestionCategoryMap() {
        List<QuestionCategory> questionCategories = questionCategoryMapper.selectList(Wrappers.<QuestionCategory>lambdaQuery().eq(QuestionCategory::getIs_del, "0"));
        if (CollectionUtils.isEmpty(questionCategories)) {
            return Collections.emptyMap();
        }
        return questionCategories.stream().collect(Collectors.toMap(QuestionCategory::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 得到优先级的键值对Map
     */
    public Map<Integer, SlaQuestionFirst> getQuestionPriorityMap() {
        List<SlaQuestionFirst> questionPriorities = slaQuestionFirstMapper.selectList(Wrappers.<SlaQuestionFirst>lambdaQuery().eq(SlaQuestionFirst::getIs_del, "0"));
        if (CollectionUtils.isEmpty(questionPriorities)) {
            return Collections.emptyMap();
        }
        return questionPriorities.stream().collect(Collectors.toMap(SlaQuestionFirst::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 描述： 问题管理删除问题
     * 作者： xuaolong
     *
     * @param id
     * @return
     */
    public ResultInfo deleteQuestion(Integer id, Integer user_id) {

        if (!CollectionUtils.isEmpty(questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                .eq(QuestionWorkorder::getQuestion_id, id)))) {
            return ResultInfo.error("该问题已关联工单，不能删除");
        }
        questionMapper.update(null, Wrappers.<Question>lambdaUpdate()
                .set(Question::getIs_del, String.valueOf(1))
                .set(Question::getUpdate_user, sysUserService.getUserData(user_id).getId())
                .set(Question::getUpdate_time, new Date())
                .eq(Question::getId, id));

        //关联工单数据删除
//        UpdateWrapper<QuestionWorkorder> workorderUpdateWrapper = new UpdateWrapper();
//        workorderUpdateWrapper.eq("question_id", id);
//        workorderUpdateWrapper.set("is_del", "1");
//        questionWorkorderService.update(null, workorderUpdateWrapper);
//
//        //关联变更数据删除
//        UpdateWrapper<QuestionChange> changeUpdateWrapper = new UpdateWrapper();
//        changeUpdateWrapper.eq("question_id", id);
//        changeUpdateWrapper.set("is_del", "1");
//        questionChangeService.update(null, changeUpdateWrapper);

        return ResultInfo.success("删除成功", new LogVo(id, "删除问题", "删除问题"));
    }


    /**
     * 描述： 问题管理修改关联
     * 作者： thr
     *
     * @return
     */
    public ResultInfo modifyRelevance(QuestionVo questionVo) {
        //关联的事件工单
        questionWorkorderService.onSave(questionVo.getId(), questionVo.getEvents());

        //关联的变更
        questionChangeService.onSave(questionVo.getId(), questionVo.getChangeInfos());
        return ResultInfo.success();
    }

    /**
     * 描述： 生成知识
     * 作者： xuaolong
     *
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo generateKnowledge(KnowledgeInfo knowledgeInfo) {
        return knowledgeInfoService.addKnowledgeInfo(knowledgeInfo, Collections.emptyList());
    }


    @Transactional
    public ResultInfo importQuestionList(MultipartFile file) {
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

            List<Question> success = new ArrayList<>();
            List<Question> error = new ArrayList<>();
            List<Question> all = new ArrayList<>();


            if (size > 1) {
                for (int i = 1; i < size; i++) {
                    Question question = new Question();
                    List<Object> line = data.get(i);
                    if (StringUtils.isEmpty(line.get(0).toString()) || StringUtils.isEmpty(line.get(1).toString()) || StringUtils.isEmpty(line.get(2).toString()) ||
                            StringUtils.isEmpty(line.get(3).toString()) || StringUtils.isEmpty(line.get(4).toString()) || StringUtils.isEmpty(line.get(5).toString())) {

                        question.setSource_name(line.get(0).toString());
                        question.setPriority_name(line.get(1).toString());
                        question.setCategory_name(line.get(2).toString());
                        question.setTitle(line.get(3).toString());
                        question.setDescription(line.get(4).toString());
                        question.setHope_solve_time(line.get(5).toString());

                        question.setIs_success("失败");
                        error.add(question);
                        all.add(question);
                    } else {
                        //来源
                        QueryWrapper<QuestionSource> queryWrapper2 = new QueryWrapper<>();
                        queryWrapper2.eq("is_del", "0");
                        queryWrapper2.eq("name", line.get(0).toString());
                        List<QuestionSource> questionSources = questionSourceMapper.selectList(queryWrapper2);

                        if (!CollectionUtils.isEmpty(questionSources)) {
                            question.setSource_id(questionSources.get(0).getId());
                        }
                        question.setSource_name(line.get(0).toString());

                        //优先级 priority_id
                        QueryWrapper<SlaQuestionFirst> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("first_name", line.get(1).toString());
                        queryWrapper1.eq("is_del", "0");
                        List<SlaQuestionFirst> slaQuestionFirsts = slaQuestionFirstMapper.selectList(queryWrapper1);
                        if (!CollectionUtils.isEmpty(slaQuestionFirsts)) {
                            question.setPriority_id(slaQuestionFirsts.get(0).getId());
                        }
                        question.setPriority_name(line.get(1).toString());

                        //类别
                        QueryWrapper<QuestionCategory> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("is_del", "0");
                        queryWrapper.eq("name", line.get(2).toString());

                        List<QuestionCategory> questionCategoryList = questionCategoryMapper.selectList(queryWrapper);
                        if (!CollectionUtils.isEmpty(questionCategoryList)) {
                            question.setCategory_id(questionCategoryList.get(0).getId());
                            question.setAppraisal_user(questionCategoryList.get(0).getAppraisal_user());
                            question.setNow_operator_user(questionCategoryList.get(0).getAppraisal_user());
                        }
                        question.setCategory_name(line.get(2).toString());


                        question.setTitle(line.get(3).toString());
                        question.setDescription(line.get(4).toString());
                        question.setHope_solve_time(line.get(5).toString());

                        question.setCreate_user(sysUserService.getUser().getId());
                        question.setCreate_user_name(sysUserService.getUser().getUser_name());


                        question.setCode(DataUtils.getNo(DataType.QUESTION_TYPE));
                        // 删除状态
                        question.setIs_del(String.valueOf(0));
                        // 创建时间
                        question.setCreate_time(new Date());
                        // 问题单发起后，状态为“待鉴定”，可在【问题处理】中查看到，指定的鉴定人可对该问题进行鉴定。
                        question.setStatus(QuestionStatusEnums.STATUS_IDENT.getType());

                        question.setIs_success("成功");
                        success.add(question);
                        all.add(question);

                        questionMapper.insert(question);
                    }
                }
            }
            Question p = new Question();
            p.setSuccess(success);
            p.setError(error);
            p.setAll(all);
            return ResultInfo.success(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("导入失败！！！");

    }

    /**
     * 问题待办
     * 鉴定审核 同意/拒绝
     * 鉴定页面可对问题信息进行修改，并需指定一处理人。
     * 通过：通过后该问题单状态变为“待解决”，处理人可在【问题处理】中查看并解决。
     * 拒绝：拒绝后该问题单状态变为“鉴定拒绝”，开单人可在【我的问题】中查看并决定是否修改信息继续等待鉴定或撤销。
     * ‘通过’：待解决（创建人功能范围：生成变更、备注；
     * 处理人功能范围：处理、转单、挂起、生成变更、备注；
     * 问题管理员：转办-处理人、备注、修改、撤销、删除、生成知识、打印、修改关联）；
     * ‘拒绝’：鉴定拒绝（创建人功能范围：撤销、修改、备注；
     * 问题管理员：备注、修改、撤销、删除、生成知识、打印、修改关联）；
     *
     * @author thr
     */
    public ResultInfo onDbAppraisalUpd(QuestionVo bean, List<MultipartFile> files) {
        Question question = new Question();

        BeanUtils.copyProperties(bean, question);
        SysUser sysUser = sysUserService.getUserData(bean.getCurrent_user());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //鉴定意见
        question.setAppraisal_opinion(bean.getAppraisal_opinion());
        //审核状态 1同意 2拒绝
        String title = "";
        if (bean.getAuditStatus().equals("1")) {
            //3待解决
            question.setStatus("3");
            title = "鉴定通过";
        } else if (bean.getAuditStatus().equals("2")) {
            //7鉴定拒绝
            question.setStatus("7");
            title = "鉴定拒绝";
        }

        //当前操作人
        question.setNow_operator_user(question.getHandle_user());
//        //17 处理人
//        question.setHandle_user(sysUser.getId());
        //14 鉴定人 用户表id
        question.setAppraisal_user(sysUser.getId());
        //15 鉴定时间
        question.setAppraisal_time(new Date());
        updateById(question);

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        //附件上传 保存
        //属性信息 是否存在文件删除
        String uploadedFiles = bean.getUploadedFiles();
        List<FileManage> fileManages = questionFileService.getFileManageByType(bean.getId(), 1);
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<QuestionFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(QuestionFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(QuestionFile::getQuestion_id, bean.getId())
                    .in(QuestionFile::getFile_id, original);
            questionFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                QuestionFile questionFile = new QuestionFile();
                questionFile.setQuestion_id(question.getId());
                questionFile.setFile_id(id);
                //类型 1问题新增附件 2问题处理附件
                questionFile.setType(1);
                questionFileService.addQuestionFile(questionFile);
            }
        }

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("操作成功", new LogVo(question.getId(), title, question.getAppraisal_opinion()));
        } else {
            return ResultInfo.success("操作成功", new LogVo(question.getUserId(), question.getId(), title, question.getAppraisal_opinion()));
        }

    }

    /**
     * 问题待办
     * 处理 保存/解决完成
     * 处理页面，填写解决方案，可选择“解决完成”或“保存”。
     * 解决完成：状态变为“待审核”，记录解决时间等信息，可在【问题管理】中查看。
     * 完成：只保存解决方案，不进入下一环节。
     * ‘解决完成’：待审核（创建人功能范围：生成变更、备注；审核人功能范围：审核、备注；
     * 问题管理员：转办-审核人、备注、修改、撤销、删除、生成知识、打印、修改关联；）；
     *
     * @author thr
     */
    public ResultInfo onDbHandleUpd(QuestionVo bean, List<MultipartFile> files) {
        //日志记录
//        QuestionLog questionLog = new QuestionLog();

        Question question = new Question();

        BeanUtils.copyProperties(bean, question);

        SysUser sysUser = sysUserService.getUserData(bean.getCurrent_user());
        question.setHandle_user(sysUser.getId());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //解决方案
        question.setSolve_content(bean.getSolve_content());
        //审核状态 1保存 2解决完成
        String title = "";
        String content = "";
        if (bean.getAuditStatus().equals("2")) {
            //2待审核
            question.setStatus("2");
            title = "解决完成";
            content = question.getSolve_content();
        }
        //18 解决人 用户表id
        question.setSolve_user(sysUser.getId());
        //19 解决时间
        question.setSolve_time(new Date());
        // 解决分钟数
        //时间处理类
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        //从对象中拿到时间
        long createTime = 0;
        try {
            createTime = df.parse(df.format(question.getCreate_time())).getTime();
            long diff = (currentTime - createTime) / 1000 / 60;
            System.out.println("当前系统时间为：" + currentTime + "下单时间为：" + createTime + "两个时间差为：" + diff);
            question.setResolution_minutes(String.valueOf(diff));
        } catch (ParseException e) {
        }

        //若多人审核流程，则查询下一个审核人
        Integer questionReviewerId = null;
        QuestionReviewer questionReviewer1 = new QuestionReviewer();
        questionReviewer1.setQuestion_id(question.getId());
        List<QuestionReviewer> questionReviewerList = questionReviewerService.getList(questionReviewer1);
        for (QuestionReviewer questionReviewer2 : questionReviewerList) {
            if (Base.empty(questionReviewer2.getTitle())) {
                questionReviewerId = questionReviewer2.getReview_user();
                break;
            }
        }
        //当前操作人
        question.setNow_operator_user(questionReviewerId);

        updateById(question);

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        //附件上传 保存
        //处理信息 是否存在文件删除
        String uploadedFiles = bean.getUploadedHandleFiles();
        List<FileManage> fileManages = questionFileService.getFileManageByType(bean.getId(), 2);
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<QuestionFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(QuestionFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(QuestionFile::getQuestion_id, bean.getId())
                    .in(QuestionFile::getFile_id, original);
            questionFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                QuestionFile questionFile = new QuestionFile();
                questionFile.setQuestion_id(question.getId());
                questionFile.setFile_id(id);
                //类型 1问题新增附件 2问题处理附件
                questionFile.setType(2);
                questionFileService.addQuestionFile(questionFile);
            }
        }

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("操作成功", new LogVo(question.getId(), title, content));
        } else {
            return ResultInfo.success("操作成功", new LogVo(question.getUserId(), question.getId(), title, content));
        }

    }

    /**
     * 问题待办
     * 审核 同意/拒绝
     * 填写审核意见，可通过或拒绝。
     * 通过：问题单状态变为“审核通过”，开单人可在【我的问题】中查看到，并可进行关单操作。
     * 拒绝：问题单状态变为“审核拒绝”，开单人可在【我的问题】中查看到，可进行修改或进行撤销操作。
     * ‘通过’：‘审核通过’（创建人功能范围：关单、生成变更、生成知识、备注；
     * 问题管理员：关单、备注、修改、撤销、删除、生成知识、打印、修改关联；）；
     * ‘拒绝’：‘审核拒绝’（问题创建人：备注；问题处理人：处理、转单、挂起、备注；
     * 问题管理员：转办-处理人、备注、修改、撤销、删除、生成知识、打印、修改关联；）；
     *
     * @author thr
     */
    public ResultInfo onDbAuditUpd(QuestionVo bean) {
        //日志记录
//        QuestionLog questionLog = new QuestionLog();

        Question question = new Question();

        BeanUtils.copyProperties(bean, question);
        SysUser sysUser = sysUserService.getUserData(bean.getCurrent_user());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //审核状态 1同意 2拒绝
        String title = "";
        if (bean.getAuditStatus().equals("1")) {

//            questionLog.setTitle("审核通过");
            title = "审核通过";

            //若多人审核流程，则查询下一个审核人
            Integer questionReviewerId = null;
            QuestionReviewer questionReviewer1 = new QuestionReviewer();
            questionReviewer1.setQuestion_id(question.getId());
            List<QuestionReviewer> questionReviewerList = questionReviewerService.getList(questionReviewer1);
            List<Integer> idList = questionReviewerList.stream().map(x -> x.getId()).collect(Collectors.toList());
            int auditCount = questionReviewerList.size();
            for (QuestionReviewer questionReviewer2 : questionReviewerList) {
                auditCount--;
                if (Base.empty(questionReviewer2.getTitle())) {
                    questionReviewerId = questionReviewer2.getId();
                    break;
                } else {
                    if (questionReviewer2.getTitle().equals("审核拒绝")) {
                        questionReviewerId = questionReviewer2.getId();
                        break;
                    }
                }
            }
            if (Base.notEmpty(questionReviewerId)) {
                //审核记录
                QuestionReviewer questionReviewer = new QuestionReviewer();
                questionReviewer.setId(questionReviewerId);
                //21 审核人 用户表id
                questionReviewer.setReview_user(sysUser.getId());
                questionReviewer.setTitle(title);
                questionReviewer.setContent(bean.getAudit_content());
                questionReviewer.setUpdate_user(sysUser.getId());
                questionReviewer.setUpdate_time(new Date());
                ;
                questionReviewerService.editQuestionReviewer(questionReviewer);
            }
            if (auditCount == 0) {
                //4审核通过
                question.setStatus("4");
                //当前操作人 （下一步为关单操作，下一操作人应为开单人）
                question.setNow_operator_user(question.getCreate_user());
            } else {
                //2待审核
                question.setStatus("2");
                //当前操作人
                if (idList.indexOf(questionReviewerId) + 1 < idList.size()) {
                    question.setNow_operator_user(questionReviewerService.selectById(idList.get(idList.indexOf(questionReviewerId) + 1)).getReview_user());
                } else {
                    question.setNow_operator_user(question.getCreate_user());
                }
            }
        } else if (bean.getAuditStatus().equals("2")) {
            //8审核拒绝
            question.setStatus("8");
//            questionLog.setTitle("审核拒绝");
            title = "审核拒绝";
            //当前操作人
            question.setNow_operator_user(question.getHandle_user());
        }

        updateById(question);

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("操作成功", new LogVo(question.getId(), title, bean.getAudit_content()));
        } else {
            return ResultInfo.success("操作成功", new LogVo(question.getUserId(), question.getId(), title, bean.getAudit_content()));
        }

    }

    /**
     * 问题待办
     * 关单
     * 状态为“审核通过”的问题单可关单，关单操作只由开单人进行；
     * 关单时可选择“是否生成知识”，若选择是则页面将跳转至“添加知识”页面，并关闭该问题单，若选择否则直接关单；
     * 关单后，状态变为“关闭”。
     * 关闭（问题创建人：生成知识、备注；问题管理员：备注、修改、撤销、删除、生成知识、打印、修改关联；）
     *
     * @author thr
     */
    public ResultInfo onDbCloseUpd(QuestionVo bean) {
        Question question = new Question();

        BeanUtils.copyProperties(bean, question);
        SysUser sysUser = sysUserService.getUserData(bean.getUserId());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //5关闭
        question.setStatus("5");
        //24 关单人 用户表id
        question.setClearance_user(sysUser.getId());
        //25 关单时间
        question.setClearance_time(new Date());
        updateById(question);

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("关闭成功", new LogVo(question.getId(), "关单", "关单成功"));
        } else {
            return ResultInfo.success("关闭成功", new LogVo(question.getUserId(), question.getId(), "关单", "关单成功"));
        }

    }

    /**
     * 问题待办
     * 转单
     * 可将问题单转单给其他工程师解决。
     * 指定新的处理人
     * 保持原状态
     *
     * @author thr
     */
    public ResultInfo onDbTransferOrderUpd(QuestionVo bean) {
        Question question = new Question();

        BeanUtils.copyProperties(bean, question);
        SysUser sysUser = sysUserService.getUser();
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
//        question.setHandle_user(bean.getHandle_user());
//        question.setTransfer_remark(bean.getTransfer_remark());
        updateById(question);

        //关联的事件工单
        questionWorkorderService.onSave(question.getId(), bean.getEvents());

        //关联的变更
        questionChangeService.onSave(question.getId(), bean.getChangeInfos());

        SysUser user = sysUserService.selectById(question.getHandle_user());

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("转单成功", new LogVo(question.getId(), "转单", "转单给" + user.getName() + "(" + user.getUser_name() + ")备注："
                    + (StringUtils.isBlank(bean.getTransfer_remark()) ? StringUtils.EMPTY : bean.getTransfer_remark())));
        } else {
            return ResultInfo.success("转单成功", new LogVo(question.getUserId(), question.getId(), "转单", "转单给" + user.getName() + "(" + user.getUser_name() + ")备注："
                    + (StringUtils.isBlank(bean.getTransfer_remark()) ? StringUtils.EMPTY : bean.getTransfer_remark())));
        }

    }

    /**
     * 问题待办
     * 转办
     * 1.待鉴定状态的问题：转办到新的鉴定人（指定人）；
     * 2.待解决状态的问题：转办到新的当前处理人（指定人）；
     * 3.待审核状态的问题：转办到新的审核人（指定人）；
     * 4.处理结果审核拒绝状态的问题：转办到新的当前处理人（指定人）；
     * 保持原节点状态
     *
     * @author thr
     */
    public ResultInfo onDbTransferUpd(QuestionVo bean) {
        Question question2 = selectById(bean.getId());
        Question question = new Question();

        BeanUtils.copyProperties(bean, question);

        SysUser sysUser = sysUserService.getUserData(bean.getCurrent_user());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
        if (question.getStatus().equals("1")) {
            //1.待鉴定状态的问题：转办到新的鉴定人（指定人）；
            question.setAppraisal_user(bean.getHandle_user());
            question.setNow_operator_user(bean.getHandle_user());
            question.setHandle_user(question2.getHandle_user());
        } else if (question.getStatus().equals("2")) {
            question.setNow_operator_user(bean.getHandle_user());
            //3.待审核状态的问题：转办到新的审核人（指定人）；
            question.setHandle_user(question2.getHandle_user());
            //若多人审核流程，则查询下一个审核人
            Integer questionReviewerId = null;
            QuestionReviewer questionReviewer1 = new QuestionReviewer();
            questionReviewer1.setQuestion_id(question.getId());
            List<QuestionReviewer> questionReviewerList = questionReviewerService.getList(questionReviewer1);
            for (QuestionReviewer questionReviewer2 : questionReviewerList) {
                if (Base.empty(questionReviewer2.getTitle())) {
                    questionReviewerId = questionReviewer2.getId();
                    break;
                }
            }
            if (Base.notEmpty(questionReviewerId)) {
                //审核记录
                QuestionReviewer questionReviewer = new QuestionReviewer();
                questionReviewer.setId(questionReviewerId);
                //21 审核人 用户表id
                questionReviewer.setReview_user(bean.getHandle_user());
                questionReviewer.setUpdate_user(sysUser.getId());
                questionReviewerService.editQuestionReviewer(questionReviewer);

            }

        }

        updateById(question);

        SysUser user = sysUserService.selectById(bean.getHandle_user());
        if (null == user) {
            return ResultInfo.error("转办失败，指定人不存在");
        }

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("转办成功", new LogVo(question.getId(), "转办", "转办至" + user.getName() + "(" + user.getUser_name() + ")"
                    + (StringUtils.isBlank(bean.getTransfer_remark()) ? StringUtils.EMPTY : "备注：" + bean.getTransfer_remark())));
        } else {
            return ResultInfo.success("转办成功", new LogVo(question.getUserId(), question.getId(), "转办", "转办至" + user.getName() + "(" + user.getUser_name() + ")"
                    + (StringUtils.isBlank(bean.getTransfer_remark()) ? StringUtils.EMPTY : "备注：" + bean.getTransfer_remark())));
        }

    }

    /**
     * 问题待办
     * 挂起
     * 问题暂时没有解决方案或其他原因，将问题单挂起。
     * 状态将变为“挂起”并仍然显示在该列表中。
     * 可进行恢复操作。
     * 挂起（待恢复，处理工程师功能范围：恢复、备注；
     * 问题管理员功能范围：恢复、备注、修改、撤销、删除、生成知识、打印、修改关联；）
     *
     * @author thr
     */
    public ResultInfo onDbHangUpd(QuestionVo bean) {
        Question question = selectById(bean.getId());
        SysUser sysUser = sysUserService.getUserData(bean.getCurrent_user());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        // 挂起前的状态
        question.setHang_status(question.getStatus());
        //9挂起
        question.setStatus("9");
        updateById(question);

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("挂起成功", new LogVo(question.getId(), "挂起", bean.getSuspend_reason()));
        } else {
            return ResultInfo.success("挂起成功", new LogVo(question.getUserId(), question.getId(), "挂起", bean.getSuspend_reason()));
        }

    }

    /**
     * 问题待办
     * 恢复
     * 1.对于已挂起的问题工单，处理工程师和问题管理员可以执行恢复操作，问题恢复到挂起前的状态；
     *
     * @author thr
     */
    public ResultInfo onDbRecoveryUpd(QuestionVo bean) {
        Question question = selectById(bean.getId());
        SysUser sysUser = sysUserService.getUserData(bean.getCurrent_user());
        // 修改者
        question.setUpdate_user(sysUser.getId());
        // 修改时间
        question.setUpdate_time(new Date());
        //3待解决
        question.setStatus(question.getHang_status());
        updateById(question);

        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("恢复成功", new LogVo(question.getId(), "恢复", ""));
        } else {
            return ResultInfo.success("恢复成功", new LogVo(question.getUserId(), question.getId(), "恢复", ""));
        }

    }

    /**
     * 问题统计
     *
     * @author xuaolong
     */
    public QuestionStatisticsVo questionStatistics(Question question) {

        //获取所有符合条件的问题
        List<Question> list = questionMapper.questionStatistics(question);
        QuestionStatisticsVo questionStatisticsVo = new QuestionStatisticsVo();
        questionStatisticsVo.setList(list);
        //把所有问题的问题分类ID 和数量 封装到 map 集合
        Map<Integer, Integer> map = new HashMap<>();

        //问题状态统计
        Map<String, Integer> statusMap = new HashMap<>();
        for (Question temp : list) {
            Integer category_id = temp.getCategory_id();
            if (map.containsKey(category_id)) {
                Integer value = map.get(category_id);
                value = value + 1;
                map.put(category_id, value);
            } else {
                map.put(category_id, 1);
            }
            String status = temp.getStatus();
            String key = QuestionStatusEnums.getValue(status);
            if (map.containsKey(key)) {
                Integer value = statusMap.get(key);
                value = value + 1;
                statusMap.put(key, value);
            } else {
                statusMap.put(key, 1);
            }

        }

        //遍历map 集合
        List<QuestionCategory> questionCategoryList = new ArrayList<>();
        Set<Integer> set = map.keySet();
        for (Integer temp : set) {
            int num = map.get(temp);
            QuestionCategory questionCategory = questionCategoryMapper.selectById(temp);
            questionCategory.setNum(num);
            questionCategoryList.add(questionCategory);
        }
        questionStatisticsVo.setQuestionCategoryList(questionCategoryList);

        //是否生成知识统计
        List<Question> questionKnowledge = questionCategoryMapper.knowledge(question);
        questionStatisticsVo.setKnowledge(questionKnowledge);

        //问题SLA统计
        String[] isFitSlaArr = {"否", "是"};//是否符合SLA 0:否  1：是
        List<Question> isFitSla = questionCategoryMapper.isFitSla(question);
        for(Question q : isFitSla){
            q.setStatus_name(isFitSlaArr[Integer.parseInt(q.getIsFitSla())]);
        }
        questionStatisticsVo.setIsFitSla(isFitSla);

        //问题状态统计
        String[] statusArr = {"", "待鉴定", "待审核", "待解决", "审核通过", "关闭", "已撤销", "鉴定拒绝", "审核拒绝", "挂起"};// 增加状态字典 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
        List<Question> statusList = questionCategoryMapper.status(question);
        for(Question q : statusList){
            q.setStatus_name(statusArr[Integer.parseInt(q.getStatus())]);
        }
        questionStatisticsVo.setStatusList(statusList);


        return questionStatisticsVo;

    }


    /**
     * description:问题统计导出excel
     *
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporQuestionExcel(HttpServletResponse response, Question question) {

        QuestionStatisticsVo questionStatisticsVo = questionStatistics(question);
        List<QuestionCategory> questionCategoryList = questionStatisticsVo.getQuestionCategoryList();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("name", "类型");
        writer.addHeaderAlias("num", "数量");
        writer.setOnlyAlias(true);
        writer.write(questionCategoryList, true);

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
     * 问题增加备注
     *
     * @param question
     * @return
     */
    public ResultInfo addRemark(Question question) {
        updateById(question);
        if (Base.empty(question.getUserId())) {
            return ResultInfo.success("添加备注成功", new LogVo(question.getId(), "备注", question.getRemark()));
        } else {
            return ResultInfo.success("添加备注成功", new LogVo(question.getUserId(), question.getId(), "备注", question.getRemark()));
        }

    }

    /**
     * 问题导出
     *
     * @return
     */
    public ResultInfo exportQuestion(HttpServletResponse response) {

        List<QuestionVo> questionVos = questionMapper.toList(QuestionVo.builder().appraisal_user(sysUserService.getUser().getId()).dataStatus("cyd").build());
        for (QuestionVo questionVo : questionVos) {
            questionVo.setWorkorders(questionWorkorderService.selectList(Wrappers.<QuestionWorkorder>lambdaQuery()
                    .eq(QuestionWorkorder::getQuestion_id, questionVo.getId())));
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("code", "问题编号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("create_user_name", "创建人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("status_name", "状态");
        writer.addHeaderAlias("now_operator_user_name", "当前操作人");
        writer.addHeaderAlias("hope_solve_time", "解决时间");
        writer.setOnlyAlias(true);
        writer.write(questionVos, true);

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
     * 代办问题导出
     *
     * @param response
     * @return
     */
    public ResultInfo exportDBQuestion(HttpServletResponse response, QuestionVo vo) {
        List<Question> questionVos = dbList(vo);

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("code", "问题编号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("create_user_name", "创建人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("status_name", "状态");
        writer.addHeaderAlias("now_operator_user_name", "当前操作人");
        writer.addHeaderAlias("hope_solve_time", "解决时间");
        writer.setOnlyAlias(true);
        writer.write(questionVos, true);

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


    public List<Question> dbList(QuestionVo vo) {
        Date newEndTime = null;
        if (null != vo.getCreate_time_end()) {
            newEndTime = org.apache.commons.lang.time.DateUtils.addDays(vo.getCreate_time_end(), 1);
        }
        List<Question> list = selectList(Wrappers.<Question>lambdaQuery().eq(Question::getIs_del, "0")
                        //创建时间
                        .ge(null != vo.getCreate_time_start(), Question::getCreate_time, vo.getCreate_time_start())
                        .le(null != newEndTime, Question::getCreate_time, newEndTime)
                        //问题类别
                        .eq(null != vo.getCategory_id(), Question::getCategory_id, vo.getCategory_id())
                        //优先级
                        .eq(null != vo.getPriority_id(), Question::getPriority_id, vo.getPriority_id())
                        //状态
                        .eq(StringUtils.isNotBlank(vo.getStatus()), Question::getStatus, vo.getStatus())
                        //解决人
                        .eq(null != vo.getSolve_user(), Question::getSolve_user, vo.getSolve_user())
                        //审核人
                        .eq(null != vo.getAppraisal_user(), Question::getAppraisal_user, vo.getAppraisal_user())
                        //关单人
                        .eq(null != vo.getClearance_user(), Question::getClearance_user, vo.getClearance_user())
                        //关单时间
                        .ge(StringUtils.isNotBlank(vo.getClearance_start_time()), Question::getClearance_time, vo.getClearance_start_time())
                        .le(StringUtils.isNotBlank(vo.getClearance_end_time()), Question::getClearance_time, vo.getClearance_end_time())
                        //关键字
                        .like(StringUtils.isNotBlank(vo.getTitle()), Question::getTitle, vo.getTitle())
//                .orderByDesc(Question::getUpdate_time)
                        .orderByDesc(Question::getCreate_time)
        );
        // 得到用户
        Map<Integer, SysUser> userMap = getUserNameMap();
        // 得到问题类别
        Map<Integer, QuestionCategory> categoryMap = getQuestionCategoryMap();
        // 得到优先级
        Map<Integer, SlaQuestionFirst> priorityMap = getQuestionPriorityMap();

        Integer userId;
        // 得到当前用户
        if (null != vo.getNow_operator_user()) {
            userId = vo.getNow_operator_user();
        } else {
            userId = sysUserService.getUser().getId();
        }
        // 过滤原集合条件
        List<Question> newList = new ArrayList<>();
        for (Question question : list) {
            String tempTime = tempTimes(question.getId(), question.getSla_time(), question.getSla_exceed_time(), question.getIsFitSla());
            question.setSla_surplus_time(tempTime);
            // 判断状态字段是否存在
            if (StringUtils.isBlank(question.getStatus())) {
                continue;
            }
            // 判断用户map是否存在
            if (!CollectionUtils.isEmpty(userMap)) {
                question.setNow_operator_user(userId);
                question.setCreate_user_name(null == userMap.get(question.getCreate_user()) ? "" : userMap.get(question.getCreate_user()).getName());
                question.setNow_operator_user_name(null == userMap.get(question.getNow_operator_user()) ? "" : userMap.get(question.getNow_operator_user()).getName());
            }
            // 判断问题类别map是否存在
            if (!CollectionUtils.isEmpty(categoryMap)) {
                question.setCategory_name(null == categoryMap.get(question.getCategory_id()) ? "" : categoryMap.get(question.getCategory_id()).getName());
            }
            // 判断优先级map是否存在
            if (!CollectionUtils.isEmpty(priorityMap)) {
                question.setPriority_name(null == priorityMap.get(question.getPriority_id()) ? "" : priorityMap.get(question.getPriority_id()).getFirst_name());
            }

            // 增加状态字典 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
            String[] statusArr = {"", "待鉴定", "待审核", "待解决", "审核通过", "关闭", "已撤销", "鉴定拒绝", "审核拒绝", "挂起"};
            question.setStatus_name(statusArr[Integer.parseInt(question.getStatus())]);
            // 判断状态
            switch (question.getStatus()) {
                case "1":
                    // 判断当前用户是否是鉴定人
                    if (userId.equals(question.getAppraisal_user())) {
                        newList.add(question);
                    }
                    break;
                case "2":
                    // 判断当前用户是否是审核人
                    List<QuestionReviewer> questionReviewers = questionReviewerService.selectList(Wrappers.<QuestionReviewer>lambdaQuery()
                            .eq(QuestionReviewer::getIs_del, "0")
                            .eq(QuestionReviewer::getQuestion_id, question.getId())
                            .eq(QuestionReviewer::getReview_user, userId)
                            .isNull(QuestionReviewer::getTitle)
                    );
                    if (!CollectionUtils.isEmpty(questionReviewers)) {
                        newList.add(question);
                    }
                    break;
                case "3":
                    // 判断当前用户是否是处理人
                    if (userId.equals(question.getHandle_user())) {
                        newList.add(question);
                    }
                    break;
                default:
                    // 判断当前用户
                    break;
            }
        }
        return newList;
    }

    //计算剩余时间
    public String tempTimes(Integer id, Integer sla_time, Integer sla_exceed_time, String isFitSla) {
        String tempTime = "";
        if (null == sla_exceed_time) {
            sla_exceed_time = 0;
        }
        if ("0".equals(isFitSla)) {
            if (sla_exceed_time > 60 * 24) {
                long day = sla_exceed_time / (60 * 24);
                long hours = sla_exceed_time % (60 * 24) / 60;
                long minutes = sla_exceed_time % (60 * 24) % 60;
                String hoursStr = hours < 1 ? "" : hours + "小时";
                tempTime = day + "天" + hoursStr + minutes + "分钟";
            } else if (sla_exceed_time > 60) {
                tempTime = sla_exceed_time / 60 + "小时" + sla_exceed_time % 60 + "分钟";
            } else {
                tempTime = sla_exceed_time + "分钟";
            }
            tempTime = "超时: " + tempTime;
        } else {
            Integer diff = 0;
            sla_time = 0;
            SlaQuestionConfig slaQuestionConfig = getSlaEventConfig(id);
            if (null != slaQuestionConfig && Base.notEmpty(slaQuestionConfig.getSolve_time())) {
                diff = Integer.parseInt(slaQuestionConfig.getSolve_time()) - sla_time;
            }
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
            tempTime = "剩余: " + tempTime;
        }
        return tempTime;
    }


    public  SlaQuestionConfig getSlaEventConfig(Integer changed_id) {
        //查询类别对应的SLA
        List<SlaQuestion> slaQuestionCategory = slaQuestionService.getSlaQuestionByCategoryId(null);
        HashMap<Integer, SlaQuestion> slaMap = new HashMap();
        slaQuestionCategory.forEach(x -> slaMap.put(x.getQuestionCategoryId(), x));
        //查询待响应、待处理的工单
        Question question = questionMapper.selectById(changed_id);
        //根据类型获取对应合同的SLA
        if (null == question) {
            return null;
        }
        SlaQuestion slaQuestion = slaMap.get(question.getCategory_id());
        if (slaQuestion == null) {
            return null;
        }
        //查询详细SLA配置
        QueryWrapper<SlaQuestionConfig> qw = new QueryWrapper();
        qw.eq("question_id",slaQuestion.getId());
        qw.eq("is_del","0");
        //判断是否启用优先级
        if("1".equals(slaQuestion.getIs_first())){
            qw.eq("first_id",question.getPriority_id());
        }
        SlaQuestionConfig slaQuestionConfig = slaQuestionConfigService.selectOne(qw);
        return slaQuestionConfig;
    }


    /**
     * 查询问题代办
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    public PageInfo<Question> getDbPageList(Integer pageIndex, Integer pageSize, QuestionVo vo) {

        return getPageInfo(pageIndex, pageSize, dbList(vo));
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
}
