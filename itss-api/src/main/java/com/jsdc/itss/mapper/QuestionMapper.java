package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.QuestionDao;
import com.jsdc.itss.model.Question;
import com.jsdc.itss.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-15 10:16:00
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    @SelectProvider(method = "toList", type = QuestionDao.class)
    List<QuestionVo> toList(QuestionVo beanParam);

    @SelectProvider(method = "toExportList", type = QuestionDao.class)
    List<QuestionVo> toExportList();

    @SelectProvider(method = "questionStatistics", type = QuestionDao.class)
    List<Question> questionStatistics(Question question);
}