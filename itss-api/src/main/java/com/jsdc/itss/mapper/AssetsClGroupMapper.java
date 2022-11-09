package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsClGroupDao;
import com.jsdc.itss.model.AssetsClGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Mapper
public interface AssetsClGroupMapper extends BaseMapper<AssetsClGroup> {

    @SelectProvider(method = "toList",type = AssetsClGroupDao.class)
    List<AssetsClGroup> toList(AssetsClGroup beanParam);
}