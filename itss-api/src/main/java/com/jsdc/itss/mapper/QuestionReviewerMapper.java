package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.QuestionReviewerDao;
import com.jsdc.itss.model.QuestionReviewer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 问题审核人关联表
 *
 * @Author thr
 * @create 2022-03-24 14:21:12
 */
@Mapper
public interface QuestionReviewerMapper extends BaseMapper<QuestionReviewer> {

    @SelectProvider(method = "toList", type = QuestionReviewerDao.class)
    List<QuestionReviewer> toList(QuestionReviewer beanParam);

    @SelectProvider(method = "delByQuestionId", type = QuestionReviewerDao.class)
    void delByQuestionId(QuestionReviewer bean);
}