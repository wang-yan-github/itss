package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.model.PublishFile;
import org.apache.ibatis.annotations.Mapper;
import com.jsdc.itss.dao.PublishFileDao;
import org.apache.ibatis.annotations.SelectProvider;
import java.util.List;

/**
 * @Author thr
 * @create 2022-06-27 13:44:00
 */
@Mapper
public interface PublishFileMapper extends BaseMapper<PublishFile> {

    @SelectProvider(method = "toList",type = PublishFileDao.class)
    List<PublishFile> toList(PublishFile beanParam);
}