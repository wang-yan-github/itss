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
 * ClassName: SysAuthority
 * Description: 权限表
 * @author xuaolong
 */
@Entity
@TableName("sys_authority")
@Table(name = "sys_authority")
@Data
public class SysAuthority extends Model<SysAuthority> implements Serializable {

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
    private String name ;

    /**
     * 权限编码
     */
    private String code;
    /**
     * 上级ID
     */
    private Integer parent_id;
    /**
     * 所属等级 1:顶级菜单 如：事件 2：2级菜单 如：工作台 3：页面按钮
     */
    private Short level;


    /**
     * 功能路径
     */
    private String path;

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


    @Transient
    @TableField(exist = false)
    private List<SysAuthority> children =new ArrayList<>();
}
