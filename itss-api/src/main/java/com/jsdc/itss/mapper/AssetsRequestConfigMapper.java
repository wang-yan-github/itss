package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsRequestConfigDao;
import com.jsdc.itss.model.AssetsRequestConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsRequestConfigMapper extends BaseMapper<AssetsRequestConfig> {

    @SelectProvider(method = "toList",type = AssetsRequestConfigDao.class)
    List<AssetsRequestConfig> toList(AssetsRequestConfig beanParam);
}