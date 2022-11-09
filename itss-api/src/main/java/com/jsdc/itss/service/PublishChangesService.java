package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.PublishChangesDao;
import com.jsdc.itss.mapper.PublishChangesMapper;
import com.jsdc.itss.model.PublishChanges;
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
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Service
@Transactional
public class PublishChangesService extends BaseService<PublishChangesDao, PublishChanges> {

    @Autowired
    private PublishChangesMapper publishChangesMapper;
    @Autowired
    private PublishChangesDao publishChangesDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<PublishChanges> toList(Integer pageIndex, Integer pageSize, PublishChanges beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<PublishChanges> publishChangesVos = publishChangesMapper.toList(beanParam);

        PageInfo<PublishChanges> page = new PageInfo<>(publishChangesVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<PublishChanges> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        PublishChanges publishChanges = selectOne(queryWrapper);
        return ResultInfo.success(publishChanges);
    }

    /**
     *  添加
     */
    public ResultInfo addPublishChanges(PublishChanges bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(null == bean.getUserId() ? sysUserService.getUser().getId() : bean.getUserId());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editPublishChanges(PublishChanges bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
