package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SlaQuestionConfig;
import com.jsdc.itss.vo.SlaQuestionVo;
import org.springframework.stereotype.Repository;

/**
 * ClassName: SlaQuestionConfigDao
 * Description:
 * date: 2022/3/8 11:04
 *
 * @author bn
 */
@Repository
public class SlaQuestionConfigDao extends BaseDao<SlaQuestionConfig> {

    public String getSlaQuestion(SlaQuestionVo slaQuestionVo){
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT sqc.id,sq.sla_question_name,sqf.id first_id,");
        sql.append("sqf.first_name,sqc.solve_time FROM sla_question_first sqf ");
        sql.append("LEFT JOIN sla_question_config sqc ON sqf.id = sqc.first_id AND sqc.is_del='0' ");
        if (notEmpty(slaQuestionVo.getId())){
            sql.append(" AND sqc.question_id= "+slaQuestionVo.getId());
        }
        sql.append(" LEFT JOIN sla_question sq ON sqc.question_id=sq.id AND sq.is_del='0' ");
        sql.append(" where sqf.is_del='0' AND sqf.is_use='1' ");
        return sql.toString();
    }

    public String getSlaQuestionConf(SlaQuestionVo slaQuestionVo){
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT sqc.id,sq.sla_question_name,sqf.id first_id,");
        sql.append("IFNULL(sqf.first_name, '-全部-') as first_name,sqc.solve_time FROM sla_question_config sqc ");
        sql.append("LEFT JOIN sla_question_first sqf ON sqf.id = sqc.first_id AND sqf.is_del='0' ");
        sql.append(" LEFT JOIN sla_question sq ON sqc.question_id=sq.id AND sq.is_del='0' ");
        sql.append(" where sqc.is_del='0' ");
        if (notEmpty(slaQuestionVo.getId())){
            sql.append(" AND sqc.question_id= "+slaQuestionVo.getId());
        }
        return sql.toString();
    }
}
