package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SlaEventConfig;
import com.jsdc.itss.vo.SlaEventVo;
import org.springframework.stereotype.Repository;

/**
 * ClassName: SlaEventConfigDao
 * Description:
 * date: 2022/3/8 9:51
 *
 * @author bn
 */
@Repository
public class SlaEventConfigDao extends BaseDao<SlaEventConfig> {

    public String getSlaEventConfig(SlaEventVo slaEventVo){
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT sec.id ,sec.request_type_id,sec.response_time,sec.first_id, ");
        sql.append("sec.solve_time,temp.request_type_name,temp.first_name FROM ( ");
        sql.append("SELECT ert.id request_type_id,ert.request_type_name,sef.id first_id, ");
        sql.append("sef.first_name FROM (SELECT 1) AS t0  ");
        sql.append("LEFT JOIN sla_event_first sef ON 1 = 1 AND sef.is_del='0' AND sef.is_use='1' ");
        sql.append("LEFT JOIN event_request_type ert ON 1 = 1 AND ert.is_del='0' AND ert.is_use='1' ");
        sql.append(") temp LEFT JOIN sla_event_config sec ON temp.request_type_id = sec.request_type_id ");
        sql.append("AND temp.first_id = sec.first_id AND sec.is_del='0' ");
        if (notEmpty(slaEventVo.getId())){
            sql.append("AND sec.sla_event_id="+slaEventVo.getId());
        }
        sql.append("LEFT JOIN sla_event se ON sec.sla_event_id=se.id AND se.is_del='0' ");
        return sql.toString();
    }

    public String getSlaEventConfigVos(SlaEventVo slaEventVo){
        StringBuilder sql=new StringBuilder();
        sql.append("select sec.id,sec.first_id,IFNULL(sef.first_name,'-全部-') as first_name,sec.request_type_id, ");
        sql.append(" IFNULL(ert.request_type_name,'-全部-') as request_type_name,sec.response_time,sec.solve_time ");
        sql.append(" from sla_event_config sec left join sla_event_first sef on sec.first_id=sef.id  ");
        sql.append(" left join event_request_type ert on sec.request_type_id=ert.id ");
        sql.append(" where sec.is_del='0'");
        if (notEmpty(slaEventVo.getId())){
            sql.append("AND sec.sla_event_id="+slaEventVo.getId());
        }
        return sql.toString();
    }
}
