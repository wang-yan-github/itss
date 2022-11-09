package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsCustomerContractFile;
import com.jsdc.itss.service.AssetsCustomerContractFileService;
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
@RequestMapping("/assetsCustomerContractFile")
public class AssetsCustomerContractFileController extends BaseController {

    @Autowired
    private AssetsCustomerContractFileService assetsCustomerContractFileService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsCustomerContractFile beanParam) {
        PageInfo<AssetsCustomerContractFile> page = assetsCustomerContractFileService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsCustomerContractFileService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsCustomerContractFile(AssetsCustomerContractFile assetsCustomerContractFile){

        return assetsCustomerContractFileService.addAssetsCustomerContractFile(assetsCustomerContractFile);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsCustomerContractFile(AssetsCustomerContractFile assetsCustomerContractFile){

        return assetsCustomerContractFileService.editAssetsCustomerContractFile(assetsCustomerContractFile);
    }
}
