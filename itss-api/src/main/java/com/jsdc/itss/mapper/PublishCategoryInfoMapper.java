package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.PublishCategoryInfoDao;
import com.jsdc.itss.model.PublishCategoryInfo;
import com.jsdc.itss.model.vo.PublishApproveInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 发布类别管理表 Mapper 接口
 * </p>
 *
 * @author wh
 * @date 2022-03-07
 */
@Mapper
public interface PublishCategoryInfoMapper extends BaseMapper<PublishCategoryInfo> {

    @SelectProvider(method = "toList",type = PublishCategoryInfoDao.class)
    List<PublishCategoryInfo> toList(PublishCategoryInfo publishCategoryInfo);

    @SelectProvider(method = "getCategoryApproves",type = PublishCategoryInfoDao.class)
    List<PublishApproveInfo> getCategoryApproves(Integer id);
}
