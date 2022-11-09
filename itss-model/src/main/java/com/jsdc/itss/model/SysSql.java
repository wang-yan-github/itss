package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SysSql
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1014:07
 */
@Entity
@TableName("sys_sql")
@Table(name = "sys_sql")
@Data
public class SysSql {

    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * sql报表名称
     */
    private String sql_name;

    /**
     * sql内容
     */
    private String sql_content;

    /**
     * （数据字典）状态 0停用 1使用
     */
    private String is_use;

    /**
     * 显示方式
     */
    private String display_mode;

    /**
     * （数据字典）默认时间类型 0日报 1周报 2月报 3季报 4年报
     */
    private String time_choose;

    /**
     * （数据字典）可见范围 0 公开  1 指定人员 角色 部门
     */
    private String scope;

    /**
     * 对应的模块
     */
    private String module;

    /**
     * （数据字典）是否分组 0否 1是
     */
    private String is_group;

    /**
     * 支持的变量
     */
    private String support_var;

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
    private List<Integer> users = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<Integer> roles = new ArrayList<>();

    //关联用户
    @Transient
    @TableField(exist = false)
    private List<SysUser> sysUserList = new ArrayList<>();
    //关联角色
    @Transient
    @TableField(exist = false)
    private List<SysRole> sysRoleList = new ArrayList<>();
    //分页
    @Transient
    @TableField(exist = false)
    private Integer pageNo;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;
    //开始时间
    @Transient
    @TableField(exist = false)
    private String timeStart;
    //结束时间
    @Transient
    @TableField(exist = false)
    private String timeEnd;
    //状态名称
    @Transient
    @TableField(exist = false)
    private String is_use_name;


    public SysSql() {
    }

    public SysSql(String sql_content) {
        this.sql_content = sql_content;
    }
}
