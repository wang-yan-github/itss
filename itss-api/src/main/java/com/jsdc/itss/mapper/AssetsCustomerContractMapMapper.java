package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsCustomerContractMapDao;
import com.jsdc.itss.model.AssetsCustomerContractMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsCustomerContractMapMapper extends BaseMapper<AssetsCustomerContractMap> {

    @SelectProvider(method = "toList",type = AssetsCustomerContractMapDao.class)
    List<AssetsCustomerContractMap> toList(AssetsCustomerContractMap beanParam);
}