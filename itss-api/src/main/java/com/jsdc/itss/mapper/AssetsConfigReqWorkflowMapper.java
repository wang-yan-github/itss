package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsConfigReqWorkflowDao;
import com.jsdc.itss.model.AssetsConfigReqWorkflow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 配置请求审批流程表
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsConfigReqWorkflowMapper extends BaseMapper<AssetsConfigReqWorkflow> {

    @SelectProvider(method = "toList", type = AssetsConfigReqWorkflowDao.class)
    List<AssetsConfigReqWorkflow> toList(AssetsConfigReqWorkflow beanParam);
}