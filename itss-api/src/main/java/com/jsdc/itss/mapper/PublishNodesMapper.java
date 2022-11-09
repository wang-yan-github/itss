package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.PublishNodesDao;
import com.jsdc.itss.model.PublishNodes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Mapper
public interface PublishNodesMapper extends BaseMapper<PublishNodes> {

    @SelectProvider(method = "toList",type = PublishNodesDao.class)
    List<PublishNodes> toList(PublishNodes beanParam);
}