package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.EventSatisfaction;
import com.jsdc.itss.service.EventSatisfactionService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhangdequan
 * @create 2022-03-22 10:30:09
 */
@Controller
@RequestMapping("/eventSatisfaction")
public class EventSatisfactionController extends BaseController {

    @Autowired
    private EventSatisfactionService eventSatisfactionService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, EventSatisfaction beanParam) {
        PageInfo<EventSatisfaction> page = eventSatisfactionService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return eventSatisfactionService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addEventSatisfaction(EventSatisfaction eventSatisfaction){

        return eventSatisfactionService.addEventSatisfaction(eventSatisfaction);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editEventSatisfaction(EventSatisfaction eventSatisfaction){

        return eventSatisfactionService.editEventSatisfaction(eventSatisfaction);
    }
}
