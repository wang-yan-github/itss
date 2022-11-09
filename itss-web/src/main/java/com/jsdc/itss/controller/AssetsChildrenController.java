package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsChildren;
import com.jsdc.itss.service.AssetsChildrenService;
import com.jsdc.itss.service.AssetsTypeService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Controller
@RequestMapping("/assetsChildren")
public class AssetsChildrenController extends BaseController {

    @Autowired
    private AssetsChildrenService assetsChildrenService;
    @Autowired
    private AssetsTypeService assetsTypeService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,@RequestBody AssetsChildren beanParam) {
        PageInfo<AssetsChildren> page = assetsChildrenService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsChildrenService.getById(id);
    }

   /**
     *  AssetsTypeId查询
     */
    @RequestMapping(value = "getACByAssetsTypeId.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getACByAssetsTypeId(@RequestBody AssetsChildren beanParam) {
        return assetsChildrenService.getACByAssetsTypeId(beanParam);
    }

    /**
     *  添加编辑
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsChildren(@RequestBody AssetsChildren assetsChildren){

        return assetsChildrenService.addAssetsChildren(assetsChildren);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "delAssetsChildren.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delAssetsChildren(Integer ids){

        return assetsChildrenService.delAssetsChildren(ids);
    }

}
