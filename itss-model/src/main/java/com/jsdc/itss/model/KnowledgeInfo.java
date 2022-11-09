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
 * @ClassName KnowledgeInfo 知识主表
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/8 9:33
 * @Version 1.0
 */
@Entity
@TableName("knowledge_info")
@Table(name = "knowledge_info")
@Data
public class KnowledgeInfo extends Model<KnowledgeInfo> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键
    private Integer dept_id;//部门
    private Integer knowledge_category_id;//知识类别
    private String is_full_search;//可被全员搜索 1是  2否
    private String is_recommend;//推荐知识 0不推荐 1推荐
    private Integer sort_num;//排序号
    private Integer knowledge_range_id;//工程师内部分享范围
    private String title;//标题
    private String content;//正文
    private String keyword;//关键字
    private Integer knowledge_status; //知识状态 0禁用 1使用
    private String status;//审核状态 1：待审核 2：发布3：审核拒绝
    private Integer approver_id;//审核人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approval_time;//审核时间
    private String approval_opinions;//审核意见
    private Integer browse_num ; //浏览量

    /**
     * 部门名称
     */
    @Transient
    @TableField(exist = false)
    private String deptName;
    /**
     * 知识类别名称
     */
    @Transient
    @TableField(exist = false)
    private String knowledge_category_name;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  String startTime;

    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  String endTime;

    @Transient
    @TableField(exist = false)
    private Integer score;//贡献分
    @Transient
    @TableField(exist = false)
    private String comment;//评语

    @Transient
    @TableField(exist = false)
    private List<Integer> fileIds = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private String uploadedFiles;//上传的文件

    @Transient
    @TableField(exist = false)
    private Integer userId ;
}
