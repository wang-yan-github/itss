package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsSupplierContractDao;
import com.jsdc.itss.model.AssetsSupplierContract;
import com.jsdc.itss.vo.AssetsSupplierContractVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsSupplierContractMapper extends BaseMapper<AssetsSupplierContract> {

    @SelectProvider(method = "toList",type = AssetsSupplierContractDao.class)
    List<AssetsSupplierContractVo> toList(AssetsSupplierContract beanParam);
}