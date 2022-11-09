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
import java.util.Date;

/**
 * ClassName: ChangeCommonlyApprove
 * Description: 一般变更审批表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_commonly_approve")
@Table(name = "change_commonly_approve")
@Data
public class ChangeCommonlyApprove extends Model<ChangeCommonlyApprove> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	变更类别id 关联变更类别表
    private Integer change_type_id;
    //3	审批人 关联用户信息
    private Integer approve_user;
    //4	排序
    private Integer sort;

    @Transient
    @TableField(exist = false)
    private String approve_name;


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
