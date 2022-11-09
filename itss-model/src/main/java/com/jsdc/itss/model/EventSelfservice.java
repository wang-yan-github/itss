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
 * 事件参数-自助服务子表
 *
 * @author wp
 */
@Entity
@TableName("event_selfservice")
@Table(name = "event_selfservice")
@Data
public class EventSelfservice extends Model<EventSelfservice> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    //    2	事件参数id	solution_id	int		事件参数id
    private Integer solution_id;
    //3	记住报障类别	remember_bzlb	varchar(2)		记住报障类别
    private String remember_bzlb;
    //4	报障页显示配置项选择	pzxxz	varchar(2)		报障页显示配置项选择0否1是
    private String pzxxz;
    //5	扫码报障标题模板	title_template	varchar(255)		扫码报障标题模板:
    private String title_template;
    //            6	扫码报障描述模板	description_template	varchar(255)		扫码报障描述模板:
    private String description_template;


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
