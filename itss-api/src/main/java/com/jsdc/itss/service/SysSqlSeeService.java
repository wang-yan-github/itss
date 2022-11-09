package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysSqlSeeDao;
import com.jsdc.itss.mapper.SysSqlSeeMapper;
import com.jsdc.itss.model.SysSqlSee;
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
public class SysSqlSeeService extends BaseService<SysSqlSeeDao, SysSqlSee> {

    @Autowired
    private SysSqlSeeMapper sysSqlSeeMapper;
    @Autowired
    private SysSqlSeeDao sysSqlSeeDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysSqlSee> toList(Integer pageIndex, Integer pageSize, SysSqlSee beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysSqlSee> sysSqlSeeVos = sysSqlSeeMapper.toList(beanParam);

        PageInfo<SysSqlSee> page = new PageInfo<>(sysSqlSeeVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysSqlSee> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysSqlSee sysSqlSee = selectOne(queryWrapper);
        return ResultInfo.success(sysSqlSee);
    }

    /**
     *  添加
     */
    public ResultInfo addSysSqlSee(SysSqlSee bean) {
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
    public ResultInfo editSysSqlSee(SysSqlSee bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
