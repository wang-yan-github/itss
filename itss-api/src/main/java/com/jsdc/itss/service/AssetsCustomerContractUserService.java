package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsCustomerContractUserDao;
import com.jsdc.itss.mapper.AssetsCustomerContractUserMapper;
import com.jsdc.itss.model.AssetsCustomerContractUser;
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
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsCustomerContractUserService extends BaseService<AssetsCustomerContractUserDao, AssetsCustomerContractUser> {

    @Autowired
    private AssetsCustomerContractUserMapper assetsCustomerContractUserMapper;
    @Autowired
    private AssetsCustomerContractUserDao assetsCustomerContractUserDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsCustomerContractUser> toList(Integer pageIndex, Integer pageSize, AssetsCustomerContractUser beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsCustomerContractUser> assetsCustomerContractUserVos = assetsCustomerContractUserMapper.toList(beanParam);

        PageInfo<AssetsCustomerContractUser> page = new PageInfo<>(assetsCustomerContractUserVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsCustomerContractUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsCustomerContractUser assetsCustomerContractUser = selectOne(queryWrapper);
        return ResultInfo.success(assetsCustomerContractUser);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsCustomerContractUser(AssetsCustomerContractUser bean) {
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
    public ResultInfo editAssetsCustomerContractUser(AssetsCustomerContractUser bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
