package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysExtendForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysExtendFormDao extends BaseDao<SysExtendForm> {

    public String toList(SysExtendForm beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_extend_form");
        sql.append(" where is_del=0 ");
        if(null != beanParam){
            if(StringUtils.isNotBlank(beanParam.getForm_name())){
                sql.append(" and form_name like '%" + beanParam.getForm_name() + "%' ");
            }
        }
        return sql.toString();
    }
}
