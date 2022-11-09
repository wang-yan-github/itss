package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.QuestionChange;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-15 13:26:30
 */
@Repository
public class QuestionChangeDao extends BaseDao<QuestionChange> {

    public String toList(QuestionChange beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("question_change");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
