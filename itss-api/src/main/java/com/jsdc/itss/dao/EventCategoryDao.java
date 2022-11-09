package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventCategory;
import org.springframework.stereotype.Repository;


@Repository
public class EventCategoryDao extends BaseDao<EventCategory> {


    public String eventExpenses(){
        String sql = "SELECT count(1),\n" +
                "       ec.NAME,\n" +
                "       customer_contract_id,\n" +
                "       SUM(expenses) expenses\n" +
                "FROM `event` e\n" +
                "LEFT JOIN event_category ec ON ec.id = e.event_category_id\n" +
                "WHERE e.is_del = 0\n" +
                "  AND ec.is_del = 0\n" +
                "GROUP BY ec.id";
        return sql;
    }

}
