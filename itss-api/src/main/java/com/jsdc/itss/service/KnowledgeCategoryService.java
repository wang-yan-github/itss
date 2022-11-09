package com.jsdc.itss.service;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.KnowledgeCategoryDao;
import com.jsdc.itss.mapper.KnowledgeCategoryMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Service
@Transactional
public class KnowledgeCategoryService extends BaseService<KnowledgeCategoryDao, KnowledgeCategory> {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private KnowledgeApproverService knowledgeApproverService;
    @Autowired
    private KnowledgeInfoService knowledgeInfoService;
    @Autowired
    private KnowledgeCategoryMapper knowledgeCategoryMapper;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    /**
     * 获取列表
     *
     * @return
     * @Author xujian
     */
    public List<KnowledgeCategory> getListByPid(Integer pid) {
        List<KnowledgeCategory> list = this.selectList(new QueryWrapper<KnowledgeCategory>().eq("parent_id", pid).eq("is_del", "0"));
        if (list != null && list.size() > 0) {
            for (KnowledgeCategory kc : list) {
                //绑定审核人信息
                List<KnowledgeApprover> knowledgeApprovers = knowledgeApproverService.selectList(Wrappers.<KnowledgeApprover>lambdaQuery()
                        .eq(KnowledgeApprover::getIs_del, String.valueOf(0))
                        .eq(KnowledgeApprover::getKnowledge_category_id, kc.getId()));
                if(!CollectionUtils.isEmpty(knowledgeApprovers)){
                    List<SysUser> sysUserList =sysUserService.selectList(Wrappers.<SysUser>lambdaQuery()
                            .in(SysUser::getId, knowledgeApprovers.stream().map(KnowledgeApprover::getApprover_id).collect(Collectors.toList())));
                    String str = "";
                    for (int i = 0 ;i < sysUserList.size(); i++ ){
                        if (null != sysUserList.get(i).getDepartment_id()){
                            SysDepartment sysDepartment =sysDepartmentService.selectById(sysUserList.get(i).getDepartment_id());
                            if (null != sysDepartment){
                                sysUserList.get(i).setDepartmentName(sysDepartment.getName());
                            }
                        }
                        if (i < sysUserList.size()-1){
                            str += sysUserList.get(i).getName();
                            str += ",";
                        }else {
                            str += sysUserList.get(i).getName();
                        }

                    }
                    kc.setApproverUsers(sysUserList);
                    kc.setApproves(str);
                }
                if (null != kc.getParent_id()){
                    KnowledgeCategory temp = this.selectById(kc.getParent_id());
                    if (null != temp ){
                        kc.setParent_name(temp.getName());
                    }
                }
                //递归获取子集
                kc.setChildren(this.getListByPid(kc.getId()));
            }
        }
        return list;
    }

    /**
     * 新增知识类别
     *
     * @param knowledgeCategory
     * @return
     * @Author xujian
     */
    public ResultInfo addKnowledgeCategory(KnowledgeCategory knowledgeCategory) {
        SysUser sysUser = sysUserService.getUser();
        if (knowledgeCategory.getId() != null) {
            //修改
            knowledgeCategory.setUpdate_user(sysUser.getId());
            knowledgeCategory.setUpdate_time(new Date());
            knowledgeCategory.updateById();
            //删除关联
            knowledgeApproverService.update(null, Wrappers.<KnowledgeApprover>lambdaUpdate()
                    .set(KnowledgeApprover::getIs_del, String.valueOf(1))
                    .eq(KnowledgeApprover::getKnowledge_category_id,knowledgeCategory.getId()));
//            knowledgeApproverService.delete(new QueryWrapper<KnowledgeApprover>().eq("knowledge_category_id", knowledgeCategory.getId()));
        } else {
            //新增
            knowledgeCategory.setParent_id(null == knowledgeCategory.getParent_id()?0:knowledgeCategory.getParent_id());
            knowledgeCategory.setCreate_user(sysUser.getId());
            knowledgeCategory.setCreate_time(new Date());
            knowledgeCategory.setIs_del(String.valueOf(0));
            knowledgeCategoryMapper.insert(knowledgeCategory);

        }

        KnowledgeApprover knowledgeApprover = new KnowledgeApprover();
        knowledgeApprover.setKnowledge_category_id(knowledgeCategory.getId());
        knowledgeApprover.setApprover_id(knowledgeCategory.getApprove());
        knowledgeApprover.setIs_del(String.valueOf(0));
        knowledgeApprover.setCreate_time(new Date());
        knowledgeApprover.setCreate_user(sysUser.getId());
        knowledgeApprover.insert();
//        if(CollectionUtils.isEmpty(knowledgeCategory.getApproverUsers())){
//            return ResultInfo.success();
//        }


//        for(SysUser member : knowledgeCategory.getApproverUsers()){
//            KnowledgeApprover knowledgeApprover = new KnowledgeApprover();
//            knowledgeApprover.setKnowledge_category_id(knowledgeCategory.getId());
//            knowledgeApprover.setApprover_id(member.getId());
//            knowledgeApprover.setIs_del(String.valueOf(0));
//            knowledgeApprover.setCreate_time(new Date());
//            knowledgeApprover.setCreate_user(sysUser.getId());
//            knowledgeApprover.insert();
//        }

//        if (knowledgeCategory.insertOrUpdate()) {
//            //新增审核人
//            List<String> approvers = knowledgeCategory.getApprover_ids();
//            for (String approver_id : approvers) {
//                if (StringUtils.isNotEmpty(approver_id)) {
//                    KnowledgeApprover knowledgeApprover = new KnowledgeApprover();
//                    knowledgeApprover.setKnowledge_category_id(knowledgeCategory.getId());
//                    knowledgeApprover.setApprover_id(Integer.parseInt(approver_id));
//                    knowledgeApprover.setIs_del("0");
//                    knowledgeApprover.setCreate_time(new Date());
//                    knowledgeApprover.setCreate_user(sysUser.getId());
//                    knowledgeApprover.insert();
//                }
//            }
//            return ResultInfo.success();
//        }
        return ResultInfo.success("操作成功！");
    }

    /**
     * 删除知识类别
     *
     * @param id
     * @return
     * @Author xujian
     */
    public ResultInfo delKnowledgeCategory(Integer id) {
        //判断是否被使用 已被使用则无法删除
        Long aLong = knowledgeInfoService.selectCount(new QueryWrapper<KnowledgeInfo>().eq("knowledge_category_id", id).eq("is_del", "0"));
        if (aLong > 0) {
            return ResultInfo.error("该类别已被使用，无法删除");
        }
        KnowledgeCategory knowledgeCategory = this.selectById(id);
        if(knowledgeCategory != null){
            //逻辑删除
//            knowledgeCategory.setIs_del("1");
//            knowledgeCategory.updateById();
            //物理删除
            knowledgeApproverService.delete(new QueryWrapper<KnowledgeApprover>().eq("knowledge_category_id", knowledgeCategory.getId()));
            knowledgeCategory.deleteById();
            return ResultInfo.success();
        }
        return ResultInfo.error("操作失败！");
    }
}
