package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsType;
import com.jsdc.itss.service.AssetsTypeService;
import com.jsdc.itss.vo.AssetsSupplierContractVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author wh
 * @create 2022-03-08 14:15:25
 */
@Controller
@RequestMapping("/assetsType")
public class AssetsTypeController extends BaseController {

    @Autowired
    private AssetsTypeService assetsTypeService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "20") Integer pageSize, AssetsType beanParam) {
        PageInfo<AssetsType> page = assetsTypeService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(AssetsType beanParam) {
        return ResultInfo.success(assetsTypeService.getList(beanParam));
    }

     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsTypeService.getById(id);
    }

    /**
     *  添加 修改
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsType(@RequestParam String body,  MultipartFile file){
        AssetsType assetsType = JSON.parseObject(body, AssetsType.class);
        return assetsTypeService.addAssetsType(assetsType,file);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "delAssetsType.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delAssetsType(Integer ids){
        return assetsTypeService.delAssetsType(ids);
    }

    /**
     *  AssetsTypeId查询
     */
    @RequestMapping(value = "getAssetsType.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAssetsType(@RequestBody AssetsType beanParam) {
        return assetsTypeService.getAssetsType(beanParam);
    }

    @RequestMapping(value = "getAll.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAll(){
        return ResultInfo.success(assetsTypeService.getAll());
    }

    /**
     * 根据name查询一条实例
     * zdq
     */
    @RequestMapping(value = "/{name}/getByName.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getByName(@PathVariable String name){
        if(StringUtils.isBlank(name)){
            return ResultInfo.error("参数不能为空");
        }
        List<AssetsType> list = assetsTypeService.selectList(Wrappers.<AssetsType>lambdaQuery().eq(AssetsType::getName,name));
        if(CollectionUtils.isEmpty(list)){
            return ResultInfo.error("查询不到数据");
        }
        return ResultInfo.success(list.get(0));
    }






}
