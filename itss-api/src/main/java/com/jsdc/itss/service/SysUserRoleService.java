package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.SysUserRoleDao;
import com.jsdc.itss.model.SysUserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysUserRoleService extends BaseService<SysUserRoleDao, SysUserRole> {

    /**
     * @param userId:
     * @return List
     * @author wp
     * @description 根据用户id获取所有的角色id
     * @date 2022/9/28 9:03
     */
    public List<Integer> getRoleIdsByUser(SysUserRole userRole){
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUser_id, userRole.getUser_id());
        wrapper.eq(SysUserRole::getIs_del, GlobalData.ISDEL_NO);
        List<SysUserRole> list = selectList(wrapper);
        List<Integer> roleIds = list.stream().map(x -> x.getRole_id()).collect(Collectors.toList());
        return roleIds;
    }

}
