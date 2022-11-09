package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsSupplierContractFile;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsSupplierContractFileDao extends BaseDao<AssetsSupplierContractFile> {

    public String toList(AssetsSupplierContractFile beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_supplier_contract_file");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }

    public String getFileManage(Integer id){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT fm.* FROM assets_supplier_contract_file t ");
        sql.append(" LEFT JOIN file_manage fm ON t.file_id = fm.id  ");
        sql.append(" where t.is_del = 0 and fm.is_del = 0 ");
        sql.append(" and t.supplier_id = ").append(id);
        return sql.toString();
    }

}
