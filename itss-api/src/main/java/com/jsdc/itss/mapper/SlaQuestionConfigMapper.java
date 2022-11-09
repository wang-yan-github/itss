package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SlaQuestionConfigDao;
import com.jsdc.itss.model.SlaQuestionConfig;
import com.jsdc.itss.vo.SlaQuestionConfigVo;
import com.jsdc.itss.vo.SlaQuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SlaQuestionConfigMapper extends BaseMapper<SlaQuestionConfig> {

    @SelectProvider(value = SlaQuestionConfigDao.class,method = "getSlaQuestion")
    List<SlaQuestionConfigVo> getSlaQuestion(SlaQuestionVo slaQuestionVo);

    @SelectProvider(value = SlaQuestionConfigDao.class,method = "getSlaQuestionConf")
    List<SlaQuestionConfigVo> getSlaQuestionConf(SlaQuestionVo slaQuestionVo);
}
