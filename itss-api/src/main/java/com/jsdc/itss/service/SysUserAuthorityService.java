package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysUserAuthorityDao;
import com.jsdc.itss.mapper.SysUserAuthorityMapper;
import com.jsdc.itss.model.SysUserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysUserAuthorityService extends BaseService<SysUserAuthorityDao, SysUserAuthority> {

    @Autowired
    private SysUserAuthorityMapper sysUserAuthorityMapper;
}
