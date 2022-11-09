package com.jsdc.itss.controller;

import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.service.SysDepartmentService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SysDepartmentController
 * Description:
 *
 * @author xuaolong
 */
@RestController
@RequestMapping("sysdepartment")
public class SysDepartmentController {

    @Autowired
    private SysDepartmentService sysDepartmentService;


    /**
     * create by xuaolong
     * description: 树状图展示部门数据
     * @return ResultInfo
     */
    @RequestMapping("getWithTree.do")
    public ResultInfo getWithTree(SysDepartment sysDepartment){
        return ResultInfo.success(sysDepartmentService.queryWithTree(sysDepartment));
    }


    /**
     * create by xuaolong
     * description: 新增部门
     * @param sysDepartment
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo save(@RequestBody SysDepartment sysDepartment) {
        return sysDepartmentService.addSysDepartment(sysDepartment);
    }


    /**
     * create by xuaolong
     * description: 修改部门
     * @param sysDepartment
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ResultInfo update(@RequestBody SysDepartment sysDepartment) {
        return sysDepartmentService.updateSysDepartment(sysDepartment);

    }


    /**
     * create by xuaolong
     * description: 根据用户ID查询部门信息
     * @param sysDepartment
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("getDepartment.do")
    @ResponseBody
    public ResultInfo getDepartment(@RequestBody SysDepartment sysDepartment) {
        return ResultInfo.success(sysDepartmentService.getOneSysDepartment(sysDepartment.getId()));
    }


    /**
     * create by xuaolong
     * description: 递归删除部门信息
     * @param sysDepartment
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("deleteDepartment.do")
    @ResponseBody
    public ResultInfo deleteDepartment(@RequestBody SysDepartment sysDepartment){
        return  sysDepartmentService.deletedSysDepartment(sysDepartment.getId());
    }

    @GetMapping("getAll.do")
    @ResponseBody
    public ResultInfo getAll(){
        return ResultInfo.success(sysDepartmentService.getAll());
    }







}
