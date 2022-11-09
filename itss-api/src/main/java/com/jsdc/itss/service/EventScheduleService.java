package com.jsdc.itss.service;

import cn.hutool.cron.CronUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventScheduleDao;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.LogVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Service
@Transactional
public class EventScheduleService extends BaseService<EventScheduleDao, EventSchedule> {

    @Autowired
    private EventScheduleMapper eventScheduleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EventScheduleSonMapper eventScheduleSonMapper;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private EventTemplateService eventTemplateService;

    /**
     * 定时任务列表接口
     * Author wzn
     * Date 2022/3/14 14:57
     */
    public ResultInfo getPage(EventSchedule eventSchedule, int pageIndex, int pageSize) {
        QueryWrapper<EventSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(eventSchedule.getSchedule_type())) {
            queryWrapper.eq("schedule_type", eventSchedule.getSchedule_type());
        }
        if (StringUtils.isNotEmpty(eventSchedule.getRemark())) {
            queryWrapper.like("remark", eventSchedule.getRemark());
        }
        if (StringUtils.isNotEmpty(eventSchedule.getStatus())) {
            queryWrapper.eq("status", eventSchedule.getStatus());
        }
        if (eventSchedule.getCreate_user() != null) {
            queryWrapper.eq("create_user", eventSchedule.getCreate_user());
        }
        if (eventSchedule.getEvent_id() != null) {
            queryWrapper.eq("event_id", eventSchedule.getEvent_id());
        }
        queryWrapper.orderByDesc("create_time");
        PageHelper.startPage(pageIndex, pageSize);
        List<EventSchedule> list = eventScheduleMapper.selectList(queryWrapper);
        if (null != list) {
            for (EventSchedule e : list) {
                QueryWrapper<EventScheduleSon> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("event_schedule_id", e.getId());
                queryWrapper1.eq("is_del", "0");
                List<EventScheduleSon> eventScheduleSonList = eventScheduleSonMapper.selectList(queryWrapper1);
                StringBuilder builder = new StringBuilder();
                for (EventScheduleSon scheduleSon : eventScheduleSonList) {
                    EventTemplate eventTemplate = eventTemplateService.selectById(scheduleSon.getEvent_id());
                    builder.append(eventTemplate.getTemplate_name()).append(",");
                }
                if (builder.length() > 0) {
                    e.setEvent_id_name(builder.deleteCharAt(builder.length() - 1).toString());
                }

                SysUser sysUser = sysUserService.selectById(e.getCreate_user());
                if (null != sysUser) {
                    e.setCreate_user_name(sysUser.getName());
                }

            }
        }
        PageInfo<EventSchedule> pageInfo = new PageInfo<EventSchedule>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 生成 Cron表达式
     * ,：表示一个列表。举例：使用在秒域中10、20、30。表示10秒、20秒、30秒
     * - ：表示一个范围。举例：使用在秒域中1-3。表示1秒、2秒、3秒
     * * ：表示所有合法值。举例：使用在秒域中*。表示每1秒
     * / ：表示递增。举例：使用在秒域中0/5。表示从第0秒开始每隔5秒
     * ？：只能使用在日和周两个域中，表示不指定：日和周两个域如果同时指定就可能会发生冲突。举例：每个月1号的星期三。这里无法保证每个月1号都是星期三，这是不现实的，所以如果指定了每月1号那么周的域就用？代替、反之如果指定了周，那么日域就用？代替。
     * # ：只能使用在周域中，用于指定月份中的第几周的第几天。举例：1#2表示第二个星期日（注意1是星期日）
     * L：只能使用在日和周两个域中，表示合法值中的最后一天。注意：周域上的最后一天是周六
     * w：只能使用在日域中，工作日（周一到周五）。举例：使用在日域中15w，表示离15号最近的工作日，如果15号是工作日那么结果就是15号。如果15号是星期日那么结果就是16号
     */
    public String onCron(EventSchedule eventSchedule) {
        //schedule_type 类型 1每工作日 2每天 3 每周 4每月 5一次性
        String cron_expression = "";
        //每工作日cron表达式0 08 14 ? 3,4 1,2,3,4,5 2022-2022
        if ("1".equals(eventSchedule.getSchedule_type())) {
            String hour = eventSchedule.getExecute_time().substring(0, 2);
            String minutes = eventSchedule.getExecute_time().substring(3, 5);
            //工作日 周一到周五
            String workDays = "2,3,4,5,6";
            if (Base.notEmpty(eventSchedule.getInterval_day())) {
                workDays = eventSchedule.getInterval_day();
            }
            cron_expression = "0 " + minutes + " " + hour + " ? * " + workDays;
        } else if ("2".equals(eventSchedule.getSchedule_type())) {
            //cron表达式  每天 0 7 10 * 7-10 ? 2022-2023 或 0 7 10 * * ? 2022-2023 或 0 7 10 * * ? *
            String hour = eventSchedule.getExecute_time().substring(0, 2);
            String minutes = eventSchedule.getExecute_time().substring(3, 5);
            cron_expression = "0 " + minutes + " " + hour + " * * ?";
        } else if ("3".equals(eventSchedule.getSchedule_type())) {
            //每周 0 3 4 ? 4,5 1,2,3,4,5,6 2022-2022
            String hour = eventSchedule.getExecute_time().substring(0, 2);
            String minutes = eventSchedule.getExecute_time().substring(3, 5);
            //周 1-7 （1代表周1，7代表周日） 默认为周一 1
            String week = "1";
            if (Base.notEmpty(eventSchedule.getWeek())) {
                week = eventSchedule.getWeek();
            }
            cron_expression = "0 " + minutes + " " + hour + " ? * " + week;
        } else if ("4".equals(eventSchedule.getSchedule_type())) {
            //每月0 9 10 1,2,3,4,5,6,7,8 1,2,3 ? 2022-2022
            String hour = eventSchedule.getExecute_time().substring(0, 2);
            String minutes = eventSchedule.getExecute_time().substring(3, 5);
            //月
            String month = "*";
            if (Base.notEmpty(eventSchedule.getMonth())) {
                month = eventSchedule.getMonth();
            }
            String days = "1";
            if (Base.notEmpty(eventSchedule.getDay())) {
                days = eventSchedule.getDay();
            }
            cron_expression = "0 " + minutes + " " + hour + " " + days + " " + month + " ?";
        } else if ("5".equals(eventSchedule.getSchedule_type())) {
            //一次性0 30 9 9 5 ? 2022-2022
            String hour = eventSchedule.getExecute_time().substring(0, 2);
            String minutes = eventSchedule.getExecute_time().substring(3, 5);
            DateFormat format = new SimpleDateFormat("MM");
//            DateFormat format2 = new SimpleDateFormat("yyyy");
            DateFormat format3 = new SimpleDateFormat("dd");
            String bagainMM = format.format(eventSchedule.getBegin_time());
//            String bagainyy = format2.format(eventSchedule.getBegin_time());
            String bagaindd = format3.format(eventSchedule.getBegin_time());
            cron_expression = "0 " + minutes + " " + hour + " " + bagaindd + " " + bagainMM + " ?";
        }
        return cron_expression;
    }

    /**
     * 新增定时任务
     * Author wzn
     * Date 2022/3/14 14:57
     */
    public ResultInfo addEventSchedule(EventSchedule eventSchedule) {
        eventSchedule.setCron_expression(onCron(eventSchedule));
        eventSchedule.setStatus("1");
        eventSchedule.setStop("0");
        eventSchedule.setIs_del("0");
        eventSchedule.setCreate_time(new Date());
        eventSchedule.setCreate_user(sysUserService.getUser().getId());
        eventScheduleMapper.insert(eventSchedule);
        //向子表插数据
        if (null != eventSchedule.getEventTemplateList()) {
            for (EventTemplate e : eventSchedule.getEventTemplateList()) {
                EventScheduleSon eventScheduleSon = new EventScheduleSon();
                eventScheduleSon.setEvent_schedule_id(eventSchedule.getId());
                eventScheduleSon.setEvent_id(e.getId());
                eventScheduleSon.setIs_del("0");
                eventScheduleSon.insert();
            }
        }
        return ResultInfo.success("新增成功", new LogVo(eventSchedule.getId(), "新增", "新增定时任务"));
    }

    /**
     * 修改定时任务
     *
     * @param eventSchedule
     * @return
     */
    public ResultInfo editEventSchedule(EventSchedule eventSchedule) {
        //状态 0运行  1停止
        if (eventSchedule.getStatus().equals("0")) {
            CronUtil.remove(eventSchedule.getId() + "");
        }
        eventSchedule.setCron_expression(onCron(eventSchedule));
        //状态 0启动  1未启动
        eventSchedule.setStatus("1");
        //停止标志  停止过一次  状态就是1  默认0
        eventSchedule.setStop("0");
        eventSchedule.setUpdate_time(new Date());
        eventSchedule.setUpdate_user(sysUserService.getUser().getId());
        eventScheduleMapper.updateById(eventSchedule);
        //子表先删再增
        QueryWrapper<EventScheduleSon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_schedule_id", eventSchedule.getId());
        eventScheduleSonMapper.delete(queryWrapper);
        if (null != eventSchedule.getEventTemplateList()) {
            for (EventTemplate e : eventSchedule.getEventTemplateList()) {
                EventScheduleSon eventScheduleSon = new EventScheduleSon();
                eventScheduleSon.setEvent_schedule_id(eventSchedule.getId());
                eventScheduleSon.setEvent_id(e.getId());
                eventScheduleSon.setIs_del("0");
                eventScheduleSon.insert();
            }
        }
        return ResultInfo.success("修改成功", new LogVo(eventSchedule.getId(), "修改", "修改定时任务"));
    }

    /**
     * 启动定时任务
     * Author wzn
     * Date 2022/3/14 16:32
     */
    public ResultInfo start(Integer id) {
        EventSchedule eventSchedule = eventScheduleMapper.selectById(id);
        //状态 0运行  1停止
        eventSchedule.setStatus("0");
        eventSchedule.setStop("0");
        eventScheduleMapper.updateById(eventSchedule);

        Job1 job1 = new Job1(eventSchedule);
        CronUtil.schedule(eventSchedule.getId() + "", eventSchedule.getCron_expression(), job1);

        return ResultInfo.success("启动成功", new LogVo(eventSchedule.getId(), "启动", "启动定时任务"));
    }

    /**
     * 停止定时任务
     * Author wzn
     * Date 2022/3/11 13:54
     */
    public ResultInfo pause(Integer id) {
        EventSchedule eventSchedule = new EventSchedule();
        CronUtil.remove(id + "");
        eventSchedule.setStatus("1");
        eventSchedule.setStop("1");
        eventSchedule.setId(id);
        eventScheduleMapper.updateById(eventSchedule);
        return ResultInfo.success("停止成功", new LogVo(eventSchedule.getId(), "停止", "停止定时任务"));
    }

    /**
     * 重启定时任务
     * Author wzn
     * Date 2022/3/11 13:54
     */
    public ResultInfo restart(Integer id) {
        //暂停定时任务
        pause(id);
        //启动定时任务
        start(id);
        return ResultInfo.success("重启成功", new LogVo(id, "重启", "重启定时任务"));
    }

    //定时任务详情信息
    public EventSchedule info(Integer id) {
        EventSchedule eventSchedule = eventScheduleMapper.selectById(id);
        QueryWrapper<EventScheduleSon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_schedule_id", eventSchedule.getId());
        queryWrapper.eq("is_del", "0");
        List<EventScheduleSon> eventScheduleSonList = eventScheduleSonMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(eventScheduleSonList)) {
            List<Integer> ids = eventScheduleSonList.stream().map(EventScheduleSon::getEvent_id).collect(Collectors.toList());
            EventTemplate eventTemplate = new EventTemplate();
            eventTemplate.setIds(ids);
            eventSchedule.setEventTemplateList(eventTemplateService.getList(eventTemplate));
        }
        return eventSchedule;
    }

    public ResultInfo del(Integer id) {
        EventSchedule eventSchedule = eventScheduleMapper.selectById(id);
        //状态 0运行  1停止
        if (eventSchedule.getStatus().equals("0")) {
            CronUtil.remove(eventSchedule.getId() + "");
        }
        eventSchedule.setIs_del("1");
        eventSchedule.setUpdate_time(new Date());
        eventSchedule.setUpdate_user(sysUserService.getUser().getId());
        eventScheduleMapper.updateById(eventSchedule);
        return ResultInfo.success("删除成功", new LogVo(eventSchedule.getId(), "删除", "删除定时任务"));
    }

    public ResultInfo getLogInfoById(Integer id) {
        // 相关日志信息
        List<SysLog> sysLogs = sysLogService.selectList(Wrappers.<SysLog>lambdaQuery().
                eq(SysLog::getBill_id, id).eq(SysLog::getModule, LogEnums.LOG_Schedule.getValue()));
        return ResultInfo.success(sysLogs);
    }

}
