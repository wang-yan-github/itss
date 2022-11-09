package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.QuestionDao;
import com.jsdc.itss.model.Question;
import com.jsdc.itss.model.QuestionCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * ClassName: QuestionCategory
 * Description: 问题类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Mapper
public interface QuestionCategoryMapper extends BaseMapper<QuestionCategory> {

    @SelectProvider(method = "knowledge", type = QuestionDao.class)
    List<Question> knowledge(Question question);

    @SelectProvider(method = "isFitSla", type = QuestionDao.class)
    List<Question> isFitSla(Question question);

    @SelectProvider(method = "status", type = QuestionDao.class)
    List<Question> status(Question question);
}
