package com.jsdc.itss.controller;

import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.EventParamConfig;
import com.jsdc.itss.service.EventParamConfigService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 事件参数管理表
 *
 * @Author thr
 * @create 2022-07-13 14:17:38
 */
@Controller
@RequestMapping("/eventParamConfig")
public class EventParamConfigController extends BaseController {

    @Autowired
    private EventParamConfigService eventParamConfigService;

    /**
     * ID查询
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById() {
        return eventParamConfigService.getConfig();
    }

    /**
     * 添加
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addEventParamConfig(EventParamConfig eventParamConfig) {
        return eventParamConfigService.addEventParamConfig(eventParamConfig);
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editEventParamConfig(EventParamConfig eventParamConfig) {
        return eventParamConfigService.editEventParamConfig(eventParamConfig);
    }
}
