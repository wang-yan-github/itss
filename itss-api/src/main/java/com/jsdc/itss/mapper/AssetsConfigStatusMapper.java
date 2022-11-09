package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsConfigStatusDao;
import com.jsdc.itss.model.AssetsConfigStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Mapper
public interface AssetsConfigStatusMapper extends BaseMapper<AssetsConfigStatus> {

    @SelectProvider(method = "toList",type = AssetsConfigStatusDao.class)
    List<AssetsConfigStatus> toList(AssetsConfigStatus beanParam);
}