package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeImplementApproveDao;
import com.jsdc.itss.mapper.ChangeImplementApproveMapper;
import com.jsdc.itss.model.ChangeImplementApprove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeImplementApproveService
 * Description:
 * date: 2022/3/18 11:16
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeImplementApproveService extends BaseService<ChangeImplementApproveDao,ChangeImplementApprove> {

    @Autowired
    ChangeImplementApproveMapper changeImplementApproveMapper;
}
