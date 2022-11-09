package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.QuestionReviewer;
import org.springframework.stereotype.Repository;

/**
 * 问题审核人关联表
 *
 * @Author thr
 * @create 2022-03-24 14:21:12
 */
@Repository
public class QuestionReviewerDao extends BaseDao<QuestionReviewer> {

    public String toList(QuestionReviewer bean) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT t.*,u.name review_user_name FROM question_reviewer t");
        sql.append(" left join sys_user u on t.review_user = u.id");
        sql.append(" where t.is_del=0 ");
        if (notEmpty(bean)) {
            if (notEmpty(bean.getTitle())) {
                sql.append(" and t.title = '").append(bean.getTitle()).append("'");
            }
            if (notEmpty(bean.getQuestion_id())) {
                sql.append(" and t.question_id = ").append(bean.getQuestion_id());
            }
        }
        sql.append(" order by sort,id ");
        return sql.toString();
    }

    /**
     * 根据问题id删除 问题关联审核人
     *
     * @param bean
     * @return
     */
    public String delByQuestionId(QuestionReviewer bean) {
        String sql = "delete FROM question_reviewer where question_id = " + bean.getQuestion_id();
        return sql;
    }
}
