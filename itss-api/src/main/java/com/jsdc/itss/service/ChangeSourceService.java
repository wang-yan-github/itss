package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeSourceDao;
import com.jsdc.itss.mapper.ChangeSourceMapper;
import com.jsdc.itss.model.ChangeSource;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ChangeSource
 * Description: 变更来源表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class ChangeSourceService extends BaseService<ChangeSourceDao, ChangeSource> {

    @Autowired
    private ChangeSourceMapper changeSourceMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询
     */
    public PageInfo<ChangeSource> getPageList(Integer pageIndex, Integer pageSize, ChangeSource bean) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<ChangeSource> list = selectList(Wrappers.<ChangeSource>lambdaQuery()
                .like(StringUtils.isNotEmpty(bean.getSource_name()), ChangeSource::getSource_name, bean.getSource_name())
                .eq(ChangeSource::getIs_del, "0")
        );
        for (ChangeSource temp : list){
            temp.setStatus_name("1".equals(temp.getStatus()) ? "使用" : "禁止");
        }
        PageInfo<ChangeSource> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 列表查询
     */
    public List<ChangeSource> getList(ChangeSource bean) {
        QueryWrapper<ChangeSource> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(bean.getSource_name())) {
            queryWrapper.like("source_name", bean.getSource_name());
        }
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("status", "1");
        List<ChangeSource> list = selectList(queryWrapper);
        return list;
    }

    /**
     * 保存/更新
     */
    public ResultInfo saveOrUpd(ChangeSource bean) {
        SysUser sysUser = sysUserService.getUser();
        if (Base.notEmpty(bean.getId())) {
            //修改
            // 修改者
            bean.setUpdate_user(sysUser.getId());
            // 修改时间
            bean.setUpdate_time(new Date());
            updateById(bean);
            return ResultInfo.success();
        } else {
            //新增
            bean.setIs_del("0");
            bean.setCreate_time(new Date());
            bean.setCreate_user(sysUser.getId());
            insert(bean);
            return ResultInfo.success();
        }
    }

    /**
     * 删除
     */
    public ResultInfo deleteSource(Integer ids){
        UpdateWrapper<ChangeSource> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",ids);
        updateWrapper.set("is_del","1");
        changeSourceMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }

}
