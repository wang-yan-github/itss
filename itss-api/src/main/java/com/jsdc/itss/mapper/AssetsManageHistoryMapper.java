package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsManageHistoryDao;
import com.jsdc.itss.model.AssetsManageHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-16 11:42:47
 */
@Mapper
public interface AssetsManageHistoryMapper extends BaseMapper<AssetsManageHistory> {

    @SelectProvider(method = "toList",type = AssetsManageHistoryDao.class)
    List<AssetsManageHistory> toList(AssetsManageHistory beanParam);
}