package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysDocument;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysDocumentDao extends BaseDao<SysDocument> {

    public String toList(SysDocument beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_document");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
