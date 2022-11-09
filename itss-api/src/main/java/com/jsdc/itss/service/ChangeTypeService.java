package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeTypeDao;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.ChangeTypeVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ChangeType
 * Description: 变更类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class ChangeTypeService extends BaseService<ChangeTypeDao, ChangeType> {

    @Autowired
    private ChangeTypeMapper changeTypeMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ChangeCommonlyApproveMapper changeCommonlyApproveMapper;
    @Autowired
    private ChangeUrgentApproveMapper changeUrgentApproveMapper;
    @Autowired
    private ChangeMajorApproveMapper changeMajorApproveMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private EventBillingDesignateMapper eventBillingDesignateMapper;

    @Autowired
    private EventServiceManageMapper eventServiceManageMapper;

    /**
     * 分页查询
     */
    public PageInfo<ChangeType> getPageList(Integer pageIndex, Integer pageSize, ChangeType bean) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<ChangeType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(bean.getType_name())) {
            queryWrapper.like("type_name", bean.getType_name());
        }
        queryWrapper.eq("is_del", 0);
        List<ChangeType> list = selectList(queryWrapper);
        PageInfo<ChangeType> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 获取根目录
     */
    public List<ChangeType> getTreeMenu(ChangeType changeType) {
        QueryWrapper<ChangeType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        //3	状态 0禁用 1使用
        if (StringUtils.isNotEmpty(changeType.getIs_use())) {
            queryWrapper.eq("is_use", changeType.getIs_use());
        }
        if (StringUtils.isNotEmpty(changeType.getType_name())) {
            queryWrapper.like("type_name", changeType.getType_name());
        }
        if(null != changeType.getId()){
            queryWrapper.ne("id", changeType.getId());
        }
        List<ChangeType> list = selectList(queryWrapper);
        List<ChangeType> voList = new ArrayList<ChangeType>();
        for (ChangeType bean : list) {
            //树状图 标题
//            bean.setName(bean.getName());
            if (Base.empty(bean.getParent_id())) {
                voList.add(findChildren(bean, list));
            }
        }
        return voList;
    }

    /**
     * 获取子目录
     */
    public ChangeType findChildren(ChangeType bean, List<ChangeType> list) {
        for (ChangeType vo : list) {
            if (vo.getIs_use() != null) {
                if (vo.getIs_use().equals("0")) {
                    vo.setIs_use_name("禁用");
                } else {
                    vo.setIs_use_name("启用");
                }
            }

            String commonly_name = "";
            //变更类别ID
            int change_type_id = vo.getId();
            //获取一般变更审批人名字
            QueryWrapper<ChangeCommonlyApprove> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("is_del", "0");
            queryWrapper1.eq("change_type_id", change_type_id);
            List<ChangeCommonlyApprove> changeCommonlyApproveList = changeCommonlyApproveMapper.selectList(queryWrapper1);
            for (ChangeCommonlyApprove temp : changeCommonlyApproveList) {
                Integer id = temp.getApprove_user();
                if (id != null) {
                    String name = getUserName(id);
                    commonly_name += name + " ";
                }
            }
            if (StringUtils.isBlank(commonly_name)) {
                commonly_name = "未知";
            }
            //获取一般变更方案审核人名字
            if (vo.getCommonly_audit_user() != null) {
                int commonly_audit_user = vo.getCommonly_audit_user();
                String tempName = getUserName(commonly_audit_user);
                commonly_name += "--" + tempName;
            }
            //获取一般变更实施后评审人名字
            if (vo.getCommonly_implement_user() != null) {
                int commonly_implement_user = vo.getCommonly_implement_user();
                String tempName1 = getUserName(commonly_implement_user);
                commonly_name += "--" + tempName1;
            }
            vo.setCommonly_name(commonly_name);

            //获取紧急变更审批人名字
            String urgent_name = "";
            QueryWrapper<ChangeUrgentApprove> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("is_del", "0");
            queryWrapper2.eq("change_type_id", change_type_id);
            List<ChangeUrgentApprove> changeUrgentApproveList = changeUrgentApproveMapper.selectList(queryWrapper2);

            for (ChangeUrgentApprove temp : changeUrgentApproveList) {
                Integer id = temp.getApprove_user();
                if (id != null) {
                    String name = getUserName(id);
                    urgent_name += name + " ";
                }
            }
            if (StringUtils.isBlank(urgent_name)) {
                urgent_name = "未知";
            }
            //获取紧急变更实施后评审人名字
            if (vo.getUrgent_implement_user() != null) {
                int urgent_implement_user = vo.getUrgent_implement_user();
                String tempName2 = getUserName(urgent_implement_user);
                urgent_name += "--" + tempName2;
            }
            vo.setUrgent_name(urgent_name);


            //获取重大变更审批人名字
            String major_name = "";
            QueryWrapper<ChangeMajorApprove> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("is_del", "0");
            queryWrapper3.eq("change_type_id", change_type_id);
            List<ChangeMajorApprove> changeMajorApproveList = changeMajorApproveMapper.selectList(queryWrapper3);
            for (ChangeMajorApprove temp : changeMajorApproveList) {
                Integer id = temp.getApprove_user();
                if (id != null) {
                    String name = getUserName(id);
                    major_name += name + " ";
                }
            }
            if (StringUtils.isBlank(major_name)) {
                major_name = "未知";
            }
            //获取重大变更方案审核人名字
            if (vo.getMajor_audit_user() != null) {
                int major_audit_user = vo.getMajor_audit_user();
                String tempName3 = getUserName(major_audit_user);
                major_name += "--" + tempName3;
            }
            // 获取重大变更实施后评审人名字
            if (vo.getMajor_implement_user() != null) {
                Integer major_implement_user = vo.getMajor_implement_user();
                String tempName4 = getUserName(major_implement_user);
                major_name += "--" + tempName4;
            }
            vo.setMajor_name(major_name);

            if (bean.getId().equals(vo.getParent_id())) {
                if (Base.empty(bean.getChildren())) {
                    bean.setChildren(new ArrayList<ChangeType>());
                }
                bean.getChildren().add(findChildren(vo, list));
            }
        }
        return bean;
    }

    private String getUserName(Integer id) {
        SysUser sysUser = (SysUser) sysUserMapper.selectById(id);
        if (sysUser == null) {
            return "";
        } else {
            return sysUser.getName();
        }
    }

    /**
     * 保存/更新
     */
    public ResultInfo saveOrUpd(ChangeType bean) {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<ChangeType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_name",bean.getType_name());
        queryWrapper.eq("is_del","0");
        if(Base.notEmpty(bean.getId())){
            queryWrapper.ne("id",bean.getId());
        }
        Long count = changeTypeMapper.selectCount(queryWrapper);
        if (count == 0) {
            if (Base.notEmpty(bean.getId())) {
                //修改
                // 修改者
                bean.setUpdate_user(sysUser.getId());
                // 修改时间
                bean.setUpdate_time(new Date());
                updateById(bean);
                UpdateWrapper<ChangeMajorApprove> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper1.eq("change_type_id", bean.getId());
                updateWrapper1.set("is_del", "1");
                changeMajorApproveMapper.update(null, updateWrapper1);

                UpdateWrapper<ChangeUrgentApprove> updateWrapper2 = new UpdateWrapper<>();
                updateWrapper2.eq("change_type_id", bean.getId());
                updateWrapper2.set("is_del", "1");
                changeUrgentApproveMapper.update(null, updateWrapper2);

                UpdateWrapper<ChangeCommonlyApprove> updateWrapper3 = new UpdateWrapper<>();
                updateWrapper3.eq("change_type_id", bean.getId());
                updateWrapper3.set("is_del", "1");
                changeCommonlyApproveMapper.update(null, updateWrapper3);

            } else {
                //新增
                bean.setIs_del("0");
                bean.setCreate_time(new Date());
                bean.setCreate_user(sysUser.getId());
                insert(bean);
            }
            List<SysUser> changeCommonlyApproveList1 = bean.getChangeCommonlyApproveList1();
            if (!CollectionUtils.isEmpty(changeCommonlyApproveList1)) {
                for (SysUser temp : changeCommonlyApproveList1) {
                    int id = temp.getId();
                    ChangeCommonlyApprove changeCommonlyApprove = new ChangeCommonlyApprove();
                    changeCommonlyApprove.setApprove_user(id);
                    changeCommonlyApprove.setChange_type_id(bean.getId());
                    changeCommonlyApprove.setIs_del("0");
                    changeCommonlyApprove.setCreate_time(new Date());
                    changeCommonlyApprove.setCreate_user(sysUserService.getUser().getId());
                    changeCommonlyApproveMapper.insert(changeCommonlyApprove);
                }
            }

            List<SysUser> changeUrgentApproveList1 = bean.getChangeUrgentApproveList1();
            if (!CollectionUtils.isEmpty(changeUrgentApproveList1)) {
                for (SysUser temp : changeUrgentApproveList1) {
                    int id = temp.getId();
                    ChangeUrgentApprove changeUrgentApprove = new ChangeUrgentApprove();
                    changeUrgentApprove.setChange_type_id(bean.getId());
                    changeUrgentApprove.setApprove_user(id);
                    changeUrgentApprove.setIs_del("0");
                    changeUrgentApprove.setCreate_time(new Date());
                    changeUrgentApprove.setCreate_user(sysUserService.getUser().getId());
                    changeUrgentApproveMapper.insert(changeUrgentApprove);
                }
            }
            List<SysUser> changeMajorApproveList1 = bean.getChangeMajorApproveList1();
            if (!CollectionUtils.isEmpty(changeMajorApproveList1)) {
                for (SysUser temp : changeMajorApproveList1) {
                    int id = temp.getId();
                    ChangeMajorApprove changeMajorApprove = new ChangeMajorApprove();
                    changeMajorApprove.setChange_type_id(bean.getId());
                    changeMajorApprove.setApprove_user(id);
                    changeMajorApprove.setIs_del("0");
                    changeMajorApprove.setCreate_time(new Date());
                    changeMajorApprove.setCreate_user(sysUserService.getUser().getId());
                    changeMajorApproveMapper.insert(changeMajorApprove);
                }
            }

        } else {
            return ResultInfo.error("属性一栏中，名称已存在");
        }
        return ResultInfo.success();
    }

    public ResultInfo deleteChangeType(Integer ids) {
        UpdateWrapper<ChangeType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", ids);
        updateWrapper.set("is_del", "1");
        changeTypeMapper.update(null, updateWrapper);

        UpdateWrapper<ChangeCommonlyApprove> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("change_type_id", ids);
        updateWrapper1.set("is_del", "1");
        changeCommonlyApproveMapper.update(null, updateWrapper1);

        UpdateWrapper<ChangeUrgentApprove> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("change_type_id", ids);
        updateWrapper2.set("is_del", "1");
        changeUrgentApproveMapper.update(null, updateWrapper2);

        UpdateWrapper<ChangeMajorApprove> updateWrapper3 = new UpdateWrapper<>();
        updateWrapper3.eq("change_type_id", ids);
        updateWrapper3.set("is_del", "1");
        changeMajorApproveMapper.update(null, updateWrapper3);

        return ResultInfo.success();
    }

    public List<ChangeType> getAll(ChangeType bean) {


        return selectList(Wrappers.<ChangeType>lambdaQuery().eq(ChangeType::getIs_del, "0").eq(ChangeType::getIs_use, "1"));
    }


    public ChangeTypeVo selectPeople(Integer id) {

        ChangeType changeType = changeTypeMapper.selectById(id);
        if (changeType != null) {
            //获取一般变更方案审核人
            Integer commonly_audit_user = changeType.getCommonly_audit_user();
            if (commonly_audit_user != null) {
                changeType.setCommonly_audit_user_name(getUserName(commonly_audit_user));
            }
            //一般变更实施后评审人
            Integer commonly_implement_user = changeType.getCommonly_implement_user();
            if (commonly_implement_user != null) {
                changeType.setCommonly_implement_user_name(getUserName(commonly_implement_user));
            }
            //紧急变更实施后评审人
            Integer urgent_implement_user = changeType.getUrgent_implement_user();
            if (urgent_implement_user != null) {
                changeType.setUrgent_implement_user_name(getUserName(urgent_implement_user));
            }
            //重大变更方案审核人
            Integer major_audit_user = changeType.getMajor_audit_user();
            if (major_audit_user != null) {
                changeType.setMajor_audit_user_name(getUserName(major_audit_user));
            }
            //重大变更实施后评审人
            Integer major_implement_user = changeType.getMajor_implement_user();
            if (major_implement_user != null) {
                changeType.setMajor_implement_user_name(getUserName(major_implement_user));
            }
        }


        ChangeTypeVo changeTypeVo = new ChangeTypeVo();
        changeTypeVo.setChangeType(changeType);

        //一般变更审批人
        List<ChangeCommonlyApprove> changeCommonlyApproveList = changeCommonlyApproveMapper.geteCommonlyApproveList(id);
        changeTypeVo.setChangeCommonlyApproveList(changeCommonlyApproveList);
        //紧急变更审批人
        List<ChangeUrgentApprove> changeUrgentApproveList = changeUrgentApproveMapper.getChangeUrgentApproveList(id);
        changeTypeVo.setChangeUrgentApproveList(changeUrgentApproveList);
        //重大变更审批人
        List<ChangeMajorApprove> changeMajorApproveList = changeMajorApproveMapper.getChangeMajorApproveList(id);
        changeTypeVo.setChangeMajorApproveList(changeMajorApproveList);

        return changeTypeVo;
    }

    public ChangeType getOneInfoById(Integer id) {
        ChangeType changeType = changeTypeMapper.selectById(id);
        if (changeType.getParent_id() != null) {
            ChangeType changeType1 = changeTypeMapper.selectById(changeType.getParent_id());
            String type_name = changeType1.getType_name();
            changeType.setParent_name(type_name);
        }
        if (changeType != null) {
            //获取一般变更方案审核人
            Integer commonly_audit_user = changeType.getCommonly_audit_user();
            if (commonly_audit_user != null) {
                changeType.setCommonly_audit_user_name(getUserName(commonly_audit_user));
            }
            //一般变更实施后评审人
            Integer commonly_implement_user = changeType.getCommonly_implement_user();
            if (commonly_implement_user != null) {
                changeType.setCommonly_implement_user_name(getUserName(commonly_implement_user));
            }
            //紧急变更实施后评审人
            Integer urgent_implement_user = changeType.getUrgent_implement_user();
            if (urgent_implement_user != null) {
                changeType.setUrgent_implement_user_name(getUserName(urgent_implement_user));
            }
            //重大变更方案审核人
            Integer major_audit_user = changeType.getMajor_audit_user();
            if (major_audit_user != null) {
                changeType.setMajor_audit_user_name(getUserName(major_audit_user));
            }
            //重大变更实施后评审人
            Integer major_implement_user = changeType.getMajor_implement_user();
            if (major_implement_user != null) {
                changeType.setMajor_implement_user_name(getUserName(major_implement_user));
            }

        }

        //一般变更审批人
        List<ChangeCommonlyApprove> changeCommonlyApproveList = changeCommonlyApproveMapper.geteCommonlyApproveList(id);
        List<SysUser> changeCommonlyApproveList1 = new ArrayList<>();
        for (ChangeCommonlyApprove temp : changeCommonlyApproveList) {
            Integer approve_user = temp.getApprove_user();
            SysUser sysUser = sysUserMapper.selectById(approve_user);

            changeCommonlyApproveList1.add(getUser(sysUser));
        }
        changeType.setChangeCommonlyApproveList1(changeCommonlyApproveList1);

        //紧急变更审批人
        List<ChangeUrgentApprove> changeUrgentApproveList = changeUrgentApproveMapper.getChangeUrgentApproveList(id);
        List<SysUser> changeUrgentApproveList1 = new ArrayList<>();
        for (ChangeUrgentApprove temp : changeUrgentApproveList) {
            Integer approve_user = temp.getApprove_user();
            SysUser sysUser = sysUserMapper.selectById(approve_user);
            changeUrgentApproveList1.add(getUser(sysUser));
        }
        changeType.setChangeUrgentApproveList1(changeUrgentApproveList1);

        //重大变更审批人
        List<ChangeMajorApprove> changeMajorApproveList = changeMajorApproveMapper.getChangeMajorApproveList(id);
        List<SysUser> changeMajorApproveList1 = new ArrayList<>();
        for (ChangeMajorApprove temp : changeMajorApproveList) {
            Integer approve_user = temp.getApprove_user();
            SysUser sysUser = sysUserMapper.selectById(approve_user);
            changeMajorApproveList1.add(getUser(sysUser));
        }
        changeType.setChangeMajorApproveList1(changeMajorApproveList1);

        return changeType;
    }

    private SysUser getUser(SysUser sysUser){
        //根据用户得到服务群组
        List<EventBillingDesignate> eventServiceManageList = eventBillingDesignateMapper.selectList(Wrappers.<EventBillingDesignate>lambdaQuery()
                .eq(EventBillingDesignate::getIs_del, "0")
                .eq(EventBillingDesignate::getEngineer_userid, sysUser.getId())
                .eq(EventBillingDesignate::getType, "1")
        );
        if(CollectionUtils.isEmpty(eventServiceManageList)){
            return sysUser;
        }
        List<Integer> eventIds = eventServiceManageList.stream().map(EventBillingDesignate::getService_management_id).collect(Collectors.toList());
        //根据服务群组id查询服务群组信息
        List<EventServiceManage> eventServiceManageList1 = eventServiceManageMapper.selectList(Wrappers.<EventServiceManage>lambdaQuery()
                .eq(EventServiceManage::getIs_del, "0")
                .in(EventServiceManage::getId, eventIds)
        );
        if (CollectionUtils.isEmpty(eventServiceManageList1)) {
            return sysUser;
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
        if(!org.springframework.util.CollectionUtils.isEmpty(fzrList)){
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
        if(!org.springframework.util.CollectionUtils.isEmpty(gcList)){
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
        return sysUser;
    }
}
