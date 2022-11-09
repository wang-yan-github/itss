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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionCategory
 * Description: 问题类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("question_category")
@Table(name = "question_category")
@Data
public class QuestionCategory extends Model<QuestionCategory> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	名称
    private String name;
    //3	状态 0：禁用 1：启用
    private String is_use;
    //4	描述
    private String description;
    //5	父级类别
    private Integer parent_id;
    @Transient
    @TableField(exist = false)
    private String parent_name;
    //6	排序
    private Integer sort;
    //7	SLA	 sla通过设置-服务级别协议-事件sla管理获取。
    private Integer sla_id;
    //8	鉴定人 用户表id
    private Integer appraisal_user;
    @Transient
    @TableField(exist = false)
    private String appraisal_name;
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

    /**
     * 合同ID
     */
    private Integer customer_contract_id;

    //客户合同名称
    @Transient
    @TableField(exist = false)
    private String contractName ;

    //子集
    @Transient
    @TableField(exist = false)
    private List<QuestionCategory> children = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String is_use_name;

    @Transient
    @TableField(exist = false)
    //	审核人
    private String review_user_name;

    @Transient
    @TableField(exist = false)
    private List<SysUser> list =new ArrayList<>();



    @Transient
    @TableField(exist = false)
    //数量
    private  Integer num ;


}
