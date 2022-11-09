package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysRegister;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysRegisterDao extends BaseDao<SysRegister> {

    public String toList(SysRegister beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_register");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
