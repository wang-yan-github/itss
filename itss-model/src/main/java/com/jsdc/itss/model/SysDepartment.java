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
 * ClassName: SysRoleAuthority
 * Description: 部门表
 *
 * @author xuaolong
 */
@Entity
@TableName("sys_department")
@Table(name = "sys_department")
@Data
public class SysDepartment extends Model<SysDepartment> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名字
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 状态 0：禁用 1：启用
     */
    private Short is_use;
    /**
     * 上级ID
     */
    private Integer parent_id;
    /**
     * 快速保障支持群组ID
     */
    private Integer group_id;
    /**
     * 公司ID
     */
    private Integer company_id;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 唯一ID
     */
    private String unique_id;


    @Transient
    @TableField(exist = false)
    private List<SysDepartment> children = new ArrayList<>();

    /**
     * 上级名称
     */
    private String parent_name;


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
     * 公司名字
     */
    @Transient
    @TableField(exist = false)
    private String companyName;

    /**
     * 服务群组名字
     */
    @Transient
    @TableField(exist = false)
    private String groupName;


}
