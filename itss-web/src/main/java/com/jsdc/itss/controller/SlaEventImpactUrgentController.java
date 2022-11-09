package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SlaEventFirst;
import com.jsdc.itss.model.SlaEventImpact;
import com.jsdc.itss.model.SlaEventImpactUrgent;
import com.jsdc.itss.service.SlaEventImpactUrgentService;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SlaEventImpactUrgentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SlaEventImpactUrgentController
 * Description: 事件影响度紧急度
 * date: 2022/3/7 14:22
 *
 * @author bn
 */
@Controller
@RequestMapping("slaEventImpactUrgent")
public class SlaEventImpactUrgentController extends BaseController {

    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;


    /**
     * 列表查询
     *
     * @return
     * @author bn
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(SlaEventImpactUrgent slaEventImpactUrgent) {
        return ResultInfo.success(slaEventImpactUrgentService.toList(slaEventImpactUrgent));
    }

    /**
     * 根据紧急度、影响度查询优先级
     *
     * @return
     * @author bn
     */
    @RequestMapping(value = "getFirst.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getFirst(@RequestBody SlaEventImpactUrgent slaEventImpactUrgent) {
        return ResultInfo.success(slaEventImpactUrgentService.getFirst(slaEventImpactUrgent));
    }

    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(@RequestBody String event_impact_urgent) {
        return slaEventImpactUrgentService.toEdit(event_impact_urgent);
    }
}
