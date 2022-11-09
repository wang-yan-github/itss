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
 * Description: 服务群组主表
 * date: 2021/12/30 9:10
 */
@Entity
@TableName("event_service_manage")
@Table(name = "event_service_manage")
@Data
public class EventServiceManage extends Model<EventServiceManage> implements Serializable {
    /**
     * ID
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //名称
    private String name;

    //状态 0：禁用 1：启用
    private String is_use;
    //非一线组  0：否 1：是
    private String nonFirstLineGroup;

    private String description;

    //状态名称
    @Transient
    @TableField(exist = false)
    private String is_use_name;

    //非一线组
    @Transient
    @TableField(exist = false)
    private String nonFirstLineGroup_name;
    //负责人名称
    @Transient
    @TableField(exist = false)
    private String principalName;
    //工程师
    @Transient
    @TableField(exist = false)
    private String engineerName;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    //工作日 名称
    @Transient
    @TableField(exist = false)
    private String work_name;

    //排序
    private String sort;

    //OLA超时分钟数
    private Integer ola_time_out;


    //根据工作日自动暂停和恢复事件单:0 否  1是
    private String is_working_day;
    //手动暂停的事件单自动恢复:0 否  1是
    private String is_pause_manually;

    //工作日配置ID
    private String work_id;

    //处理人为空自动指定工程师
    private String auto_engineer;
    @Transient
    @TableField(exist = false)
    private String auto_engineer_name;

    //用户开单是否自动指派工程师字典  0 否  1是
    private String is_user_create;

    //工程师开单是否自动指派工程师字典  0否  1是
    private String is_engineer_create;

    //指派自动指派工程师转单自动指派工程师0否  1是
    private String is_assign_automatic;

    //转单自动指派工程师转单自动指派工程师0否  1是
    private String is_automatic_transfer;

    //自动指派方式字典 0 权重  1轮询
    private String assign_type;

    //待处理工单数权重
    private String to_be_processed;

    //预计完成时间数权重
    private String expected_to_complete;

    //响应SLA超时X分钟后升级:
    private String sla_upgrade;

    //解决SLA超时Y分钟后升级
    private String sla_solve;

    //响应前，OLA超时A分钟后升级:
    private String ola_font_upgrade;

    //响应后，OLA超时B分钟后升级:
    private String ola_after_upgrade;

    //超时升级人
    private Integer timeout_user_id;

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
