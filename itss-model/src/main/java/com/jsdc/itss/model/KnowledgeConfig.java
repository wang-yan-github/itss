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
 * @ClassName KnowledgeConfig 知识配置
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/8 9:33
 * @Version 1.0
 */
@Entity
@TableName("knowledge_config")
@Table(name = "knowledge_config")
@Data
public class KnowledgeConfig extends Model<KnowledgeConfig> implements Serializable {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键
    private Integer title_weight;//标题权重
    private Integer desc_weight;//描述权重
    private Integer keyword_weight;//关键字权重

    public KnowledgeConfig(Integer title_weight, Integer desc_weight, Integer keyword_weight) {
        this.title_weight = title_weight;
        this.desc_weight = desc_weight;
        this.keyword_weight = keyword_weight;
    }

    public KnowledgeConfig() {
    }

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
