package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventBillingDesignateDao;
import com.jsdc.itss.model.EventBillingDesignate;
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
public interface EventBillingDesignateMapper extends BaseMapper<EventBillingDesignate> {

    @SelectProvider(method = "selectByEventId",type = EventBillingDesignateDao.class)
    List<EventBillingDesignate> selectByEventId(Integer eventId,String type);
}
