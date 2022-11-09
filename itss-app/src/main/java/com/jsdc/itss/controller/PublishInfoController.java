package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.PublishApprovalInfoService;
import com.jsdc.itss.service.PublishBasicInfoService;
import com.jsdc.itss.service.PublishCategoryInfoService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 发布管理
 * @Author zdq
 */
@RestController
@RequestMapping("/app/publishInfo")
public class PublishInfoController extends BaseController {

    @Autowired
    private PublishBasicInfoService publishBasicInfoService;
    @Autowired
    private PublishApprovalInfoService publishApprovalInfoService;
    @Autowired
    private PublishCategoryInfoService publishCategoryInfoService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 我的发布查询
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             PublishBasicInfo beanParam,Integer userId) {

        beanParam.setUserId(userId);
        PageInfo<PublishBasicInfo> page = publishBasicInfoService.toList(pageIndex, pageSize, beanParam);
        List<PublishBasicInfo> list = page.getList();
        for (PublishBasicInfo temp : list){
            if (StringUtils.isNotEmpty(temp.getType())){
                if ("1".equals(temp.getType())){
                    temp.setType_name("一般发布");
                }else {
                    temp.setType_name("紧急发布");
                }
            }
        }
        return ResultInfo.success(page);
    }

    /**
     * 发布代办查询
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toDbList.do", method = RequestMethod.POST)
    public ResultInfo toDbList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,  PublishBasicInfo beanParam,Integer userId) {
        beanParam.setUserId(userId);
        PageInfo<PublishBasicInfo> page = publishBasicInfoService.toDbList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }

    /**
     * 发布管理查询
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toManageList.do", method = RequestMethod.POST)
    public ResultInfo toManageList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,  PublishBasicInfo beanParam){
        PageInfo<PublishBasicInfo> page = publishBasicInfoService.toManageList(pageIndex, pageSize, beanParam);
        List<PublishBasicInfo> list = page.getList();
        for (PublishBasicInfo temp : list){
            if (StringUtils.isNotEmpty(temp.getType())){
                if ("1".equals(temp.getType())){
                    temp.setType_name("一般发布");
                }else {
                    temp.setType_name("紧急发布");
                }
            }
        }
        return ResultInfo.success(page);
    }

    /**
     * 发布评审信息
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "getApprovalByPublishId.do", method = RequestMethod.POST)
    public ResultInfo getApprovalByPublishId(@RequestBody PublishBasicInfo beanParam) {
        List<PublishApprovalInfo> byPublishId = publishApprovalInfoService.getByPublishId(beanParam.getId());
        return ResultInfo.success(byPublishId);
    }

    /**
     *  查询
     */
    @RequestMapping(value = "getPublishBasic.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getPublishBasic( Integer id,Integer userId){
        PublishBasicInfo beanParam = new PublishBasicInfo();
        beanParam.setId(id);
        beanParam.setUserId(userId);
        return publishBasicInfoService.getPublishBasic(beanParam);
    }

    /**
     * 撤销
     * @param id
     * @return
     */
    @RequestMapping(value = "revoke.do",method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo revoke(Integer id,Integer userId){
        return publishBasicInfoService.revoke(id,userId);
    }


    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo addPublishBasicInfo(@RequestBody PublishBasicInfo publishBasicInfo){

        return publishBasicInfoService.addPublishBasicInfo(publishBasicInfo, null);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo editPublishBasicInfo(@RequestParam(value = "body") String body, @RequestParam List<MultipartFile> file){
        PublishBasicInfo publishBasicInfo = JSON.parseObject(body, PublishBasicInfo.class);
        return publishBasicInfoService.editPublishBasicInfo(publishBasicInfo, file);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "toDel.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delPublishBasicInfo(@RequestBody PublishBasicInfo publishBasicInfo){
        PublishBasicInfo publishBasicInfo1 = publishBasicInfoService.selectById(publishBasicInfo.getId());
        publishBasicInfo1.setUserId(publishBasicInfo.getUserId());

        return publishBasicInfoService.delPublishBasicInfo(publishBasicInfo1);
    }

    /**
     *  撤回
     */
    @RequestMapping(value = "revokePublish.do",method = RequestMethod.POST)
    public ResultInfo revokePublish(@RequestBody PublishBasicInfo publishBasicInfo){
        return publishBasicInfoService.revokePublish(publishBasicInfo);
    }

    /**
     *  评审
     */
    @RequestMapping(value = "approvePublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    @ResponseBody
    public ResultInfo approvePublish(@RequestBody PublishBasicInfo publishBasicInfo){
//        PublishBasicInfo publishBasicInfo = new PublishBasicInfo();
//        publishBasicInfo.setId(id);
//        publishBasicInfo.setApproval_opinions(approval_opinions);
//        publishBasicInfo.setIsPass(isPass);
        return publishBasicInfoService.approvePublish(publishBasicInfo);
    }

    /**
     *  测试
     */
    @RequestMapping(value = "testPublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    @ResponseBody
    public ResultInfo testPublish(@RequestBody PublishBasicInfo publishBasicInfo){
        return publishBasicInfoService.testPublish(publishBasicInfo);
    }

    /**
     *  发布
     */
    @RequestMapping(value = "doPublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    @ResponseBody
    public ResultInfo doPublish(@RequestBody PublishBasicInfo publishBasicInfo){
        return publishBasicInfoService.doPublish(publishBasicInfo);
    }

    /**
     * Excel发布信息模板下载
     * @param response
     * @return
     */
    @RequestMapping("downloadPublishExcel.do")
    public void downloadWhite(HttpServletResponse response) {
        String filename = "发布信息模板.xlsx";
        int len = 0;
        //5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        //通过response对象获取OutputStream流
        try (OutputStream out = response.getOutputStream()) {

            ClassPathResource classPathResource = new ClassPathResource("masterplate/发布信息模板.xlsx");
            InputStream in = classPathResource.getInputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

            //将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**

     * description:内部知识导出excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @PostMapping("exportMyPublishExcel.do")
    public void exportMyPublishExcel(HttpServletResponse response){
        publishBasicInfoService.exporMyPublishExcel(response);
    }

    /**
     * exportPublishExcel
     */
    @PostMapping("exportPublishExcel.do")
    public void exportPublishExcel(HttpServletResponse response){
        publishBasicInfoService.exportPublishExcel(response);
    }

    /**
     * 发布信息导出模板下载
     */
    @RequestMapping("exportPublishExcelTemplate.do")
    public void downloadPublishExcel(HttpServletResponse response){
        publishBasicInfoService.exportPublishExcelTemplate(response);
    }

    /**
     *
     * exportDbPublishExcel
     */
    @PostMapping("exportDbPublishExcel.do")
    public void exportDbPublishExcel(HttpServletResponse response){
        publishBasicInfoService.exportDbPublishExcel(response);
    }

    /**
     * 发布代办 - 参与的发布列表
     */
    @PostMapping("getPublishListByParticipate.do")
    public ResultInfo getPublishListByParticipate(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  PublishBasicInfo beanParam){
        return ResultInfo.success(publishBasicInfoService.getPublishListByParticipate(pageIndex, pageSize, beanParam));
    }
    /**
     * exportPublishParticipateExcel
     */
    @PostMapping("exportPublishParticipateExcel.do")
    public void exportPublishParticipateExcel(HttpServletResponse response){
        publishBasicInfoService.exportPublishParticipateExcel(response);
    }

    /**
     * 查询日志
     * @param publishBasicInfo
     * @return
     */
    @RequestMapping(value = "getLogInfo.do",method = RequestMethod.POST)
    public ResultInfo getLogInfo(@RequestBody PublishBasicInfo publishBasicInfo){
        List<SysLog> list = publishBasicInfoService.getLogInfo(publishBasicInfo);
        return ResultInfo.success(list);
    }



    /**
     * 获取所有在用发布类别
     * @return
     * @author wh
     */
    @RequestMapping(value = "getAllCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllList(  PublishCategoryInfo publishCategoryInfo) {
        publishCategoryInfo.setIs_use("1");
        List<PublishCategoryInfo> publishCategoryInfoPageInfo = publishCategoryInfoService.getAllList( publishCategoryInfo);
        return ResultInfo.success(publishCategoryInfoPageInfo);
    }


    /**
     * 获取发布类别评审信息
     *
     * @param
     * @return
     * @author wh
     */
    @RequestMapping(value = "getAllUser.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllUser() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("is_del", "0");
        List<SysUser> list =  sysUserService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }



}
