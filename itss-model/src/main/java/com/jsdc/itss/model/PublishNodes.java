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
 * ClassName: PublishNodes
 * Description: 发布时间信息表
 * date: 2022/3/14 11:47
 *
 * @author wh
 */
@Entity
@TableName("publish_nodes")
@Table(name = "publish_nodes")
@DynamicInsert
@DynamicUpdate
@Data
public class PublishNodes extends Model<PublishNodes> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //发布id
    private Integer publish_id;
    //时间轴节点 1.开单 2.审批 3.测试 4.发布
    private Integer time_nodes;
    // 排序
    private Integer sort;
    //节点状态 0:完成，1.未完成
    private Integer time_status;
    //节点结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end_time;

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
    private Integer userId;

}
