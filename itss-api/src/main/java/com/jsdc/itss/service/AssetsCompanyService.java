package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsCompanyDao;
import com.jsdc.itss.mapper.AssetsCompanyMapper;
import com.jsdc.itss.model.AssetsCompany;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class AssetsCompanyService extends BaseService<AssetsCompanyDao, AssetsCompany> {

    @Autowired
    private AssetsCompanyMapper assetsCompanyMapper;
    @Autowired
    private AssetsCompanyDao assetsCompanyDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     *  购置公司展示数据
     * @param pageIndex
     * @param pageSize
     * @param assetsCompany
     * @return
     */
    public PageInfo<AssetsCompany> toList(Integer pageIndex, Integer pageSize, AssetsCompany assetsCompany) {
        PageHelper.startPage(pageIndex,pageSize, "sort asc");
        List<AssetsCompany> assetsCompanies = selectList(Wrappers.<AssetsCompany>lambdaQuery()
                .like(StringUtils.isNotEmpty(assetsCompany.getCompany_name()),AssetsCompany::getCompany_name,assetsCompany.getCompany_name())
                .eq(AssetsCompany::getIs_del, "0")

        );
        return new PageInfo<>(assetsCompanies);
    }

    /**
     *  购置公司ID查询
     * @param id
     * @return
     */
    public ResultInfo getById(Integer id) {
        return ResultInfo.success(selectById(id));
    }

    /**
     *  添加购置公司
     * @param assetsCompany
     * @return
     */
    public ResultInfo addAssetsCompany(AssetsCompany assetsCompany) {
        QueryWrapper<AssetsCompany> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("company_name",assetsCompany.getCompany_name());
        Long count = assetsCompanyMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在");
        }
        // 删除状态
        assetsCompany.setIs_del(String.valueOf(0));
        // 创建时间
        assetsCompany.setCreate_time(new Date());
        // 创建者
        assetsCompany.setCreate_user(sysUserService.getUser().getId());
        insert(assetsCompany);
        return ResultInfo.success();
    }

    /**
     *  编辑购置公司
     * @param assetsCompany
     * @return
     */
    public ResultInfo editAssetsCompany(AssetsCompany assetsCompany) {
        QueryWrapper<AssetsCompany> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("company_name",assetsCompany.getCompany_name()).ne("id",assetsCompany.getId());
        Long count = assetsCompanyMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在");
        }
        // 修改者
        assetsCompany.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsCompany.setUpdate_time(new Date());
        updateById(assetsCompany);
        return ResultInfo.success();
    }

    /**
     * 查询所有公司
     * @return
     */
    public List<AssetsCompany> getAll(){
        LambdaQueryWrapper<AssetsCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetsCompany::getIs_del, GlobalData.ISDEL_NO);
        wrapper.eq(AssetsCompany::getIs_enable, GlobalData.IS_ENABLE_YES);
        return selectList(wrapper);
    }

}
