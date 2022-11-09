package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsChildren;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Repository
public class AssetsChildrenDao extends BaseDao<AssetsChildren> {

    public String toList(AssetsChildren beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_children");
        sql.append(" where is_del='0' ");
        if (beanParam.getAssets_type_id() != null){
            sql.append(" and assets_type_id ="+beanParam.getAssets_type_id());
        }
        if (StringUtils.isNotEmpty(beanParam.getName())){
            sql.append(" and name like '%" + beanParam.getName() + "%' ");
        }
        return sql.toString();
    }
}
