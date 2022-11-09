package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsTypeDao;
import com.jsdc.itss.model.AssetsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-08 14:15:25
 */
@Mapper
public interface AssetsTypeMapper extends BaseMapper<AssetsType> {

    @SelectProvider(method = "toList",type = AssetsTypeDao.class)
    List<AssetsType> toList(AssetsType beanParam);
}