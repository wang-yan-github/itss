package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.model.EventParamConfig;
import org.apache.ibatis.annotations.Mapper;
import com.jsdc.itss.dao.EventParamConfigDao;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 事件参数管理表
 *
 * @Author thr
 * @create 2022-07-13 14:17:38
 */
@Mapper
public interface EventParamConfigMapper extends BaseMapper<EventParamConfig> {

    @SelectProvider(method = "toList", type = EventParamConfigDao.class)
    List<EventParamConfig> toList(EventParamConfig beanParam);
}