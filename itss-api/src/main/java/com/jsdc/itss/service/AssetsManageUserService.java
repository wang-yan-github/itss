package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsManageUserDao;
import com.jsdc.itss.mapper.AssetsManageUserMapper;
import com.jsdc.itss.model.AssetsManageUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.Date;
import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsManageUserService extends BaseService<AssetsManageUserDao, AssetsManageUser> {

    @Autowired
    private AssetsManageUserMapper assetsManageUserMapper;
    @Autowired
    private AssetsManageUserDao assetsManageUserDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsManageUser> toList(Integer pageIndex, Integer pageSize, AssetsManageUser beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsManageUser> assetsManageUserVos = assetsManageUserMapper.toList(beanParam);

        PageInfo<AssetsManageUser> page = new PageInfo<>(assetsManageUserVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsManageUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsManageUser assetsManageUser = selectOne(queryWrapper);
        return ResultInfo.success(assetsManageUser);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsManageUser(AssetsManageUser bean) {
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
    public ResultInfo editAssetsManageUser(AssetsManageUser bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
