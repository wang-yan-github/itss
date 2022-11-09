package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsInventoryDao;
import com.jsdc.itss.model.AssetsInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsInventoryMapper extends BaseMapper<AssetsInventory> {

    @SelectProvider(method = "toList",type = AssetsInventoryDao.class)
    List<AssetsInventory> toList(AssetsInventory beanParam);
}