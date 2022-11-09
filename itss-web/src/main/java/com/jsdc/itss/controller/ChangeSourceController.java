package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.ChangeSource;
import com.jsdc.itss.service.ChangeSourceService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ChangeSource
 * Description: 变更来源表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@RestController
@RequestMapping("changeSource")
public class ChangeSourceController {
    @Autowired
    private ChangeSourceService changeSourceService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "getPageList.do", method = RequestMethod.POST)
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             ChangeSource bean) {
        PageInfo<ChangeSource> pageInfo = changeSourceService.getPageList(pageIndex, pageSize, bean);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 列表查询
     */
    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(ChangeSource bean) {
        List<ChangeSource> list = changeSourceService.getList(bean);
        return ResultInfo.success(list);
    }

    /**
     * 根据id获取对象
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id) {
        return ResultInfo.success(changeSourceService.selectById(id));
    }

    /**
     * 保存/更新
     */
    @RequestMapping(value = "saveOrUpd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveOrUpd(@RequestBody ChangeSource bean) {
        return changeSourceService.saveOrUpd(bean);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "deleteSource.do", method = RequestMethod.POST)
    public ResultInfo deleteSource(Integer ids){
        return changeSourceService.deleteSource(ids);
    }

}
