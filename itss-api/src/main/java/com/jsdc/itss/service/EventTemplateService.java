package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventTemplateDao;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventTemplateService extends BaseService<EventTemplateDao, EventTemplate> {
    @Autowired
    private EventEemplateMapper eventEemplateMapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private EventServiceManageMapper eventServiceManageMapper;

    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private SchemeClassificationService schemeClassificationService;

    @Autowired
    private EventSourceMapper eventSourceMapper;

    @Autowired
    private EventRequestTypeMapper eventRequestTypeMapper;

    @Autowired
    private SlaEventImpactMapper slaEventImpactMapper;

    @Autowired
    private SlaEventUrgentMapper slaEventUrgentMapper;

    @Autowired
    private EventSolutionTypeService eventSolutionTypeService;

    /**
     * 分页
     * 工单模板列表接口
     * 查看公共模板 + 创建人是自己的个人模板
     * Author wzn
     * Date 2022/3/10 13:50
     */
    public ResultInfo getPage(EventTemplate eventTemplate, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<EventTemplate> list = eventEemplateMapper.selectList(Wrappers.<EventTemplate>lambdaQuery()
                .like(StringUtils.isNotBlank(eventTemplate.getTemplate_name()), EventTemplate::getTemplate_name, eventTemplate.getTemplate_name())
                .eq(eventTemplate.getCreate_user() != null, EventTemplate::getCreate_user, eventTemplate.getCreate_user())
                .eq(StringUtils.isNotBlank(eventTemplate.getIs_use()), EventTemplate::getIs_use, eventTemplate.getIs_use())
                .eq(StringUtils.isNotBlank(eventTemplate.getIs_public()), EventTemplate::getIs_public, eventTemplate.getIs_public())
                .eq(EventTemplate::getIs_del, "0")
                // 根据用户或者是否为公共模版
                .and(wrapper -> wrapper.eq(EventTemplate::getCreate_user, sysUserService.getUser().getId())
                        .or()
                        .eq(EventTemplate::getIs_public, "1")
                )
        );
        if (null != list) {
            for (EventTemplate e : list) {
                SysUser sysUser = sysUserService.selectById(e.getCreate_user());
                if (null != sysUser) {
                    e.setCreate_user_name(sysUser.getName());
                }
                SysUser sysUser2 = sysUserService.selectById(e.getUpdate_user());
                if (null != sysUser2) {
                    e.setUpdate_user_name(sysUser2.getName());
                }
                EventServiceManage eventServiceManage = eventServiceManageMapper.selectById(e.getEvent_service_id());
                if (null != eventServiceManage) {
                    e.setEvent_service_name(eventServiceManage.getName());
                }

            }
        }
        PageInfo<EventTemplate> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 个人模版列表接口
     *
     * @param eventTemplate
     * @param pageNo
     * @param pageSize
     * @return
     */
    public ResultInfo getMyPage(EventTemplate eventTemplate, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize, "create_time desc");

        List<EventTemplate> list = eventEemplateMapper.selectList(Wrappers.<EventTemplate>lambdaQuery()
                .like(StringUtils.isNotBlank(eventTemplate.getTemplate_name()), EventTemplate::getTemplate_name, eventTemplate.getTemplate_name())
                .eq(eventTemplate.getCreate_user() != null, EventTemplate::getCreate_user, eventTemplate.getCreate_user())
                .eq(StringUtils.isNotBlank(eventTemplate.getIs_use()), EventTemplate::getIs_use, eventTemplate.getIs_use())
                .eq(StringUtils.isNotBlank(eventTemplate.getIs_public()), EventTemplate::getIs_public, eventTemplate.getIs_public())
                .eq(EventTemplate::getIs_del, "0")
                // 根据用户或者是否为公共模版
                .and(wrapper -> wrapper.eq(EventTemplate::getCreate_user, sysUserService.getUser().getId())
                        .or()
                        .in(EventTemplate::getIs_public, "1")
                )

        );
        if (null != list) {
            for (EventTemplate e : list) {
                SysUser sysUser = sysUserService.selectById(e.getCreate_user());
                if (null != sysUser) {
                    e.setCreate_user_name(sysUser.getName());
                }
                SysUser sysUser2 = sysUserService.selectById(e.getUpdate_user());
                if (null != sysUser2) {
                    e.setUpdate_user_name(sysUser2.getName());
                }
                EventServiceManage eventServiceManage = eventServiceManageMapper.selectById(e.getEvent_service_id());
                if (null != eventServiceManage) {
                    e.setEvent_service_name(eventServiceManage.getName());
                }

            }
        }
        PageInfo<EventTemplate> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 列表
     * 查看公共模板 + 创建人是自己的个人模板
     */
    public List<EventTemplate> getList(EventTemplate eventTemplate) {
        List<EventTemplate> list = eventEemplateMapper.selectList(Wrappers.<EventTemplate>lambdaQuery()
                .eq(StringUtils.isNotBlank(eventTemplate.getTemplate_name()), EventTemplate::getTemplate_name, eventTemplate.getTemplate_name())
//                .eq(eventTemplate.getCreate_user() != null, EventTemplate::getCreate_user, eventTemplate.getCreate_user())
                .eq(StringUtils.isNotBlank(eventTemplate.getIs_use()), EventTemplate::getIs_use, eventTemplate.getIs_use())
                .eq(StringUtils.isNotBlank(eventTemplate.getIs_public()), EventTemplate::getIs_public, eventTemplate.getIs_public())
                .eq(EventTemplate::getIs_del, "0")
                .in(Base.notEmpty(eventTemplate.getIds()) && eventTemplate.getIds().size() > 0, EventTemplate::getId, eventTemplate.getIds())
                // 根据用户或者是否为公共模版
                .and(wrapper -> wrapper.eq(EventTemplate::getCreate_user, sysUserService.getUser().getId())
                        .or()
                        .eq(EventTemplate::getIs_public, "1")
                )
                .orderByDesc(EventTemplate::getCreate_time)
        );
        if (null != list) {
            for (EventTemplate e : list) {
                SysUser sysUser = sysUserService.selectById(e.getCreate_user());
                if (null != sysUser) {
                    e.setCreate_user_name(sysUser.getName());
                }
                SysUser sysUser2 = sysUserService.selectById(e.getUpdate_user());
                if (null != sysUser2) {
                    e.setUpdate_user_name(sysUser2.getName());
                }
                EventServiceManage eventServiceManage = eventServiceManageMapper.selectById(e.getEvent_service_id());
                if (null != eventServiceManage) {
                    e.setEvent_service_name(eventServiceManage.getName());
                }

            }
        }
        return list;
    }


    /**
     * 新增工单模板
     * Author wzn
     * Date 2022/3/10 13:59
     */
    public ResultInfo addEventEemplate(EventTemplate eventTemplate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String newsNo = df.format(new Date());
        int num = (int) ((Math.random() * 9 + 1) * 1000);
        eventTemplate.setTemplateNumber("TICTEMP" + newsNo + num);
        eventTemplate.setIs_del("0");
        eventTemplate.setCreate_time(new Date());
        eventTemplate.setCreate_user(sysUserService.getUser().getId());
        eventEemplateMapper.insert(eventTemplate);
        return ResultInfo.success();
    }


    /**
     * 修改工单模板接口
     * Author wzn
     * Date 2022/3/10 14:01
     */
    public ResultInfo updateEventEemplate(EventTemplate eventTemplate) {
        eventTemplate.setUpdate_time(new Date());
        eventTemplate.setUpdate_user(sysUserService.getUser().getId());
        eventEemplateMapper.updateById(eventTemplate);
        return ResultInfo.success();
    }


    /**
     * 详情查看
     * @param id
     * @return
     */
    public EventTemplate eventTemplateInfo(Integer id) {
        EventTemplate eventTemplate = eventEemplateMapper.selectById(id);
        if (null != eventTemplate) {
            //工单类别
            EventCategory eventCategory = eventCategoryService.selectById(eventTemplate.getBill_type());
            if (null != eventCategory) {
                eventTemplate.setBill_type_name(eventCategory.getName());
            }
            SchemeClassification schemeClassification = schemeClassificationService.selectById(eventTemplate.getProgramme_type());
            if (null != schemeClassification) {
                eventTemplate.setProgramme_type_name(schemeClassification.getName());
            }
            //服务群组
            EventServiceManage eventServiceManage = eventServiceManageMapper.selectById(eventTemplate.getEvent_service_id());
            if (null != eventServiceManage) {
                eventTemplate.setEvent_service_name(eventServiceManage.getName());
            }
            //创建人
            SysUser sysUser = sysUserService.selectById(eventTemplate.getCreate_user());
            if (null != sysUser) {
                eventTemplate.setCreate_user_name(sysUser.getName());
            }
            //修改人
            SysUser sysUser2 = sysUserService.selectById(eventTemplate.getUpdate_user());
            if (null != sysUser2) {
                eventTemplate.setUpdate_user_name(sysUser2.getName());
            }
            //用户
            SysUser sysUser3 = sysUserService.selectById(eventTemplate.getUser_id());
            if (null != sysUser3) {
                eventTemplate.setUser_name(sysUser3.getName());
            }
            //来源
            EventSource eventSource = eventSourceMapper.selectById(eventTemplate.getSource());
            if (null != eventSource) {
                eventTemplate.setSource_name(eventSource.getResource_name());
            }
            //请求类别
            EventRequestType eventRequestType = eventRequestTypeMapper.selectById(eventTemplate.getRequest_type());
            if (null != eventRequestType) {
                eventTemplate.setRequest_type_name(eventRequestType.getRequest_type_name());
            }
            // 处理人
            SysUser sysUser4 = sysUserService.selectById(eventTemplate.getHandler());
            if (null != sysUser4) {
                eventTemplate.setHandler_name(sysUser4.getName());
            }
            // 紧急度
            SlaEventUrgent urgent = slaEventUrgentMapper.selectById(eventTemplate.getUrgency_degree());
            if (null != urgent) {
                eventTemplate.setUrgency_degree_name(urgent.getUrgent_name());
            }
            // 影响度
            SlaEventImpact impact = slaEventImpactMapper.selectById(eventTemplate.getEffect_degree());
            if (null != impact) {
                eventTemplate.setEffect_degree_name(impact.getImpact_name());
            }
            // 解决方式
            EventSolutionType solve = eventSolutionTypeService.selectById(eventTemplate.getSolve_type());
            if (null != solve) {
                eventTemplate.setSolve_type_name(solve.getSolution_name());
            }

        }
        return eventTemplate;
    }


    public void del(Integer id) {
        EventTemplate eventTemplate = new EventTemplate();
        eventTemplate.setId(id);
        eventTemplate.setIs_del("1");
        eventEemplateMapper.updateById(eventTemplate);
    }
}
