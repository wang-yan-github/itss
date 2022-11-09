package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventServiceManage;
import org.springframework.stereotype.Repository;

@Repository
public class EventServiceManageDao extends BaseDao<EventServiceManage> {

    public String getByKnowRangeId(int knowledgeRangeId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" b.* from knowledge_range_info a ");
        sql.append(" INNER join event_service_manage b  ");
        sql.append(" on a.range_type='3' and a.fk_id=b.id ");
        sql.append(" where a.knowledge_range_id= "+knowledgeRangeId);
        sql.append(" and b.is_del='0' ");
        return sql.toString();
    }

}
