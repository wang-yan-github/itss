package com.jsdc.itss.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.mapper.QuestionCategoryMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import com.jsdc.itss.utils.ExcelUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.QuestionVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: QuestionMyController
 * Description: 问题管理控制器
 *
 * @author zhangdequan
 */
@RestController
@RequestMapping("questionManage")
public class QuestionManageController {

    @Autowired
    private QuestionManageService questionService;

    @Autowired
    private ChangeInfoService changeInfoService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private QuestionSourceService questionSourceService;
    @Autowired
    private SlaQuestionFirstService slaQuestionFirstService;
    @Autowired
    private QuestionCategoryMapper questionCategoryMapper;

    /**
     * 添加我的问题
     */
    @RequestMapping(value = "myQuestionToAdd.do", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo addMyQuestion(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        QuestionVo questionVo = JSON.parseObject(body, QuestionVo.class);
        return questionService.addMyQuestion(questionVo, file);
    }

    /**
     * 修改我的问题
     */
    @RequestMapping(value = "myQuestionToEdit.do", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo editMyQuestion(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> fileHandle) {
        QuestionVo questionVo = JSON.parseObject(body, QuestionVo.class);
        return questionService.editMyQuestion(questionVo, file, fileHandle);
    }

    /**
     * 查询我的问题列表
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             QuestionVo beanParam) {
        beanParam.setCreate_user(sysUserService.getUser().getId());
        PageInfo<QuestionVo> page = questionService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 根据ID 查询绑定的问题信息,(用于修改页面,初始化数据)
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    public ResultInfo getById(@RequestBody Integer id,Integer user_id) {
        return questionService.getById(id,user_id);
    }

    /**
     * 撤销操作
     */
    @RequestMapping(value = "toRevoke.do", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo editStatusRevoke(@RequestBody QuestionVo questionVo) {
        Question question = questionManageService.selectById(questionVo.getId());
        if (!question.getStatus().equals("1") && !question.getStatus().equals("7")) {
            return ResultInfo.error("该问题在此环节不能撤销");
        }
        return questionService.editStatusRevoke(questionVo);
    }

    /**
     * 描述： 问题管理分页查询
     * 作者： thr
     */
    @RequestMapping(value = "getManageByPage.do", method = RequestMethod.POST)
    public ResultInfo getManageByPage(QuestionVo vo,
                                      @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<QuestionVo> pageInfo = questionService.toList(pageIndex, pageSize, vo);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 描述： 问题管理删除问题
     * 作者： xuaolong
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteQuestion.do", method = RequestMethod.POST)
    public ResultInfo deleteQuestion(Integer id,Integer user_id) {
        return questionService.deleteQuestion(id,user_id);
    }

    /**
     * 描述： 问题管理修改关联
     * 作者： thr
     *
     * @return
     */
    @RequestMapping(value = "modifyRelevance.do", method = RequestMethod.POST)
    public ResultInfo modifyRelevance(@RequestBody QuestionVo questionVo) {
        return questionService.modifyRelevance(questionVo);
    }

    /**
     * 描述： 生成知识
     * 作者： xuaolong
     *
     * @param knowledgeInfo
     * @return
     */
    @RequestMapping(value = "generateKnowledge.do", method = RequestMethod.POST)
    public ResultInfo generateKnowledge(KnowledgeInfo knowledgeInfo) {
        return questionService.generateKnowledge(knowledgeInfo);
    }

    /**
     * 生成变更
     */
    @RequestMapping(value = "generateChangeInfo.do", method = RequestMethod.POST)
    public ResultInfo generateChangeInfo(@RequestBody String change) {
        JSONObject jsonObject = JSON.parseObject(change);

        ChangeVo changeVo = JSONObject.
                parseObject(jsonObject.getString("change"), ChangeVo.class);
        return changeInfoService.toAdd(changeVo,null);
    }

    /**
     * Excel导入问题
     * author xuaolong
     *
     * @param file
     * @return
     */
    @RequestMapping("importQuestionList")
    @ResponseBody
    public ResultInfo importQuestionList(@RequestParam("file") MultipartFile file) {
        return questionService.importQuestionList(file);
    }

    /**
     * 问题待办
     * 审核列表
     *
     * @author thr
     */
    @RequestMapping(value = "getDbPageList", method = RequestMethod.POST)
    public ResultInfo getDbPageList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    QuestionVo vo) {
        //当前操作人
        vo.setNow_operator_user(sysUserService.getUser().getId());
        vo.setDataStatus("db");
        PageInfo<Question> pageInfo = questionService.getDbPageList(vo.getPageNo(), vo.getPageSize(), vo);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 问题待办
     * 参与的列表
     *
     * @author thr
     */
    @RequestMapping(value = "getParticipatingPageList", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo getParticipatingPageList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                               @RequestParam(defaultValue = "10") Integer pageSize, QuestionVo vo) {
        vo.setAppraisal_user(sysUserService.getUser().getId());
        vo.setDataStatus("cyd");
        PageInfo<QuestionVo> pageInfo = questionService.toList(pageIndex, pageSize, vo);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 问题待办
     * 鉴定审核 同意/拒绝
     *
     * @author thr
     */
    @RequestMapping(value = "onDbAppraisalUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbAppraisalUpd(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        QuestionVo vo = JSON.parseObject(body, QuestionVo.class);
        return questionService.onDbAppraisalUpd(vo, file);
    }

    /**
     * 问题待办
     * 处理 保存/解决完成
     *
     * @author thr
     */
    @RequestMapping(value = "onDbHandleUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbHandleUpd(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        QuestionVo vo = JSON.parseObject(body, QuestionVo.class);
        return questionService.onDbHandleUpd(vo, file);
    }

    /**
     * 问题待办
     * 审核 同意/拒绝
     *
     * @author thr
     */
    @RequestMapping(value = "onDbAuditUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbAuditUpd(@RequestBody QuestionVo vo) {
        return questionService.onDbAuditUpd(vo);
    }

    /**
     * 问题待办
     * 关单
     *
     * @author thr
     */
    @RequestMapping(value = "onDbCloseUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbCloseUpd(@RequestBody QuestionVo vo) {
        return questionService.onDbCloseUpd(vo);
    }

    /**
     * 问题待办
     * 转单
     * 可将问题单转单给其他工程师解决。
     *
     * @author thr
     */
    @RequestMapping(value = "onDbTransferOrderUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbTransferOrderUpd(@RequestBody QuestionVo vo) {
        return questionService.onDbTransferOrderUpd(vo);
    }

    /**
     * 问题待办
     * 转办
     *
     * @author thr
     */
    @RequestMapping(value = "onDbTransferUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbTransferUpd(@RequestBody QuestionVo vo) {
        return questionService.onDbTransferUpd(vo);
    }

    /**
     * 问题待办
     * 挂起
     * 问题暂时没有解决方案或其他原因，将问题单挂起。
     * 状态将变为“挂起”并仍然显示在该列表中。
     * 可进行恢复操作。
     *
     * @author thr
     */
    @RequestMapping(value = "onDbHangUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbHangUpd(@RequestBody QuestionVo vo) {
        return questionService.onDbHangUpd(vo);
    }

    /**
     * 问题待办
     * 恢复
     *
     * @author thr
     */
    @RequestMapping(value = "onDbRecoveryUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo onDbRecoveryUpd(@RequestBody QuestionVo vo) {
        return questionService.onDbRecoveryUpd(vo);
    }


    /**
     * 问题统计
     */
    @RequestMapping(value = "questionStatistics", method = RequestMethod.POST)
    public ResultInfo questionStatistics(@RequestBody Question question) {
        return ResultInfo.success(questionService.questionStatistics(question));
    }


    /**
     * create by xuaolong
     * description:问题统计导出excel
     *
     * @param response
     * @return
     */
    @PostMapping("exporQuestionExcel.do")
    public void exporQuestionExcel(HttpServletResponse response, Question question) {
        questionService.exporQuestionExcel(response, question);
    }

    /**
     * 参与的问题导出
     */
    @RequestMapping(value = "exportQuestion", method = RequestMethod.POST)
    public ResultInfo exportQuestion(HttpServletResponse response) {
        return ResultInfo.success(questionService.exportQuestion(response));
    }

    /**
     * 参与的问题导出
     */
    @RequestMapping(value = "exportDBQuestion", method = RequestMethod.POST)
    public void exportDBQuestion(HttpServletResponse response,QuestionVo vo) {
        vo.setNow_operator_user(sysUserService.getUser().getId());
        vo.setDataStatus("db");
        List<Question> list = questionService.dbList(vo);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("code", "问题单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("status_name", "状态");
        writer.addHeaderAlias("now_operator_user_name", "当前操作人");
        writer.addHeaderAlias("create_user_name", "创建人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("solve_time", "解决时间");
        writer.addHeaderAlias("sla_surplus_time", "剩余时间");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * addRemark:添加备注
     */
    @RequestMapping(value = "addRemark", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    public ResultInfo addRemark(@RequestBody Question question) {
        return questionService.addRemark(question);
    }

    /**
     * Excel问题导入模板下载
     * author xuaolong
     *
     * @param response
     * @return
     */
    @RequestMapping("downloadQuestion")
    public void downloadQuestion(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();

        row.put("问题来源", StringUtils.EMPTY);
        row.put("优先级", StringUtils.EMPTY);
        row.put("问题类别", StringUtils.EMPTY);
        row.put("标题", StringUtils.EMPTY);
        row.put("描述", StringUtils.EMPTY);
        row.put("期望解决时间", StringUtils.EMPTY);

        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();

        //设置下拉数据 从第几行开始
        int firstRow = 1;
        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();

        //问题来源
        String[] questionSources = questionSourceService.selectList(Wrappers.<QuestionSource>lambdaQuery()
                .eq(QuestionSource::getIs_del, GlobalData.ISDEL_NO)
                .eq(QuestionSource::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(QuestionSource::getName).toArray(String[]::new);
        //优先级
        String[] slaQuestionFirsts = slaQuestionFirstService.selectList(Wrappers.<SlaQuestionFirst>lambdaQuery()
                .eq(SlaQuestionFirst::getIs_del, GlobalData.ISDEL_NO)
                .eq(SlaQuestionFirst::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(SlaQuestionFirst::getFirst_name).toArray(String[]::new);
        //问题类别
        String[] questionCategorys = questionCategoryMapper.selectList(Wrappers.<QuestionCategory>lambdaQuery()
                .eq(QuestionCategory::getIs_del, GlobalData.ISDEL_NO)
                .eq(QuestionCategory::getIs_use, GlobalData.IS_ENABLE_YES)
        ).stream().map(QuestionCategory::getName).toArray(String[]::new);

        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, questionSources, firstRow, 0));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, slaQuestionFirsts, firstRow, 1));
        writer.addValidationData(ExcelUtils.setSelectCol(styleSet, sheet, questionCategorys, firstRow, 2));

        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("1234.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导出我的问题
     */
    @RequestMapping(value = "toExportMyQuestions.do", method = RequestMethod.POST)
    public void exportMyQuestions(HttpServletResponse response, QuestionVo beanParam) {
        beanParam.setCreate_user(sysUserService.getUser().getId());
        List<QuestionVo> list = questionService.toList(beanParam);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("code", "问题单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("now_operator_user_name", "当前操作人");
        writer.addHeaderAlias("solve_time", "解决时间");
        writer.addHeaderAlias("sla_surplus_time", "剩余时间");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 问题管理 导出
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @PostMapping("exportQuestionManExcel.do")
    public void exportQuestionManExcel(HttpServletResponse response, QuestionVo beanParam){
        List<QuestionVo> list = questionService.toList(beanParam);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("code", "问题单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("category_name", "问题类别");
        writer.addHeaderAlias("hope_solve_time", "期望解决时间");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("now_operator_user_name", "当前操作人");
        writer.addHeaderAlias("solve_time", "解决时间");
        writer.addHeaderAlias("sla_surplus_time", "剩余时间");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
