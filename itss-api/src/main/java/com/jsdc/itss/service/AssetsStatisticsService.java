package com.jsdc.itss.service;

import com.jsdc.itss.mapper.AssetsManageMapper;
import com.jsdc.itss.vo.AssetsStatisticsResult;
import com.jsdc.itss.vo.AssetsStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: itss
 * @className: AssetsStatisticsService
 * @author: wp
 * @description:
 * @date: 2022/7/20 9:22
 */
@Service
public class AssetsStatisticsService {

    @Autowired
    private AssetsManageMapper assetsManageMapper;

    /**
     * @param assetsStatisticsVo:
     * @return List<AssetsStatisticsResult>
     * @author wp
     * @description 配置项类别统计
     * @date 2022/7/20 15:06
     */
    public List<AssetsStatisticsResult> assetsTypeStatistics(AssetsStatisticsVo assetsStatisticsVo){
        List<AssetsStatisticsResult> list = assetsManageMapper.assetsTypeStatistics(assetsStatisticsVo);
        return list;
    }

    /**
     * @param assetsStatisticsVo:
     * @return List<AssetsStatisticsResult>
     * @author wp
     * @description 配置项CL分组统计
     * @date 2022/7/20 15:08
     */
    public List<AssetsStatisticsResult> assetsClGroupStatistics(AssetsStatisticsVo assetsStatisticsVo){
        List<AssetsStatisticsResult> list = assetsManageMapper.clGroupStatistics(assetsStatisticsVo);
        return list;
    }

    /**
     * @param assetsStatisticsVo:
     * @return List<AssetsStatisticsResult>
     * @author wp
     * @description 部门统计
     * @date 2022/7/20 15:52
     */
    public List<AssetsStatisticsResult> assetsDeptStatistics(AssetsStatisticsVo assetsStatisticsVo){
        List<AssetsStatisticsResult> list = assetsManageMapper.assetsDeptStatistics(assetsStatisticsVo);
        return list;
    }
}
