package com.jsdc.itss.service;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.Base;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 设置-事件配置-定时任务
 * 根据事件工单模板定时生成事件工单
 *
 * @ClassName Job1
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/3 13:51
 * @Version 1.0
 */
@DependsOn("ApplicationContextUtil")
@Data
public class Job1 implements Task {

    private EventSchedule eventSchedule;

    public Job1(EventSchedule eventSchedule) {
        this.eventSchedule = eventSchedule;
    }

    /**
     * 定时任务
     */
    @Override
    public void execute() {
        try {

            //schedule_type 类型 1每工作日 2每天 3 每周 4每月 5一次性
            if (Base.notEmpty(eventSchedule.getEnd_time())) {
                //before  getEnd_time < new Date()
                if (eventSchedule.getEnd_time().before(new Date())) {
                    //若 结束时间 < 当前时间，则移除该定时任务
                    CronUtil.remove(eventSchedule.getId() + "");
                } else if (!eventSchedule.getEnd_time().before(new Date()) && eventSchedule.getBegin_time().before(new Date())) {
                    //若 结束时间 > 当前时间 && 开始时间 < 当前时间，则根据工单模板生成事件工单
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    int day = daysBetween(eventSchedule.getLastTime(), new Date());

                    //todo 调用开单接口
                    onStart();
                }
            } else {
                //若 开始时间 < 当前时间，则根据工单模板生成事件工单
                if (eventSchedule.getBegin_time().before(new Date())) {
                    //todo 调用开单接口
                    onStart();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始
     */
    private void onStart() {
        EventScheduleService eventScheduleService = ApplicationContextUtil.getContext().getBean(EventScheduleService.class);

        //todo 调用开单接口
        createEvent();
        //todo 生成日志
        saveLog();

        if (eventSchedule.getSchedule_type().equals("5")) {
            //停止标志  停止过一次  状态就是1  默认0
            eventSchedule.setStop("1");
            //上次执行时间
            eventSchedule.setLastTime(new Date());
            //状态 0运行  1停止
            eventSchedule.setStatus("1");
            eventScheduleService.updateById(eventSchedule);
            //若 结束时间 < 当前时间，则移除该定时任务
            CronUtil.remove(eventSchedule.getId() + "");
        } else {
            //上次执行时间
            eventSchedule.setLastTime(new Date());
            eventScheduleService.updateById(eventSchedule);
        }
    }

    /**
     * 生成工单
     */
    private void createEvent() {
        EventScheduleSonService eventScheduleSonService = ApplicationContextUtil.getContext().getBean(EventScheduleSonService.class);
        EventTemplateService eventTemplateService = ApplicationContextUtil.getContext().getBean(EventTemplateService.class);
        EventService eventService = ApplicationContextUtil.getContext().getBean(EventService.class);

        //根据定时任务id 查询 工单模板list集合
        QueryWrapper<EventScheduleSon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_schedule_id", eventSchedule.getId());
        queryWrapper.eq("is_del", "0");
        List<EventScheduleSon> eventScheduleSonList = eventScheduleSonService.selectList(queryWrapper);
//        List<EventTemplate> eventTemplateList = new ArrayList<>();
        if (Base.notEmpty(eventScheduleSonList) && eventScheduleSonList.size() > 0) {
            for (EventScheduleSon e : eventScheduleSonList) {
                EventTemplate eventTemplate = eventTemplateService.selectById(e.getEvent_id());
                //定时生成工单
                EventVo vo = new EventVo();
                vo.setCreate_user(Base.empty(eventTemplate.getUser_id()) ? null : eventTemplate.getUser_id());//用户
                vo.setUser_id(Base.empty(eventTemplate.getUser_id()) ? null : eventTemplate.getUser_id());//用户
                vo.setTitle(Base.empty(eventTemplate.getTitle()) ? "" : eventTemplate.getTitle());//工单标题
                vo.setDescription(Base.empty(eventTemplate.getDescription()) ? "" : eventTemplate.getDescription());//工单描述
                vo.setSource_id(Base.empty(eventTemplate.getSource()) ? null : Integer.parseInt(eventTemplate.getSource()));//来源
                vo.setRequest_type(Base.empty(eventTemplate.getRequest_type()) ? null : eventTemplate.getRequest_type());//请求类型
                vo.setEvent_category_id(Base.empty(eventTemplate.getBill_type()) ? null : Integer.parseInt(eventTemplate.getBill_type()));//工单类别
                vo.setEffect_degree(Base.empty(eventTemplate.getEffect_degree()) ? null : eventTemplate.getEffect_degree());//影响度
                vo.setUrgency_degree_id(Base.empty(eventTemplate.getUrgency_degree()) ? null : Integer.parseInt(eventTemplate.getUrgency_degree()));//紧急度
                vo.setIs_effect(Base.empty(eventTemplate.getIs_effect()) ? "" : eventTemplate.getIs_effect());//影响系统可用性
                vo.setService_groups_id(Base.empty(eventTemplate.getService_groups()) ? null : eventTemplate.getService_groups());//处理服务群组
                vo.setHandler(Base.empty(eventTemplate.getHandler()) ? null : eventTemplate.getHandler());//处理人
                vo.setEx_solve_time(Base.empty(eventTemplate.getEx_solve_time()) ? null : eventTemplate.getEx_solve_time());//预计解决时间
                vo.setSolve_type(Base.empty(eventTemplate.getSolve_type()) ? null : Integer.parseInt(eventTemplate.getSolve_type()));//解决方式
                vo.setProgramme_type(Base.empty(eventTemplate.getProgramme_type()) ? null : Integer.parseInt(eventTemplate.getProgramme_type()));//方案类别
                vo.setExpenses(Base.empty(eventTemplate.getExpenses()) ? null : eventTemplate.getExpenses());//费用
                vo.setSolution(Base.empty(eventTemplate.getSolution()) ? null : eventTemplate.getSolution());//解决方案

                vo.setService_groups_id(eventTemplate.getEvent_service_id());//所属群组
                //1 定时生成工单标识
                vo.setTimeTaskStatus("1");

                eventService.addEvent(vo, null, null);
            }
        }
    }

    /**
     * 计算两个时间相差几天
     * Author wzn
     * Date 2022/3/11 9:53
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    private void saveLog() {
        SysLogService sysLogService = ApplicationContextUtil.getContext().getBean(SysLogService.class);

        SysLog sysLog = new SysLog();
        sysLog.setBill_id(eventSchedule.getId());
        sysLog.setTitle("定时任务执行日志");
        sysLog.setContent("定时任务执行日志");
        sysLog.setOperate_time(new Date());
        sysLog.setModule(LogEnums.LOG_Schedule.getValue());
        sysLogService.insert(sysLog);
    }
}
