package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsExpandFormDao;
import com.jsdc.itss.mapper.AssetsExpandFormFieldMapper;
import com.jsdc.itss.mapper.AssetsExpandFormMapper;
import com.jsdc.itss.model.AssetsExpandForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsExpandFormField;
import com.jsdc.itss.model.AssetsType;
import com.jsdc.itss.vo.SysDepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class AssetsExpandFormService extends BaseService<AssetsExpandFormDao, AssetsExpandForm> {

    @Autowired
    private AssetsExpandFormMapper assetsExpandFormMapper;
    @Autowired
    private AssetsExpandFormDao assetsExpandFormDao;
    @Autowired
    private AssetsExpandFormFieldService assetsExpandFormFieldService;
    @Autowired
    private SysUserService sysUserService;


    public PageInfo<AssetsExpandForm> toList(Integer pageIndex, Integer pageSize, AssetsExpandForm beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsExpandForm> assetsExpandFormVos = assetsExpandFormMapper.toList(beanParam);

        PageInfo<AssetsExpandForm> page = new PageInfo<>(assetsExpandFormVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsExpandForm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsExpandForm assetsExpandForm = selectOne(queryWrapper);
        return ResultInfo.success(assetsExpandForm);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsExpandForm(AssetsExpandForm bean) {
        if (null == bean.getId() || "".equals(bean.getId())){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
        }else {
            editAssetsExpandForm(bean);
        }

        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editAssetsExpandForm(AssetsExpandForm bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public ResultInfo getExpandSubList(int assetsExpandFormId) {
        QueryWrapper<AssetsExpandForm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",assetsExpandFormId);
        AssetsExpandForm aef = selectOne(queryWrapper);

        return getExpandSubList(aef);
    }

    public ResultInfo getExpandSubList(AssetsExpandForm bean) {
        List<AssetsExpandFormField> assetsExpandFormVos = assetsExpandFormFieldService.getExpandSubList(bean);
        return ResultInfo.success(assetsExpandFormVos);
    }

    /**
     * 根据formId查询id==formId或parent_form_id == formId
     * @param formId
     * @return
     */
    public List<AssetsExpandForm> getAllExpandFormByFormId(Integer formId){
        LambdaQueryWrapper<AssetsExpandForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetsExpandForm::getIs_del, GlobalData.ISDEL_NO);
        wrapper.and(w -> w.eq(AssetsExpandForm::getId, formId).or().eq(AssetsExpandForm::getParent_form_id, formId));
        return selectList(wrapper);
    }



    public List<AssetsExpandForm> getTree( AssetsExpandForm beanParam) {
        List<AssetsExpandForm> assetsExpandFormList = assetsExpandFormMapper.toList(beanParam);
        List<AssetsExpandForm> resultList = new ArrayList<>();
        for (AssetsExpandForm temp: assetsExpandFormList){
            if (0 == temp.getParent_form_id()){
                resultList.add(temp);
                setChildList(temp,assetsExpandFormList);
            }
        }
        return resultList;
    }


    private void setChildList(AssetsExpandForm assetsExpandForm, List<AssetsExpandForm> assetsExpandFormList){
        for(AssetsExpandForm temp:assetsExpandFormList){
            if(temp.getParent_form_id()==assetsExpandForm.getId()){
                if ( 0 != temp.getParent_form_id()){
                    temp.setParent_form_name(null == selectById(temp.getParent_form_id()) ? "" : selectById(temp.getParent_form_id()).getName());
                }
                assetsExpandForm.getChildren().add(temp);
                setChildList(temp,assetsExpandFormList);
            }
        }
    }


    public ResultInfo deleteOneForm(Integer id){
        QueryWrapper<AssetsExpandForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("parent_form_id",id);
        Long count = selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("操作失败,此数据包含子数据，无法删除");
        }else {
            AssetsExpandForm assetsExpandForm = selectById(id);
            assetsExpandForm.setIs_del("1");
            updateById(assetsExpandForm);
            return ResultInfo.success();
        }
    }

}
