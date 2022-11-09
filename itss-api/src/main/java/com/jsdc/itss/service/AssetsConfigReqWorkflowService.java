package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigReqWorkflowDao;
import com.jsdc.itss.mapper.AssetsConfigReqWorkflowMapper;
import com.jsdc.itss.model.AssetsConfigReqWorkflow;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 配置请求审批流程表
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsConfigReqWorkflowService extends BaseService<AssetsConfigReqWorkflowDao, AssetsConfigReqWorkflow> {

    @Autowired
    private AssetsConfigReqWorkflowMapper assetsConfigReqWorkflowMapper;
    @Autowired
    private AssetsConfigReqWorkflowDao assetsConfigReqWorkflowDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsConfigReqWorkflow> toList(Integer pageIndex, Integer pageSize, AssetsConfigReqWorkflow beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsConfigReqWorkflow> assetsConfigReqWorkflowVos = assetsConfigReqWorkflowMapper.toList(beanParam);

        PageInfo<AssetsConfigReqWorkflow> page = new PageInfo<>(assetsConfigReqWorkflowVos);

        return page;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsConfigReqWorkflow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        AssetsConfigReqWorkflow assetsConfigReqWorkflow = selectOne(queryWrapper);
        return ResultInfo.success(assetsConfigReqWorkflow);
    }

    /**
     * 添加
     */
    public ResultInfo addAssetsConfigReqWorkflow(AssetsConfigReqWorkflow bean) {
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
     * 编辑
     */
    public ResultInfo editAssetsConfigReqWorkflow(AssetsConfigReqWorkflow bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
