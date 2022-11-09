package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsInventoryConfig;
import com.jsdc.itss.model.AssetsInventoryDetails;
import com.jsdc.itss.service.AssetsInventoryDetailsService;
import com.jsdc.itss.vo.AssetsInventoryDetailsVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 资产盘点详情表
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsInventoryDetails")
public class AssetsInventoryDetailsController extends BaseController {

    @Autowired
    private AssetsInventoryDetailsService assetsInventoryDetailsService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1",value="pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize, AssetsInventoryDetailsVo beanParam) {

        PageInfo<AssetsInventoryDetailsVo> page = assetsInventoryDetailsService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }
     /**
     *  查询
     */
    @RequestMapping(value = "getInventoryDetails.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getInventoryDetails(@RequestBody AssetsInventoryDetailsVo beanParam){
        return assetsInventoryDetailsService.getInventoryDetails(beanParam);
    }

    /**
     *  导出excel
     * @param beanParam
     */
    @RequestMapping(value = "exportExcel.do",method = RequestMethod.POST)
    @ResponseBody
    public void exportExcel(AssetsInventoryDetailsVo beanParam, HttpServletResponse response){
        assetsInventoryDetailsService.exportExcel(beanParam, response);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsInventoryDetails(AssetsInventoryConfig assetsInventoryConfig){

        return assetsInventoryDetailsService.addAssetsInventoryDetails(assetsInventoryConfig);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsInventoryDetails(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.editAssetsInventoryDetails(assetsInventoryDetails);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "doDeleteInventoryDetails.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo doDeleteInventoryDetails(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.doDeleteInventoryDetails(assetsInventoryDetails);
    }


    /**
     *  盘点记录处理
     */
    @RequestMapping(value = "toDeal.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toDeal(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.toDeal(assetsInventoryDetails);
    }

    /**
     *  盘点记录确认
     */
    @RequestMapping(value = "toConfirm.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toConfirm(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.toConfirm(assetsInventoryDetails);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "delBatch.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delBatch(@RequestBody String ids){
        assetsInventoryDetailsService.delBatch(ids);
        return ResultInfo.success();
    }
    /**
     *  删除
     */
    @RequestMapping(value = "confirmBatch.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo confirmBatch(@RequestBody String ids){
        assetsInventoryDetailsService.confirmBatch(ids);
        return ResultInfo.success();
    }
}
