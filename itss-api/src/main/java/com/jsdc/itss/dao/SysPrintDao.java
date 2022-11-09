package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.model.SysPrint;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysPrintDao extends BaseDao<SysPrint> {

    public String toList(SysPrint beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_print");
        sql.append(" where is_del=0 ");

        if (StringUtils.isNotEmpty(beanParam.getPrint_name())){
            sql.append(" and print_name like concat('%','"+beanParam.getPrint_name()+"','%') " );
        }
        if (beanParam.getCreate_user()!= null){
            sql.append(" and create_user ="+beanParam.getCreate_user());
        }
        if (StringUtils.isNotEmpty(beanParam.getIs_use())){
            sql.append(" and is_use =" +beanParam.getIs_use());
        }
        if (StringUtils.isNotEmpty(beanParam.getContent())){
            sql.append(" and content like concat('%','"+beanParam.getContent()+"','%') " );
        }
        return sql.toString();
    }
}
