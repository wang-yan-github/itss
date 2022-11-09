package com.jsdc.itss.vo;

import lombok.Data;

@Data
public class SysCompanyVo {
    private Integer id ;
    private String name;
    private String manageName;
    private String categoryName;
    private String is_show;
    private String sort;
    private String is_use;
}
