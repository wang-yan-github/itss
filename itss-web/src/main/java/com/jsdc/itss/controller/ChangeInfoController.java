package com.jsdc.itss.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.ChangeInfo;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.vo.ChangesOf;
import com.jsdc.itss.service.ChangeInfoService;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.QuestionVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * ClassName: ChangeInfoController
 * Description: 变更信息
 * date: 2022/3/14 9:25
 *
 * @author bn
 */
@Controller
@RequestMapping("changeInfo")
public class ChangeInfoController extends BaseController {

    @Autowired
    private ChangeInfoService changeInfoService;

    /**
     * 变更待办列表
     */
    @RequestMapping(value = "toNeedList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toNeedList(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toNeedList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 审批
     */
    @RequestMapping(value = "toApprove.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toApprove(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toApprove(changeVo);
    }

    /**
     * 制定方案
     */
    @RequestMapping(value = "toProgramme.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toProgrammeEdit(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toProgrammeEdit(changeVo);
    }

    /**
     * 方案审核
     */
    @RequestMapping(value = "toProgrammeApprove.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toProgrammeApprove(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toProgrammeApprove(changeVo);
    }

    /**
     * 我的变更列表
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 变更管理
     * 分页查询
     */
    @RequestMapping(value = "getPage.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getPage(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 我参与的
     * 分页查询
     * 创建人、审批人、方案拟定人、方案审核人、实施人、实施后评审人、关单人其中包含我的数据
     */
    @RequestMapping(value = "getParticipatingPageList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getParticipatingPageList(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 转办
     */
    @RequestMapping(value = "toTransForm.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toTransForm(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toTransForm(changeVo);
    }

    /**
     * 转单
     */
    @RequestMapping(value = "toTransOrder.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toTransOrder(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        ChangeVo changeVo = JSON.parseObject(body, ChangeVo.class);
        return changeInfoService.toTransOrder(changeVo, file);
    }

    /**
     * 挂起/恢复
     */
    @RequestMapping(value = "toSuspend.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toSuspend(@RequestBody ChangeInfo changeInfo) {
        return changeInfoService.toSuspend(changeInfo);
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
     * 添加变更
     * {"change":{"changeInfo":{"title":"bn紧急变更基金","start_time":"2022-03-01 08:55","end_time":"2022-03-16 08:56","change_classify":"1","change_type":"1","change_source":"1","change_reason":"bn基金变更","current_environment":"大A","is_del":"0"},"changeProgrammeDraft":{"programme_user":1,"change_programme":"进749","fallback_plan":"20000股","risk_assessment":"发家致富","implement_user":"1","is_del":"0","status":"1"},"changeNodes":[{"time_nodes":"1","sort":"1","time_status":"0","is_del":"0"},{"time_nodes":"2","sort":"2","time_status":"1","approve_user":"1","is_del":"0"},{"time_nodes":"2","sort":"3","time_status":"1","approve_user":"2","is_del":"0"},{"time_nodes":"3","sort":"4","time_status":"1","is_del":"0"},{"time_nodes":"4","sort":"5","time_status":"1","is_del":"0"},
     * {"time_nodes":"5","sort":"6","time_status":"1","is_del":"0"},
     * {"time_nodes":"6","sort":"7","time_status":"1","is_del":"0"}],
     * "changeEvents":[{"event_id":1,"is_del":"0"},{"event_id":2,"is_del":"0"}],
     * "changeQuestions":[{"question_id":1,"is_del":"0"}]}}
     */
    @LogInfo(LogEnums.LOG_CHANGE)
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        ChangeVo changeVo = JSON.parseObject(body, ChangeVo.class);
        return changeInfoService.toAdd(changeVo, file);
    }

    /**
     * 整体编辑
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toEdit(@RequestParam String body, @RequestParam List<MultipartFile> file) {
        ChangeVo changeVo = JSON.parseObject(body, ChangeVo.class);
        return changeInfoService.toEdit(changeVo, file);
    }

    /**
     * 修改关联
     */
    @RequestMapping(value = "toEditRelevance.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toEditRelevance(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toEditRelevance(changeVo);
    }

    /**
     * 添加备注
     */
    @RequestMapping(value = "toRemark.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toRemark(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toRemark(changeVo);
    }

    /**
     * 方案实施
     */
    @RequestMapping(value = "toImplement.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toImplement(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toImplement(changeVo);
    }

    /**
     * 实施后评审
     * 方案实施审核
     */
    @RequestMapping(value = "toImplementApprove.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toImplementApprove(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toImplementApprove(changeVo);
    }

    /**
     * 撤销
     */
    @RequestMapping(value = "toCancel.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo toCancel(@RequestBody ChangeInfo changeInfo) {
        return changeInfoService.toCancel(changeInfo);
    }

    /**
     * 导入excel
     */
    @RequestMapping(value = "importExcel.do")
    @ResponseBody
    public ResultInfo importExcel(MultipartFile importFile) {
        return changeInfoService.importExcel(importFile);
    }

    /**
     * 删除功能
     */
    @RequestMapping(value = "deleteChange.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_CHANGE)
    public ResultInfo deleteChange(Integer id) {
        return changeInfoService.deleteChange(id);
    }

    /**
     * 删除功能
     */
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delete(@RequestBody Integer id) {
        ChangeInfo changeInfo = changeInfoService.selectById(id);
        changeInfo.setIs_del("1");
        return ResultInfo.success(changeInfoService.updateById(changeInfo));
    }

    /**
     * 我的变更 导出excel
     */
    @RequestMapping(value = "exportExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportExcel(HttpServletResponse response, ChangeVo changeVo) {
        changeVo.setData_flag(GlobalData.DATAFLAG_1);
        List<ChangeVo> list = changeInfoService.toList(changeVo);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("change_no", "变更单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("classify_name", "变更类别");
        writer.addHeaderAlias("type_name", "变更类型");
        writer.addHeaderAlias("start_time", "计划开始时间");
        writer.addHeaderAlias("approve_name", "审批人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("status_name", "状态");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 变更待办 导出excel
     */
    @RequestMapping(value = "exportNeedExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportNeedExcel(HttpServletResponse response, ChangeVo changeVo) {
        changeVo.setData_flag(GlobalData.DATAFLAG_1);
        List<ChangeVo> list = changeInfoService.toNeedList(changeVo);;
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("change_no", "变更单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("classify_name", "变更类别");
        writer.addHeaderAlias("type_name", "变更类型");
        writer.addHeaderAlias("start_time", "计划开始时间");
        writer.addHeaderAlias("approve_name", "审批人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("status_name", "状态");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 变更管理 导出excel
     */
    @RequestMapping(value = "exportManagerExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exportManagerExcel(HttpServletResponse response, ChangeVo changeVo) {
        changeVo.setData_flag(GlobalData.DATAFLAG_2);
        List<ChangeVo> list = changeInfoService.toList(changeVo);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("change_no", "变更单号");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("classify_name", "变更类别");
        writer.addHeaderAlias("type_name", "变更类型");
        writer.addHeaderAlias("start_time", "计划开始时间");
        writer.addHeaderAlias("end_time", "计划完成时间");
        writer.addHeaderAlias("create_user_name", "创建人");
        writer.addHeaderAlias("approve_name", "当前操作人");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("status_name", "状态");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 变更来源统计
     * Author wzn
     * Date 2022/11/2 15:32
     */
    @RequestMapping(value = "sourcesOfStatistics.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo sourcesOfStatistics(){

        return ResultInfo.success(changeInfoService.sourcesOfStatistics());
    }

    /**
     *  变更类别数量统计
     * Author wzn
     * Date 2022/11/2 15:32
     */
    @RequestMapping(value = "categoriesOfStatistical.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo categoriesOfStatistical(){

        return ResultInfo.success( changeInfoService.categoriesOfStatistical());
    }


    /**
     * 变更类型数量统计
     * Author wzn
     * Date 2022/11/2 15:34
     */
    @RequestMapping(value = "typesOfStatistical.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo typesOfStatistical(){
        List<ChangesOf> changesOfList = changeInfoService.typesOfStatistical() ;
        if(!CollectionUtils.isEmpty(changesOfList)){
            for(ChangesOf c:changesOfList){
                //更类型 1.紧急变更 2.一般变更 3.重大变更|紧急变更影响时间轴，影响流程
                if("1".equals(c.getName())){
                    c.setName("紧急变更");
                }else if("2".equals(c.getName())){
                    c.setName("一般变更");
                }else if("3".equals(c.getName())){
                    c.setName("重大变更");
                }
            }
        }
        return ResultInfo.success(changesOfList);
    }


}
