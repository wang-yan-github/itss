package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsBackupDao;
import com.jsdc.itss.mapper.AssetsBackupMapper;
import com.jsdc.itss.model.AssetsBackup;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Service
@Transactional
public class AssetsBackupService extends BaseService<AssetsBackupDao, AssetsBackup> {

    @Autowired
    private AssetsBackupMapper assetsBackupMapper;
    @Autowired
    private AssetsBackupDao assetsBackupDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsBackup> toList(Integer pageIndex, Integer pageSize, AssetsBackup beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<AssetsBackup> assetsBackupVos = assetsBackupMapper.toList(beanParam);

        PageInfo<AssetsBackup> page = new PageInfo<>(assetsBackupVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsBackup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsBackup assetsBackup = selectOne(queryWrapper);
        return ResultInfo.success(assetsBackup);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsBackup(AssetsBackup bean) {
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
    public ResultInfo editAssetsBackup(AssetsBackup bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
