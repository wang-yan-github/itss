package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsConfigReqDao;
import com.jsdc.itss.model.AssetsConfigReq;
import com.jsdc.itss.vo.AssetsConfigReqVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsConfigReqMapper extends BaseMapper<AssetsConfigReq> {

    @SelectProvider(method = "toList",type = AssetsConfigReqDao.class)
    List<AssetsConfigReqVo> toList(AssetsConfigReqVo beanParam);

    @SelectProvider(method = "getAssetsConfigReq",type = AssetsConfigReqDao.class)
    AssetsConfigReqVo getAssetsConfigReq(AssetsConfigReqVo beanParam);
}