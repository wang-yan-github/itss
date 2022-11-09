package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsManageMap;
import com.jsdc.itss.service.AssetsManageMapService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsManageMap")
public class AssetsManageMapController extends BaseController {

    @Autowired
    private AssetsManageMapService assetsManageMapService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsManageMap beanParam) {
        PageInfo<AssetsManageMap> page = assetsManageMapService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsManageMapService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsManageMap(AssetsManageMap assetsManageMap){

        return assetsManageMapService.addAssetsManageMap(assetsManageMap);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsManageMap(AssetsManageMap assetsManageMap){

        return assetsManageMapService.editAssetsManageMap(assetsManageMap);
    }
}
