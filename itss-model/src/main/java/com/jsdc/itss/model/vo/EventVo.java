package com.jsdc.itss.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.SysUserPageVo;
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


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EventVo implements Serializable {

   private Integer id;
   private Integer user_id;//用户外键
   private String user_name;//姓名
   private Integer department;//SLA
   private String position;//职位
   private String location;//位置
   private String phone;//电话
   private String landline;//座机
   private String email;//邮箱
   private String title;//标题
   //服务类别
   private Integer service_category;
   @ExcelProperty("描述")
   private String description;//描述
   private Integer source_id;//来源
   private String request_type;//请求类型
   private Integer event_category_id;//工单类别
   private String effect_degree;//影响度
   private Integer urgency_degree_id;//紧急度
   private Integer priority_id;//事件优先级
   private String is_effect;//影响系统可用性
   private Integer service_groups_id;//服务群组
   private Integer user_type;//开单类型
   private Integer service_groups_type;//服务群组1、查询当前用户 2、查询全部,不传也查询全部
    //响应人ID
    private Integer response_user;

   private Integer assigner;//指派人

   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date assigner_time; //指派时间
   private Integer handler;//处理人
   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date ex_solve_time ; //预计解决时间
   private Integer solve_type;//解决方式
   private Integer programme_type;//方案类别
   private String expenses;//费用
   private String expenses_start;//费用起始时间
   private String expenses_end;//费用结束时间
   private String solution;//解决方案
   private String status;//单据状态
   private String remark ;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date responseTime ;//点响应时的时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date processTime ; //点处理时的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date pauseTime ; //点暂停时的时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date restoreTime ; //点恢复时的时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date revokeTime ; //撤销时间
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date closeTime; //关单时间


   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private String closeStartTime; //关单开始时间
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private String closeEndTime; //关单结束时间

   /**
    * 工单模板ID
    */
   private Integer template_id;

   //转单备注
   private String transferRemark ;

   private String revokeRemark ; //撤销备注
   private String pauseRemark ; //暂停备注

   private Integer solve_user_id; //解决工程师id
   private Integer create_user; //创建人id


   private Integer event_satisfaction; // 满意度

   private Integer numberOfRestarts; //重启次数

   //符合响应SLA  0否 1是
   private String meetResponseSLA ;

   private String solve_user_name;
   private Integer close_users;//  处理人/关单人
   private String close_user_name;
   private String assigner_name;

   @ExcelProperty("服务群组")
   private String service_group_name;
   @ExcelProperty("部门名称")
   private String dept_name;
   @ExcelProperty("状态")
   private String status_name;
   @ExcelProperty("请求类型")
   private String request_type_name;
   @ExcelProperty("工单类别")
   private String event_category_name;
   @ExcelProperty("优先级")
   private String priority_name;
   @ExcelProperty("开单人")
   private String create_user_name;
   @ExcelProperty("开单时间")
   private String create_time;
   @ExcelProperty("处理人")
   private String handler_user_name;
   @ExcelProperty("响应时间")
   private String response_time;
   @ExcelProperty("关单时间")
   private String close_time;
   @ExcelProperty("解决方案")
   private String solution_name;
   @ExcelProperty("用户username")
   private String userName;

   private Event event ;
   //用户评价
   private EventSatisfaction eventSatisfaction;

   //开单开始时间
   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
   private String billStartTime;
   //开单结束时间
   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
   private String billEndTime;
   //查询需要的状态集合
   private List<String> status_list = new ArrayList<>();
    //协查人
   private List<SysUserPageVo> eventAssist1 = new ArrayList<>();
   private List<EventAssist> eventAssist = new ArrayList<>();
   //管理配置项
   private List<EventAssets> eventAssets = new ArrayList<>();

   private List<AssetsManage> eventAssets1 = new ArrayList<>();
   //关联的工单
   private List<EventRelation> eventRelation = new ArrayList<>();

   private List<Event> eventRelation1 = new ArrayList<>();
   //关联的问题
   private List<EventQuestionRelation> questionRelations = new ArrayList<>();
   //关联的变更
   private List<EventChangeRelation> changeRelations = new ArrayList<>();

   private List<Integer> ids = new ArrayList<>();
   //属性信息 附件
   private String uploadedFiles;

    private Integer pageIndex;
    private Integer pageSize;
    private  Integer num;

    private List<Integer> numList = new ArrayList<>();
    private List<Integer> yearList = new ArrayList<>();
   private List<String> monthList = new ArrayList<>();
    private List<String> categoryName = new ArrayList<>();
    //工单完成率
    private Integer finish;
    //响应slamanzulv
    private Integer resSla;
    //处理SLA满足率
    private Integer dealSla;

    private String create_start_time;
    private String create_end_time;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_time_start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time_end;

    private String keywords;
    //1 定时生成工单标识
    private String timeTaskStatus;
    //当前登录id
    private Integer current_user;

    private  String fileIds;
    //获取当前用户下的服务群组
    private List<Integer>  service_groups_list;//服务群组

    private Integer WX_userId;


    private Integer userId;
}
