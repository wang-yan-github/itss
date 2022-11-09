package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SlaEventDao;
import com.jsdc.itss.model.SlaEvent;
import com.jsdc.itss.vo.SlaEventFirstAndRequestVo;
import com.jsdc.itss.vo.SlaEventVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SlaEventMapper extends BaseMapper<SlaEvent> {
    @SelectProvider(method = "getSlaEvent",type = SlaEventDao.class)
    List<SlaEventVo> getSlaEvent(SlaEvent slaEvent);

    @SelectProvider(method = "getSlaEventFirstAndRequest",type = SlaEventDao.class)
    List<SlaEventFirstAndRequestVo> getSlaEventFirstAndRequest(String is_first, String is_request);

    @SelectProvider(method = "getSlaByEventCategory",type = SlaEventDao.class)
    List<SlaEvent> getSlaByEventCategory(Integer eventCategoryId);
}
