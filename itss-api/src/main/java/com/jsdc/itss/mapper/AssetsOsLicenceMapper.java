package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsOsLicenceDao;
import com.jsdc.itss.model.AssetsOsLicence;
import com.jsdc.itss.vo.AssetsOsLicenceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Mapper
public interface AssetsOsLicenceMapper extends BaseMapper<AssetsOsLicence> {

    @SelectProvider(method = "toList",type = AssetsOsLicenceDao.class)
    List<AssetsOsLicenceVo> toList(AssetsOsLicence beanParam);
}