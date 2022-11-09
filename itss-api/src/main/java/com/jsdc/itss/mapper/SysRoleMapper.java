package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysRoleDao;
import com.jsdc.itss.model.SysAuthority;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.vo.ExportRoleAuthorityVo;
import com.jsdc.itss.vo.ExportRoleWithUserVo;
import com.jsdc.itss.vo.SysRoleUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @SelectProvider(method = "getSysAuthority",type = SysRoleDao.class)
    List<SysAuthority> getSysAuthority(int roleId);

    @SelectProvider(method = "getByKnowRangeId",type = SysRoleDao.class)
    List<SysRole> getByKnowRangeId(int knowledgeRangeId);

    @SelectProvider(method = "getRoleAndUser",type = SysRoleDao.class)
    List<SysRoleUserVo> getRoleAndUser(int roleId);

    @SelectProvider(method = "exportRoleWithUser",type = SysRoleDao.class)
    List<ExportRoleWithUserVo> exportRoleWithUser();

    @SelectProvider(method = "exportRoleAuthority",type = SysRoleDao.class)
    List<ExportRoleAuthorityVo> exportRoleAuthority();

    @SelectProvider(method = "getAuthorityByRole",type = SysRoleDao.class)
    List<Integer> getAuthorityByRole(Integer id);

    @SelectProvider(method = "saveBatch", type = SysRoleDao.class)
    void saveBatch(Integer id , List<Integer> authorityIdList);

}
