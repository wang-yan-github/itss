package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.vo.PublishApproveInfo;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * ClassName: PublishCategoryInfo
 * Description: 发布类别管理
 * date: 2022/3/7 14:11
 *
 * @author wh
 **/
@Entity
@TableName("publish_category_info")
@Table(name = "publish_category_info")
@Data
public class PublishCategoryInfo {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态 0：禁用 1：启用
     */
    private String is_use;

    /**
     * 发布人id
     */
    private Integer publisher;

    /**
     * 评审人
     */
    private String approver;

    /**
     * 发布人姓名
     */
    @Transient
    @TableField(exist = false)
    private String publisherName;

    /**
     * 发布类别审批表
     */
    @Transient
    @TableField(exist = false)
    private List<PublishCategoryApprove> publishCategoryApproves;

    /**
     * 发布类别评审人信息
     */
    @Transient
    @TableField(exist = false)
    private List<PublishApproveInfo> publishApproveInfos;

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
