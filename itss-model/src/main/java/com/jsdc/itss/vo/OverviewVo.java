package com.jsdc.itss.vo;

import com.jsdc.itss.model.AssetsClGroup;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.model.AssetsType;
import lombok.Data;
import org.elasticsearch.client.license.LicensesStatus;

import java.util.List;
import java.util.Map;

/**
 * @Author libin
 * @create 2022/3/15 9:42
 * 配置管理 概览vo类
 */
@Data
public class OverviewVo extends AssetsManage {
    private int ciId;
    private String ciEnName;
    private String ciName;
    //assetsTypes和assetsTypeIds 数量应该一样多
    private String assetsTypes;//多个逗号分隔
    private String assetsTypeIds;//多个逗号分隔

    private int assetsTypeId;
    private int assetsTypeSum;

    // 结果  每个CI 分组对应的配置项类型（Key） 配置项对应配置项管理数量（value）
    private Map<String, Integer> result;
}
