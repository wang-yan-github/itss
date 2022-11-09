package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsInventory;
import com.jsdc.itss.service.AssetsInventoryService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 盘点期
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsInventory")
public class AssetsInventoryController extends BaseController {

    @Autowired
    private AssetsInventoryService assetsInventoryService;


    /**
     *  查询
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsInventory beanParam) {
        PageInfo<AssetsInventory> page = assetsInventoryService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsInventoryService.getById(id);
    }
    /**
     *  所属盘点期
     */
    @RequestMapping(value = "getAllPanDianQi.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllPanDianQi(){
        return assetsInventoryService.getAllPanDianQi();
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsInventory(@RequestBody AssetsInventory assetsInventory){
        if(null == assetsInventory.getId()) {
            return assetsInventoryService.addAssetsInventory(assetsInventory);
        }else{
            return assetsInventoryService.editAssetsInventory(assetsInventory);
        }
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsInventory(AssetsInventory assetsInventory){

        return assetsInventoryService.editAssetsInventory(assetsInventory);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toDelAssetsInventory.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toDelAssetsInventory(@RequestBody AssetsInventory assetsInventory){
        return assetsInventoryService.delAssetsInventory(assetsInventory);
    }

    @RequestMapping(value = "delBatch.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delBatch(@RequestBody String ids){
        assetsInventoryService.delBatch(ids);
        return ResultInfo.success();
    }
}
