package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.ChangeMajorApprove;
import org.springframework.stereotype.Repository;

@Repository
public class ChangeMajorApproveDao extends BaseDao<ChangeMajorApprove> {

    public String getChangeMajorApproveList (Integer change_type_id){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT change_major_approve.id, change_major_approve.approve_user,change_major_approve.sort, ");
        sql.append(" sys_user.name approve_name FROM change_major_approve ");
        sql.append(" INNER JOIN sys_user ON change_major_approve.approve_user  = sys_user.id  ");
        sql.append(" WHERE change_major_approve.is_del = '0' ");
        sql.append(" AND sys_user.is_del = '0 '");
        sql.append(" AND change_major_approve.change_type_id = "+change_type_id);
        sql.append(" ORDER BY change_major_approve.sort asc ");
        return sql.toString();
    }
}
