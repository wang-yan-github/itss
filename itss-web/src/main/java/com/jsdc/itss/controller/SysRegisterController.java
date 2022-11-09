package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysRegister;
import com.jsdc.itss.service.SysRegisterService;
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
@RequestMapping("/sysRegister")
public class SysRegisterController extends BaseController {

    @Autowired
    private SysRegisterService sysRegisterService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysRegister beanParam) {
        PageInfo<SysRegister> page = sysRegisterService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysRegisterService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysRegister(SysRegister sysRegister){

        return sysRegisterService.addSysRegister(sysRegister);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysRegister(SysRegister sysRegister){

        return sysRegisterService.editSysRegister(sysRegister);
    }
}
