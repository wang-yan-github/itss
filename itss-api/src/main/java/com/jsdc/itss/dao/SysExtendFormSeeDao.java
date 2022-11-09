package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysExtendFormSee;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysExtendFormSeeDao extends BaseDao<SysExtendFormSee> {

    public String toList(SysExtendFormSee beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_extend_form_see");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
