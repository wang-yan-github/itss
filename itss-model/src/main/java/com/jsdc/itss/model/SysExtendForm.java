package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * ClassName: SysExtendForm
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1014:22
 */
@Entity
@TableName("sys_extend_form")
@Table(name = "sys_extend_form")
@Data
public class SysExtendForm{
    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 扩展表单名称
     */
    private String form_name;

    /**
     * 父级表单
     */
    private String parent_form;

    /**
     * （数据字典）状态 0停用 1使用
     */
    private Integer is_use;

    /**
     * （数据字典）类型 0工单 1问题 2变更 3发布 4配置项
     */
    private Integer extend_type;

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
