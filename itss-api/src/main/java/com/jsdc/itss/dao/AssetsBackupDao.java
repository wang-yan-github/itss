package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsBackup;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Repository
public class AssetsBackupDao extends BaseDao<AssetsBackup> {

    public String toList(AssetsBackup beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_backup");
        sql.append(" where is_del=0 ");
        if(StringUtils.isNotBlank(beanParam.getBackup_name())){
            sql.append(" and backup_name like '%" + beanParam.getBackup_name() + "%' ");
        }
        return sql.toString();
    }
}
