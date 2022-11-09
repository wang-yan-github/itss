package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.QuestionWorkorder;
import com.jsdc.itss.service.QuestionWorkorderService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhangdequan
 * @create 2022-03-15 10:40:27
 */
@Controller
@RequestMapping("/questionWorkorder")
public class QuestionWorkorderController extends BaseController {

    @Autowired
    private QuestionWorkorderService questionWorkorderService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, QuestionWorkorder beanParam) {
        PageInfo<QuestionWorkorder> page = questionWorkorderService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return questionWorkorderService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addQuestionWorkorder(QuestionWorkorder questionWorkorder){

        return questionWorkorderService.addQuestionWorkorder(questionWorkorder);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editQuestionWorkorder(QuestionWorkorder questionWorkorder){

        return questionWorkorderService.editQuestionWorkorder(questionWorkorder);
    }
}
