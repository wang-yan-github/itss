package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventServiceCategoryModularDao;
import com.jsdc.itss.mapper.EventServiceCategoryModularMapper;
import com.jsdc.itss.model.EventServiceCategoryModular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventServiceCategoryModularService  extends BaseService<EventServiceCategoryModularDao, EventServiceCategoryModular> {

    @Autowired
    private EventServiceCategoryModularMapper eventServiceCategoryModularMapper;
}
