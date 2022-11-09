package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventParamConfigDao;
import com.jsdc.itss.mapper.EventParamConfigMapper;
import com.jsdc.itss.model.EventParamConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 事件参数管理表
 *
 * @Author thr
 * @create 2022-07-13 14:17:38
 */
@Service
@Transactional
public class EventParamConfigService extends BaseService<EventParamConfigDao, EventParamConfig> {

    @Autowired
    private EventParamConfigMapper eventParamConfigMapper;
    @Autowired
    private SysUserService sysUserService;

    public ResultInfo getById(Integer id) {
        QueryWrapper<EventParamConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        EventParamConfig eventParamConfig = selectOne(queryWrapper);
        return ResultInfo.success(eventParamConfig);
    }

    public ResultInfo getConfig() {
        QueryWrapper<EventParamConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.orderByDesc("id");
        queryWrapper.last(" limit 1");
        EventParamConfig eventParamConfig = selectOne(queryWrapper);
        return ResultInfo.success(eventParamConfig);
    }

    /**
     * 添加
     */
    public ResultInfo addEventParamConfig(EventParamConfig bean) {
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
     * 编辑
     */
    public ResultInfo editEventParamConfig(EventParamConfig bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
