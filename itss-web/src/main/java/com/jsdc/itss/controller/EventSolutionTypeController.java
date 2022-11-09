package com.jsdc.itss.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.itss.model.EventSolutionType;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.service.EventSolutionTypeService;
import com.jsdc.itss.service.SysCompanyService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("eventSolutionType")
public class EventSolutionTypeController {

    @Autowired
    private EventSolutionTypeService eventSolutionTypeService;


    /**
     * 解决方式管理列表接口
     * Author wzn
     * Date 2022/3/9 9:42
     */
    @RequestMapping("getPage.do")
    public ResultInfo getPage(EventSolutionType eventSolutionType,
                              @RequestParam(defaultValue = "1") Integer pageIndex,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return eventSolutionTypeService.getPage(eventSolutionType, pageIndex, pageSize);
    }


    /**
     * 新增解决方式
     * Author wzn
     * Date 2022/3/9 9:40
     */
    @RequestMapping("save.do")
    public ResultInfo save(@RequestBody EventSolutionType eventSolutionType) {
        return eventSolutionTypeService.addSysCompany(eventSolutionType);
    }


    /**
     * 解决方式修改接口
     * Author wzn
     * Date 2022/3/9 9:47
     */
    @RequestMapping("update.do")
    public ResultInfo update(@RequestBody EventSolutionType eventSolutionType) {
        return eventSolutionTypeService.updateSysCompany(eventSolutionType);
    }


    /**
     * schemeClassification
     * 解决方式删除接口
     * Author wzn
     * Date 2022/3/9 9:48
     */
    @RequestMapping("deleteSysCompany.do")
    public ResultInfo deleteSysCompany(Integer id) {
        return ResultInfo.success(eventSolutionTypeService.deleteSysCompany(id));
    }

    @RequestMapping("upEventSolutionType")
    public ResultInfo upEventSolutionType() {
        return ResultInfo.success(eventSolutionTypeService.upEventSolutionType());
    }

    /**
     * 解决方式下拉菜单
     */
    @RequestMapping("getAllTypeInfo")
    public ResultInfo getAllTypeInfo() {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<EventSolutionType> list = eventSolutionTypeService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }


}
