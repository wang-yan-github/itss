package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.vo.ChangeVo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: PublishBasicInfo
 * Description: 发布基础信息
 * date: 2022/3/14 9:33
 *
 * @author wh
 **/
@Entity
@TableName("publish_basic_info")
@Table(name = "publish_basic_info")
@Data
public class PublishBasicInfo extends Model<PublishBasicInfo> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发布类别
     */
    private String category;

    /**
     * 发布类别名称
     */
    @Transient
    @TableField(exist = false)
    private String category_name;

    /**
     * 发布类型 1:一般发布 2：紧急发布
     */
    private String type;
    @Transient
    @TableField(exist = false)
    private String type_name;

    /**
     * 发布单号
     */
    private String issue_no;

    /**
     * 标题
     */
    private String title;

    /**
     * 申请发布理由
     */
    private String reason;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布方案
     */
    private String publish_plan;

    /**
     * 附件
     */
    private String enclosure;

    /**
     * 发布添加通知
     */
    private String add_notice;

    /**
     * 发布修改通知
     */
    private String edit_notice;

    /**
     * 发布通知 0:否 1：是
     */
    private String publish_notice;

    /**
     * 发布备注
     */
    private String remarks;

    /**
     * 状态 0:已撤销 1:待评审 2:评审拒绝 3:待测试 4:待发布 5:发布成功 6:发布失败
     */
    private String status;
    @Transient
    @TableField(exist = false)
    private String status_name;

    /**
     * 发布人
     */
    private String publisher;

    /**
     * 发布人名称
     */
    @Transient
    @TableField(exist = false)
    private String publisher_name;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publish_time;

    /**
     * 测试人
     */
    private String tester_id;

    /**
     * 测试人名称
     */
    @Transient
    @TableField(exist = false)
    private String tester_name;

    /**
     * 评审人
     */
    private String approver;

    /**
     * 当前评审人
     */
    private String current_approver;

    /**
     * 测试方案
     */
    private String test_plan;

    /**
     * 测试结果
     */
    private String test_result;

    /**
     * 测试时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date test_time;

    /**
     * 关联变更id
     */
    private String change_id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 创建时间开始
     */
    @Transient
    @TableField(exist = false)
    private String create_time_start;

    /**
     * 创建时间结束
     */
    @Transient
    @TableField(exist = false)
    private String create_time_end;

    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 创建人名称
     */
    @Transient
    @TableField(exist = false)
    private String create_user_name;

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
     * 关键字
     */
    @Transient
    @TableField(exist = false)
    private String keyword;

    /**
     * 关联变更集合
     */
    @Transient
    @TableField(exist = false)
    private List<ChangeVo> changeVoList;

    /**
     * 发布评审信息
     */
    @Transient
    @TableField(exist = false)
    private PublishApprovalInfo  publishApprovalInfo;

    /**
     * 当前操作人
     */
    @Transient
    @TableField(exist = false)
    private String operator;

    /**
     * 审批状态 1.审批通过 2.审批拒绝
     */
    @Transient
    @TableField(exist = false)
    private String isPass;

    /**
     * 上传的文件
     */
    @Transient
    @TableField(exist = false)
    private String uploadedFiles;

    /**
     * 发布文件实体类
     */
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManages = new ArrayList<>();
    /**
     * 审核意见
     */
    @Transient
    @TableField(exist = false)
    private String approval_opinions;

    @Transient
    @TableField(exist = false)
    List<PublishBasicInfo> success = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    List<PublishBasicInfo> error = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    List<PublishBasicInfo> all  = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private String is_success;

    private Integer pass_approve;

    @Transient
    @TableField(exist = false)
    private Integer userId;

}
