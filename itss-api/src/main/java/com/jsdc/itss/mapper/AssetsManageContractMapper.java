package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsManageContractDao;
import com.jsdc.itss.model.AssetsManageContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsManageContractMapper extends BaseMapper<AssetsManageContract> {

    @SelectProvider(method = "toList",type = AssetsManageContractDao.class)
    List<AssetsManageContract> toList(AssetsManageContract beanParam);
}