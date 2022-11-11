package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysPrint;
import com.jsdc.itss.service.SysPrintService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 打印模板管理控制器
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Controller
@RequestMapping("/sysPrint")
public class SysPrintController extends BaseController {

    @Autowired
    private SysPrintService sysPrintService;

    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,  SysPrint beanParam) {
        PageInfo<SysPrint> page = sysPrintService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysPrintService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysPrint(@RequestBody SysPrint sysPrint){
        return sysPrintService.addSysPrint(sysPrint);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysPrint(SysPrint sysPrint){

        return sysPrintService.editSysPrint(sysPrint);
    }


    /**
     *  删除
     */
    @RequestMapping(value = "deleteById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteById(Integer id){
        return sysPrintService.deleteById(id);
    }
}
