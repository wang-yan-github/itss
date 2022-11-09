package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsSupplierContractUser;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsSupplierContractUserDao extends BaseDao<AssetsSupplierContractUser> {

    public String toList(AssetsSupplierContractUser beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_supplier_contract_user");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
