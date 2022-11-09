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
 * @ClassName KnowledgeCategory 知识类别
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/8 9:33
 * @Version 1.0
 */
@Entity
@TableName("knowledge_category")
@Table(name = "knowledge_category")
@Data
public class KnowledgeCategory extends Model<KnowledgeCategory> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键
    private String name;//名称
    private String is_use;//状态 0：禁用 1：启用
    private Integer sort;//排序
    private Integer parent_id;//上级
    private String description;//描述
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
     * 子集
     */
    @Transient
    @TableField(exist = false)
    private List<KnowledgeCategory> children = new ArrayList<>();
    /**
     * 审批人
     */
    @Transient
    @TableField(exist = false)
    private List<String> approver_ids = new ArrayList<>();

    /**
     * 审批人集合
     */
    @Transient
    @TableField(exist = false)
    private List<SysUser> approverUsers = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<KnowledgeCategory> childern = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private String parent_name;
    @Transient
    @TableField(exist = false)
    private String approves;

    @Transient
    @TableField(exist = false)
    private Integer approve ;
    @Transient
    @TableField(exist = false)
    private String approveName;
}
