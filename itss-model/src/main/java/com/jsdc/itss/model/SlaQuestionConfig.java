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
 * ClassName: SlaQuestionConfig
 * Description:
 * date: 2022/3/8 9:42
 *
 * @author bn
 */
@Entity
@TableName("sla_question_config")
@Table(name = "sla_question_config")
@DynamicInsert
@DynamicUpdate
@Data
public class SlaQuestionConfig extends Model<SlaQuestionConfig> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //问题优先级id
    private Integer first_id;
    //问题sla管理id
    private Integer question_id;
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

    //解决时间(分钟数)
    @Transient
    @TableField(exist = false)
    private String first_name;
}
