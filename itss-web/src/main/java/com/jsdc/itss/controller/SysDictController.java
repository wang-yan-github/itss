package com.jsdc.itss.controller;

import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysDict;
import com.jsdc.itss.service.SysDictService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ClassName: SysDictController
 * Description:
 * date: 2022/3/28 17:22
 *
 * @author bn
 */
@RequestMapping("sysDict")
@Controller
public class SysDictController extends BaseController {

    @Autowired
    private SysDictService sysDictService;

    @RequestMapping(value = "getDicts.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getDicts(@RequestBody SysDict sysDict){
        List<SysDict> sysDictList=sysDictService.getDicts(sysDict.getDict_type());
        return ResultInfo.success(sysDictList);
    }
}
