package com.jsdc.itss.vo;

import lombok.Data;

@Data
public class ExportSysUserRoleVo {

    private String user_name ;
    private String name;

    private String mailbox;

    private String location;

    private String dName;

    private  Short status;

    private  String status_name;

    private Integer rId;

    private String rName;
}
