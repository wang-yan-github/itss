package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: ChangeInfo
 * Description: 变更基本信息
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Entity
@TableName("change_info")
@Table(name = "change_info")
@Data
public class ChangeInfo extends Model<ChangeInfo> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //2	变更单号
    private String change_no;
    //3	标题
    private String title;
    //4	计划开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    //5	计划完成时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;
    //6	变更类别 审批人，方案审核人，实施后评审人受变更类别影响|受变更类别影响
    private String change_classify;
    //7	变更来源 受变更来源影响
    private String change_source;
    //8	变更类型 1.紧急变更 2.一般变更 3.重大变更|紧急变更影响时间轴，影响流程
    private String change_type;
    //9	变更原因 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String change_reason;
    //10 当前环境 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String current_environment;
    //11 附件
    private Integer enclosure_id;
    //12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销
    private String status;

    // 挂起前状态
    private String old_status;

    //14 备注 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String remark;
    // 当前审批人
    private Integer approve_user;
    // 当前审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approve_time;
    // 当前操作人
    private Integer operate_user;

    //下一位处理人
    private Integer handler_user;

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
    private Integer userId ;



}
