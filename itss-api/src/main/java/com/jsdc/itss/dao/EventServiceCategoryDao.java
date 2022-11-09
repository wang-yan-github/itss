package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventServiceCategory;
import org.springframework.stereotype.Repository;

@Repository
public class EventServiceCategoryDao extends BaseDao<EventServiceCategory> {


    public String selectData(){
        StringBuffer sql = new StringBuffer();
        sql.append( " SELECT event_service_category.`name`,event_service_category.id,event_service_category.is_use,event_service_category.sort, ");
        sql.append( " sys_company.`name` companyName,event_service_manage.`name` groupName,sys_user.`name` userName ,event_service_category.description, ");
        sql.append( " event_service_category.parent_type_id FROM event_service_category ");
        sql.append( " INNER JOIN sys_company ON event_service_category.company_id =sys_company.id ");
        sql.append( " INNER JOIN event_service_manage ON event_service_manage.id = event_service_category.group_id ");
        sql.append( " INNER JOIN sys_user ON event_service_category.engineer_id = sys_user.id ");
        sql.append( " WHERE sys_company.is_del = '0' ");
        sql.append( " AND event_service_manage.is_del = '0' ");
        sql.append( " AND sys_user.is_del = '0' ");
        sql.append( " AND event_service_category.is_del = '0' ");
        return sql.toString();
    }
}
