package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysWorkingDaysDistinguish;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysWorkingDaysDistinguishDao extends BaseDao<SysWorkingDaysDistinguish> {

    public String toList(SysWorkingDaysDistinguish beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_working_days_distinguish");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
