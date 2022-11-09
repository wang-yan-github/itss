package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventSatisfaction;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-22 10:30:09
 */
@Repository
public class EventSatisfactionDao extends BaseDao<EventSatisfaction> {

    public String toList(EventSatisfaction beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("event_satisfaction");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
