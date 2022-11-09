package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysPrintParameterDao;
import com.jsdc.itss.model.SysPrintParameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysPrintParameterMapper extends BaseMapper<SysPrintParameter> {

    @SelectProvider(method = "toList",type = SysPrintParameterDao.class)
    List<SysPrintParameter> toList(SysPrintParameter beanParam);
}