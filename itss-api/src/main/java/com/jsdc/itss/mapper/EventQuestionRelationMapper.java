package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventQuestionRelationDao;
import com.jsdc.itss.model.EventQuestionRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-17 11:41:40
 */
@Mapper
public interface EventQuestionRelationMapper extends BaseMapper<EventQuestionRelation> {

    @SelectProvider(method = "toList",type = EventQuestionRelationDao.class)
    List<EventQuestionRelation> toList(EventQuestionRelation beanParam);
}