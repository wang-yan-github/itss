package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsManageUserDao;
import com.jsdc.itss.model.AssetsManageUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsManageUserMapper extends BaseMapper<AssetsManageUser> {

    @SelectProvider(method = "toList",type = AssetsManageUserDao.class)
    List<AssetsManageUser> toList(AssetsManageUser beanParam);
}