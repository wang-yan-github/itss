package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.EventParamConfig;
import com.jsdc.itss.model.EventServiceManage;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.model.SysUserEventGroup;
import com.jsdc.itss.model.vo.EventServiceManageVo;
import com.jsdc.itss.service.EventParamConfigService;
import com.jsdc.itss.service.EventServiceManageService;
import com.jsdc.itss.service.SysUserEventGroupService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: AssetsCompanyController
 * Description:
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("eventServiceManage")
public class EventServiceManageController {
    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private EventParamConfigService eventParamConfigService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserEventGroupService sysUserEventGroupService;
    /**
     * 服务群组管理新增
     * Author wzn
     * Date 2022/3/8 8:38
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsCompany(@RequestBody EventServiceManageVo assetsCompany) {
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("name",assetsCompany.getEventServiceManage().getName());
        Long count  = eventServiceManageService.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在");
        }
        eventServiceManageService.add(assetsCompany);
        return ResultInfo.success();
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo edit(@RequestBody EventServiceManageVo assetsCompany) {
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("name",assetsCompany.getEventServiceManage().getName()).ne("id",assetsCompany.getEventServiceManage().getId());
        Long count  = eventServiceManageService.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在");
        }
        eventServiceManageService.edit(assetsCompany);
        return ResultInfo.success();
    }

    /**
     * 列表查询
     *
     * @param
     * @return
     * @author wh
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    public ResultInfo toList(@RequestBody EventServiceManage eventServiceManage) {
        PageInfo<EventServiceManage> publishCategoryInfoPageInfo = eventServiceManageService.toList(eventServiceManage);
        return ResultInfo.success(publishCategoryInfoPageInfo);
    }

    /**
     * 编辑发布类别
     *
     * @return
     * @author wh
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo del(Integer id) {
        return eventServiceManageService.del(id);
    }

    /**
     * 详情信息
     * Author wzn
     * Date 2022/3/8 9:33
     */
    @RequestMapping(value = "info", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo info(Integer id) {
        return ResultInfo.success(eventServiceManageService.selectEventDetailById(id));
    }

    /**
     * 服务群组下列数据接口
     * Author wzn
     * Date 2022/3/23 17:29
     */
    @RequestMapping(value = "upList", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo upList() {
        return ResultInfo.success(eventServiceManageService.upList());
    }

    /**
     * 获取所有服务群组
     * Author xuaolong
     * Date 2022/3/23 17:29
     */

    @RequestMapping(value = "getAllInfo", method = RequestMethod.POST)
    public ResultInfo getAllInfo(EventServiceManage eventServiceManage) {
        return ResultInfo.success(eventServiceManageService.getAllInfo(eventServiceManage));
    }

    /**
     *
     * @param isAll 1:开单 2：指派 3：转单
     * @return
     */
    @RequestMapping(value = "getAllManageInfo", method = RequestMethod.POST)
    public ResultInfo getAllManageInfo(String isAll) {
        SysUser sysUser = sysUserService.getUser();
        //查询用户所属的服务群组
        List<SysUserEventGroup> groups = sysUserEventGroupService.selectList(new QueryWrapper<SysUserEventGroup>().eq("user_id", sysUser.getId()));
        Integer groupIds [] = new Integer[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            groupIds[i] = groups.get(i).getEvent_group_id();
        }
        //查询事件参数配置
        EventParamConfig eventParamConfig = eventParamConfigService.selectById(1);
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        //传入参数，代表需要判断事件参数配置
        if("1".equals(isAll)){//开单
            if(!"1".equals(eventParamConfig.getIs_billing())){
                queryWrapper.in("id",groupIds.length !=0 ?groupIds:new Integer[1]);
            }
        }else if("2".equals(isAll)){//指派
            if(!"1".equals(eventParamConfig.getIs_designate())){
                queryWrapper.in("id",groupIds.length !=0 ?groupIds:new Integer[1]);
            }
        }else if("3".equals(isAll)){//转单
            if(!"1".equals(eventParamConfig.getIs_transfer_order())){
                queryWrapper.in("id",groupIds.length !=0 ?groupIds:new Integer[1]);
            }
        }
        List<EventServiceManage> list = eventServiceManageService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    /**
     * 根据用户集合得到用户下的服务群组
     */
    @RequestMapping(value = "getEventServiceManageByUserIds", method = RequestMethod.POST)
    public ResultInfo getEventServiceManageByUserIds(@RequestBody List<Integer> userIds) {
        return ResultInfo.success(eventServiceManageService.getEventServiceManageByUserIds(userIds));
    }


}
