package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventParamConfig;
import org.springframework.stereotype.Repository;

/**
 * 事件参数管理表
 *
 * @Author thr
 * @create 2022-07-13 14:17:38
 */
@Repository
public class EventParamConfigDao extends BaseDao<EventParamConfig> {

    public String toList(EventParamConfig beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * FROM event_param_config");
        sql.append(" where is_del = 0 ");
        return sql.toString();
    }
}
