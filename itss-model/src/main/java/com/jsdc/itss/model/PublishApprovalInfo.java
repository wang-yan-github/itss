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
 * ClassName: PublishApprovalInfo
 * Description: 发布评审信息
 * date: 2022/3/14 9:33
 *
 * @author wh
 **/
@Entity
@TableName("publish_approval_info")
@Table(name = "publish_approval_info")
@Data
public class PublishApprovalInfo extends Model<PublishApprovalInfo> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发布基础信息
     */
    private Integer basic_info_id;

    /**
     * 评审人
     */
    private Integer approver;

    /**
     * 评审人名称
     */
    @Transient
    @TableField(exist = false)
    private String approver_name;

    /**
     * 评审意见
     */
    private String approval_opinions;

    /**
     * 评审时间
     */
    private Date approval_time;

    /**
     * 审批状态 1.审批通过 2.审批拒绝
     */
    private String status;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;
}
