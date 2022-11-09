package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsType;
import com.jsdc.itss.model.SysBackgroundPic;
import com.jsdc.itss.model.SysBackgroundPicSetting;
import com.jsdc.itss.service.SysBackgroundPicService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 首页背景图管理控制器
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Controller
@RequestMapping("/sysBackgroundPic")
public class SysBackgroundPicController extends BaseController {

    @Autowired
    private SysBackgroundPicService sysBackgroundPicService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,SysBackgroundPic beanParam) {
        PageInfo<SysBackgroundPic> page = sysBackgroundPicService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return sysBackgroundPicService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysBackgroundPic(@RequestParam String body, MultipartFile file){
        SysBackgroundPic sysBackgroundPic = JSON.parseObject(body, SysBackgroundPic.class);
        return sysBackgroundPicService.addSysBackgroundPic(sysBackgroundPic,file);
    }

//    /**
//     *  编辑
//     */
//    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editSysBackgroundPic(SysBackgroundPic sysBackgroundPic){
//
//        return sysBackgroundPicService.editSysBackgroundPic(sysBackgroundPic);
//    }

    /**
     *  删除
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteSysBackgroundPic( Integer id ){

        return sysBackgroundPicService.deleteSysBackgroundPic(id);
    }

    /**
     * 展示配置项
     */
    @RequestMapping(value = "showSetting.do",method = RequestMethod.POST)
    @ResponseBody
    public  ResultInfo showSetting(){
        return  sysBackgroundPicService.showSetting();
    }

    /**
     * 修改配置项
     */
    @RequestMapping(value = "updateSetting.do",method = RequestMethod.POST)
    @ResponseBody
    public  ResultInfo updateSetting(@RequestBody  SysBackgroundPicSetting sysBackgroundPicSetting){
        return sysBackgroundPicService.updateSetting(sysBackgroundPicSetting);
    }
}
