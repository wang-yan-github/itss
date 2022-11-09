package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventServiceCategoryDao;
import com.jsdc.itss.model.EventServiceCategory;
import com.jsdc.itss.vo.GetEventServiceCategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface EventServiceCategoryMapper extends BaseMapper<EventServiceCategory> {


    @SelectProvider(method = "selectData",type = EventServiceCategoryDao.class)
    List<GetEventServiceCategoryVo> selectData();


}
