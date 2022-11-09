package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsSupplierContractFileDao;
import com.jsdc.itss.mapper.AssetsSupplierContractFileMapper;
import com.jsdc.itss.model.AssetsSupplierContractFile;
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
public class AssetsSupplierContractFileService extends BaseService<AssetsSupplierContractFileDao, AssetsSupplierContractFile> {

    @Autowired
    private AssetsSupplierContractFileMapper assetsSupplierContractFileMapper;
    @Autowired
    private AssetsSupplierContractFileDao assetsSupplierContractFileDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsSupplierContractFile> toList(Integer pageIndex, Integer pageSize, AssetsSupplierContractFile beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsSupplierContractFile> assetsSupplierContractFileVos = assetsSupplierContractFileMapper.toList(beanParam);

        PageInfo<AssetsSupplierContractFile> page = new PageInfo<>(assetsSupplierContractFileVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsSupplierContractFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsSupplierContractFile assetsSupplierContractFile = selectOne(queryWrapper);
        return ResultInfo.success(assetsSupplierContractFile);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsSupplierContractFile(AssetsSupplierContractFile bean) {
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
    public ResultInfo editAssetsSupplierContractFile(AssetsSupplierContractFile bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
