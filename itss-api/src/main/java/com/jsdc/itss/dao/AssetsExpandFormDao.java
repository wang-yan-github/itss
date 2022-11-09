package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsExpandFormDao extends BaseDao<AssetsExpandForm> {

    public String toList(AssetsExpandForm beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_expand_form");
        sql.append(" where is_del= '0'");
        if ( StringUtils.isNotEmpty(beanParam.getName() )){
            sql.append( " and name like concat('%','"+beanParam.getName()+"','%' ");
        }
        return sql.toString();
    }
}
