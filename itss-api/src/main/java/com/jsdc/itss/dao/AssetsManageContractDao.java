package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsManageContract;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsManageContractDao extends BaseDao<AssetsManageContract> {

    public String toList(AssetsManageContract beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_manage_contract");
        sql.append(" where is_del=0 ");
        return sql.toString();

    }
}
