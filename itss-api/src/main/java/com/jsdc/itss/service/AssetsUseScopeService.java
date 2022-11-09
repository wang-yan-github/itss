package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsUseScopeDao;
import com.jsdc.itss.mapper.AssetsUseScopeMapper;
import com.jsdc.itss.model.AssetsUseScope;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class AssetsUseScopeService extends BaseService<AssetsUseScopeDao, AssetsUseScope> {

    @Autowired
    private AssetsUseScopeMapper assetsUseScopeMapper;
    @Autowired
    private AssetsUseScopeDao assetsUseScopeDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     *  使用范围展示数据
     * @param pageIndex
     * @param pageSize
     * @param assetsUseScope
     * @return
     */
    public PageInfo<AssetsUseScope> toList(Integer pageIndex, Integer pageSize, AssetsUseScope assetsUseScope) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<AssetsUseScope> assetsUseScopes = selectList(Wrappers.<AssetsUseScope>lambdaQuery()
                .like(StringUtils.isNotEmpty(assetsUseScope.getScope_name()),AssetsUseScope::getScope_name,assetsUseScope.getScope_name())
                .eq(AssetsUseScope::getIs_del, "0")
        );
        return new PageInfo<>(assetsUseScopes);
    }

    /**
     *  使用范围ID查询
     * @param id
     * @return
     */
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsUseScope> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsUseScope assetsUseScope = selectOne(queryWrapper);
        return ResultInfo.success(assetsUseScope);
    }

    /**
     *  添加使用范围
     * @param assetsUseScope
     * @return
     */
    public ResultInfo addAssetsUseScope(AssetsUseScope assetsUseScope) {
        // 删除状态
        assetsUseScope.setIs_del(String.valueOf(0));
        // 创建时间
        assetsUseScope.setCreate_time(new Date());
        // 创建者
        assetsUseScope.setCreate_user(sysUserService.getUser().getId());
        insert(assetsUseScope);
        return ResultInfo.success();
    }

    /**
     *  编辑使用范围
     * @param assetsUseScope
     * @return
     */
    public ResultInfo editAssetsCompany(AssetsUseScope assetsUseScope) {
        // 修改者
        assetsUseScope.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsUseScope.setUpdate_time(new Date());
        updateById(assetsUseScope);
        return ResultInfo.success();
    }

    public List<AssetsUseScope> getAll(){
        LambdaQueryWrapper<AssetsUseScope> wrapper = new LambdaQueryWrapper();
        wrapper.eq(AssetsUseScope::getIs_del, GlobalData.ISDEL_NO);
        wrapper.eq(AssetsUseScope::getIs_enable, GlobalData.IS_ENABLE_YES);
        return selectList(wrapper);
    }


}
