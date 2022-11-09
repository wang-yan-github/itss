package com.jsdc.itss.service;


import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysRoleDao;
import com.jsdc.itss.dao.SysUserConfigAuthorityDao;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.model.SysUserConfigAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysUserConfigAuthorityService  extends BaseService<SysUserConfigAuthorityDao, SysUserConfigAuthority> {


}
