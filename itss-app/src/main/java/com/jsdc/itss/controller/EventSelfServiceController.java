package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.EventRequestType;
import com.jsdc.itss.model.vo.DataVo;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.service.EventRequestTypeService;
import com.jsdc.itss.service.EventService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自助服务控制器
 *
 * @author thr
 */
@RestController
@RequestMapping("/app/eventSelfService")
public class EventSelfServiceController {

    @Autowired
    private EventService eventService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EventRequestTypeService eventRequestTypeService;

    /**
     * 查询我的事件列表
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, Event event) {
        event.setUser_id(event.getUser_id());
        PageInfo<Event> page = eventService.toList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }

    /**
     * 事件状态集合
     */
    @RequestMapping(value = "getEventStatusList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getEventStatusList() {
        //单据状态 字典(1.待响应 2.处理中 3暂停 4.处理完成 5.待评价 6.已关闭 7.已撤销)
        Map<String, String> map = new HashMap<>();
        map.put("1", "待响应");
        map.put("2", "处理中");
        map.put("3", "暂停");
        map.put("4", "处理完成");
        map.put("5", "待评价");
        map.put("6", "已关闭");
        map.put("7", "已撤销");
        List<DataVo> list = new ArrayList<>();
        for (Map.Entry<String, String> row : map.entrySet()) {
            DataVo vo = new DataVo();
            vo.setCode(row.getKey());
            vo.setName(row.getValue());
            list.add(vo);
        }
        return ResultInfo.success(list);
    }

    /**
     * 请求类型字典
     */
    @RequestMapping(value = "getRequestTypeList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(EventRequestType eventRequestType) {
        JSONObject jsonObject = new JSONObject();
        List<String> nameList = new ArrayList<>();
        List<EventRequestType> list = eventRequestTypeService.getList(eventRequestType);
        list.forEach(x -> {
            x.setName(x.getRequest_type_name());
            nameList.add(x.getName());
        });
        jsonObject.put("list", list);
        jsonObject.put("nameList", nameList);
        return ResultInfo.success(jsonObject);
    }

}
