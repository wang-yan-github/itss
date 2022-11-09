package com.jsdc.itss.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionVo
 * Description:
 *
 * @author zhangdequan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVo implements Serializable {

    private Integer id;

    //来源id 来源通过设置-问题配置中的问题来源管理中获取
    private Integer source_id;
    //3	优先级	设置-服务级别协议中的问题优先级管理中获取
    private Integer priority_id;
    //4	问题类别id	问题类别通过设置-问题配置中的问题类别管理中获取
    private Integer category_id;
    //5	标题
    private String title;
    //6	描述
    private String description;
    //7	期望解决时间 yyyy-MM-dd
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String hope_solve_time;
    //8	问题鉴定通知 0否 1是
    private String is_notice;
    //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
    private String status;
    //10 备注
    private String remark;
    //11 问题单号
    private String code;
    //14 鉴定人 用户表id
    private Integer appraisal_user;
    //15 鉴定时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appraisal_time;
    //16 鉴定意见
    private String appraisal_opinion;
    //17 处理人
    private Integer handle_user;
    //18 解决人 用户表id
    private Integer solve_user;
    //19 解决时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date solve_time;
    //20 解决方案
    private String solve_content;
    //21 审核人 用户表id
    private Integer audit_user;
    //22 审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date audit_time;
    //23 审核内容
    private String audit_content;
    //24 关单人 用户表id
    private Integer clearance_user;
    //25 关单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date clearance_time;
    //26 sla:(小时)
    private Integer sla_time;
    //27 关单时限
    private String time_limit;
    //28 解决分钟数
    private String resolution_minutes;
    //29 当前操作人 用户表id
    private Integer now_operator_user;

    //挂起原因
    private String suspend_reason;
    //转办备注
    private String transfer_remark;

    //是否生成了知识
    private Integer is_knowledge;

    /**
     * 审核人名称
     */
    private String audit_user_name;
    /**
     * 来源名称
     */
    private String source_name;
    /**
     * 关单人名称
     */
    private String clearance_user_name;
    /**
     * 解决人名称
     */
    private String solve_user_name;
    /**
     * 鉴定人名称
     */
    private String appraisal_user_name;
    /**
     * 处理人名称
     */
    private String handle_user_name;
    /**
     * 状态名称
     */
    private String status_name;
    /**
     * 查询开单的开始结束事件
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String create_start_time;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String create_end_time;
    /**
     * 查询的开始结束事件
     */
    private String clearance_start_time;
    private String clearance_end_time;

    /**
     * 创建人名称
     */
    private String create_user_name;
    /**
     * 类别名称
     */
    private String category_name;
    /**
     * 优先级名称
     */
    private String priority_name;
    /**
     * 当前操作人名称
     */
    private String now_operator_user_name;
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

    /**
     * 基础实体类,添加修改操作
     */
    private Question question;
    /**
     * 问题关联的工单
     */
    private List<QuestionWorkorder> workorders = new ArrayList<>();
    private List<Integer> questionWorkorderIds = new ArrayList<>();
    /**
     * 问题关联的变更
     */
    private List<QuestionChange> questionChanges = new ArrayList<>();
    private List<Integer> questionChangeIds = new ArrayList<>();
    /**
     * 问题关联的日志
     */
    private List<SysLog> logs = new ArrayList<>();
    /**
     * 工单事件列表
     */
    private List<Event> events = new ArrayList<>();
    /**
     * 绑定关联的变更表
     */
    private List<ChangeVo> changeInfos = new ArrayList<>();
    /**
     * 关联审核人
     */
    private List<QuestionReviewer> questionReviewers = new ArrayList<>();
    /**
     * 关联审核人数量
     */
    private Integer questionReviewerCount;
    /**
     * 审核状态 1同意 2拒绝
     */
    private String auditStatus;
    /**
     * 列表状态
     */
    private String dataStatus;
    //关键字查询
    private String keyword;

    //属性信息 附件
    private List<FileManage> fileManageInfoList = new ArrayList<>();
    //处理信息信息 附件
    private List<FileManage> fileManageHandleList = new ArrayList<>();
    //属性信息 附件
    private String uploadedFiles;
    //处理信息信息 附件
    private String uploadedHandleFiles;

    //问题工单ids集合
    private List<Integer> ids = new ArrayList<>();
    //当前登录id
    private Integer current_user;

    private Integer pageNo;
    private Integer pageSize;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_time_start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time_end;

    private Integer userId;


    private String sla_surplus_time;
    //SLA超时时间
    private Integer sla_exceed_time;
    private String isFitSla;
}
