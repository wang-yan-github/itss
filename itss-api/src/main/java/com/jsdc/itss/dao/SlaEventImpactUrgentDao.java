package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SlaEventImpactUrgent;
import org.springframework.stereotype.Repository;

/**
 * ClassName: SlaEventImpactUrgentDao
 * Description:
 * date: 2022/3/7 14:39
 *
 * @author bn
 */
@Repository
public class SlaEventImpactUrgentDao extends BaseDao<SlaEventImpactUrgent> {


//    public String toList(SlaEventImpactUrgent slaEventImpactUrgent){
//        StringBuilder sql=new StringBuilder();
//
//        sql.append("SELECT seiu.id,temp.*, sef.first_name,sef.id first_id ");
//        sql.append("FROM(SELECT sei.id impact_id,sei.impact_name,seu.id urgent_id, ");
//        sql.append("seu.urgent_name FROM (select 1) as t0 LEFT JOIN sla_event_impact sei ON 1=1 LEFT JOIN sla_event_urgent seu ON 1=1 WHERE sei.is_del='0' AND seu.is_del='0' ");
//        sql.append(") temp LEFT JOIN sla_event_impact_urgent seiu ON temp.impact_id = seiu.impact_id ");
//        sql.append("AND temp.urgent_id = seiu.urgent_id ");
//        sql.append("LEFT JOIN sla_event_first sef ON seiu.first_id = sef.id ");
//        sql.append("where seiu.is_del='0' and sef.is_del='0' ");
//        return sql.toString();
//    }

    public String toList(SlaEventImpactUrgent slaEventImpactUrgent){
        StringBuilder sql=new StringBuilder();
        sql.append("select seiu.id,sei.impact_name,seu.urgent_name,seiu.first_id,sef.first_name");
        sql.append(" from sla_event_impact_urgent seiu left join sla_event_impact sei on seiu.impact_id= sei.id ");
        sql.append(" left join sla_event_urgent seu on seiu.urgent_id= seu.id ");
        sql.append(" left join sla_event_first sef on seiu.first_id= sef.id ");
        sql.append(" where seiu.is_del = '0' and sef.is_use = '1' ");
        if(notEmpty(slaEventImpactUrgent.getImpact_id())){
            sql.append(" and seiu.impact_id = '"+ slaEventImpactUrgent.getImpact_id() +"'");
        }
        if(notEmpty(slaEventImpactUrgent.getUrgent_id())){
            sql.append(" and seiu.urgent_id = '"+ slaEventImpactUrgent.getUrgent_id() +"'");
        }
        return sql.toString();
    }

}
