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
 * ClassName: ChangeImplementApprove
 * Description: 变更实施后评审
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_implement_approve")
@Table(name = "change_implement_approve")
@Data
public class ChangeImplementApprove extends Model<ChangeImplementApprove> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	变更id 关联变更id
    private Integer change_id;
    //3	变更实施id 关联变更实施信息
    private Integer implement_id;
    //4	实施后评审人 关联用户信息
    private Integer after_implement_user;
    //5	实施后评审时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date after_implement_time;
    //6	实施后评审意见 blob 富文本编辑
    @Column(columnDefinition = "longtext")
    private String after_implement_remark;
    //7	实施后续流程 下拉框 配置请求
    private String implement_process;

    //7	实施状态 1.结束
    private String status;


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
