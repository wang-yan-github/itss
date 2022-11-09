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
import java.util.List;

/**
 * 工单类别管理表
 */
@Entity
@TableName("event_category")
@Table(name = "event_category")
@Data
public class EventCategory extends Model<EventCategory> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name; //工单名称
    private Integer is_use;//状态 0：禁用 1：启用
    private Integer parent_id;//上级工单类别ID
    private Integer customer_contract_id;//合同ID
    private Integer is_send_charge;//是否抄送负责人
    private Integer sort;//排序
    private String remark;//描述

    //客户合同名称
    @Transient
    @TableField(exist = false)
    private String contractName ;

    @Transient
    @TableField(exist = false)
    private String parent_id_name;

    //抄送负责人
    @Transient
    @TableField(exist = false)
    private String is_send_charge_name;

    @Transient
    @TableField(exist = false)
    private List<EventCategory> children;

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
