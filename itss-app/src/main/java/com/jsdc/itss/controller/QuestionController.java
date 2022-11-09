package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import com.jsdc.itss.vo.QuestionVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/question")
public class QuestionController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private EventService eventService;
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private QuestionSourceService questionSourceService;
    @Autowired
    private SlaQuestionFirstService slaQuestionFirstService;
    @Autowired
    private QuestionCategoryService questionCategoryService;

    // ********** 我的问题 ***********

    /**
     * 我的问题跳转
     *
     * @return
     */
    @RequestMapping("/my/list/page")
    public String list() {
        return "question/list";
    }

    /**
     * 我的问题查询
     *
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "my/list.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo myList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             QuestionVo beanParam) {
        beanParam.setCreate_user(beanParam.getCurrent_user());
        PageInfo<QuestionVo> page = questionManageService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }

    /**
     * 添加我的问题
     */
    @RequestMapping(value = "myQuestionToAdd.do", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_APP_QUESTION)
    @ResponseBody
    public ResultInfo addMyQuestion(@RequestBody QuestionVo questionVo) {
        return questionManageService.addMyQuestion(questionVo, null);
    }

    /**
     * 修改我的问题
     */
    @RequestMapping(value = "myQuestionToEdit.do", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_APP_QUESTION)
    @ResponseBody
    public ResultInfo editMyQuestion(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> fileHandle) {
        QuestionVo questionVo = JSON.parseObject(body, QuestionVo.class);
        return questionManageService.editMyQuestion(questionVo, file, fileHandle);
    }

    // ********** 问题管理 ***********

    /**
     * 根据id查询问题
     */
    @RequestMapping(value = "selectQuestionById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo selectQuestionById(Integer id,Integer user_id) {
        return questionManageService.getById(id,user_id);
    }

    /**
     * 根据id删除问题
     *
     * @return
     */
    @RequestMapping(value = "deleteQuestion.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteQuestion(Integer id,Integer user_id) {
        return questionManageService.deleteQuestion(id,user_id);
    }

    /**
     * 描述： 问题管理分页查询
     */
    @RequestMapping(value = "getManageByPage.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getManageByPage(QuestionVo vo,
                                      @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultInfo.success(questionManageService.toList(pageIndex, pageSize, vo));
    }

    // ********** 问题待办 ***********

    /**
     * 问题待办
     * 审核列表
     */
    @RequestMapping(value = "getDbPageList", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getDbPageList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    QuestionVo vo) {
        vo.setDataStatus("db");
        return ResultInfo.success(questionManageService.getDbPageList(pageIndex, pageSize, vo));
    }

    /**
     * 撤销操作
     * “待鉴定”和“鉴定拒绝”状态下的问题单可被撤销,撤消后状态变为“已撤销”，可在“我的问题”和“已结束问题”中查看
     * // row.status == '1' || row.status == '7'
     */
    @RequestMapping(value = "toRevoke.do", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo editStatusRevoke(@RequestBody QuestionVo questionVo) {
        if (null == questionVo.getId() || null == questionManageService.selectById(questionVo.getId())) {
            return ResultInfo.error("该问题不存在");
        }
        Question question = questionManageService.selectById(questionVo.getId());
        if (!question.getStatus().equals("1") && !question.getStatus().equals("7")) {
            return ResultInfo.error("该问题在此环节不能撤销");
        }
        return questionManageService.editStatusRevoke(questionVo);
    }

    /**
     * 描述： 问题管理修改关联
     * 作者： thr
     *
     * @return
     */
    @RequestMapping(value = "modifyRelevance.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo modifyRelevance(@RequestBody QuestionVo questionVo) {
        return questionManageService.modifyRelevance(questionVo);
    }

    /**
     * 问题待办
     * 鉴定审核 同意/拒绝
     *
     * @author thr
     */
    @RequestMapping(value = "onDbAppraisalUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo onDbAppraisalUpd(@RequestBody QuestionVo bean) {
        return questionManageService.onDbAppraisalUpd(bean, null);
    }

    /**
     * 问题待办
     * 处理 保存/解决完成
     *
     * @author thr
     */
    @RequestMapping(value = "onDbHandleUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo onDbHandleUpd(@RequestBody QuestionVo vo) {
        return questionManageService.onDbHandleUpd(vo, null);
    }

    /**
     * 问题待办
     * 审核 同意/拒绝
     *
     * @author thr
     */
    @RequestMapping(value = "onDbAuditUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo onDbAuditUpd(@RequestBody QuestionVo vo) {
        return questionManageService.onDbAuditUpd(vo);
    }

    /**
     * 问题待办
     * 关单
     *
     * @author thr
     */
    @RequestMapping(value = "onDbCloseUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo onDbCloseUpd(@RequestBody QuestionVo vo) {
        if (null == vo.getId() || null == questionManageService.selectById(vo.getId())) {
            return ResultInfo.error("该问题不存在");
        }
        return questionManageService.onDbCloseUpd(vo);
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
    @ResponseBody
    public ResultInfo onDbTransferOrderUpd(@RequestBody QuestionVo vo) {
        return questionManageService.onDbTransferOrderUpd(vo);
    }

    /**
     * 问题待办
     * 转办
     *
     * @author thr
     */
    @RequestMapping(value = "onDbTransferUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo onDbTransferUpd(QuestionVo vo) {
        return questionManageService.onDbTransferUpd(vo);
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
    @ResponseBody
    public ResultInfo onDbHangUpd(@RequestBody QuestionVo vo) {
        return questionManageService.onDbHangUpd(vo);
    }

    /**
     * 问题待办
     * 恢复
     *
     * @author thr
     */
    @RequestMapping(value = "onDbRecoveryUpd", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo onDbRecoveryUpd(QuestionVo vo) {
        return questionManageService.onDbRecoveryUpd(vo);
    }

    /**
     * addRemark:添加备注
     */
    @RequestMapping(value = "addRemark", method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_QUESTION)
    @ResponseBody
    public ResultInfo addRemark(@RequestBody Question question) {
        if (null == question.getUserId()) {
            question.setUserId(question.getCreate_user());
        }
        if (null == question.getId() || questionManageService.getById(question.getId(),question.getUserId()) == null) {
            return ResultInfo.error("问题id不能为空");
        }
        Integer userId = question.getUserId();
        String remark = question.getRemark();
        question = questionManageService.selectById(question.getId());
        question.setRemark(remark);
        question.setUserId(userId);
        return questionManageService.addRemark(question);
    }


    /**
     * 工程师下拉数据接口
     * Author wzn
     * Date 2022/3/22 9:54
     */
    @RequestMapping("/upUserData.do")
    @ResponseBody
    public AjaxResult upUserData() {
        return AjaxResult.success(sysUserService.upUserData());
    }

    /**
     * 问题来源
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "questionSourceList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo questionSourceList(QuestionSource bean) {
        List<QuestionSource> list = questionSourceService.getList(bean);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("nameList",list.stream().map(QuestionSource::getName).collect(Collectors.toList()));
        return ResultInfo.success(jsonObject);
    }


    /**
     * 优先级
     *
     * @param slaQuestionFirst
     * @return
     */
    @RequestMapping(value = "slaQuestionFirstList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo slaQuestionFirstList(SlaQuestionFirst slaQuestionFirst) {
        List<SlaQuestionFirst> list = slaQuestionFirstService.getList(slaQuestionFirst);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("nameList",list.stream().map(SlaQuestionFirst::getFirst_name).collect(Collectors.toList()));
        return ResultInfo.success(jsonObject);
    }

    /**
     * 问题类别
     * @param questionCategory
     * @return
     */
    @RequestMapping(value = "getParentList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getParentList(QuestionCategory questionCategory) {
        List<QuestionCategory> list = questionCategoryService.getTreeMenu(questionCategory);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("nameList",list.stream().map(QuestionCategory::getName).collect(Collectors.toList()));
        return ResultInfo.success(jsonObject);
    }

    /**
     * 状态
     *
     * @param sysDict
     * @return
     */
    @RequestMapping(value = "selectByDictTypeList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo selectByDictTypeList(@RequestBody SysDict sysDict) {
        List<SysDict> sysDictList = sysDictService.getDicts(sysDict.getDict_type());
        return ResultInfo.success(sysDictList);
    }

    /**
     * 添加关联的工单 列表
     */
    @RequestMapping(value = "eventList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestBody Event event) {

        PageInfo<Event> page = eventService.toList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }
}
