package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysAnnouncementDao;
import com.jsdc.itss.mapper.SysAnnouncementMapper;
import com.jsdc.itss.mapper.SysAnnouncementSeeMapper;
import com.jsdc.itss.mapper.SysUserMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysAnnouncementService extends BaseService<SysAnnouncementDao, SysAnnouncement> {

    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;
    @Autowired
    private SysAnnouncementDao sysAnnouncementDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysAnnouncementSeeMapper sysAnnouncementSeeMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    public PageInfo<SysAnnouncement> toList(Integer pageIndex, Integer pageSize, SysAnnouncement beanParam) {
        PageHelper.startPage(pageIndex, pageSize);
        List<SysAnnouncement> sysAnnouncementVos = sysAnnouncementMapper.toList(beanParam);
        for (SysAnnouncement temp : sysAnnouncementVos){
            if (temp.getCreate_user() != null){
                String create_user_name =getUserName(temp.getCreate_user());
                temp.setCreate_user_name(create_user_name);
            }


                QueryWrapper<SysAnnouncementSee> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("is_del","0");
                queryWrapper.eq("announcement_id",temp.getId());
                List<SysAnnouncementSee> list = sysAnnouncementSeeMapper.selectList(queryWrapper);
                List<SysUser> sysUserList = new ArrayList<>();
                List<SysRole> sysRoleList = new ArrayList<>();
                List<SysDepartment> sysDepartmentList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(list)){
                    for (int i = 0 ; i < list.size() ; i++){
                        //??????
                        if (0 == list.get(i).getVisual_type()){
                            if (null != list.get(i).getVisual_id()){
                                SysUser sysUser = sysUserMapper.selectById(list.get(i).getVisual_id());
                                if (null != sysUser){
                                    sysUserList.add(sysUser);
                                }
                            }
                        //??????
                        }else if (1 == list.get(i).getVisual_type()){
                            if (null != list.get(i).getVisual_id()){
                                SysRole sysRole = sysRoleService.selectById(list.get(i).getVisual_id());
                                if (null != sysRole){
                                    sysRoleList.add(sysRole);
                                }
                            }
                        //??????
                        }else {
                            if (null != list.get(i).getVisual_id()){
                                SysDepartment sysDepartment = sysDepartmentService.selectById(list.get(i).getVisual_id());
                                if (null != sysDepartment){
                                    sysDepartmentList.add(sysDepartment);
                                }
                            }
                        }
                    }
                }
                temp.setSysUserList(sysUserList);
                temp.setSysRoleList(sysRoleList);
                temp.setSysDepartmentList(sysDepartmentList);

        }
        PageInfo<SysAnnouncement> page = new PageInfo<>(sysAnnouncementVos);

        return page;
    }

    public String getUserName (Integer id){
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser != null){
            return sysUser.getName();
        }
        return null;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<SysAnnouncement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        SysAnnouncement sysAnnouncement = selectOne(queryWrapper);

        QueryWrapper<SysAnnouncementSee> announcementSeeQueryWrapper = new QueryWrapper<>();
        announcementSeeQueryWrapper.eq("is_del", 0);
        announcementSeeQueryWrapper.eq("announcement_id", id);

        sysAnnouncement.setAnnouncementSees(sysAnnouncementSeeMapper.selectList(announcementSeeQueryWrapper));
        return ResultInfo.success(sysAnnouncement);
    }

    /**
     * ??????
     */
    public ResultInfo addSysAnnouncement(SysAnnouncement bean) {
        if (bean.getId() == null || bean.getId() .equals("")){
            // ????????????
            bean.setIs_del(String.valueOf(0));
            // ????????????
            bean.setCreate_time(new Date());
            // ?????????
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);

//            if (null == bean.getAnnouncementSees() || CollectionUtils.isEmpty(bean.getAnnouncementSees())) {
//                return ResultInfo.success();
//            }

            for (SysAnnouncementSee member : bean.getAnnouncementSees()) {
                member.setAnnouncement_id(bean.getId());
                // ????????????
                member.setIs_del(String.valueOf(0));
                // ????????????
                member.setCreate_time(new Date());
                // ?????????
                member.setCreate_user(sysUserService.getUser().getId());

                sysAnnouncementSeeMapper.insert(member);
            }
            return ResultInfo.success();
        }else {
            return editSysAnnouncement(bean);
        }
    }

    /**
     * ??????
     */
    public ResultInfo editSysAnnouncement(SysAnnouncement bean) {
        // ?????????
         bean.setUpdate_user(sysUserService.getUser().getId());
        // ????????????
        bean.setUpdate_time(new Date());
        updateById(bean);

//        if(null == bean.getAnnouncementSees() || CollectionUtils.isEmpty(bean.getAnnouncementSees())){
//            return ResultInfo.success();
//        }

        //??????announcement_id???????????????????????? ????????????
        UpdateWrapper<SysAnnouncementSee> announcementSeeUpdateWrapper = new UpdateWrapper();
        announcementSeeUpdateWrapper.eq("announcement_id", bean.getId());
        announcementSeeUpdateWrapper.set("is_del", "1");
        sysAnnouncementSeeMapper.update(null, announcementSeeUpdateWrapper);

        for (SysAnnouncementSee member : bean.getAnnouncementSees()) {
            member.setAnnouncement_id(bean.getId());
            // ????????????
            member.setIs_del(String.valueOf(0));
            // ????????????
            member.setCreate_time(new Date());
            // ?????????
            member.setCreate_user(sysUserService.getUser().getId());
            sysAnnouncementSeeMapper.insert(member);
        }
        return ResultInfo.success();
    }

    public ResultInfo deleteSysAnnouncement(Integer id) {
        SysAnnouncement bean = selectById(id);
        // ?????????
        bean.setUpdate_user(sysUserService.getUser().getId());
        // ????????????
        bean.setUpdate_time(new Date());
        //??????????????????
        bean.setIs_del("1");
        updateById(bean);

        //??????announcement_id?????????????????????
        UpdateWrapper<SysAnnouncementSee> announcementSeeUpdateWrapper = new UpdateWrapper();
        announcementSeeUpdateWrapper.eq("announcement_id", bean.getId());
        announcementSeeUpdateWrapper.set("is_del", "1");
        sysAnnouncementSeeMapper.update(null, announcementSeeUpdateWrapper);

        return ResultInfo.success();
    }

    public PageInfo<SysAnnouncement> toNoticeList(Integer pageIndex, Integer pageSize, SysAnnouncement beanParam) {

        // ?????????????????????
        SysUser loginUser = sysUserService.getUser();

        List<SysAnnouncementSee> sysAnnouncementSees = sysAnnouncementSeeMapper.selectList(Wrappers.<SysAnnouncementSee>lambdaQuery().eq(SysAnnouncementSee::getIs_del, "0")
                // ????????????visual_type = 0 ????????????,????????????id,?????????1 ????????????,????????????id,?????????2 ????????????,????????????id
                .and(i -> i.eq(SysAnnouncementSee::getVisual_type, "0").eq(SysAnnouncementSee::getVisual_id, loginUser.getId())
                        .or().eq(SysAnnouncementSee::getVisual_type, "1").inSql(SysAnnouncementSee::getVisual_id, "select role_id from sys_user_role where user_id = " + loginUser.getId())
                        .or().eq(SysAnnouncementSee::getVisual_type, "2").eq(SysAnnouncementSee::getVisual_id, loginUser.getDepartment_id())
                )
        );
        if (CollectionUtils.isEmpty(sysAnnouncementSees)) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<SysAnnouncement> list = sysAnnouncementMapper.selectList(Wrappers.<SysAnnouncement>lambdaQuery().eq(SysAnnouncement::getIs_del, "0")
                                .like(null != beanParam && StringUtils.isNotBlank(beanParam.getTitle()), SysAnnouncement::getTitle, beanParam.getTitle())
                // ???????????? 0 ??????  1 ???????????? ?????? ??????  scope
                // ?????? scope ?????????1, ?????????1,???????????????????????????????????????
                .and(i -> i.eq(SysAnnouncement::getScope, "0")
                        .or()
                        .eq(SysAnnouncement::getScope, "1").in(SysAnnouncement::getId, sysAnnouncementSees.stream().map(SysAnnouncementSee::getAnnouncement_id).distinct().collect(Collectors.toList()))
                )
                .orderByDesc(SysAnnouncement::getCreate_time)
        );
        PageInfo<SysAnnouncement> page = new PageInfo<>(list);

        return page;
    }
}
