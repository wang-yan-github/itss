package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsInventoryConfig;
import com.jsdc.itss.vo.AssetsInventoryVo;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsInventoryConfigDao extends BaseDao<AssetsInventoryConfig> {

    public String toList(AssetsInventoryConfig beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_inventory_config");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }

    public String getAssetsInventorys(AssetsInventoryVo vo) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT config.*,inv.`name`,inv.status ,inv.id as inventoryId ")
                .append(" FROM ").append("  assets_inventory_config config left join  assets_inventory  inv on config.inventory_id = inv.id ");
        sql.append(" where 1=1 and is_del=0 ");
        if (null != vo) {
            // sql.append(" where 1=1 and is_del=0 ");
        }
        return sql.toString();
    }

    public String getAssetsInventory(AssetsInventoryConfig vo) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT config.*,inv.`name`,inv.status ").append(" FROM ").append("  assets_inventory_config config left join  assets_inventory  inv on config.inventory_id = inv.id ");
        sql.append(" where 1=1 ");
        if (null != vo && null != vo.getInventory_id() && vo.getInventory_id().intValue() >0) {
            sql.append(" and config.inventory_id = ").append(vo.getInventory_id()  );
        }

        if (null != vo && null != vo.getAssets_children_id() && vo.getAssets_children_id().intValue() > 0) {
            sql.append(" and config.assets_children_id = ").append(vo.getAssets_children_id());
        }

        if (null != vo && null != vo.getDept_id() && vo.getDept_id().intValue() > 0) {
            sql.append(" and config.dept_id = ").append(vo.getDept_id());
        }
        return sql.toString();
    }
}