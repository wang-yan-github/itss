package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * ClassName: SysPrint
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1014:01
 */
@Entity
@TableName("sys_print")
@Table(name = "sys_print")
@Data
public class SysPrint{

    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模板名称
     */
    private String print_name;

    /**
     * （数据字典）状态 0停用 1使用
     */
    private String is_use;

    /**
     * 打印模板对应的模块
     */
    private String module;

    /**
     * 排序
     */
    private Integer order_num;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 列表展示创建人
     */
    @Transient
    @TableField(exist = false)
    private String create_user_name;

    /**
     * 列表展示创建时间
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
