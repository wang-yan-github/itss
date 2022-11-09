package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeImplementDao;
import com.jsdc.itss.mapper.ChangeImplementMapper;
import com.jsdc.itss.model.ChangeImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeImplementService
 * Description:
 * date: 2022/3/18 11:13
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeImplementService extends BaseService<ChangeImplementDao,ChangeImplement> {

    @Autowired
    ChangeImplementMapper changeImplementMapper;
}
