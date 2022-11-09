package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysAnnouncement;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysAnnouncementDao extends BaseDao<SysAnnouncement> {

    public String toList(SysAnnouncement beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_announcement");
        sql.append(" where is_del=0 ");
        if (StringUtils.isNotEmpty(beanParam.getTitle())){
            sql.append(" and title like concat('%','"+beanParam.getTitle()+"','%') ");
        }
        return sql.toString();
    }
}
