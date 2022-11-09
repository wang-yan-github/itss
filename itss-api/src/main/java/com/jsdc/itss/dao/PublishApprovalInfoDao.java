package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.PublishApprovalInfo;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-14 15:00:18
 */
@Repository
public class PublishApprovalInfoDao extends BaseDao<PublishApprovalInfo> {

    public String toList(PublishApprovalInfo beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("publish_approval_info");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }

    public String getByPublishId(Integer id){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT p.*,s.name as approver_name from publish_approval_info p ");
        sql.append(" left join sys_user s on p.approver=s.id ");
        sql.append(" where p.is_del=0 ");
        sql.append(" and p.basic_info_id= "+id);
        return sql.toString();
    }
}
