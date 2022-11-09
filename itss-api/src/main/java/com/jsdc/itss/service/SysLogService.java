package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysLogDao;
import com.jsdc.itss.model.SysLog;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SysLogService extends BaseService<SysLogDao, SysLog> {
}
