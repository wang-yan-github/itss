package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionFileDao;
import com.jsdc.itss.mapper.QuestionFileMapper;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.QuestionFile;
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
 * 问题管理 附件
 * @Author thr
 * @create 2022-04-14 14:12:37
 */
@Service
@Transactional
public class QuestionFileService extends BaseService<QuestionFileDao, QuestionFile> {

    @Autowired
    private QuestionFileMapper questionFileMapper;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<QuestionFile> toList(Integer pageIndex, Integer pageSize, QuestionFile beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<QuestionFile> questionFileVos = questionFileMapper.toList(beanParam);

        PageInfo<QuestionFile> page = new PageInfo<>(questionFileVos);

        return page;
    }

    public List<QuestionFile> getList(QuestionFile beanParam) {

        List<QuestionFile> questionFileVos = questionFileMapper.toList(beanParam);

        return questionFileVos;
    }

    public List<FileManage> getFileManageByType(int questionId, int type) {
        return  questionFileMapper.getFileManageByType(questionId, type);
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<QuestionFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        QuestionFile questionFile = selectOne(queryWrapper);
        return ResultInfo.success(questionFile);
    }

    /**
     * 添加
     */
    public ResultInfo addQuestionFile(QuestionFile bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
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
    public ResultInfo editQuestionFile(QuestionFile bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

}
