package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.service.*;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.LogVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/eventSource")
public class EventSourceController {

    @Autowired
    private EventSourceService eventSourceService;
    @Autowired
    private EventRequestTypeService eventRequestTypeService;
    @Autowired
    private SlaEventUrgentService slaEventUrgentService;
    @Autowired
    private SlaEventImpactService slaEventImpactService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserEventGroupService sysUserEventGroupService;
    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private EventParamConfigService eventParamConfigService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AssetsManageService assetsManageService;
    @Autowired
    private EventSolutionTypeService eventSolutionTypeService;
    @Autowired
    private SchemeClassificationService schemeClassificationService;
    @Autowired
    private EventBillingDesignateService eventBillingDesignateService;
    @Autowired
    private EventCategoryService eventCategoryService;
    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;


    /**
     * ??????????????????
     * Author wzn
     * Date 2022/3/9 13:54
     */
    @RequestMapping("getList.do")
    public ResultInfo getList(EventSource eventSource) {
        return ResultInfo.success(eventSourceService.getList(eventSource));
    }

    /**
     * ??????????????????
     *
     * @return
     * @author zdq
     */
    @RequestMapping(value = "getEventTypeList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getEventTypeList(EventRequestType eventRequestType) {
        return ResultInfo.success(eventRequestTypeService.getList(eventRequestType));
    }


    /**
     * ???????????????????????????
     *
     * @param slaEventUrgent
     * @return
     * @author bn
     */
    @RequestMapping(value = "getUrgentList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getUrgentList(SlaEventUrgent slaEventUrgent) {
        List<SlaEventUrgent> list = slaEventUrgentService.getList(slaEventUrgent);
        return ResultInfo.success(list);
    }


    /**
     * ???????????????
     *
     * @return
     * @author zdq
     */
    @RequestMapping(value = "getImpactList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getImpactList(SlaEventImpact slaEventImpact) {
        return ResultInfo.success(slaEventImpactService.getList(slaEventImpact));
    }


    /**
     * @param isAll 1:?????? 2????????? 3????????? ????????????
     * @return
     */
    @RequestMapping(value = "getAllManageInfo.do", method = RequestMethod.POST)
    public ResultInfo getAllManageInfo(String isAll, Integer userId) {
        SysUser sysUser = sysUserService.getUserData(userId);
        //?????????????????????????????????
        List<SysUserEventGroup> groups = sysUserEventGroupService.selectList(new QueryWrapper<SysUserEventGroup>().eq("user_id", sysUser.getId()));
        Integer groupIds[] = new Integer[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            groupIds[i] = groups.get(i).getEvent_group_id();
        }
        //????????????????????????
        EventParamConfig eventParamConfig = eventParamConfigService.selectById(1);
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        //???????????????????????????????????????????????????
        if ("1".equals(isAll)) {//??????
            if (!"1".equals(eventParamConfig.getIs_billing())) {
                queryWrapper.in("id", groupIds.length != 0 ? groupIds : new Integer[1]);
            }
        } else if ("2".equals(isAll)) {//??????
            if (!"1".equals(eventParamConfig.getIs_designate())) {
                queryWrapper.in("id", groupIds.length != 0 ? groupIds : new Integer[1]);
            }
        } else if ("3".equals(isAll)) {//??????
            if (!"1".equals(eventParamConfig.getIs_transfer_order())) {
                queryWrapper.in("id", groupIds.length != 0 ? groupIds : new Integer[1]);
            }
        }
        List<EventServiceManage> list = eventServiceManageService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }


    /**
     * ??????????????????
     *
     * @param pageNo
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toAssetsManageList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAssetsManageList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         AssetsManage beanParam) {
        PageInfo<AssetsManage> page = assetsManageService.toList(pageNo, pageSize, beanParam);
        return ResultInfo.success(page);
    }

    /**
     * ???????????????
     * ????????????????????????
     */
    @RequestMapping(value = "toEventList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEventList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  Event event) {
        PageInfo<Event> page = eventService.toList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }


    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(value = "userHistory", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo userHistory(@RequestBody EventVo vo) {
        return ResultInfo.success(eventService.userHistory(vo));
    }


    /**
     * ??????????????????
     * Author wzn
     * Date 2022/3/16 9:16
     */
    @RequestMapping(value = "process", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo process(@RequestParam String body, @RequestParam List<MultipartFile> fileHandle) {
        Event event = JSON.parseObject(body, Event.class);
        return eventService.process(event, fileHandle);
    }

    /**
     * ???????????????????????????
     * Author wzn
     * Date 2022/3/16 9:16
     */
    @RequestMapping(value = "processClose", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo processClose(@RequestBody Event event) {
        event.setSolve_user_id(sysUserService.getUser().getId());
        event.setEx_solve_time(new Date());
        eventService.processClose(event);
        return ResultInfo.success("??????", new LogVo(event.getId(), "?????????????????????", ""));
    }

    /**
     * ????????????
     * Author wzn
     * Date 2022/3/14 15:22
     */
    @RequestMapping(value = "response", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo response(@RequestBody Event event) {
        return eventService.response(event);
    }


    /**
     * ????????????????????????
     * Author wzn
     * Date f 10:56
     */
    @RequestMapping("tree")
    public ResultInfo tree() {
        return ResultInfo.success(schemeClassificationService.getDetailTree());
    }


    /**
     * ????????????????????????
     */
    @RequestMapping("getAllTypeInfo")
    public ResultInfo getAllTypeInfo() {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<EventSolutionType> list = eventSolutionTypeService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping("getGroupPage.do")
    public ResultInfo getGroupPage(Integer group_id) {
        // ??????????????????id????????????id??????
        List<EventBillingDesignate> billingDesignates = eventBillingDesignateService.selectList(Wrappers.<EventBillingDesignate>lambdaQuery()
                .eq(EventBillingDesignate::getService_management_id, group_id)
                .eq(StringUtils.isNotBlank("2"), EventBillingDesignate::getType, "2")
        );
        // ??????userid,???????????????
        List<Integer> userIds = billingDesignates.stream().map(EventBillingDesignate::getEngineer_userid).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIds)) {
            return ResultInfo.success(new PageInfo<>());
        }
        return ResultInfo.success(sysUserService.getList(SysUser.builder().ids(userIds).build()));
    }


    /**
     * ????????????????????????????????????
     * Author wzn
     * Date 2022/3/23 22:31
     */
    @RequestMapping(value = "treeInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo treeInfo(EventCategory eventCategory) {
        List<EventCategory> list = eventCategoryService.tree(eventCategory);
        return ResultInfo.success(list);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return
     * @author bn
     */
    @RequestMapping(value = "getFirst.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getFirst(SlaEventImpactUrgent slaEventImpactUrgent) {
        return ResultInfo.success(slaEventImpactUrgentService.getFirst(slaEventImpactUrgent));
    }

}
