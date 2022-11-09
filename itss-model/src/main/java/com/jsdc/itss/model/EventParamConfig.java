package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 事件参数管理表
 *
 * @author wp
 */
@Entity
@TableName("event_param_config")
@Table(name = "event_param_config")
@Data
public class EventParamConfig extends Model<EventParamConfig> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	是否启用“首问责任制	is_first_question	varchar(2)		是否启用“首问责任制”0否  1是
    private String is_first_question;
    //3	工单超时自动升级	is_auto_update	varchar(2)		工单超时自动升级0否  1是
    private String is_auto_update;
    //4	开单时可选择所有服务群组	is_billing	varchar(2)		开单时可选择所有服务群组0否  1是
    private String is_billing;
    //5	指派时可选择所有服务群组	is_designate	varchar(2)		指派时可选择所有服务群组0否  1是
    private String is_designate;
    //6	转单时可转给所有服务群组	is_transfer_order	varchar(2)		转单时可转给所有服务群组0否  1是
    private String is_transfer_order;
    //7	统计前N位开单人	num	int		统计前N位开单人/解决人/关单人的工单数
    private Integer num;
    //8	是否自动恢复事件单	is_working_day_zthf	varchar(2)		根据工作日和上下班时间自动暂停并恢复事件单0否  1是
    private String is_working_day_zthf;
    //9	手动暂停的事件单自动恢复	is_pause_manually	varchar(2)		手动暂停的事件单自动恢复0否  1是
    private String is_pause_manually;
    //10 满意度评价时效性	is_aging_switch	varchar(2)		满意度评价时效性0否1是
    private String is_aging_switch;
    //11 评价超时天数	timeout_days	int		评价超时天数
    private Integer timeout_days;
    //12 超时默认评价	default_rating		超时默认评价
    private String default_rating;
    //13 自动评价为	automatic_evaluation	varchar(2)
    private String automatic_evaluation;
    //14 满意度评价强提醒	is_strong_reminder	varchar(2)		满意度评价强提醒0否1是
    private String is_strong_reminder;

    // 记住报障类别 0否  1是
    private String is_category;
    // 报障页显示配置项选择 0否  1是
    private String is_show_config;
    // 扫码报障标题模板
    private String title_template;
    // 扫码报障描述模板
    private String describe_template;

    //15 是否启用“用户确认”	is_user_confirm	varchar(2)		启用“用户确认”0否1是
    private String is_user_confirm;
    //16 用户确认超时时间(天):	user_time_out	int		用户确认超时时间(天):
    private Integer user_time_out;
    //17 启用“重开时效性”	is_reopen	varchar(2)		启用“重开时效性”0否1是
    private String is_reopen;
    //18 重开超时时间	reopen_day	int		重开超时时间(天):
    private Integer reopen_day;


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
