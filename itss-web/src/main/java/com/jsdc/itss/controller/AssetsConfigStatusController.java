package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsConfigStatus;
import com.jsdc.itss.service.AssetsConfigStatusService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Controller
@RequestMapping("/assetsConfigStatus")
public class AssetsConfigStatusController extends BaseController {

    @Autowired
    private AssetsConfigStatusService assetsConfigStatusService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsConfigStatus beanParam) {
        PageInfo<AssetsConfigStatus> page = assetsConfigStatusService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsConfigStatusService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsConfigStatus(@RequestBody AssetsConfigStatus assetsConfigStatus){

        return assetsConfigStatusService.addAssetsConfigStatus(assetsConfigStatus);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "delAssetsConfigStatus.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delAssetsConfigStatus(Integer ids){
        return assetsConfigStatusService.delAssetsConfigStatus(ids);
    }
}
