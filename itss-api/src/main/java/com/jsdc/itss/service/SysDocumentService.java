package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysDocumentDao;
import com.jsdc.itss.mapper.SysDocumentMapper;
import com.jsdc.itss.model.SysDocument;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysDocumentService extends BaseService<SysDocumentDao, SysDocument> {

    @Autowired
    private SysDocumentMapper sysDocumentMapper;
    @Autowired
    private SysDocumentDao sysDocumentDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysDocument> toList(Integer pageIndex, Integer pageSize, SysDocument beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysDocument> sysDocumentVos = sysDocumentMapper.toList(beanParam);

        PageInfo<SysDocument> page = new PageInfo<>(sysDocumentVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysDocument> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysDocument sysDocument = selectOne(queryWrapper);
        return ResultInfo.success(sysDocument);
    }

    /**
     *  添加
     */
    public ResultInfo addSysDocument(SysDocument bean) {
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
    public ResultInfo editSysDocument(SysDocument bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
