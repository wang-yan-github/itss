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
import java.util.Date;

/**
 * @ClassName KnowledgeScoring 知识评分
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/8 9:33
 * @Version 1.0
 */
@Entity
@TableName("knowledge_scoring")
@Table(name = "knowledge_scoring")
@Data
public class KnowledgeScoring extends Model<KnowledgeScoring> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键
    private Integer knowledge_info_id;//知识ID
    private Integer rater;//评分人
    private Date scoring_time;//评分时间
    private Integer score;//贡献分
    private String comment;//评语

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
    private Integer userId;
}
