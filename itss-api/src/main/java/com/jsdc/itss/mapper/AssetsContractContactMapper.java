package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsContractContactDao;
import com.jsdc.itss.model.AssetsContractContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsContractContactMapper extends BaseMapper<AssetsContractContact> {

    @SelectProvider(method = "toList",type = AssetsContractContactDao.class)
    List<AssetsContractContact> toList(AssetsContractContact beanParam);
}