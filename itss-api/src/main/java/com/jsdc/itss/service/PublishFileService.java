package com.jsdc.itss.service;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.PublishFileDao;
import com.jsdc.itss.mapper.FileManageMapper;
import com.jsdc.itss.mapper.PublishFileMapper;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.PublishFile;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author thr
 * @create 2022-06-27 13:44:00
 */
@Service
@Transactional
public class PublishFileService extends BaseService<PublishFileDao, PublishFile> {

    @Autowired
    private PublishFileMapper publishFileMapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileManageMapper fileManageMapper;

    public PageInfo<PublishFile> toList(Integer pageIndex, Integer pageSize, PublishFile beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<PublishFile> publishFileVos = publishFileMapper.toList(beanParam);

        PageInfo<PublishFile> page = new PageInfo<>(publishFileVos);

        return page;
    }

    public List<PublishFile> getList(PublishFile beanParam) {

        List<PublishFile> publishFileVos = publishFileMapper.toList(beanParam);

        return publishFileVos;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<PublishFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        PublishFile publishFile = selectOne(queryWrapper);
        return ResultInfo.success(publishFile);
    }

    /**
     *  添加
     */
    public ResultInfo addPublishFile(PublishFile bean) {
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
    public ResultInfo editPublishFile(PublishFile bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    /**
     * 查询绑定附件
     * @param id
     * @param type
     * @return
     */
    public List<FileManage> getFileManageByType(Integer id) {
        List<PublishFile> pubFiles = selectList(Wrappers.<PublishFile>lambdaQuery()
                .eq(PublishFile::getPublish_id,id));
        if (CollectionUtils.isEmpty(pubFiles)) {
            return Collections.emptyList();
        }
        return fileManageMapper.selectList(Wrappers.<FileManage>lambdaQuery()
                .in(FileManage::getId,pubFiles.stream().map(PublishFile::getFile_id).toArray()));
    }
}
