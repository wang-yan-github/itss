package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventChangeRelationDao;
import com.jsdc.itss.mapper.EventChangeRelationMapper;
import com.jsdc.itss.model.EventChangeRelation;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-17 11:41:40
 */
@Service
@Transactional
public class EventChangeRelationService extends BaseService<EventChangeRelationDao, EventChangeRelation> {

    @Autowired
    private EventChangeRelationMapper eventChangeRelationMapper;
    @Autowired
    private EventChangeRelationDao eventChangeRelationDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<EventChangeRelation> toList(Integer pageIndex, Integer pageSize, EventChangeRelation beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<EventChangeRelation> eventChangeRelationVos = eventChangeRelationMapper.toList(beanParam);

        PageInfo<EventChangeRelation> page = new PageInfo<>(eventChangeRelationVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<EventChangeRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        EventChangeRelation eventChangeRelation = selectOne(queryWrapper);
        return ResultInfo.success(eventChangeRelation);
    }

    /**
     *  添加
     */
    public ResultInfo addEventChangeRelation(EventChangeRelation bean) {
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
    public ResultInfo editEventChangeRelation(EventChangeRelation bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
