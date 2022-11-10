package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.EventCategory;
import com.jsdc.itss.model.EventServiceManage;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.model.vo.EventCategoryVo;
import com.jsdc.itss.model.vo.EventServiceManageVo;
import com.jsdc.itss.service.EventCategoryService;
import com.jsdc.itss.service.EventServiceManageService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工单类别管理表
 * ClassName: AssetsCompanyController
 * Description:
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("eventCategory")
public class EventCategoryController {
    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 工单类别新增
     * Author wzn
     * Date 2022/3/8 8:38
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsCompany(@RequestBody EventCategoryVo eventCategoryVo) {
        QueryWrapper<EventCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0").eq("name",eventCategoryVo.getEventCategory().getName());
        Long count = eventCategoryService.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在！");
        }
        eventCategoryService.add(eventCategoryVo);
        return ResultInfo.success();
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo edit(@RequestBody EventCategoryVo eventCategoryVo) {
        QueryWrapper<EventCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0").eq("name",eventCategoryVo.getEventCategory().getName()).ne("id", eventCategoryVo.getEventCategory().getId());
        Long count = eventCategoryService.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已存在！");
        }
        eventCategoryService.edit(eventCategoryVo);
        return ResultInfo.success();
    }


    /**
     * 列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @author wh
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<EventCategory> publishCategoryInfoPageInfo = eventCategoryService.toList(pageIndex, pageSize);
        return ResultInfo.success(publishCategoryInfoPageInfo);
    }

    /**
     * description:递归删除类别
     */
    @RequestMapping(value = "del.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo del(int id) {
        return eventCategoryService.del(id);
    }

    /**
     * 详情信息
     * Author wzn
     * Date 2022/3/8 9:33
     */
    @RequestMapping(value = "info", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo info(Integer id) {
        return ResultInfo.success(eventCategoryService.info(id));
    }

    /**
     * 工单类别树形结构数据接口
     * Author wzn
     * Date 2022/3/23 22:31
     */
    @RequestMapping(value = "tree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult treeInfo(@RequestBody EventCategory eventCategory) {
        List<EventCategory> eventCategoryList = eventCategoryService.tree(eventCategory);
        return AjaxResult.success(eventCategoryList);
    }

}
