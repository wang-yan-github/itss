package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsEnclosure;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsEnclosureDao extends BaseDao<AssetsEnclosure> {

    public String toList(AssetsEnclosure beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("assets_enclosure");
        sql.append(" where is_del=0 ");
        return sql.toString();
    }

    public String getFileManage(int assetsId){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT fm.* ").append(" FROM ").append(" assets_enclosure ae ");
        sql.append(" LEFT JOIN file_manage fm ON ae.file_id = fm.id  " );
        sql.append(" where 1=1 ");
        sql.append(" and ae.is_del=0 ");
        sql.append(" and fm.is_del=0 ");
        sql.append(" and ae.assets_id=").append(assetsId);
        return sql.toString();
    }

    public String getFileManageByType(int assetsId, int fileType){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT fm.* ").append(" FROM ").append(" assets_enclosure ae ");
        sql.append(" LEFT JOIN file_manage fm ON ae.file_id = fm.id  " );
        sql.append(" where 1=1 ");
        sql.append(" and ae.is_del=0 ");
        sql.append(" and fm.is_del=0 ");
        sql.append(" and ae.file_type= "+ fileType);
        sql.append(" and ae.assets_id=").append(assetsId);
        return sql.toString();
    }
}
