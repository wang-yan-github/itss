package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsManageUser;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsManageUserDao extends BaseDao<AssetsManageUser> {

    public String toList(AssetsManageUser beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_manage_user");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
