package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.model.SysUserRole;
import com.jsdc.itss.service.SysRoleService;
import com.jsdc.itss.service.SysUserRoleService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysRoleController
 * Description:
 *
 * @author xuaolong
 */
@RestController
@RequestMapping("sysrole")
public class SysRoleController {


    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * create by xuaolong
     * description: 角色管理管理分页查询

     * @param pageIndex
     * @param pageSize
     * @return ResultInfo
     */
    @RequestMapping("getPage.do")
    @ResponseBody
    public ResultInfo getPage( SysRole sysRole ,
                               @RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        sysRole.setIs_incident_engineer(null == sysRole.getIs_incident_engineer() ? (short)-1 : sysRole.getIs_incident_engineer());

        return sysRoleService.getByPage(sysRole, pageIndex, pageSize);
    }

    @RequestMapping("selectAllRole.do")
    public ResultInfo selectAllRole(){
        QueryWrapper<SysRole> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
            return ResultInfo.success(sysRoleService.selectList(queryWrapper));
    }

    /**
     * create by xuaolong
     * description: 新增角色
     * @param sysRole
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("save.do")
    public ResultInfo save(@RequestBody SysRole sysRole) {
        return sysRoleService.addSysRole(sysRole);
    }


    /**
     * create by xuaolong
     * description: 根据ID查询角色信息
     * @param id
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("getOne.do")
    @ResponseBody
    public ResultInfo getOne(int id) {
        return sysRoleService.getOneRole(id);
    }

    /**
     * create by xuaolong
     * description: 修改角色
     * @param sysRole
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("update.do")
    public ResultInfo update(@RequestBody SysRole sysRole) {
        return sysRoleService.updateSysRole(sysRole);
    }


    /**
     * create by xuaolong
     * description: 删除角色信息
     * @param ids
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("deleteSysRole.do")
    public ResultInfo deleteSysRole( int ids) {
        return sysRoleService.deleteSysRole(ids);
    }

    /**
     * create by xuaolong
     * description: 设置权限
     * @param
     * @param
     * @return ResultInfo
     */
    @RequestMapping("powerSysRole.do")
    public  ResultInfo powerSysRole(@RequestBody SysRole sysRole){
        List<Integer> authorityIdList =sysRole.getAuthorityIdList();
        Integer id =sysRole.getId();
        return sysRoleService.powerWithRole(id, authorityIdList);
    }


    /**
     * create by xuaolong
     * description:查看角色对应的用户
     * @param roleId
     * @return
     */
    @RequestMapping("getRoleAndUser.do")
    @ResponseBody
    public ResultInfo getRoleAndUser(Integer roleId){
        return  sysRoleService.selectRoleAndUser(roleId);
    }

    /**
     * create by xuaolong
     * description:查看角色对应的用户
     * @param roleId
     * @return
     */
    @RequestMapping("getUserByRoleId.do")
    @ResponseBody
    public ResultInfo getUserByRoleId(Integer roleId){
        return  sysRoleService.getUserByRoleId(roleId);
    }


    /**
     * create by xuaolong
     * description:添加角色对应的用户
     * @param
     * @param
     * @return
     */
    @RequestMapping("addRoleAndUser.do")
    public ResultInfo  addRoleAndUser(@RequestBody SysRole sysRole ){
        Integer roleId= sysRole.getId();
        List<SysUser> sysUsers = sysRole.getUserIdList();
        //删除的用户id
        List<Integer> userDeletes = sysRole.getUserDeletes();
        if (null != userDeletes && userDeletes.size() > 0) {
            userDeletes.forEach(userId->{
                List<SysUserRole> list = sysUserRoleService.selectList(new QueryWrapper<SysUserRole>().eq("user_id", userId).eq("role_id", roleId));
                list.forEach(b->{
                    sysUserRoleService.deleteById(b.getId());
                });
            });
        }
        List<Integer> userIdList = new ArrayList<>();
        if (null != sysUsers && sysUsers.size() > 0) {
            for (SysUser temp : sysUsers) {
                Integer id = temp.getId();
                if (id != null) {
                    userIdList.add(id);
                }
            }
        }
        return sysRoleService.insertRoleAndUser(roleId,userIdList);
    }



    /**
     * create by xuaolong
     * description:删除角色对应的用户
     * @return
     */
    @RequestMapping("deleteRoleAndUser.do")
    public ResultInfo  deleteRoleAndUser(@RequestBody SysRole sysRole ){
        Integer roleId= sysRole.getId();
        List<SysUser> sysUsers = sysRole.getUserIdList();
        List<Integer> userIdList = new ArrayList<>();
        for (SysUser temp : sysUsers){
            Integer id = temp .getId();
            if (id != null){
                userIdList.add(id);
            }
        }
        return   sysRoleService.reduceRoleAndUser(roleId, userIdList);
    }

    /**
     * create by xuaolong
     * description:获取角色对应的权限
     * @param roleId
     * @return
     */
    @RequestMapping("getSysAuthority.do")
    @ResponseBody
    public ResultInfo getSysAuthority(Integer roleId){
        if (null == roleId){
            return ResultInfo.success();
        }
        return sysRoleService.selectSysAuthority(roleId);
    }


    /**
     * create by xuaolong
     * description:角色导出excel
     * @param response
     * @return
     */
    @PostMapping("exporRoleExcel.do")
    public void exporRoleExcel(HttpServletResponse response){
        sysRoleService.exporRoleExcel(response);
    }


    /**
     * description:角色权限导出excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @PostMapping("exporRoleAuthorityExcel.do")
    public void exporRoleAuthorityExcel(HttpServletResponse response){
        sysRoleService.exporRoleAuthorityExcel(response);
    }


    /**
     * 根据用户ID 获取对应的角色
     * @param sysUser
     * @return
     */
    @RequestMapping("getRoleIdByUser.do")
    public ResultInfo getRoleIdByUser( SysUser sysUser){
        return sysRoleService.getRoleIdByUser(sysUser);

    }





}
