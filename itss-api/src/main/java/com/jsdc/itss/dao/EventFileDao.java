package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.EventFile;
import org.springframework.stereotype.Repository;

/**
 * <p>添加描述信息<p>
 *
 * @author 徐傲龙
 * @since 2022年04月21日 14:45
 */

@Repository
public class EventFileDao extends BaseDao<EventFile> {

    public String getFileManageByType(int eventId, int type) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT fm.* FROM event_file t ");
        sql.append(" LEFT JOIN file_manage fm ON t.file_id = fm.id  ");
        sql.append(" where t.is_del = 0 and fm.is_del = 0 ");
        sql.append(" and t.type = ").append(type);
        sql.append(" and t.event_id = ").append(eventId);
        return sql.toString();
    }
}
