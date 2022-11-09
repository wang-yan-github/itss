package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * ClassName: SlaEventUrgent
 * Description: 事件紧急度
 * date: 2022/3/7 13:59
 *
 * @author bn
 */
@Entity
@TableName("sla_event_urgent")
@Table(name = "sla_event_urgent")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaEventUrgent extends Model<SlaEventUrgent> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //名称
    private String urgent_name;
    //排序
    private String sort;
    //状态 0：禁用 1：启用
    private String is_use;
    //设为默认值 0.是 1.否 有且仅有一个
    private String default_value;
    //描述
    private String remark;
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

}
