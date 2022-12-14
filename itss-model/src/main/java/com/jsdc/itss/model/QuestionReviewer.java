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
 * ClassName: QuestionReviewer
 * Description: 问题审核人关联表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("question_reviewer")
@Table(name = "question_reviewer")
@Data
public class QuestionReviewer extends Model<QuestionReviewer> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	问题id
    private Integer question_id;
    //3	审核人 用户表id
    private Integer review_user;
    //排序
    private Integer sort;
    //审核内容
    private String content;
    //审核标题
    private String title;

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

    //18 审核人姓名
    @Transient
    @TableField(exist = false)
    private String review_user_name;

    @Transient
    @TableField(exist = false)
    private Integer WX_userId;

}
