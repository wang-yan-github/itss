package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeNodesDao;
import com.jsdc.itss.mapper.ChangeNodesMapper;
import com.jsdc.itss.model.ChangeNodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ClassName: ChangeNodesService
 * Description: 变更节点
 * date: 2022/3/14 16:16
 *
 * @author bn
 */
@Transactional
@Service
public class ChangeNodesService extends BaseService<ChangeNodesDao,ChangeNodes> {

    @Autowired
    ChangeNodesMapper changeNodesMapper;
}
