package com.jsdc.itss.dao;

import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.Question;
import com.jsdc.itss.utils.DateUtils;
import com.jsdc.itss.vo.QuestionVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-15 10:16:00
 */
@Repository
public class QuestionDao extends BaseDao<Question> {

    /**
     * 分页查询
     *
     * @param beanParam
     * @return
     */
    public String toList(QuestionVo beanParam) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT q.id, ");
        sql.append(" q.CODE, q.title, q.create_user , u.name create_user_name, q.create_time, q.category_id, qc.`name` as category_name, ");
        sql.append(" q.hope_solve_time, q.priority_id, sqf.first_name as priority_name, q.`status`, d.label as status_name, q.now_operator_user, ");
        sql.append(" q.solve_time,q.sla_exceed_time,q.sla_time,q.sla_exceed_time,q.isFitSla, ");
        sql.append(" u2.name as now_operator_user_name ");
        sql.append(" FROM ").append(" `question` q ");
        sql.append(" LEFT JOIN sys_user u ON q.create_user = u.id left join question_category qc on qc.id = q.category_id ");
        sql.append(" LEFT JOIN sys_user u2 ON q.now_operator_user = u2.id left join sla_question_first sqf on sqf.id = q.priority_id ");
        sql.append(" LEFT JOIN sys_user solveUser ON q.solve_user = solveUser.id ");
        sql.append(" LEFT JOIN  (SELECT review_user FROM question_reviewer qr,question where qr.question_id = question.id GROUP BY qr.review_user) reviewUser on reviewUser.review_user=u.id ");
        sql.append(" LEFT JOIN sys_user reviewUser2 ON reviewUser.review_user = reviewUser2.id ");
        sql.append(" LEFT JOIN sys_user clearanceUser ON clearanceUser.id = q.clearance_user ");
        sql.append(" LEFT JOIN sys_dict d ON d.value = q.`status` and d.dict_type = 'question_status' ");

        sql.append(" where q.is_del=0 ");

        if (Base.notEmpty(beanParam)) {
            if (notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0) {
                sql.append(" and q.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
            }
            if (StringUtils.isNotBlank(beanParam.getTitle())) {
                sql.append(" and q.title like '%" + beanParam.getTitle() + "%' ");
            }
             if (StringUtils.isNotBlank(beanParam.getCreate_start_time())) {
                sql.append(" and date_format(q.create_time,'%Y-%m-%d') >= '" + DateUtils.formatTime(beanParam.getCreate_start_time()) + "' ");
            }
             if (StringUtils.isNotBlank(beanParam.getCreate_end_time())) {
                sql.append(" and date_format(q.create_time,'%Y-%m-%d') <= '" + DateUtils.formatTime(beanParam.getCreate_end_time()) + "' ");
            }

            if (Base.notEmpty(beanParam.getCategory_id())) {
                sql.append(" and q.category_id = " + beanParam.getCategory_id());
            }
            if (Base.notEmpty(beanParam.getPriority_id())) {
                sql.append(" and q.priority_id = " + beanParam.getPriority_id());
            }
            if (Base.notEmpty(beanParam.getStatus())) {
                sql.append(" and q.`status` = '" + beanParam.getStatus() + "' ");
            }
            if (Base.notEmpty(beanParam.getSolve_user_name())) {
                sql.append(" AND solveUser.name LIKE '%" + beanParam.getSolve_user_name() + "%' ");
            }
//            if (Base.notEmpty(beanParam.getAudit_user_name())) {
//                sql.append(" AND reviewUser2.name LIKE '%" + beanParam.getAudit_user_name() + "%' ");
//            }
            if (Base.notEmpty(beanParam.getClearance_user_name())) {
                sql.append(" AND clearanceUser.name LIKE '%" + beanParam.getClearance_user_name() + "%' ");
            }
            if (StringUtils.isNotBlank(beanParam.getClearance_start_time())) {
                sql.append(" and  date_format(q.clearance_time,'%Y-%m-%d') >= '" + DateUtils.formatTime(beanParam.getClearance_start_time()) + "' ");
            }
            if (StringUtils.isNotBlank(beanParam.getClearance_end_time())) {
                sql.append(" and  date_format(q.clearance_time,'%Y-%m-%d') <= '" + DateUtils.formatTime(beanParam.getClearance_end_time()) + "' ");
            }
            if (Base.notEmpty(beanParam.getCreate_user())) {
                //我的问题 列出所有开单人是当前人的问题单，按照标号的降序排列。
                sql.append(" and q.create_user = " + beanParam.getCreate_user());
            }
            if (Base.notEmpty(beanParam.getNow_operator_user())) {
                sql.append(" and q.now_operator_user = " + beanParam.getNow_operator_user());
            }
            if (Base.notEmpty(beanParam.getNow_operator_user_name())) {
                sql.append(" and u2.NAME  LIKE '%" + beanParam.getNow_operator_user_name() + "%' ");
            }
            if (Base.notEmpty(beanParam.getDataStatus())) {
                //列表状态
                if (beanParam.getDataStatus().equals("db")) {
                    //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
                    //问题待办列表 1待鉴定 2待审核 3待解决 8审核拒绝 9挂起
                    sql.append(" and q.`status` in ('1','2','3','8','9')");
                    //列出待我处理的问题列表 （当前操作人）
                    sql.append(" and ( q.now_operator_user = " + beanParam.getNow_operator_user() + ")");
                } else if (beanParam.getDataStatus().equals("cyd")) {
                    //列出我参与的问题列表 鉴定人、处理人、解决人、关单人、当前操作人、开单人、审核人
                    sql.append(" and ( q.appraisal_user = " + beanParam.getAppraisal_user() + " or q.handle_user = " + beanParam.getAppraisal_user()
                            + " or q.solve_user = " + beanParam.getAppraisal_user() + " or q.clearance_user = " + beanParam.getAppraisal_user()
                            + " or q.now_operator_user = " + beanParam.getAppraisal_user() + " or q.create_user = " + beanParam.getAppraisal_user()
                            + " or reviewUser2.id = " + beanParam.getAppraisal_user() + ")");
                }
            }
        }
        sql.append(" order by q.create_time desc");
        return sql.toString();
    }

    public String toExportList() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT q.*, ");
        sql.append(" qs.`name` as source_name,sqf.first_name as priority_name,qc.`name` as category_name ");
        sql.append(" FROM ").append(" `question` q ");
        sql.append(" left join question_source qs on q.source_id = qs.id ");
        sql.append(" left join sla_question_first sqf on q.priority_id = sqf.id ");
        sql.append(" left join question_category qc on q.category_id = qc.id ");
        sql.append(" where q.is_del=0 ");
        sql.append(" order by q.create_time desc");
        return sql.toString();
    }

    /**
     * 问题统计
     */
    public String questionStatistics(Question question) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * from question  ");
        if (Base.notEmpty(question)) {
            if (Base.notEmpty(question.getReview_user())) {
                sql.append(" inner JOIN question_reviewer on question.id = question_reviewer.question_id ");
            }
        }
        sql.append(" where question.is_del = '0' ");
        questionWhere(question,sql);
        sql.append(" order by question.create_time desc");
        return sql.toString();

    }

    /**
     * 是否生成知识统计
     * @param question
     * @return
     */
    public String knowledge(Question question) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT question.category_id,qc.name category_name,count(1) num from question  ");
        if (Base.notEmpty(question)) {
            if (Base.notEmpty(question.getReview_user())) {
                sql.append(" inner JOIN question_reviewer on question.id = question_reviewer.question_id ");
            }
            sql.append(" left join question_category qc on question.category_id = qc.id ");
        }
        sql.append(" where question.is_knowledge = 1 and question.is_del = '0' ");
        questionWhere(question,sql);
        sql.append(" group by question.category_id");
        return sql.toString();
    }

    public String isFitSla(Question question) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT question.isFitSla, count(1) num from question  ");
        if (Base.notEmpty(question)) {
            if (Base.notEmpty(question.getReview_user())) {
                sql.append(" inner JOIN question_reviewer on question.id = question_reviewer.question_id ");
            }
            sql.append(" left join question_category qc on question.category_id = qc.id ");
        }
        sql.append(" where question.is_del = '0' and question.isFitSla is not null ");
        questionWhere(question,sql);
        sql.append(" group by question.isFitSla");
        return sql.toString();
    }

    public String status(Question question) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT question.status, count(1) num from question  ");
        if (Base.notEmpty(question)) {
            if (Base.notEmpty(question.getReview_user())) {
                sql.append(" inner JOIN question_reviewer on question.id = question_reviewer.question_id ");
            }
            sql.append(" left join question_category qc on question.category_id = qc.id ");
        }
        sql.append(" where question.is_del = '0' and question.status is not null ");
        questionWhere(question,sql);
        sql.append(" group by question.status");
        return sql.toString();
    }

    public void questionWhere(Question question, StringBuilder sql) {
        if (Base.notEmpty(question)) {
            if (Base.notEmpty(question.getCategory_id())) {
                sql.append(" and question.category_id = " + question.getCategory_id());
            }
            if (Base.notEmpty(question.getPriority_id())) {
                sql.append(" and question.priority_id = " + question.getPriority_id());
            }
            if (Base.notEmpty(question.getSolve_user())) {
                sql.append(" and question.solve_user = " + question.getSolve_user());
            }
            if (Base.notEmpty(question.getReview_user())) {
                sql.append(" and question_reviewer.review_user = " + question.getReview_user());
            }
            if (Base.notEmpty(question.getClearance_user())) {
                sql.append(" and question.clearance_user = " + question.getClearance_user());
            }
            if (Base.notEmpty(question.getStatus())) {
                sql.append(" and question.status = " + question.getStatus());
            }
            if (Base.notEmpty(question.getCreate_time_start())) {
                sql.append(" and question.create_time >= '" + question.getCreate_time_start() + " 00:00:00' ");
            }
            if (Base.notEmpty(question.getCreate_time_end())) {
                sql.append(" and question.create_time <= '" + question.getCreate_time_end() + " 23:59:59' ");
            }
            if (Base.notEmpty(question.getClearance_time_start()) && Base.notEmpty(question.getClearance_time_end())) {
                sql.append(" and question.clearance_time > " + question.getClearance_time_start() + " and question.clearance_time < " + question.getClearance_time_end());
            } else if (Base.notEmpty(question.getClearance_time_start()) && Base.empty(question.getClearance_time_end())) {
                sql.append(" and question.clearance_time > " + question.getClearance_time_start());
            } else if (Base.empty(question.getClearance_time_start()) && Base.notEmpty(question.getClearance_time_end())) {
                sql.append(" and question.clearance_time < " + question.getClearance_time_end());
            }
        }
    }
}
