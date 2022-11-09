package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionChangeDao;
import com.jsdc.itss.mapper.QuestionChangeMapper;
import com.jsdc.itss.model.ChangeInfo;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.QuestionChange;
import com.jsdc.itss.model.QuestionWorkorder;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-15 13:26:30
 */
@Service
@Transactional
public class QuestionChangeService extends BaseService<QuestionChangeDao, QuestionChange> {

    @Autowired
    private QuestionChangeMapper questionChangeMapper;
    @Autowired
    private QuestionChangeDao questionChangeDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<QuestionChange> toList(Integer pageIndex, Integer pageSize, QuestionChange beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<QuestionChange> questionChangeVos = questionChangeMapper.toList(beanParam);

        PageInfo<QuestionChange> page = new PageInfo<>(questionChangeVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<QuestionChange> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        QuestionChange questionChange = selectOne(queryWrapper);
        return ResultInfo.success(questionChange);
    }

    /**
     *  添加
     */
    public ResultInfo addQuestionChange(QuestionChange bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();
    }


    public ResultInfo onSave(Integer questionId, List<ChangeVo> changeInfoList) {
        if (Base.notEmpty(questionId)) {
            QueryWrapper<QuestionChange> questionChangeQueryWrapper = new QueryWrapper<>();
            questionChangeQueryWrapper.eq("question_id", questionId);
            delete(questionChangeQueryWrapper) ;
        }
        if (Base.notEmpty(changeInfoList)) {
            for (ChangeVo e : changeInfoList) {
                QuestionChange questionChange = new QuestionChange();
                questionChange.setQuestion_id(questionId);
                questionChange.setChange_id(e.getId());
                addQuestionChange(questionChange);
            }
        }
        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editQuestionChange(QuestionChange bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
