package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsSupplierContractMap;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsSupplierContractMapDao extends BaseDao<AssetsSupplierContractMap> {

    public String toList(AssetsSupplierContractMap beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_supplier_contract_map");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
