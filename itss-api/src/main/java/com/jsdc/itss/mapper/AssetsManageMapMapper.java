package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsManageMapDao;
import com.jsdc.itss.model.AssetsManageMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsManageMapMapper extends BaseMapper<AssetsManageMap> {

    @SelectProvider(method = "toList",type = AssetsManageMapDao.class)
    List<AssetsManageMap> toList(AssetsManageMap beanParam);
}