package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.OperateNodeDao;
import com.jsdc.itss.model.OperateNode;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * @projectName: itss
 * @className: OperateNodeService
 * @author: wp
 * @description:
 * @date: 2022/7/7 9:50
 */
@Service
@Transactional
public class OperateNodeService extends BaseService<OperateNodeDao, OperateNode> {
}
