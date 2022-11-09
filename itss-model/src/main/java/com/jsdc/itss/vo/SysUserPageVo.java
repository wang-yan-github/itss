package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName SysUserPageVo
 * @Description 用户管理分页返回参数
 * @Author xuaolong
 * @Date 2021/12/30 17:21
 * @Version 1.0
 */
@Data
public class SysUserPageVo {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门ID
     */
    private Integer department_id;


    /**
     * 部门
     */
    private String departmentName;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String mailbox;

    /**
     * 微信昵称
     */
    private String wechat_nickname;

    /**
     * 类型
     */
    private String user_type;

    /**
     * 状态
     */
    private String status;

    /**
     * 最后登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date last_login_date;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    private String companyName;

    private String address;

    private String sort;

    /**
     * 职位
     */
    private String position;
    /**
     * 位置
     */
    private String location;

    /**
     * 座机
     */
    private String landline;


}
