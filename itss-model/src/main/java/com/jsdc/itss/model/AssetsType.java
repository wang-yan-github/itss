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
 * ClassName: AssetsBrand
 * Description: 配置项类型管理表
 * date: 2022/3/7 14:11
 *
 * @author wh
 **/
@Entity
@TableName("assets_type")
@Table(name = "assets_type")
@Data
public class AssetsType extends Model<AssetsType> implements Serializable {
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
     * 状态 0 启用 1 禁用
     */
    private String is_enable;

    /**
     * 分组
     */
    private Integer group_id;
    @Transient
    @TableField(exist = false)
    private  String group_name;

    /**
     * 代码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加地址
     */
    private String add_address;

    /**
     * 修改地址
     */
    private String edit_address;

    /**
     * 列表地址
     */
    private String list_address;

    /**
     * 查看地址
     */
    private String check_address;

    /**
     * 快速报障支持群组
     */
    private Integer support_group_id;

    /**
     * 表单
     */
    private Integer form_id;

    /**
     * 图标
     */
    private Integer file_id;

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

    @Transient
    @TableField(exist = false)
    private List<AssetsChildren> assetsChildrens;

    /**
     * 自义字段属性字段
     */
    @Transient
    @TableField(exist = false)
    private List<AssetsExpandFormField> sysExtendFormSees;

    @Transient
    @TableField(exist = false)
    private List<Integer> ids;

    @Transient
    @TableField(exist = false)
    private Long num = (long)0;
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManageList = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private String formName;
    @Transient
    @TableField(exist = false)
    private String pic_path;

}
