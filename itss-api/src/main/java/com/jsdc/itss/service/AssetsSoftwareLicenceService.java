package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsSoftwareLicenceDao;
import com.jsdc.itss.mapper.AssetsManageMapper;
import com.jsdc.itss.mapper.AssetsSoftwareLicenceMapper;
import com.jsdc.itss.mapper.AssetsTypeMapper;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.model.AssetsSoftwareLicence;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class AssetsSoftwareLicenceService extends BaseService<AssetsSoftwareLicenceDao, AssetsSoftwareLicence> {

    @Autowired
    private AssetsSoftwareLicenceMapper assetsSoftwareLicenceMapper;
    @Autowired
    private AssetsSoftwareLicenceDao assetsSoftwareLicenceDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AssetsTypeMapper assetsTypeMapper;
    @Autowired
    private AssetsManageMapper assetsManageMapper ;

    public PageInfo<AssetsSoftwareLicence> toList(Integer pageIndex, Integer pageSize, AssetsSoftwareLicence beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<AssetsSoftwareLicence> assetsSoftwareLicenceVos = assetsSoftwareLicenceMapper.toList(beanParam);

        PageInfo<AssetsSoftwareLicence> page = new PageInfo<>(assetsSoftwareLicenceVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsSoftwareLicence> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsSoftwareLicence assetsSoftwareLicence = selectOne(queryWrapper);
        return ResultInfo.success(assetsSoftwareLicence);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsSoftwareLicence(AssetsSoftwareLicence bean) {
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
    public ResultInfo editAssetsSoftwareLicence(AssetsSoftwareLicence bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public List<AssetsSoftwareLicence> getAllSoftWare() {


        return selectList(Wrappers.<AssetsSoftwareLicence>lambdaQuery().eq(AssetsSoftwareLicence::getIs_del,"0"));
    }


    /**
     * 查询软件有所关联的配置项目
     */

    public List<AssetsManage> getAssetsManageList(){
        QueryWrapper<AssetsType> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("code","software");
        queryWrapper.eq("check_address","/pzgl/softwareView");
        List<AssetsType> list = assetsTypeMapper.selectList(queryWrapper);
        List<AssetsManage> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            AssetsType assetsType = list.get(0);
            Integer assets_type_id = assetsType.getId();
            QueryWrapper<AssetsManage> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("is_del","0").eq("assets_type_id",assets_type_id);
            result = assetsManageMapper.selectList(queryWrapper1);
        }

        return result;
    }
}
