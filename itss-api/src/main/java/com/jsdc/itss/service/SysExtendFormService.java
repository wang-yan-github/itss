package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysExtendFormDao;
import com.jsdc.itss.mapper.SysExtendFormMapper;
import com.jsdc.itss.model.SysExtendForm;
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
public class SysExtendFormService extends BaseService<SysExtendFormDao, SysExtendForm> {

    @Autowired
    private SysExtendFormMapper sysExtendFormMapper;
    @Autowired
    private SysExtendFormDao sysExtendFormDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysExtendForm> toList(Integer pageIndex, Integer pageSize, SysExtendForm beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysExtendForm> sysExtendFormVos = sysExtendFormMapper.toList(beanParam);

        PageInfo<SysExtendForm> page = new PageInfo<>(sysExtendFormVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysExtendForm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysExtendForm sysExtendForm = selectOne(queryWrapper);
        return ResultInfo.success(sysExtendForm);
    }

    /**
     *  添加
     */
    public ResultInfo addSysExtendForm(SysExtendForm bean) {
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
    public ResultInfo editSysExtendForm(SysExtendForm bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    /**
     * 获取全部扩展表单
     * @param sysExtendForm
     * @return
     */
    public ResultInfo getExtendFormList(SysExtendForm sysExtendForm) {
        List<SysExtendForm> sysExtendFormVos = sysExtendFormMapper.toList(sysExtendForm);
        return ResultInfo.success(sysExtendFormVos);
    }
}
