package com.jsdc.itss.controller;

import com.jsdc.itss.model.SysUserRole;
import com.jsdc.itss.service.SysUserRoleService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: itss
 * @className: SysUserRoleController
 * @author: wp
 * @description:
 * @date: 2022/9/28 9:04
 */
@RestController
@RequestMapping("userRole")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * 根据用户id获取所有的角色id
     * @param userRole
     * @return
     */
    @RequestMapping(value = "getRoleIdsByUser.do")
    public ResultInfo getRoleIdsByUser(@RequestBody SysUserRole userRole){
        return ResultInfo.success(userRoleService.getRoleIdsByUser(userRole));
    }
}
