package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsInventory;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsInventoryDao extends BaseDao<AssetsInventory> {

    public String toList(AssetsInventory beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" select ai.*,su.name as createUser ,supdate.name as updateUser ").append(" FROM ").append(" assets_inventory ai ");
        sql.append(" LEFT JOIN sys_user su ON su.id = ai.create_user ");
        sql.append(" LEFT JOIN sys_user supdate ON supdate.id = ai.update_user ");
        sql.append(" where ai.is_del=0 ");
        if (notEmpty(beanParam.getName())){
            sql.append(" AND ai.name like '%"+beanParam.getName()+"%'");
        }
        if (notEmpty(beanParam.getStatus())){
            sql.append(" AND ai.status ="+beanParam.getStatus());
        }
        if (notEmpty(beanParam.getCreate_user())){
            sql.append(" AND ai.create_user="+beanParam.getCreate_user());
        }
        if (notEmpty(beanParam.getUpdate_user())){
            sql.append(" AND ai.update_user="+beanParam.getUpdate_user());
        }


        return sql.toString();
    }
}
