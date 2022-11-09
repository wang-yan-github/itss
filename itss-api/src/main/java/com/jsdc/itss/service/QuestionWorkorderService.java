package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionWorkorderDao;
import com.jsdc.itss.mapper.QuestionWorkorderMapper;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.QuestionWorkorder;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-15 10:40:27
 */
@Service
@Transactional
public class QuestionWorkorderService extends BaseService<QuestionWorkorderDao, QuestionWorkorder> {

    @Autowired
    private QuestionWorkorderMapper questionWorkorderMapper;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<QuestionWorkorder> toList(Integer pageIndex, Integer pageSize, QuestionWorkorder beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<QuestionWorkorder> questionWorkorderVos = questionWorkorderMapper.toList(beanParam);

        PageInfo<QuestionWorkorder> page = new PageInfo<>(questionWorkorderVos);

        return page;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<QuestionWorkorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        QuestionWorkorder questionWorkorder = selectOne(queryWrapper);
        return ResultInfo.success(questionWorkorder);
    }

    /**
     * 添加
     */
    public ResultInfo addQuestionWorkorder(QuestionWorkorder bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();
    }

    public ResultInfo onSave(Integer questionId, List<Event> eventList) {
        if (Base.notEmpty(questionId)) {
            QueryWrapper<QuestionWorkorder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("question_id", questionId);
            delete(queryWrapper);
        }
        if (Base.notEmpty(eventList)) {
            for (Event e : eventList) {
                QuestionWorkorder questionWorkorder = new QuestionWorkorder();
                questionWorkorder.setQuestion_id(questionId);
                questionWorkorder.setEvent_id(e.getId());
                addQuestionWorkorder(questionWorkorder);
            }
        }
        return ResultInfo.success();
    }

    /**
     * 编辑
     */
    public ResultInfo editQuestionWorkorder(QuestionWorkorder bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
