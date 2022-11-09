package com.jsdc.itss.vo;

import com.jsdc.itss.model.AssetsInventoryConfig;
import lombok.Data;

/**
 * 盘点期管理表(assets_inventory) 和 盘点资产配置表(assets_inventory_config) 在本类中
 * @Author libin
 * @create 2022/3/18 14:44
 */
@Data
public class AssetsInventoryVo extends AssetsInventoryConfig {

    private String name;//名称
    private Integer status;//状态
//    private Integer inventoryId;//盘点期id

}
