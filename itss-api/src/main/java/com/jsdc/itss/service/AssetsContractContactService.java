package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsContractContactDao;
import com.jsdc.itss.mapper.AssetsContractContactMapper;
import com.jsdc.itss.mapper.SysDepartmentMapper;
import com.jsdc.itss.model.AssetsContractContact;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Service
@Transactional
public class AssetsContractContactService extends BaseService<AssetsContractContactDao, AssetsContractContact> {

    @Autowired
    private AssetsContractContactMapper assetsContractContactMapper;
    @Autowired
    private AssetsContractContactDao assetsContractContactDao;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysCompanyService companyService;

    public PageInfo<AssetsContractContact> toList(Integer pageIndex, Integer pageSize, AssetsContractContact beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        Integer companyId = null;
        if(null != beanParam && null != beanParam.getDeptId()){
            SysDepartment sysDepartment = sysDepartmentMapper.selectById(beanParam.getDeptId());
            if(null != sysDepartment){
                companyId = sysDepartment.getCompany_id();
            }

        }
        List<AssetsContractContact> assetsContractContactVos = assetsContractContactMapper.selectList(Wrappers.<AssetsContractContact>lambdaQuery()
                .like(StringUtils.isNotBlank(beanParam.getContact_name()), AssetsContractContact::getContact_name, beanParam.getContact_name())
                .like(StringUtils.isNotBlank(beanParam.getPhone()), AssetsContractContact::getPhone, beanParam.getPhone())
                .like(StringUtils.isNotBlank(beanParam.getEmail()), AssetsContractContact::getEmail, beanParam.getEmail())
                .eq(null != beanParam.getIs_enable(), AssetsContractContact::getIs_enable, beanParam.getIs_enable())
                .eq(AssetsContractContact::getIs_del,"0")
                .eq(null != companyId, AssetsContractContact::getCompany_id, companyId)
        );

        for (AssetsContractContact assetsContractContact : assetsContractContactVos) {
            SysDepartment dept = sysDepartmentMapper.selectById(assetsContractContact.getDept_id());
            SysCompany company = companyService.selectById(assetsContractContact.getCompany_id());
            assetsContractContact.setCompany_name(company.getName());
            assetsContractContact.setDeptName(null == dept?"":dept.getName());
        }

        PageInfo<AssetsContractContact> page = new PageInfo<>(assetsContractContactVos);

        return page;
    }

    public List<AssetsContractContact> getList(AssetsContractContact beanParam) {
        List<AssetsContractContact> assetsContractContactVos = assetsContractContactMapper.toList(beanParam);
        return assetsContractContactVos;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsContractContact> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsContractContact assetsContractContact = selectOne(queryWrapper);
        return ResultInfo.success(assetsContractContact);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsContractContact(AssetsContractContact bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editAssetsContractContact(AssetsContractContact bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }}
