package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventCategoryDao;
import com.jsdc.itss.dao.EventSolutionTypeDao;
import com.jsdc.itss.mapper.EventCategoryEngineerMapper;
import com.jsdc.itss.mapper.EventCategoryMapper;
import com.jsdc.itss.mapper.EventSolutionTypeMapper;
import com.jsdc.itss.model.EventCategory;
import com.jsdc.itss.model.EventCategoryEngineer;
import com.jsdc.itss.model.EventSolutionType;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.model.vo.EventCategoryVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventSolutionTypeService extends BaseService<EventSolutionTypeDao, EventSolutionType> {

    @Autowired
    private EventSolutionTypeMapper eventSolutionTypeMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * create by xuaolong
     * description: 公司管理分页查询
     *
     * @param sysCompany
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultInfo getPage(EventSolutionType eventSolutionType, int pageIndex, int pageSize) {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(eventSolutionType.getSolution_name())) {
            queryWrapper.like("solution_name", eventSolutionType.getSolution_name());
        }

        if (StringUtils.isNotEmpty(eventSolutionType.getIs_use())) {
            queryWrapper.eq("is_use", eventSolutionType.getIs_use());
        }
        queryWrapper.eq("is_del", "0");
        queryWrapper.orderByDesc("id");
        PageHelper.startPage(pageIndex, pageSize);
        List<EventSolutionType> list = eventSolutionTypeMapper.selectList(queryWrapper);
        PageInfo<EventSolutionType> pageInfo = new PageInfo<EventSolutionType>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * create by xuaolong
     * description: 公司管理新增公司
     *
     * @param sysCompany
     * @return
     */

    public ResultInfo addSysCompany(EventSolutionType eventSolutionType) {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("solution_name",eventSolutionType.getSolution_name());
        Long count =  eventSolutionTypeMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }

        eventSolutionType.setIs_del("0");
        eventSolutionType.setCreate_time(new Date());
        eventSolutionType.setCreate_user(sysUserService.getUser().getId());
        eventSolutionTypeMapper.insert(eventSolutionType);
        if (Base.notEmpty(eventSolutionType.getIs_default()) && eventSolutionType.getIs_default().equals("1")) {
            //是否默认0否 1是
            LambdaUpdateWrapper<EventSolutionType> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventSolutionType::getIs_default, 0)
                    .ne(EventSolutionType::getId, eventSolutionType.getId());
            update(null, wrapper);
        }
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description: 公司管理修改公司信息
     *
     * @param sysCompany
     * @return
     */
    public ResultInfo updateSysCompany(EventSolutionType eventSolutionType) {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("solution_name",eventSolutionType.getSolution_name()).ne("id",eventSolutionType.getId());
        Long count =  eventSolutionTypeMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        eventSolutionType.setUpdate_time(new Date());
        eventSolutionType.setUpdate_user(sysUserService.getUser().getId());
        eventSolutionTypeMapper.updateById(eventSolutionType);

        if (Base.notEmpty(eventSolutionType.getIs_default()) && eventSolutionType.getIs_default().equals("1")) {
            //是否默认0否 1是
            LambdaUpdateWrapper<EventSolutionType> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(EventSolutionType::getIs_default, 0)
                    .ne(EventSolutionType::getId, eventSolutionType.getId());
            update(null, wrapper);
        }

        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description: 公司管理删除公司信息
     *
     * @param id
     * @return
     */
    public ResultInfo deleteSysCompany(Integer id) {
        EventSolutionType eventSolutionType = new EventSolutionType();
        eventSolutionType.setId(id);
        eventSolutionType.setIs_del("1");
        eventSolutionType.setUpdate_time(new Date());
        eventSolutionType.setUpdate_user(sysUserService.getUser().getId());
        eventSolutionTypeMapper.updateById(eventSolutionType);
        return ResultInfo.success();
    }


    public List<EventSolutionType> upEventSolutionType() {
        QueryWrapper<EventSolutionType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        return eventSolutionTypeMapper.selectList(queryWrapper);
    }

}
