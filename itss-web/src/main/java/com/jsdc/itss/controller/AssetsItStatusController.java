package com.jsdc.itss.controller;

import com.jsdc.itss.service.AssetsItStatusService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("itstatus")
public class AssetsItStatusController {

    @Autowired
    private AssetsItStatusService statusService;

    /**
     * @description 查询所有的it状态
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/4/7
    */
    @RequestMapping("getAll.do")
    public ResultInfo getAll(){
        return ResultInfo.success(statusService.getAll());
    }
}
