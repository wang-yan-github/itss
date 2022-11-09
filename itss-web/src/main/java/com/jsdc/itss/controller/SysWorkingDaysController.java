package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.SysWorkingDays;
import com.jsdc.itss.model.SysWorkingDaysCalendar;
import com.jsdc.itss.service.SysWorkingDaysService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作日管理控制器
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Controller
@RequestMapping("/sysWorkingDays")
public class SysWorkingDaysController extends BaseController {

    @Autowired
    private SysWorkingDaysService sysWorkingDaysService;

    /**
     * 分页
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */

    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,  SysWorkingDays beanParam) {
        PageInfo<SysWorkingDays> page = sysWorkingDaysService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }


    /**
     * 获取所有默认状态的日历
     * @param
     * @return
     */

    @RequestMapping(value = "getAllList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllList() {
        List<SysWorkingDays> list = sysWorkingDaysService.getAllList();
        return ResultInfo.success(list);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysWorkingDaysService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysWorkingDays(@RequestBody SysWorkingDays sysWorkingDays){

        return sysWorkingDaysService.addSysWorkingDays(sysWorkingDays);
    }


    /**
     *  获取工作时间
     */
    @RequestMapping(value = "getWorkTime.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getWorkTime(@RequestBody SysWorkingDays sysWorkingDays){
        return sysWorkingDaysService.getWorkTime(sysWorkingDays);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteSysWorkingDays(Integer ids){

        return sysWorkingDaysService.deleteSysWorkingDays(ids);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysWorkingDays(SysWorkingDays sysWorkingDays){

        return sysWorkingDaysService.editSysWorkingDays(sysWorkingDays);
    }

    /**
    *工作日下拉数据接口
    * Author wzn
    * Date 2022/3/22 8:37
    */
    @PostMapping("/dropDownData")
    @ResponseBody
    public AjaxResult dropDownData(){
        return AjaxResult.success(sysWorkingDaysService.dropDownData()) ;
    }


    @RequestMapping("/selectCalendar")
    @ResponseBody
    public ResultInfo selectCalendar(@RequestBody  SysWorkingDaysCalendar sysWorkingDaysCalendar){
        List<SysWorkingDaysCalendar> list =sysWorkingDaysService.selectCalendar(sysWorkingDaysCalendar);
        return ResultInfo.success(list);
    }

    @RequestMapping("/setCalendar")
    @ResponseBody
    public ResultInfo setCalendar(@RequestBody SysWorkingDaysCalendar sysWorkingDaysCalendar){
        return sysWorkingDaysService.setCalendar(sysWorkingDaysCalendar);
    }
}
