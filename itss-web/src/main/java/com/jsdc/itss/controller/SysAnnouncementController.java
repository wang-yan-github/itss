package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysAnnouncement;
import com.jsdc.itss.service.SysAnnouncementService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 公告管理控制器
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Controller
@RequestMapping("/sysAnnouncement")
public class SysAnnouncementController extends BaseController {

    @Autowired
    private SysAnnouncementService sysAnnouncementService;


    @RequestMapping(value = "toList.do")
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysAnnouncement beanParam) {
        PageInfo<SysAnnouncement> page = sysAnnouncementService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }
    /**
     * toNoticeList.do
     */
    @RequestMapping(value = "toNoticeList.do")
    @ResponseBody
    public ResultInfo toNoticeList(@RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysAnnouncement beanParam) {
        PageInfo<SysAnnouncement> page = sysAnnouncementService.toNoticeList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysAnnouncementService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysAnnouncement(@RequestBody SysAnnouncement sysAnnouncement){

        return sysAnnouncementService.addSysAnnouncement(sysAnnouncement);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysAnnouncement(@RequestBody SysAnnouncement sysAnnouncement){

        return sysAnnouncementService.editSysAnnouncement(sysAnnouncement);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "toDelete.do")
    @ResponseBody
    public ResultInfo deleteSysAnnouncement(Integer id){
        return sysAnnouncementService.deleteSysAnnouncement(id);
    }
}
