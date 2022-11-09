package com.jsdc.itss.dao;

import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsContractContact;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsContractContactDao extends BaseDao<AssetsContractContact> {

    public String toList(AssetsContractContact beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT acc.*, sd.name as deptName ").append(" FROM ").append("assets_contract_contact acc");
        sql.append(" left join sys_department sd on acc.dept_id = sd.id ");
        sql.append(" where acc.is_del=0 ");
        if(StringUtils.isNotBlank(beanParam.getContact_name())){
            sql.append(" and acc.contact_name like '%" + beanParam.getContact_name() + "%' ");
        }
        if(Base.notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0){
            sql.append(" and acc.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
        }
        return sql.toString();
    }
}
