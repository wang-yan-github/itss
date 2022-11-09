package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SlaQuestionDao;
import com.jsdc.itss.model.SlaQuestion;
import com.jsdc.itss.vo.SlaQuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SlaQuestionMapper extends BaseMapper<SlaQuestion> {
    @SelectProvider(value = SlaQuestionDao.class,method = "getSlaQuestion")
    List<SlaQuestionVo> getSlaQuestion(SlaQuestion slaQuestion);

    @SelectProvider(value = SlaQuestionDao.class,method = "getSlaQuestionByCategoryId")
    List<SlaQuestion> getSlaQuestionByCategoryId(Integer questionCategoryId);
}
