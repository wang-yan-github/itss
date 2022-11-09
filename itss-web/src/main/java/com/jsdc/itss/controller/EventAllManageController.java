package com.jsdc.itss.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.service.EventBillingDesignateService;
import com.jsdc.itss.service.EventService;
import com.jsdc.itss.service.KnowledgeInfoService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: EventAllManage
 * Description: 全部事件管理
 *
 * @author zhangdequan
 */
@Controller
@RequestMapping("eventAll")
public class EventAllManageController {

    @Autowired
    private EventService eventService;
    @Autowired
    private KnowledgeInfoService knowledgeInfoService;
    @Autowired
    private EventBillingDesignateService designateService;
    /**
     * 查询全部事件列表
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Event event) {

//        if (null != event.getService_groups_type() && 1 == event.getService_groups_type()) {
//            //获取当前用户所在的服务群组
//            List<Integer> list = designateService.getServiceIdByCurrentUser();
//            event.setService_groups_list(list);
//        }
        PageInfo<Event> page = eventService.toList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }

    /**
     * 协查事件列表
     */
    @RequestMapping(value = "toXieChaList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toXieChaList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             EventVo event) {
        PageInfo<Event> page = eventService.toXieChaList(pageIndex, pageSize, event);

        return ResultInfo.success(page);
    }

    /**
     * 根据id查询关联
     */
    @RequestMapping(value = "/{id}/getById", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(@PathVariable("id") Integer id) {
        return ResultInfo.success(eventService.getById(id));
    }

    /**
     * 全部事件导出
     *
     * @param response
     */
    @RequestMapping(value = "exportEventExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exporEventExcel(HttpServletResponse response, Event event) {
        List<Event> list = eventService.toList(event);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("service_name", "服务群组");
        writer.addHeaderAlias("category_name", "工单类型");
        writer.addHeaderAlias("user_id_name", "用户");
        writer.addHeaderAlias("create_time", "开单人");
        writer.addHeaderAlias("create_time", "开单时间");
        writer.addHeaderAlias("handler_name", "当前操作人");
        writer.addHeaderAlias("ex_solve_time", "解决时间");
        writer.addHeaderAlias("statusName", "状态");
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
     * 全部事件 导入模板下载
     *
     * @param response
     */
    @RequestMapping(value = "exporTemplatesEventExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exporTemplatesEventExcel(HttpServletResponse response) {
        eventService.exportTemplatesEventExcel(response);
    }

    /**
     * 导入excel
     */
    @RequestMapping(value = "importExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo importExcel(@RequestParam("file") MultipartFile importFile) {
        return eventService.importExcel(importFile);
    }

    /**
     * 全部事件 - 重开操作
     */
    @RequestMapping(value = "toReopen.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toReopen(EventVo eventVo) {
        return eventService.reopenEvent(eventVo);
    }

    /**
     * 全部事件 - 修改操作
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toEdit(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> fileHandle) {
        Event event = JSON.parseObject(body, Event.class);
        return eventService.editEvent(event, file, fileHandle);
    }

    /**
     * 全部事件 - 删除操作
     */
    @RequestMapping(value = "toDelete.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toDelete(Integer id) {
        return eventService.deleteEvent(EventVo.builder().id(id).build());
    }

    /**
     * 全部事件 - 撤销操作
     */
    @RequestMapping(value = "toRevoke.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo toRevoke(Integer id) {
        return eventService.revokeEvent(EventVo.builder().id(id).build());
    }

    /**
     * 生成知识
     */
    @RequestMapping(value = "generateKnowledge.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo generateKnowledge(@RequestBody KnowledgeInfo knowledgeInfo) {
        return knowledgeInfoService.addKnowledgeInfo(knowledgeInfo, Collections.emptyList());
    }

    /**
     * 批量关单
     */
    @RequestMapping(value = "closingBatch.do", method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_GZT)
    public ResultInfo closingBatch(@RequestBody EventVo eventVo) {
        if (null == eventVo || CollectionUtils.isEmpty(eventVo.getIds())) {
            return ResultInfo.error("非法入参");
        }
        return eventService.closingBatch(eventVo);
    }


    //导出功能
    @RequestMapping(value = "eventAllManageExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void eventAllManageExcel(HttpServletResponse response, Event event) {
        List statusList = new ArrayList();
        statusList.add("1");
        statusList.add("2");
        event.setStatusList(statusList);
        PageInfo<Event> pageInfo = eventService.getAllByPage(event, null, null);
        List<Event> list = pageInfo.getList();
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("category_name", "工单类型");
        writer.addHeaderAlias("service_name", "服务群组");
        writer.addHeaderAlias("user_id_name", "用户");
        writer.addHeaderAlias("create_time", "开单时间");
        writer.addHeaderAlias("create_time", "开单人");
//        writer.addHeaderAlias("category_name", "开单类别");
        writer.addHeaderAlias("priority_name", "优先级");
        writer.addHeaderAlias("handler_name", "处理人");
        writer.addHeaderAlias("surplusMinutes", "剩余时间");
        writer.addHeaderAlias("statusName", "状态");
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
}
