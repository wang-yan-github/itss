package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.PublishNodes;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Repository
public class PublishNodesDao extends BaseDao<PublishNodes> {

    public String toList(PublishNodes beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("publish_nodes");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
