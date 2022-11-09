package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsExpandFormFieldDao;
import com.jsdc.itss.mapper.AssetsExpandFormFieldMapper;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.model.AssetsExpandFormField;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Date;
import java.util.stream.Collectors;

import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.CollectionUtils;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsExpandFormFieldService extends BaseService<AssetsExpandFormFieldDao, AssetsExpandFormField> {

    @Autowired
    private AssetsExpandFormFieldMapper assetsExpandFormFieldMapper;
    @Autowired
    private AssetsExpandFormFieldDao assetsExpandFormFieldDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AssetsExpandFormService expandFormService;

    public PageInfo<AssetsExpandFormField> toList(Integer pageIndex, Integer pageSize, AssetsExpandFormField beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsExpandFormField> assetsExpandFormFieldVos = assetsExpandFormFieldMapper.toList(beanParam);

        PageInfo<AssetsExpandFormField> page = new PageInfo<>(assetsExpandFormFieldVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsExpandFormField> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsExpandFormField assetsExpandFormField = selectOne(queryWrapper);
        return ResultInfo.success(assetsExpandFormField);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsExpandFormField(AssetsExpandFormField bean) {
        if (null == bean.getId()){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
        }else {
            editAssetsExpandFormField(bean);
        }

        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editAssetsExpandFormField(AssetsExpandFormField bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public List<AssetsExpandFormField> getAssetsExpandFormFields(AssetsExpandFormField beanParam){
        QueryWrapper<AssetsExpandFormField> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("form_id",beanParam.getForm_id());
        return selectList(queryWrapper);
    }

    public List<AssetsExpandFormField> getAssetsExpandFormFields(int formId){
        AssetsExpandFormField beanParam =  new AssetsExpandFormField();
        beanParam.setForm_id(formId);
        return getAssetsExpandFormFields(beanParam);
    }

    /**
     * 根据主表获取拓展表单字段表
     * @param beanParam
     * @return
     */
    public List<AssetsExpandFormField> getExpandSubList(AssetsExpandForm beanParam){
        return   assetsExpandFormFieldMapper.getExpandSubList(beanParam);
    }

    /**
     * 根据form_id list查询所有的字段信息
     * @param assetsExpandForms
     * @return
     */
    public List<AssetsExpandFormField> getAllFields(List<AssetsExpandForm> assetsExpandForms){
        List<Integer> formIds = assetsExpandForms.stream().map(x -> x.getId()).collect(Collectors.toList());
        LambdaQueryWrapper<AssetsExpandFormField> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(AssetsExpandFormField::getForm_id, formIds);
        wrapper.eq(AssetsExpandFormField::getIs_del, GlobalData.ISDEL_NO);
        wrapper.eq(AssetsExpandFormField::getIs_enable, GlobalData.IS_ENABLE_YES);
        return selectList(wrapper);
    }

    public List<AssetsExpandFormField> getExpandFormFieldFormId(AssetsType assetsType){
        List<AssetsExpandFormField> assetsExpandFormFields =   new ArrayList<>();
        if(null != assetsType && null != assetsType.getForm_id() && assetsType.getForm_id().intValue() > 0) {
            List<AssetsExpandForm> assetsExpandForms = expandFormService.getAllExpandFormByFormId(assetsType.getForm_id());
            if (!CollectionUtils.isEmpty(assetsExpandForms)){
                assetsExpandFormFields = getAllFields(assetsExpandForms);
            }

        }

        return assetsExpandFormFields;
    }
    public ResultInfo delFormFiled(Integer id){
        AssetsExpandFormField assetsExpandFormField = selectById(id);
        assetsExpandFormField.setIs_del("1");
        updateById(assetsExpandFormField);
        return ResultInfo.success();
    }
}


