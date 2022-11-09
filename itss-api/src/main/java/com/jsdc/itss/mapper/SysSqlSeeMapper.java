package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysSqlSeeDao;
import com.jsdc.itss.model.SysSqlSee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysSqlSeeMapper extends BaseMapper<SysSqlSee> {

    @SelectProvider(method = "toList",type = SysSqlSeeDao.class)
    List<SysSqlSee> toList(SysSqlSee beanParam);
}