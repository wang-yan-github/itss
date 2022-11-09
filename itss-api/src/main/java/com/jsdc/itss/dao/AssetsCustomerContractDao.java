package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsCustomerContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsCustomerContractDao extends BaseDao<AssetsCustomerContract> {

    public String toList(AssetsCustomerContract beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT acc.*, sd.name as deptName ").append(" FROM ").append("assets_customer_contract acc ");
        sql.append(" left join sys_department sd on sd.id = acc.dept_id ");
        sql.append(" where acc.is_del=0 ");
        if(StringUtils.isNotBlank(beanParam.getCustomer_name())){
            sql.append(" and acc.customer_name like '%" + beanParam.getCustomer_name() + "%' ");
        }
        if(StringUtils.isNotBlank(beanParam.getContract_name())){
            sql.append(" and acc.contract_name like '%" + beanParam.getContract_name() + "%' ");
        }
        if(null !=  beanParam.getStatus()){
            sql.append(" and acc.status=" + beanParam.getStatus());
        }
        if(null != beanParam.getDept_id()){
            sql.append(" and acc.dept_id=" + beanParam.getDept_id());
        }
        return sql.toString();
    }
}
