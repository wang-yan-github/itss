package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.KnowledgeApprovalDao;
import com.jsdc.itss.model.KnowledgeApproval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Service
@Transactional
public class KnowledgeApprovalService extends BaseService<KnowledgeApprovalDao, KnowledgeApproval> {


}
