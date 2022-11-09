package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.SlaEventImpact;
import com.jsdc.itss.service.SlaEventImpactService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SlaEventImpactController
 * Description: 事件影响度
 * date: 2022/3/7 14:18
 *
 * @author bn
 */
@Controller
@RequestMapping("slaEventImpact")
public class SlaEventImpactController extends BaseController {


    @Autowired
    private SlaEventImpactService slaEventImpactService;

    /**
     * 影响度分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @param slaEventImpact
     * @return
     * @author bn
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             SlaEventImpact slaEventImpact) {
        PageInfo<SlaEventImpact> page = slaEventImpactService.toList(pageIndex, pageSize, slaEventImpact);
        return ResultInfo.success(page);
    }

    /**
     * 影响度查询
     *
     * @return
     * @author zdq
     */
    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(@RequestBody SlaEventImpact slaEventImpact) {
        return ResultInfo.success(slaEventImpactService.getList(slaEventImpact));
    }

    /**
     * 添加
     *
     * @param slaEventImpact
     * @return
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestBody SlaEventImpact slaEventImpact) {
        return slaEventImpactService.toAdd(slaEventImpact);
    }

    /**
     * 编辑
     *
     * @param slaEventImpact
     * @return
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(@RequestBody SlaEventImpact slaEventImpact) {
        return slaEventImpactService.toEdit(slaEventImpact);
    }

    /**
     * 影响度下拉接口
     * Author wzn
     * Date 2022/3/26 14:13
     */
    @RequestMapping(value = "upSlaEventImpact", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upSlaEventImpact() {
        return AjaxResult.success(slaEventImpactService.upSlaEventImpact());
    }

}
