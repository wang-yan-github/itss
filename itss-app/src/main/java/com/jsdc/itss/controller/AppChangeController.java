package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.ChangeInfoService;
import com.jsdc.itss.service.ChangeSourceService;
import com.jsdc.itss.service.ChangeTypeService;
import com.jsdc.itss.service.SysDictService;
import com.jsdc.itss.vo.ChangeTypeVo;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/app/change")
public class AppChangeController {

    @Autowired
    private ChangeInfoService changeInfoService;
    @Autowired
    private ChangeTypeService changeTypeService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private ChangeSourceService changeSourceService;

    /**
     * 全部数据
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "getAll.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAll(ChangeType bean) {
        List<ChangeType> list = changeTypeService.getAll(bean);
        return ResultInfo.success(list);
    }

    /**
     * 我的变更 撤销
     */
    @RequestMapping(value = "toCancel.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toCancel(@RequestBody ChangeInfo changeInfo) {
        return changeInfoService.toCancel(changeInfo);
    }

    /**
     * 我的变更  变更管理 修改关联
     */
    @RequestMapping(value = "toEditRelevance.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toEditRelevance(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toEditRelevance(changeVo);
    }

    /**
     * 我的变更 变更管理  重开
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toEdit(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        ChangeVo changeVo = JSON.parseObject(body, ChangeVo.class);
        return changeInfoService.toEdit(changeVo, file);
    }

    /**
     * 我的变更 变更查看
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody ChangeVo changeVo) {
        changeVo.setData_flag("1");
        return changeInfoService.toList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 我的变更 变更代办 变更管理 添加备注
     */
    @RequestMapping(value = "toRemark.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toRemark(@RequestBody ChangeVo changeVo) {
        ChangeInfo info = changeInfoService.selectById(changeVo.getId());
        info.setId(changeVo.getId());
        info.setRemark(changeVo.getRemark());
        changeVo.setChangeInfo(info);
        return changeInfoService.toRemark(changeVo);
    }


    /**
     * 根据id查询对象
     * 获取单个change
     */
    @RequestMapping(value = "getChange.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getChange(@RequestBody ChangeInfo changeInfo) {
        return changeInfoService.getChange(changeInfo);
    }


    /**
     * 变更待办 审批
     */
    @RequestMapping(value = "toApprove.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toApprove(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toApprove(changeVo);
    }

    /**
     * 变更待办 制定方案
     */
    @RequestMapping(value = "toProgramme.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toProgrammeEdit(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toProgrammeEdit(changeVo);
    }

    /**
     * 变更待办 审核方案
     */
    @RequestMapping(value = "toProgrammeApprove.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toProgrammeApprove(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toProgrammeApprove(changeVo);
    }


    /**
     * 变更待办 已处理（筛选，展示已处理过的变更列表）
     */
    @RequestMapping(value = "toNeedList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toNeedList(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toNeedList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }


    /**
     * 变更代办 转单
     */
    @RequestMapping(value = "toTransOrder.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toTransOrder(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        ChangeVo changeVo = JSON.parseObject(body, ChangeVo.class);
        return changeInfoService.toTransOrder(changeVo, file);
    }

    /**
     * 变更代办 实施后评审
     */
    @RequestMapping(value = "toImplementApprove.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toImplementApprove(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toImplementApprove(changeVo);
    }

    /**
     * 变更代办 挂起 恢复 关单 变更管理 恢复
     */
    @RequestMapping(value = "toSuspend.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toSuspend(@RequestBody ChangeInfo changeInfo) {
        return changeInfoService.toSuspend(changeInfo);
    }


    /**
     * 变更代办 实施
     */
    @RequestMapping(value = "toImplement.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toImplement(@RequestBody ChangeVo changeVo) {
        changeVo.getChangeImplement().setStart_time(changeVo.getStart_time());
        changeVo.getChangeImplement().setEnd_time(changeVo.getEnd_time());
        return changeInfoService.toImplement(changeVo);
    }

    /**
     * 变更管理 转办
     */
    @RequestMapping(value = "toTransForm.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toTransForm(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toTransForm(changeVo);
    }

    /**
     * 变更管理 变更查看
     */
    @RequestMapping(value = "getPage.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getPage(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 变更管理 删除变更
     */
    @RequestMapping(value = "deleteChange.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteChange(Integer id) {
        ChangeInfo changeInfo = changeInfoService.selectById(id);
        changeInfo.setIs_del("1");
        return ResultInfo.success(changeInfoService.updateById(changeInfo));
    }

    @RequestMapping(value = "getDicts.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getDicts(){
        List<SysDict> sysDictList=sysDictService.getDicts("change_type");
        return ResultInfo.success(sysDictList);
    }

    /**
     * 列表查询
     */
    @RequestMapping(value = "getSourceList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getSourceList() {
        List<ChangeSource> list = changeSourceService.getList(new ChangeSource());
        return ResultInfo.success(list);
    }

    /**
     * 根据Id查询各种审批信息
     */
    @RequestMapping(value = "getApproveName.do", method = RequestMethod.POST)
    public ResultInfo getApproveName(Integer id){
        ChangeTypeVo changeTypeVo = changeTypeService.selectPeople(id);
        return ResultInfo.success(changeTypeVo);
    }

    @LogInfo(LogEnums.LOG_CHANGE)
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestBody ChangeVo  changeVo ) {

        return changeInfoService.toAdd(changeVo, null);
    }



}
