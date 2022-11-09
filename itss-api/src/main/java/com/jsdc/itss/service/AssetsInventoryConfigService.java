package com.jsdc.itss.service;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsInventoryConfigDao;
import com.jsdc.itss.mapper.AssetsInventoryConfigMapper;
import com.jsdc.itss.mapper.AssetsInventoryMapper;
import com.jsdc.itss.model.AssetsInventory;
import com.jsdc.itss.model.AssetsInventoryConfig;
import com.jsdc.itss.vo.AssetsInventoryVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.catalina.mbeans.MBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsInventoryConfigService extends BaseService<AssetsInventoryConfigDao, AssetsInventoryConfig> {

    @Autowired
    private AssetsInventoryConfigMapper assetsInventoryConfigMapper;
    @Autowired
    private AssetsInventoryConfigDao assetsInventoryConfigDao;

    @Autowired
    private AssetsInventoryMapper assetsInventoryMapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AssetsInventoryDetailsService assetsInventoryDetailsService;

    public PageInfo<AssetsInventoryConfig> toList(Integer pageIndex, Integer pageSize, AssetsInventoryConfig beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsInventoryConfig> assetsInventoryConfigVos = assetsInventoryConfigMapper.toList(beanParam);

        PageInfo<AssetsInventoryConfig> page = new PageInfo<>(assetsInventoryConfigVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsInventoryConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsInventoryConfig assetsInventoryConfig = selectOne(queryWrapper);
        return ResultInfo.success(assetsInventoryConfig);
    }

    /**
     *  添加
     */
    public ResultInfo addAIConfig(AssetsInventoryConfig bean) {
       return assetsInventoryDetailsService.addAssetsInventoryDetails(bean);
    }
    public ResultInfo addAssetsInventoryConfig(AssetsInventoryConfig bean) {
        List<AssetsInventoryConfig> configs = selectList(Wrappers.<AssetsInventoryConfig>lambdaQuery()
                .eq(AssetsInventoryConfig::getInventory_id,bean.getInventory_id())
                .eq(AssetsInventoryConfig::getAssets_children_id,bean.getAssets_children_id())
                .eq(AssetsInventoryConfig::getIs_del, GlobalData.ISDEL_NO)
                .eq(AssetsInventoryConfig::getDept_id,bean.getDept_id()));
        if (!CollectionUtils.isEmpty(configs)){
            return ResultInfo.success();
        }
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
    public ResultInfo editAssetsInventoryConfig(AssetsInventoryConfig bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    /**
     * AssetsInventoryConfig 和  AssetsInventory合并成一个类
     * @param vo
     * @return
     */
    public List<AssetsInventoryVo>  getAssetsInventorys(AssetsInventoryVo vo){
        List<AssetsInventoryConfig> list = assetsInventoryConfigMapper.selectList(Wrappers.<AssetsInventoryConfig>lambdaQuery()
                .eq(AssetsInventoryConfig::getIs_del,"0"));

        List<AssetsInventoryVo> assetsInventoryVos = new ArrayList<>();
        for(AssetsInventoryConfig temp : list){
            AssetsInventoryVo assetsInventoryVo = new AssetsInventoryVo();
            assetsInventoryVo.setId(temp.getId());
            assetsInventoryVo.setInventory_id(temp.getInventory_id());
            assetsInventoryVo.setAssets_type_id(temp.getAssets_type_id());
            assetsInventoryVo.setAssets_children_id(temp.getAssets_children_id());
            assetsInventoryVo.setDept_id(temp.getDept_id());
            assetsInventoryVo.setIs_del(temp.getIs_del());
            assetsInventoryVo.setCreate_time(temp.getCreate_time());
            assetsInventoryVo.setCreate_user(temp.getCreate_user());
            assetsInventoryVo.setUpdate_time(temp.getUpdate_time());
            assetsInventoryVo.setUpdate_user(temp.getUpdate_user());

            AssetsInventory assetsInventory = assetsInventoryMapper.selectById(temp.getInventory_id());
            if(null != assetsInventory){
                assetsInventoryVo.setName(assetsInventory.getName());
                assetsInventoryVo.setStatus(assetsInventory.getStatus());
            }
            assetsInventoryVos.add(assetsInventoryVo);
        }

        return assetsInventoryVos;

    }

    /**
     * AssetsInventoryConfig 和  AssetsInventory合并成一个类
     * @param vo
     * @return
     */
    public AssetsInventoryVo  getAssetsInventory(AssetsInventoryConfig vo){
        List<AssetsInventoryConfig> list = assetsInventoryConfigMapper.selectList(Wrappers.<AssetsInventoryConfig>lambdaQuery()
                .eq(null != vo.getInventory_id(), AssetsInventoryConfig::getInventory_id, vo.getInventory_id())
                .eq(null != vo.getAssets_children_id(), AssetsInventoryConfig::getAssets_children_id, vo.getAssets_children_id())
                .eq(null != vo.getDept_id(), AssetsInventoryConfig::getDept_id, vo.getDept_id())
                .eq(AssetsInventoryConfig::getIs_del,"0")
        );

        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        AssetsInventoryConfig temp = list.get(0);

        AssetsInventoryVo assetsInventoryVo = new AssetsInventoryVo();
        assetsInventoryVo.setId(temp.getId());
        assetsInventoryVo.setInventory_id(temp.getInventory_id());
        assetsInventoryVo.setAssets_type_id(temp.getAssets_type_id());
        assetsInventoryVo.setAssets_children_id(temp.getAssets_children_id());
        assetsInventoryVo.setDept_id(temp.getDept_id());
        assetsInventoryVo.setIs_del(temp.getIs_del());
        assetsInventoryVo.setCreate_time(temp.getCreate_time());
        assetsInventoryVo.setCreate_user(temp.getCreate_user());
        assetsInventoryVo.setUpdate_time(temp.getUpdate_time());
        assetsInventoryVo.setUpdate_user(temp.getUpdate_user());

        AssetsInventory assetsInventory = assetsInventoryMapper.selectById(temp.getInventory_id());
        if(null != assetsInventory){
            assetsInventoryVo.setName(assetsInventory.getName());
            assetsInventoryVo.setStatus(assetsInventory.getStatus());
        }

        return assetsInventoryVo;

    }
}
