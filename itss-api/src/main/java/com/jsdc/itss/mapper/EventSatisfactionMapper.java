package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventSatisfactionDao;
import com.jsdc.itss.model.EventSatisfaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-22 10:30:09
 */
@Mapper
public interface EventSatisfactionMapper extends BaseMapper<EventSatisfaction> {

    @SelectProvider(method = "toList",type = EventSatisfactionDao.class)
    List<EventSatisfaction> toList(EventSatisfaction beanParam);
}