package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.ChangeUrgentApprove;
import org.springframework.stereotype.Repository;

@Repository
public class ChangeUrgentApproveDao extends BaseDao<ChangeUrgentApprove> {

    public  String  getChangeUrgentApproveList(Integer change_type_id){
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT change_urgent_approve.id, change_urgent_approve.approve_user,change_urgent_approve.sort, ");
        sql.append(" sys_user.name approve_name FROM change_urgent_approve ");
        sql.append(" INNER JOIN sys_user ON change_urgent_approve.approve_user  = sys_user.id ");
        sql.append(" WHERE change_urgent_approve.is_del = '0' ");
        sql.append(" AND sys_user.is_del = '0' ");
        sql.append(" AND change_urgent_approve.change_type_id = "+change_type_id);
        sql.append(" ORDER BY change_urgent_approve.sort asc ");
        return sql.toString();
    }
}
