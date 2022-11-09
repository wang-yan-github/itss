package com.jsdc.itss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SlaEventDao;
import com.jsdc.itss.mapper.SlaEventConfigMapper;
import com.jsdc.itss.mapper.SlaEventMapper;
import com.jsdc.itss.model.SlaEvent;
import com.jsdc.itss.model.SlaEventConfig;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SlaEventFirstAndRequestVo;
import com.jsdc.itss.vo.SlaEventVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaEventService
 * Description:
 * date: 2022/3/7 14:51
 *
 * @author bn
 */
@Transactional
@Service
public class SlaEventService extends BaseService<SlaEventDao, SlaEvent> {

    @Autowired
    private SlaEventMapper slaEventMapper;
    @Autowired
    private SlaEventConfigService slaEventConfigService;
    @Autowired
    private SlaEventConfigMapper slaEventConfigMapper;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SlaEvent> toList(Integer pageIndex, Integer pageSize, SlaEvent slaEvent) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<SlaEvent> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(slaEvent.getSla_event_name())) {
            queryWrapper.like("sla_event_name", slaEvent.getSla_event_name());
        }
        queryWrapper.eq("is_del", "0");
        queryWrapper.orderByDesc("id");
        List<SlaEvent> slaEvents = selectList(queryWrapper);
        PageInfo<SlaEvent> pageInfo = new PageInfo<>(slaEvents);
        return pageInfo;

    }

    @Transactional
    public ResultInfo toAdd(SlaEventVo slaEventVo) {
        QueryWrapper<SlaEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("sla_event_name",slaEventVo.getSla_event_name());
        Long count = slaEventMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        SlaEvent slaEvent = new SlaEvent();
        BeanUtils.copyProperties(slaEventVo, slaEvent);
        slaEvent.setCreate_user(sysUserService.getUser().getId());
        slaEvent.setCreate_time(new Date());
        slaEvent.setIs_del(GlobalData.ISDEL_NO);
        if (insert(slaEvent) > 0) {
            slaEventVo.getSlaEventFirstAndRequestVos().forEach(x -> {
                SlaEventConfig slaEventConfig = new SlaEventConfig();
                BeanUtils.copyProperties(x, slaEventConfig);
                slaEventConfig.setSla_event_id(slaEvent.getId());
                slaEventConfig.setCreate_time(new Date());
                slaEventConfig.setCreate_user(sysUserService.getUser().getId());
                slaEventConfig.setIs_del(GlobalData.ISDEL_NO);
                slaEventConfigService.insert(slaEventConfig);
            });
        }
        return ResultInfo.success("操作成功");
    }

    @Transactional
    public ResultInfo toEdit(SlaEventVo slaEventVo) {
        QueryWrapper<SlaEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("sla_event_name",slaEventVo.getSla_event_name()).ne("id",slaEventVo.getId());
        Long count = slaEventMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        SlaEvent slaEvent = new SlaEvent();
        BeanUtils.copyProperties(slaEventVo, slaEvent);
        slaEvent.setUpdate_user(sysUserService.getUser().getId());
        slaEvent.setUpdate_time(new Date());
        //编辑
        if (updateById(slaEvent) > 0) {
            // 逻辑删除之前的配置数据
            UpdateWrapper<SlaEventConfig> wrapper = new UpdateWrapper<>();
            wrapper.lambda().set(SlaEventConfig::getIs_del, "1").
                    eq(SlaEventConfig::getSla_event_id, slaEvent.getId());
            slaEventConfigService.update(null, wrapper);
            if (slaEventVo.getSlaEventFirstAndRequestVos() != null && slaEventVo.getSlaEventFirstAndRequestVos().size() > 0) {
                slaEventVo.getSlaEventFirstAndRequestVos().forEach(x -> {
                    SlaEventConfig slaEventConfig = new SlaEventConfig();
                    BeanUtils.copyProperties(x, slaEventConfig);
                    if (slaEventConfig.getId() != null) {
                        //编辑
                        slaEventConfig.setIs_del(GlobalData.ISDEL_NO);
                        slaEventConfig.setUpdate_time(new Date());
                        slaEventConfig.setUpdate_user(sysUserService.getUser().getId());
                        slaEventConfigService.updateById(slaEventConfig);
                    } else {
                        //新增
                        slaEventConfig.setSla_event_id(slaEvent.getId());
                        slaEventConfig.setCreate_time(new Date());
                        slaEventConfig.setCreate_user(sysUserService.getUser().getId());
                        slaEventConfig.setIs_del(GlobalData.ISDEL_NO);
                        slaEventConfigService.insert(slaEventConfig);
                    }

                });
            }
        }

        return ResultInfo.success("操作成功");
    }

    public ResultInfo getSlaEvent(SlaEvent slaEvent) {

        List<SlaEventVo> slaEvents = slaEventMapper.getSlaEvent(slaEvent);
        slaEvents.forEach(x -> {
            x.setSlaEventFirstAndRequestVos(slaEventConfigMapper.getSlaEventConfigVos(x));
        });
        return ResultInfo.success(slaEvents);
    }

    public ResultInfo getFirstAndRequest(String is_first, String is_request) {
        List<SlaEventFirstAndRequestVo> list = new ArrayList<>();
        if (is_first.equals("0") && is_request.equals("0")) {
            SlaEventFirstAndRequestVo slaEventFirstAndRequestVo = new SlaEventFirstAndRequestVo();
            slaEventFirstAndRequestVo.setFirst_name("-全部-");
            slaEventFirstAndRequestVo.setRequest_type_name("-全部-");
            list.add(slaEventFirstAndRequestVo);
        } else {
            list = slaEventMapper.getSlaEventFirstAndRequest(is_first, is_request);
        }
        return ResultInfo.success(list);
    }

    public List<SlaEvent> getSlaByEventCategory(Integer eventCategoryId) {
        return slaEventMapper.getSlaByEventCategory(eventCategoryId);
    }
}
