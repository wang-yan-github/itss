package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsClGroup;
import com.jsdc.itss.service.AssetsClGroupService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * CL分组管理控制器
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Controller
@RequestMapping("/assetsClGroup")
public class AssetsClGroupController extends BaseController {

    @Autowired
    private AssetsClGroupService assetsClGroupService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表查询
     * @author zdq
     * @date 2022/3/24
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsClGroup beanParam) {
        PageInfo<AssetsClGroup> page = assetsClGroupService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestBody AssetsClGroup bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }

        if(null != bean.getId() && null != assetsClGroupService.selectById(bean.getId())){

            return assetsClGroupService.editAssetsClGroup(bean);
        }
        return assetsClGroupService.addAssetsClGroup(bean);

    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsClGroup bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }
        int result = assetsClGroupService.update(null, Wrappers.<AssetsClGroup>lambdaUpdate()
                .set(AssetsClGroup::getIs_del, String.valueOf(1))
                .set(AssetsClGroup::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsClGroup::getUpdate_time, new Date())
                .eq(AssetsClGroup::getId, bean.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsClGroupService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsClGroup(AssetsClGroup assetsClGroup){

        return assetsClGroupService.addAssetsClGroup(assetsClGroup);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsClGroup(AssetsClGroup assetsClGroup){

        return assetsClGroupService.editAssetsClGroup(assetsClGroup);
    }

    /**
     * 获取CI分组数据
     * @return
     */
    @RequestMapping(value = "getAllList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllList() {
        return assetsClGroupService.getAllList(null);
    }


    /**
     * 概览
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "overviewList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo overviewList(AssetsClGroup beanParam) {

        return ResultInfo.success(assetsClGroupService.overviewList(beanParam));
    }
}
