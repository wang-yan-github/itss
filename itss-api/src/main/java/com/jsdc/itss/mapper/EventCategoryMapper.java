package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventCategoryDao;
import com.jsdc.itss.model.EventCategory;
import com.jsdc.itss.vo.EventExpensesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yanbin123
 * @since 2019-08-14
 */
@Mapper
public interface EventCategoryMapper extends BaseMapper<EventCategory> {

    /**
     * 按照类型统计订单金额
     * @return
     */
    @SelectProvider(method = "eventExpenses", type = EventCategoryDao.class)
    List<EventExpensesVo> eventExpenses();

}
