package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsEnclosureDao;
import com.jsdc.itss.model.AssetsEnclosure;
import com.jsdc.itss.model.FileManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsEnclosureMapper extends BaseMapper<AssetsEnclosure> {

    @SelectProvider(method = "toList",type = AssetsEnclosureDao.class)
    List<AssetsEnclosure> toList(AssetsEnclosure beanParam);

    @SelectProvider(method = "getFileManage",type = AssetsEnclosureDao.class)
    List<FileManage> getFileManage(int assetsId);

    @SelectProvider(method = "getFileManageByType",type = AssetsEnclosureDao.class)
    List<FileManage> getFileManageByType(int assetsId, int fileType);
}