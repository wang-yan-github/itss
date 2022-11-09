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
 * 请求类型管理表名(event_request_type)
 *
 * @Author libin
 * @create 2022/3/7 15:14
 */

@Entity
@TableName("event_request_type")
@Table(name = "event_request_type")
@Data
public class EventRequestType extends Model<EventRequestType> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String request_type_name;// 名称
    private Integer is_use;//状态  状态 0：禁用 1：启用
    private Integer sort;// 排序
    private Integer is_default;//是否默认0否 1是

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
    private String name;

}
