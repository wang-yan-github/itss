package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeTransferDao;
import com.jsdc.itss.mapper.ChangeTransferMapper;
import com.jsdc.itss.model.ChangeTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeTransferService
 * Description:
 * date: 2022/3/18 14:24
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeTransferService extends BaseService<ChangeTransferDao, ChangeTransfer> {

    @Autowired
    ChangeTransferMapper changeTransferMapper;




}
