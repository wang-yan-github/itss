package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.model.AssetsExpandFormField;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsExpandFormFieldDao extends BaseDao<AssetsExpandFormField> {

    public String toList(AssetsExpandFormField beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_expand_form_field");
        sql.append(" where is_del = '0' ");
        if (null != beanParam.getForm_id()){
            sql.append(" and form_id = '" + beanParam.getForm_id()+"' ");
        }
        if (StringUtils.isNotEmpty(beanParam.getName())){
            sql.append(" and name like concat('%','"+beanParam.getName()+"','%') ");
        }
        if (null != beanParam.getType()){
            sql.append(" and type = "+beanParam.getType());
        }
        if (null != beanParam.getIs_required()){
            sql.append(" and is_required = "+beanParam.getIs_required());
        }
        if (null != beanParam.getIs_enable()){
            sql.append(" and is_enable = "+beanParam.getIs_enable());
        }
        return sql.toString();
    }

    public String getExpandSubList(AssetsExpandForm beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" select sub.*  ").append(" FROM ").append(" assets_expand_form m left JOIN assets_expand_form_field sub on m.id = sub.form_id ");
        sql.append(" where 1=1 ");
        if(null != beanParam && null != beanParam.getType() && beanParam.getType().intValue() > 0){
            sql.append(" m.type =  ").append(beanParam.getType());
        }
        return sql.toString();
    }
}
