package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.PublishCategoryApproveDao;
import com.jsdc.itss.mapper.PublishCategoryApproveMapper;
import com.jsdc.itss.model.PublishCategoryApprove;
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
 * @Author wh
 * @create 2022-03-18 10:32:19
 */
@Service
@Transactional
public class PublishCategoryApproveService extends BaseService<PublishCategoryApproveDao, PublishCategoryApprove> {

    @Autowired
    private PublishCategoryApproveMapper publishCategoryApproveMapper;
    @Autowired
    private PublishCategoryApproveDao publishCategoryApproveDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<PublishCategoryApprove> toList(Integer pageIndex, Integer pageSize, PublishCategoryApprove beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<PublishCategoryApprove> publishCategoryApproveVos = publishCategoryApproveMapper.toList(beanParam);

        PageInfo<PublishCategoryApprove> page = new PageInfo<>(publishCategoryApproveVos);

        return page;
    }

    /**
     * 根据发布基础信息id查询
     * @param id
     * @return
     */
    public List<PublishCategoryApprove> getByPublishId(Integer id) {
        QueryWrapper<PublishCategoryApprove> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",GlobalData.ISDEL_NO);
        queryWrapper.eq("publish_category_id",id);
        List<PublishCategoryApprove> publishCategoryApproves = selectList(queryWrapper);
        return publishCategoryApproves;
    }

    /**
     *  添加
     */
    public ResultInfo addPublishCategoryApprove(PublishCategoryApprove bean) {
        // 删除状态
        bean.setIs_del(GlobalData.ISDEL_NO);
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
    public ResultInfo editPublishCategoryApprove(PublishCategoryApprove bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
