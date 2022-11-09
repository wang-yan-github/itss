package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventTimesDao;
import com.jsdc.itss.model.EventTimes;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EventTimesService extends BaseService<EventTimesDao, EventTimes> {
}
