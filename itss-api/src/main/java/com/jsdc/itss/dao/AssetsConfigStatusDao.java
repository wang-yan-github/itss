package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsConfigStatus;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Repository
public class AssetsConfigStatusDao extends BaseDao<AssetsConfigStatus> {

    public String toList(AssetsConfigStatus beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_config_status");
        sql.append(" where is_del='0' ");
        if (beanParam.getAssets_id() != null){
            sql.append( " AND assets_id ="+beanParam.getAssets_id());
        }
        if (StringUtils.isNotEmpty(beanParam.getName())){
            sql.append(" and name like '%" + beanParam.getName() + "%' ");
        }
        return sql.toString();
    }
}
