package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsOsLicenceDao;
import com.jsdc.itss.mapper.AssetsOsLicenceMapper;
import com.jsdc.itss.model.AssetsOsLicence;
import com.jsdc.itss.vo.AssetsOsLicenceVo;
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
public class AssetsOsLicenceService extends BaseService<AssetsOsLicenceDao, AssetsOsLicence> {

    @Autowired
    private AssetsOsLicenceMapper assetsOsLicenceMapper;
    @Autowired
    private AssetsOsLicenceDao assetsOsLicenceDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表查询
     * @author zdq
     * @date 2022/3/23
     */
    public PageInfo<AssetsOsLicenceVo> toList(Integer pageIndex, Integer pageSize, AssetsOsLicence beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<AssetsOsLicenceVo> assetsOsLicenceVos = assetsOsLicenceMapper.toList(beanParam);

        PageInfo<AssetsOsLicenceVo> page = new PageInfo<>(assetsOsLicenceVos);

        return page;
    }


    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsOsLicence> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsOsLicence assetsOsLicence = selectOne(queryWrapper);
        return ResultInfo.success(assetsOsLicence);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsOsLicence(AssetsOsLicence bean) {
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
    public ResultInfo editAssetsOsLicence(AssetsOsLicence bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public List<AssetsOsLicence> getAll(){
        LambdaQueryWrapper<AssetsOsLicence> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetsOsLicence::getIs_del, GlobalData.ISDEL_NO);
        return selectList(wrapper);
    }

}
