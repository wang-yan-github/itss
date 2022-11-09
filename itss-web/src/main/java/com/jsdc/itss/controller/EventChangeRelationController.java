package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.EventChangeRelation;
import com.jsdc.itss.service.EventChangeRelationService;
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
@RequestMapping("/eventChangeRelation")
public class EventChangeRelationController extends BaseController {

    @Autowired
    private EventChangeRelationService eventChangeRelationService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, EventChangeRelation beanParam) {
        PageInfo<EventChangeRelation> page = eventChangeRelationService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return eventChangeRelationService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addEventChangeRelation(EventChangeRelation eventChangeRelation){

        return eventChangeRelationService.addEventChangeRelation(eventChangeRelation);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editEventChangeRelation(EventChangeRelation eventChangeRelation){

        return eventChangeRelationService.editEventChangeRelation(eventChangeRelation);
    }
}
