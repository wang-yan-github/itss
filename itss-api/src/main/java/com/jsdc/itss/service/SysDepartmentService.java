package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.SysDepartmentDao;
import com.jsdc.itss.dao.SysRoleDao;
import com.jsdc.itss.mapper.SysDepartmentMapper;
import com.jsdc.itss.model.EventServiceManage;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysDepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysDepartmentService extends BaseService<SysDepartmentDao, SysDepartment> {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysCompanyService sysCompanyService;
    @Autowired
    private EventServiceManageService eventServiceManageService;

    //存储要删除的ID集合
    private List<Integer> ids = new ArrayList<>();


    /**
     * create by xuaolong
     * description: 树状图展示部门数据
     *
     * @return resultList
     */
    public List<SysDepartmentVo> queryWithTree(SysDepartment sysDepartment1) {

        List<SysDepartmentVo> list = sysDepartmentMapper.getDepartmentInfo(sysDepartment1);
        for (SysDepartmentVo sysDepartmentVo : list) {
            if (null != sysDepartmentVo.getParent_id() && null != sysDepartmentMapper.selectById(sysDepartmentVo.getParent_id())) {
                sysDepartmentVo.setParent_name(sysDepartmentMapper.selectById(sysDepartmentVo.getParent_id()).getName());
            }
        }
        List<SysDepartmentVo> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysDepartmentVo sysDepartment = list.get(i);
            if (sysDepartment.getParent_id() == -1) {
                sysDepartment.setParentName(sysDepartment.getCompanyName());
                resultList.add(sysDepartment);
                setChildList(sysDepartment, list);
            }
        }
        return resultList;
    }

    private void setChildList(SysDepartmentVo sysDepartment, List<SysDepartmentVo> sysDepartmentList) {
        for (SysDepartmentVo temp : sysDepartmentList) {
            if (temp.getParent_id() == sysDepartment.getId()) {
                temp.setParentName(sysDepartment.getName());
                sysDepartment.getChildren().add(temp);
                setChildList(temp, sysDepartmentList);
            }
        }
    }


    /**
     * create by xuaolong
     * description: 新增部门
     *
     * @return ResultInfo
     */
    public ResultInfo addSysDepartment(SysDepartment sysDepartment) {
        if (sysDepartment.getParent_id() == null) {
            sysDepartment.setParent_id(-1);
        }
        sysDepartment.setCreate_time(new Date());
        sysDepartment.setCreate_user(sysUserService.getUser().getId());
        sysDepartment.setIs_del(GlobalData.ISDEL_NO);
        sysDepartmentMapper.insert(sysDepartment);
        return ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description: 修改部门
     *
     * @return ResultInfo
     */
    public ResultInfo updateSysDepartment(SysDepartment sysDepartment) {
        sysDepartment.setUpdate_time(new Date());
        sysDepartment.setUpdate_user(sysUserService.getUser().getId());
        sysDepartmentMapper.updateById(sysDepartment);
        return ResultInfo.success();
    }


    /**
     * create by xuaolong
     * description: 删除部门 并删除其下级部门
     *
     * @return ResultInfo
     */
    public ResultInfo deletedSysDepartment(Integer id) {
        ids.add(id);
        method1(id);
        for (Integer temp : ids) {
            UpdateWrapper<SysDepartment> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("is_del", "1");
            updateWrapper.eq("id", temp);
            sysDepartmentMapper.update(null, updateWrapper);
        }
        ids.clear();
        return ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description: 私有方法 服务递归删除 把部门的Id存储到ids集合
     *
     * @param id
     */
    private void method1(Integer id) {
        QueryWrapper<SysDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<SysDepartment> list = sysDepartmentMapper.selectList(queryWrapper);
        if (!list.isEmpty()) {
            for (SysDepartment temp : list) {
                ids.add(temp.getId());
                method1(temp.getId());
            }
        }
    }

    /**
     * create by xuaolong
     * description: 根据ID 查询部门信息
     *
     * @return id
     */
    public ResultInfo getOneSysDepartment(Integer id) {
        SysDepartment sysDepartment = sysDepartmentMapper.selectById(id);
        SysDepartment sysDepartment1 = sysDepartmentMapper.selectById(sysDepartment.getParent_id());
        if (Base.notEmpty(sysDepartment1)) {
            sysDepartment.setParent_name(sysDepartment1.getName());
        }
        if (Base.notEmpty(sysDepartment.getCompany_id())) {
            SysCompany sysCompany = sysCompanyService.selectById(sysDepartment.getCompany_id());
            if (Base.notEmpty(sysCompany)) {
                sysDepartment.setCompanyName(sysCompany.getName());
            }
        }
        if (Base.notEmpty(sysDepartment.getGroup_id())) {
            EventServiceManage eventServiceManage = eventServiceManageService.selectById(sysDepartment.getGroup_id());
            if (Base.notEmpty(eventServiceManage)) {
                sysDepartment.setGroupName(eventServiceManage.getName());
            }
        }
        return ResultInfo.success(sysDepartment);
    }

    /**
     * 根据知识查询范围获取部门集合
     *
     * @param knowledgeRangeId
     * @return
     */
    public List<SysDepartment> getByKnowRangeId(Integer knowledgeRangeId) {
        return sysDepartmentMapper.getByKnowRangeId(knowledgeRangeId);
    }

    /**
     * 查询所有部门
     *
     * @return
     */
    public List<SysDepartment> getAll() {
        List<SysDepartment> list = selectList(Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getIs_del, GlobalData.ISDEL_NO).eq(SysDepartment::getIs_use, GlobalData.IS_ENABLE_YES));
        return list;
    }


}
