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
 * ClassName: SlaQuestionFirst
 * Description: 问题优先管理
 * date: 2022/3/7 14:12
 *
 * @author bn
 */
@Entity
@TableName("sla_question_first")
@Table(name = "sla_question_first")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaQuestionFirst extends Model<SlaQuestionFirst> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //状态0：禁用 1：启用
    private String is_use;
    // 名称
    private String first_name;
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

    //解决时间(分钟数)
    @Transient
    @TableField(exist = false)
    private String solve_time;

}
