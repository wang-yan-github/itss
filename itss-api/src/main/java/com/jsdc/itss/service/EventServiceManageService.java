package com.jsdc.itss.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.MD5Utils;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventServiceManageDao;
import com.jsdc.itss.dao.SysUserDao;
import com.jsdc.itss.mapper.EventBillingDesignateMapper;
import com.jsdc.itss.mapper.EventServiceManageMapper;
import com.jsdc.itss.mapper.SysUserMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventServiceManageVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventServiceManageService extends BaseService<EventServiceManageDao, EventServiceManage> {
    @Autowired
    private EventServiceManageMapper eventServiceManageMapper;

    @Autowired
    private EventBillingDesignateMapper eventBillingDesignateMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysWorkingDaysService sysWorkingDaysService;

    /**
     * Author wzn
     * Date 2022/3/7 16:45
     */
    public void add(EventServiceManageVo eventServiceManageVo) {
        EventServiceManage eventServiceManage = eventServiceManageVo.getEventServiceManage();
        //服务群组主表新增
        // 删除状态
        eventServiceManageVo.getEventServiceManage().setIs_del(String.valueOf(0));
        // 创建时间
        eventServiceManageVo.getEventServiceManage().setCreate_time(new Date());
        // 创建者
        eventServiceManageVo.getEventServiceManage().setCreate_user(sysUserService.getUser().getId());
        eventServiceManageMapper.insert(eventServiceManage);
        if (!CollectionUtils.isEmpty(eventServiceManageVo.getType1())) {
            for (SysUser sysUser : eventServiceManageVo.getType1()) {
                EventBillingDesignate e = new EventBillingDesignate();
                e.setIs_del(String.valueOf(0));
                e.setService_management_id(eventServiceManage.getId());
                e.setEngineer_userid(sysUser.getId());
                // 创建时间
                e.setCreate_time(new Date());
                // 创建者
                e.setCreate_user(sysUserService.getUser().getId());
                e.setType("1");
                eventBillingDesignateMapper.insert(e);
            }
        }
        if (!CollectionUtils.isEmpty(eventServiceManageVo.getType2())) {
            for (SysUser sysUser : eventServiceManageVo.getType2()) {
                EventBillingDesignate e = new EventBillingDesignate();
                e.setService_management_id(eventServiceManage.getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                // 创建时间
                e.setCreate_time(new Date());
                // 创建者
                e.setCreate_user(sysUserService.getUser().getId());
                e.setType("2");
                eventBillingDesignateMapper.insert(e);
            }
        }
        if (!CollectionUtils.isEmpty(eventServiceManageVo.getType3())) {
            for (SysUser sysUser : eventServiceManageVo.getType3()) {
                EventBillingDesignate e = new EventBillingDesignate();
                e.setService_management_id(eventServiceManage.getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                // 创建时间
                e.setCreate_time(new Date());
                // 创建者
                e.setCreate_user(sysUserService.getUser().getId());
                e.setType("3");
                eventBillingDesignateMapper.insert(e);
            }
        }

    }

    /**
     * 服务群组列表接口
     * Author wzn
     * Date 2022/3/21 16:16
     */
    public PageInfo<EventServiceManage> toList(EventServiceManage eventServiceManage) {
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(eventServiceManage.getName())) {
            queryWrapper.like("name", eventServiceManage.getName());
        }
        if (StringUtils.isNotEmpty(eventServiceManage.getIs_use())) {
            queryWrapper.eq("is_use", eventServiceManage.getIs_use());
        }
        PageHelper.startPage(eventServiceManage.getPageNo(), eventServiceManage.getPageSize());
        List<EventServiceManage> eventServiceManageList = selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(eventServiceManageList)) {
            for (EventServiceManage e : eventServiceManageList) {
                String principalName = "";
                String engineerName = "";

                //字典 类型1 开单、指派、转单  2处理  3  负责人
                List<EventBillingDesignate> eventBillingDesignateList = eventBillingDesignateMapper.selectByEventId(e.getId(), "3");
                List<EventBillingDesignate> eventBillingDesignateList2 = eventBillingDesignateMapper.selectByEventId(e.getId(), "2");
                if (!CollectionUtils.isEmpty(eventBillingDesignateList)) {
                    for (int i = 0; i < eventBillingDesignateList.size(); i++) {
                        if (null != eventBillingDesignateList.get(i)) {
                            principalName += eventBillingDesignateList.get(i).getName();
                            if (i < eventBillingDesignateList.size() - 1) {
                                //负责人名称
                                principalName += ",";
                            }
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(eventBillingDesignateList2)) {
                    for (int i = 0; i < eventBillingDesignateList2.size(); i++) {
                        if (null != eventBillingDesignateList2.get(i)) {
                            engineerName += eventBillingDesignateList2.get(i).getName();
                            if (i < eventBillingDesignateList2.size() - 1) {
                                //工程师
                                engineerName += ",";
                            }
                        }
                    }
                }
                //负责人名称
                e.setPrincipalName(principalName);
                //工程师
                e.setEngineerName(engineerName);
                SysUser sysUser = sysUserMapper.selectById(e.getAuto_engineer());
                if (Base.notEmpty(sysUser)) {
                    //处理人为空自动指定工程师
                    e.setAuto_engineer_name(sysUser.getName());
                }
            }
        }
        PageInfo<EventServiceManage> page = new PageInfo<>(eventServiceManageList);
        return page;
    }


    /**
     * 删除
     *
     * @param assetsBrand
     * @return
     */
    public ResultInfo del(Integer id) {
        EventServiceManage eventServiceManage = new EventServiceManage();
        eventServiceManage.setId(id);
        // 修改者
        eventServiceManage.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        eventServiceManage.setUpdate_time(new Date());
        eventServiceManage.setIs_del("1");
        updateById(eventServiceManage);
        return ResultInfo.success();
    }

    public void edit(EventServiceManageVo eventServiceManageVo) {
        eventServiceManageMapper.updateById(eventServiceManageVo.getEventServiceManage());
        //子表先删再增
        QueryWrapper<EventBillingDesignate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_management_id", eventServiceManageVo.getEventServiceManage().getId());
        eventBillingDesignateMapper.delete(queryWrapper);
        if (!CollectionUtils.isEmpty(eventServiceManageVo.getType1())) {
            for (SysUser sysUser : eventServiceManageVo.getType1()) {
                EventBillingDesignate e = new EventBillingDesignate();
                e.setService_management_id(eventServiceManageVo.getEventServiceManage().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                // 创建时间
                e.setCreate_time(new Date());
                // 创建者
                e.setCreate_user(sysUserService.getUser().getId());
                e.setType("1");
                eventBillingDesignateMapper.insert(e);
            }
        }
        if (!CollectionUtils.isEmpty(eventServiceManageVo.getType2())) {
            for (SysUser sysUser : eventServiceManageVo.getType2()) {
                EventBillingDesignate e = new EventBillingDesignate();
                e.setService_management_id(eventServiceManageVo.getEventServiceManage().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                // 创建时间
                e.setCreate_time(new Date());
                // 创建者
                e.setCreate_user(sysUserService.getUser().getId());
                e.setType("2");
                eventBillingDesignateMapper.insert(e);
            }
        }
        if (!CollectionUtils.isEmpty(eventServiceManageVo.getType3())) {
            for (SysUser sysUser : eventServiceManageVo.getType3()) {
                EventBillingDesignate e = new EventBillingDesignate();
                e.setService_management_id(eventServiceManageVo.getEventServiceManage().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                // 创建时间
                e.setCreate_time(new Date());
                // 创建者
                e.setCreate_user(sysUserService.getUser().getId());
                e.setType("3");
                eventBillingDesignateMapper.insert(e);
            }
        }
    }


    /**
     * 根据知识查询范围获取服务群组集合
     *
     * @param knowledgeRangeId
     * @return
     */
    public List<EventServiceManage> getByKnowRangeId(Integer knowledgeRangeId) {
        return eventServiceManageMapper.getByKnowRangeId(knowledgeRangeId);
    }

    /**
     * 详情接口
     * Author wzn
     * Date 2022/3/23 10:34
     */
    public EventServiceManageVo selectEventDetailById(Integer id) {
        EventServiceManageVo eventServiceManageVo = new EventServiceManageVo();
        EventServiceManage eventServiceManage = eventServiceManageMapper.selectById(id);
        if (null != eventServiceManage) {
            if (Base.notEmpty(eventServiceManage.getWork_id())) {
                //工作日 名称
                SysWorkingDays sysWorkingDays = sysWorkingDaysService.selectById(eventServiceManage.getWork_id());
                eventServiceManage.setWork_name(null == sysWorkingDays?"":sysWorkingDays.getWork_name());
            }
            if (Base.notEmpty(eventServiceManage.getAuto_engineer())) {
                //处理人为空自动指定工程师 名称
                SysUser sysUser = sysUserService.selectById(eventServiceManage.getAuto_engineer());
                eventServiceManage.setAuto_engineer_name(sysUser.getName());
            }

            eventServiceManageVo.setEventServiceManage(eventServiceManage);
            //开单、指派、转单
            QueryWrapper<EventBillingDesignate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("service_management_id", eventServiceManage.getId());
            queryWrapper.eq("type", "1");
            List<EventBillingDesignate> eventBillingDesignateList = eventBillingDesignateMapper.selectList(queryWrapper);
            List<SysUser> sysUsers1 = new ArrayList<>();
            if (!CollectionUtils.isEmpty(eventBillingDesignateList)) {
                List<Integer> ids = eventBillingDesignateList.stream().map(EventBillingDesignate::getEngineer_userid).collect(Collectors.toList());
                SysUser sysUser = new SysUser();
                sysUser.setIds(ids);
                eventServiceManageVo.setSysUserList1(sysUserService.getList(sysUser));
            }
            //2处理工程师
            List<SysUser> sysUsers2 = new ArrayList<>();
            QueryWrapper<EventBillingDesignate> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("service_management_id", eventServiceManage.getId());
            queryWrapper2.eq("type", "2");
            List<EventBillingDesignate> eventBillingDesignateList2 = eventBillingDesignateMapper.selectList(queryWrapper2);
            if (!CollectionUtils.isEmpty(eventBillingDesignateList2)) {
                List<Integer> ids = eventBillingDesignateList2.stream().map(EventBillingDesignate::getEngineer_userid).collect(Collectors.toList());
                SysUser sysUser = new SysUser();
                sysUser.setIds(ids);
                eventServiceManageVo.setSysUserList2(sysUserService.getList(sysUser));
            }

            //3负责人
            List<SysUser> sysUsers3 = new ArrayList<>();
            QueryWrapper<EventBillingDesignate> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("service_management_id", eventServiceManage.getId());
            queryWrapper3.eq("type", "3");
            List<EventBillingDesignate> eventBillingDesignateList3 = eventBillingDesignateMapper.selectList(queryWrapper3);
            if (!CollectionUtils.isEmpty(eventBillingDesignateList3)) {
                List<Integer> ids = eventBillingDesignateList3.stream().map(EventBillingDesignate::getEngineer_userid).collect(Collectors.toList());
                SysUser sysUser = new SysUser();
                sysUser.setIds(ids);
                eventServiceManageVo.setSysUserList3(sysUserService.getList(sysUser));
            }
        }

        return eventServiceManageVo;

    }

    /**
     * 服务群组下列数据接口
     * Author wzn
     * Date 2022/3/23 17:27
     */
    public List<EventServiceManage> upList() {
        List<EventServiceManage> eventServiceManageList = eventServiceManageMapper.selectList(Wrappers.<EventServiceManage>lambdaQuery()
                .eq(EventServiceManage::getIs_del, "0")
                .eq(EventServiceManage::getIs_use, "1")
        );
        return eventServiceManageList;
    }


    public ResultInfo getAllInfo(EventServiceManage eventServiceManage) {
        QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(eventServiceManage.getName())) {
            queryWrapper.like("name", eventServiceManage.getName());
        }
        if (StringUtils.isNotEmpty(eventServiceManage.getIs_use())) {
            queryWrapper.eq("is_use", eventServiceManage.getIs_use());
        }
        List<EventServiceManage> eventServiceManageList = selectList(queryWrapper);
        if (!eventServiceManageList.isEmpty()) {
            for (EventServiceManage e : eventServiceManageList) {
                String principalName = "";
                String engineerName = "";
                List<EventBillingDesignate> eventBillingDesignateList = eventBillingDesignateMapper.selectByEventId(e.getId(), "3");
                List<EventBillingDesignate> eventBillingDesignateList2 = eventBillingDesignateMapper.selectByEventId(e.getId(), "2");
                if (!eventBillingDesignateList.isEmpty()) {
                    for (int i = 0; i < eventBillingDesignateList.size(); i++) {
                        principalName += eventBillingDesignateList.get(i).getName();
                        if (i < eventBillingDesignateList.size() - 1) {
                            principalName += ",";
                        }
                    }
                }
                if (!eventBillingDesignateList2.isEmpty()) {
                    for (int i = 0; i < eventBillingDesignateList2.size(); i++) {
                        engineerName += eventBillingDesignateList2.get(i).getName();
                        if (i < eventBillingDesignateList2.size() - 1) {
                            engineerName += ",";
                        }
                    }
                }
                e.setPrincipalName(principalName);
                e.setEngineerName(engineerName);
                SysUser sysUser = sysUserMapper.selectById(e.getAuto_engineer());
                if (null != sysUser) {
                    e.setAuto_engineer(sysUser.getName());
                }
            }
        }
        return ResultInfo.success(eventServiceManageList);

    }

    /**
     * 根据用户集合得到用户下的服务群组
     * @param userIds
     * @return
     */
    public List<SysUser> getEventServiceManageByUserIds(List<Integer> userIds) {
        List<SysUser> sysUserList = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, userIds));
        for (SysUser sysUser : sysUserList) {
            //根据用户得到服务群组
            List<EventBillingDesignate> eventServiceManageList = eventBillingDesignateMapper.selectList(Wrappers.<EventBillingDesignate>lambdaQuery()
                    .eq(EventBillingDesignate::getIs_del, "0")
                    .eq(EventBillingDesignate::getEngineer_userid, sysUser.getId())
                    .eq(EventBillingDesignate::getType, "1")
            );
           if(CollectionUtils.isEmpty(eventServiceManageList)){
               continue;
           }
           List<Integer> eventIds = eventServiceManageList.stream().map(EventBillingDesignate::getService_management_id).collect(Collectors.toList());
           //根据服务群组id查询服务群组信息
           List<EventServiceManage> eventServiceManageList1 = selectList(Wrappers.<EventServiceManage>lambdaQuery()
                   .eq(EventServiceManage::getIs_del, "0")
                   .in(EventServiceManage::getId, eventIds)
           );
            if (CollectionUtils.isEmpty(eventServiceManageList1)) {
                continue;
            }
            //取第一个服务群组信息
            EventServiceManage eventServiceManage = eventServiceManageList1.get(0);
            //得到 处理人为空自动指定工程师
            SysUser user1 = sysUserMapper.selectById(eventServiceManage.getAuto_engineer());
            sysUser.setAuto_engineer(user1 == null? "" : user1.getName());
            //得到非一线组
            sysUser.setNonFirstLineGroup(eventServiceManage.getNonFirstLineGroup());
            //得到负责人
            List<EventBillingDesignate> fzrList = eventBillingDesignateMapper.selectList(Wrappers.<EventBillingDesignate>lambdaQuery()
                    .eq(EventBillingDesignate::getIs_del, "0")
                    .eq(EventBillingDesignate::getService_management_id, eventServiceManage.getId())
                    .eq(EventBillingDesignate::getType, "2")
            );
            if(!CollectionUtils.isEmpty(fzrList)){
                //取集合中user集合
                List<Integer> userIds1 = fzrList.stream().map(EventBillingDesignate::getEngineer_userid).collect(Collectors.toList());
                List<SysUser> sysUserList1 = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, userIds1));
                // 得到集合中的name,拼接成字符串
                String name = "";
                for (SysUser sysUser1 : sysUserList1) {
                    name += sysUser1.getName();
                    if (sysUserList1.indexOf(sysUser1) != sysUserList1.size() - 1) {
                        name += ",";
                    }
                }
                sysUser.setPrincipalName(name);
            }
            //得到工程师
            List<EventBillingDesignate> gcList = eventBillingDesignateMapper.selectList(Wrappers.<EventBillingDesignate>lambdaQuery()
                    .eq(EventBillingDesignate::getIs_del, "0")
                    .eq(EventBillingDesignate::getService_management_id, eventServiceManage.getId())
                    .eq(EventBillingDesignate::getType, "3")
            );
            if(!CollectionUtils.isEmpty(gcList)){
                //取集合中user集合
                List<Integer> userIds1 = gcList.stream().map(EventBillingDesignate::getEngineer_userid).collect(Collectors.toList());
                List<SysUser> sysUserList1 = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, userIds1));
                // 得到集合中的name,拼接成字符串
                String name = "";
                for (SysUser sysUser1 : sysUserList1) {
                    name += sysUser1.getName();
                    if (sysUserList1.indexOf(sysUser1) != sysUserList1.size() - 1) {
                        name += ",";
                    }
                }
                sysUser.setEngineerName(name);
            }
        }
        return sysUserList;
    }
}
