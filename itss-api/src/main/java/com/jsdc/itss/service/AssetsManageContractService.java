package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsManageContractDao;
import com.jsdc.itss.mapper.AssetsManageContractMapper;
import com.jsdc.itss.model.AssetsManageContract;
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
public class AssetsManageContractService extends BaseService<AssetsManageContractDao, AssetsManageContract> {

    @Autowired
    private AssetsManageContractMapper assetsManageContractMapper;
    @Autowired
    private AssetsManageContractDao assetsManageContractDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsManageContract> toList(Integer pageIndex, Integer pageSize, AssetsManageContract beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsManageContract> assetsManageContractVos = assetsManageContractMapper.toList(beanParam);

        PageInfo<AssetsManageContract> page = new PageInfo<>(assetsManageContractVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsManageContract> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsManageContract assetsManageContract = selectOne(queryWrapper);
        return ResultInfo.success(assetsManageContract);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsManageContract(AssetsManageContract bean) {
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
    public ResultInfo editAssetsManageContract(AssetsManageContract bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
