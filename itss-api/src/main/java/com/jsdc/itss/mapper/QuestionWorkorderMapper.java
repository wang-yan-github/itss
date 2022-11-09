package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.QuestionWorkorderDao;
import com.jsdc.itss.model.QuestionWorkorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-15 10:40:27
 */
@Mapper
public interface QuestionWorkorderMapper extends BaseMapper<QuestionWorkorder> {

    @SelectProvider(method = "toList",type = QuestionWorkorderDao.class)
    List<QuestionWorkorder> toList(QuestionWorkorder beanParam);
}