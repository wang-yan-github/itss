package com.jsdc.itss.vo;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.SysDict;
import com.jsdc.itss.model.SysSql;
import lombok.Data;

import java.util.HashMap;
@Data
public class SysSqlVo {

    private PageInfo<SysSql> page;

    private HashMap<String, SysDict> sqlDisplayMode;
    //SQL报表显示方式
    private HashMap<String, SysDict> sqlModule;
    //SQL报表时间类型
    private HashMap<String, SysDict> sqlTimechoose;
    //SQL报表可见范围
    private HashMap<String, SysDict> sqlScope;
    //SQL报表状态
    private HashMap<String, SysDict> sqlIsUse;
}
