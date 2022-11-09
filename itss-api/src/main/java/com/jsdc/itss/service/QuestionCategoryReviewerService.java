package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionCategoryReviewerDao;
import com.jsdc.itss.mapper.QuestionCategoryReviewerMapper;
import com.jsdc.itss.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: QuestionCategoryReviewer
 * Description: 问题类别审核人关联表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class QuestionCategoryReviewerService extends BaseService<QuestionCategoryReviewerDao, QuestionCategoryReviewer> {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QuestionCategoryReviewerMapper questionCategoryReviewerMapper;

    /**
     * 列表查询
     *
     * @param bean
     * @return
     */
    public List<QuestionCategoryReviewer> getList(QuestionCategoryReviewer bean) {
        QueryWrapper<QuestionCategoryReviewer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (Base.notEmpty(bean)) {
            //问题类别id
            if (Base.notEmpty(bean.getCategory_id())) {
                queryWrapper.eq("category_id", bean.getCategory_id());
            }
        }
        queryWrapper.orderByAsc("sort");
        List<QuestionCategoryReviewer> list = selectList(queryWrapper);
        return list;
    }

}
