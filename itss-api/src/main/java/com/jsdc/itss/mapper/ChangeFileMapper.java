package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.ChangeFileDao;
import com.jsdc.itss.model.ChangeFile;
import com.jsdc.itss.model.FileManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ChangeFileMapper extends BaseMapper<ChangeFile> {
    @SelectProvider(value = ChangeFileDao.class,method = "getFileManage")
    List<FileManage> getFileManage(Integer id);
}
