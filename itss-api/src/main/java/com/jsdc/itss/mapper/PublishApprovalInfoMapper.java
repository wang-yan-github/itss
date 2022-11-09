package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.PublishApprovalInfoDao;
import com.jsdc.itss.model.PublishApprovalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-14 15:00:18
 */
@Mapper
public interface PublishApprovalInfoMapper extends BaseMapper<PublishApprovalInfo> {

    @SelectProvider(method = "toList",type = PublishApprovalInfoDao.class)
    List<PublishApprovalInfo> toList(PublishApprovalInfo beanParam);

    @SelectProvider(method = "getByPublishId",type = PublishApprovalInfoDao.class)
    List<PublishApprovalInfo> getByPublishId(Integer id);
}