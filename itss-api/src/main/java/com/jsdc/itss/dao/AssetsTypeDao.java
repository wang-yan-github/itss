package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsType;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-08 14:15:25
 */
@Repository
public class AssetsTypeDao extends BaseDao<AssetsType> {

    public String toList(AssetsType beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_type");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
