package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: ChangeNodes
 * Description: 变更节点
 * date: 2022/3/14 11:47
 *
 * @author bn
 */
@Entity
@TableName("change_nodes")
@Table(name = "change_nodes")
@DynamicInsert
@DynamicUpdate
@Data
public class ChangeNodes extends Model<ChangeNodes> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //变更id
    private Integer change_id;
    //一般、重大时间轴节点 1.开单 2.审批 3.制定方案 4.方案审核 5.实施 6.实施后评审 7.关单 出现多节点的情况下用sort排序
    //紧急 1.开单 2.审批 3.实施 4.实施后评审 5.关单
    private String time_nodes;
    // 排序
    private String sort;
    //时间状态 0:完成，1.未完成
    private String time_status;
    //审批人id
    private Integer approve_user;
    //审批状态 0.保存 1.审批通过 2.审批拒绝
    private String approve_status;
    //审批意见 富文本编辑
    @Column(columnDefinition = "longtext")
    private String approve_remark;
    //审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    //审批人
    @Transient
    @TableField(exist = false)
    private String approve_user_name;
}
