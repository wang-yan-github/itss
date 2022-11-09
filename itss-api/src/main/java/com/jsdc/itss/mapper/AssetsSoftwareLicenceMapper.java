package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsSoftwareLicenceDao;
import com.jsdc.itss.model.AssetsSoftwareLicence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsSoftwareLicenceMapper extends BaseMapper<AssetsSoftwareLicence> {

    @SelectProvider(method = "toList",type = AssetsSoftwareLicenceDao.class)
    List<AssetsSoftwareLicence> toList(AssetsSoftwareLicence beanParam);
}