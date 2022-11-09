package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class SysDepartmentDao extends BaseDao<SysDepartment> {

    public String getDepartmentInfo(SysDepartment sysDepartment){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT sys_department.*,code, ");
        sql.append(" unique_id,sys_company.name companyName,event_service_manage.`name` manageName ");
        sql.append(" from sys_department left JOIN sys_company ON sys_department.company_id = sys_company.id ");
        sql.append(" left JOIN event_service_manage ON sys_department.group_id =event_service_manage.id ");
        sql.append(" WHERE  sys_department.is_del ='0' ");
//        sql.append(" AND sys_company.is_del ='0' ");
//        sql.append(" AND event_service_manage.is_del='0' ");
        if (StringUtils.isNotEmpty(sysDepartment.getName())){
            sql.append(" AND sys_department.name like concat ('%','"+sysDepartment.getName()+"','%' ");
        }
        return sql.toString();
    }

    public String getByKnowRangeId(int knowledgeRangeId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" b.* from knowledge_range_info a ");
        sql.append(" INNER join sys_department b  ");
        sql.append(" on a.range_type='2' and a.fk_id=b.id ");
        sql.append(" where a.knowledge_range_id= "+knowledgeRangeId);
        sql.append(" and b.is_del='0' ");
        return sql.toString();
    }
}
