package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeCloseDao;
import com.jsdc.itss.mapper.ChangeCloseMapper;
import com.jsdc.itss.model.ChangeClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeCloseService
 * Description:
 * date: 2022/3/18 11:26
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeCloseService extends BaseService<ChangeCloseDao,ChangeClose> {

    @Autowired
    ChangeCloseMapper changeCloseMapper;

}
