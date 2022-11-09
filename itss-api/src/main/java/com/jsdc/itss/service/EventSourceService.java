package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventSourceDao;
import com.jsdc.itss.dao.SysCompanyDao;
import com.jsdc.itss.mapper.EventSourceMapper;
import com.jsdc.itss.mapper.SysCompanyMapper;
import com.jsdc.itss.model.AssetsCustomerContract;
import com.jsdc.itss.model.EventRequestType;
import com.jsdc.itss.model.EventSource;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventSourceService extends BaseService<EventSourceDao, EventSource> {
    @Autowired
    private EventSourceMapper eventSourceMapper;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AssetsCustomerContractService assetsCustomerContractService;

    /**
     * 分页查询来源列表
     * Author wzn
     * Date 2022/3/9 10:55
     */
    public ResultInfo getPage(EventSource eventSource, int pageIndex, int pageSize) {
        QueryWrapper<EventSource> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(eventSource.getResource_name())) {
            queryWrapper.like("resource_name ", eventSource.getResource_name());
        }
        if (StringUtils.isNotEmpty(eventSource.getIs_use())) {
            queryWrapper.eq("is_use", eventSource.getIs_use());
        }
        queryWrapper.eq("is_del", "0");
        queryWrapper.orderByDesc("id");
        PageHelper.startPage(pageIndex, pageSize);
        List<EventSource> list = eventSourceMapper.selectList(queryWrapper);
//        list.forEach(x->{
//            if (Base.notEmpty(x.getCustomer_contract_id())) {
//                x.setContractName(assetsCustomerContractService.selectById(x.getCustomer_contract_id()).getContract_name());
//            }
//        });
        PageInfo<EventSource> pageInfo = new PageInfo<EventSource>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 查询来源列表
     * Author wzn
     * Date 2022/3/9 10:55
     */
    public List<EventSource> getList(EventSource eventSource) {
        QueryWrapper<EventSource> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(eventSource.getResource_name())) {
            queryWrapper.eq("resource_name", eventSource.getResource_name());
        }
        if (eventSource.getIs_use() != null && !"".equals(eventSource.getIs_use())) {
            queryWrapper.eq("is_use", eventSource.getIs_use());
        }
        queryWrapper.eq("is_del", GlobalData.ISDEL_NO);
        queryWrapper.orderByAsc("sort,id");
        return eventSourceMapper.selectList(queryWrapper);
    }

    /**
     * 新增来源
     * Author wzn
     * Date 2022/3/9 10:56
     */
    public ResultInfo addEventSource(EventSource eventSource) {
        eventSource.setIs_del("0");
        eventSource.setCreate_time(new Date());
        eventSource.setCreate_user(sysUserService.getUser().getId());
        eventSourceMapper.insert(eventSource);
        return ResultInfo.success();
    }


    /**
     * 来源修改接口
     * Author wzn
     * Date 2022/3/9 10:56
     */
    public ResultInfo updateEventSource(EventSource eventSource) {
        eventSource.setUpdate_time(new Date());
        eventSource.setUpdate_user(sysUserService.getUser().getId());
        eventSourceMapper.updateById(eventSource);
        return ResultInfo.success();
    }


    /**
     * 来源删除接口
     * Author wzn
     * Date 2022/3/9 10:56
     */
    public ResultInfo deleteEventSource(Integer id) {
        EventSource eventSource = new EventSource();
        eventSource.setId(id);
        eventSource.setIs_del("1");
        eventSource.setUpdate_time(new Date());
        eventSource.setUpdate_user(sysUserService.getUser().getId());
        eventSourceMapper.updateById(eventSource);
        return ResultInfo.success();
    }

    /**
     * 详情页面接口
     * Author wzn
     * Date 2022/3/24 13:49
     */
    public EventSource info(Integer id) {
        return eventSourceMapper.selectById(id);
    }


    public List<EventSource> upEventSourceList() {
        return eventSourceMapper.selectList(Wrappers.<EventSource>lambdaQuery().eq(EventSource::getIs_del, "0").eq(EventSource::getIs_use, "1"));
    }


}
