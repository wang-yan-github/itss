package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SlaQuestion;
import org.springframework.stereotype.Repository;

/**
 * ClassName: SlaQuestionDao
 * Description:
 * date: 2022/3/7 14:34
 *
 * @author bn
 */
@Repository
public class SlaQuestionDao extends BaseDao<SlaQuestion> {

    public String getSlaQuestion(SlaQuestion slaQuestion) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from sla_question where is_del='0' ");
        if (null != slaQuestion.getId()) {
            sql.append(" AND id=" + slaQuestion.getId());
        }
        if (notEmpty(slaQuestion.getSla_question_name())) {
            sql.append(" AND sla_question_name ='" + slaQuestion.getSla_question_name() + "'");
        }
        return sql.toString();
    }

    public String getSlaQuestionByCategoryId(Integer questionCategoryId){
        String sql = "select a.id as questionCategoryId,c.* from question_category a\n" +
                "INNER JOIN assets_customer_contract b\n" +
                "on a.customer_contract_id = b.id\n" +
                "and a.is_use = '1'\n" +
                "and a.is_del = '0'\n" +
                "and b.is_del = '0'\n" +
                "INNER JOIN sla_question c\n" +
                "on b.sla_question_id = c.id\n" +
                "where c.is_del='0'\n" +
                "and c.is_use='1'";
        if(questionCategoryId != null){
            sql += " and a.id="+questionCategoryId;
        }
        return sql;
    }
}
