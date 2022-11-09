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
 * ClassName: AssetsBrand
 * Description: 配置项状态表
 * date: 2022/3/8 14:11
 *
 * @author wh
 **/
@Entity
@TableName("assets_config_status")
@Table(name = "assets_config_status")
@Data
public class AssetsConfigStatus extends Model<AssetsConfigStatus> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 配置项id
     */
    private Integer assets_id;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态 0 启用 1 禁用
     */
    private String is_enable;

    /**
     * 描述
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

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
