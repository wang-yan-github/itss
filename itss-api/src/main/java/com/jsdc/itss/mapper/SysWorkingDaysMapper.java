package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysWorkingDaysDao;
import com.jsdc.itss.model.SysWorkingDays;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysWorkingDaysMapper extends BaseMapper<SysWorkingDays> {

    @SelectProvider(method = "toList",type = SysWorkingDaysDao.class)
    List<SysWorkingDays> toList(SysWorkingDays beanParam);
}