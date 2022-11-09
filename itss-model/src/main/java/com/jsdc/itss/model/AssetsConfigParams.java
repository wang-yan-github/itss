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
import java.util.Date;

@Entity
@TableName("assets_config_params")
@Table(name = "assets_config_params")
@Data
public class AssetsConfigParams extends Model<AssetsConfigParams> implements Serializable {


    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 是否自动生成资产编号 1 是 2否
     */
    private String is_auto_code;
    /**
     * 资产编号方式 1 大流水 2 小流水
     */
    private  String is_code_mode;
    /**
     * 流水号位数
     */
    private Integer erial_number;
    /**
     * 时间格式
     */
    private String time_format;

    /**
     * 配置项资产编号格式
     */
    private String num_format;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private  String contant;
    /**
     * 微信消息
     */
    private String wechat_message;

    /**
     * 发件箱
     */
    private String send_email;
    /**
     * 打印机名称
     */
    private String print_name;
    /**
     * 纸张宽度
     */
    private Integer weight;
    /**
     * 纸张高度
     */
    private  Integer high;

    /**
     * 打印浓度
     */
    private String concentration;
    /**
     * 感应器
     */

    private  String sensor;
    /**
     * 距离感应器垂直间距高度
     */

    private Integer print_high ;
    /**
     * 距离感应器偏移距离
     */

    private Integer deviation;

    /**
     * 打印份数
     */

    private Integer print_num;
    /**
     * 打印内容
     */
    private String print_content;
    /**
     * 全局拓展表单Id
     */
    private  Integer form_id;


    /**
     * 全局拓展表单名字
     */
    @Transient
    @TableField(exist = false)
    private  String form_name ;

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
