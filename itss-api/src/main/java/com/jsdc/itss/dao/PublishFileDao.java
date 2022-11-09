package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.PublishFile;
import org.springframework.stereotype.Repository;

/**
 * @Author thr
 * @create 2022-06-27 13:44:00
 */
@Repository
public class PublishFileDao extends BaseDao<PublishFile> {

    public String toList(PublishFile beanParam){
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * FROM publish_file");
        sql.append(" where is_del = 0 ");
        return sql.toString();
    }
}
