package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsOsDao;
import com.jsdc.itss.mapper.AssetsOsMapper;
import com.jsdc.itss.model.AssetsOs;
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
public class AssetsOsService extends BaseService<AssetsOsDao, AssetsOs> {

    @Autowired
    private AssetsOsMapper assetsOsMapper;
    @Autowired
    private AssetsOsDao assetsOsDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     *  操作系统展示数据
     * @param pageIndex
     * @param pageSize
     * @param assetsOs
     * @return
     */
    public PageInfo<AssetsOs> toList(Integer pageIndex, Integer pageSize, AssetsOs assetsOs) {
        PageHelper.startPage(pageIndex,pageSize, "create_time desc");
        List<AssetsOs> assetsOsList = selectList(Wrappers.<AssetsOs>lambdaQuery()
                .like(StringUtils.isNotEmpty(assetsOs.getOs_name()), AssetsOs::getOs_name, assetsOs.getOs_name())
                .eq(AssetsOs::getIs_del, "0")
        );
        return new PageInfo<>(assetsOsList);
    }

    /**
     *  操作系统ID查询
     * @param id
     * @return
     */
    public ResultInfo getById(Integer id) {
        return ResultInfo.success(selectById(id));
    }

    /**
     *  添加操作系统
     * @param assetsOs
     * @return
     */
    public ResultInfo addAssetsOs(AssetsOs assetsOs) {
        // 删除状态
        assetsOs.setIs_del(String.valueOf(0));
        // 创建时间
        assetsOs.setCreate_time(new Date());
        // 创建者
        assetsOs.setCreate_user(sysUserService.getUser().getId());
        insert(assetsOs);
        return ResultInfo.success();
    }

    /**
     *  编辑操作系统
     * @param assetsOs
     * @return
     */
    public ResultInfo editAssetsOs(AssetsOs assetsOs) {
        // 修改者
        assetsOs.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsOs.setUpdate_time(new Date());
        updateById(assetsOs);
        return ResultInfo.success();
    }


}
