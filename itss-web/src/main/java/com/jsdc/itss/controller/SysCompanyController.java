package com.jsdc.itss.controller;


import com.jsdc.itss.model.SysCompany;

import com.jsdc.itss.service.SysCompanyService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SysCompanyController
 * Description:
 *
 * @author xuaolong
 */
@RestController
@RequestMapping("syscompany")
public class SysCompanyController {

    @Autowired
    private SysCompanyService sysCompanyService;



    /**
     * create by xuaolong
     * description: 分页查询
     * @param sysCompany
     * @param pageIndex
     * @param pageSize
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("getPage.do")
    @ResponseBody
    public ResultInfo getPage(SysCompany sysCompany,
                              @RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return sysCompanyService.getPage(sysCompany, pageIndex, pageSize);
    }


    /**
     * create by xuaolong
     * description: 新增公司
     * @param sysCompany
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ResultInfo save(@RequestBody SysCompany sysCompany) {
        return sysCompanyService.addSysCompany(sysCompany);
    }


    /**
     * create by xuaolong
     * description: 修改公司
     * @param sysCompany
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ResultInfo update(@RequestBody SysCompany sysCompany) {
        return sysCompanyService.updateSysCompany(sysCompany);
    }


    /**
     * create by xuaolong
     * description: 删除公司信息
     * @param id
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("deleteSysCompany.do")
    @ResponseBody
    public ResultInfo deleteSysCompany(Integer id) {
        return ResultInfo.success(sysCompanyService.deleteSysCompany(id));
    }


    /**
    *公司下拉数据接口
    * Author wzn
    * Date 2022/3/24 8:56
    */
    @RequestMapping("upCompany")
    @ResponseBody
    public ResultInfo upCompany() {
        return ResultInfo.success(sysCompanyService.upCompany());
    }

    /**
     * 根据公司得到公司下的所有用户
     */
    @RequestMapping("getCompanyUser")
    @ResponseBody
    public ResultInfo getCompanyUser(@RequestBody SysCompany sysCompany) {
        return ResultInfo.success(sysCompanyService.getUserByCompany(sysCompany.getId()));
    }




}
