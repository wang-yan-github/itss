package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;

import com.jsdc.itss.dao.SysRoleAuthorityDao;
import com.jsdc.itss.mapper.SysRoleAuthorityMapper;

import com.jsdc.itss.model.SysRoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysRoleAuthorityService extends BaseService<SysRoleAuthorityDao, SysRoleAuthority> {

    @Autowired
    private SysRoleAuthorityMapper sysRoleAuthorityMapper;
}
