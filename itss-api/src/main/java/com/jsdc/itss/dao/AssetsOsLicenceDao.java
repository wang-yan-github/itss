package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsOsLicence;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsOsLicenceDao extends BaseDao<AssetsOsLicence> {

    public String toList(AssetsOsLicence beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" select aol.*,sd.`name` as deptName ").append(" FROM ").append("assets_os_licence aol");
        sql.append(" left join sys_department sd on aol.deptId = sd.id ");
        sql.append(" where aol.is_del=0 ");
        if(StringUtils.isNotBlank(beanParam.getOs_licence_name())){
            sql.append(" and aol.os_licence_name like '%" + beanParam.getOs_licence_name() + "%' ");
        }
        return sql.toString();
    }
}
