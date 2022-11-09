package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysSqlDao;
import com.jsdc.itss.model.SysSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysSqlMapper extends BaseMapper<SysSql> {

    @SelectProvider(method = "toList", type = SysSqlDao.class)
    List<SysSql> toList(SysSql beanParam);


    @SelectProvider(method = "parametSql", type = SysSqlDao.class)
    List<LinkedHashMap<String, Object>> parametSql(SysSql sysSql);
}