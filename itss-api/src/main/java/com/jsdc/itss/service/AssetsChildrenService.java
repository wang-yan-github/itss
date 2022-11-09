package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsChildrenDao;
import com.jsdc.itss.mapper.AssetsChildrenMapper;
import com.jsdc.itss.model.AssetsChildren;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author wh
 * @create 2022-03-08 15:50:09
 */
@Service
@Transactional
public class AssetsChildrenService extends BaseService<AssetsChildrenDao, AssetsChildren> {

    @Autowired
    private AssetsChildrenMapper assetsChildrenMapper;
    @Autowired
    private AssetsChildrenDao assetsChildrenDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsChildren> toList(Integer pageIndex, Integer pageSize, AssetsChildren beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsChildren> assetsChildrenVos = assetsChildrenMapper.toList(beanParam);

        PageInfo<AssetsChildren> page = new PageInfo<>(assetsChildrenVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsChildren> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsChildren assetsChildren = selectOne(queryWrapper);
        return ResultInfo.success(assetsChildren);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsChildren(AssetsChildren bean) {
        if (bean.getId() == null){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
            return ResultInfo.success();
        }else {
            // 修改者
            bean.setUpdate_user(sysUserService.getUser().getId());
            // 修改时间
            bean.setUpdate_time(new Date());
            updateById(bean);
            return ResultInfo.success();
        }

    }

    /**
     *  删除
     */
    public ResultInfo delAssetsChildren(Integer ids) {
        UpdateWrapper<AssetsChildren> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",ids);
        updateWrapper.set("is_del","1");
        assetsChildrenMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }

    /**
     * 根据配置类id获取配置项子类
     * @param beanParam
     * @return
     */
    public ResultInfo getACByAssetsTypeId(AssetsChildren beanParam) {
        QueryWrapper<AssetsChildren> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("assets_type_id",beanParam.getAssets_type_id());
        List<AssetsChildren> acList = selectList(queryWrapper);
        return ResultInfo.success(acList);
    }
    public  List<AssetsChildren> getACListByAssetsTypeId(AssetsChildren beanParam) {
        QueryWrapper<AssetsChildren> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("assets_type_id",beanParam.getAssets_type_id());
        List<AssetsChildren> acList = selectList(queryWrapper);
        return acList;
    }
}
