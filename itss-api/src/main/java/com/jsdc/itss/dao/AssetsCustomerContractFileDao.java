package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsCustomerContractFile;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsCustomerContractFileDao extends BaseDao<AssetsCustomerContractFile> {

    public String toList(AssetsCustomerContractFile beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_customer_contract_file");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
