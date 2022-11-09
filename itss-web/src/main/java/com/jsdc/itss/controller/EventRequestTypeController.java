package com.jsdc.itss.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.EventParamConfigService;
import com.jsdc.itss.service.EventRequestTypeService;
import com.jsdc.itss.service.EventServiceCategoryService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: PublishCategoryInfoController
 * Description:
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("eventRequestType")
public class EventRequestTypeController {
    @Autowired
    private EventRequestTypeService eventRequestTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EventServiceCategoryService serviceCategoryService;
    @Autowired
    private EventParamConfigService eventParamConfigService;
    /**
     * 列表查询
     *
     * @return
     * @author wh
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody EventRequestType eventRequestType) {
        PageInfo<EventRequestType> eventRequestTypePageInfo = eventRequestTypeService.toList(eventRequestType, eventRequestType.getPageNo(), eventRequestType.getPageSize());
        return ResultInfo.success(eventRequestTypePageInfo);
    }

    /**
     * 请求类型字典
     *
     * @return
     * @author zdq
     */
    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(@RequestBody EventRequestType eventRequestType) {
        return ResultInfo.success(eventRequestTypeService.getList(eventRequestType));
    }

    /**
     * 添加发布类别
     *
     * @param eventRequestType
     * @return
     * @author wh
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addPublishCategory(@RequestBody EventRequestType eventRequestType) {

        return eventRequestTypeService.addEventRequestType(eventRequestType);
    }

    /**
     * 编辑发布类别
     *
     * @param eventRequestType
     * @return
     * @author wh
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editPublishCategory(@RequestBody EventRequestType eventRequestType) {

        return eventRequestTypeService.editEventRequestType(eventRequestType);
    }

    /**
     * 请求类别删除接口
     * Author wzn
     * Date 2022/3/24 20:16
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult del(Integer id) {
        eventRequestTypeService.delete(id);
        return AjaxResult.success();
    }

    /**
     * 请求类型下拉接口
     * Author wzn
     * Date 2022/3/26 11:15
     */
    @RequestMapping(value = "upList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upList() {
        return AjaxResult.success(eventRequestTypeService.upList());
    }


    @RequestMapping(value = "getKehuType", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getKehuInit(){
        JSONObject jsonObject = new JSONObject();
        SysUser sysUser_old = sysUserService.getUser();
        SysUser sysUser = sysUserService.selectById(sysUser_old.getId());
        EventParamConfig eventParamConfig = eventParamConfigService.selectById(1);
        jsonObject.put("config",eventParamConfig);
        if(sysUser.getServiceTypeId() != null){
            EventServiceCategory eventCategory = serviceCategoryService.selectById(sysUser.getServiceTypeId());
            jsonObject.put("eventCategory",eventCategory);
        }
        return ResultInfo.success(jsonObject);
    }
}
