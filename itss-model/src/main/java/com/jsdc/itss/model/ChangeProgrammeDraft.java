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
 * ClassName: ChangeProgrammeDraft
 * Description: 变更方案拟定
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_programme_draft")
@Table(name = "change_programme_draft")
@Data
public class ChangeProgrammeDraft extends Model<ChangeProgrammeDraft> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	变更id 关联变更信息
    private Integer change_id;
    //3	方案拟定人 关联用户信息
    private Integer programme_user;
    //4	变更方案 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String change_programme;
    //5	回退计划 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String fallback_plan;
    //6	风险评估 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String risk_assessment;
    //7	实施人 关联用户信息
    private Integer implement_user;



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
