package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ExportSysUserVo {
    /**
     * 用户名
     */
    private String user_name;

    /**
     * 状态
     * 1活动 2暂停 3已禁止
     */
    private Short status;

    private String status_name;

    /**
     *类型
     */
    private String user_type;

    /**
     *邮箱
     */
    private  String mailbox;

    /**
     * 电话
     */
    private  String telephone;

    /**
     * 邮件签名
     */
    private  String email_token;
    /**
     * manageKey
     */
    private String manageKey;



    /**
     * 姓名
     */
    private String name;

    /**
     *座机
     */
    private  String landline;

    /**
     *是否是VIP
     * 0否（默认） 1是
     */
    private  Short is_vip;
    private  String is_vip_name;


    /**
     * 职位
     */
    private  String position;


    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date last_login_date;

    private String department_name;

    /**
     * 服务群组
     */
    private  String event_group_name;


    /**
     *角色
     */
    private  String role_name;


    /**
     * 密码
     */
    private String pass;


    /**
     * 是否接收邮箱通知
     * 0否 1是
     */
    private Short is_emial;

    private String is_emial_name;


    /**
     * 是否接受微信通知
     * 0否 1是
     */
    private Short is_wechat;
    private String is_wechat_name;

    /**
     * 位置
     */
    private  String location;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

}
