package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.PublishCategoryApproveDao;
import com.jsdc.itss.model.PublishCategoryApprove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Mapper
public interface PublishCategoryApproveMapper extends BaseMapper<PublishCategoryApprove> {

    @SelectProvider(method = "toList",type = PublishCategoryApproveDao.class)
    List<PublishCategoryApprove> toList(PublishCategoryApprove beanParam);
}