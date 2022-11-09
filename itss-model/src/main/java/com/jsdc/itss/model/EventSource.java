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
import java.util.Date;

/**
 * 来源管理表
 *
 * @author wp
 */
@Entity
@TableName("event_source")
@Table(name = "event_source")
@Data
public class EventSource extends Model<EventSource> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	来源名称 来源名称
    private String resource_name;
    /**
     * 状态 0：禁用 1：启用
     */
    private String is_use;
    //3	排序排序
    private Integer sort;
    //5	描述
    private String description;
    //6	默认工程师开单	 0 否  1是
    private String is_def_engineer;
    //7	微信开单	 0 否  1是
    private String is_def_wechat;
    //8	网页自助报障	 0 否  1是
    private String is_def_webpage;

    //客户合同名称
    @Transient
    @TableField(exist = false)
    private String contractName ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

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
