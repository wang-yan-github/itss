package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: Question
 * Description: 问题管理表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("question")
@Table(name = "question")
@Data
public class Question extends Model<Question> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //来源id 来源通过设置-问题配置中的问题来源管理中获取
    private Integer source_id;
    //3	优先级	设置-服务级别协议中的问题优先级管理中获取
    private Integer priority_id;
    //4	问题类别id	问题类别通过设置-问题配置中的问题类别管理中获取
    private Integer category_id;
    //5	标题
    private String title;
    //6	描述
    private String description;
    //7	期望解决时间 yyyy-MM-dd
    private String hope_solve_time;
    //8	问题鉴定通知 0否 1是
    private String is_notice;
    //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
    private String status;

    //11 问题单号
    private String code;
    //14 鉴定人 用户表id
    private Integer appraisal_user;
    //15 鉴定时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appraisal_time;
    //16 鉴定意见
    private String appraisal_opinion;
    //17 处理人
    private Integer handle_user;
    //18 解决人 用户表id
    private Integer solve_user;
    //19 解决时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date solve_time;
    //20 解决方案
    private String solve_content;
//    //21 审核人 用户表id
//    private Integer audit_user;
//    //22 审核时间
//    private Date audit_time;
    //23 审核内容
    @Transient
    @TableField(exist = false)
    private String audit_content;
    //24 关单人 用户表id
    private Integer clearance_user;
    //25 关单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date clearance_time;
    //26 sla:(分钟)
    private Integer sla_time;
    //SLA超时时间
    private Integer sla_exceed_time;
    //是否符合SLA 0:否  1：是
    private String isFitSla;
    //27 关单时限
    private String time_limit;
    //28 解决分钟数
    private String resolution_minutes;
    //29 当前操作人 用户表id
    private Integer now_operator_user;

    //转办备注
    private String transfer_remark;
    //挂起前的状态
    private String hang_status;

    //18 审核人ID
    @Transient
    @TableField(exist = false)
    private Integer review_user;

    //24 关单时间开始
    @Transient
    @TableField(exist = false)
    private Date clearance_time_start;
    //24 关单时间结束
    @Transient
    @TableField(exist = false)
    private Date clearance_time_end;

    //是否生成了知识
    private Integer is_knowledge;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    //创建时间开始
    @Transient
    @TableField(exist = false)
    private String create_time_start;
    //创建时间结束
    @Transient
    @TableField(exist = false)
    private String create_time_end;

    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    /**
     * 更新人id
     */
    private Integer update_user;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;

    @Transient
    @TableField(exist = false)
    private String remark;

    /**
     * 创建人名称
     */
    @Transient
    @TableField(exist = false)
    private String create_user_name;

    /**
     * 当前操作人名称
     */
    @Transient
    @TableField(exist = false)
    private String now_operator_user_name;
    /**
     * 类别名称
     */
    @Transient
    @TableField(exist = false)
    private String category_name;

    /**
     * 优先级名称
     */
    @Transient
    @TableField(exist = false)
    private String priority_name;

    /**
     * 状态名称
     */
    @Transient
    @TableField(exist = false)
    private String status_name;


    @Transient
    @TableField(exist = false)
    private List<Question> success = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<Question> error = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<Question> all = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String source_name;

    @Transient
    @TableField(exist = false)
    private String is_success;

    @Transient
    @TableField(exist = false)
    private Integer userId;

    //SLA剩余时间
    @Transient
    @TableField(exist = false)
    private String sla_surplus_time;

    @Transient
    @TableField(exist = false)
    //数量
    private  Integer num ;

}
