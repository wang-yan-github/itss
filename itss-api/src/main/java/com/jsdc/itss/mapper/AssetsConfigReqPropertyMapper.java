package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsConfigReqPropertyDao;
import com.jsdc.itss.model.AssetsConfigReqProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 配置请求资产中间程表
 *
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsConfigReqPropertyMapper extends BaseMapper<AssetsConfigReqProperty> {

    @SelectProvider(method = "toList", type = AssetsConfigReqPropertyDao.class)
    List<AssetsConfigReqProperty> toList(AssetsConfigReqProperty beanParam);
}