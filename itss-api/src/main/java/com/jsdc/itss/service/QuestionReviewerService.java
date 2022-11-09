package com.jsdc.itss.service;

import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionReviewerDao;
import com.jsdc.itss.mapper.QuestionReviewerMapper;
import com.jsdc.itss.model.QuestionReviewer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Date;

import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 问题审核人关联表
 *
 * @Author thr
 * @create 2022-03-24 14:21:12
 */
@Service
@Transactional
public class QuestionReviewerService extends BaseService<QuestionReviewerDao, QuestionReviewer> {

    @Autowired
    private QuestionReviewerMapper questionReviewerMapper;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<QuestionReviewer> toList(Integer pageIndex, Integer pageSize, QuestionReviewer beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<QuestionReviewer> questionReviewerVos = questionReviewerMapper.toList(beanParam);

        PageInfo<QuestionReviewer> page = new PageInfo<>(questionReviewerVos);

        return page;
    }

    public List<QuestionReviewer> getList(QuestionReviewer beanParam) {
        List<QuestionReviewer> list = questionReviewerMapper.toList(beanParam);
        return list;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<QuestionReviewer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        QuestionReviewer questionReviewer = selectOne(queryWrapper);
        return ResultInfo.success(questionReviewer);
    }

    /**
     * 添加
     */
    public ResultInfo addQuestionReviewer(QuestionReviewer bean) {
        // 删除状态
        bean.setIs_del("0");
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     * 编辑
     */
    public ResultInfo editQuestionReviewer(QuestionReviewer bean) {
        // 修改者
        if(Base.empty(bean.getUpdate_user())){
            bean.setUpdate_user(sysUserService.getUser().getId());
        }
        // 修改时间
//
        updateById(bean);
        return ResultInfo.success();
    }


    /**
     * 根据问题id删除 问题关联审核人
     *
     * @param bean
     * @return
     */
    public void delByQuestionId(QuestionReviewer bean) {
        questionReviewerMapper.delByQuestionId(bean);
    }

}
