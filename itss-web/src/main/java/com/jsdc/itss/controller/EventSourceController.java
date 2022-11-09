package com.jsdc.itss.controller;

import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.EventSource;
import com.jsdc.itss.service.EventSourceService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eventSource")
public class EventSourceController {

    @Autowired
    private EventSourceService eventSourceService;

    /**
     * 来源列表接口
     * Author wzn
     * Date 2022/3/9 13:54
     */
    @RequestMapping("getList.do")
    public ResultInfo getList(@RequestBody EventSource eventSource) {
        return ResultInfo.success(eventSourceService.getList(eventSource));
    }

    /**
     * 来源列表接口
     * Author wzn
     * Date 2022/3/9 13:54
     */
    @RequestMapping("getPage.do")
    public ResultInfo getPage(@RequestBody EventSource eventSource) {
        return eventSourceService.getPage(eventSource, eventSource.getPageNo(), eventSource.getPageSize());
    }

    /**
     * 新增来源接口
     * Author wzn
     * Date 2022/3/9 14:18
     */
    @RequestMapping("save.do")
    public ResultInfo save(@RequestBody EventSource eventSource) {
        return eventSourceService.addEventSource(eventSource);
    }

    /**
     * 来源修改接口
     * Author wzn
     * Date 2022/3/9 14:19
     */
    @RequestMapping("update.do")
    public ResultInfo update(@RequestBody EventSource eventSource) {
        return eventSourceService.updateEventSource(eventSource);
    }

    /**
     * 来源删除接口
     * Author wzn
     * Date 2022/3/9 14:20
     */
    @RequestMapping("deleteSysCompany.do")
    public ResultInfo deleteSysCompany(Integer id) {
        return ResultInfo.success(eventSourceService.deleteEventSource(id));
    }

    /**
     * 详情页面接口
     * Author wzn
     * Date 2022/3/24 13:50
     */
    @RequestMapping("info")
    public AjaxResult info(Integer id) {
        return AjaxResult.success(eventSourceService.info(id));
    }

    /**
     * 来源下拉接口
     * Author wzn
     * Date 2022/3/26 11:29
     */
    @RequestMapping("upEventSourceList")
    public AjaxResult upEventSourceList() {
        return AjaxResult.success(eventSourceService.upEventSourceList());
    }


}
