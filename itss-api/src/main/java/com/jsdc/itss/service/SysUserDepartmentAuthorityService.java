package com.jsdc.itss.service;


import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysUserDepartmentAuthorityDao;
import com.jsdc.itss.model.SysUserDepartmentAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysUserDepartmentAuthorityService  extends BaseService<SysUserDepartmentAuthorityDao, SysUserDepartmentAuthority> {


}
