package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.model.SlaEvent;
import org.springframework.stereotype.Repository;

/**
 * ClassName: SlaEventDao
 * Description:
 * date: 2022/3/7 14:52
 *
 * @author bn
 */
@Repository
public class SlaEventDao extends BaseDao<SlaEvent> {


    public String getSlaEvent(SlaEvent slaEvent) {
        StringBuilder sql = new StringBuilder();

        sql.append("select * from sla_event where is_del='0' ");

        if (null != slaEvent.getId()) {
            sql.append(" AND id=" + slaEvent.getId());
        }

        if (StringUtils.isNotEmpty(slaEvent.getSla_event_name())) {
            sql.append(" AND sla_event_name ='" + slaEvent.getSla_event_name() + "'");
        }

        return sql.toString();

    }

    public String getSlaEventFirstAndRequest(String is_first, String is_request) {
        StringBuilder sql = new StringBuilder();
        if(is_first.equals("1") && is_request.equals("1")){
            sql.append("select sef.id as first_id,sef.first_name,ert.id as request_type_id,ert.request_type_name ");
            sql.append(" from sla_event_first sef INNER JOIN event_request_type ert ");
            sql.append(" where sef.is_del='0' and ert.is_del='0' and sef.is_use='1' and ert.is_use='1' ");
        }else if(is_first.equals("1") && is_request.equals("0")){
            sql.append("select sef.id as first_id,sef.first_name,'-全部-' as request_type_name ");
            sql.append(" from sla_event_first sef where sef.is_del='0' and sef.is_use='1' ");
        }else if(is_first.equals("0") && is_request.equals("1")){
            sql.append("select ert.id as request_type_id,ert.request_type_name,'-全部-' as first_name ");
            sql.append("from event_request_type ert where ert.is_del='0' and ert.is_use='1' ");
        }
        return sql.toString();
    }

    public String getSlaByEventCategory(Integer eventCategoryId){
        String sql="select a.id eventCategoryId,c.* from event_category a\n" +
                "left join assets_customer_contract b\n" +
                "on a.customer_contract_id = b.id\n" +
                "left join sla_event c\n" +
                "on b.sla_id = c.id";
            if(eventCategoryId != null){
                sql+=" where a.id="+eventCategoryId;
            }
        return sql;
    }

}
