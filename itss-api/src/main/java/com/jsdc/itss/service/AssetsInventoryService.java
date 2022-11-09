package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsInventoryDao;
import com.jsdc.itss.mapper.AssetsInventoryMapper;
import com.jsdc.itss.model.AssetsInventory;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsInventoryService extends BaseService<AssetsInventoryDao, AssetsInventory> {

    @Autowired
    private AssetsInventoryMapper assetsInventoryMapper;
    @Autowired
    private AssetsInventoryDao assetsInventoryDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsInventory> toList(Integer pageIndex, Integer pageSize, AssetsInventory beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<AssetsInventory> assetsInventoryVos = assetsInventoryMapper.toList(beanParam);

        PageInfo<AssetsInventory> page = new PageInfo<>(assetsInventoryVos);

        return page;
    }

    public ResultInfo getById(Integer id) {
        AssetsInventory assetsInventory = selectOne(Wrappers.<AssetsInventory>lambdaQuery().
                eq(AssetsInventory::getIs_del, 0).eq(AssetsInventory::getId, id));
        return ResultInfo.success(assetsInventory);
    }


    /**
     * 添加
     */
    public ResultInfo addAssetsInventory(AssetsInventory bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     * 删除
     * @param bean
     * @return
     */
    public ResultInfo delAssetsInventory(AssetsInventory bean){
        AssetsInventory assetsInventory = selectOne(Wrappers.<AssetsInventory>lambdaQuery().eq(AssetsInventory::getIs_del, 0).eq(AssetsInventory::getId, bean.getId()));
        // 修改者
        assetsInventory.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsInventory.setUpdate_time(new Date());
        assetsInventory.setIs_del("1");
        updateById(assetsInventory);
        return ResultInfo.success();
    }

    /**
     * 编辑
     */
    public ResultInfo editAssetsInventory(AssetsInventory bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public ResultInfo getAllPanDianQi() {
        QueryWrapper<AssetsInventory> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<AssetsInventory> assetsInventorys = selectList(queryWrapper);
        return ResultInfo.success(assetsInventorys);
    }

    public void delBatch(String ids) {
        String[] strArr = ids.split(",");
        for (int i = 0; i < strArr.length; i++) {
            AssetsInventory assetsInventory = selectById(NumberUtils.toInt(strArr[i]));
            assetsInventory.setUpdate_user(sysUserService.getUser().getId());
            // 修改时间
            assetsInventory.setUpdate_time(new Date());
            assetsInventory.setIs_del("1");
            updateById(assetsInventory);
        }
    }
}
