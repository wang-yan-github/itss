package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.model.SysCompany;
import org.springframework.stereotype.Repository;

@Repository
public class SysCompanyDao extends BaseDao<SysCompany> {

    public String getCompanyInfo(SysCompany sysCompany){
        StringBuffer sql =new StringBuffer( );
        sql.append( " SELECT sys_company.id,sys_company.`name`,sys_company.is_show,event_service_manage.`name` manageName,event_service_category.`name` categoryName,sys_company.sort, ");
        sql.append( " sys_company.is_use FROM sys_company left JOIN event_service_manage ON sys_company.service_type = event_service_manage.id ");
        sql.append( " left JOIN event_service_category ON event_service_category.id = sys_company.group_id ");
        sql.append( " WHERE 1=1 ");
        sql.append(" AND sys_company.is_del ='0' ");
        if (StringUtils.isNotEmpty(sysCompany.getName())){
            sql.append(" AND sys_company.`name` like '%"+sysCompany.getName()+"%' ");
        }
        if (sysCompany.getSort()!=null){
            sql.append(" AND sys_company.`sort`= "+sysCompany.getSort());
        }
        if (sysCompany.getIs_use()!=null){
            sql.append(" AND sys_company.`is_use`= "+sysCompany.getIs_use());
        }
        return sql.toString();
    }
}
