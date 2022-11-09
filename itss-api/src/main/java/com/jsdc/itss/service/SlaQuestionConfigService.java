package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SlaQuestionConfigDao;
import com.jsdc.itss.mapper.SlaQuestionConfigMapper;
import com.jsdc.itss.model.SlaQuestion;
import com.jsdc.itss.model.SlaQuestionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: SlaQuestionConfigService
 * Description:
 * date: 2022/3/8 11:04
 *
 * @author bn
 */
@Transactional
@Service
public class SlaQuestionConfigService extends BaseService<SlaQuestionConfigDao,SlaQuestionConfig> {

    @Autowired
    private SlaQuestionConfigMapper slaQuestionConfigMapper;
}
