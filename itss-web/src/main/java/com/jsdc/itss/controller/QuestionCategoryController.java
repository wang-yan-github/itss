package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.QuestionCategory;
import com.jsdc.itss.service.QuestionCategoryService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: QuestionCategory
 * Description: 问题类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@RestController
@RequestMapping("questionCategory")
public class QuestionCategoryController {
    @Autowired
    private QuestionCategoryService questionCategoryService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "getPageList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize, QuestionCategory bean) {
        PageInfo<QuestionCategory> pageInfo = questionCategoryService.getPageList(pageIndex, pageSize, bean);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 获取父级类别
     * 树状图
     */
    @RequestMapping(value = "getParentList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getParentList(@RequestBody QuestionCategory questionCategory) {
        List<QuestionCategory> list = questionCategoryService.getTreeMenu(questionCategory);
        return ResultInfo.success(list);
    }

    /**
     * 根据id获取对象
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    public ResultInfo getById(Integer ids) {
        return ResultInfo.success(questionCategoryService.getOneInfo(ids));
    }

    /**
     * 保存/更新
     */
    @RequestMapping(value = "saveOrUpd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveOrUpd(@RequestBody QuestionCategory bean) {
        return questionCategoryService.saveOrUpd(bean);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "deleteCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteCategory(Integer ids){
        return questionCategoryService.deleteCategory(ids);
    }
}
