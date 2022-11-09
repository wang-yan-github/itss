package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsSupplierContractMap;
import com.jsdc.itss.service.AssetsSupplierContractMapService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Controller
@RequestMapping("/assetsSupplierContractMap")
public class AssetsSupplierContractMapController extends BaseController {

    @Autowired
    private AssetsSupplierContractMapService assetsSupplierContractMapService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsSupplierContractMap beanParam) {
        PageInfo<AssetsSupplierContractMap> page = assetsSupplierContractMapService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsSupplierContractMapService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsSupplierContractMap(AssetsSupplierContractMap assetsSupplierContractMap){

        return assetsSupplierContractMapService.addAssetsSupplierContractMap(assetsSupplierContractMap);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsSupplierContractMap(AssetsSupplierContractMap assetsSupplierContractMap){

        return assetsSupplierContractMapService.editAssetsSupplierContractMap(assetsSupplierContractMap);
    }
}
