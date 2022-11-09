package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigReqDao;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.mapper.AssetsConfigReqMapper;
import com.jsdc.itss.mapper.AssetsManageMapper;
import com.jsdc.itss.mapper.EventMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.utils.DataUtils;
import com.jsdc.itss.vo.AssetsConfigReqVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置请求关联事件工单中间表
 *
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsConfigReqService extends BaseService<AssetsConfigReqDao, AssetsConfigReq> {

    @Autowired
    private AssetsConfigReqMapper assetsConfigReqMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AssetsConfigReqOrderService assetsConfigReqOrderService;
    @Autowired
    private AssetsConfigReqPropertyService assetsConfigReqPropertyService;

    @Autowired
    private AssetsRequestConfigService assetsRequestConfigService;

    @Autowired
    private EventService eventService;
    @Autowired
    private AssetsManageService assetsManageService;
    @Autowired
    private ChangeInfoService changeInfoService;

    public PageInfo<AssetsConfigReqVo> toList(Integer pageIndex, Integer pageSize, AssetsConfigReqVo beanParam) {
        // 0 配置请求，1.我的配置请求,2.配置请求待办
        if ("1".equals(beanParam.getData_flag())) {
            beanParam.setCreate_user(null == beanParam.getUserId() ? sysUserService.getUser().getId() : beanParam.getUserId());
        } else if ("2".equals(beanParam.getData_flag())) {
            beanParam.setDeal_userId(null == beanParam.getUserId() ? sysUserService.getUser().getId() : beanParam.getUserId());
            beanParam.setStatus(1);
        }

        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<AssetsConfigReqVo> assetsConfigReqVos = assetsConfigReqMapper.toList(beanParam);

        PageInfo<AssetsConfigReqVo> page = new PageInfo<>(assetsConfigReqVos);

        return page;
    }

    /**
     * 详情
     */
    public ResultInfo getAssetsConfigReq(AssetsConfigReqVo beanParam) {
        AssetsConfigReqVo getAssetsConfigReq = assetsConfigReqMapper.getAssetsConfigReq(beanParam);
        //关联的变更单编号
        if (Base.notEmpty(getAssetsConfigReq.getRelevance_NO())) {
            ChangeInfo changeInfo = changeInfoService.selectById(getAssetsConfigReq.getRelevance_NO());
            getAssetsConfigReq.setRelevance_name(changeInfo.getChange_no());
        }

        //关联的配置
        getAssetsConfigReq.setAssetsConfigReqProperties(assetsConfigReqPropertyService.selectList(Wrappers.<AssetsConfigReqProperty>lambdaQuery().
                eq(AssetsConfigReqProperty::getReq_NO, getAssetsConfigReq.getReq_NO()).eq(AssetsConfigReqProperty::getIs_del, "0")));
        //关联的配置
        if (!CollectionUtils.isEmpty(getAssetsConfigReq.getAssetsConfigReqProperties())) {
            List<Integer> ids = getAssetsConfigReq.getAssetsConfigReqProperties().stream().map(AssetsConfigReqProperty::getProperty_id).collect(Collectors.toList());
            AssetsManage assetsManage2 = new AssetsManage();
            assetsManage2.setIds(ids);
            getAssetsConfigReq.setAssetsManages(assetsManageService.getList(assetsManage2));
        }

        //关联的事件工单
        getAssetsConfigReq.setAssetsConfigReqOrders(assetsConfigReqOrderService.selectList(Wrappers.<AssetsConfigReqOrder>lambdaQuery().
                eq(AssetsConfigReqOrder::getReq_NO, getAssetsConfigReq.getReq_NO()).eq(AssetsConfigReqOrder::getIs_del, "0")));
        //关联的事件工单
        if (!CollectionUtils.isEmpty(getAssetsConfigReq.getAssetsConfigReqOrders())) {
            List<Integer> ids = getAssetsConfigReq.getAssetsConfigReqOrders().stream().map(AssetsConfigReqOrder::getOrder_id).collect(Collectors.toList());
            EventVo eventVo = new EventVo();
            eventVo.setIds(ids);
            getAssetsConfigReq.setEvents(eventService.getList(eventVo));
        }

        return ResultInfo.success(getAssetsConfigReq);
    }

    /**
     * 添加
     */
    public ResultInfo addAssetsConfigReq(AssetsConfigReqVo assetsConfigReqVo) {

        AssetsConfigReq assetsConfigReq = new AssetsConfigReq();
        BeanUtils.copyProperties(assetsConfigReqVo, assetsConfigReq);

        AssetsRequestConfig assetsRequestConfig = assetsRequestConfigService.selectOne(Wrappers.<AssetsRequestConfig>lambdaQuery()
                .eq(AssetsRequestConfig::getId, assetsConfigReqVo.getType()));

        // 处理人
        assetsConfigReq.setDeal_userId(assetsRequestConfig.getHandler());
        // 请求编号
        assetsConfigReq.setReq_NO(DataUtils.getNo(DataType.ASSETS_RQ_TYPE));
        // 删除状态
        assetsConfigReq.setIs_del(String.valueOf(0));
        // 创建时间
        assetsConfigReq.setCreate_time(new Date());
        // 创建者
        assetsConfigReq.setCreate_user(sysUserService.getUser().getId());
        insert(assetsConfigReq);

        //关联的配置
        if (!CollectionUtils.isEmpty(assetsConfigReqVo.getAssetsManages())) {
            assetsConfigReqVo.getAssetsManages().forEach(x -> {
                AssetsConfigReqProperty assetsConfigReqProperty = new AssetsConfigReqProperty();
                assetsConfigReqProperty.setReq_NO(assetsConfigReq.getReq_NO());
                assetsConfigReqProperty.setProperty_id(x.getId());
                assetsConfigReqPropertyService.addAssetsConfigReqProperty(assetsConfigReqProperty);
            });
        }
        //关联的事件
        if (!CollectionUtils.isEmpty(assetsConfigReqVo.getEvents())) {
            assetsConfigReqVo.getEvents().forEach(x -> {
                AssetsConfigReqOrder assetsConfigReqOrder = new AssetsConfigReqOrder();
                assetsConfigReqOrder.setReq_NO(assetsConfigReq.getReq_NO());
                assetsConfigReqOrder.setOrder_id(x.getId());
                assetsConfigReqOrderService.add(assetsConfigReqOrder);
            });
        }

        return ResultInfo.success();
    }

    /**
     * 编辑
     */
    public ResultInfo editAssetsConfigReq(AssetsConfigReqVo assetsConfigReqVo) {


        AssetsConfigReq assetsConfigReq = new AssetsConfigReq();
        BeanUtils.copyProperties(assetsConfigReqVo, assetsConfigReq);

        AssetsRequestConfig assetsRequestConfig = assetsRequestConfigService.selectOne(Wrappers.<AssetsRequestConfig>lambdaQuery().eq(AssetsRequestConfig::getId, assetsConfigReqVo.getType()));

        // 处理人
        assetsConfigReq.setDeal_userId(assetsRequestConfig.getHandler() == null ? null : assetsRequestConfig.getHandler());

        // 修改者
        assetsConfigReq.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsConfigReq.setUpdate_time(new Date());
        //状态 1待处理 2已完成 3已撤销 4拒绝
        assetsConfigReq.setStatus(1);
        updateById(assetsConfigReq);

        // 关联的事件
        assetsConfigReqOrderService.update(null, Wrappers.<AssetsConfigReqOrder>lambdaUpdate()
                .set(AssetsConfigReqOrder::getIs_del, String.valueOf(1)).eq(AssetsConfigReqOrder::getReq_NO, assetsConfigReq.getReq_NO()));
        // 变更事件关联
        if (!CollectionUtils.isEmpty(assetsConfigReqVo.getEvents())) {
            assetsConfigReqVo.getEvents().forEach(x -> {
                AssetsConfigReqOrder assetsConfigReqOrder = new AssetsConfigReqOrder();
                assetsConfigReqOrder.setReq_NO(assetsConfigReq.getReq_NO());
                assetsConfigReqOrder.setOrder_id(x.getId());
                assetsConfigReqOrderService.add(assetsConfigReqOrder);
            });
        }

        //关联的配置
        assetsConfigReqPropertyService.update(null, Wrappers.<AssetsConfigReqProperty>lambdaUpdate()
                .set(AssetsConfigReqProperty::getIs_del, String.valueOf(1)).eq(AssetsConfigReqProperty::getReq_NO, assetsConfigReq.getReq_NO()));

        //关联的配置
        if (!CollectionUtils.isEmpty(assetsConfigReqVo.getAssetsManages())) {
            assetsConfigReqVo.getAssetsManages().forEach(x -> {
                AssetsConfigReqProperty assetsConfigReqProperty = new AssetsConfigReqProperty();
                assetsConfigReqProperty.setReq_NO(assetsConfigReq.getReq_NO());
                assetsConfigReqProperty.setProperty_id(x.getId());
                assetsConfigReqPropertyService.addAssetsConfigReqProperty(assetsConfigReqProperty);
            });
        }

        return ResultInfo.success();
    }


    public ResultInfo toCancel(AssetsConfigReqVo beanParam) {
        AssetsConfigReq assetsConfigReq = new AssetsConfigReq();
        BeanUtils.copyProperties(beanParam, assetsConfigReq);


        // 修改者
        assetsConfigReq.setUpdate_user(null == beanParam.getUserId() ? sysUserService.getUser().getId() : beanParam.getUserId());
        // 修改时间
        assetsConfigReq.setUpdate_time(new Date());
        updateById(assetsConfigReq);

        return ResultInfo.success();
    }

    public ResultInfo toResult(AssetsConfigReqVo beanParam) {
        AssetsConfigReq assetsConfigReq = new AssetsConfigReq();
        BeanUtils.copyProperties(beanParam, assetsConfigReq);

        // 修改者
        assetsConfigReq.setUpdate_user(null == beanParam.getUserId() ? sysUserService.getUser().getId() : beanParam.getUserId());
        // 修改时间
        assetsConfigReq.setUpdate_time(new Date());
        assetsConfigReq.setDeal_time(new Date());
        updateById(assetsConfigReq);

        return ResultInfo.success();
    }

    public ResultInfo toDel(Integer id){
        AssetsConfigReq assetsConfigReq = assetsConfigReqMapper.selectById(id);
        assetsConfigReq.setIs_del("1");
        assetsConfigReqMapper.updateById(assetsConfigReq);
        return ResultInfo.success();
    }
}
