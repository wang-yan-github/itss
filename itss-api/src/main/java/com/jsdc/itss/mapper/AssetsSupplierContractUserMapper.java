package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsSupplierContractUserDao;
import com.jsdc.itss.model.AssetsSupplierContractUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsSupplierContractUserMapper extends BaseMapper<AssetsSupplierContractUser> {

    @SelectProvider(method = "toList",type = AssetsSupplierContractUserDao.class)
    List<AssetsSupplierContractUser> toList(AssetsSupplierContractUser beanParam);
}