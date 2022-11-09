package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.ChangeType;
import com.jsdc.itss.service.ChangeTypeService;
import com.jsdc.itss.vo.ChangeTypeVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ChangeType
 * Description: 变更类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@RestController
@RequestMapping("changeType")
public class ChangeTypeController {
    @Autowired
    private ChangeTypeService changeTypeService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize, ChangeType bean) {
        PageInfo<ChangeType> pageInfo = changeTypeService.getPageList(pageIndex, pageSize, bean);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 获取父级类别
     * 树状图
     */
    @RequestMapping(value = "getParentList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getParentList(@RequestBody ChangeType bean) {
        List<ChangeType> list = changeTypeService.getTreeMenu(bean);
        return ResultInfo.success(list);
    }

    /**
     *  全部数据
     * @param bean
     * @return
     */
    @RequestMapping(value = "getAll.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAll(ChangeType bean){
        List<ChangeType> list = changeTypeService.getAll(bean);
        return ResultInfo.success(list);
    }

    /**
     * 根据id获取对象
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    public ResultInfo getById(Integer ids) {
        return ResultInfo.success(changeTypeService.getOneInfoById(ids));
    }

    /**
     * 保存/更新
     */
    @RequestMapping(value = "saveOrUpd.do", method = RequestMethod.POST)
    public ResultInfo saveOrUpd(@RequestBody ChangeType bean) {
        return changeTypeService.saveOrUpd(bean);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "deleteChangeType.do", method = RequestMethod.POST)
    public ResultInfo deleteChangeType(Integer ids){
        return changeTypeService.deleteChangeType(ids);
    }

    /**
     * 根据Id查询各种审批信息
     */
    @RequestMapping(value = "getApproveName.do", method = RequestMethod.POST)
    public ResultInfo getApproveName(Integer id){
        ChangeTypeVo changeTypeVo = changeTypeService.selectPeople(id);
        return ResultInfo.success(changeTypeVo);
    }


}
