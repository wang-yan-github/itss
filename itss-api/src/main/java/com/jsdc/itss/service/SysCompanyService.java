package com.jsdc.itss.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SysCompanyDao;
import com.jsdc.itss.dao.SysRoleDao;
import com.jsdc.itss.mapper.SysCompanyMapper;
import com.jsdc.itss.mapper.SysDepartmentMapper;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysCompanyVo;
import com.jsdc.itss.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysCompanyService extends BaseService<SysCompanyDao, SysCompany> {
    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;


    /**
     * create by xuaolong
     * description: 公司管理分页查询
     * @param sysCompany
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultInfo getPage(SysCompany sysCompany ,int pageIndex, int pageSize){
        PageHelper.startPage(pageIndex, pageSize);
        List<SysCompanyVo> list = sysCompanyMapper.getCompanyInfo(sysCompany);
        PageInfo<SysCompanyVo> pageInfo = new PageInfo<SysCompanyVo>(list);
        return  ResultInfo.success(pageInfo);
    }

    /**
     * create by xuaolong
     * description: 公司管理新增公司
     * @param sysCompany
     * @return
     */

    public ResultInfo addSysCompany(SysCompany sysCompany){
        sysCompany.setCreate_time(new Date());
        sysCompany.setCreate_user(sysUserService.getUser().getId());
        sysCompany.setIs_del(GlobalData.ISDEL_NO);
        sysCompanyMapper.insert(sysCompany);
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description: 公司管理修改公司信息
     * @param sysCompany
     * @return
     */
    public ResultInfo updateSysCompany(SysCompany sysCompany){
        sysCompanyMapper.updateById(sysCompany);
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description: 公司管理删除公司信息
     * @param id
     * @return
     */
    public ResultInfo deleteSysCompany(Integer id){
        sysCompanyMapper.deleteById(id);
        return ResultInfo.success();
    }


    /**
    *公司下拉数据接口
    * Author wzn
    * Date 2022/3/24 8:53
    */
    public List<SysCompany> upCompany(){
        QueryWrapper<SysCompany> queryWrapper = new QueryWrapper<>() ;
        return sysCompanyMapper.selectList(queryWrapper.eq("is_del", "0")) ;
    }

    /**
     * 根据公司得到公司下的所有用户
     */
    public List<SysUser> getUserByCompany(Integer companyId) {
        List<SysDepartment> departments = sysDepartmentMapper.selectList(Wrappers.<SysDepartment>lambdaQuery()
                .eq(SysDepartment::getCompany_id, companyId)
                .eq(SysDepartment::getIs_del, GlobalData.ISDEL_NO)
        );
        if (CollectionUtils.isEmpty(departments)) {
            return Collections.EMPTY_LIST;
        }
        // 得到部门id集合
        List<Integer> ids = departments.stream().map(SysDepartment::getId).collect(Collectors.toList());

        return sysUserService.selectList(Wrappers.<SysUser>lambdaQuery()
                .in(SysUser::getDepartment_id, ids)
                .eq(SysUser::getIs_del, GlobalData.ISDEL_NO)
        );
    }


}
