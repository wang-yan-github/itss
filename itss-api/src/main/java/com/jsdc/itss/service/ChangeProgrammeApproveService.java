package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeProgrammeApproveDao;
import com.jsdc.itss.mapper.ChangeProgrammeApproveMapper;
import com.jsdc.itss.model.ChangeProgrammeApprove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeProgrammeApproveService
 * Description:
 * date: 2022/3/17 9:33
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeProgrammeApproveService extends BaseService<ChangeProgrammeApproveDao,ChangeProgrammeApprove> {

    @Autowired
    ChangeProgrammeApproveMapper mapper;
}
