package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsInventoryConfigDao;
import com.jsdc.itss.model.AssetsInventoryConfig;
import com.jsdc.itss.vo.AssetsInventoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsInventoryConfigMapper extends BaseMapper<AssetsInventoryConfig> {

    @SelectProvider(method = "toList",type = AssetsInventoryConfigDao.class)
    List<AssetsInventoryConfig> toList(AssetsInventoryConfig beanParam);

    @SelectProvider(method = "getAssetsInventorys",type = AssetsInventoryConfigDao.class)
    List<AssetsInventoryVo> getAssetsInventorys(AssetsInventoryVo vo);

    @SelectProvider(method = "getAssetsInventory",type = AssetsInventoryConfigDao.class)
    AssetsInventoryVo getAssetsInventory(AssetsInventoryConfig vo);
}