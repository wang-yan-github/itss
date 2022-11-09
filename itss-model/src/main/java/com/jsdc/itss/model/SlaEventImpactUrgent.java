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
 * ClassName: SlaEventImpactUrgent
 * Description: 事件影响度紧急度 由影响度，紧急度交叉遍历后选优先级
 * date: 2022/3/7 14:05
 *
 * @author bn
 */
@Entity
@TableName("sla_event_impact_urgent")
@Table(name = "sla_event_impact_urgent")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaEventImpactUrgent extends Model<SlaEventImpactUrgent> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    //事件影响度id
    private Integer impact_id;
    //事件紧急度id
    private Integer urgent_id;
    //事件优先级id
    private Integer first_id;

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
