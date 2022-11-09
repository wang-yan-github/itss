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
 * cl分组管理表(assets_cl_group)
 * @Author libin
 * @create 2022/3/7 16:12
 */
@Entity
@TableName("assets_cl_group")
@Table(name = "assets_cl_group")
@Data
public class AssetsClGroup extends Model<AssetsClGroup> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String group_name  ;// 中文名称
    private String en_name     ;// 英文名称
    private Integer is_enable   ;// 状态0:禁用1,启用
    private Integer sort         ;//排序



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
     * 类型集合
     */
    @Transient
    @TableField(exist = false)
    private List<AssetsType> assetsTypeList = new ArrayList<>();

    /**
     * 关键字
     */
    @Transient
    @TableField(exist = false)
    private String keyword;

    @Transient
    @TableField(exist = false)
    private Integer userId;
}
