package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.QuestionChangeDao;
import com.jsdc.itss.model.QuestionChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-15 13:26:30
 */
@Mapper
public interface QuestionChangeMapper extends BaseMapper<QuestionChange> {

    @SelectProvider(method = "toList",type = QuestionChangeDao.class)
    List<QuestionChange> toList(QuestionChange beanParam);
}