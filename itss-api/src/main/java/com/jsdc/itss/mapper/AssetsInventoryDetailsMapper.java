package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsInventoryDetailsDao;
import com.jsdc.itss.model.AssetsInventoryDetails;
import com.jsdc.itss.vo.AssetsInventoryDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsInventoryDetailsMapper extends BaseMapper<AssetsInventoryDetails> {

    @SelectProvider(method = "toList",type = AssetsInventoryDetailsDao.class)
    List<AssetsInventoryDetailsVo> toList(AssetsInventoryDetailsVo beanParam);

    @SelectProvider(method = "getOne",type = AssetsInventoryDetailsDao.class)
    AssetsInventoryDetailsVo getOne(Integer id);

    @SelectProvider(method = "getInventoryDetails",type = AssetsInventoryDetailsDao.class)
    AssetsInventoryDetailsVo getInventoryDetails(AssetsInventoryDetailsVo beanParam);
}