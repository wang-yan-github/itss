package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsCustomerContractFileDao;
import com.jsdc.itss.mapper.AssetsCustomerContractFileMapper;
import com.jsdc.itss.model.AssetsCustomerContractFile;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Service
@Transactional
public class AssetsCustomerContractFileService extends BaseService<AssetsCustomerContractFileDao, AssetsCustomerContractFile> {

    @Autowired
    private AssetsCustomerContractFileMapper assetsCustomerContractFileMapper;
    @Autowired
    private AssetsCustomerContractFileDao assetsCustomerContractFileDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsCustomerContractFile> toList(Integer pageIndex, Integer pageSize, AssetsCustomerContractFile beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsCustomerContractFile> assetsCustomerContractFileVos = assetsCustomerContractFileMapper.toList(beanParam);

        PageInfo<AssetsCustomerContractFile> page = new PageInfo<>(assetsCustomerContractFileVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsCustomerContractFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsCustomerContractFile assetsCustomerContractFile = selectOne(queryWrapper);
        return ResultInfo.success(assetsCustomerContractFile);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsCustomerContractFile(AssetsCustomerContractFile bean) {
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
    public ResultInfo editAssetsCustomerContractFile(AssetsCustomerContractFile bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
