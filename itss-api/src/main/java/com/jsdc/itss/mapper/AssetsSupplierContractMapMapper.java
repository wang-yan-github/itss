package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsSupplierContractMapDao;
import com.jsdc.itss.model.AssetsSupplierContractMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsSupplierContractMapMapper extends BaseMapper<AssetsSupplierContractMap> {

    @SelectProvider(method = "toList",type = AssetsSupplierContractMapDao.class)
    List<AssetsSupplierContractMap> toList(AssetsSupplierContractMap beanParam);
}