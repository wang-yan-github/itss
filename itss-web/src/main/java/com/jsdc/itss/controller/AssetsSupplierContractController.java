package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.model.AssetsSupplierContract;
import com.jsdc.itss.service.AssetsSupplierContractService;
import com.jsdc.itss.vo.AssetsSupplierContractVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Controller
@RequestMapping("/assetsSupplierContract")
public class AssetsSupplierContractController extends BaseController {

    @Autowired
    private AssetsSupplierContractService assetsSupplierContractService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1" ,value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsSupplierContract beanParam) {
        PageInfo<AssetsSupplierContractVo> page = assetsSupplierContractService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsSupplierContractService.getById(id);
    }


    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestParam String body, @RequestParam List<MultipartFile> file){
        if(null == body){
            return ResultInfo.error("非法入参");
        }
        AssetsSupplierContractVo assetsSupplierContractVo = JSON.parseObject(body, AssetsSupplierContractVo.class);
        if(null != assetsSupplierContractVo.getId() && null != assetsSupplierContractService.selectById(assetsSupplierContractVo.getId())){

            return assetsSupplierContractService.editAssetsSupplierContract(assetsSupplierContractVo,file);
        }
        return assetsSupplierContractService.addAssetsSupplierContract(assetsSupplierContractVo,file);

    }


//    /**
//     *  添加
//     */
//    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo addAssetsSupplierContract(AssetsSupplierContractVo assetsSupplierContract){
//
//        return assetsSupplierContractService.addAssetsSupplierContract(assetsSupplierContract);
//    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsSupplierContractVo bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }

        return assetsSupplierContractService.removeById(bean.getId());
    }

//    /**
//     *  编辑
//     */
//    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editAssetsSupplierContract(AssetsSupplierContractVo assetsSupplierContract){
//
//        return assetsSupplierContractService.editAssetsSupplierContract(assetsSupplierContract);
//    }
}
