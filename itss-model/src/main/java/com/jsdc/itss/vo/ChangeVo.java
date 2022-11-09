package com.jsdc.itss.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ChangeVo
 * Description: 我的变更集合
 * date: 2022/3/14 10:42
 *
 * @author bn
 */
@Data
public class ChangeVo {

    /**
     * 编号
     */
    private Integer id;
    //2	变更单号
    private String change_no;
    //3	标题
    private String title;
    //3	关键字
    private String keyword;
    //4	计划开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_time;
    //5	计划完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_time;

    //6	变更类别 审批人，方案审核人，实施后评审人受变更类别影响|受变更类别影响
    private String change_classify;
    //7	变更来源 受变更来源影响
    private String change_source;
    // 变更来源名
    private String source_name;
    //8	变更类型 1.紧急变更 2.一般变更 3.重大变更|紧急变更影响时间轴，影响流程
    private String change_type;
    //9	变更原因 longtext 富文本编辑
    private String change_reason;
    //10 当前环境 longtext 富文本编辑
    private String current_environment;
    //11 附件
    private Integer enclosure_id;
    //12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销
    private String status;
    //14 备注 longtext 富文本编辑
    private String remark;
    // 当前审批人 多审批人的情况下 展示sort小的待审批的，审批流程完结的展示sort最大的已审批
    private Integer approve_user;

    //下一位处理人
    private Integer handler_user;
    //下一位处理人
    private String handler_user_name;


    //审批时间 多审批的情况下 展示最新的 没有为空
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approve_time;

    // 当前操作人
    private Integer operate_user;
    // 挂起前状态
    private String old_status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    /**
     * 更新人id
     */
    private Integer update_user;
    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;

    // 当前审批人名称
    private String approve_name;
    // 操作人名称
    private String operate_name;
    // 创建人用户名
    private String create_user_uname;
    // 创建人姓名
    private String create_user_name;
    // 变更类型名称
    private String type_name;
    // 类别名称
    private String classify_name;
    //方案审核人名称
    private String cpa_approve_name;
    // 审批人
    private String cn_approve_name;
    // 状态名
    private String status_name;
    // 关单人
    private String close_user_name;

    // 方案拟定人
    private String programme_user_name;

    // 方案实施人名
    private String implement_user_name;

    // 审批人
    private Integer cn_approve_user;

    // 方案审核人
    private Integer cpa_approve_user;

    // 我参与的 用户id
    private Integer canyu_user_id;

    // 创建开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_start_time;
    // 创建结束时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_end_time;

//    // 计划开始时间开始
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date start_start_time;
//
//    // 计划开始时间结束
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date start_end_time;

    //4	实施开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date implement_start_time;

    //5	实施结束时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date implement_end_time;

    private String str_change_nodes;

    // 1.我的变更列表分页 2.变更管理列表分页 3我参与的
    private String data_flag;

    // 转办人
    private Integer trans_form_user;


    //属性信息 附件
    private List<FileManage> fileManageInfoList = new ArrayList<>();

    //属性信息 附件
    private String uploadedFiles;

    // 时间节点 已完成最高的节点状态 针对操作项
    private String time_nodes;

    // 变更信息
    private ChangeInfo changeInfo;

    // 方案拟定
    private ChangeProgrammeDraft changeProgrammeDraft;

    // 方案审核
    private ChangeProgrammeApprove changeProgrammeApprove;

    // 变更实施信息
    private ChangeImplement changeImplement;

    // 变更实施后评审
    private ChangeImplementApprove changeImplementApprove;

    // 变更关单
    private ChangeClose changeClose;

    // 方案节点
    private List<ChangeNodes> changeNodes;

    // 单节点
    private ChangeNodes changeNode;

    // 关联的事件
    private List<ChangeEvent> changeEvents;

    //事件信息
    private List<Event> events = new ArrayList<>();

    // 关联的问题
    private List<ChangeQuestion> changeQuestions;

    //问题信息
    private List<Question> questions = new ArrayList<>();
    //问题信息
    private List<QuestionVo> questionVos = new ArrayList<>();


    // 关联的配置
    private List<ChangeAssets> changeAssets;

    //问题信息
    private List<AssetsManage> assetsManages = new ArrayList<>();

    private List<Integer> ids = new ArrayList<>();

    // 日志
    private List<SysLog> sysLogs;

    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;

    @Transient
    @TableField(exist = false)
    private Integer userId;
    //权限标识
    @Transient
    @TableField(exist = false)
    private Integer htmlType;
    //临时状态
    private String ls_status;
}
