package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.Page;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: AssetsCompany
 * Description: 购置公司表
 * date: 2022/3/7 14:11
 *
 * @author wh
 **/
@Entity
@TableName("assets_company")
@Table(name = "assets_company")
@Data
public class AssetsCompany extends Model<AssetsCompany> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String company_name;

    /**
     * 状态 0：禁用 1：启用
     */
    private Integer is_enable;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 代码
     */
    private String code;

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
     * 分页标识
     */
    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;

}
