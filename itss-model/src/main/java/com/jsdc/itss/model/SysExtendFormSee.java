package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * ClassName: SysExtendFormSee
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1014:33
 */
@Entity
@TableName("sys_extend_form_see")
@Table(name = "sys_extend_form_see")
@Data
public class SysExtendFormSee{
    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 扩展表单管理id主键
     */
    private Integer extend_id;

    /**
     * 字段名称
     */
    private String field_name;

    /**
     * （数据字典）类型0文本1长文本 2整数 3小数 4日期 5单选 6复选
     */
    private Integer field_type;

    /**
     * （数据字典）是否必填 0否 1是
     */
    private Integer is_required;

    /**
     * （数据字典）是否启用查询 0否 1是
     */
    private Integer is_query;

    /**
     * （数据字典）是否整行显示 0否 1是
     */
    private Integer is_show_line;

    /**
     * （数据字典）状态 0停用 1使用
     */
    private Integer is_use;

    /**
     * 序号
     */
    private Integer order_num;

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
