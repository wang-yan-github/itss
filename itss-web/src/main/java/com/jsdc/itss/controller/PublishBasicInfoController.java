package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.model.PublishApprovalInfo;
import com.jsdc.itss.model.PublishBasicInfo;
import com.jsdc.itss.model.SysLog;
import com.jsdc.itss.model.vo.ChangesOf;
import com.jsdc.itss.service.PublishApprovalInfoService;
import com.jsdc.itss.service.PublishBasicInfoService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author wh
 * @create 2022-03-14 14:58:22
 */
@RestController
@RequestMapping("/publishBasicInfo")
public class PublishBasicInfoController extends BaseController {

    @Autowired
    private PublishBasicInfoService publishBasicInfoService;
    @Autowired
    private PublishApprovalInfoService publishApprovalInfoService;

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
                             PublishBasicInfo beanParam) {
        PageInfo<PublishBasicInfo> page = publishBasicInfoService.toList(pageIndex, pageSize, beanParam);
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
    public ResultInfo toDbList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,  PublishBasicInfo beanParam) {
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
    public ResultInfo getPublishBasic( PublishBasicInfo beanParam){
        return publishBasicInfoService.getPublishBasic(beanParam);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo addPublishBasicInfo(@RequestParam(value = "body") String body, @RequestParam List<MultipartFile> file){

        PublishBasicInfo publishBasicInfo = JSON.parseObject(body, PublishBasicInfo.class);
        return publishBasicInfoService.addPublishBasicInfo(publishBasicInfo, file);
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
    public ResultInfo delPublishBasicInfo(@RequestBody PublishBasicInfo publishBasicInfo){

        return publishBasicInfoService.delPublishBasicInfo(publishBasicInfo);
    }

    /**
     *  撤回
     */
    @RequestMapping(value = "revokePublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo revokePublish(@RequestBody PublishBasicInfo publishBasicInfo){
        return publishBasicInfoService.revokePublish(publishBasicInfo);
    }

    /**
     *  评审
     */
    @RequestMapping(value = "approvePublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo approvePublish(@RequestParam(value = "body") String body){
        PublishBasicInfo publishBasicInfo = JSON.parseObject(body, PublishBasicInfo.class);
        return publishBasicInfoService.approvePublish(publishBasicInfo);
    }

    /**
     *  测试
     */
    @RequestMapping(value = "testPublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo testPublish(@RequestParam(value = "body") String body){
        PublishBasicInfo publishBasicInfo = JSON.parseObject(body, PublishBasicInfo.class);
        return publishBasicInfoService.testPublish(publishBasicInfo);
    }

    /**
     *  发布
     */
    @RequestMapping(value = "doPublish.do",method = RequestMethod.POST)
    @LogInfo(LogEnums.LOG_PUBLISH)
    public ResultInfo doPublish(@RequestParam(value = "body") String body){
        PublishBasicInfo publishBasicInfo = JSON.parseObject(body, PublishBasicInfo.class);
        return publishBasicInfoService.doPublish(publishBasicInfo);
    }

    /**
     * Excel发布信息模板下载
     * author wh
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

//    /**
//     * Excel导入发布
//     * author xuaolong
//     * @param file
//     * @return
//     */
//    @RequestMapping("importPublishList")
//    public ResultInfo importPublishList(@RequestParam("file") MultipartFile file){
//        return publishBasicInfoService.importPublishList(file);
//    }

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
     * Excel导入问题
     * author xuaolong
     *
     * @param file
     * @return
     */
    @RequestMapping("importPublishList")
    @ResponseBody
    public ResultInfo importPublishList(@RequestParam("file") MultipartFile file) {
        return publishBasicInfoService.importPublishList(file);
    }



    /**
    *发布来源统计
    * Author wzn
    * Date 2022/11/3 10:14
    */
    @RequestMapping("publishedSources")
    @ResponseBody
    public ResultInfo publishedSources() {
        return ResultInfo.success(publishBasicInfoService.publishedSources());
    }


    /**
    *发布类别统计
    * Author wzn
    * Date 2022/11/3 10:14
    */
    @RequestMapping("releaseTheCategory")
    @ResponseBody
    public ResultInfo releaseTheCategory() {
        return ResultInfo.success(publishBasicInfoService.releaseTheCategory());
    }


    /**
    *发布类型统计
    * Author wzn
    * Date 2022/11/3 10:15
    */
    @RequestMapping("publishType")
    @ResponseBody
    public ResultInfo publishType() {
        List<ChangesOf> changesOfList = publishBasicInfoService.publishType();
        if(!CollectionUtils.isEmpty(changesOfList)){
            for(ChangesOf c:changesOfList){
                if("1".equals(c.getName())){
                    c.setName("一般发布");
                }else {
                    c.setName("紧急发布");
                }
            }
        }
        return ResultInfo.success(changesOfList);
    }

}
