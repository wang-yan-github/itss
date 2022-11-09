package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.PublishChanges;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Repository
public class PublishChangesDao extends BaseDao<PublishChanges> {

    public String toList(PublishChanges beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("publish_changes");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
