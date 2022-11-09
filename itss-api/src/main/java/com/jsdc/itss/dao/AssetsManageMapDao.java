package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsManageMap;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsManageMapDao extends BaseDao<AssetsManageMap> {

    public String toList(AssetsManageMap beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_manage_map");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
