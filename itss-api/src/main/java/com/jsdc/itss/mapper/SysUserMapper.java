package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysUserDao;
import com.jsdc.itss.model.SysAuthority;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ExportSysUserRoleVo;
import com.jsdc.itss.vo.ExportSysUserVo;
import com.jsdc.itss.vo.SysUserPageVo;
import com.jsdc.itss.vo.UserDepartmentRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yanbin123
 * @since 2019-08-14
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @SelectProvider(method = "exporUserExcel",type = SysUserDao.class)
    List<ExportSysUserVo> exporUserExcel();

    @SelectProvider(method = "exporUserRoleExcel",type = SysUserDao.class)
    List<ExportSysUserRoleVo> exporUserRoleExcel();

    @SelectProvider(method = "getUserInfoByPage",type = SysUserDao.class)
    List<SysUserPageVo> getUserInfoByPage(SysUser sysUser);

    @SelectProvider(method = "getUserPage",type = SysUserDao.class)
    List<SysUserPageVo> getUserPage(SysUser sysUser);

    @SelectProvider(method = "getSysAuthority",type = SysUserDao.class)
    List<SysAuthority> getSysAuthority(int userId);

    @SelectProvider(method = "getSysAuthorityOther",type = SysUserDao.class)
    List<SysAuthority> getSysAuthorityOther(int roleId);

    @SelectProvider(method = "getUserDepartmentRole",type = SysUserDao.class)
    List<UserDepartmentRoleVo> getUserDepartmentRole();

}
