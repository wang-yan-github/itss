package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.itss.enums.EventStatusEnums;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.service.EventService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: EventStatisticsController
 * Description: 事件统计控制器
 *
 * @author zhangdequan
 */
@RestController
@RequestMapping("eventStatistics")
public class EventStatisticsController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "toExport.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo export(@RequestBody Map<String,String> params){
        if(null == params){
            return ResultInfo.error("非法入参");
        }
        //工单数量统计
        Map<String, Object> countsMap = new HashMap<>();
        //工单个数
        countsMap.put("eventCount", eventService.selectCount(Wrappers.<Event>lambdaQuery().eq(Event::getIs_del, String.valueOf(0))));
        //完成数
        countsMap.put("finishCount", eventService.selectCount(Wrappers.<Event>lambdaQuery()
                .eq(Event::getStatus, EventStatusEnums.CLOSED.getType()).eq(Event::getIs_del, String.valueOf(0))));

        return null;
    }

}
