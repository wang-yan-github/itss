package com.jsdc.itss.controller;

import com.jsdc.itss.service.AssetsStatisticsService;
import com.jsdc.itss.vo.AssetsStatisticsResult;
import com.jsdc.itss.vo.AssetsStatisticsVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: itss
 * @className: AssetsStatisticsController
 * @author: wp
 * @description:
 * @date: 2022/7/20 9:18
 */
@RestController
@RequestMapping("assets/statistics")
public class AssetsStatisticsController {

    @Autowired
    private AssetsStatisticsService assetsStatisticsService;

    /**
     * @param assetsStatisticsVo:
     * @return ResultInfo
     * @author wp
     * @description 配置项类别统计
     * @date 2022/7/20 15:10
     */
    @GetMapping(value = "assetsTypeStatistics.do")
    public ResultInfo assetsTypeStatistics(AssetsStatisticsVo assetsStatisticsVo){
        List<AssetsStatisticsResult> assetsStatisticsResults = assetsStatisticsService.assetsTypeStatistics(assetsStatisticsVo);
        return ResultInfo.success(assetsStatisticsResults);
    }

    /**
     * @param assetsStatisticsVo:
     * @return ResultInfo
     * @author wp
     * @description 配置项CL分组统计
     * @date 2022/7/20 15:10
     */
    @GetMapping(value = "clGroupStatistics.do")
    public ResultInfo clGroupStatistics(AssetsStatisticsVo assetsStatisticsVo){
        List<AssetsStatisticsResult> assetsStatisticsResults = assetsStatisticsService.assetsClGroupStatistics(assetsStatisticsVo);
        return ResultInfo.success(assetsStatisticsResults);
    }

    /**
     * @param assetsStatisticsVo:
     * @return ResultInfo
     * @author wp
     * @description 部门统计
     * @date 2022/7/20 15:53
     */
    @GetMapping(value = "assetsDeptStatistics.do")
    public ResultInfo assetsDeptStatistics(AssetsStatisticsVo assetsStatisticsVo){
        List<AssetsStatisticsResult> assetsStatisticsResults = assetsStatisticsService.assetsDeptStatistics(assetsStatisticsVo);
        return ResultInfo.success(assetsStatisticsResults);
    }
}
