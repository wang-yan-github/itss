package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigParamsDao;
import com.jsdc.itss.mapper.AssetsConfigParamsMapper;
import com.jsdc.itss.mapper.AssetsExpandFormMapper;
import com.jsdc.itss.model.AssetsConfigParams;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;

@Service
@Transactional
@SuppressWarnings("ALL")
public class AssetsConfigParamsService  extends BaseService<AssetsConfigParamsDao, AssetsConfigParams> {

    @Autowired
    private AssetsConfigParamsMapper assetsConfigParamsMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AssetsExpandFormMapper assetsExpandFormMapper ;


    public ResultInfo getOne(){
        QueryWrapper<AssetsConfigParams> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        AssetsConfigParams assetsConfigParams =  assetsConfigParamsMapper.selectOne(queryWrapper);
        if (assetsConfigParams != null){
            Integer form_id = assetsConfigParams.getForm_id();
            AssetsExpandForm assetsExpandForm= assetsExpandFormMapper.selectById(form_id);
            if (assetsExpandForm != null){
                assetsConfigParams.setForm_name(assetsExpandForm.getName());
            }
        }

        return ResultInfo.success(assetsConfigParams);
    }

    public ResultInfo repairById(AssetsConfigParams assetsConfigParams){
        UpdateWrapper<AssetsConfigParams> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del","1");
        assetsConfigParamsMapper.update(null,updateWrapper);
        assetsConfigParams.setIs_del("0");
        assetsConfigParams.setCreate_user(sysUserService.getUser().getId());
        assetsConfigParams.setCreate_time(new Date());
        assetsConfigParams.setId(null);
        assetsConfigParamsMapper.insert(assetsConfigParams);
        return ResultInfo.success();
    }
}
