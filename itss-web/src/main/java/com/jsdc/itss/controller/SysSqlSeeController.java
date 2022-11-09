package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysSqlSee;
import com.jsdc.itss.service.SysSqlSeeService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Controller
@RequestMapping("/sysSqlSee")
public class SysSqlSeeController extends BaseController {

    @Autowired
    private SysSqlSeeService sysSqlSeeService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysSqlSee beanParam) {
        PageInfo<SysSqlSee> page = sysSqlSeeService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysSqlSeeService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysSqlSee(SysSqlSee sysSqlSee){

        return sysSqlSeeService.addSysSqlSee(sysSqlSee);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysSqlSee(SysSqlSee sysSqlSee){

        return sysSqlSeeService.editSysSqlSee(sysSqlSee);
    }
}
