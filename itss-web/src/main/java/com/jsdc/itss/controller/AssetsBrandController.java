package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsBrand;
import com.jsdc.itss.service.AssetsBrandService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: AssetsBrandController
 * Description:
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("assetsBrand")
public class AssetsBrandController {
    @Autowired
    private AssetsBrandService assetsBrandService;

    @Autowired
    private SysUserService sysUserService;

    /**
     *  品牌管理列表查询
     * @author wh
     * @param pageIndex
     * @param pageSize
     * @param assetsBrand
     * @return
     */
    @RequestMapping(value = "toList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String brand_name,
                             AssetsBrand assetsBrand){
        assetsBrand.setBrand_name(brand_name);
        PageInfo<AssetsBrand> assetsBrandPageInfo=assetsBrandService.toList(pageIndex,pageSize,assetsBrand);
        return ResultInfo.success(assetsBrandPageInfo);
    }

    @RequestMapping(value = "getList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(AssetsBrand assetsBrand){
        List<AssetsBrand> assetsBrandPageInfo=assetsBrandService.getList(assetsBrand);
        return ResultInfo.success(assetsBrandPageInfo);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    public ResultInfo saveBean(@RequestBody AssetsBrand bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        if(null != bean.getId() && null != assetsBrandService.selectById(bean.getId())){

            return assetsBrandService.editAssetsBrand(bean);
        }
        return assetsBrandService.addAssetsBrand(bean);
    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    public ResultInfo removeById(@RequestBody AssetsBrand bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        int result = assetsBrandService.update(null, Wrappers.<AssetsBrand>lambdaUpdate()
                .set(AssetsBrand::getIs_del, String.valueOf(1))
                .set(AssetsBrand::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsBrand::getUpdate_time, new Date())
                .eq(AssetsBrand::getId, bean.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }

    /**
     *  品牌管理ID查询
     * @author wh
     * @param id
     * @return
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsBrandService.getById(id);
    }

    /**
     *  添加品牌管理
     * @author wh
     * @param assetsBrand
     * @return
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsBrand(AssetsBrand assetsBrand){

        return assetsBrandService.addAssetsBrand(assetsBrand);
    }

    /**
     *  编辑品牌管理
     * @author wh
     * @param assetsBrand
     * @return
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsBrand(AssetsBrand assetsBrand){

        return assetsBrandService.editAssetsBrand(assetsBrand);
    }

    @RequestMapping("getAll.do")
    public ResultInfo getAll(){
        return ResultInfo.success(assetsBrandService.getAll());
    }
}
