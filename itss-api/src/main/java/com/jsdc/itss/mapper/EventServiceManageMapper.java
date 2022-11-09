package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventServiceManageDao;
import com.jsdc.itss.model.EventServiceManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yanbin123
 * @since 2019-08-14
 */
@Mapper
public interface EventServiceManageMapper extends BaseMapper<EventServiceManage> {

    @SelectProvider(method = "getByKnowRangeId",type = EventServiceManageDao.class)
    List<EventServiceManage> getByKnowRangeId(int knowledgeRangeId);
}
