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
 * ClassName: ChangeProgrammeApprove
 * Description:
 * date: 2022/3/17 8:42
 *
 * @author bn
 */
@Entity
@TableName("change_programme_approve")
@Table(name = "change_programme_approve")
@Data
public class ChangeProgrammeApprove extends Model<ChangeProgrammeApprove> implements Serializable {


    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //变更id
    private Integer change_id;
    //变更方案id
    private Integer change_programme_id;
    //审批意见
    @Column(columnDefinition = "longtext")
    private String approve_remark;
    //审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date approve_time;
    //审批人id
    private Integer approve_user;
    //审批状态  1.审批通过 2.审批拒绝
    private String status;

    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
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
