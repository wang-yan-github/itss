package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigStatusDao;
import com.jsdc.itss.mapper.AssetsConfigStatusMapper;
import com.jsdc.itss.model.AssetsConfigStatus;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Service
@Transactional
public class AssetsConfigStatusService extends BaseService<AssetsConfigStatusDao, AssetsConfigStatus> {

    @Autowired
    private AssetsConfigStatusMapper assetsConfigStatusMapper;
    @Autowired
    private AssetsConfigStatusDao assetsConfigStatusDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsConfigStatus> toList(Integer pageIndex, Integer pageSize, AssetsConfigStatus beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsConfigStatus> assetsConfigStatusVos = assetsConfigStatusMapper.toList(beanParam);

        PageInfo<AssetsConfigStatus> page = new PageInfo<>(assetsConfigStatusVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsConfigStatus> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsConfigStatus assetsConfigStatus = selectOne(queryWrapper);
        return ResultInfo.success(assetsConfigStatus);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsConfigStatus(AssetsConfigStatus bean) {
        if (bean.getId() == null){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
            return ResultInfo.success();
        }else {
            // 修改者
            bean.setUpdate_user(sysUserService.getUser().getId());
            // 修改时间
            bean.setUpdate_time(new Date());
            updateById(bean);
            return ResultInfo.success();
        }

    }

    /**
     *  删除
     */
    public ResultInfo delAssetsConfigStatus(Integer ids) {
        UpdateWrapper<AssetsConfigStatus> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",ids);
        updateWrapper.set("is_del","1");
        assetsConfigStatusMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }}
