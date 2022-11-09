package com.jsdc.itss.vo;

import lombok.Data;

@Data
public class SysRoleUserVo {
    private  Integer id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 状态
     * 1活动 2暂停 3已禁止
     */
    private Short status;

    /**
     * 电话
     */
    private  String telephone;

    /**
     * 用户类型
     */
    private String user_type;
}
