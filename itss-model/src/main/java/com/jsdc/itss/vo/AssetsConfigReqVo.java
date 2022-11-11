package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: AssetsConfigReqVo
 * Description:
 * date: 2022/3/24 9:32
 *
 * @author bn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetsConfigReqVo extends AssetsConfigReq {

    // 部门名称
    private String department_name;

    // 类型名称
    private String request_name;

    // 创建人名
    private String create_name;

    // 处理人名
    private String deal_user_name;

    // 状态名
    private String label;
    // 关联的变更单编号
    private String relevance_name;

    // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_time;

    // 结束时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_time;

    // 处理开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deal_start_time;

    // 处理结束时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deal_end_time;

    //配置资产中间表
    List<AssetsConfigReqProperty> assetsConfigReqProperties;

    //关联的事件集合
    private List<Event> events = new ArrayList<>();

    // 配置工单中间表
    List<AssetsConfigReqOrder> assetsConfigReqOrders;

    //关联的配置集合
    private List<AssetsManage> assetsManages = new ArrayList<>();

    // 0 配置请求，1.我的配置请求,2.配置请求待办
    private String data_flag;

    // 关键字 请求编号
    private String keyword;

    private Integer userId;
    private Integer WX_userId;


}
