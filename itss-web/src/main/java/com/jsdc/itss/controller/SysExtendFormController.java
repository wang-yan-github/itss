package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysExtendForm;
import com.jsdc.itss.service.SysExtendFormService;
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
@RequestMapping("/sysExtendForm")
public class SysExtendFormController extends BaseController {

    @Autowired
    private SysExtendFormService sysExtendFormService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysExtendForm beanParam) {
        PageInfo<SysExtendForm> page = sysExtendFormService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysExtendFormService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysExtendForm(SysExtendForm sysExtendForm){

        return sysExtendFormService.addSysExtendForm(sysExtendForm);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysExtendForm(SysExtendForm sysExtendForm){

        return sysExtendFormService.editSysExtendForm(sysExtendForm);
    }

    /**
     * 获取全部扩展表单
     * @param sysExtendForm
     * @return
     */
    @RequestMapping(value = "getExtendFormList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getExtendFormList(SysExtendForm sysExtendForm) {
        return sysExtendFormService.getExtendFormList(sysExtendForm);
    }

}
