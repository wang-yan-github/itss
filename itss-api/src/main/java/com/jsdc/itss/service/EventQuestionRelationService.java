package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventQuestionRelationDao;
import com.jsdc.itss.mapper.EventQuestionRelationMapper;
import com.jsdc.itss.model.EventQuestionRelation;
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
public class EventQuestionRelationService extends BaseService<EventQuestionRelationDao, EventQuestionRelation> {

    @Autowired
    private EventQuestionRelationMapper eventQuestionRelationMapper;
    @Autowired
    private EventQuestionRelationDao eventQuestionRelationDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<EventQuestionRelation> toList(Integer pageIndex, Integer pageSize, EventQuestionRelation beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<EventQuestionRelation> eventQuestionRelationVos = eventQuestionRelationMapper.toList(beanParam);

        PageInfo<EventQuestionRelation> page = new PageInfo<>(eventQuestionRelationVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<EventQuestionRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        EventQuestionRelation eventQuestionRelation = selectOne(queryWrapper);
        return ResultInfo.success(eventQuestionRelation);
    }

    /**
     *  添加
     */
    public ResultInfo addEventQuestionRelation(EventQuestionRelation bean) {
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
    public ResultInfo editEventQuestionRelation(EventQuestionRelation bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
