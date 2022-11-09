package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsSupplierContractFileDao;
import com.jsdc.itss.model.AssetsSupplierContractFile;
import com.jsdc.itss.model.FileManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsSupplierContractFileMapper extends BaseMapper<AssetsSupplierContractFile> {

    @SelectProvider(method = "toList", type = AssetsSupplierContractFileDao.class)
    List<AssetsSupplierContractFile> toList(AssetsSupplierContractFile beanParam);

    @SelectProvider(value = AssetsSupplierContractFileDao.class, method = "getFileManage")
    List<FileManage> getFileManage(Integer id);
}