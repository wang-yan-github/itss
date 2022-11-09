package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SlaEventConfigDao;
import com.jsdc.itss.mapper.SlaEventConfigMapper;
import com.jsdc.itss.model.SlaEvent;
import com.jsdc.itss.model.SlaEventConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: SlaEventConfigService
 * Description:
 * date: 2022/3/8 9:50
 *
 * @author bn
 */
@Service
@Transactional
public class SlaEventConfigService extends BaseService<SlaEventConfigDao,SlaEventConfig> {

    @Autowired
    private SlaEventConfigMapper slaEventConfigMapper;
}
