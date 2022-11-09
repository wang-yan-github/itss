package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SlaEvent;
import com.jsdc.itss.model.SlaQuestion;
import com.jsdc.itss.service.SlaQuestionService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SlaQuestionController
 * Description:
 * date: 2022/3/7 14:24
 *
 * @author bn
 */
@Controller
@RequestMapping("slaQuestion")
public class SlaQuestionController extends BaseController {

    @Autowired
    private SlaQuestionService slaQuestionService;

    /**
     * @param slaQuestion
     * @return
     */
    @RequestMapping(value = "getSlaQuestion.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getSlaQuestion(@RequestBody SlaQuestion slaQuestion) {

        return slaQuestionService.getSlaQuestion(slaQuestion);
    }


    /**
     * 列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @param slaQuestion
     * @return
     * @author bn
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize, SlaQuestion slaQuestion) {
        PageInfo<SlaQuestion> page = slaQuestionService.toList(pageIndex, pageSize, slaQuestion);
        return ResultInfo.success(page);
    }


    /**
     * 添加
     *
     * @param sla_question
     * @return
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestBody String sla_question) {
        return slaQuestionService.toAdd(sla_question);
    }


    /**
     * 编辑
     *
     * @param sla_question
     * @return
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(@RequestBody String sla_question) {
        return slaQuestionService.toEdit(sla_question);
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "toDel.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toDel(SlaQuestion bean) {
        return slaQuestionService.toDel(bean);
    }

}
