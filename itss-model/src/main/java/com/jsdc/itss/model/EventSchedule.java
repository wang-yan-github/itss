package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工单定时任务表
 *
 * @author wp
 */
@Entity
@TableName("event_schedule")
@Table(name = "event_schedule")
@Data
public class EventSchedule extends Model<EventSchedule> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String schedule_type;//类型 1每工作日 2每天 3 每周 4每月 5一次性
    private String execute_time;//执行时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin_time;//开始日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_time;//结束日期
    private String cron_expression;//定时任务表达式
    private String interval_day;//每(工作日)
    private Integer event_id;//工单模板
    @Transient
    @TableField(exist = false)
    private String event_id_name;//工单模板名称
    private String remark;//备注
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime; //上次执行时间
    private String status;//状态 0运行  1停止
    private String week; //选中哪几个星期1,2,3,4
    private String day; //选中哪天1,2,3,4
    private String month; //选中哪月1,2,3,4
    //停止标志  停止过一次  状态就是1  默认0
    private String stop;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private List<EventTemplate> eventTemplateList = new ArrayList<>();

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 创建人id
     */
    private Integer create_user;

    @Transient
    @TableField(exist = false)
    private String create_user_name;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 更新人id
     */
    private Integer update_user;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;


}
