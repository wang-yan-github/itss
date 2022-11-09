package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventSatisfactionDao;
import com.jsdc.itss.mapper.EventSatisfactionMapper;
import com.jsdc.itss.model.EventSatisfaction;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-22 10:30:09
 */
@Service
@Transactional
public class EventSatisfactionService extends BaseService<EventSatisfactionDao, EventSatisfaction> {

    @Autowired
    private EventSatisfactionMapper eventSatisfactionMapper;
    @Autowired
    private EventSatisfactionDao eventSatisfactionDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<EventSatisfaction> toList(Integer pageIndex, Integer pageSize, EventSatisfaction beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<EventSatisfaction> eventSatisfactionVos = eventSatisfactionMapper.toList(beanParam);

        PageInfo<EventSatisfaction> page = new PageInfo<>(eventSatisfactionVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<EventSatisfaction> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        EventSatisfaction eventSatisfaction = selectOne(queryWrapper);
        return ResultInfo.success(eventSatisfaction);
    }

    /**
     *  添加
     */
    public ResultInfo addEventSatisfaction(EventSatisfaction bean) {
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
    public ResultInfo editEventSatisfaction(EventSatisfaction bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
