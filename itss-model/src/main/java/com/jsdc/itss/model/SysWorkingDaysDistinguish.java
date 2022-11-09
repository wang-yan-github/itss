package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 *  ClassName: SysWorkingDaysDistinguish
 *  Description: 工作日管理副表
 *  @author zhangdequan
 */
@Entity
@TableName("sys_working_days_distinguish")
@Table(name = "sys_working_days_distinguish")
@Data
public class SysWorkingDaysDistinguish{

    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 工作日主键id
     */
    private Integer work_day_id;

    /**
     * 名称
     */
    private String week_name;

    /**
     * 上午上班开始时间
     */
    private String start_time_AM;

    /**
     * 上午上班结束时间
     */
    private String end_time_AM;

    /**
     * 下午上班开始时间
     */
    private String start_time_PM;

    /**
     * 下午上班结束时间
     */
    private String end_time_PM;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
