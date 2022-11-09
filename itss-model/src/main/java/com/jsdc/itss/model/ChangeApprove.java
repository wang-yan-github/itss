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
 * ClassName: ChangeApprove
 * Description: 变更审批信息
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_approve")
@Table(name = "change_approve")
@Data
public class ChangeApprove extends Model<ChangeApprove> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	变更id
    private Integer change_id;
    //3	审批人id 关联用户信息
    private Integer approve_user;
    //4	审批状态 1.审批通过 2.审批拒绝
    private String status;
    //5	审批意见 longtext 富文本编辑
    private String approve_remark;
    //6	审批时间 date
    private Date approve_time;

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
