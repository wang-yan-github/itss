package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.ChangeInfo;
import com.jsdc.itss.model.ChangeNodes;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ChangeExcel
 * Description: 变更excel导出列
 * date: 2022/3/18 15:08
 *
 * @author bn
 */
@Data
public class ChangeExcelReport extends ChangeInfo {

    // 状态名
    private String status_name;
    // 创建人登录名
    private String create_user_uname;

    // 创建人姓名
    private String create_user_name;

    // 类别名称
    private String classify_name;

    // 类型名称
    private String type_name;

    // 变更方案
    private String cpd_change_programme;

    //回退计划
    private String cpd_fallback_plan;

    //风险评估
    private String cpd_risk_assessment;

    //方案拟定人
    private Integer cpd_programme_user;

    // 方案拟定人登录名
    private String cpd_programme_user_uname;

    // 方案拟定人姓名
    private String cpd_programme_user_name;

    // 关单人
    private Integer cc_close_user;

    // 关单人(用户名)
    private String cc_close_user_uname;

    // 关单人(姓名)
    private String cc_close_user_name;

    // 关单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cc_close_time;

    // 方案审核人
    private Integer cpa_approve_user;

    // 方案审核人(用户名)
    private String cpa_approve_user_uname;

    // 方案审核人(姓名)
    private String cpa_approve_user_name;

    // 方案审批意见
    private String cpa_approve_remark;

    // 方案审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cpa_approve_time;

    // 方案实施人
    private Integer cim_implement_user;

    // 方案实施人(用户名)
    private String cim_implement_user_uname;

    // 实施备注
    private String cim_implement_remark;

    // 方案实施人(姓名)
    private String cim_implement_user_name;

    // 方案实施时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cim_start_time;

    // 实施后评审人
    private Integer cia_after_implement_user;

    // 实施后评审人(用户名)
    private String cia_after_implement_user_uname;

    // 实施后评审人(姓名)
    private String cia_after_implement_user_name;

    // 实施后评审意见
    private String cia_after_implement_remark;

    // 实施后评审时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String cia_after_implement_time;

    // 审批信息
    private List<ChangeNodes> changeNodes;
    // 审批信息
    private String str_change_nodes;







}
