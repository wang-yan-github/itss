package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 事件模板表
 *
 * @author wp
 */
@Entity
@TableName("event_template")
@Table(name = "event_template")
@Data
public class EventTemplate extends Model<EventTemplate> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String templateNumber;//模板编号

    private String template_name;//模板名称

    private String is_public;//是否公共模板  0否1是

    private int event_service_id;//所属群组

    @Transient
    @TableField(exist = false)
    private String event_service_name;//所属群组

    private String is_use;//是否启用 0：禁用 1：启用

    private String template_desc;//模板描述

    private int user_id;//用户

    private String title;//工单标题

    private String description;//工单描述

    private String source;//来源

    private String request_type;//请求类型

    private String bill_type;//工单类别
    @Transient
    @TableField(exist = false)
    private String bill_type_name;//工单类别名称

    private String effect_degree;//影响度

    private String urgency_degree;//紧急度

    private String is_effect;//影响系统可用性

    private int service_groups;//处理服务群组

    private int handler;//处理人

    private Date ex_solve_time;//预计解决时间

    private String solve_type;//解决方式

    private String programme_type;//方案类别
    @Transient
    @TableField(exist = false)
    private String programme_type_name;//方案类别

    private String expenses;//费用

    private String solution;//解决方案

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 创建人id
     */
    private Integer create_user;

    @Transient
    @TableField(exist = false)
    private String create_user_name;//创建人名称

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;

    /**
     * 更新人id
     */
    private Integer update_user;

    @Transient
    @TableField(exist = false)
    private String update_user_name;//修改人人名称

    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;

    @Transient
    @TableField(exist = false)
    private List<Integer> ids = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String user_name;//用户名称

    @Transient
    @TableField(exist = false)
    private String source_name;//来源名称

    @Transient
    @TableField(exist = false)
    private String request_type_name;//请求类型名称

    @Transient
    @TableField(exist = false)
    private String handler_name;//处理人名称

    @Transient
    @TableField(exist = false)
    private String urgency_degree_name;//紧急度名称

    @Transient
    @TableField(exist = false)
    private String effect_degree_name;//影响度名称

    @Transient
    @TableField(exist = false)
    private String solve_type_name;//解决方式名称


}
