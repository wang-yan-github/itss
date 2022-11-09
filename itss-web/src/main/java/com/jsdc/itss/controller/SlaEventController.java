package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SlaEvent;
import com.jsdc.itss.model.SlaEventConfig;
import com.jsdc.itss.service.SlaEventConfigService;
import com.jsdc.itss.service.SlaEventService;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SlaEventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName: SlaEventController
 * Description:
 * date: 2022/3/7 14:16
 *
 * @author bn
 */
@Controller
@RequestMapping("slaEvent")
public class SlaEventController extends BaseController {

    @Autowired
    private SlaEventService slaEventService;
    @Autowired
    private SlaEventConfigService slaEventConfigService;

    /**
     * 根据事件优先级id和事件请求类型id获取数据
     */
    @RequestMapping(value = "getSlaEventConfigInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo<SlaEventConfig> getSlaEventConfigInfo(@RequestBody SlaEventConfig slaEventConfig) {
        QueryWrapper<SlaEventConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("first_id", slaEventConfig.getFirst_id());
        queryWrapper.eq("request_type_id", slaEventConfig.getRequest_type_id());
        queryWrapper.eq("is_del", "0");
        SlaEventConfig slaEventConfig1 = slaEventConfigService.selectOne(queryWrapper);
        return ResultInfo.success(slaEventConfig1);
    }


    /**
     * @param slaEvent
     * @return
     */
    @RequestMapping(value = "getSlaEvent.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getSlaEvent(@RequestBody SlaEvent slaEvent) {

        return slaEventService.getSlaEvent(slaEvent);
    }

    /**
     * @param params
     * @return
     */
    @RequestMapping(value = "getFirstAndRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getFirstAndRequest(@RequestBody Map<String, Object> params) {
        String is_first = params.get("is_first").toString();
        String is_request = params.get("is_request").toString();
        return slaEventService.getFirstAndRequest(is_first, is_request);
    }

    /**
     * 列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @param slaEvent
     * @return
     * @author bn
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             SlaEvent slaEvent) {
        PageInfo<SlaEvent> page = slaEventService.toList(pageIndex, pageSize, slaEvent);
        return ResultInfo.success(page);
    }

    /**
     * 添加
     *
     * @param slaEventVo
     * @return
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestBody SlaEventVo slaEventVo) {
        return slaEventService.toAdd(slaEventVo);
    }


    /**
     * 修改
     *
     * @param slaEventVo
     * @return
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(@RequestBody SlaEventVo slaEventVo) {
        return slaEventService.toEdit(slaEventVo);
    }


}
