package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xujian
 * @create 2022-03-08 12:19:43
 */
@RestController
@RequestMapping("/knowledgeConfig")
public class KnowledgeConfigController extends BaseController {

    //*****************************知识类别 begin************************************
    @Autowired
    private KnowledgeCategoryService knowledgeCategoryService;
    @Autowired
    private KnowledgeRangeService knowledgeRangeService;
    @Autowired
    private KnowledgeApproverService knowledgeApproverService;
    @Autowired
    private KnowledgeConfigService knowledgeConfigService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取知识类别列表
     *
     * @return
     * @Author xujian
     */
    @PostMapping("/category/getList")
    public ResultInfo getList() {
        List<KnowledgeCategory> list = knowledgeCategoryService.getListByPid(0);
        return ResultInfo.success(list);
    }

    /**
     * 获取知识类别详情
     *
     * @return
     * @Author xujian
     */
    @RequestMapping("/category/getDetail")
    public ResultInfo getDetail(Integer id) {
        JSONObject jsonObject = new JSONObject();
        KnowledgeCategory knowledgeCategory = knowledgeCategoryService.selectById(id);
        if (notEmpty(knowledgeCategory)) {
            if (notEmpty(knowledgeCategory.getParent_id()) && knowledgeCategory.getParent_id() > 0) {
                KnowledgeCategory knowledgeCategory1 = knowledgeCategoryService.selectById(knowledgeCategory.getParent_id());
                if (notEmpty(knowledgeCategory1)) {
                    knowledgeCategory.setParent_name(knowledgeCategory1.getName());
                }
            }
            jsonObject.put("knowledgeCategory", knowledgeCategory);
            //查询审核人
            List<KnowledgeApprover> knowledgeApprovers = knowledgeApproverService.selectList(new QueryWrapper<KnowledgeApprover>().eq("knowledge_category_id", knowledgeCategory.getId()).eq("is_del", "0"));

            if (!CollectionUtils.isEmpty(knowledgeApprovers)){
                Integer userId = knowledgeApprovers.get(0).getApprover_id();
                SysUser sysUser = sysUserService.selectById(userId);
                if (null != sysUser){
                    knowledgeCategory.setApprove(knowledgeApprovers.get(0).getApprover_id());
                    knowledgeCategory.setApproveName(sysUser.getName());
                }
            }


//            List<Integer> ids = knowledgeApprovers.stream().map(KnowledgeApprover::getApprover_id).collect(Collectors.toList());
//            SysUser sysUser = new SysUser();
//            sysUser.setIds(ids);
//            jsonObject.put("knowledgeApprovers", sysUserService.getList(sysUser));
            return ResultInfo.success(jsonObject);
        }
        return ResultInfo.error("无此记录！");
    }

    /**
     * 新增知识类别
     *
     * @return
     * @Author xujian
     */
    @PostMapping("/category/add")
    public ResultInfo addKnowledgeCategory(@RequestBody KnowledgeCategory knowledgeCategory) {
        ResultInfo resultInfo = knowledgeCategoryService.addKnowledgeCategory(knowledgeCategory);
        return resultInfo;
    }

    /**
     * 删除知识类别
     *
     * @return
     * @Author xujian
     */
    @RequestMapping("/category/del")
    public ResultInfo delKnowledgeCategory(Integer id) {
        ResultInfo resultInfo = knowledgeCategoryService.delKnowledgeCategory(id);
        return resultInfo;
    }

    //*****************************知识类别 end************************************

    //*****************************知识查询范围 begin************************************

    /**
     * 获取查询范围
     *
     * @return
     * @Author xujian
     */
    @RequestMapping("/knowledgeRange/getList")
    public ResultInfo getRangeList(@RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String isUse) {
        return knowledgeRangeService.getList(pageNo, pageSize, name, isUse);
    }

    /**
     * 获取查询范围详情
     *
     * @return
     * @Author xujian
     */
    @RequestMapping("/knowledgeRange/getDetail")
    public ResultInfo getRangeDetail(Integer id) {
        return knowledgeRangeService.getDetail(id);
    }

    /**
     * 新增查询范围
     *
     * @return
     * @Author xujian
     */
    @PostMapping("/knowledgeRange/add")
    public ResultInfo addKnowledgeRange(@RequestBody KnowledgeRange knowledgeRange) {
        ResultInfo resultInfo = knowledgeRangeService.addKnowledgeRange(knowledgeRange);
        return resultInfo;
    }

    /**
     * 删除查询范围
     *
     * @return
     * @Author xujian
     */
    @RequestMapping("/knowledgeRange/del")
    public ResultInfo delKnowledgeRange(Integer id) {
        ResultInfo resultInfo = knowledgeRangeService.delKnowledgeRange(id);
        return resultInfo;
    }

    //*****************************知识查询范围 end************************************
    //*****************************知识配置 begin************************************

    /**
     * 获取知识配置详情
     *
     * @return
     * @Author xujian
     */
    @PostMapping("/knowledgeConfig/getDetail")
    public ResultInfo getConfigDetail() {
        return knowledgeConfigService.getDetail();
    }

    /**
     * 修改知识配置详情
     *
     * @return
     * @Author xujian
     */
    @PostMapping("/knowledgeConfig/addOrUpdate")
    public ResultInfo addOrUpdate(@RequestBody KnowledgeConfig knowledgeConfig) {
        return knowledgeConfigService.addOrUpdate(knowledgeConfig);
    }
    //*****************************知识配置 end************************************
}
