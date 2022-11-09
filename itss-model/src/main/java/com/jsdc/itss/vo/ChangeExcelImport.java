package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: ChangeExcelImport
 * Description: 变更excel导入列
 * date: 2022/3/21 8:55
 *
 * @author bn
 */
@Data
public class ChangeExcelImport {

    // 编号
    private Integer id;

    //2	变更单号
    private String change_no;

    // 创建人登录名
    private String create_user_uname;

    // 创建人姓名
    private String create_user_name;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    //12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销
    private String status_name;

    //3	标题
    private String title;

    //4	计划开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date start_time;
    //5	计划完成时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end_time;

    // 类别名称
    private String classify_name;

    // 类型名称
    private String type_name;

    //7	变更来源 受变更来源影响
    private String change_source;

    //9	变更原因 longtext 富文本编辑
    private String change_reason;

    //10 当前环境 longtext 富文本编辑
    private String current_environment;

    // 变更方案
    private String cpd_change_programme;

    //回退计划
    private String cpd_fallback_plan;

    //风险评估
    private String cpd_risk_assessment;

    // 方案拟定人登录名
    private String cpd_programme_user_uname;

    // 方案拟定人姓名
    private String cpd_programme_user_name;

    // 关单人(用户名)
    private String cc_close_user_uname;

    // 关单人(姓名)
    private String cc_close_user_name;

    // 关单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cc_close_time;

    // 方案审核人(用户名)
    private String cpa_approve_user_uname;

    // 方案审核人(姓名)
    private String cpa_approve_user_name;

    // 方案审批意见
    private String cpa_approve_remark;

    // 方案审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cpa_approve_time;

    // 方案实施人(用户名)
    private String cim_implement_user_uname;

    // 实施备注
    private String cim_implement_remark;

    // 方案实施人(姓名)
    private String cim_implement_user_name;

    // 方案实施时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cim_start_time;

    // 实施后评审人(用户名)
    private String cia_after_implement_user_uname;

    // 实施后评审人(姓名)
    private String cia_after_implement_user_name;

    // 实施后评审意见
    private String cia_after_implement_remark;

    // 实施后评审时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cia_after_implement_time;

    // 审批信息
    private String str_change_nodes;





}
