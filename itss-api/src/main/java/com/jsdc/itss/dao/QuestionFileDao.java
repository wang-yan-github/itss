package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.QuestionFile;
import org.springframework.stereotype.Repository;

/**
 * 问题管理 附件
 *
 * @Author thr
 * @create 2022-04-14 14:12:37
 */
@Repository
public class QuestionFileDao extends BaseDao<QuestionFile> {

    public String toList(QuestionFile beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * FROM question_file");
        sql.append(" where is_del = 0 ");
        return sql.toString();
    }

    public String getFileManageByType(int questionId, int type) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT fm.* FROM question_file t ");
        sql.append(" LEFT JOIN file_manage fm ON t.file_id = fm.id  ");
        sql.append(" where t.is_del = 0 and fm.is_del = 0 ");
        sql.append(" and t.type = ").append(type);
        sql.append(" and t.question_id = ").append(questionId);
        return sql.toString();
    }
}
