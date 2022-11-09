package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventChangeRelationDao;
import com.jsdc.itss.model.EventChangeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-17 11:41:40
 */
@Mapper
public interface EventChangeRelationMapper extends BaseMapper<EventChangeRelation> {

    @SelectProvider(method = "toList",type = EventChangeRelationDao.class)
    List<EventChangeRelation> toList(EventChangeRelation beanParam);
}