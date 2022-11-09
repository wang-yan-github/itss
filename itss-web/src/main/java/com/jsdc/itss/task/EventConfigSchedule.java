package com.jsdc.itss.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class EventConfigSchedule {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private SysWorkingDaysCalendarService calendarService;
    @Autowired
    private EventTimesService eventTimesService;
    @Autowired
    private SysWorkingDaysDistinguishService distinguishService;
    @Autowired
    private SysWorkingDaysService daysService;
    @Autowired
    private EventParamConfigService eventParamConfigService;

    /**
     * 每15分钟执行
     * Todo 根据工作日来判断是否应该暂停工单
     */
    @Scheduled(cron = "1 0 0 * * ?")
    public void eventPauseTask() {
        //查询配置
        EventParamConfig eventParamConfig = eventParamConfigService.selectById(1);
        if ("1".equals(eventParamConfig.getIs_aging_switch()) && eventParamConfig.getTimeout_days() != null) {
            //查询待评审的工单 关单时间超过配置时间的工单
            QueryWrapper qw = new QueryWrapper<Event>();
            qw.eq("status", "5");
            qw.eq("is_del", "0");
            DateTime newDate2 = DateUtil.offsetDay(new Date(), -(eventParamConfig.getTimeout_days()-1));
            qw.lt("closeTime", DateUtil.formatDate(newDate2));
            List<Event> eventList = eventService.selectList(qw);
            for (Event event : eventList) {
                event.setStatus("6");//关闭工单
                //判断是否自动评价
                if("1".equals(eventParamConfig.getDefault_rating())){
                    event.setEvent_satisfaction(Integer.parseInt(eventParamConfig.getAutomatic_evaluation()));
                }
                event.updateById();
            }

        }
    }

    public static void main(String[] args) {
        DateTime newDate2 = DateUtil.offsetDay(new Date(), -5);
        System.out.println(DateUtil.formatDate(newDate2));
    }
}
