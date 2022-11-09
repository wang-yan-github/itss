package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventQuestionRelation;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-17 11:41:40
 */
@Repository
public class EventQuestionRelationDao extends BaseDao<EventQuestionRelation> {

    public String toList(EventQuestionRelation beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("event_question_relation");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
