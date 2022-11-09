package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
  *  ClassName: SysWorkingDays
  *  Description: 工作日管理
  *  @author zhangdequan
 */
@Entity
@TableName("sys_working_days")
@Table(name = "sys_working_days")
@Data
public class SysWorkingDays{

    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 工作日名称
     */
    private String work_name;

    /**
     * （数据字典）当前记录状态 0停用 1使用
     */
    private String is_use;

    /**
     * （数据字典）默认 0是 1否
     */
    private String acquiesce;

    /**
     * 是否分别设置上下班时间 0否 1是
     */
    private Integer is_distinguish;

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

    /**
     * 工作日关联表集合
     */
    @Transient
    @TableField(exist = false)
    private List<SysWorkingDaysDistinguish> distinguishes = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private SysWorkingDaysDistinguish sysWorkingDaysDistinguish ;
    @Transient
    @TableField(exist = false)
    private String start_time_AM;
    @Transient
    @TableField(exist = false)
    private String end_time_AM;
    @Transient
    @TableField(exist = false)
    private String start_time_PM;
    @Transient
    @TableField(exist = false)
    private String end_time_PM;

}
