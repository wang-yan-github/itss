package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysPrintDao;
import com.jsdc.itss.mapper.SysPrintMapper;
import com.jsdc.itss.mapper.SysUserMapper;
import com.jsdc.itss.model.SysPrint;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysPrintService extends BaseService<SysPrintDao, SysPrint> {

    @Autowired
    private SysPrintMapper sysPrintMapper;
    @Autowired
    private SysPrintDao sysPrintDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper ;

    public PageInfo<SysPrint> toList(Integer pageIndex, Integer pageSize, SysPrint beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysPrint> sysPrintVos = sysPrintMapper.toList(beanParam);
        List<SysPrint> result = new ArrayList<>();
        for (SysPrint temp : sysPrintVos){
            if (temp.getCreate_user()!= null){
                String create_user_name = getUserName(temp.getCreate_user());
                temp.setCreate_user_name(create_user_name);
            }
            result.add(temp);
        }
        PageInfo<SysPrint> page = new PageInfo<>(result);

        return page;
    }

    public String getUserName ( Integer id){
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser != null){
            return sysUser.getName();
        }
        return null;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<SysPrint> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysPrint sysPrint = selectOne(queryWrapper);
        return ResultInfo.success(sysPrint);
    }

    /**
     *  添加
     */
    public ResultInfo addSysPrint(SysPrint bean) {
        if (bean.getId()== null ){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
            return ResultInfo.success();
        }else {
            return this.editSysPrint(bean);
        }

    }

    /**
     *  编辑
     */
    public ResultInfo editSysPrint(SysPrint bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public ResultInfo deleteById(Integer id){
        UpdateWrapper<SysPrint> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("is_del","1");
        sysPrintMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }
}


