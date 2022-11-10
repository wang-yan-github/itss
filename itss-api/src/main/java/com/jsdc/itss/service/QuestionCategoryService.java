package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionCategoryDao;
import com.jsdc.itss.mapper.AssetsCustomerContractMapper;
import com.jsdc.itss.mapper.QuestionCategoryMapper;
import com.jsdc.itss.mapper.QuestionCategoryReviewerMapper;
import com.jsdc.itss.mapper.SysUserMapper;
import com.jsdc.itss.model.AssetsCustomerContract;
import com.jsdc.itss.model.QuestionCategory;
import com.jsdc.itss.model.QuestionCategoryReviewer;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.model.vo.QuestionCategoryVo;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysDepartmentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionCategory
 * Description: 问题类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class QuestionCategoryService extends BaseService<QuestionCategoryDao, QuestionCategory> {

    @Autowired
    private QuestionCategoryMapper questionCategoryMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QuestionCategoryReviewerMapper questionCategoryReviewerMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AssetsCustomerContractMapper assetsCustomerContractMapper;

    /**
     * 分页查询
     */
    public PageInfo<QuestionCategory> getPageList(Integer pageIndex, Integer pageSize, QuestionCategory bean) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<QuestionCategory> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(bean.getName())) {
            queryWrapper.like("name", bean.getName());
        }
        queryWrapper.eq("is_del", "0");
        List<QuestionCategory> list = selectList(queryWrapper);
        PageInfo<QuestionCategory> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 获取根目录
     */
    public List<QuestionCategory> getTreeMenu(QuestionCategory questionCategory) {
        QueryWrapper<QuestionCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(questionCategory.getName())){
            queryWrapper.like("name",questionCategory.getName());
        }
        List<QuestionCategory> list = selectList(queryWrapper);
        for (QuestionCategory questionCategory1 : list) {
            questionCategory1.setContractName(null == assetsCustomerContractMapper.selectById(questionCategory1.getCustomer_contract_id()) ? "" : assetsCustomerContractMapper.selectById(questionCategory1.getCustomer_contract_id()).getContract_name());
        }
        List<QuestionCategory> voList = new ArrayList<QuestionCategory>();
        for (QuestionCategory bean : list) {
            //树状图 标题
//            bean.setName(bean.getName());
            if (Base.empty(bean.getParent_id())) {
                voList.add(findChildren(bean, list));
            }

        }
        return voList;
    }

    /**
     * 获取子目录
     */
    public  QuestionCategory findChildren(QuestionCategory bean, List<QuestionCategory> list) {
        for (QuestionCategory vo : list) {
            if (StringUtils.isNotEmpty(vo.getIs_use())){
                if (vo.getIs_use().equals("0")){
                    vo.setIs_use_name("禁用");
                }else{
                    vo.setIs_use_name("使用");
                }
            }
//            int id = vo.getId();
//            QueryWrapper<QuestionCategoryReviewer> questionCategoryReviewerQueryWrapper =new QueryWrapper<>();
//            questionCategoryReviewerQueryWrapper.eq("category_id",id);
//            questionCategoryReviewerQueryWrapper.eq("is_del","0");
//            QuestionCategoryReviewer questionCategoryReviewer =questionCategoryReviewerMapper.selectOne(questionCategoryReviewerQueryWrapper);
//
//            if (questionCategoryReviewer != null){
//                SysUser sysUser = sysUserService.selectById(questionCategoryReviewer.getReview_user());
//                if (sysUser!= null){
//                    vo.setReview_user_name(sysUser.getUser_name());
//                }
//            }
            if (bean.getId().equals(vo.getParent_id())) {
                if (Base.empty(bean.getChildren())) {
                    bean.setChildren(new ArrayList<QuestionCategory>());
                }
                bean.getChildren().add(findChildren(vo, list));
            }
        }
        return bean;
    }

    /**
     * 保存/更新
     */
    public ResultInfo saveOrUpd(QuestionCategory bean) {
        QueryWrapper<QuestionCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("name",bean.getName());
        if(Base.notEmpty(bean.getId())){
            queryWrapper.ne("id",bean.getId());
        }
        Long  count = questionCategoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在");
        }




        SysUser sysUser = sysUserService.getUser();
        if (Base.notEmpty(bean.getId())) {
            //修改
            // 修改者
            bean.setUpdate_user(sysUser.getId());
            // 修改时间
            bean.setUpdate_time(new Date());
            updateById(bean);
            UpdateWrapper<QuestionCategoryReviewer> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("category_id",bean.getId());
            updateWrapper.set("is_del","1");
            questionCategoryReviewerMapper.update(null,updateWrapper);
        } else {
            //新增
            bean.setIs_del("0");
            bean.setCreate_time(new Date());
            bean.setCreate_user(sysUser.getId());
            insert(bean);
            //QuestionCategoryReviewer

        }
        List<SysUser> list1 = bean.getList();
        if (!CollectionUtils.isEmpty(list1)){
            for (SysUser temp : list1){
                QuestionCategoryReviewer questionCategoryReviewer = new QuestionCategoryReviewer();
                int review_user =temp.getId();
                int category_id = bean.getId();
                questionCategoryReviewer.setIs_del("0");
                questionCategoryReviewer.setCreate_time(new Date());
                questionCategoryReviewer.setCreate_user(sysUserService.getUser().getId());
                questionCategoryReviewer.setReview_user(review_user);
                questionCategoryReviewer.setCategory_id(category_id);
                questionCategoryReviewer.setSort(temp.getSort());
                questionCategoryReviewerMapper.insert(questionCategoryReviewer);
            }

        }
        return ResultInfo.success();
    }


    public ResultInfo deleteCategory(Integer ids){
        UpdateWrapper<QuestionCategory> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",ids);
        updateWrapper.set("is_del","1");
        questionCategoryMapper.update(null,updateWrapper);

        UpdateWrapper<QuestionCategoryReviewer> updateWrapper1 =new UpdateWrapper<>();
        updateWrapper1.eq("category_id",ids);
        updateWrapper1.set("is_del","1");
        questionCategoryReviewerMapper.update(null,updateWrapper1);
        return ResultInfo.success();
    }

    public QuestionCategory getOneInfo(Integer ids){
        QuestionCategory questionCategory = questionCategoryMapper.selectById(ids);
        Integer appraisal_user = questionCategory.getAppraisal_user();
        if (appraisal_user != null){
            SysUser sysUser = sysUserMapper.selectById(appraisal_user);
            if (sysUser != null){
                questionCategory.setAppraisal_name(sysUser.getName());
            }
        }
        if (questionCategory.getParent_id() != null){
            questionCategory.setParent_name(questionCategoryMapper.selectById(questionCategory.getParent_id()).getName());
        }
        AssetsCustomerContract contract = assetsCustomerContractMapper.selectById(questionCategory.getCustomer_contract_id());
        questionCategory.setContractName(null == contract?"":contract.getContract_name());

        List<QuestionCategoryReviewer> list = new ArrayList<>();
        QueryWrapper<QuestionCategoryReviewer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("category_id",questionCategory.getId());
        list= questionCategoryReviewerMapper.selectList(queryWrapper);
        List<SysUser> list1 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (QuestionCategoryReviewer temp : list){
                Integer review_user = temp.getReview_user();
                SysUser sysUser =sysUserService.selectById(review_user);
                sysUser.setSort(temp.getSort());
                list1.add(sysUser);
            }
        }
        questionCategory.setList(list1);
        return questionCategory;
    }

}
