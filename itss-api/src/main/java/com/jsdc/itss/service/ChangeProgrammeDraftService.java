package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeProgrammeDraftDao;
import com.jsdc.itss.mapper.ChangeProgrammeDraftMapper;
import com.jsdc.itss.model.ChangeProgrammeDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeProgrammeDraftService
 * Description: 方案拟定
 * date: 2022/3/14 15:57
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeProgrammeDraftService extends BaseService<ChangeProgrammeDraftDao, ChangeProgrammeDraft> {

    @Autowired
    ChangeProgrammeDraftMapper mapper;


}
