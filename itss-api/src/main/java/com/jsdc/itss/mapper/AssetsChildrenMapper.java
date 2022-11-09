package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsChildrenDao;
import com.jsdc.itss.model.AssetsChildren;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Mapper
public interface AssetsChildrenMapper extends BaseMapper<AssetsChildren> {

    @SelectProvider(method = "toList",type = AssetsChildrenDao.class)
    List<AssetsChildren> toList(AssetsChildren beanParam);
}