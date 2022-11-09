package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: SlaEvent
 * Description: 事件sla管理
 * date: 2022/3/7 14:09
 *
 * @author bn
 */
@Entity
@TableName("sla_event")
@Table(name = "sla_event")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaEvent extends Model<SlaEvent> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //名称
    private String sla_event_name;
    //描述
    private String remark;
    //考虑优先级 0.否 1.是
    private String is_first;
    //考虑请求类型 0.否 1.是
    private String is_request;


    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    //创建人
    private Integer create_user;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;
    //更新人
    private Integer update_user;
    //是否删除
    private String is_del;

    //工单类型ID
    @Transient
    @TableField(exist = false)
    private Integer eventCategoryId;

}
