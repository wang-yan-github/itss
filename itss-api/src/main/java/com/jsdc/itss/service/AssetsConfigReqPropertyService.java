package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigReqPropertyDao;
import com.jsdc.itss.mapper.AssetsConfigReqPropertyMapper;
import com.jsdc.itss.model.AssetsConfigReqProperty;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 配置请求资产中间程表
 *
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsConfigReqPropertyService extends BaseService<AssetsConfigReqPropertyDao, AssetsConfigReqProperty> {

    @Autowired
    private AssetsConfigReqPropertyMapper assetsConfigReqPropertyMapper;
    @Autowired
    private AssetsConfigReqPropertyDao assetsConfigReqPropertyDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsConfigReqProperty> toList(Integer pageIndex, Integer pageSize, AssetsConfigReqProperty beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsConfigReqProperty> assetsConfigReqPropertyVos = assetsConfigReqPropertyMapper.toList(beanParam);

        PageInfo<AssetsConfigReqProperty> page = new PageInfo<>(assetsConfigReqPropertyVos);

        return page;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsConfigReqProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        AssetsConfigReqProperty assetsConfigReqProperty = selectOne(queryWrapper);
        return ResultInfo.success(assetsConfigReqProperty);
    }

    /**
     * 添加
     */
    public ResultInfo addAssetsConfigReqProperty(AssetsConfigReqProperty bean,Integer ... intArray) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        //获取用户信息
        SysUser user = null;
        if (intArray.length > 0 && null != intArray[0]) {
            user = sysUserService.selectById(intArray[0]);
        } else {
            user = sysUserService.getUser();
        }
        // 创建者
        bean.setCreate_user(user.getId());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     * 编辑
     */
    public ResultInfo editAssetsConfigReqProperty(AssetsConfigReqProperty bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
