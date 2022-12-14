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

        for (SysPrint temp : sysPrintVos){
            if (temp.getCreate_user()!= null){
                String create_user_name = getUserName(temp.getCreate_user());
                temp.setCreate_user_name(create_user_name);
            }

        }
        PageInfo<SysPrint> page = new PageInfo<>(sysPrintVos);

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
     *  ??????
     */
    public ResultInfo addSysPrint(SysPrint bean) {
        if (bean.getId()== null ){
            // ????????????
            bean.setIs_del(String.valueOf(0));
            // ????????????
            bean.setCreate_time(new Date());
            // ?????????
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
            return ResultInfo.success();
        }else {
            return this.editSysPrint(bean);
        }

    }

    /**
     *  ??????
     */
    public ResultInfo editSysPrint(SysPrint bean) {
        // ?????????
        bean.setUpdate_user(sysUserService.getUser().getId());
        // ????????????
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


