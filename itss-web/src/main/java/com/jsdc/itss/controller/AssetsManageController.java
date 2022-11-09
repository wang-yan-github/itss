package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.service.AssetsManageService;
import com.jsdc.itss.vo.OverviewVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 配置项管理 控制器
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@RestController
@RequestMapping("/assetsManage")
public class AssetsManageController extends BaseController {

    @Autowired
    private AssetsManageService assetsManageService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                             AssetsManage beanParam) {
        PageInfo<AssetsManage> page = assetsManageService.toList(pageNo, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(@RequestBody AssetsManage beanParam) {
        return ResultInfo.success(assetsManageService.getList(beanParam));
    }

     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(@RequestBody Integer id){
        return assetsManageService.getById(id);
    }

    /**
     *  改变打印状态
     */
    @RequestMapping(value = "changePrint.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo changePrint(@RequestBody Integer id){
        return assetsManageService.changePrint(id);
    }

    /**
     * 查询扩展字段
     * @param id 配置项类型Id
     * @return
     */
    @RequestMapping(value = "getExpandInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getExpandInfo(Integer id){
        return assetsManageService.getExpandInfo(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsManage(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> picture){
        AssetsManage assetsManage = JSON.parseObject(body, AssetsManage.class);
        return assetsManageService.addAssetsManage(assetsManage, file, picture);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsManage(@RequestParam String body, @RequestParam List<MultipartFile> file, @RequestParam List<MultipartFile> picture){
        AssetsManage assetsManage = JSON.parseObject(body, AssetsManage.class);
        return assetsManageService.editAssetsManage(assetsManage, file, picture);
    }

    /**
     *  删除
     */
    @RequestMapping(value = "toDel.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delAssetsManage(AssetsManage assetsManage){
        return assetsManageService.delAssetsManage(assetsManage);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "delIds.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delIds(@RequestBody String ids){
        return ResultInfo.success(assetsManageService.delIds(ids));
    }

    /**
     * 下载模板
     * @param response
     */
    @RequestMapping(value = "/downloadTemplate.do")
    public void downloadTemplate(HttpServletResponse response) {
        assetsManageService.downloadFile(response);
    }

    @RequestMapping(value = "/exportTemplate.do")
    public void exportExcel(AssetsManage assetsManage,HttpServletResponse response) {
        assetsManageService.exportFile(assetsManage,response);
    }

    /**
     *  查看-属性
     */
    @RequestMapping(value = "toViewProperties.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toViewProperties(AssetsManage assetsManage){
        return assetsManageService.viewProperties(assetsManage);
    }
    /**
     *  查看-联系人
     */
    @RequestMapping(value = "toViewContacts.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toViewContacts(AssetsManage assetsManage){
        return assetsManageService.viewContacts(assetsManage);
    }
    /**
     *  查看-供应商合同
     */
    @RequestMapping(value = "toViewSupplierContract.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toViewSupplierContract(AssetsManage assetsManage){
        return assetsManageService.viewSupplierContract(assetsManage);
    }
    /**
     *  查看-关联配置项
     */
    @RequestMapping(value = "toViewAssetsConfig.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toViewAssetsConfig(AssetsManage assetsManage){
        return assetsManageService.viewAssetsConfig(assetsManage);
    }
    /**
     *  查看-工单
     */
    @RequestMapping(value = "toViewEvent.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toViewEvent(AssetsManage assetsManage){

        return assetsManageService.viewEvent(assetsManage);
    }


    /**
     *  查看-生成二维码
     */
    @RequestMapping(value = "genratorQrCode.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo genratorQrCode(AssetsManage assetsManage){
        String qrCode = assetsManageService.genratorQrCode(assetsManage);
        return ResultInfo.success(qrCode);
    }

    /**
     * 概览
     * @return
     */
    @RequestMapping(value = "overview.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toOverviewList(AssetsManage bean) {
        List<OverviewVo> overviewList = assetsManageService.getOverviewList(bean);

        return ResultInfo.success(overviewList);
    }

    /**
     * 根据配置类型查询配置项
     * @param code
     * @return
     */
    @RequestMapping(value = "getAssetsByType.do")
    @ResponseBody
    public ResultInfo getAssetsByType(@RequestBody String code){
        return ResultInfo.success(assetsManageService.getAssetsByType(code));
    }

    @RequestMapping(value = "getSeats.do")
    @ResponseBody
    public ResultInfo getSeats( Integer data){
        return ResultInfo.success(assetsManageService.getSeats(data));
    }



    /**
     *配置管理模板导出
     * Author wzn
     * Date 2022/11/1 10:41
     */
    @RequestMapping("exportAssetsExcelTemplate.do")
    public void downloadPublishExcel(HttpServletResponse response){
        assetsManageService.exportAssetsExcelTemplate(response);
    }



    @RequestMapping("importAssetsList")
    @ResponseBody
    public ResultInfo importAssetsList(@RequestParam("file") MultipartFile file) {
        return assetsManageService.importAssetsList(file);
    }
}
