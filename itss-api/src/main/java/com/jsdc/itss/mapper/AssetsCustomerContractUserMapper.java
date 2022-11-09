package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsCustomerContractUserDao;
import com.jsdc.itss.model.AssetsCustomerContractUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsCustomerContractUserMapper extends BaseMapper<AssetsCustomerContractUser> {

    @SelectProvider(method = "toList",type = AssetsCustomerContractUserDao.class)
    List<AssetsCustomerContractUser> toList(AssetsCustomerContractUser beanParam);
}