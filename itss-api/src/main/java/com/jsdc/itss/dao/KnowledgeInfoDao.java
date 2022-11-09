package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Repository
public class KnowledgeInfoDao extends BaseDao<KnowledgeInfo> {


    public  String getStatusKnowledgeByPage(KnowledgeInfo knowledgeInfo ){
        StringBuffer sql = new StringBuffer() ;
        sql.append(" SELECT knowledge_info.id,knowledge_info.title ,sys_department.name departmentName,knowledge_category.`name` categoryName,knowledge_info.knowledge_category_id, ");
        sql.append(" sys_user.name createUserName,knowledge_info.approver_id,knowledge_scoring.score , ");
        sql.append(" knowledge_info.create_time,knowledge_info.`status`,knowledge_info.is_full_search, ");
        sql.append(" knowledge_info.knowLedge_status,knowledge_info.sort_num FROM knowledge_info ");
        sql.append(" LEFT JOIN  sys_department ON knowledge_info.dept_id = sys_department.id ");
        sql.append(" LEFT JOIN  knowledge_category ON knowledge_info.knowledge_category_id = knowledge_category.id ");
        sql.append(" LEFT JOIN  sys_user ON sys_user.id = knowledge_info.create_user ");
        sql.append(" LEFT JOIN knowledge_scoring ON knowledge_info.id = knowledge_scoring.knowledge_info_id ");
        sql.append(" WHERE knowledge_info.is_del = '0' ");


        if (knowledgeInfo.getKnowledge_category_id() != null && !knowledgeInfo.getKnowledge_category_id().equals("")){
            sql.append( " AND knowledge_info.knowledge_category_id = "+knowledgeInfo.getKnowledge_category_id());
        }
        if (knowledgeInfo.getStatus() != null && knowledgeInfo.getStatus() != "-1" &&  !knowledgeInfo.getStatus().equals("")){
            sql.append( " AND knowledge_info.status = "+knowledgeInfo.getStatus());
        }
        if (StringUtils.isNotBlank(knowledgeInfo.getStartTime())){
            sql.append(" AND knowledge_info.create_time >= '"+ DateUtils.formatTime(knowledgeInfo.getStartTime())+" 00:00:00' " );
        }
        if (StringUtils.isNotBlank(knowledgeInfo.getEndTime())){
            sql.append( " AND knowledge_info.create_time <= '"+DateUtils.formatTime(knowledgeInfo.getEndTime())+" 23:59:59' " );
        }
        if (knowledgeInfo.getKeyword() != null && !knowledgeInfo.getKeyword().equals("")){
            sql.append(" AND knowledge_info.keyword like CONCAT ('%','"+knowledgeInfo.getKeyword()+"','%') ");
        }
        if (StringUtils.isNotBlank(knowledgeInfo.getTitle())){
            sql.append(" AND knowledge_info.title like CONCAT ('%','"+knowledgeInfo.getTitle()+"','%') ");
        }

        if (knowledgeInfo.getDept_id() != null && !knowledgeInfo.getDept_id().equals("")){
            sql.append(" AND knowledge_info.dept_id = "+knowledgeInfo.getDept_id());
        }
        if (knowledgeInfo.getKnowledge_status() != null && !knowledgeInfo.getKnowledge_status().equals("") ){
            sql.append( " AND knowledge_info.knowledge_status = "+knowledgeInfo.getKnowledge_status());
        }
        if (knowledgeInfo.getCreate_user() != null && !knowledgeInfo.getCreate_user().equals("")){
            sql.append( " AND knowledge_info.create_user = "+knowledgeInfo.getCreate_user());
        }

        if (knowledgeInfo.getIs_recommend() != null && !knowledgeInfo.getIs_recommend().equals("")){
            sql.append( " AND knowledge_info.is_recommend = " +knowledgeInfo.getIs_recommend() );
        }

        sql.append(" order by knowledge_info.create_time desc");
        return  sql.toString();

    }

    public String selectKnowledgeByPage (KnowledgeInfo knowledgeInfo,int userId){
        StringBuffer sql = new StringBuffer() ;
        sql.append(" SELECT knowledge_info.id,knowledge_info.title ,sys_department.name departmentName,knowledge_category.`name` categoryName,knowledge_info.knowledge_category_id, ");
        sql.append(" sys_user.name createUserName,knowledge_info.approver_id,knowledge_scoring.score , ");
        sql.append(" knowledge_info.create_time,knowledge_info.`status`,knowledge_info.is_full_search, ");
        sql.append(" knowledge_info.knowLedge_status,knowledge_info.sort_num FROM knowledge_info ");
        sql.append(" LEFT JOIN sys_department ON knowledge_info.dept_id = sys_department.id ");
        sql.append(" LEFT JOIN knowledge_category ON knowledge_info.knowledge_category_id = knowledge_category.id ");
        sql.append(" LEFT JOIN sys_user ON sys_user.id = knowledge_info.create_user ");
        sql.append(" LEFT JOIN knowledge_scoring ON knowledge_info.id = knowledge_scoring.knowledge_info_id ");
        sql.append(" WHERE knowledge_info.is_del = '0' ");
        if (userId != -1 ){
            sql.append(" AND knowledge_info.create_user = "+userId);
        }
        if (knowledgeInfo != null){
            if (knowledgeInfo.getDept_id() != null && !knowledgeInfo.getDept_id().equals("") ){
                sql.append(" AND knowledge_info.dept_id = "+knowledgeInfo.getDept_id());
            }
            if (knowledgeInfo.getKnowledge_category_id() != null && !knowledgeInfo.getKnowledge_category_id().equals("")){
                sql.append( " AND knowledge_info.knowledge_category_id = "+knowledgeInfo.getKnowledge_category_id());
            }
            if (knowledgeInfo.getStatus() != null && !knowledgeInfo.getStatus().equals("")){
                sql.append( " AND knowledge_info.status = "+knowledgeInfo.getStatus());
            }
            if (knowledgeInfo.getKnowledge_status() != null && !knowledgeInfo.getKnowledge_status().equals("")){
                sql.append( " AND knowledge_info.knowledge_status = "+knowledgeInfo.getKnowledge_status());
            }
            if (knowledgeInfo.getCreate_user() != null && !knowledgeInfo.getCreate_user().equals("")){
                sql.append( " AND knowledge_info.create_user = "+knowledgeInfo.getCreate_user());
            }
            if (knowledgeInfo.getIs_recommend() != null  && !knowledgeInfo.getIs_recommend().equals("")){
                sql.append( " AND knowledge_info.is_recommend = '" +knowledgeInfo.getIs_recommend()+"'" );
            }
            if (knowledgeInfo.getKeyword() != null && !knowledgeInfo.getKeyword().equals("")){
                sql.append(" AND knowledge_info.keyword like CONCAT ('%','"+knowledgeInfo.getKeyword()+"','%') ");
            }
            if (StringUtils.isNotBlank(knowledgeInfo.getStartTime())){
                sql.append(" AND knowledge_info.create_time > '"+DateUtils.formatTime(knowledgeInfo.getStartTime())+" 00:00:00' " );
            }
            if (StringUtils.isNotBlank(knowledgeInfo.getEndTime())){
                sql.append( " AND knowledge_info.create_time < '"+DateUtils.formatTime(knowledgeInfo.getEndTime())+" 23:59:59' " );
            }
            if (StringUtils.isNotBlank(knowledgeInfo.getTitle())){
                sql.append(" AND knowledge_info.title like CONCAT ('%','"+knowledgeInfo.getTitle()+"','%') ");
            }

        }
        sql.append(" order by knowledge_info.create_time desc");

        return sql.toString();
    }

    public String getKnowledgeById(int id){
        StringBuffer sql =new StringBuffer();
        sql.append(" SELECT knowledge_info.id,knowledge_info.title ,knowledge_info.content,sys_department.name departmentName,knowledge_category.`name` categoryName,knowledge_info.is_recommend,knowledge_info.knowledge_range_id, ");
        sql.append(" knowledge_info.keyword,knowledge_info.update_user,knowledge_info.update_time,knowledge_info.knowledge_category_id,knowledge_info.approval_opinions, ");
        sql.append(" knowledge_info.approval_time,knowledge_scoring.rater,knowledge_scoring.scoring_time,knowledge_scoring.`comment`, ");
        sql.append(" sys_user.name createUserName,knowledge_info.approver_id,knowledge_scoring.score , ");
        sql.append(" knowledge_info.create_time,knowledge_info.`status`,knowledge_info.is_full_search, ");
        sql.append(" knowledge_info.knowLedge_status,knowledge_info.sort_num FROM knowledge_info ");
        sql.append(" LEFT JOIN sys_department ON knowledge_info.dept_id = sys_department.id ");
        sql.append(" LEFT JOIN knowledge_category ON knowledge_info.knowledge_category_id = knowledge_category.id ");
        sql.append(" LEFT JOIN sys_user ON sys_user.id = knowledge_info.create_user ");
        sql.append(" LEFT JOIN knowledge_scoring ON knowledge_info.id = knowledge_scoring.knowledge_info_id ");
        sql.append(" WHERE knowledge_info.is_del = '0' ");
        sql.append(" AND knowledge_info.id = "+ id);
        return sql.toString();
    }



    /**
    * 贡献分统计
    * Author wzn
    * Date 2022/11/3 17:31
    */
    public String  contributionScoreStatistics(){
        String sql = "SELECT\n" +
                "\tsu.name addPeople,\n" +
                "\tcount(1) knowledgeOfTheNumber,\n" +
                "\tSUM(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN ki.STATUS = '2' THEN\n" +
                "\t\t\t1\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) quantityReleased,\n" +
                "\tsum(ks.score) publishContributionPoints,\n" +
                "\tSUM(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN ks.score IS NOT NULL THEN\n" +
                "\t\t\tks.score\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) / SUM(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN ks.score IS NOT NULL THEN\n" +
                "\t\t\t1\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) releaseAverageScore\n" +
                "FROM\n" +
                "\tknowledge_info ki\n" +
                "LEFT JOIN knowledge_scoring ks ON ks.knowledge_info_id = ki.id\n" +
                "left join sys_user su on su.id = ki.create_user\n" +
                "where ki.is_del = '0'\n" +
                "GROUP BY\n" +
                "\tki.create_user\n" ;
        return sql ; 
    }



    /**
    *知识部门统计
    * Author wzn
    * Date 2022/11/4 9:47
    */
    public String  departmentOfKnowledge(){
        String sql = "SELECT\n" +
                "\tsd.name addPeople ,\n" +
                "\tcount(1) knowledgeOfTheNumber,\n" +
                "\tSUM(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN ki.STATUS = '2' THEN\n" +
                "\t\t\t1\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) quantityReleased,\n" +
                "\tsum(ks.score) publishContributionPoints,\n" +
                "\tSUM(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN ks.score IS NOT NULL THEN\n" +
                "\t\t\tks.score\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) / SUM(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN ks.score IS NOT NULL THEN\n" +
                "\t\t\t1\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) releaseAverageScore\n" +
                "FROM\n" +
                "\tknowledge_info ki\n" +
                "LEFT JOIN knowledge_scoring ks ON ks.knowledge_info_id = ki.id\n" +
                "left join sys_department sd on sd.id = ki.dept_id\n" +
                "where ki.is_del = '0'\n" +
                "GROUP BY\n" +
                "\tki.dept_id" ;
        return sql ;
    }




}
