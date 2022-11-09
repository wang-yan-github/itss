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
 * ClassName: SlaEventConfig
 * Description: 事件sla配置
 * date: 2022/3/8 9:40
 *
 * @author bn
 */
@Entity
@TableName("sla_event_config")
@Table(name = "sla_event_config")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaEventConfig extends Model<SlaEventConfig> implements Serializable {


    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //事件sla管理id
    private Integer sla_event_id;
    //事件优先级id
    private Integer first_id;
    //事件请求类型id
    private Integer request_type_id;
    //响应时间(分钟数)
    private String response_time;
    //解决时间(分钟数)
    private String solve_time;

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
