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
                        //人员
                        if (0 == list.get(i).getVisual_type()){
                            if (null != list.get(i).getVisual_id()){
                                SysUser sysUser = sysUserMapper.selectById(list.get(i).getVisual_id());
                                if (null != sysUser){
                                    sysUserList.add(sysUser);
                                }
                            }
                        //角色
                        }else if (1 == list.get(i).getVisual_type()){
                            if (null != list.get(i).getVisual_id()){
                                SysRole sysRole = sysRoleService.selectById(list.get(i).getVisual_id());
                                if (null != sysRole){
                                    sysRoleList.add(sysRole);
                                }
                            }
                        //部门
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
     * 添加
     */
    public ResultInfo addSysAnnouncement(SysAnnouncement bean) {
        if (bean.getId() == null || bean.getId() .equals("")){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);

//            if (null == bean.getAnnouncementSees() || CollectionUtils.isEmpty(bean.getAnnouncementSees())) {
//                return ResultInfo.success();
//            }

            for (SysAnnouncementSee member : bean.getAnnouncementSees()) {
                member.setAnnouncement_id(bean.getId());
                // 删除状态
                member.setIs_del(String.valueOf(0));
                // 创建时间
                member.setCreate_time(new Date());
                // 创建者
                member.setCreate_user(sysUserService.getUser().getId());

                sysAnnouncementSeeMapper.insert(member);
            }
            return ResultInfo.success();
        }else {
            return editSysAnnouncement(bean);
        }
    }

    /**
     * 编辑
     */
    public ResultInfo editSysAnnouncement(SysAnnouncement bean) {
        // 修改者
         bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);

//        if(null == bean.getAnnouncementSees() || CollectionUtils.isEmpty(bean.getAnnouncementSees())){
//            return ResultInfo.success();
//        }

        //根据announcement_id删除掉关联表数据 重新添加
        UpdateWrapper<SysAnnouncementSee> announcementSeeUpdateWrapper = new UpdateWrapper();
        announcementSeeUpdateWrapper.eq("announcement_id", bean.getId());
        announcementSeeUpdateWrapper.set("is_del", "1");
        sysAnnouncementSeeMapper.update(null, announcementSeeUpdateWrapper);

        for (SysAnnouncementSee member : bean.getAnnouncementSees()) {
            member.setAnnouncement_id(bean.getId());
            // 删除状态
            member.setIs_del(String.valueOf(0));
            // 创建时间
            member.setCreate_time(new Date());
            // 创建者
            member.setCreate_user(sysUserService.getUser().getId());
            sysAnnouncementSeeMapper.insert(member);
        }
        return ResultInfo.success();
    }

    public ResultInfo deleteSysAnnouncement(Integer id) {
        SysAnnouncement bean = selectById(id);
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        //设置删除状态
        bean.setIs_del("1");
        updateById(bean);

        //根据announcement_id删除关联表数据
        UpdateWrapper<SysAnnouncementSee> announcementSeeUpdateWrapper = new UpdateWrapper();
        announcementSeeUpdateWrapper.eq("announcement_id", bean.getId());
        announcementSeeUpdateWrapper.set("is_del", "1");
        sysAnnouncementSeeMapper.update(null, announcementSeeUpdateWrapper);

        return ResultInfo.success();
    }

    public PageInfo<SysAnnouncement> toNoticeList(Integer pageIndex, Integer pageSize, SysAnnouncement beanParam) {

        // 得到当前登录人
        SysUser loginUser = sysUserService.getUser();

        List<SysAnnouncementSee> sysAnnouncementSees = sysAnnouncementSeeMapper.selectList(Wrappers.<SysAnnouncementSee>lambdaQuery().eq(SysAnnouncementSee::getIs_del, "0")
                // 判断如果visual_type = 0 则是用户,匹配用户id,如果是1 则是角色,匹配角色id,如果是2 则是部门,匹配部门id
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
                // 可见范围 0 公开  1 指定人员 角色 部门  scope
                // 判断 scope 是否为1, 如果是1,则需要判断是否在可见范围内
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
