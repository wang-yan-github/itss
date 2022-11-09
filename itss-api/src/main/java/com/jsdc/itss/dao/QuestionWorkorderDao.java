package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.QuestionWorkorder;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-15 10:40:27
 */
@Repository
public class QuestionWorkorderDao extends BaseDao<QuestionWorkorder> {

    public String toList(QuestionWorkorder beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("question_workorder");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
