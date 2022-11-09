package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysDocumentDao;
import com.jsdc.itss.model.SysDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysDocumentMapper extends BaseMapper<SysDocument> {

    @SelectProvider(method = "toList",type = SysDocumentDao.class)
    List<SysDocument> toList(SysDocument beanParam);
}