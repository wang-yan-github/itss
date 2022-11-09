package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeAssetsDao;
import com.jsdc.itss.mapper.ChangeAssetsMapper;
import com.jsdc.itss.model.ChangeAssets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeAssetsService
 * Description:
 * date: 2022/3/14 17:10
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeAssetsService extends BaseService<ChangeAssetsDao,ChangeAssets> {

    @Autowired
    ChangeAssetsMapper changeAssetsMapper;
}
