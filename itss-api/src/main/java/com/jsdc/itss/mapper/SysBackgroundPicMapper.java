package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysBackgroundPicDao;
import com.jsdc.itss.model.SysBackgroundPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysBackgroundPicMapper extends BaseMapper<SysBackgroundPic> {

    @SelectProvider(method = "toList",type = SysBackgroundPicDao.class)
    List<SysBackgroundPic> toList(SysBackgroundPic beanParam);
}