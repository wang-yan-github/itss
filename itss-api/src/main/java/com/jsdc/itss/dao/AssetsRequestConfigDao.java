package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsRequestConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Repository
public class AssetsRequestConfigDao extends BaseDao<AssetsRequestConfig> {

    public String toList(AssetsRequestConfig beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_request_config");
        sql.append(" where is_del=0 ");
        if(StringUtils.isNotBlank(beanParam.getRequest_name())){
            sql.append(" and request_name like '%" + beanParam.getRequest_name() + "%' ");
        }
        if(null != beanParam.getIs_enable()){
            sql.append(" and is_enable=" + beanParam.getIs_enable());
        }
        return sql.toString();
    }
}
