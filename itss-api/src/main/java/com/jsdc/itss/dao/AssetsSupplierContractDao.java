package com.jsdc.itss.dao;

import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsSupplierContract;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsSupplierContractDao extends BaseDao<AssetsSupplierContract> {

    public String toList(AssetsSupplierContract beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append("SELECT asco.*,sdi.label currency_name,sdic.label status_name,sd.`name` deptName ");
        sql.append("FROM assets_supplier_contract asco ");
        sql.append("LEFT JOIN sys_department sd ON asco.dept_id = sd.id ");
        sql.append("LEFT JOIN sys_dict sdi ON asco.currency = sdi.`value` AND sdi.dict_type='currency' ");
        sql.append("LEFT JOIN sys_dict sdic ON asco.`status` =sdic.`value` AND sdic.dict_type='supplier_contract_status' ");
        sql.append("where asco.is_del='0' ");
        if (notEmpty(beanParam.getDept_id())){
            sql.append(" AND asco.dept_id="+beanParam.getDept_id());
        }
        if (notEmpty(beanParam.getContract_name())){
            sql.append(" AND asco.contract_name like '%"+beanParam.getContract_name()+"%'");
        }
        if (notEmpty(beanParam.getStatus())){
            sql.append(" AND asco.status ="+beanParam.getStatus());
        }
        if (notEmpty(beanParam.getSupplier_name())){
            sql.append(" AND asco.supplier_name like '%"+beanParam.getSupplier_name()+"%'");
        }
        if(Base.notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0){
            sql.append(" and asco.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
        }
        sql.append(" ");
        return sql.toString();
    }
}
