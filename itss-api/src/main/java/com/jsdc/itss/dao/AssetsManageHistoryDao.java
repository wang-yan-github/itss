package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsManageHistory;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-16 11:42:47
 */
@Repository
public class AssetsManageHistoryDao extends BaseDao<AssetsManageHistory> {

    public String toList(AssetsManageHistory beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT  h.id,option_type,u.`name` AS user_name,assets_id,option_date,h.is_del ").append(" FROM ").append("assets_manage_history H LEFT JOIN sys_user u  on h.create_user=u.id");
        sql.append(" where h.is_del=0 ");
        if (notEmpty(beanParam)) {
            if (notEmpty(beanParam.getAssets_id())) {
                sql.append(" and assets_id ='" + beanParam.getAssets_id() + "'");
            }
        }
        return sql.toString();
    }
}
