package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsBrandDao;
import com.jsdc.itss.mapper.AssetsBrandMapper;
import com.jsdc.itss.model.AssetsBrand;
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
public class AssetsBrandService extends BaseService<AssetsBrandDao, AssetsBrand> {

    @Autowired
    private AssetsBrandMapper assetsBrandMapper;
    @Autowired
    private AssetsBrandDao assetsBrandDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     *  品牌管理展示数据
     * @param pageIndex
     * @param pageSize
     * @param assetsBrand
     * @return
     */
    public PageInfo<AssetsBrand> toList(Integer pageIndex, Integer pageSize, AssetsBrand assetsBrand) {
        PageHelper.startPage(pageIndex,pageSize);
        QueryWrapper<AssetsBrand> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(assetsBrand.getBrand_name())){
            queryWrapper.like("brand_name",assetsBrand.getBrand_name());
        }
        queryWrapper.eq("is_del",0);
        List<AssetsBrand> assetsBrands = selectList(queryWrapper);
        PageInfo<AssetsBrand> page=new PageInfo<>(assetsBrands);
        return page;
    }

    public List<AssetsBrand> getList(AssetsBrand assetsBrand) {
        QueryWrapper<AssetsBrand> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        List<AssetsBrand> assetsBrands = selectList(queryWrapper);
        return assetsBrands;
    }

    /**
     *  品牌管理ID查询
     * @param id
     * @return
     */
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsBrand> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsBrand assetsBrand = selectOne(queryWrapper);
        return ResultInfo.success(assetsBrand);
    }

    /**
     *  添加品牌管理
     * @param assetsBrand
     * @return
     */
    public ResultInfo addAssetsBrand(AssetsBrand assetsBrand) {
        // 删除状态
        assetsBrand.setIs_del(String.valueOf(0));
        // 创建时间
        assetsBrand.setCreate_time(new Date());
        // 创建者
        assetsBrand.setCreate_user(sysUserService.getUser().getId());
        insert(assetsBrand);
        return ResultInfo.success();
    }

    /**
     *  编辑品牌管理
     * @param assetsBrand
     * @return
     */
    public ResultInfo editAssetsBrand(AssetsBrand assetsBrand) {
        // 修改者
        assetsBrand.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsBrand.setUpdate_time(new Date());
        updateById(assetsBrand);
        return ResultInfo.success();
    }

    public List<AssetsBrand> getAll(){
        LambdaQueryWrapper<AssetsBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetsBrand::getIs_del, GlobalData.ISDEL_NO);
        return selectList(wrapper);
    }


}
