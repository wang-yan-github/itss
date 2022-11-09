package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsConfigReqProperty;
import org.springframework.stereotype.Repository;

/**
 * 配置请求资产中间程表
 *
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsConfigReqPropertyDao extends BaseDao<AssetsConfigReqProperty> {

    public String toList(AssetsConfigReqProperty beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_config_req_property");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }
}
