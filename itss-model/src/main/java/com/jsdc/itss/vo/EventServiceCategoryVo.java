package com.jsdc.itss.vo;

import lombok.Data;

@Data
public class EventServiceCategoryVo {

    private Integer id;

    //    2	名称	name	varchar(256)	无	名字
    private String name;
    //3	状态	is_use	int		（数据字典）0：禁用 1：启用
    private Integer is_use;
    //4	自动分派到服务群组	group_id	int		自动分派到服务群组ID
    private Integer group_id;
    private String group_name;//服务群组名称
    //5	自动分派到工程师	engineer_id	int		自动分派到工程师ID
    private Integer engineer_id;
    private String engineer_name;//自动分派到工程师
    //6	上级类别	parent_type_id	int		上级类别ID
    private Integer parent_type_id;
    private String parent_type_name;//上级类别名称
    //7	公司	company_id	int		公司ID
    private Integer company_id;
    private String company_name;//公司名称
    //8	工单类别	worker_order_id	int		工单类别ID
    private Integer worker_order_id;
    private String bill_type_name;//工单类别名称
    //9	排序	sort	int		排序
    private Integer sort;
    //10	描述	desc	blob		描述
    private String description;
    //11	服务表单默认标题	from_title	varchar 256		默认标题
    private String from_title;
    //12	服务表单默认描述	from_desc	blob		服务表单默认描述
    private String from_desc;
    // 模块id集合
    private  String ids;
}
