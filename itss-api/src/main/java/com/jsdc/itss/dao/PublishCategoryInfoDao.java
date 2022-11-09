package com.jsdc.itss.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.PublishCategoryInfo;
import com.jsdc.itss.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class PublishCategoryInfoDao extends BaseDao<PublishCategoryInfo> {

    public String toList(PublishCategoryInfo publishCategoryInfo){
        StringBuilder sql=new StringBuilder();

        sql.append(" select pci.*,su.name as publisherName ");
        sql.append(" from publish_category_info pci left join sys_user su on pci.publisher=su.id ");
        sql.append(" where pci.is_del=0 ");
        if(notEmpty(publishCategoryInfo.getIs_use())){
            sql.append(" and pci.is_use='1' ");
        }
        return sql.toString();
    }

    public QueryWrapper<SysUser> queryByName(String name, String pass) {
        QueryWrapper<SysUser> queryHandler = new QueryWrapper<>();
        if (notEmpty(name)) {
            queryHandler.eq("login_name", name);
        }
        if (notEmpty(pass)) {
            queryHandler.eq("password", pass);
        }
        queryHandler.eq("is_del", "0");
        queryHandler.eq("status", "1");
        return queryHandler;
    }

    public String getCategoryApproves(Integer id){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" pca.id as category_approve_id,su.name,su.user_name,su.user_type,su.status,su.telephone,pca.sort ");
        sql.append(" FROM");
        sql.append(" publish_category_approve pca left join sys_user su on pca.approve_user=su.id ");
        sql.append(" where pca.is_del='0' ");
        sql.append(" and pca.publish_category_id = "+ id);
        return sql.toString();
    }
}
