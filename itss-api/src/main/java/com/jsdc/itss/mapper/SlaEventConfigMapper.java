package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SlaEventConfigDao;
import com.jsdc.itss.model.SlaEventConfig;
import com.jsdc.itss.vo.SlaEventConfigVo;
import com.jsdc.itss.vo.SlaEventFirstAndRequestVo;
import com.jsdc.itss.vo.SlaEventVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SlaEventConfigMapper extends BaseMapper<SlaEventConfig> {

    @SelectProvider(value = SlaEventConfigDao.class,method = "getSlaEventConfig")
    List<SlaEventConfigVo> getSlaEventConfig(SlaEventVo slaEventVo);

    @SelectProvider(value = SlaEventConfigDao.class,method = "getSlaEventConfigVos")
    List<SlaEventFirstAndRequestVo> getSlaEventConfigVos(SlaEventVo slaEventVo);
}
