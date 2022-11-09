package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SysAnnouncement
 * Description: 公告管理
 *
 * @author zhangdequan
 * @date 2022/3/1013:37
 */
@Entity
@TableName("sys_announcement")
@Table(name = "sys_announcement")
@Data
public class SysAnnouncement{
    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态 （数据字典）当前公告状态 0停用 1使用
     */
    private String is_use;

    /**
     * 可见范围 0 公开  1 指定人员 角色 部门
     */
    private String scope;

    /**
     * 是否置顶 0 是 1 否
     */
    private String is_top;

    /**
     * 公告有效截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effective_date;

    /**
     * 列表展示创建人
     */
    private String create_user_name;

    /**
     * 列表展示创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 可见部门ID
     */
    private Integer visual_department;

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
     * 公告管理关联表
     */
    @Transient
    @TableField(exist = false)
    private List<SysAnnouncementSee> announcementSees = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<SysUser> sysUserList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<SysRole> sysRoleList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<SysDepartment> sysDepartmentList = new ArrayList<>();
}
