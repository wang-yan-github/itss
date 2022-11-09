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
 * ClassName: SlaEventFirst
 * Description: 事件优先管理
 * date: 2022/3/7 14:02
 *
 * @author bn
 */
@Entity
@TableName("sla_event_first")
@Table(name = "sla_event_first")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaEventFirst extends Model<SlaEventFirst> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //名称
    private String first_name;
    //状态 0：禁用 1：启用
    private String is_use;
    //Vip 0.否 1.是
    private String vip;
    //排序
    private String sort;
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
