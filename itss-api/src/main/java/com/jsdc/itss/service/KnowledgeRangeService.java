package com.jsdc.itss.service;

import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.KnowledgeRangeDao;
import com.jsdc.itss.mapper.EventBillingDesignateMapper;
import com.jsdc.itss.mapper.SysUserMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Service
@Transactional
public class KnowledgeRangeService extends BaseService<KnowledgeRangeDao, KnowledgeRange> {
    @Autowired
    private KnowledgeRangeInfoService knowledgeRangeInfoService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private KnowledgeInfoService knowledgeInfoService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private EventBillingDesignateMapper eventBillingDesignateMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询列表
     *
     * @param pageNo
     * @param pageSize
     * @param name
     * @param isUse
     * @return
     */
    public ResultInfo getList(Integer pageNo, Integer pageSize, String name, String isUse) {
        PageHelper.startPage(pageNo, pageSize);
        List<KnowledgeRange> list = this.selectList(Wrappers.<KnowledgeRange>lambdaQuery().eq(KnowledgeRange::getIs_del, String.valueOf(0))
                .eq(StringUtils.isNotBlank(isUse), KnowledgeRange::getIs_use, isUse)
                .like(StringUtils.isNotBlank(name), KnowledgeRange::getName, name));
        for (KnowledgeRange member : list) {
            //查询关联表
            List<KnowledgeRangeInfo> infoList = knowledgeRangeInfoService.selectList(Wrappers.<KnowledgeRangeInfo>lambdaQuery()
                    .eq(KnowledgeRangeInfo::getKnowledge_range_id, member.getId())
                    .eq(KnowledgeRangeInfo::getIs_del, String.valueOf(0)));
            if (CollectionUtils.isEmpty(infoList)) {
                continue;
            }
            Map<String, List<KnowledgeRangeInfo>> map = infoList.stream().collect(Collectors.groupingBy(KnowledgeRangeInfo::getRange_type));
            //1：角色 2：部门 3：服务群组
            StringBuilder builder = new StringBuilder();
            builder.append("【角色】");
            if (null != map.get("1")) {
                List<KnowledgeRangeInfo> mapList = map.get("1");
                List<SysRole> roles = sysRoleService.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getIs_del, String.valueOf(0))
                        .in(SysRole::getId, mapList.stream().map(KnowledgeRangeInfo::getFk_id).collect(Collectors.toList())));
                member.setRoles(roles);
                builder.append(roles.stream().filter(c -> c.getName() != null).map(SysRole::getName).distinct().collect(Collectors.joining(",")));
            } else {
                builder.append(" 无 ");
            }
            builder.append("【部门】");
            if (null != map.get("2")) {
                List<KnowledgeRangeInfo> mapList = map.get("2");
                List<SysDepartment> depts = sysDepartmentService.selectList(Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getIs_del, String.valueOf(0))
                        .in(SysDepartment::getId, mapList.stream().map(KnowledgeRangeInfo::getFk_id).collect(Collectors.toList())));
                List<Integer> deptIds = new ArrayList<>();
                List<String> deptNames = new ArrayList<>();
                for (SysDepartment dept : depts) {
                    deptIds.add(dept.getId());
                    deptNames.add(dept.getName());
                }
                member.setDeptIds(deptIds);
                member.setDeptNames(deptNames);
                builder.append(deptNames.stream().collect(Collectors.joining(",")));
            } else {
                builder.append("无");
            }
            builder.append("【服务群组】");
            if (null != map.get("3")) {
                List<KnowledgeRangeInfo> mapList = map.get("3");
                List<EventServiceManage> groups = eventServiceManageService.selectList(Wrappers.<EventServiceManage>lambdaQuery().eq(EventServiceManage::getIs_del, String.valueOf(0))
                        .in(EventServiceManage::getId, mapList.stream().map(KnowledgeRangeInfo::getFk_id).collect(Collectors.toList())));
                if (!CollectionUtils.isEmpty(groups)) {

                    for (EventServiceManage e : groups) {
                        String principalName = "";
                        String engineerName = "";

                        //字典 类型1 开单、指派、转单  2处理  3  负责人
                        List<EventBillingDesignate> eventBillingDesignateList = eventBillingDesignateMapper.selectByEventId(e.getId(), "3");
                        List<EventBillingDesignate> eventBillingDesignateList2 = eventBillingDesignateMapper.selectByEventId(e.getId(), "2");
                        if (!org.springframework.util.CollectionUtils.isEmpty(eventBillingDesignateList)) {
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
                        if (!org.springframework.util.CollectionUtils.isEmpty(eventBillingDesignateList2)) {
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
                member.setGroups(groups);
                builder.append(groups.stream().filter(x -> x.getName() != null).map(EventServiceManage::getName).collect(Collectors.joining(",")));
            } else {
                builder.append("无");
            }

            member.setDes(builder.toString());

        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 删除查询范围
     *
     * @param id
     * @return
     */
    public ResultInfo delKnowledgeRange(Integer id) {
        //判断是否被使用 已被使用则无法删除
        Long aLong = knowledgeInfoService.selectCount(new QueryWrapper<KnowledgeInfo>().eq("knowledge_range_id", id).eq("is_del", "0"));
        if (aLong > 0) {
            return ResultInfo.error("该数据范围已被使用，无法删除");
        }
        KnowledgeRange knowledgeRange = this.selectById(id);
        if (knowledgeRange != null) {
            //物理删除
            knowledgeRangeInfoService.delete(new QueryWrapper<KnowledgeRangeInfo>().eq("knowledge_range_id", knowledgeRange.getId()));
            knowledgeRange.deleteById();
            return ResultInfo.success();
        }
        return ResultInfo.error("操作失败！");
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public ResultInfo getDetail(Integer id) {
        JSONObject resultData = new JSONObject();
        KnowledgeRange knowledgeRange = this.selectById(id);
        if (Base.notEmpty(knowledgeRange)) {
            resultData.put("knowledgeRange", knowledgeRange);
            //角色
            List<SysRole> roles = sysRoleService.getByKnowRangeId(knowledgeRange.getId());
            //部门
            List<SysDepartment> departments = sysDepartmentService.getByKnowRangeId(knowledgeRange.getId());
            //服务群组
            List<EventServiceManage> manages = eventServiceManageService.getByKnowRangeId(knowledgeRange.getId());

            resultData.put("roles", roles);
            resultData.put("departments", departments);
            resultData.put("manages", manages);
        }
        return ResultInfo.success(resultData);
    }

    /**
     * 新增知识范围
     *
     * @param knowledgeRange
     * @return
     */
    public ResultInfo addKnowledgeRange(KnowledgeRange knowledgeRange) {
        SysUser sysUser = sysUserService.getUser();
        if (knowledgeRange.getId() != null) {
            //修改
            knowledgeRange.setUpdate_user(sysUser.getId());
            knowledgeRange.setUpdate_time(new Date());
            //删除关联
            knowledgeRangeInfoService.delete(new QueryWrapper<KnowledgeRangeInfo>().eq("knowledge_range_id", knowledgeRange.getId()));
        } else {
            //新增
            knowledgeRange.setIs_del(String.valueOf(0));
            knowledgeRange.setCreate_user(sysUser.getId());
            knowledgeRange.setCreate_time(new Date());
        }
        if (knowledgeRange.insertOrUpdate()) {
            //新增范围
            //角色
            if (!CollectionUtils.isEmpty(knowledgeRange.getRoles())) {
                for (Integer roleId : knowledgeRange.getRoles().stream().map(SysRole::getId).collect(Collectors.toList())) {
                    insertKnowledgeRangeInfo(knowledgeRange.getId(), roleId, "1", sysUser);
                }
            }
//            String[] roleIds = knowledgeRange.getRole_ids().split(",");

            //部门
            if (!CollectionUtils.isEmpty(knowledgeRange.getDeptIds())) {
                for (Integer deptId : knowledgeRange.getDeptIds()) {
                    insertKnowledgeRangeInfo(knowledgeRange.getId(), deptId, "2", sysUser);
                }
            }
//            String[] deptIds = knowledgeRange.getDept_ids().split(",");

            //服务群组
            if (!CollectionUtils.isEmpty(knowledgeRange.getGroups())) {
                for (Integer groupId : knowledgeRange.getGroups().stream().map(EventServiceManage::getId).collect(Collectors.toList())) {
                    insertKnowledgeRangeInfo(knowledgeRange.getId(), groupId, "3", sysUser);
                }
            }
//            String[] groupIds = knowledgeRange.getGroup_ids().split(",");

            return ResultInfo.success();
        }
        return ResultInfo.error("操作失败！");
    }

    /**
     * 插入查询范围信息
     *
     * @param mainId  知识查看范围ID
     * @param fkId    外键
     * @param type    范围类别 1：角色 2：部门 3：服务群组
     * @param sysUser
     */
    private void insertKnowledgeRangeInfo(Integer mainId, Integer fkId, String type, SysUser sysUser) {
        KnowledgeRangeInfo k = new KnowledgeRangeInfo();
        k.setKnowledge_range_id(mainId);
        k.setFk_id(fkId);
        k.setRange_type(type);
        k.setCreate_user(sysUser.getId());
        k.setCreate_time(new Date());
        k.setIs_del(String.valueOf(0));
        k.insert();
    }

}
