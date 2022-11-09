package com.jsdc.itss.controller;

import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.EventSchedule;
import com.jsdc.itss.service.EventScheduleService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 工单定时任务表
 */
@RestController
@RequestMapping("eventSchedule")
public class EventScheduleController {

    @Autowired
    private EventScheduleService eventScheduleService;


    /**
     * 定时任务列表接口
     * Author wzn
     * Date 2022/3/14 15:35
     */
    @RequestMapping("getPage.do")
    public ResultInfo getPage(@RequestBody EventSchedule eventSchedule) {
        return eventScheduleService.getPage(eventSchedule, eventSchedule.getPageNo(), eventSchedule.getPageSize());
    }


    /**
     * 新增定时任务
     * Author wzn
     * Date 2022/3/14 15:44
     */
    @RequestMapping("save.do")
    @LogInfo(LogEnums.LOG_Schedule)
    public ResultInfo save(@RequestBody EventSchedule eventSchedule) {
        return eventScheduleService.addEventSchedule(eventSchedule);
    }

    /**
     * 开启定时任务
     * Author wzn
     * Date 2022/3/11 14:26
     */
    @RequestMapping("start")
    @LogInfo(LogEnums.LOG_Schedule)
    public ResultInfo start(Integer id) {
        return eventScheduleService.start(id);
    }


    /**
     * 停止定时任务
     * Author wzn
     * Date 2022/3/11 14:26
     */
    @RequestMapping("pause")
    @LogInfo(LogEnums.LOG_Schedule)
    public ResultInfo pause(Integer id) {
        return eventScheduleService.pause(id);
    }

    /**
     * 重启定时任务
     * Author wzn
     * Date 2022/3/11 14:26
     */
    @RequestMapping("restart")
    @LogInfo(LogEnums.LOG_Schedule)
    public ResultInfo restart(Integer id) {
        return eventScheduleService.restart(id);
    }

    //详情
    @RequestMapping("info")
    public ResultInfo info(Integer id) {
        return ResultInfo.success(eventScheduleService.info(id));
    }

    /**
     * 修改
     */
    @RequestMapping("editEventSchedule")
    @LogInfo(LogEnums.LOG_Schedule)
    public ResultInfo editEventSchedule(@RequestBody EventSchedule eventSchedule) {
        return eventScheduleService.editEventSchedule(eventSchedule);
    }

    /**
     * 删除
     */
    @RequestMapping("del")
    @LogInfo(LogEnums.LOG_Schedule)
    public ResultInfo del(Integer id) {
        return eventScheduleService.del(id);
    }

    /**
     * 查看日志
     */
    @RequestMapping("getLogInfoById")
    public ResultInfo getLogInfoById(Integer id) {
        return eventScheduleService.getLogInfoById(id);
    }


}
