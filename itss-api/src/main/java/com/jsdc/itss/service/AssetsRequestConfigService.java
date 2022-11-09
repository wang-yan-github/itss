package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsRequestConfigDao;
import com.jsdc.itss.mapper.AssetsRequestConfigMapper;
import com.jsdc.itss.model.AssetsRequestConfig;
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
public class AssetsRequestConfigService extends BaseService<AssetsRequestConfigDao, AssetsRequestConfig> {

    @Autowired
    private AssetsRequestConfigMapper assetsRequestConfigMapper;
    @Autowired
    private AssetsRequestConfigDao assetsRequestConfigDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsRequestConfig> toList(Integer pageIndex, Integer pageSize, AssetsRequestConfig beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsRequestConfig> assetsRequestConfigVos = assetsRequestConfigMapper.toList(beanParam);

        PageInfo<AssetsRequestConfig> page = new PageInfo<>(assetsRequestConfigVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsRequestConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsRequestConfig assetsRequestConfig = selectOne(queryWrapper);
        return ResultInfo.success(assetsRequestConfig);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsRequestConfig(AssetsRequestConfig bean) {
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
    public ResultInfo editAssetsRequestConfig(AssetsRequestConfig bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public List<AssetsRequestConfig> getAll(AssetsRequestConfig beanParam) {

        return selectList(Wrappers.<AssetsRequestConfig>lambdaQuery().eq(AssetsRequestConfig::getIs_del,"0"));
    }
}
