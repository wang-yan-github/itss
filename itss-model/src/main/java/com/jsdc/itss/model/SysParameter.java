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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>添加描述信息<p>
 *
 * @author 徐傲龙
 * @since 2022年04月13日 14:29
 */
@Entity
@TableName("sys_parameter")
@Table(name = "sys_parameter")
@Data
public class SysParameter extends Model<SysParameter> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统名称
     */
    private String prefs_name;

    /**
     * 上传图片服务器之后主页LOGO的ID
     */
    private Integer principal_sheet_pic;

    /**
     * 上传图片服务器之后登陆页LOGO的ID
     */
    private Integer land_logo_pic;

    /**
     * 上传图片服务器之后Favicon的ID
     */
    private Integer favicon_pic;

    /**
     * 上传图片服务器之后登陆页二维码的ID
     */
    private Integer land_code_pic;

    /**
     * 登陆页二维码说明
     */
    private String code_explain;

    /**
     * 登陆页二维码说明(英文)
     */
    private String code_explain_en;

    /**
     * 系统根目录
     */
    private String directory_root;

    /**
     * （数据字典）邮箱开关（0停用 1使用）
     */
    private String is_use;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 测试邮箱 ;分隔
     */
    private String email_test;

    /**
     * 系统配置（M）
     */
    private Integer system_config;

    /**
     * 工单（M）
     */
    private Integer work_order;

    /**
     * 问题（M）
     */
    private Integer question;

    /**
     * 变更（M）
     */
    private Integer alteration;

    /**
     * 发布（M）
     */
    private Integer publish;

    /**
     * 配置-附件（M）
     */
    private Integer annex;

    /**
     * 配置-图片（M）
     */
    private Integer pic;

    /**
     * 知识库（M）
     */
    private Integer knowledge_base;

    /**
     * 帮助（M）
     */
    private Integer help;

    /**
     * 邮件（M）
     */
    private Integer email_size;

    /**
     * 即时通信（M）
     */
    private Integer instant_messaging;

    /**
     * 文件夹知识库
     */
    private String folder_knowledge_base;

    /**
     * 文件夹工单
     */
    private String folder_work_order;

    /**
     * 文件夹文档
     */
    private String folder_document;

    /**
     * 文件夹配置
     */
    private String folder_setting;

    /**
     * 文件夹问题
     */
    private String folder_question;

    /**
     * 文件夹变更
     */
    private String folder_change;

    /**
     * 文件夹即时通信
     */
    private String folder_instant_messaging;


    /**
     * 软件许可证到期前提醒天数
     */
    private Integer software_license;

    /**
     * 客户合同到期提醒天数
     */
    private Integer customer_contract;

    /**
     * 供应商合同到期提醒天数
     */
    private Integer supplier_contract;

    /**
     * （数据字典）自动创建用户(0 否  1 是）
     */
    private String is_auto_user;

    /**
     * 默认服务群组(ID)
     */
    private Integer default_group;

    /**
     * 开单接口地址
     */
    private String billing_interface_address;

    /**
     * 参数列表,json数据
     */
    private String parameter_tables;

    /**
     * 返回值 json数据
     */
    private String result;

    /**
     * 自动跳转-说明
     */
    private String illustrate;

    /**
     * 地图默认显示级别
     */
    private String map_show;

    /**
     * （数据字典）是否开启（0停用 1使用）
     */
    private String support_is_use;

    /**
     * 聊天链接
     */
    private String chat_link;

    /**
     * 文件ID
     */
    private Integer server;

    /**
     * 文件ID
     */
    private Integer client;

    /**
     * （数据字典）是否开启（0停用 1使用）
     */
    private String strip_is_use;

    /**
     * Proxy url
     */
    private String proxy_url;

    /**
     * 验证码策略
     */
    private String strategy;

    /**
     * （数据字典）是否开启（0停用 1使用）
     */
    private String message_is_use;

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
    private List<FileManage> fileManageList1 = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManageList2 = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManageList3 = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManageList4 = new ArrayList<>();
}
