package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.QuestionSource;
import com.jsdc.itss.service.QuestionSourceService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: QuestionSource
 * Description: 问题来源表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@RestController
@RequestMapping("questionSource")
public class QuestionSourceController {
    @Autowired
    private QuestionSourceService questionSourceService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "getPageList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getPageList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  QuestionSource bean) {
        PageInfo<QuestionSource> pageInfo = questionSourceService.getPageList(pageIndex, pageSize, bean);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 列表查询
     */
    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(QuestionSource bean) {
        List<QuestionSource> list = questionSourceService.getList(bean);
        return ResultInfo.success(list);
    }

    /**
     * 根据id获取对象
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id) {
        return ResultInfo.success(questionSourceService.selectById(id));
    }

    /**
     * 保存/更新
     */
    @RequestMapping(value = "saveOrUpd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveOrUpd(@RequestBody QuestionSource bean) {
        return questionSourceService.saveOrUpd(bean);
    }


    /**
     * 删除
     */
    @RequestMapping(value = "deleteSource.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteSource (Integer ids){
        return questionSourceService.deleteSource(ids);
    }

}
