package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.ChangeCommonlyApprove;
import org.springframework.stereotype.Repository;

@Repository
public class ChangeCommonlyApproveDao  extends BaseDao<ChangeCommonlyApprove> {

    public  String geteCommonlyApproveList ( Integer change_type_id ){
        StringBuilder sql= new StringBuilder();
        sql.append(" SELECT change_commonly_approve.id, change_commonly_approve.approve_user,change_commonly_approve.sort, ");
        sql.append(" sys_user.name approve_name FROM change_commonly_approve ");
        sql.append(" INNER JOIN sys_user ON change_commonly_approve.approve_user  = sys_user.id ");
        sql.append(" WHERE change_commonly_approve.is_del = '0' ");
        sql.append(" AND sys_user.is_del = '0' ");
        sql.append(" AND change_commonly_approve.change_type_id = "+change_type_id);
        sql.append(" ORDER BY change_commonly_approve.sort asc  ");
        return sql.toString();
    }
}
