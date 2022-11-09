package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.ChangeFile;
import org.springframework.stereotype.Repository;

/**
 * ClassName: ChangeFileDao
 * Description:
 * date: 2022/4/16 17:50
 *
 * @author bn
 */
@Repository
public class ChangeFileDao extends BaseDao<ChangeFile> {

    public String getFileManage(Integer id){

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT fm.* FROM change_file t ");
        sql.append(" LEFT JOIN file_manage fm ON t.file_id = fm.id  ");
        sql.append(" where t.is_del = 0 and fm.is_del = 0 ");
        sql.append(" and t.change_id = ").append(id);
        return sql.toString();
    }
}
