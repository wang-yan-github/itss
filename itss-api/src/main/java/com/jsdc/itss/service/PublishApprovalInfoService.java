package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.PublishApprovalInfoDao;
import com.jsdc.itss.mapper.PublishApprovalInfoMapper;
import com.jsdc.itss.model.PublishApprovalInfo;
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
 * @create 2022-03-14 15:00:18
 */
@Service
@Transactional
public class PublishApprovalInfoService extends BaseService<PublishApprovalInfoDao, PublishApprovalInfo> {

    @Autowired
    private PublishApprovalInfoMapper publishApprovalInfoMapper;
    @Autowired
    private PublishApprovalInfoDao publishApprovalInfoDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<PublishApprovalInfo> toList(Integer pageIndex, Integer pageSize, PublishApprovalInfo beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<PublishApprovalInfo> publishApprovalInfoVos = publishApprovalInfoMapper.toList(beanParam);

        PageInfo<PublishApprovalInfo> page = new PageInfo<>(publishApprovalInfoVos);

        return page;
    }

    public List<PublishApprovalInfo> getByPublishId(Integer id) {
        return publishApprovalInfoMapper.getByPublishId(id);
    }

    /**
     *  添加
     */
    public void addPublishApprovalInfo(PublishApprovalInfo bean) {
        // 删除状态
        bean.setIs_del(GlobalData.ISDEL_NO);
        insert(bean);
    }

    /**
     *  编辑
     */
    public ResultInfo editPublishApprovalInfo(PublishApprovalInfo bean) {
        updateById(bean);
        return ResultInfo.success();
    }

}
