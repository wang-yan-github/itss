package com.jsdc.itss.vo;

import lombok.Data;

@Data
public class ExportRoleWithUserVo {


    private Integer id;


    /**
     * 名称
     */
    private  String name;


    /**
     * 用户名
     */
    private  String user_name;

    /**
     * 姓名
     */
    private String real_name;

    /**
     * 邮箱
     */
    private  String mailbox;


    /**
     * 公司
     */
    private String company_name;


    /**
     * 部门
     */
    private String department_name;

    /**
     * 状态
     * 1活动 2暂停 3已禁止
     */
    private Short status;

    private String status_name;

}
