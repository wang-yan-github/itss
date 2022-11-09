package com.jsdc.itss.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EventPauseSchedule {
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

    /**
     * 每15分钟执行
     * Todo 根据工作日来判断是否应该暂停工单
     */
    @Scheduled(cron = "0 0/15 * * * ?")
    public void eventPauseTask() {
        //1.查询待处理的单据
        List<Event> eventList = eventService.selectList(new QueryWrapper<Event>().eq("status", "2").eq("is_del", "0"));
        //查询当前时间日期是否为工作日，若是节假日，则暂停，若是工作日，则查询上班时间
        SysWorkingDaysCalendar calendar = calendarService.selectOne(new QueryWrapper<SysWorkingDaysCalendar>().eq("dateStr", DateUtil.formatDate(new Date())));
        //当前时间
        String timeStr = DateUtil.format(new Date(), "HH:mm");
        //2.查询工单所在群组，和选择的工作日
        for (Event event : eventList) {
            if (event.getService_groups_id() != null) {
                EventServiceManage eventServiceManage = eventServiceManageService.selectById(event.getService_groups_id());
                //记录暂停时间
                EventTimes eventTimes = eventTimesService.selectOne(new QueryWrapper<EventTimes>().eq("event_id", event.getId()));
                if ("1".equals(eventServiceManage.getIs_working_day())) {//是否自动暂停
                    if (calendar == null) continue;
                    if (0 == calendar.getGrade() || 1 == calendar.getGrade()) {
                        //暂停工单
                        event.setStatus("3");
                        eventTimes.setLast_pause_type("2");
                        eventTimes.setLast_pause_time(new Date());
                        event.updateById();
                        eventTimes.updateById();
                    } else {
                        //判断群组是否选择工作日，若未选择，则查询默认工作日
                        SysWorkingDaysDistinguish daysDistinguish = null;
                        if (eventServiceManage.getWork_id() != null) {
                            daysDistinguish = distinguishService.selectOne(new QueryWrapper<SysWorkingDaysDistinguish>().eq("work_day_id", eventServiceManage.getWork_id()).eq("is_del", "0"));
                        } else {
                            //查询默认工作日 默认 0是 1否
                            SysWorkingDays sysWorkingDays = daysService.selectOne(new QueryWrapper<SysWorkingDays>().eq("acquiesce", "0").eq("is_use", "1").eq("is_del", "0"));
                            if (sysWorkingDays != null) {
                                //查询对应时间
                                daysDistinguish = distinguishService.selectOne(new QueryWrapper<SysWorkingDaysDistinguish>().eq("work_day_id", sysWorkingDays.getId()).eq("is_del", "0"));
                            }
                        }
                        if(daysDistinguish == null) continue;
                        if (timeStr.equals(daysDistinguish.getEnd_time_AM()) || timeStr.equals(daysDistinguish.getEnd_time_PM())) {
                            //暂停工单
                            event.setStatus("3");
                            eventTimes.setLast_pause_type("2");
                            eventTimes.setLast_pause_time(new Date());
                            event.updateById();
                            eventTimes.updateById();
                        }
                    }
                }

            }
        }
    }

    /**
     * 每15分钟执行
     * Todo 根据工作日来判断是否应该恢复工单
     */
    @Scheduled(cron = "0 0/15 * * * ?")
    public void eventRecoveryTask() {
        //1.查询暂停的单据
        List<Event> eventList = eventService.selectList(new QueryWrapper<Event>().eq("status", "3").eq("is_del", "0"));
        //查询当前时间日期是否为工作日，若是工作日,则根据工作时间恢复
        SysWorkingDaysCalendar calendar = calendarService.selectOne(new QueryWrapper<SysWorkingDaysCalendar>().eq("dateStr", DateUtil.formatDate(new Date())));
        //当前时间
        String timeStr = DateUtil.format(new Date(), "HH:mm");
        //2.查询工单所在群组，和选择的工作日
        for (Event event : eventList) {
            if (event.getService_groups_id() != null) {
                EventServiceManage eventServiceManage = eventServiceManageService.selectById(event.getService_groups_id());
                //记录暂停时间
                EventTimes eventTimes = eventTimesService.selectOne(new QueryWrapper<EventTimes>().eq("event_id", event.getId()));
                if ("1".equals(eventServiceManage.getIs_working_day())) {//是否自动恢复
                    if (calendar == null) continue;
                    if (2 == calendar.getGrade()) {
                        //判断群组是否选择工作日，若未选择，则查询默认工作日
                        SysWorkingDaysDistinguish daysDistinguish = null;
                        if (eventServiceManage.getWork_id() != null) {
                            daysDistinguish = distinguishService.selectOne(new QueryWrapper<SysWorkingDaysDistinguish>().eq("work_day_id", eventServiceManage.getWork_id()).eq("is_del", "0"));
                        } else {
                            //查询默认工作日 默认 0是 1否
                            SysWorkingDays sysWorkingDays = daysService.selectOne(new QueryWrapper<SysWorkingDays>().eq("acquiesce", "0").eq("is_use", "1").eq("is_del", "0"));
                            if (sysWorkingDays != null) {
                                //查询对应时间
                                daysDistinguish = distinguishService.selectOne(new QueryWrapper<SysWorkingDaysDistinguish>().eq("work_day_id", sysWorkingDays.getId()).eq("is_del", "0"));
                            }

                        }
                        if (daysDistinguish == null) continue;
                        if (timeStr.equals(daysDistinguish.getStart_time_AM()) || timeStr.equals(daysDistinguish.getStart_time_PM())) {
                            if ("1".equals(eventTimes.getLast_pause_type()) && "1".equals(eventServiceManage.getIs_pause_manually())) {//手动暂停  并且判断是否开启手动暂停的工单自动恢复
                                event.setStatus("2");//恢复工单
                                long betweenDay = DateUtil.between(eventTimes.getLast_pause_time(), new Date(), DateUnit.MINUTE);
                                Integer lastTime = eventTimes.getManual_pause_times() == null ? 0 : eventTimes.getManual_pause_times();
                                eventTimes.setManual_pause_times((int) (lastTime + betweenDay));
                                event.updateById();
                                eventTimes.updateById();
                            } else if ("2".equals(eventTimes.getLast_pause_type())) {//自动
                                event.setStatus("2");//恢复工单
                                long betweenDay = DateUtil.between(eventTimes.getLast_pause_time(), new Date(), DateUnit.MINUTE);
                                Integer lastTime = eventTimes.getAuto_pause_times() == null ? 0 : eventTimes.getAuto_pause_times();
                                eventTimes.setAuto_pause_times((int) (lastTime + betweenDay));
                                event.updateById();
                                eventTimes.updateById();
                            }

                        }
                    }
                }
            }
        }
    }

}
