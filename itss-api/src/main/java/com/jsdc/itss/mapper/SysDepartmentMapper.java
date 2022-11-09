package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysDepartmentDao;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.vo.SysDepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {


    @SelectProvider(method = "getDepartmentInfo",type = SysDepartmentDao.class)
    List<SysDepartmentVo> getDepartmentInfo(SysDepartment sysDepartment);

    @SelectProvider(method = "getByKnowRangeId",type = SysDepartmentDao.class)
    List<SysDepartment> getByKnowRangeId(int knowledgeRangeId);
}
