package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventRequestTypeDao;
import com.jsdc.itss.mapper.EventRequestTypeMapper;
import com.jsdc.itss.model.EventRequestType;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventRequestTypeService extends BaseService<EventRequestTypeDao, EventRequestType> {

    @Autowired
    private EventRequestTypeMapper eventRequestTypeMapper;
    @Autowired
    private EventRequestTypeDao eventRequestTypeDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 请求类型数据列表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageInfo<EventRequestType> toList(EventRequestType eventRequestType, Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<EventRequestType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(eventRequestType.getRequest_type_name())) {
            queryWrapper.like("request_type_name", eventRequestType.getRequest_type_name());
        }
        if (null != eventRequestType.getIs_default()) {
            queryWrapper.eq("is_default", eventRequestType.getIs_default());
        }
        if (null != eventRequestType.getIs_use()) {
            queryWrapper.eq("is_use", eventRequestType.getIs_use());
        }
        queryWrapper.orderByAsc("sort");
        List<EventRequestType> eventRequestTypeList = selectList(queryWrapper);
        PageInfo<EventRequestType> page = new PageInfo<>(eventRequestTypeList);
        return page;
    }

    /**
     * 请求类型数据列表
     *
     * @return
     */
    public List<EventRequestType> getList(EventRequestType eventRequestType) {
        QueryWrapper<EventRequestType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(eventRequestType.getRequest_type_name())) {
            queryWrapper.like("request_type_name", eventRequestType.getRequest_type_name());
        }
        if (null != eventRequestType.getIs_default()) {
            queryWrapper.eq("is_default", eventRequestType.getIs_default());
        }
        if (null != eventRequestType.getIs_use()) {
            queryWrapper.eq("is_use", eventRequestType.getIs_use());
        }
        queryWrapper.orderByAsc("sort,id");
        List<EventRequestType> eventRequestTypeList = selectList(queryWrapper);
        return eventRequestTypeList;
    }

    /**
     * 请求类型详情
     *
     * @param id
     * @return
     */
    public ResultInfo getById(Integer id) {
        QueryWrapper<EventRequestType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        EventRequestType eventRequestType = selectOne(queryWrapper);
        return ResultInfo.success(eventRequestType);
    }

    /**
     * 新增请求类型
     *
     * @param assetsStatus
     * @return
     */
    public ResultInfo addEventRequestType(EventRequestType eventRequestType) {
        QueryWrapper<EventRequestType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("request_type_name", eventRequestType.getRequest_type_name());
        Long count = eventRequestTypeMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已经存在");
        }
        // 删除状态
        eventRequestType.setIs_del(String.valueOf(0));
        // 创建时间
        eventRequestType.setCreate_time(new Date());
        // 创建者
        eventRequestType.setCreate_user(sysUserService.getUser().getId());
        insert(eventRequestType);
        if (Base.notEmpty(eventRequestType.getIs_default()) && eventRequestType.getIs_default() == 1) {
            //是否默认0否 1是
            LambdaUpdateWrapper<EventRequestType> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventRequestType::getIs_default, 0)
                    .ne(EventRequestType::getId, eventRequestType.getId());
            update(null, wrapper);
        }
        return ResultInfo.success();
    }

    /**
     * 编辑资产状态
     *
     * @param assetsStatus
     * @return
     */
    public ResultInfo editEventRequestType(EventRequestType eventRequestType) {
        QueryWrapper<EventRequestType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("request_type_name", eventRequestType.getRequest_type_name()).ne("id",eventRequestType.getId());
        Long count = eventRequestTypeMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已经存在");
        }

        // 修改者
        eventRequestType.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        eventRequestType.setUpdate_time(new Date());
        updateById(eventRequestType);

        if (Base.notEmpty(eventRequestType.getIs_default()) && eventRequestType.getIs_default() == 1) {
            //是否默认0否 1是
            LambdaUpdateWrapper<EventRequestType> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventRequestType::getIs_default, 0)
                    .ne(EventRequestType::getId, eventRequestType.getId());
            update(null, wrapper);
        }

        return ResultInfo.success();
    }

    public ResultInfo delete(Integer id) {
        EventRequestType eventRequestType = new EventRequestType();
        eventRequestType.setId(id);
        eventRequestType.setIs_del("1");
        eventRequestTypeMapper.updateById(eventRequestType);
        return ResultInfo.success();
    }

    public List<EventRequestType> upList() {
        return eventRequestTypeMapper.selectList(Wrappers.<EventRequestType>lambdaQuery()
                .eq(EventRequestType::getIs_del, "0").eq(EventRequestType::getIs_use, "1"));
    }


}
