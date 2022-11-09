package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsConfigReqFile;
import com.jsdc.itss.model.ChangeFile;
import org.springframework.stereotype.Repository;

/**
 * ClassName: AssetsConfigReqFileDao
 * Description:
 * date: 2022/4/18 8:51
 *
 * @author bn
 */
@Repository
public class AssetsConfigReqFileDao extends BaseDao<AssetsConfigReqFile> {

    public String getFileManage(Integer id){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT fm.* FROM assets_config_req_file t ");
        sql.append(" LEFT JOIN file_manage fm ON t.file_id = fm.id  ");
        sql.append(" where t.is_del = 0 and fm.is_del = 0 ");
        sql.append(" and t.assets_id = ").append(id);
        return sql.toString();
    }

}
