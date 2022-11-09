package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsSupplierContractUser;
import com.jsdc.itss.service.AssetsSupplierContractUserService;
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
@RequestMapping("/assetsSupplierContractUser")
public class AssetsSupplierContractUserController extends BaseController {

    @Autowired
    private AssetsSupplierContractUserService assetsSupplierContractUserService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsSupplierContractUser beanParam) {
        PageInfo<AssetsSupplierContractUser> page = assetsSupplierContractUserService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsSupplierContractUserService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsSupplierContractUser(AssetsSupplierContractUser assetsSupplierContractUser){

        return assetsSupplierContractUserService.addAssetsSupplierContractUser(assetsSupplierContractUser);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsSupplierContractUser(AssetsSupplierContractUser assetsSupplierContractUser){

        return assetsSupplierContractUserService.editAssetsSupplierContractUser(assetsSupplierContractUser);
    }
}
