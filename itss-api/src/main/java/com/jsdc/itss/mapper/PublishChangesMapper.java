package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.PublishChangesDao;
import com.jsdc.itss.model.PublishChanges;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Mapper
public interface PublishChangesMapper extends BaseMapper<PublishChanges> {

    @SelectProvider(method = "toList",type = PublishChangesDao.class)
    List<PublishChanges> toList(PublishChanges beanParam);
}