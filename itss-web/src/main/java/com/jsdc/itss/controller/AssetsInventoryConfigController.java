package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsInventoryConfig;
import com.jsdc.itss.service.AssetsInventoryConfigService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 盘点期配置
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsInventoryConfig")
public class AssetsInventoryConfigController extends BaseController {

    @Autowired
    private AssetsInventoryConfigService assetsInventoryConfigService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsInventoryConfig beanParam) {
        PageInfo<AssetsInventoryConfig> page = assetsInventoryConfigService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsInventoryConfigService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsInventoryConfig(@RequestBody AssetsInventoryConfig assetsInventoryConfig){
//        assetsInventoryConfigService.addAssetsInventoryConfig(assetsInventoryConfig);
        return assetsInventoryConfigService.addAIConfig(assetsInventoryConfig);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsInventoryConfig(AssetsInventoryConfig assetsInventoryConfig){

        return assetsInventoryConfigService.editAssetsInventoryConfig(assetsInventoryConfig);
    }
}
