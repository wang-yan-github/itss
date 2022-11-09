package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsInventoryDetails;
import com.jsdc.itss.vo.AssetsInventoryDetailsVo;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsInventoryDetailsDao extends BaseDao<AssetsInventoryDetails> {

    public String toList(AssetsInventoryDetailsVo beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT aid.*, ai.`name` ai_name,am.id am_id, ");
        sql.append("am.assets_number am_assets_number,aty.`name` aty_name, ");
        sql.append("ac.`name` ac_name,am.assets_name am_assets_name, ");
        sql.append("sd.`name` sd_name,su.`name` su_name,ast.status_name ast_status_name, ");
        sql.append("am.it_status am_it_status,suc.`name` suc_name,suh.`name` suh_name ");
        sql.append("FROM assets_inventory_details aid ");
        sql.append("LEFT JOIN assets_inventory ai ON aid.inventory_id = ai.id ");
        sql.append("LEFT JOIN assets_manage am ON aid.assets_id = am.id ");
        sql.append("LEFT JOIN assets_children ac ON am.child_asset = ac.id ");
        sql.append("LEFT JOIN assets_type aty ON ac.assets_type_id = aty.id ");
        sql.append("LEFT JOIN sys_department sd ON am.dept_id = sd.id ");
        sql.append("LEFT JOIN sys_user su ON am.receiver = su.id ");
        sql.append("LEFT JOIN assets_status ast ON am.assets_status = ast.id ");
        sql.append("LEFT JOIN sys_user suc ON aid.confirm_user=suc.id ");
        sql.append("LEFT JOIN sys_user suh ON aid.handle_user=suh.id ");
        sql.append("where aid.is_del=0 ");

        //盘点期
        if (notEmpty(beanParam.getAi_name())) {
            sql.append(" AND ai.`name` = '" + beanParam.getAi_name() + "'");
        }
        //盘点期id
        if (notEmpty(beanParam.getInventory_id())) {
            sql.append(" AND ai.id = " + beanParam.getInventory_id());
        }
        //资产编号
        if (notEmpty(beanParam.getAm_assets_number())) {
            sql.append(" AND am.assets_number like '%" + beanParam.getAm_assets_number() + "%'");
        }
        //配置项类型id
        if (notEmpty(beanParam.getAty_id())) {
            sql.append(" AND aty.id = " + beanParam.getAty_id());
        }
        //配置项子类型id
        if (notEmpty(beanParam.getAc_id())) {
            sql.append(" AND am.child_asset = " + beanParam.getAc_id());
        }
        if (notEmpty(beanParam.getInventory_id())) {
            sql.append(" AND aid.inventory_id= " + beanParam.getInventory_id());
        }

        if (notEmpty(beanParam.getAm_assets_name())) {
            sql.append(" AND am.assets_name like '%" + beanParam.getAm_assets_name() + "%'");
        }
        if (null != beanParam.getSd_id()) {
            sql.append(" AND am.dept_id =" + beanParam.getSd_id());
        }
        if (null != beanParam.getSu_id() ) {
            sql.append(" AND am.receiver=" + beanParam.getSu_id());
        }
        if (notEmpty(beanParam.getStatus())) {
            sql.append(" AND aid.status=" + beanParam.getStatus());
        }
        if (notEmpty(beanParam.getHandle_status())) {
            sql.append(" AND aid.handle_status=" + beanParam.getHandle_status());
        }
        if (null !=beanParam.getSuc_id())  {
            sql.append(" AND aid.confirm_user=" + beanParam.getSuc_id());
        }
        if (null != beanParam.getSuh_id()) {
            sql.append(" AND aid.handle_user=" + beanParam.getSuh_id());
        }
        //盘点期名称
        if (notEmpty(beanParam.getKeywords())) {
            sql.append(" AND ai.`name` like '%" + beanParam.getKeywords() + "%'");
        }

        return sql.toString();
    }

    public String getOne(Integer id) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT aid.*, ai.`name` ai_name,am.id am_id, ");
        sql.append("am.assets_number am_assets_number,aty.`name` aty_name, ");
        sql.append("ac.`name` ac_name,am.assets_name am_assets_name, ");
        sql.append("sd.`name` sd_name,su.`name` su_name,ast.status_name ast_status_name, ");
        sql.append("am.it_status am_it_status,suc.`name` suc_name,suh.`name` suh_name ");
        sql.append("FROM assets_inventory_details aid ");
        sql.append("LEFT JOIN assets_inventory ai ON aid.inventory_id = ai.id ");
        sql.append("LEFT JOIN assets_manage am ON aid.assets_id = am.id ");
        sql.append("LEFT JOIN assets_children ac ON am.child_asset = ac.id ");
        sql.append("LEFT JOIN assets_type aty ON ac.assets_type_id = aty.id ");
        sql.append("LEFT JOIN sys_department sd ON am.dept_id = sd.id ");
        sql.append("LEFT JOIN sys_user su ON am.receiver = su.id ");
        sql.append("LEFT JOIN assets_status ast ON am.assets_status = ast.id ");
        sql.append("LEFT JOIN sys_user suc ON aid.confirm_user=suc.id ");
        sql.append("LEFT JOIN sys_user suh ON aid.handle_user=suh.id ");
        sql.append("where aid.is_del=0 ");
        sql.append(" and aid.id = "+id);
        return sql.toString();
    }

    public String getInventoryDetails(AssetsInventoryDetailsVo beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT aid.*, ai.`name` ai_name,am.id am_id, ");
        sql.append("am.assets_number am_assets_number,aty.`name` aty_name, ");
//        sql.append("ac.assets_type_id aty_id, ");
        sql.append("ac.`name` ac_name,am.assets_name am_assets_name, ");
        sql.append("sd.`name` sd_name,su.`name` su_name,ast.status_name ast_status_name, ");
        sql.append("am.it_status am_it_status,suc.`name` suc_name,suh.`name` suh_name ");
        sql.append("FROM assets_inventory_details aid ");
        sql.append("LEFT JOIN assets_inventory ai ON aid.inventory_id = ai.id ");
        sql.append("LEFT JOIN assets_manage am ON aid.assets_id = am.id ");
        sql.append("LEFT JOIN assets_children ac ON am.child_asset = ac.id ");
        sql.append("LEFT JOIN assets_type aty ON ac.assets_type_id = aty.id ");
        sql.append("LEFT JOIN sys_department sd ON am.dept_id = sd.id ");
        sql.append("LEFT JOIN sys_user su ON am.receiver = su.id ");
        sql.append("LEFT JOIN assets_status ast ON am.assets_status = ast.id ");
        sql.append("LEFT JOIN sys_user suc ON aid.confirm_user=suc.id ");
        sql.append("LEFT JOIN sys_user suh ON aid.handle_user=suh.id ");
        sql.append("where aid.is_del=0 ");

        if (notEmpty(beanParam.getId()) && beanParam.getId().intValue() > 0) {
            sql.append(" AND aid.id= " + beanParam.getId());
        }
        //盘点期
        if (notEmpty(beanParam.getAi_name())) {
            sql.append(" AND ai.`name` = '" + beanParam.getAi_name() + "'");
        }
        //资产编号
        if (notEmpty(beanParam.getAm_assets_number())) {
            sql.append(" AND am.assets_number like '%" + beanParam.getAm_assets_number() + "%'");
        }
        //配置项类型
        if (notEmpty(beanParam.getAty_id())) {
            sql.append(" AND aty.id like '%" + beanParam.getAty_id() + "%'");
        }
        //配置项子类型
        if (notEmpty(beanParam.getAc_id())) {
            sql.append(" AND am.child_asset like '%" + beanParam.getAc_id() + "%'");
        }
        if (notEmpty(beanParam.getInventory_id())) {
            sql.append(" AND aid.inventory_id= " + beanParam.getInventory_id());
        }

        if (notEmpty(beanParam.getAm_assets_name())) {
            sql.append(" AND am.assets_name like '%" + beanParam.getAm_assets_name() + "%'");
        }
        if (notEmpty(beanParam.getSd_id())) {
            sql.append(" AND am.dept_id =" + beanParam.getSd_id());
        }
        if (notEmpty(beanParam.getSu_id())) {
            sql.append(" AND am.receiver=" + beanParam.getSu_id());
        }
        if (notEmpty(beanParam.getStatus())) {
            sql.append(" AND aid.status=" + beanParam.getStatus());
        }
        if (notEmpty(beanParam.getHandle_status())) {
            sql.append(" AND aid.handle_status=" + beanParam.getHandle_status());
        }
        if (notEmpty(beanParam.getSuc_id()) && beanParam.getSuc_id() > 0) {
            sql.append(" AND aid.confirm_user=" + beanParam.getSuc_id());
        }
        if (notEmpty(beanParam.getSuh_id()) && beanParam.getSuh_id() > 0) {
            sql.append(" AND aid.handle_user=" + beanParam.getSuh_id());
        }

        return sql.toString();
    }
}
