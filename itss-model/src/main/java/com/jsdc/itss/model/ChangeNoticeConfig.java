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
 * ClassName: ChangeNoticeConfig
 * Description: 变更通知配置表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_notice_config")
@Table(name = "change_notice_config")
@Data
public class ChangeNoticeConfig extends Model<ChangeNoticeConfig> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	标题
    private String title;
    //3	内容
    private String content;
    //4	额外抄送 抄送仅限于发送邮件时，多个请用半角分号分隔
    private String extra_cc;
    //5	微信消息
    private String wechat_message;
    //6	通知方式 1手动 2自动 3不发送
    private String notification_method;
    //7	【字典】1待审批通知 2审批拒绝通知 3制定方案通知 4方案待审核通知 5方案审核拒绝通知 6待实施通知 7待评审通知 8实施失败通知 9关闭通知 10撤销
    private String type;

    private String title_reassignment;
    private String content_reassignment;
    private String extra_cc_reassignment;
    private String wechat_message_reassignment;
    private String notification_method_reassignment;
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
    private List<ChangeNoticeConfig>  list =new ArrayList<>();

}
