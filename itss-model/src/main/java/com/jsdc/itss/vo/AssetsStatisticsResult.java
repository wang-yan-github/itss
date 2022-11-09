package com.jsdc.itss.vo;

import lombok.Data;

/**
 * @projectName: itss
 * @className: StatisticsResult
 * @author: wp
 * @description:
 * @date: 2022/7/20 10:10
 */
@Data
public class AssetsStatisticsResult {
    private String typeName;

    private Integer count;

    private String statusName;

    private String groupName;

    private String name;

    private Integer value;
}
