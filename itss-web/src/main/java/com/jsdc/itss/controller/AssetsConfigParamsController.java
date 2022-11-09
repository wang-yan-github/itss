package com.jsdc.itss.controller;


import com.jsdc.itss.model.AssetsConfigParams;
import com.jsdc.itss.service.AssetsConfigParamsService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("assetsConfigParams")
public class AssetsConfigParamsController {

    @Autowired
    private AssetsConfigParamsService assetsConfigParamsService;


    /**
     * 查询
     */
    @RequestMapping(value = "getOne.do", method = RequestMethod.POST)
    public ResultInfo getOneInfo(){
       return assetsConfigParamsService.getOne();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "updateById.do", method = RequestMethod.POST)
    public ResultInfo updateById(@RequestBody AssetsConfigParams assetsConfigParams){
        return assetsConfigParamsService.repairById(assetsConfigParams);
    }
}
