package com.jsdc.itss.controller;

import com.jsdc.itss.model.SysAuthority;
import com.jsdc.itss.service.SysAuthorityService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sysauthority")
public class SysAuthorityController {
    @Autowired
    private SysAuthorityService sysAuthorityService;

    /**
     * 作者：xuaolong
     * 描述：权限表树状查询
     * @return
     */
    @RequestMapping("getSysAuthorityByTree.do")
    public ResultInfo getSysAuthorityByTree(@RequestBody SysAuthority sysAuthority){
        return sysAuthorityService.getSysAuthorityByTree(sysAuthority);
    }
}
