package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.AssetsInventoryDetails;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.service.AssetsInventoryDetailsService;
import com.jsdc.itss.service.EventService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.AssetsInventoryDetailsVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: EventSelfServiceController
 * Description: 自助服务控制器
 *
 * @author zhangdequan
 */
@RestController
@RequestMapping("eventSelfService")
public class EventSelfServiceController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AssetsInventoryDetailsService assetsInventoryDetailsService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询我的事件列表
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Event event) {
        event.setUser_id(sysUserService.getUser().getId());
        PageInfo<Event> page = eventService.getAllByPage(event, pageIndex, pageSize);

        return ResultInfo.success(page);
    }

    /**
     * 修改操作
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    public ResultInfo toEdit(@RequestBody Event event) {
        return eventService.editEventById(event);
    }

    /**
     * 重开操作
     */
    @RequestMapping(value = "toReopen.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toReopen(EventVo eventVo) {
        return eventService.reopenEvent(eventVo);
    }

    /**
     * 资产盘点确认表
     */
    @RequestMapping(value = "propertyAssessmentList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo propertyAssessmentList() {
        return eventService.propertyAssessmentList();
    }

    /**
     * 撤销操作
     */
    @RequestMapping(value = "toRevoke.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toRevoke(EventVo eventVo) {
        return eventService.revokeEvent(eventVo);
    }

//    /**
//     * 新增开单接口
//     * Author wzn
//     * Date 2022/3/14 11:16
//     */
//    @PostMapping("/addEvent")
//    public AjaxResult addEvent(EventVo eventVo){
//        eventService.addEvent(eventVo);
//        return AjaxResult.success() ;
//    }

    /**
     * 评价
     */
    @RequestMapping(value = "evaluateUser.do", method = RequestMethod.POST)
    public ResultInfo evaluateUser(@RequestBody Event event){
        return eventService.evaluateUser(event);
    }

    /**
     * 资金盘点查询
     * zdq
     */
    @RequestMapping(value = "toInventoryList.do", method = RequestMethod.POST)
    public ResultInfo toInventoryList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestBody AssetsInventoryDetailsVo beanParam) {
        PageInfo<AssetsInventoryDetailsVo> page = assetsInventoryDetailsService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 盘点查询根据id
     * zdq
     */
    @RequestMapping(value = "toInventoryById.do", method = RequestMethod.POST)
    public ResultInfo toInventoryById(Integer id) {
        if(null == id){
            return ResultInfo.error("非法入参");
        }

        return ResultInfo.success(assetsInventoryDetailsService.getById(id));
    }


    /**
     *  资金盘点确认
     */
    @RequestMapping(value = "editAssetsInventoryDetails.do",method = RequestMethod.POST)
    public ResultInfo editAssetsInventoryDetails(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.editAssetsInventoryDetails(assetsInventoryDetails);
    }

    /**
     *  批量确认
     */
    @RequestMapping(value = "editBatchDetails.do",method = RequestMethod.POST)
    public ResultInfo editBatchDetails(@RequestBody AssetsInventoryDetailsVo assetsInventoryDetails){

        return assetsInventoryDetailsService.editBatchDetails(assetsInventoryDetails);
    }

}
