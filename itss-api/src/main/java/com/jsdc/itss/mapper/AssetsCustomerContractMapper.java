package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsCustomerContractDao;
import com.jsdc.itss.model.AssetsCustomerContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsCustomerContractMapper extends BaseMapper<AssetsCustomerContract> {

    @SelectProvider(method = "toList",type = AssetsCustomerContractDao.class)
    List<AssetsCustomerContract> toList(AssetsCustomerContract beanParam);
}