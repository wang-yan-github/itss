package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsConfigReqWorkflow;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsConfigReqWorkflowDao extends BaseDao<AssetsConfigReqWorkflow> {

    public String toList(AssetsConfigReqWorkflow beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_config_req_workflow");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
