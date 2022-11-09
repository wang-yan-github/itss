package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysWorkingDaysCalendarDao;
import com.jsdc.itss.model.SysWorkingDaysCalendar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysWorkingDaysCalendarService extends BaseService<SysWorkingDaysCalendarDao, SysWorkingDaysCalendar> {
}
