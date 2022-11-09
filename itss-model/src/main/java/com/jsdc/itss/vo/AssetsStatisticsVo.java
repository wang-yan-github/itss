package com.jsdc.itss.vo;

import lombok.Data;

import java.util.Date;

/**
 * @projectName: itss
 * @className: AssetsStatisticsVo
 * @author: wp
 * @description: 配置项统计查询条件
 * @date: 2022/7/20 9:24
 */
@Data
public class AssetsStatisticsVo {

    // 配置项类型
    private Integer assets_type_id;
    // 配置子类
    private Integer child_asset;
    // 资产状态
    private Integer assets_status;
    // IT状态
    private Integer it_status;
    // 资产名称
    private String assets_name;
    //部门
    private Integer dept_id;
    // 负责人
    private Integer person_liable;
    // 领用人
    private Integer receiver;
    // 购置公司
    private Integer purchase_company;
    // 使用范围
    private Integer use_scope_id;
    //购置创建日期开始
    private String start_time;
    //购置创建日期结束
    private String end_time;
    //是否打印 0已打印 1未打印
    private String is_print;
}
