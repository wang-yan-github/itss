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
 * @Author libin
 * @create 2022/3/16 11:33
 */
@Entity
@TableName("assets_manage_history")
@Table(name = "assets_manage_history")
@Data
public class AssetsManageHistory  extends Model<AssetsManageHistory> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer assets_id;
    private String option_type; //操作类型
    private int  option_userId;//操作人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date option_date;//操作时间
    private String assets_number;                         //     资产编号
    private Integer assets_type_id;                         //     配置项类型id
    private Integer child_asset;                            //     配置子类
    private String assets_name;                             //     资产名称
    private Integer dept_id;                                //     部门
    private Integer receiver;                                //     领用人
    private Integer purchase_company;                       //     购置公司
    private Integer is_secrecy;                             //     保密
    private Integer assets_status;                          //     资产状态
    private Integer it_status;                              //     IT状态

    @Transient
    private String user_name;
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
    private Integer pageNo=1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize=10;
}
