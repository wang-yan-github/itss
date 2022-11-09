package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SlaEventFirst;
import com.jsdc.itss.model.SlaEventUrgent;
import com.jsdc.itss.service.SlaEventFirstService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SlaEventFirstContro
 * Description:
 * date: 2022/3/7 14:17
 *
 * @author bn
 */
@Controller
@RequestMapping("slaEventFirst")
public class SlaEventFirstController extends BaseController {

    @Autowired
    private SlaEventFirstService slaEventFirstService;

    /**
     * 列表查询
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             SlaEventFirst slaEventFirst) {
        PageInfo<SlaEventFirst> page = slaEventFirstService.toList(pageIndex, pageSize, slaEventFirst);
        return ResultInfo.success(page);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestBody SlaEventFirst slaEventFirst) {
        return slaEventFirstService.toAdd(slaEventFirst);
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(@RequestBody SlaEventFirst slaEventFirst) {
        return slaEventFirstService.toEdit(slaEventFirst);
    }

    /**
     * 查询所有事件紧急度
     */
    @RequestMapping(value = "getAllEventFirst.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllEventFirst() {
        QueryWrapper<SlaEventFirst> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<SlaEventFirst> list = slaEventFirstService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }
}
