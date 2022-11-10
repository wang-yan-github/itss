package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import com.jsdc.itss.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置项管理 控制器
 * @Author zdq
 */
@RestController
@RequestMapping("/app/assetsManage")
public class AssetsManageController extends BaseController {

    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ChangeInfoService changeInfoService;
    @Autowired
    private AssetsManageService assetsManageService;
    @Autowired
    private AssetsClGroupService assetsClGroupService;
    @Autowired
    private AssetsManageHistoryService assetsManageHistoryService;
    @Autowired
    private AssetsInventoryDetailsService assetsInventoryDetailsService;
    @Autowired
    private AssetsConfigReqService assetsConfigReqService;
    @Autowired
    private AssetsTypeService assetsTypeService;
    @Autowired
    private AssetsChildrenService assetsChildrenService;
    @Autowired
    private AssetsUseScopeService assetsUseScopeService;
    @Autowired
    private AssetsInventoryService assetsInventoryService;
    @Autowired
    private AssetsRequestConfigService assetsRequestConfigService;


    //配置项管理列表
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                             AssetsManage beanParam) {
        PageInfo<AssetsManage> page = assetsManageService.toList(pageNo, pageSize, beanParam);

        return ResultInfo.success(page);
    }




    //配置项管理列表
    @RequestMapping(value = "toAssetsManageList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAssetsManageList(AssetsManage beanParam) {
        List<AssetsManage> list = assetsManageService.toAssetsManageList( beanParam);

        return ResultInfo.success(list);
    }


    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(AssetsManage beanParam) {
        JSONObject jsonObject = new JSONObject();
        List<String> nameList = new ArrayList<>();
        List<AssetsManage> list = assetsManageService.getList(beanParam);
        list.forEach(x -> {
            nameList.add(x.getAssets_number());
        });
        jsonObject.put("list", list);
        jsonObject.put("nameList", nameList);
        return ResultInfo.success(jsonObject);
    }

     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsManageService.getById(id);
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
    public ResultInfo delAssetsManage(@RequestBody AssetsManage assetsManage){
        return assetsManageService.delAssetsManage(assetsManage);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "delIds.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delIds(String ids){
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

//    @RequestMapping(value = "/exportTemplate.do")
//    public void exportExcel(AssetsManage assetsManage,Integer pageIndex, Integer pageSize,HttpServletResponse response) {
//        assetsManageService.exportFile(assetsManage,pageIndex,pageSize,response);
//    }

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
     * 概览
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "overviewList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo overviewList(@RequestBody AssetsClGroup beanParam) {

        return ResultInfo.success(assetsClGroupService.overviewList(beanParam));
    }

    /**
     * 根据配置类型查询配置项
     * @param code
     * @return
     */
    @RequestMapping(value = "getAssetsByType.do")
    @ResponseBody
    public ResultInfo getAssetsByType(String code){
        return ResultInfo.success(assetsManageService.getAssetsByType(code));
    }

    @RequestMapping(value = "getSeats.do")
    @ResponseBody
    public ResultInfo getSeats(Integer data){
        return ResultInfo.success(assetsManageService.getSeats(data));
    }


    //历史记录
    @RequestMapping(value = "toHistoryList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody AssetsManageHistory beanParam) {
        List<AssetsManageHistory> assetsManageHistoryVos = assetsManageHistoryService.getAppList(beanParam);
        return ResultInfo.success(assetsManageHistoryVos);
    }

    //资产盘点分页
    @RequestMapping(value = "toInventoryList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toInventoryList(@RequestParam(defaultValue = "1",value="pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize, AssetsInventoryDetailsVo beanParam) {
        PageInfo<AssetsInventoryDetailsVo> page = assetsInventoryDetailsService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }

    //资产盘点详情
    @RequestMapping(value = "getInventoryId.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getInventoryId( Integer id) {
        AssetsInventoryDetailsVo assetsInventoryDetailsVo= assetsInventoryDetailsService.getOne(id);
        return ResultInfo.success(assetsInventoryDetailsVo);
    }

    /**
     *  盘点记录处理
     */
    @RequestMapping(value = "toInventoryDeal.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toInventoryDeal(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.toDeal(assetsInventoryDetails);
    }

    /**
     *  盘点记录确认
     */
    @RequestMapping(value = "toInventoryConfirm.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toInventoryConfirm(@RequestBody AssetsInventoryDetails assetsInventoryDetails){

        return assetsInventoryDetailsService.toConfirm(assetsInventoryDetails);
    }

    /**
     * 请求管理列表
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toReqList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toReqList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsConfigReqVo beanParam) {
        PageInfo<AssetsConfigReqVo> page = assetsConfigReqService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 我的请求管理列表
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toMyReqList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toMyReqList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                AssetsConfigReqVo beanParam) {
        beanParam.setData_flag("1");
        PageInfo<AssetsConfigReqVo> page = assetsConfigReqService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 请求代办列表
     * @param pageIndex
     * @param pageSize
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toAgencyReqList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAgencyReqList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  AssetsConfigReqVo beanParam) {
        beanParam.setData_flag("2");
        PageInfo<AssetsConfigReqVo> page = assetsConfigReqService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * ID查询
     */
    @RequestMapping(value = "getReqById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getReqById(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.getAssetsConfigReq(beanParam);
    }

    //请求管理删除
    @RequestMapping(value = "deleteReqById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteReqById(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.toDel(beanParam.getId());
    }

    /**
     * 我的请求撤销
     *
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toCancel.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toCancel(@RequestBody AssetsConfigReqVo beanParam) {
        beanParam.setStatus(3);
        return assetsConfigReqService.toCancel(beanParam);
    }

    /**
     * 请求代办处理
     *
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toResult.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toResult(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.toResult(beanParam);
    }


    //查看所有配置项类型
    @RequestMapping(value = "getAllType.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllType(){
        QueryWrapper<AssetsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<AssetsType> list = assetsTypeService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }



    //查看所有配置项子类
    @RequestMapping(value = "getAllChild.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllChild(){
        QueryWrapper<AssetsChildren> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<AssetsChildren> list = assetsChildrenService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    //查看所有配置项使用范围
    @RequestMapping(value = "getAllScope.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllScope(){
        QueryWrapper<AssetsUseScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<AssetsUseScope> list = assetsUseScopeService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    //获取所有盘点期
    @RequestMapping(value = "getAllInventory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllInventory(){
        QueryWrapper<AssetsInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<AssetsInventory> list = assetsInventoryService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }

    //获取所有配置请求类型
    @RequestMapping(value = "getAllRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllRequest(){
        QueryWrapper<AssetsRequestConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<AssetsRequestConfig> list = assetsRequestConfigService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }


    /**
     * 添加
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsConfigReq(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.addAssetsConfigReq(beanParam);
    }

    /**
     * 类型
     */
    @RequestMapping(value = "getAll.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAll(AssetsRequestConfig beanParam) {
        List<AssetsRequestConfig> list = assetsRequestConfigService.getAll(beanParam);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("nameList",list.stream().map(AssetsRequestConfig::getRequest_name).collect(Collectors.toList()));
        return ResultInfo.success(jsonObject);
    }

    /**
     * 部门
     */
    @RequestMapping("deptList.do")
    public ResultInfo deptList(SysDepartment sysDepartment){
        List<SysDepartmentVo> list = sysDepartmentService.queryWithTree(sysDepartment);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        jsonObject.put("nameList",list.stream().map(SysDepartmentVo::getName).collect(Collectors.toList()));
        return ResultInfo.success(jsonObject);
    }

    /**
     * 关联的变更
     */
    @RequestMapping(value = "changeList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo changeList(@RequestBody ChangeVo changeVo) {
        return changeInfoService.toList(changeVo.getPageNo(), changeVo.getPageSize(), changeVo);
    }

    /**
     * 添加关联的工单
     */
    @RequestMapping(value = "eventList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo eventList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestBody Event event) {

        PageInfo<Event> page = eventService.toList(pageIndex, pageSize, event);
        return ResultInfo.success(page);
    }

    /**
     * 添加关联的配置项
     */
    @RequestMapping(value = "assetsManageList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo assetsManageList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                             AssetsManage beanParam) {
        PageInfo<AssetsManage> page = assetsManageService.toList(pageNo, pageSize, beanParam);
        return ResultInfo.success(page);
    }
}
