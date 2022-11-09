package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.SlaEventImpact;
import com.jsdc.itss.model.SlaEventUrgent;
import com.jsdc.itss.service.SlaEventUrgentService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SlaEventUrgentController
 * Description: 事件紧急度
 * date: 2022/3/7 14:23
 *
 * @author bn
 */
@Controller
@RequestMapping("slaEventUrgent")
public class SlaEventUrgentController extends BaseController {


    @Autowired
    private SlaEventUrgentService slaEventUrgentService;

    /**
     * 分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @param slaEventUrgent
     * @return
     * @author bn
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             SlaEventUrgent slaEventUrgent) {
        PageInfo<SlaEventUrgent> page = slaEventUrgentService.toList(pageIndex, pageSize, slaEventUrgent);
        return ResultInfo.success(page);
    }

    /**
     * 列表查询
     *
     * @param slaEventUrgent
     * @return
     * @author bn
     */
    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody SlaEventUrgent slaEventUrgent) {
        List<SlaEventUrgent> list = slaEventUrgentService.getList(slaEventUrgent);
        return ResultInfo.success(list);
    }

    /**
     * 添加
     *
     * @param slaEventUrgent
     * @return
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestBody SlaEventUrgent slaEventUrgent) {
        return slaEventUrgentService.toAdd(slaEventUrgent);
    }

    /**
     * 编辑
     *
     * @param slaEventUrgent
     * @return
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(@RequestBody SlaEventUrgent slaEventUrgent) {
        return slaEventUrgentService.toEdit(slaEventUrgent);
    }

    @RequestMapping(value = "upSlaEventImpactUrgent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upSlaEventImpactUrgent() {
        return AjaxResult.success(slaEventUrgentService.upSlaEventImpactUrgent());
    }


}
