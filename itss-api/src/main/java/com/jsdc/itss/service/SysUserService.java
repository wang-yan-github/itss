package com.jsdc.itss.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.MD5Utils;
import com.jsdc.itss.dao.SysUserDao;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventServiceManageVo;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysUserService extends BaseService<SysUserDao, SysUser> {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserDao userDao;
    @Autowired
    private SysUserConfigAuthorityMapper sysUserConfigAuthorityMapper;
    @Autowired
    private SysUserDepartmentAuthorityMapper sysUserDepartmentAuthorityMapper;
    @Autowired
    private SysUserEventGroupMapper sysUserEventGroupMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private EventServiceManageMapper eventServiceManageMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private EventServiceManageService eventServiceManageService;

    @Autowired
    private EventBillingDesignateService eventBillingDesignateService;

    @Autowired
    private SysUserEventGroupMapper userEventGroupMapper;


    /**
     * create by xuaolong
     * description: ????????????????????????
     *
     * @param sysUser
     * @param pageIndex
     * @param pageSize
     * @return ResultInfo
     */
    public ResultInfo getPage(SysUser sysUser, int pageIndex, int pageSize) {
        List<Integer> ids = new ArrayList<>();
        if (Base.notEmpty(sysUser.getEventServiceGroupId())){
//            sql.append(" and sys_user_event_group.event_group_id = " + sysUser.getEventServiceGroupId() );
            List<SysUserEventGroup> eventGroups = userEventGroupMapper.selectList(Wrappers.<SysUserEventGroup>lambdaQuery()
                    .eq(SysUserEventGroup::getEvent_group_id,sysUser.getEventServiceGroupId()));
            if (!CollectionUtils.isEmpty(eventGroups)){
                ids = eventGroups.stream().map(SysUserEventGroup::getUser_id).collect(Collectors.toList());
            }
        }
        sysUser.setIds(ids);
        PageHelper.startPage(pageIndex, pageSize);
        List<SysUserPageVo> list = sysUserMapper.getUserInfoByPage(sysUser);
        PageInfo<SysUserPageVo> pageInfo = new PageInfo<SysUserPageVo>(list);
        return ResultInfo.success(pageInfo);
    }

    public ResultInfo getuserPage(SysUser sysUser, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<SysUserPageVo> list = sysUserMapper.getUserPage(sysUser);
        PageInfo<SysUserPageVo> pageInfo = new PageInfo<SysUserPageVo>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * ????????????????????????????????????page
     * @param sysUser
     * @return
     */
    public ResultInfo getGroupPage(SysUser sysUser, int pageIndex, int pageSize) {
        // ????????????????????????????????????,??????????????????????????????
        if(null == sysUser || null == sysUser.getEventServiceGroupId()){
            return getPage(sysUser,pageIndex,pageSize);
        }
        Integer group_id = sysUser.getEventServiceGroupId();
        String type = sysUser.getGroupType() == null?"":sysUser.getGroupType();
        // ??????????????????id????????????id??????
        List<EventBillingDesignate> billingDesignates = eventBillingDesignateService.selectList(Wrappers.<EventBillingDesignate>lambdaQuery()
                .eq(EventBillingDesignate::getService_management_id,group_id)
                .eq(StringUtils.isNotBlank(type), EventBillingDesignate::getType,type)
        );
        // ??????userid,???????????????
        List<Integer> userIds = billingDesignates.stream().map(EventBillingDesignate::getEngineer_userid).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIds)){
            return ResultInfo.success(new PageInfo<>());
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<SysUserPageVo> list = sysUserMapper.getUserInfoByPage(SysUser.builder().ids(userIds).build());
        PageInfo<SysUserPageVo> pageInfo = new PageInfo<SysUserPageVo>(list);
        return ResultInfo.success(pageInfo);
    }

    public List<SysUserPageVo> getList(SysUser sysUser) {
        List<SysUserPageVo> list = sysUserMapper.getUserInfoByPage(sysUser);
        return list;
    }

    /**
     * create by xuaolong
     * description: ????????????
     *
     * @param sysUserVo
     * @return ResultInfo
     */
    public ResultInfo addSysUser(SysUserVo sysUserVo) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("user_name",sysUserVo.getUser_name());
        queryWrapper.eq("is_del","0");
        Long count = selectCount(queryWrapper);
        if (count == 0) {
            SysUser sysUser = new SysUser();
            //??????????????????SysUser ?????????
            BeanUtils.copyProperties(sysUserVo, sysUser);
            sysUser.setIs_vip(StringUtils.isEmpty(sysUserVo.getIs_vip()) ? 0 : Short.parseShort(sysUserVo.getIs_vip()));
            sysUser.setStatus(StringUtils.isEmpty(sysUserVo.getStatus()) ? 2: Short.parseShort(sysUserVo.getStatus()));
            sysUser.setIs_emial(StringUtils.isEmpty(sysUserVo.getIs_emial()) ? 0 : Short.parseShort(sysUserVo.getIs_emial()));
            sysUser.setIs_wechat(StringUtils.isEmpty(sysUserVo.getIs_wechat()) ? 0 : Short.parseShort(sysUserVo.getIs_wechat()));
            sysUser.setCreate_time(new Date());
            sysUser.setCreate_user(this.getUser().getId());
            sysUser.setIs_del("0");
            sysUser.setPass(MD5Utils.getMD5(sysUserVo.getPass()));
            sysUserMapper.insert(sysUser);
            int id = sysUser.getId();
            //????????????????????? ????????????
//        if(null != sysUserVo.getRoleId()){
//            SysUserRole sysUserRole = new SysUserRole();
//            sysUserRole.setUser_id(id);
//            sysUserRole.setRole_id(sysUserVo.getRoleId());
//            sysUserRole.setIs_del("0");
//            sysUserRole.setCreate_time(new Date());
//            sysUserRole.setCreate_user(getUser().getId());
//            sysUserRoleMapper.insert(sysUserRole);
//        }

            if (!CollectionUtils.isEmpty(sysUserVo.getRoleList())){
                for (SysRole temp : sysUserVo.getRoleList()){
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUser_id(id);
                    sysUserRole.setRole_id(temp.getId());
                    sysUserRole.setIs_del("0");
                    sysUserRole.setCreate_time(new Date());
                    sysUserRole.setCreate_user(getUser().getId());
                    sysUserRoleMapper.insert(sysUserRole);
                }
            }


            //????????????????????????????????? ????????????
            List<EventServiceManage> groupList = sysUserVo.getGroupList();
            if (!CollectionUtils.isEmpty(groupList)) {
                List<Integer> groupIdList = groupList.stream().map(EventServiceManage::getId).collect(Collectors.toList());
                for (Integer temp : groupIdList) {
                    SysUserEventGroup sysUserEventGroup = new SysUserEventGroup();
                    sysUserEventGroup.setUser_id(id);
                    sysUserEventGroup.setEvent_group_id(temp);
                    sysUserEventGroup.setIs_del("0");
                    sysUserEventGroup.setCreate_user(getUser().getId());
                    sysUserEventGroup.setCreate_time(new Date());
                    sysUserEventGroupMapper.insert(sysUserEventGroup);
                }
            }

            //???????????????????????????????????????????????? ????????????
            List<Integer> departmentIdList = sysUserVo.getDepartmentIdList();
            for (Integer temp : departmentIdList) {
                SysUserDepartmentAuthority sysUserDepartmentAuthority = new SysUserDepartmentAuthority();
                sysUserDepartmentAuthority.setUser_id(id);
                sysUserDepartmentAuthority.setDepartment_id(temp);
                sysUserDepartmentAuthority.setIs_del("0");
                sysUserDepartmentAuthority.setCreate_user(getUser().getId());
                sysUserDepartmentAuthority.setCreate_time(new Date());
                sysUserDepartmentAuthorityMapper.insert(sysUserDepartmentAuthority);
            }
            //??????????????????????????????????????? ????????????
            List<Integer> configIdList = sysUserVo.getConfigIdList();
            for (Integer temp : configIdList) {
                SysUserConfigAuthority sysUserConfigAuthority = new SysUserConfigAuthority();
                sysUserConfigAuthority.setUser_id(id);
                sysUserConfigAuthority.setDepartment_id(temp);
                sysUserConfigAuthority.setIs_del("0");
                sysUserConfigAuthority.setCreate_user(getUser().getId());
                sysUserConfigAuthority.setCreate_time(new Date());
                sysUserConfigAuthorityMapper.insert(sysUserConfigAuthority);
            }
            return ResultInfo.success();
        }else{
            return ResultInfo.error("?????????????????????");
        }

    }


    /**
     * create by xuaolong
     * description: ????????????id????????????????????????????????????
     *
     * @param userId
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo getUser(Integer userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        sysUserVo.setIs_vip(sysUser.getIs_vip() + "");
        sysUserVo.setStatus(sysUser.getStatus() + "");
        sysUserVo.setIs_emial(sysUser.getIs_emial() + "");
        sysUserVo.setIs_wechat(sysUser.getIs_wechat() + "");
        SysDepartment sysDepartment = sysDepartmentMapper.selectById(sysUser.getDepartment_id());
        if (sysDepartment != null) {
            sysUserVo.setDepartName(sysDepartment.getName());
        }

        //????????????ID ?????????????????????????????????
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id", userId);
        sysUserRoleQueryWrapper.eq("is_del", "0");
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        List<Integer> roleIdList = new ArrayList<>();
        for (SysUserRole temp : sysUserRoleList) {
            roleIdList.add(temp.getRole_id());
        }
        sysUserVo.setRoleIdList(roleIdList);

        //????????????ID ???????????????????????????????????????????????????
        QueryWrapper<SysUserConfigAuthority> sysUserConfigAuthorityQueryWrapper = new QueryWrapper<>();
        sysUserConfigAuthorityQueryWrapper.eq("user_id", userId);
        sysUserConfigAuthorityQueryWrapper.eq("is_del", "0");
        List<SysUserConfigAuthority> sysUserConfigAuthorityList = sysUserConfigAuthorityMapper.selectList(sysUserConfigAuthorityQueryWrapper);
        if (CollectionUtils.isEmpty(sysUserConfigAuthorityList)) {
            sysUserVo.setConfigList(null);
        } else {
            List<SysDepartment> configList = new ArrayList<>();
            for (SysUserConfigAuthority temp : sysUserConfigAuthorityList) {
                SysDepartment sysDepartment1 = sysDepartmentMapper.selectById(temp.getDepartment_id());
                if (null != sysDepartment1) {
                    configList.add(sysDepartment1);
                }
            }
            sysUserVo.setConfigList(configList);
        }


        //????????????ID ????????????????????????????????????????????????????????????
        QueryWrapper<SysUserDepartmentAuthority> sysUserDepartmentAuthorityQueryWrapper = new QueryWrapper<>();
        sysUserDepartmentAuthorityQueryWrapper.eq("user_id", userId);
        sysUserDepartmentAuthorityQueryWrapper.eq("is_del", "0");
        List<SysUserDepartmentAuthority> sysUserDepartmentAuthorityList = sysUserDepartmentAuthorityMapper.selectList(sysUserDepartmentAuthorityQueryWrapper);
        List<SysDepartment> departmentList = new ArrayList<>();
        for (SysUserDepartmentAuthority temp : sysUserDepartmentAuthorityList) {
            SysDepartment sysDepartment1 = sysDepartmentMapper.selectById(temp.getDepartment_id());
            if (null != sysDepartment1) {
                departmentList.add(sysDepartment1);
            }
        }
        sysUserVo.setDepartmentList(departmentList);
        //????????????ID ?????????????????????????????????????????????
        QueryWrapper<SysUserEventGroup> sysUserEventGroupQueryWrapper = new QueryWrapper<>();
        sysUserEventGroupQueryWrapper.eq("user_id", userId);
        sysUserEventGroupQueryWrapper.eq("is_del", "0");
        List<SysUserEventGroup> sysUserEventGroupList = sysUserEventGroupMapper.selectList(sysUserEventGroupQueryWrapper);
        List<EventServiceManage> groupList = new ArrayList<>();
        for (SysUserEventGroup temp : sysUserEventGroupList) {
            EventServiceManage eventServiceManage = eventServiceManageMapper.selectById(temp.getEvent_group_id());
            groupList.add(eventServiceManage);
        }
        sysUserVo.setGroupList(groupList);
        return ResultInfo.success(sysUserVo);
    }


    /**
     * create by xuaolong
     * description: ??????????????????
     *
     * @param sysUser
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo edit(SysUserVo sysUserVo) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.ne("id",sysUserVo.getId());
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("user_name",sysUserVo.getUser_name());
        Long count = selectCount(queryWrapper);
        if (count == 0) {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(sysUserVo, sysUser);
            sysUser.setIs_wechat(Short.parseShort(sysUserVo.getIs_wechat()));
            sysUser.setIs_emial(Short.parseShort(sysUserVo.getIs_emial()));
            sysUser.setIs_vip(Short.parseShort(sysUserVo.getIs_vip()));
            sysUser.setStatus(Short.parseShort(sysUserVo.getStatus()));
            if(sysUserVo.getUpdatePassword()){
                sysUser.setPass(MD5Utils.getMD5(sysUserVo.getNewPassword()));
            }
            sysUserMapper.updateById(sysUser);

            //??????user_id????????????????????????????????????????????? ????????????
            UpdateWrapper<SysUserEventGroup> sysUserEventGroupUpdateWrapper =new UpdateWrapper();
            sysUserEventGroupUpdateWrapper.eq("user_id",sysUserVo.getId());
            sysUserEventGroupUpdateWrapper.set("is_del","1");
            sysUserEventGroupMapper.update(null,sysUserEventGroupUpdateWrapper);
            List<Integer> groupIdList =sysUserVo.getGroupIdList();
            if(!CollectionUtils.isEmpty(sysUserVo.getGroupList())){
                for (EventServiceManage temp : sysUserVo.getGroupList()){
                    SysUserEventGroup sysUserEventGroup =new SysUserEventGroup();
                    sysUserEventGroup.setUser_id(sysUserVo.getId());
                    sysUserEventGroup.setEvent_group_id(temp.getId());
                    sysUserEventGroup.setIs_del("0");
                    sysUserEventGroup.setUpdate_time(new Date());
                    sysUserEventGroup.setUpdate_user(getUser().getId());
                    sysUserEventGroupMapper.insert(sysUserEventGroup);
                }
            }


            //??????user_id????????????????????????????????? ????????????
//        UpdateWrapper<SysUserDepartmentAuthority> sysUserDepartmentAuthorityUpdateWrapper =new UpdateWrapper();
//        sysUserDepartmentAuthorityUpdateWrapper.eq("user_id",sysUserVo.getId());
//        sysUserDepartmentAuthorityUpdateWrapper.set("is_del","1");
//        sysUserDepartmentAuthorityMapper.update(null,sysUserDepartmentAuthorityUpdateWrapper);
//        List<Integer> departmentIdList =sysUserVo.getDepartmentIdList();
//        for (Integer temp : departmentIdList){
//            SysUserDepartmentAuthority sysUserDepartmentAuthority =new SysUserDepartmentAuthority();
//            sysUserDepartmentAuthority.setUser_id(sysUserVo.getId());
//            sysUserDepartmentAuthority.setDepartment_id(temp);
//            sysUserDepartmentAuthority.setIs_del("0");
//            sysUserDepartmentAuthority.setUpdate_time(new Date());
//            sysUserDepartmentAuthority.setUpdate_user(getUser().getId());
//            sysUserDepartmentAuthorityMapper.insert(sysUserDepartmentAuthority);
//        }

            //??????user_id???????????????????????????????????????????????????    ????????????
            UpdateWrapper<SysUserConfigAuthority> sysUserConfigAuthorityUpdateWrapper =new UpdateWrapper();
            sysUserConfigAuthorityUpdateWrapper.eq("user_id",sysUserVo.getId());
            sysUserConfigAuthorityUpdateWrapper.set("is_del","1");
            sysUserConfigAuthorityMapper.update(null,sysUserConfigAuthorityUpdateWrapper);
            List<Integer> configIdList =sysUserVo.getConfigIdList();
            for (Integer temp : configIdList){
                SysUserConfigAuthority sysUserConfigAuthority =new SysUserConfigAuthority();
                sysUserConfigAuthority.setUser_id(sysUserVo.getId());
                sysUserConfigAuthority.setDepartment_id(temp);
                sysUserConfigAuthority.setIs_del("0");
                sysUserConfigAuthority.setUpdate_time(new Date());
                sysUserConfigAuthority.setUpdate_user(getUser().getId());
                sysUserConfigAuthorityMapper.insert(sysUserConfigAuthority);
            }
            return ResultInfo.success();
        }else{
            return ResultInfo.error("??????????????????~");
        }
    }


    /**
     * create by xuaolong
     * description: ??????????????????
     *
     * @param sysUser
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo deleteByUserId(Integer userId) {
        UpdateWrapper<SysUser> sysUserUpdateWrapper = new UpdateWrapper<>();
        sysUserUpdateWrapper.set("is_del", "1");
        sysUserUpdateWrapper.eq("id", userId);
        sysUserMapper.update(null, sysUserUpdateWrapper);
        //??????user_id????????????????????????
        UpdateWrapper<SysUserRole> sysUserRoleUpdateWrapper = new UpdateWrapper();
        sysUserRoleUpdateWrapper.eq("user_id", userId);
        sysUserRoleUpdateWrapper.set("is_del", "1");
        sysUserRoleMapper.update(null, sysUserRoleUpdateWrapper);
        //??????user_id?????????????????????
        UpdateWrapper<SysUserEventGroup> sysUserEventGroupUpdateWrapper = new UpdateWrapper();
        sysUserEventGroupUpdateWrapper.eq("user_id", userId);
        sysUserEventGroupUpdateWrapper.set("is_del", "1");
        sysUserEventGroupMapper.update(null, sysUserEventGroupUpdateWrapper);
        //??????user_id?????????????????????
        UpdateWrapper<SysUserDepartmentAuthority> sysUserDepartmentAuthorityUpdateWrapper = new UpdateWrapper();
        sysUserDepartmentAuthorityUpdateWrapper.eq("user_id", userId);
        sysUserDepartmentAuthorityUpdateWrapper.set("is_del", "1");
        sysUserDepartmentAuthorityMapper.update(null, sysUserDepartmentAuthorityUpdateWrapper);
        //??????user_id?????????????????????
        UpdateWrapper<SysUserConfigAuthority> sysUserConfigAuthorityUpdateWrapper = new UpdateWrapper();
        sysUserConfigAuthorityUpdateWrapper.eq("user_id", userId);
        sysUserConfigAuthorityUpdateWrapper.set("is_del", "1");
        sysUserConfigAuthorityMapper.update(null, sysUserConfigAuthorityUpdateWrapper);
        return ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description: ????????????
     *
     * @param userId
     * @param roleIdList
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo setUserRoleById(Integer userId, List<Integer> roleIdList) {
        //??????user_id????????????????????????
        UpdateWrapper<SysUserRole> sysUserRoleUpdateWrapper = new UpdateWrapper();
        sysUserRoleUpdateWrapper.eq("user_id", userId);
        sysUserRoleUpdateWrapper.set("is_del", "1");
        sysUserRoleMapper.update(null, sysUserRoleUpdateWrapper);
        for (Integer temp : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUser_id(userId);
            sysUserRole.setRole_id(temp);

            sysUserRole.setIs_del("0");
            sysUserRole.setCreate_time(new Date());
            sysUserRole.setCreate_user(getUser().getId());
            sysUserRoleMapper.insert(sysUserRole);
        }
        return ResultInfo.success();
    }



    /**
     * ??????
     */
    public SaTokenInfo login(String userName, String passWord) {
        QueryWrapper<SysUser> queryWrapper = dao.queryByName(userName, MD5Utils.getMD5(passWord));
        SysUser user = selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        user.setLast_login_date(new Date());
        updateById(user);
        StpUtil.login(user.getId());
        SaTokenInfo token = StpUtil.getTokenInfo();
        StpUtil.getSession().set(user.getId().toString(), user);
        return token;
    }

    public SysUser login(String userName, String passWord, String openid) {
        QueryWrapper<SysUser> queryWrapper = dao.queryByName(userName, MD5Utils.getMD5(passWord));
        SysUser user = selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        user.setLast_login_date(new Date());
        user.setOpenid(openid);
        updateById(user);
        return user;
    }

    /**
     * create by wp at 2021/12/30 14:06
     * description: ??????????????????????????????
     *
     * @return com.jsdc.itss.model.SysUser
     */
    public SysUser getUser() {
        return (SysUser) StpUtil.getSession().get(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString());
    }

    public SysUser getUserByOpenid(String openid){
        SysUser sysUser = selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getOpenid, openid).eq(SysUser::getIs_del, GlobalData.ISDEL_NO));
        return sysUser;
    }


    /**
     * create by xuaolong
     * description:????????????????????????
     *
     * @param userId
     * @return
     */
    public ResultInfo selectSysAuthority(Integer userId) {
        if (null == userId) {
            SysUser user = getUser();
            userId = user.getId();
        }
        //???????????????????????????
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> roleList = sysUserRoleMapper.selectList(queryWrapper);
        //????????????????????????

        List<SysAuthority> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleList)){
            for (int i = 0 ; i < roleList.size(); i++){
                Integer role_id = roleList.get(i).getRole_id();
                List<SysAuthority> tempList = sysUserMapper.getSysAuthorityOther(role_id);
                //??????
                for (int j = 0 ; j <tempList.size() ; j++){
                    // ????????????????????????????????????
                    if (!list.contains(tempList.get(j))) {
                        list.add(tempList.get(j));
                    }
                }

            }
        }else {
            return ResultInfo.error("???????????????????????????");
        }

        List<SysAuthority> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysAuthority sysAuthority = list.get(i);
            if (sysAuthority.getParent_id() == -1) {
                resultList.add(list.get(i));
                setChildList(sysAuthority, list);
            }
        }
        return ResultInfo.success(resultList);
    }


    /**
     * create by xuaolong
     * description:????????????????????????
     *
     * @param userId
     * @return
     */
    public List<SysAuthority> selectSysAuthorityApp(Integer userId) {
        if (null == userId) {
            SysUser user = getUser();
            userId = user.getId();
        }
        //???????????????????????????
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> roleList = sysUserRoleMapper.selectList(queryWrapper);
        //????????????????????????

        List<SysAuthority> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleList)){
            for (int i = 0 ; i < roleList.size(); i++){
                Integer role_id = roleList.get(i).getRole_id();
                List<SysAuthority> tempList = sysUserMapper.getSysAuthorityOther(role_id);
                //??????
                for (int j = 0 ; j <tempList.size() ; j++){
                    // ????????????????????????????????????
                    if (!list.contains(tempList.get(j))) {
                        list.add(tempList.get(j));
                    }
                }
            }
        }else {
            List<SysAuthority> list1 = new ArrayList<>();
            return list1;
        }


        return list;
    }

    /**
     * ??????????????????????????????????????????
     * @param userId
     * @return
     */
    public Map<String, List<String>> getPermissions(Integer userId){
        List<SysAuthority> list = sysUserMapper.getSysAuthority(userId);
        List<SysAuthority> menus = list.stream().filter(x -> GlobalData.AUTHORITY_TOP_MENU == x.getLevel() || GlobalData.AUTHORITY_MENU == x.getLevel()).collect(Collectors.toList());
        List<SysAuthority> buttons = list.stream().filter(x -> GlobalData.AUTHORITY_BUTTON == x.getLevel()).collect(Collectors.toList());

        List<String> menuStr = menus.stream().map(x -> x.getCode()).collect(Collectors.toList());
        List<String> buttonStr = list.stream().map(x -> x.getCode()).collect(Collectors.toList());
        Map<String, List<String>> map = new HashMap<>();
        map.put("menuStr", menuStr);
        map.put("buttonStr", buttonStr);
        return map;
    }

    public List<String> getAppPermissions(Integer userId){
        List<SysAuthority> list = sysUserMapper.getSysAuthority(userId);
        List<String> ids = list.stream().map(x -> x.getCode()).collect(Collectors.toList());
        return ids;
    }

    public List<Integer> getAllPermissions(Integer userId){
        List<SysAuthority> list = sysUserMapper.getSysAuthority(userId);
        List<Integer> permissions = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        return permissions;
    }

    private void setChildList(SysAuthority sysAuthority, List<SysAuthority> sysAuthorityList) {
        for (SysAuthority temp : sysAuthorityList) {
            if (temp.getParent_id() == sysAuthority.getId()) {
                sysAuthority.getChildren().add(temp);
                setChildList(temp, sysAuthorityList);
            }
        }
    }


    /**
     * description:????????????excel
     *
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporUserExcel(HttpServletResponse response) {


        List<ExportSysUserVo> result = sysUserMapper.exporUserExcel();
        List<ExportSysUserVo> list = new ArrayList<>();
        for (ExportSysUserVo temp : result) {
            if (temp.getStatus() != null) {
                if (temp.getStatus() == 1) {
                    temp.setStatus_name("??????");
                } else if (temp.getStatus() == 2) {
                    temp.setStatus_name("??????");
                } else {
                    temp.setStatus_name("?????????");
                }
            }
            if (temp.getIs_vip() != null) {
                if (temp.getIs_vip() == 0) {
                    temp.setIs_vip_name("???");
                } else {
                    temp.setIs_vip_name("???");
                }
            }
            if (temp.getIs_emial() != null) {
                if (temp.getIs_emial() == 0) {
                    temp.setIs_emial_name("???");
                } else {
                    temp.setIs_emial_name("???");
                }
            }
            if (temp.getIs_wechat() != null) {
                if (temp.getIs_wechat() == 0) {
                    temp.setIs_wechat_name("???");
                } else {
                    temp.setIs_wechat_name("???");
                }
            }
            list.add(temp);
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("user_name", "?????????");
        writer.addHeaderAlias("status_name", "??????");
        writer.addHeaderAlias("user_type", "??????");
        writer.addHeaderAlias("mailbox", "??????");
        writer.addHeaderAlias("telephone", "??????");
        writer.addHeaderAlias("email_token", "????????????");
        writer.addHeaderAlias("manageKey", "manageKey");
        writer.addHeaderAlias("name", "??????");
        writer.addHeaderAlias("landline", "??????");
        writer.addHeaderAlias("is_vip_name", "??????VIP");
        writer.addHeaderAlias("position", "??????");
        writer.addHeaderAlias("last_login_date", "??????????????????");
        writer.addHeaderAlias("department_name", "??????");
        writer.addHeaderAlias("event_group_name", "??????????????????");
        writer.addHeaderAlias("role_name", "??????");
        writer.addHeaderAlias("pass", "????????????");
        writer.addHeaderAlias("is_emial_name", "????????????????????????");
        writer.addHeaderAlias("is_wechat_name", "????????????????????????");
        writer.addHeaderAlias("location", "??????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("update_time", "????????????");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
//        response.setContentType("application/octet-stream; charset=utf-8");

        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
//            response.setHeader("Content-disposition", "attachment;filename=" + "??????.xls");
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }


    /**
     * description:????????????excel
     *
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporUserRoleExcel(HttpServletResponse response) {
        List<ExportSysUserRoleVo> resultList = sysUserMapper.exporUserRoleExcel();
        List<ExportSysUserRoleVo> list = new ArrayList<>();
        for (ExportSysUserRoleVo temp : resultList) {
            if (temp.getStatus() == 1) {
                temp.setStatus_name("??????");
            } else if (temp.getStatus() == 2) {
                temp.setStatus_name("??????");
            } else {
                temp.setStatus_name("?????????");
            }
            list.add(temp);
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("user_name", "?????????");
        writer.addHeaderAlias("name", "??????");
        writer.addHeaderAlias("mailbox", "??????");
        writer.addHeaderAlias("location", "??????");
        writer.addHeaderAlias("dName", "????????????");
        writer.addHeaderAlias("status_name", "??????");
        writer.addHeaderAlias("rId", "??????ID");
        writer.addHeaderAlias("rName", "????????????");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }

    /**
     * @return java.util.List<com.jsdc.itss.model.SysUser>
     * @description ??????????????????????????????
     * @author wp
     * @date 2022/3/23
     */
    public List<SysUser> getUsers() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getIs_del, GlobalData.ISDEL_NO);
        return selectList(wrapper);
    }

    public ResultInfo logout() {
        SysUser sysUser = getUser();
        StpUtil.logout(sysUser.getId());
        return ResultInfo.success();
    }

    public ResultInfo checkOpenid(String openId){
        SysUser sysUser = selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getOpenid, openId).eq(SysUser::getIs_del, GlobalData.ISDEL_NO));
        if(null != sysUser){
            List<String> list = getAppPermissions(sysUser.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userInfo", sysUser);
            jsonObject.put("authority", list);
            return ResultInfo.success(jsonObject);
        }else{
            return ResultInfo.error("????????????");
        }
    }


    @Transactional
    public ResultInfo importUserList(MultipartFile file) {
        try {
            //fileName ?????????
            String fileName = file.getOriginalFilename();
            boolean xlsx = fileName.endsWith(".xlsx");
            if (!xlsx) {
                return ResultInfo.error("????????????.xlsx???????????????");
            }
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
            List<List<Object>> data = reader.read();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = data.size();
            if (size > 1) {
                for (int i = 1; i < size; i++) {
                    SysUser sysUser = new SysUser();
                    List<Object> line = data.get(i);
                    if (com.jsdc.itss.utils.StringUtils.isEmpty(line.get(0).toString()) || com.jsdc.itss.utils.StringUtils.isEmpty(line.get(15).toString())) {
                        return ResultInfo.error("????????????????????????");
                    } else {
                        //?????????
                        sysUser.setUser_name(line.get(0).toString());
                        //??????
                        String status = line.get(1).toString();
                        if (status.equals("??????")) {
                            sysUser.setStatus((short) 1);
                        } else if (status.equals("?????????")) {
                            sysUser.setStatus((short) 3);
                        } else if (status.equals("??????")) {
                            sysUser.setStatus((short) 2);

                        }
                        //??????
                        sysUser.setUser_type(line.get(2).toString());
                        //??????
                        sysUser.setMailbox(line.get(3).toString());
                        //??????
                        sysUser.setTelephone(line.get(4).toString());
                        //????????????
                        String email_token = line.get(5).toString();
                        //manageKey
                        String manageKey = line.get(6).toString();
                        //??????
                        sysUser.setName(line.get(7).toString());
                        //??????
                        sysUser.setLandline(line.get(8).toString());
                        //??????VIP
                        String is_vip = line.get(9).toString();
                        if (is_vip.equals("???")) {
                            sysUser.setIs_vip((short) 1);
                        } else if (is_vip.equals("???")) {
                            sysUser.setIs_vip((short) 0);
                        }
                        //??????
                        sysUser.setPosition(line.get(10).toString());
                        //??????????????????
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (null != line.get(11).toString()) {
                            Date last_login_date = simpleDateFormat.parse(line.get(10).toString());
                            sysUser.setLast_login_date(last_login_date);
                        }
                        //??????
                        if (null != line.get(12).toString()) {
                            QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
                            queryWrapper.eq("name", line.get(12).toString());
                            queryWrapper.eq("is_del", "0");
                            SysDepartment sysDepartment = sysDepartmentMapper.selectOne(queryWrapper);
                            if (null != sysDepartment) {
                                sysUser.setDepartment_id(sysDepartment.getId());
                            }
                        }
                        //??????????????????
                        List<Integer> gorupIds = new ArrayList<>();
                        if (null != line.get(13).toString()) {
                            String names = line.get(13).toString();
                            String[] name = names.split(",");
                            for (String temp : name) {
                                QueryWrapper<EventServiceManage> queryWrapper = new QueryWrapper<>();
                                queryWrapper.eq("name", temp);
                                queryWrapper.eq("is_del", "0");
                                EventServiceManage eventServiceManage = eventServiceManageMapper.selectOne(queryWrapper);
                                if (eventServiceManage != null) {
                                    gorupIds.add(eventServiceManage.getId());
                                }
                            }

                        }

                        //??????
                        List<Integer> roleIds = new ArrayList<>();
                        if (null != line.get(14).toString()) {
                            String names = line.get(14).toString();
                            String[] name = names.split(",");
                            for (String temp : name) {
                                QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
                                queryWrapper.eq("name", temp);
                                queryWrapper.eq("is_del", "0");
                                SysRole SysRole = sysRoleMapper.selectOne(queryWrapper);
                                if (SysRole != null) {
                                    roleIds.add(SysRole.getId());
                                }
                            }
                        }
                        //????????????
                        sysUser.setPass(line.get(15).toString());
                        //????????????????????????
                        if (line.get(16).toString().equals("???")) {
                            sysUser.setIs_emial((short) 1);
                        } else if (line.get(16).toString().equals("???")) {
                            sysUser.setIs_emial((short) 0);
                        }
                        //????????????????????????
                        if (line.get(17).toString().equals("???")) {
                            sysUser.setIs_wechat((short) 1);
                        } else if (line.get(17).toString().equals("???")) {
                            sysUser.setIs_wechat((short) 0);
                        }
                        //??????
                        sysUser.setLocation(line.get(18).toString());
                        //????????????
                        if (null != line.get(19).toString()) {
                            Date time = simpleDateFormat.parse(line.get(19).toString());
                            sysUser.setCreate_time(time);
                        }
                        //????????????
                        if (null != line.get(20).toString()) {
                            Date time = simpleDateFormat.parse(line.get(20).toString());
                            sysUser.setUpdate_time(time);
                        }

                        sysUserMapper.insert(sysUser);
                        if (!CollectionUtils.isEmpty(gorupIds)) {
                            for (Integer temp : gorupIds) {
                                SysUserEventGroup sysUserEventGroup = new SysUserEventGroup();
                                sysUserEventGroup.setIs_del("0");
                                sysUserEventGroup.setEvent_group_id(temp);
                                sysUserEventGroup.setUser_id(sysUser.getId());
                                sysUserEventGroupMapper.insert(sysUserEventGroup);
                            }
                        }

                        if (!CollectionUtils.isEmpty(roleIds)) {
                            for (Integer temp : roleIds) {
                                SysUserRole sysUserRole = new SysUserRole();
                                sysUserRole.setIs_del("0");
                                sysUserRole.setRole_id(temp);
                                sysUserRole.setUser_id(sysUser.getId());
                                sysUserRoleMapper.insert(sysUserRole);
                            }
                        }
                    }
                }
                return ResultInfo.success("???????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("???????????????(????????????????????????????????????????????????)");

    }

    /**
     * ???????????????????????????
     * Author wzn
     * Date 2022/3/22 16:34
     */
    public List<SysUser> upUserData() {
        List<SysUser> sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getIs_del, "0")
        );
        if (null != sysUsers && sysUsers.size() > 0) {
            sysUsers.forEach(a -> {
                if (null != a && null != a.getDepartment_id()) {
                    SysDepartment dt = sysDepartmentMapper.selectById(a.getDepartment_id());
                    a.setDepartmentName(dt.getName());
                }
            });
        }
        return sysUsers;
    }

    public List<SysUser> getUserList(String ids) {
        List<SysUser> sysUserList = new ArrayList<>();
        for (String id : ids.split(",")) {
            SysUser sysUser = sysUserMapper.selectById(id);
            if (null != sysUser) {
                sysUserList.add(sysUser);
            }
        }
        return sysUserList;
    }

    /**
     * ??????????????????
     * @param user_id
     * @return
     */
    public SysUser getUserData(Integer user_id) {
        SysUser sysUser = new SysUser();
        if (null != user_id) {
            sysUser = sysUserMapper.selectById(user_id);
        } else {
            sysUser = getUser();
        }
        return sysUser;
    }
}
