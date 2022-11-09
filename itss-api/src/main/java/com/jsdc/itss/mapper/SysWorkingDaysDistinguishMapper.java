package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysWorkingDaysDistinguishDao;
import com.jsdc.itss.model.SysWorkingDaysDistinguish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysWorkingDaysDistinguishMapper extends BaseMapper<SysWorkingDaysDistinguish> {

    @SelectProvider(method = "toList",type = SysWorkingDaysDistinguishDao.class)
    List<SysWorkingDaysDistinguish> toList(SysWorkingDaysDistinguish beanParam);
}