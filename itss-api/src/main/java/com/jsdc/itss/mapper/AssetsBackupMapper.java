package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsBackupDao;
import com.jsdc.itss.model.AssetsBackup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Mapper
public interface AssetsBackupMapper extends BaseMapper<AssetsBackup> {

    @SelectProvider(method = "toList",type = AssetsBackupDao.class)
    List<AssetsBackup> toList(AssetsBackup beanParam);
}