package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysAnnouncementSeeDao;
import com.jsdc.itss.mapper.SysAnnouncementSeeMapper;
import com.jsdc.itss.model.SysAnnouncementSee;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysAnnouncementSeeService extends BaseService<SysAnnouncementSeeDao, SysAnnouncementSee> {

    @Autowired
    private SysAnnouncementSeeMapper sysAnnouncementSeeMapper;
    @Autowired
    private SysAnnouncementSeeDao sysAnnouncementSeeDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysAnnouncementSee> toList(Integer pageIndex, Integer pageSize, SysAnnouncementSee beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysAnnouncementSee> sysAnnouncementSeeVos = sysAnnouncementSeeMapper.toList(beanParam);

        PageInfo<SysAnnouncementSee> page = new PageInfo<>(sysAnnouncementSeeVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysAnnouncementSee> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysAnnouncementSee sysAnnouncementSee = selectOne(queryWrapper);
        return ResultInfo.success(sysAnnouncementSee);
    }

    /**
     *  添加
     */
    public ResultInfo addSysAnnouncementSee(SysAnnouncementSee bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editSysAnnouncementSee(SysAnnouncementSee bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
