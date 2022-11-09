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
 * 软件许可证表(assets_software_licence)
 * @Author libin
 * @create 2022/3/7 15:08
 */
@Entity
@TableName("assets_software_licence")
@Table(name = "assets_software_licence")
@Data
public class AssetsSoftwareLicence extends Model<AssetsSoftwareLicence> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String os_licence_name; //名称
    private Integer deptId;        //部门
    private String description;     //描述
    private String use_limit;      //使用限制
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_time;        //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end_time;          //结束时间
    private String key_license;            //key
    private Integer is_limit; //1：有期限 0：无期限
    private String software; // 原系统是只有office 365和windows系统安装两个选项，但是没找到是在哪配置的  ??
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

    /**
     * 部门名称
     */
    @Transient
    @TableField(exist = false)
    private String deptName;
    @Transient
    @TableField(exist = false)
    private String starTime;
    @Transient
    @TableField(exist = false)
    private String endTime;

}
