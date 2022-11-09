package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysExtendFormSee;
import com.jsdc.itss.service.SysExtendFormSeeService;
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
@RequestMapping("/sysExtendFormSee")
public class SysExtendFormSeeController extends BaseController {

    @Autowired
    private SysExtendFormSeeService sysExtendFormSeeService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysExtendFormSee beanParam) {
        PageInfo<SysExtendFormSee> page = sysExtendFormSeeService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysExtendFormSeeService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysExtendFormSee(SysExtendFormSee sysExtendFormSee){

        return sysExtendFormSeeService.addSysExtendFormSee(sysExtendFormSee);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysExtendFormSee(SysExtendFormSee sysExtendFormSee){

        return sysExtendFormSeeService.editSysExtendFormSee(sysExtendFormSee);
    }
}
