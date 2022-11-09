package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.QuestionVo;
import com.jsdc.itss.vo.SysUserPageVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 事件表
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@Entity
@TableName("event")
@Table(name = "event")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event extends Model<Event> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户外键
    private Integer user_id;
    //开单人类别 1:用户开单 2:工程师开单
    private Integer user_type;
    // 公司id
    private Integer company_id;
    // 公司名称
    @Transient
    @TableField(exist = false)
    private String company_name;
    //姓名
    private String user_name;
    //部门
    private Integer department;
    //部门名称
    @Transient
    @TableField(exist = false)
    private String deptName;
    //职位
    private String position;
    //位置
    private String location;
    //电话
    private String phone;
    //座机
    private String landline;
    //邮箱
    private String email;
    //标题
    private String title;
    //描述
    private String description;
    //请求类型
    private Integer request_type;
    //来源
    private Integer source_id;
    //工单类别
    private Integer event_category_id;
    //服务类别
    private Integer service_category;
    //影响度
    private Integer effect_degree;
    //紧急度
    private Integer urgency_degree_id;
    //影响系统可用性
    private String is_effect;
    //服务群组
    private Integer service_groups_id;
    //指派人
    private Integer assigner;
    //处理人
    private Integer handler;
    //解决方式
    private Integer solve_type;
    @TableField(exist = false)
    private String solve_type_name;
    //方案类别
    private Integer programme_type;
    //解决方案（处理信息描述）
    private String solution;
    //单据状态 字典(1.待响应2.处理中  3暂停 4.处理完成5.待评价6.已关闭7.已撤销)
    private String status;
    //响应人ID
    private Integer response_user;
    /**
     * 来源名称
     */
    @Transient
    @TableField(exist = false)
    private String source_name;

    @Transient
    @TableField(exist = false)
    private String request_type_name;

    //工单名称
    @Transient
    @TableField(exist = false)
    private String event_category_name;

    //影响度名称
    @Transient
    @TableField(exist = false)
    private String effect_degree_name;

    //紧急度名称
    @Transient
    @TableField(exist = false)
    private String urgency_degree_name;
    //事件优先级
    private Integer priority_id;
    @Transient
    @TableField(exist = false)
    private String priority_name;
    @Transient
    @TableField(exist = false)
    //影响系统可用性名称
    private String is_effect_name;


    //服务群组名称
    @Transient
    @TableField(exist = false)
    private String service_name;

    @Transient
    @TableField(exist = false)
    private String assigner_name;
    //指派时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assigner_time;

    //处理人名称
    @Transient
    @TableField(exist = false)
    private String handler_name;
    //预计解决时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ex_solve_time ;

    //方案类别名称
    @Transient
    @TableField(exist = false)
    private String programme_type_name;
    //费用
    private String expenses;

    //费用开始
    @Transient
    @TableField(exist = false)
    private String expenses_start;
    //费用结束
    @Transient
    @TableField(exist = false)
    private String expenses_end;



    @Transient
    @TableField(exist = false)
    private String statusName;
    private String remark ;
    //点响应时的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date responseTime ;
    //点处理时的时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date processTime ;
    //点暂停时的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pauseTime ;
    //点恢复时的时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date restoreTime ;
    //撤销时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date revokeTime ;
    //关单人
    private Integer close_users;
    @Transient
    @TableField(exist = false)
    private String close_user_name;
    //关单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    //符合响应SLA  0否 1是
    private String meetResponseSLA ;

    //符合处理SLA  0否 1是
    private String meetProcessingSLAs ;
    //重开次数  默认0
    private Integer numberOfRestarts ;
    //转单备注
    private String transferRemark ;
    //撤销备注
    private String revokeRemark ;
    //暂停备注
    private String pauseRemark ;
    //恢复备注
    private String returnRemark ;
    //指派备注
    private String appointRemark ;
    //解决工程师id
    private Integer solve_user_id;
    // 响应升级标识 0-未升级 1-已升级
    private Integer response_upgrade = 0;
    // 处理升级标识 0-未升级 1-已升级
    private Integer process_upgrade = 0;
    @Transient
    @TableField(exist = false)
    private String solve_user_name;
    //工单类别名称
    @Transient
    @TableField(exist = false)
    private String category_name;
    //用户名称
    @Transient
    @TableField(exist = false)
    private String user_id_name;
    //用户登录名称
    @Transient
    @TableField(exist = false)
    private String user_id_loginName;
    //开单人名称
    @Transient
    @TableField(exist = false)
    private String create_user_name;
    //响应人名称
    @Transient
    @TableField(exist = false)
    private String response_user_name;

    //用户满意度【最新】
    private Integer event_satisfaction;
    //用户满意度【建议】
    private String proposal;
    //协同处理人
    private String assistUsers;
    //协查人
    @Transient
    @TableField(exist = false)
    private List<EventAssist> eventAssist = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<SysUserPageVo> eventAssistUsers = new ArrayList<>();
    //管理配置项
    @Transient
    @TableField(exist = false)
    private List<EventAssets> eventAssets = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<AssetsManage> assetsManages = new ArrayList<>();
    //关联的工单
    @Transient
    @TableField(exist = false)
    private List<EventRelation> eventRelation = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<Event> eventList = new ArrayList<>();
    //关联的问题
    @Transient
    @TableField(exist = false)
    private List<EventQuestionRelation> questionRelations = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<QuestionVo> questionList = new ArrayList<>();
    //关联的变更
    @Transient
    @TableField(exist = false)
    private List<EventChangeRelation> changeRelations = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<ChangeVo> changeList = new ArrayList<>();

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_time_start;

    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time_end;


    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_time_relend;

    /**
     * 关单时间
     */
    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date closeStartTime;

    @Transient
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeEndTime;


    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 工单模板ID
     */
    private Integer template_id;



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
    private String handlerWithMeFlag; //待我处理

    //属性信息 附件
    @Transient
    @TableField(exist = false)
    private List<FileManage> files = new ArrayList<>();
    //处理信息信息 附件
    @Transient
    @TableField(exist = false)
    private List<FileManage> files1 = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<SysLog> sysLogs = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private EventTimes eventTimes ;
    //属性信息 附件
    private String uploadedFiles;
    //处理信息信息 附件
    private String uploadedHandleFiles;
    //服务类别
    @Transient
    @TableField(exist = false)
    private String parent_type_name;
    //关联配置项
    @Transient
    @TableField(exist = false)
    private String eventAssetName;
    // 剩余分钟数
    @Transient
    @TableField(exist = false)
    private String surplusMinutes;
    @Transient
    @TableField(exist = false)
    private Integer WX_userId;
    @Transient
    @TableField(exist = false)
    private Integer login_id;
    @Transient
    @TableField(exist = false)
    private String ex_time;
    @Transient
    @TableField(exist = false)
    private String fileIds;
    @Transient
    @TableField(exist = false)
    private String billStartTime;
    @Transient
    @TableField(exist = false)
    private String billEndTime;

    //相应SLA/解决SLA
    @Transient
    @TableField(exist = false)
    private String responseSLA;
    //关联的配置项id
    @Transient
    @TableField(exist = false)
    private Integer eventAssetId;

    // 当前用户是否为处理人
    @Transient
    @TableField(exist = false)
    private Boolean isHandler = false;

    // 当前用户是否为开单人
    @Transient
    @TableField(exist = false)
    private Boolean isCreateUser = false;

    //服务群组1、查询当前用户 2、查询全部,不传也查询全部
    @Transient
    @TableField(exist = false)
    private Integer service_groups_type;

    @Transient
    @TableField(exist = false)
    private List<Integer> statusList = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private Integer userId;

    @Transient
    @TableField(exist = false)
    private List<Event> success = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<Event> error = new ArrayList<>();
    @Transient
    @TableField(exist = false)
    private List<Event> all = new ArrayList<>();
}
