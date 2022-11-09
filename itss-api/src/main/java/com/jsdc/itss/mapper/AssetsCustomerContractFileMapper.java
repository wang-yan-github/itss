package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsCustomerContractFileDao;
import com.jsdc.itss.model.AssetsCustomerContractFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsCustomerContractFileMapper extends BaseMapper<AssetsCustomerContractFile> {

    @SelectProvider(method = "toList",type = AssetsCustomerContractFileDao.class)
    List<AssetsCustomerContractFile> toList(AssetsCustomerContractFile beanParam);
}