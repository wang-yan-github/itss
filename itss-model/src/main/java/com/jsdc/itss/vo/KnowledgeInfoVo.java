package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.KnowledgeFile;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class KnowledgeInfoVo {

    private  Integer id ;
    private  Integer knowledge_status; //知识状态
    private  String categoryName ; // 知识分类名字
    private  Integer  knowledge_category_id;// 知识分类ID
    private  String is_full_search;//可被全员搜索
    private String is_recommend;//推荐知识 0不推荐 1推荐
    private Integer knowledge_range_id;//工程师内部分享范围ID
    private String knowledge_range;//工程师内部分享范围
    private  String title;//标题
    private String content;//正文
    private String keyword;//关键字
    private  String createUserName ; // 创建人名字
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;//创建时间
    private Integer update_user;
    private String  updateUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time; //更新时间



    private  Integer approver_id;//审核人id
    private String approver_name; //审核人姓名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approval_time;//审核时间
    private  String status;//状态 1：待审核 2：发布3：审核拒绝
    private String approval_opinions;//审核意见


    private Integer rater;//评分人ID
    private String rater_name;//评分人名字
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scoring_time;//评分时间
    private  Integer score;//贡献分
    private String comment;//评语



    private  String departmentName;//部门名称
    private  Integer sort_num;//排序号

    private List<FileManage> files = new ArrayList<>();


}
