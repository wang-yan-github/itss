package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsSoftwareLicence;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsSoftwareLicenceDao extends BaseDao<AssetsSoftwareLicence> {

    public String toList(AssetsSoftwareLicence beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT t.*, sd.name as deptName ").append(" FROM ").append("assets_software_licence t ");
        sql.append(" left join sys_department sd on sd.id = t.deptId ");
        sql.append(" where t.is_del=0 ");
        if(StringUtils.isNotBlank(beanParam.getOs_licence_name())){
            sql.append(" and t.os_licence_name like '%" + beanParam.getOs_licence_name() + "%' ");
        }
        return sql.toString();
    }
}
