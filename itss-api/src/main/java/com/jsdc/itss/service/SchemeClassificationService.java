package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SchemeClassificationDao;
import com.jsdc.itss.dao.SysCompanyDao;
import com.jsdc.itss.mapper.SchemeClassificationMapper;
import com.jsdc.itss.mapper.SysCompanyMapper;
import com.jsdc.itss.model.EventServiceCategory;
import com.jsdc.itss.model.SchemeClassification;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SchemeClassificationService extends BaseService<SchemeClassificationDao, SchemeClassification> {
    @Autowired
    private SchemeClassificationMapper schemeClassificationMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 新增方案分类
     * Author wzn
     * Date 2022/3/9 10:22
     */
    public ResultInfo addSchemeClassification(SchemeClassification bean) {
        QueryWrapper<SchemeClassification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("name",bean.getName());
        Long count = schemeClassificationMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        bean.setIs_del("0");
        if (null == bean.getParent_id()) {
            bean.setParent_id(0);
        }
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        schemeClassificationMapper.insert(bean);
        return ResultInfo.success();
    }


    /**
     * 修改方案分类
     * Author wzn
     * Date 2022/3/9 10:22
     */
    public ResultInfo updateSchemeClassification(SchemeClassification bean) {
        QueryWrapper<SchemeClassification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("name",bean.getName()).ne("id",bean.getId());
        Long count = schemeClassificationMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        bean.setUpdate_time(new Date());
        bean.setUpdate_user(sysUserService.getUser().getId());
        schemeClassificationMapper.updateById(bean);
        return ResultInfo.success();
    }


    /**
     * 删除方案分类
     * Author wzn
     * Date 2022/3/9 10:23
     */
    public ResultInfo deleteSchemeClassification(Integer id) {
        SchemeClassification bean = new SchemeClassification();
        bean.setId(id);
        bean.setIs_del("1");
        bean.setUpdate_time(new Date());
        bean.setUpdate_user(sysUserService.getUser().getId());
        schemeClassificationMapper.updateById(bean);
        return ResultInfo.success();
    }


    /**
     * 方案分类树形结构接口
     * Author wzn
     * Date 2022/3/25 10:53
     */
    public List<SchemeClassification> getDetailTree() {
        QueryWrapper<SchemeClassification> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", "0");
        wrapper.orderByAsc("sort");
        List<SchemeClassification> fileCatalogs = schemeClassificationMapper.selectList(wrapper);
        List<SchemeClassification> lists = new ArrayList<>();
        for (SchemeClassification fileCatalog : fileCatalogs) {
            SchemeClassification vo = new SchemeClassification();
            BeanUtils.copyProperties(fileCatalog, vo);
            lists.add(vo);
        }
        List<SchemeClassification> tree = createTree(lists, 0);
        return tree;
    }

    /**
     * 递归建立树形结构 （算法）
     *
     * @param lists
     * @param pid
     * @return
     */
    private List<SchemeClassification> createTree(List<SchemeClassification> lists, int pid) {
        List<SchemeClassification> tree = new ArrayList<>();
        for (SchemeClassification catelog : lists) {
            if (catelog.getParent_id() == pid) {
                catelog.setChildren(createTree(lists, catelog.getId()));
                tree.add(catelog);
            }
        }
        return tree;
    }


    /**
     * 详情
     */
    public SchemeClassification getDetail(Integer id) {
        SchemeClassification bean = schemeClassificationMapper.selectById(id);
        if (bean.getParent_id() != 0) {
            bean.setParent_name(selectById(bean.getParent_id()).getName());
        }
        return bean;
    }
}
