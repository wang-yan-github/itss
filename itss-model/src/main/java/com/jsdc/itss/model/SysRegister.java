package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * ClassName: SysRegister
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1015:03
 */
@Entity
@TableName("sys_register")
@Table(name = "sys_register")
@Data
public class SysRegister{
    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * （数据字典）注册状态 0未注册 1已注册
     */
    private Integer is_register;

    /**
     * 系统版本
     */
    private String edition;

    /**
     * 注册码
     */
    private String serial;

    /**
     * 机器码
     */
    private String machine_code;

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
