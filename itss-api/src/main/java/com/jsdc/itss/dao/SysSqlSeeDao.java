package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysSqlSee;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysSqlSeeDao extends BaseDao<SysSqlSee> {

    public String toList(SysSqlSee beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_sql_see");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
