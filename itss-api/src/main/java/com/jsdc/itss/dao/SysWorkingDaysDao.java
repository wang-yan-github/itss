package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysWorkingDays;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysWorkingDaysDao extends BaseDao<SysWorkingDays> {

    public String toList(SysWorkingDays beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_working_days");
        sql.append(" where is_del=0 ");
        if(StringUtils.isNotEmpty(beanParam.getWork_name())){
            sql.append(" and work_name like '%" + beanParam.getWork_name() + "%' ");
        }
        if(StringUtils.isNotEmpty( beanParam.getIs_use())){
            sql.append(" and is_use=" + beanParam.getIs_use());
        }
        if(StringUtils.isNotEmpty( beanParam.getAcquiesce())){
            sql.append(" and acquiesce=" + beanParam.getAcquiesce());
        }

        return sql.toString();
    }
}
