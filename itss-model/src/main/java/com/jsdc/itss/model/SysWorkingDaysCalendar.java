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
 * <p>生成日历表<p>
 *
 * @author 徐傲龙
 * @since 2022年04月14日 9:21
 */
@Entity
@TableName("sys_working_days_calendar")
@Table(name = "sys_working_days_calendar")
@Data
public class SysWorkingDaysCalendar extends Model<SysWorkingDaysCalendar> implements Serializable {


    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dateStr;

    /**
     * 年份名称
     */
    private Integer year;

    /**
     * 月份名称
     */
    private Integer month;

    /**
     * 每一天的名称
     */
    private Integer day;

    /**
     * 年月日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;



    /**
     * 每一天对应的周几
     */
    private String week;



    /**
     * 每一天的性质 0 节假日 1 双休日  2 工作日
     */
    private Integer grade ;


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

    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date value;

}
