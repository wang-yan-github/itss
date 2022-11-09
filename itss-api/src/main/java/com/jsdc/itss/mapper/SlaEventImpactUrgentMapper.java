package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SlaEventImpactUrgentDao;
import com.jsdc.itss.model.SlaEventImpactUrgent;
import com.jsdc.itss.vo.SlaEventImpactUrgentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SlaEventImpactUrgentMapper extends BaseMapper<SlaEventImpactUrgent> {
    @SelectProvider(type = SlaEventImpactUrgentDao.class,method = "toList")
    List<SlaEventImpactUrgentVo> toList(SlaEventImpactUrgent slaEventImpactUrgent);
}
