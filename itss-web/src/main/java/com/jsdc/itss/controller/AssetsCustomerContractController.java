package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsCustomerContract;
import com.jsdc.itss.service.AssetsCustomerContractService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.AssetsSupplierContractVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 客户合同管理控制器
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Controller
@RequestMapping("/assetsCustomerContract")
public class AssetsCustomerContractController extends BaseController {

    @Autowired
    private AssetsCustomerContractService assetsCustomerContractService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表查询
     * zdq
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsCustomerContract beanParam) {
        PageInfo<AssetsCustomerContract> page = assetsCustomerContractService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestParam String body,@RequestParam List<MultipartFile> file){
        AssetsCustomerContract bean = JSON.parseObject(body, AssetsCustomerContract.class);
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        if(null != bean.getId() && null != assetsCustomerContractService.selectById(bean.getId())){
            return assetsCustomerContractService.editAssetsCustomerContract(bean,file);
        }
        return assetsCustomerContractService.addAssetsCustomerContract(bean,file);

    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsCustomerContract bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }

        return assetsCustomerContractService.removeById(bean.getId());
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsCustomerContractService.getById(id);
    }

//    /**
//     *  添加
//     */
//    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo addAssetsCustomerContract(AssetsCustomerContract assetsCustomerContract){
//
//        return assetsCustomerContractService.addAssetsCustomerContract(assetsCustomerContract);
//    }
//
//    /**
//     *  编辑
//     */
//    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editAssetsCustomerContract(AssetsCustomerContract assetsCustomerContract){
//
//        return assetsCustomerContractService.editAssetsCustomerContract(assetsCustomerContract);
//    }
}
