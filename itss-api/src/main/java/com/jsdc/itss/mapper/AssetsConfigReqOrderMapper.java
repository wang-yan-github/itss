package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.model.AssetsConfigReqOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 配置请求关联事件工单中间表
 */
@Mapper
public interface AssetsConfigReqOrderMapper extends BaseMapper<AssetsConfigReqOrder> {
}
