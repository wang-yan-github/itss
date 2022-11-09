package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.EventTemplate;
import com.jsdc.itss.service.EventTemplateService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("eventTemplate")
public class EventTemplateController {

    @Autowired
    private EventTemplateService eventTemplateService;


    /**
     * 工单模板列表接口
     * Author wzn
     * Date 2022/3/10 14:04
     */
    @RequestMapping("getPage.do")
    public ResultInfo getPage(@RequestBody EventTemplate eventTemplate) {
        return eventTemplateService.getPage(eventTemplate, eventTemplate.getPageNo(), eventTemplate.getPageSize());
    }

    /**
     * 个人模版列表接口
     */
    @RequestMapping("getMyPage.do")
    public ResultInfo getMyPage(@RequestBody EventTemplate eventTemplate) {
        return eventTemplateService.getMyPage(eventTemplate, eventTemplate.getPageNo(), eventTemplate.getPageSize());
    }


    /**
     * 新增工单模板
     * Author wzn
     * Date 2022/3/10 14:09
     */
    @RequestMapping("save.do")
    public ResultInfo save(@RequestBody EventTemplate eventTemplate) {
        return eventTemplateService.addEventEemplate(eventTemplate);
    }


    /**
     * 修改工单模板
     * Author wzn
     * Date 2022/3/10 14:10
     */
    @RequestMapping("update.do")
    public ResultInfo update(@RequestBody EventTemplate eventTemplate) {
        return eventTemplateService.updateEventEemplate(eventTemplate);
    }

    /**
     * 详情接口
     * Author wzn
     * Date 2022/3/26 15:52
     */
    @RequestMapping("eventTemplateInfo")
    public AjaxResult eventTemplateInfo(Integer id) {
        return AjaxResult.success(eventTemplateService.eventTemplateInfo(id));
    }

    /**
     * 工单模板删除接口
     * Author wzn
     * Date 2022/3/26 16:44
     */
    @RequestMapping("del")
    public AjaxResult del(Integer id) {
        eventTemplateService.del(id);
        return AjaxResult.success();
    }

    @RequestMapping("getAllEventTemplate")
    public ResultInfo getAllEventTemplate(EventTemplate eventTemplate) {
        List<EventTemplate> list = eventTemplateService.getList(eventTemplate);
        return ResultInfo.success(list);
    }


}
