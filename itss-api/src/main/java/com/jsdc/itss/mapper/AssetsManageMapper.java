package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsManageDao;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.vo.AssetsStatisticsResult;
import com.jsdc.itss.vo.AssetsStatisticsVo;
import com.jsdc.itss.vo.OverviewVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsManageMapper extends BaseMapper<AssetsManage> {

    @SelectProvider(method = "toList",type = AssetsManageDao.class)
    List<AssetsManage> toList(AssetsManage beanParam);

    @SelectProvider(method = "clGroupConfig",type = AssetsManageDao.class)
    List<OverviewVo> clGroupConfig(AssetsManage bean);

    @SelectProvider(method = "getGroupSum",type = AssetsManageDao.class)
    List<OverviewVo> getGroupSum(AssetsManage bean);

    /**
     * 配置项类别统计
     * @param assetsStatisticsVo
     * @return
     */
    @SelectProvider(method = "assetsTypeStatistics",type = AssetsManageDao.class)
    List<AssetsStatisticsResult> assetsTypeStatistics(AssetsStatisticsVo assetsStatisticsVo);

    /**
     * CL分组统计
     * @param assetsStatisticsVo
     * @return
     */
    @SelectProvider(method = "clGroupStatistics",type = AssetsManageDao.class)
    List<AssetsStatisticsResult> clGroupStatistics(AssetsStatisticsVo assetsStatisticsVo);

    /**
     * 部门统计
     * @param assetsStatisticsVo
     * @return
     */
    @SelectProvider(method = "assetsDeptStatistics",type = AssetsManageDao.class)
    List<AssetsStatisticsResult> assetsDeptStatistics(AssetsStatisticsVo assetsStatisticsVo);
}