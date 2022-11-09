package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysWorkingDaysDistinguishDao;
import com.jsdc.itss.mapper.SysWorkingDaysDistinguishMapper;
import com.jsdc.itss.model.SysWorkingDaysDistinguish;
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
public class SysWorkingDaysDistinguishService extends BaseService<SysWorkingDaysDistinguishDao, SysWorkingDaysDistinguish> {

    @Autowired
    private SysWorkingDaysDistinguishMapper sysWorkingDaysDistinguishMapper;
    @Autowired
    private SysWorkingDaysDistinguishDao sysWorkingDaysDistinguishDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysWorkingDaysDistinguish> toList(Integer pageIndex, Integer pageSize, SysWorkingDaysDistinguish beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysWorkingDaysDistinguish> sysWorkingDaysDistinguishVos = sysWorkingDaysDistinguishMapper.toList(beanParam);

        PageInfo<SysWorkingDaysDistinguish> page = new PageInfo<>(sysWorkingDaysDistinguishVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysWorkingDaysDistinguish> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysWorkingDaysDistinguish sysWorkingDaysDistinguish = selectOne(queryWrapper);
        return ResultInfo.success(sysWorkingDaysDistinguish);
    }

    /**
     *  添加
     */
    public ResultInfo addSysWorkingDaysDistinguish(SysWorkingDaysDistinguish bean) {
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
    public ResultInfo editSysWorkingDaysDistinguish(SysWorkingDaysDistinguish bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
