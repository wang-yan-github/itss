package com.jsdc.itss.service;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysRoleDao;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysRoleService extends BaseService<SysRoleDao, SysRole> {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleAuthorityMapper sysRoleAuthorityMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper ;
    @Autowired
    private SysUserMapper sysUserMapper ;
    @Autowired
    private SysAuthorityMapper sysAuthorityMapper;
    @Autowired
    private  SysUserService sysUserService;


    /**
     * create by xuaolong
     * description: 角色管理管理分页查询
     * @param name
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultInfo getByPage( SysRole sysRole, int pageIndex, int pageSize){

        PageHelper.startPage(pageIndex, pageSize);

        List<SysRole> list = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                .eq(sysRole.getIs_incident_engineer()!=-1, SysRole::getIs_incident_engineer, sysRole.getIs_incident_engineer())
                .like(StringUtils.isNotBlank(sysRole.getName()), SysRole::getName, sysRole.getName())
                .eq(SysRole::getIs_del, "0")
        );
        for (SysRole role : list) {
            List<SysRoleAuthority> roleAuthorities = sysRoleAuthorityMapper.selectList(Wrappers.<SysRoleAuthority>lambdaQuery()
                    .eq(SysRoleAuthority::getRole_id, role.getId()));
            if (!CollectionUtils.isEmpty(roleAuthorities)) {
                List<Integer> authorityIds = roleAuthorities.stream().map(SysRoleAuthority::getAuthority_id).collect(Collectors.toList());
                role.setAuthorityIdList(authorityIds);
            }
        }
        return  ResultInfo.success(new PageInfo<>(list));
    }


    /**
     * create by xuaolong
     * description: 添加角色
     * @param sysRole
     * @return ResultInfo
     */
    public ResultInfo addSysRole(SysRole sysRole){
        sysRole.setCreate_user(sysUserService.getUser().getId());
        sysRole.setCreate_time(new Date());
        sysRole.setIs_del("0");
        sysRole.setId(null);
        sysRoleMapper.insert(sysRole);
        return  ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description: 根据ID查询角色信息
     * @param id
     * @return
     */
    public ResultInfo getOneRole(int id){
        SysRole sysRole = sysRoleMapper.selectById(id);
        return ResultInfo.success(sysRole);
    }


    /**
     * create by xuaolong
     * description: 修改角色
     * @param sysRole
     * @return ResultInfo
     */
    public ResultInfo updateSysRole(SysRole sysRole){
        sysRole.setUpdate_time(new Date());
        sysRole.setUpdate_user(sysUserService.getUser().getId());
        sysRoleMapper.updateById(sysRole);
        return  ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description: 删除角色
     * @param sysRole
     * @return ResultInfo
     */
    public ResultInfo deleteSysRole(int id){
        UpdateWrapper<SysRole> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("is_del","1");
        sysRoleMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description: 设置权限
     * @param id
     * @param authorityIdList
     * @return ResultInfo
     */
    public ResultInfo powerWithRole(Integer id , List<Integer> authorityIdList){
        //删除所有权限
        UpdateWrapper<SysRoleAuthority> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id",id);
        updateWrapper.set("is_del","1");
        sysRoleAuthorityMapper.update(null,updateWrapper);

        if(CollectionUtils.isEmpty(authorityIdList)){
            return ResultInfo.success();
        }
        sysRoleMapper.saveBatch(id, authorityIdList);
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description:查看角色对应的用户
     * @param roleId
     * @return
     */
    public ResultInfo selectRoleAndUser(Integer roleId){
        List<SysRoleUserVo> list = sysRoleMapper.getRoleAndUser(roleId);
        return ResultInfo.success(list);
    }

    /**
     * create by xuaolong
     * description:根据用户ID获取对应的角色
     * @param sysUser
     * @return
     */
    public ResultInfo getRoleIdByUser(SysUser sysUser){

        List<SysUserRole> roleList = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUser_id, sysUser.getId())
                .eq(SysUserRole::getIs_del, "0")
        );
        return ResultInfo.success(roleList);
    }

    /**
     * create by xuaolong
     * description:查看角色对应的用户
     * @param roleId
     * @return
     */
    public ResultInfo getUserByRoleId(Integer roleId) {
        SysRole role = sysRoleMapper.selectById(roleId);
        List<SysUserRole> roleList = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRole_id, roleId)
                .eq(SysUserRole::getIs_del, "0")
        );
        if(CollectionUtils.isEmpty(roleList)){
            return ResultInfo.success(role);
        }
        List<SysUser> users = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getIs_del, "0")
                .in(SysUser::getId, roleList.stream().map(SysUserRole::getUser_id).collect(Collectors.toList())));

        role.setUserIdList(users);
        return ResultInfo.success(role);
    }


    /**
     * create by xuaolong
     * description:添加角色对应的用户
     * @param roleId
     * @param userIdList
     * @return
     */
    public ResultInfo  insertRoleAndUser(Integer roleId , List<Integer> userIdList ){
        for (Integer temp : userIdList){
            SysUserRole sysUserRole =new SysUserRole();
            sysUserRole.setRole_id(roleId);
            sysUserRole.setUser_id(temp);
            sysUserRole.setIs_del("0");
            sysUserRole.setCreate_user(sysUserService.getUser().getId());
            sysUserRole.setCreate_time(new Date());
            sysUserRoleMapper.insert(sysUserRole);
        }
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description:减少角色对应的用户
     * @param roleId
     * @param userIdList
     * @return
     */
    public ResultInfo  reduceRoleAndUser(Integer roleId , List<Integer> userIdList ){
        for (Integer temp : userIdList){
            UpdateWrapper<SysUserRole> updateWrapper =new UpdateWrapper<>();
            updateWrapper.eq("role_id",roleId);
            updateWrapper.eq("user_id",temp);
            updateWrapper.set("is_del","1");
            sysUserRoleMapper.update(null ,updateWrapper);
        }
        return ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description:查看角色对应权限
     * @param roleId
     * @return
     */
    public ResultInfo selectSysAuthority(int roleId){
        List <SysAuthority> list = sysRoleMapper.getSysAuthority(roleId);
        List<SysAuthority> result =new ArrayList<>();
        for (int i = 0 ; i< list.size();i++){
            SysAuthority sysAuthority =list.get(i);
            if (sysAuthority.getParent_id()==-1){
                result.add(sysAuthority);
                setChildList(sysAuthority,list);
            }
        }
        return ResultInfo.success(result);
    }

    private void setChildList(SysAuthority sysAuthority, List<SysAuthority> sysAuthorityList){
        for(SysAuthority temp:sysAuthorityList){
            if(temp.getParent_id()==sysAuthority.getId()){
                sysAuthority.getChildren().add(temp);
                setChildList(temp,sysAuthorityList);
            }
        }
    }



    /**
     * description:角色导出excel
     *
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporRoleExcel(HttpServletResponse response) {
        List<ExportRoleWithUserVo> list = sysRoleMapper.exportRoleWithUser();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("id", "角色ID");
        writer.addHeaderAlias("name", "角色名称");
        writer.addHeaderAlias("user_name", "用户名");
        writer.addHeaderAlias("real_name", "姓名");
        writer.addHeaderAlias("mailbox", "Email");
        writer.addHeaderAlias("company_name", "公司");
        writer.addHeaderAlias("department_name", "部门");
        writer.addHeaderAlias("status_name", "状态");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }


    /**
     * description:角色权限导出excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */

    public ResultInfo exporRoleAuthorityExcel(HttpServletResponse response){
        QueryWrapper<SysAuthority> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<SysAuthority> list = sysAuthorityMapper.selectList(queryWrapper);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("name", "名称");
        writer.addHeaderAlias("authority_name", "权限名称");
        writer.addHeaderAlias("path", "功能路径");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);




    }

    /**
     * 根据知识查询范围获取角色集合
     * @param knowledgeRangeId
     * @return
     */
    public List<SysRole> getByKnowRangeId(Integer knowledgeRangeId){
       return sysRoleMapper.getByKnowRangeId(knowledgeRangeId);
    }

    public List<Integer> getAuthorityByRole(Integer id){
        return sysRoleMapper.getAuthorityByRole(id);
    }

}
