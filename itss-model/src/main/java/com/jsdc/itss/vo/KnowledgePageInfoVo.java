package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 知识管理分页展示数据
 * created by : xuaolong
 */

@Data
public class KnowledgePageInfoVo {

    private  Integer id ;

    private  String title;//标题

    private  String departmentName;//部门名称

    private  String categoryName ; // 知识分类名字
    private  Integer  knowledge_category_id;// 知识分类ID

    private  String createUserName ; // 创建人名字

    private  Integer approver_id;//审核人id

    private  Integer score;//贡献分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date create_time;//创建时间

    private  String status;//状态 1：待审核 2：发布3：审核拒绝

    private  Integer is_full_search;//可被全员搜索
    private  String is_full_search_name;//可被全员搜索

    private  Integer knowledge_status; //知识状态
    private  String knowledge_status_name; //知识状态

    private  Integer sort_num;//排序号


    private String approver_name; //审核人姓名





}
