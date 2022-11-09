package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SysUser
 * Description: 系统用户表
 * date: 2021/12/30 9:10
 *
 * @author wp
 */
@Entity
@TableName("sys_user")
@Table(name = "sys_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends Model<SysUser> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 密码
     */
    private String pass;

    /**
     * 姓名
     */
    private String name;


    /**
     * 部门ID
     */
    private Integer department_id;

    /**
     * 部门名字
     */
    @Transient
    @TableField(exist = false)
    private String departmentName;

    /**
     * 职位
     */
    private String position;

    /**
     * 位置
     */
    private String location;


    /**
     * 电话
     */
    private String telephone;


    /**
     * 座机
     */
    private String landline;


    /**
     * 邮箱
     */
    private String mailbox;


    /**
     * 是否是VIP
     * 0否（默认） 1是
     */
    private Short is_vip;

    /**
     * 状态
     * 1活动 2暂停 3已禁止
     */
    private Short status;


    /**
     * 是否接收邮箱通知
     * 0否 1是
     */
    private Short is_emial;


    /**
     * 是否接受微信通知
     * 0否 1是
     */
    private Short is_wechat;


    /**
     * 微信昵称
     */
    private String wechat_nickname;


    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date last_login_date;


    /**
     * 类型
     */
    private String user_type;

    /**
     * 是否技术用户 0 否 1 是
     */
    private Integer is_technology;

    /**
     * 微信openid
     */
    private String openid;
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

    //记录上一次选择的自助服务类别
    private Integer serviceTypeId;

    @Transient
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<Integer> ids;

    /**
     * 审批人排序
     */
    @Transient
    @TableField(exist = false)
    private Integer sort;

    /**
     * 非一线组
     */
    @Transient
    @TableField(exist = false)
    private String nonFirstLineGroup;

    /**
     * 负责人
     */
    @Transient
    @TableField(exist = false)
    private String principalName;

    /**
     * 工程师
     */
    @Transient
    @TableField(exist = false)
    private String engineerName;

    /**
     * 处理人为空自动指定工程师
     */
    @Transient
    @TableField(exist = false)
    private String auto_engineer;

    /**
     * 事件服务群组id
     */
    @Transient
    @TableField(exist = false)
    private Integer eventServiceGroupId;

    /**
     * 字典 类型1 开单、指派、转单  2处理  3  负责人
     */
    @Transient
    @TableField(exist = false)
    private String groupType;

}
