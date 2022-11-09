package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeEventDao;
import com.jsdc.itss.mapper.ChangeEventMapper;
import com.jsdc.itss.model.ChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeEvent
 * Description:
 * date: 2022/3/14 17:05
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeEventService extends BaseService<ChangeEventDao, ChangeEvent> {

    @Autowired
    ChangeEventMapper changeEventMapper;
}
