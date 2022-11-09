package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.PublishBasicInfoDao;
import com.jsdc.itss.model.PublishBasicInfo;
import com.jsdc.itss.model.vo.ChangesOf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-14 14:58:22
 */
@Mapper
public interface PublishBasicInfoMapper extends BaseMapper<PublishBasicInfo> {

    @SelectProvider(method = "toList",type = PublishBasicInfoDao.class)
    List<PublishBasicInfo> toList(PublishBasicInfo beanParam);

    @SelectProvider(method = "publishedSources",type = PublishBasicInfoDao.class)
    List<ChangesOf> publishedSources();

    @SelectProvider(method = "releaseTheCategory",type = PublishBasicInfoDao.class)
    List<ChangesOf> releaseTheCategory();

    @SelectProvider(method = "publishType",type = PublishBasicInfoDao.class)
    List<ChangesOf> publishType();


}