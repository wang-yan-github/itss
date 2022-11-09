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
 * ClassName: ChangeType
 * Description: 变更类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_type")
@Table(name = "change_type")
@Data
public class ChangeType extends Model<ChangeType> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	名称
    private String type_name;
    //3	状态 0：禁用 1：启用
    private String is_use;
    //4	排序
    private Integer sort;
    //5	上级
    private Integer parent_id;
    //6	一般变更方案审核人 关联用户信息
    private Integer commonly_audit_user;
    //7	一般变更实施后评审人 关联用户信息
    private Integer commonly_implement_user;
    //8	紧急变更实施后评审人 关联用户信息
    private Integer urgent_implement_user;
    //9	重大变更方案审核人 关联用户信息
    private Integer major_audit_user;
    //10	重大变更实施后评审人 关联用户信息
    private Integer major_implement_user;
    @Transient
    @TableField(exist = false)
    private String parent_name;
    @Transient
    @TableField(exist = false)
    //一般变更方案审核人名字
    private String commonly_audit_user_name;
    @Transient
    @TableField(exist = false)
    //7	一般变更实施后评审人名字
    private String commonly_implement_user_name;
    @Transient
    @TableField(exist = false)
    //8	紧急变更实施后评审名字
    private String urgent_implement_user_name;
    @Transient
    @TableField(exist = false)
    //9	重大变更方案审核人 关联用户信息
    private String major_audit_user_name;
    @Transient
    @TableField(exist = false)
    //10	重大变更实施后评审人 关联用户信息
    private String major_implement_user_name;

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

    //一般变更
    @Transient
    @TableField(exist = false)
    private String commonly_name;
    //紧急变更
    @Transient
    @TableField(exist = false)
    private String urgent_name;
    //重大变更
    @Transient
    @TableField(exist = false)
    private String major_name;
    @Transient
    @TableField(exist = false)
    private  String is_use_name;

    //子集
    @Transient
    @TableField(exist = false)
    private List<ChangeType> children = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    //一般变更审批人
    private List<Integer> changeCommonlyApproveList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    //一般变更审批人
    private List<SysUser> changeCommonlyApproveList1 = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    //紧急变更审批人
    private List<Integer> changeUrgentApproveList = new ArrayList<>();


    @Transient
    @TableField(exist = false)
    //紧急变更审批人
    private List<SysUser> changeUrgentApproveList1 = new ArrayList<>();


    @Transient
    @TableField(exist = false)
    //重大变更审批人
    private List<Integer> changeMajorApproveList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    //重大变更审批人
    private List<SysUser> changeMajorApproveList1 = new ArrayList<>();



}
