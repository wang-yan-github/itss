package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysSql;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysSqlDao extends BaseDao<SysSql> {

    public String toList(SysSql beanParam) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ").append(" FROM ").append("sys_sql");
        sql.append(" where is_del = 0 ");
        if (notEmpty(beanParam)) {
            if (notEmpty(beanParam.getSql_name())) {
                sql.append(" and sql_name like '%" + beanParam.getSql_name() + "%'");
            }
            if (notEmpty(beanParam.getSql_content())) {
                sql.append(" and sql_content like '%" + beanParam.getSql_content() + "%'");
            }
            if (notEmpty(beanParam.getIs_use())) {
                sql.append(" and is_use ='" + beanParam.getIs_use() + "'");
            }
            if (notEmpty(beanParam.getDisplay_mode())) {
                sql.append(" and display_mode ='" + beanParam.getDisplay_mode() + "'");
            }
            if (notEmpty(beanParam.getTime_choose())) {
                sql.append(" and time_choose ='" + beanParam.getTime_choose() + "'");
            }
            if (notEmpty(beanParam.getScope())) {
                sql.append(" and scope ='" + beanParam.getScope() + "'");
            }
            if (notEmpty(beanParam.getModule())) {
                sql.append(" and module ='" + beanParam.getModule() + "'");
            }
        }
        return sql.toString();
    }

    //动态传递sql语句,进行查询操作
    public String parametSql(SysSql sysSql) {
        String sql = sysSql.getSql_content();
        if (notEmpty(sysSql.getTimeStart())) {
            if (sql.contains("timeStart")) {
                sql = sql.replaceAll("timeStart", sysSql.getTimeStart());
            }
        }
        if (notEmpty(sysSql.getTimeEnd())) {
            if (sql.contains("timeEnd")) {
                sql = sql.replaceAll("timeEnd", sysSql.getTimeEnd());
            }
        }
        if (empty(sysSql.getTimeStart()) && empty(sysSql.getTimeEnd())) {
            //默认当前日期
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            sql = sql.replaceAll("timeStart", formatter.format(date) + " 00:00:00 ")
                    .replaceAll("timeEnd", formatter.format(date) + " 23:59:59 ");
        }
        return sql;
    }
}
