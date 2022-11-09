package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.vo.SysDepartmentVo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拓展表单表(assets_expand_form)
 *
 * @Author libin
 * @create 2022/3/15 10:30
 */
@Entity
@TableName("assets_expand_form")
@Table(name = "assets_expand_form")
@Data
public class AssetsExpandForm extends Model<AssetsExpandForm> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;//名称
    private Integer parent_form_id;//父级表单
     //（数据字典）类型 0工单 1问题 2变更 3发布 4配置项
    private Integer type;//类型
    /**
     * （数据字典）状态 0停用 1使用
     */
    private Integer is_use;


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
    private List<AssetsExpandForm> childList = new ArrayList<>();


    @Transient
    @TableField(exist = false)
    private List<AssetsExpandForm> children =new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private String parent_form_name;
}
