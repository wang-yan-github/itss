package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysExtendFormSeeDao;
import com.jsdc.itss.mapper.SysExtendFormSeeMapper;
import com.jsdc.itss.model.SysExtendFormSee;
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
public class SysExtendFormSeeService extends BaseService<SysExtendFormSeeDao, SysExtendFormSee> {

    @Autowired
    private SysExtendFormSeeMapper sysExtendFormSeeMapper;
    @Autowired
    private SysExtendFormSeeDao sysExtendFormSeeDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SysExtendFormSee> toList(Integer pageIndex, Integer pageSize, SysExtendFormSee beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysExtendFormSee> sysExtendFormSeeVos = sysExtendFormSeeMapper.toList(beanParam);

        PageInfo<SysExtendFormSee> page = new PageInfo<>(sysExtendFormSeeVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysExtendFormSee> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysExtendFormSee sysExtendFormSee = selectOne(queryWrapper);
        return ResultInfo.success(sysExtendFormSee);
    }

    /**
     *  添加
     */
    public ResultInfo addSysExtendFormSee(SysExtendFormSee bean) {
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
    public ResultInfo editSysExtendFormSee(SysExtendFormSee bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    /**
     * 查询自定义字段list
     * @param extendId
     * @return
     */
    public  List<SysExtendFormSee>  getByExtendId(Integer extendId) {
        QueryWrapper<SysExtendFormSee> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("is_use",0);
        queryWrapper.eq("extend_id",extendId);
        queryWrapper.orderByAsc("order_num");
        List<SysExtendFormSee> sysExtendFormSees = selectList(queryWrapper);
        return sysExtendFormSees;
    }
}
