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
 * 拓展表单字段表(assets_expand_form_field)
 * @Author libin
 * @create 2022/3/15 10:33
 */
@Entity
@TableName("assets_expand_form_field")
@Table(name = "assets_expand_form_field")
@Data
public class AssetsExpandFormField extends Model<AssetsExpandFormField> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;//名称

    //字段名
    private String en_name;
    private Integer type;//类型  0文本、1长文本、2整数、3小数、4日期、5单选、6复选
    private Integer is_required;//   必填 0是 1否
    private Integer is_search;//启用查询 0是 1否
    private Integer is_whole_line;// 整行显示 0是 1否
    private Integer serial;// 序号
    private Integer is_enable;//状态 0使用 1禁用
    private Integer form_id;//所属表单
    //当type为5单选、6复选此值为必填
    private String value;//以 @,@ 作为分隔符 分隔 当type为5单选、6复选

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
