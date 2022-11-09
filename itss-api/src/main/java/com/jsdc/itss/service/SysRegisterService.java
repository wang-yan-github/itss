package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysRegisterDao;
import com.jsdc.itss.mapper.SysRegisterMapper;
import com.jsdc.itss.model.SysRegister;
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
public class SysRegisterService extends BaseService<SysRegisterDao, SysRegister> {

    @Autowired
    private SysRegisterMapper sysRegisterMapper;
    @Autowired
    private SysRegisterDao sysRegisterDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysRegister> toList(Integer pageIndex, Integer pageSize, SysRegister beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysRegister> sysRegisterVos = sysRegisterMapper.toList(beanParam);

        PageInfo<SysRegister> page = new PageInfo<>(sysRegisterVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysRegister> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysRegister sysRegister = selectOne(queryWrapper);
        return ResultInfo.success(sysRegister);
    }

    /**
     *  添加
     */
    public ResultInfo addSysRegister(SysRegister bean) {
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
    public ResultInfo editSysRegister(SysRegister bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
