package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeQuestionDao;
import com.jsdc.itss.mapper.ChangeQuestionMapper;
import com.jsdc.itss.model.ChangeQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeQuestion
 * Description:
 * date: 2022/3/14 17:07
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeQuestionService extends BaseService<ChangeQuestionDao,ChangeQuestion> {

    @Autowired
    ChangeQuestionMapper changeQuestionMapper;
}
