package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 工单处理各阶段时间表
 *
 * @author wp
 */
@Entity
@TableName("event_times")
@Table(name = "event_times")
@Data
public class EventTimes extends Model<EventTimes> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer event_id;//事件工单ID
    private Integer response_times;//响应总时间
    private Integer response_exceed_times;//响应超时时间
    private Integer handle_times;//处理总时间
    private Integer handle_exceed_times;//处理超时时间
    private Date last_pause_time;//上次暂停时间
    private String last_pause_type ;//上次暂停类型 1：手动 2：自动
    private Integer manual_pause_times ;//手动暂停总时间
    private Integer auto_pause_times;//自动暂停总时间


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
