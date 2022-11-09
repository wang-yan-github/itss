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
 * ClassName: QuestionNoticeConfig
 * Description: 问题通知配置表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("question_notice_config")
@Table(name = "question_notice_config")
@Data
public class QuestionNoticeConfig extends Model<QuestionNoticeConfig> implements Serializable {
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
    //7	类型 【字典】1待鉴定 2鉴定拒绝 3待处理 4待审核 5审核拒绝 6审核通过 7超时 8撤销
    private String type;

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
    private List<QuestionNoticeConfig> list =new ArrayList<>();


}
