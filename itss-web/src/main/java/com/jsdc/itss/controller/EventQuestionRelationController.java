package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.EventQuestionRelation;
import com.jsdc.itss.service.EventQuestionRelationService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhangdequan
 * @create 2022-03-17 11:41:40
 */
@Controller
@RequestMapping("/eventQuestionRelation")
public class EventQuestionRelationController extends BaseController {

    @Autowired
    private EventQuestionRelationService eventQuestionRelationService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, EventQuestionRelation beanParam) {
        PageInfo<EventQuestionRelation> page = eventQuestionRelationService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return eventQuestionRelationService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addEventQuestionRelation(EventQuestionRelation eventQuestionRelation){

        return eventQuestionRelationService.addEventQuestionRelation(eventQuestionRelation);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editEventQuestionRelation(EventQuestionRelation eventQuestionRelation){

        return eventQuestionRelationService.editEventQuestionRelation(eventQuestionRelation);
    }
}
