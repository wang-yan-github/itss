package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsEnclosureDao;
import com.jsdc.itss.mapper.AssetsEnclosureMapper;
import com.jsdc.itss.model.AssetsEnclosure;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.FileManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.Date;
import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsEnclosureService extends BaseService<AssetsEnclosureDao, AssetsEnclosure> {

    @Autowired
    private AssetsEnclosureMapper assetsEnclosureMapper;
    @Autowired
    private AssetsEnclosureDao assetsEnclosureDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsEnclosure> toList(Integer pageIndex, Integer pageSize, AssetsEnclosure beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsEnclosure> assetsEnclosureVos = assetsEnclosureMapper.toList(beanParam);

        PageInfo<AssetsEnclosure> page = new PageInfo<>(assetsEnclosureVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsEnclosure> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsEnclosure assetsEnclosure = selectOne(queryWrapper);
        return ResultInfo.success(assetsEnclosure);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsEnclosure(AssetsEnclosure bean) {
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
    public ResultInfo editAssetsEnclosure(AssetsEnclosure bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public List<FileManage> getFileManage(int assetsId) {
       return  assetsEnclosureMapper.getFileManage(assetsId);
    }
    public List<FileManage> getFileManageByType(int assetsId, int fileType) {
        return  assetsEnclosureMapper.getFileManageByType(assetsId, fileType);
    }
}
