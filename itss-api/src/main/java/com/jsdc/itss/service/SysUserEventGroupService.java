package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysUserDepartmentAuthorityDao;
import com.jsdc.itss.dao.SysUserEventGroupDao;
import com.jsdc.itss.model.SysUserDepartmentAuthority;
import com.jsdc.itss.model.SysUserEventGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@SuppressWarnings("ALL")
public class SysUserEventGroupService  extends BaseService<SysUserEventGroupDao, SysUserEventGroup> {
}
