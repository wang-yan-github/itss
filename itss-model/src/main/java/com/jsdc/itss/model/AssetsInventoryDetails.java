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
 * 盘点资产表(assets_inventory_details)
 *
 * @Author libin
 * @create 2022/3/15 11:01
 */

@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@TableName("assets_inventory_details")
@Table(name = "assets_inventory_details")
@Data
public class
AssetsInventoryDetails extends Model<AssetsInventoryDetails> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer inventory_id;//盘点期id
    private Integer assets_id;//配置项id
    private Integer handle_status;//处理状态 0 未处理，1已处理
    private Integer status;//盘点状态 0未确认 1.确认无误 2.确认有误
    private String handle_result;//处理结果
    private String confirm_remark;//确认备注
    private Integer confirm_user;// 确认人
    private Integer handle_user;// 处理人

    /**
     * 确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirm_time;
    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date handle_time;


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
