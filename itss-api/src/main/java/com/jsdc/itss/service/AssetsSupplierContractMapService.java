package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsSupplierContractMapDao;
import com.jsdc.itss.mapper.AssetsSupplierContractMapMapper;
import com.jsdc.itss.model.AssetsSupplierContractMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.Date;
import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Service
@Transactional
public class AssetsSupplierContractMapService extends BaseService<AssetsSupplierContractMapDao, AssetsSupplierContractMap> {

    @Autowired
    private AssetsSupplierContractMapMapper assetsSupplierContractMapMapper;
    @Autowired
    private AssetsSupplierContractMapDao assetsSupplierContractMapDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsSupplierContractMap> toList(Integer pageIndex, Integer pageSize, AssetsSupplierContractMap beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsSupplierContractMap> assetsSupplierContractMapVos = assetsSupplierContractMapMapper.toList(beanParam);

        PageInfo<AssetsSupplierContractMap> page = new PageInfo<>(assetsSupplierContractMapVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsSupplierContractMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsSupplierContractMap assetsSupplierContractMap = selectOne(queryWrapper);
        return ResultInfo.success(assetsSupplierContractMap);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsSupplierContractMap(AssetsSupplierContractMap bean) {
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
    public ResultInfo editAssetsSupplierContractMap(AssetsSupplierContractMap bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
