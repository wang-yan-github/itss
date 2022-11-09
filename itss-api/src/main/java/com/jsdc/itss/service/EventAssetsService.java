package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventAssetsDao;
import com.jsdc.itss.model.EventAssets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author libin
 * @create 2022/3/16 11:12
 */
@Service
@Transactional
public class EventAssetsService extends BaseService<EventAssetsDao, EventAssets> {
}
