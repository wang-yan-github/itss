package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventCategoryDao;
import com.jsdc.itss.mapper.EventCategoryEngineerMapper;
import com.jsdc.itss.mapper.EventCategoryMapper;
import com.jsdc.itss.mapper.SysUserMapper;
import com.jsdc.itss.model.AssetsCustomerContract;
import com.jsdc.itss.model.EventCategory;
import com.jsdc.itss.model.EventCategoryEngineer;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.model.vo.EventCategoryVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventCategoryService extends BaseService<EventCategoryDao, EventCategory> {
    @Autowired
    private EventCategoryMapper eventCategoryMapper;

    @Autowired
    private EventCategoryEngineerMapper eventCategoryEngineerMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private AssetsCustomerContractService assetsCustomerContractService;

    /**
     * Author wzn
     * Date 2022/3/7 16:45
     */
    public void add(EventCategoryVo eventCategoryVo) {
        EventCategory eventCategory = eventCategoryVo.getEventCategory();
        //服务群组主表新增
        // 删除状态
        eventCategoryVo.getEventCategory().setIs_del(String.valueOf(0));
        // 创建时间
        eventCategoryVo.getEventCategory().setCreate_time(new Date());
        // 创建者
        eventCategoryVo.getEventCategory().setCreate_user(sysUserService.getUser().getId());
        if (null == eventCategory.getParent_id()) {
            eventCategory.setParent_id(0);
        }
        eventCategoryMapper.insert(eventCategory);

        if (!CollectionUtils.isEmpty(eventCategoryVo.getType1())) {
            for (SysUser sysUser : eventCategoryVo.getType1()) {
                EventCategoryEngineer e = new EventCategoryEngineer();
                e.setEvent_id(eventCategoryVo.getEventCategory().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                e.setCreate_time(new Date());
                e.setType(1);
                e.setCreate_user(sysUserService.getUser().getId());
                eventCategoryEngineerMapper.insert(e);
            }
        }
        if (!CollectionUtils.isEmpty(eventCategoryVo.getType2())) {
            for (SysUser sysUser : eventCategoryVo.getType2()) {
                EventCategoryEngineer e = new EventCategoryEngineer();
                e.setEvent_id(eventCategoryVo.getEventCategory().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                e.setCreate_time(new Date());
                e.setType(2);
                e.setCreate_user(sysUserService.getUser().getId());
                eventCategoryEngineerMapper.insert(e);
            }
        }

    }

    public PageInfo<EventCategory> toList(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<EventCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        List<EventCategory> eventServiceManageList = selectList(queryWrapper);
        PageInfo<EventCategory> page = new PageInfo<>(eventServiceManageList);
        return page;
    }


    /**
     * 删除
     *
     * @param assetsBrand
     * @return
     */
    public ResultInfo del(int id) {
        EventCategory eventCategory = new EventCategory();
        eventCategory.setId(id);
        // 修改者
        eventCategory.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        eventCategory.setUpdate_time(new Date());
        eventCategory.setIs_del("1");
        updateById(eventCategory);
        return ResultInfo.success();
    }

    public void edit(EventCategoryVo eventCategoryVo) {
        eventCategoryMapper.updateById(eventCategoryVo.getEventCategory());
        //子表先删再增
        QueryWrapper<EventCategoryEngineer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", eventCategoryVo.getEventCategory().getId());
        eventCategoryEngineerMapper.delete(queryWrapper);

        if (!CollectionUtils.isEmpty(eventCategoryVo.getType1())) {
            for (SysUser sysUser : eventCategoryVo.getType1()) {
                EventCategoryEngineer e = new EventCategoryEngineer();
                e.setEvent_id(eventCategoryVo.getEventCategory().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                e.setCreate_time(new Date());
                e.setType(1);
                e.setCreate_user(sysUserService.getUser().getId());
                eventCategoryEngineerMapper.insert(e);
            }
        }
        if (!CollectionUtils.isEmpty(eventCategoryVo.getType2())) {
            for (SysUser sysUser : eventCategoryVo.getType2()) {
                EventCategoryEngineer e = new EventCategoryEngineer();
                e.setEvent_id(eventCategoryVo.getEventCategory().getId());
                e.setEngineer_userid(sysUser.getId());
                e.setIs_del(String.valueOf(0));
                e.setCreate_time(new Date());
                e.setType(2);
                e.setCreate_user(sysUserService.getUser().getId());
                eventCategoryEngineerMapper.insert(e);
            }
        }

    }

    public EventCategoryVo info(Integer id) {
        EventCategoryVo eventCategoryVo = new EventCategoryVo();
        EventCategory eventCategory = eventCategoryMapper.selectById(id);
        if (null != eventCategory) {

            if (Base.notEmpty(eventCategory.getCustomer_contract_id())) {
                AssetsCustomerContract assetsCustomerContract = assetsCustomerContractService.selectById(eventCategory.getCustomer_contract_id());
                if (Base.notEmpty(assetsCustomerContract)) {
                    //客户合同名称
                    eventCategory.setContractName(assetsCustomerContract.getContract_name());
                }
            }

            EventCategory eventCategory2 = eventCategoryMapper.selectById(eventCategory.getParent_id());
            if (null != eventCategory2) {
                eventCategory.setParent_id_name(eventCategory2.getName());
            }
        }
        eventCategoryVo.setEventCategory(eventCategory);
        QueryWrapper<EventCategoryEngineer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", id);
        queryWrapper.eq("type", 1);
        List<EventCategoryEngineer> eventCategoryEngineers = eventCategoryEngineerMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(eventCategoryEngineers)) {
            List<Integer> ids = eventCategoryEngineers.stream().map(EventCategoryEngineer::getEngineer_userid).collect(Collectors.toList());
            SysUser sysUser = new SysUser();
            sysUser.setIds(ids);
            eventCategoryVo.setSysUserList1(sysUserService.getList(sysUser));
        }
        QueryWrapper<EventCategoryEngineer> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("event_id", id);
        queryWrapper2.eq("type", 2);
        List<EventCategoryEngineer> eventCategoryEngineers2 = eventCategoryEngineerMapper.selectList(queryWrapper2);
        if (!CollectionUtils.isEmpty(eventCategoryEngineers2)) {
            List<Integer> ids = eventCategoryEngineers2.stream().map(EventCategoryEngineer::getEngineer_userid).collect(Collectors.toList());
            SysUser sysUser = new SysUser();
            sysUser.setIds(ids);
            eventCategoryVo.setSysUserList2(sysUserService.getList(sysUser));
        }
        return eventCategoryVo;
    }


    /**
     * 工单类别列表递归接口
     * Author wzn
     * Date 2022/3/23 16:18
     */
    public List<EventCategory> tree(EventCategory eventCategory) {
        QueryWrapper<EventCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", "0");
        if (null != eventCategory){
            if (null != eventCategory.getId()){
                wrapper.ne("id",eventCategory.getId());
            }
        }

        List<EventCategory> fileCatalogs = eventCategoryMapper.selectList(wrapper);
        List<EventCategory> lists = new ArrayList<>();
        for (EventCategory bean : fileCatalogs) {
            //客户合同名称
            if (Base.notEmpty(bean.getCustomer_contract_id())) {
                bean.setContractName(assetsCustomerContractService.selectById(bean.getCustomer_contract_id()).getContract_name());
            }
            //负责人
            QueryWrapper<EventCategoryEngineer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("event_id", bean.getId());
            queryWrapper.eq("type", 1);
            List<EventCategoryEngineer> eventCategoryEngineers = eventCategoryEngineerMapper.selectList(queryWrapper);
            StringBuilder builder = new StringBuilder();
            if (Base.notEmpty(eventCategoryEngineers)) {
                for (EventCategoryEngineer e : eventCategoryEngineers) {
                    SysUser sysUser = sysUserMapper.selectById(e.getEngineer_userid());
                    builder.append(sysUser.getName()).append(",");
                }
            }
            if (builder.length() > 0) {
                bean.setIs_send_charge_name(builder.deleteCharAt(builder.length() - 1).toString());
            }

            EventCategory vo = new EventCategory();
            BeanUtils.copyProperties(bean, vo);
            lists.add(vo);
        }
        List<EventCategory> tree = createTree(lists, 0);
        return tree;
    }

    /**
     * 递归建立树形结构 （算法）
     *
     * @param lists
     * @param pid
     * @return
     */
    private List<EventCategory> createTree(List<EventCategory> lists, int pid) {
        List<EventCategory> tree = new ArrayList<>();
        for (EventCategory catelog : lists) {
            if (null != catelog.getParent_id() && catelog.getParent_id() == pid) {
                catelog.setChildren(createTree(lists, catelog.getId()));
                tree.add(catelog);
            }
        }
        return tree;
    }

}
