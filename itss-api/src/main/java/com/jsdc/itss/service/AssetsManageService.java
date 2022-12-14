package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsManageDao;
import com.jsdc.itss.enums.AssetsTypeEnum;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.exception.CustomException;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.DataUtils;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.QrCodeUtil;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.OverviewVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsManageService extends BaseService<AssetsManageDao, AssetsManage> {

    @Autowired
    private AssetsManageMapper assetsManageMapper;
    @Autowired
    private AssetsManageDao assetsManageDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AssetsManageUserService assetsManageUserService;
    @Autowired
    private AssetsManageContractService assetsManageContractService;
    @Autowired
    private AssetsManageMapService assetsManageMapService;
    @Autowired
    private EventAssetsService eventAssetsService;
    @Autowired
    private ChangeAssetsService changeAssetsService;
    @Autowired
    private AssetsManageHistoryService assetsManageHistoryService;
    @Autowired
    private AssetsTypeService assetsTypeService;
    @Autowired
    private AssetsExpandFormService assetsExpandFormService;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private AssetsEnclosureService assetsEnclosureService;
    @Autowired
    private AssetsChildrenService assetsChildrenService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private AssetsContractContactService assetsContractContactService;
    @Autowired
    private AssetsSupplierContractService assetsSupplierContractService;
    @Autowired
    private AssetsBrandService assetsBrandService;
    @Autowired
    private AssetsUseScopeService assetsUseScopeService;
    @Autowired
    private AssetsItStatusService assetsItStatusService;
    @Autowired
    private AssetsStatusService assetsStatusService;
    @Autowired
    private AssetsCompanyService assetsCompanyService;
    @Autowired
    private AssetsOsService assetsOsService;
    @Autowired
    private AssetsOsLicenceService assetsOsLicenceService;
    @Autowired
    private AssetsSoftwareLicenceService assetsSoftwareLicenceService;
    @Autowired
    private SysUserConfigAuthorityMapper sysUserConfigAuthorityMapper;
    @Autowired
    private AssetsConfigStatusMapper assetsConfigStatusMapper;


    @Autowired
    private AssetsChildrenMapper assetsChildrenMapper;

    @Autowired
    private AssetsItStatusService statusService;

    @Autowired
    private AssetsTypeMapper assetsTypeMapper ;


    private String[] dateFormat = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

    @Value("${jsdc.fileUrl}")
    private String fileUrl;

    @Value("${file.upload-path}")
    private String uploadPath;

    public static String CODE_FIX = "PZ";


    /**
     * pagehelper????????????
     * @param currentPage ?????????
     * @param pageSize
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * currentPage;
            if (toIndex > total) {
                toIndex = total;
            }
            if (pageSize * (currentPage - 1)<toIndex) {
                list = list.subList(pageSize * (currentPage - 1), toIndex);
            }
            else {
                list=new ArrayList<>();
            }
        }else {
            if (currentPage == 1){

            }else {
                list = new ArrayList<>();
            }
        }
        Page<T> page = new Page<>(currentPage, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);

        PageInfo<T> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }


    public PageInfo<AssetsManage> toList(Integer pageIndex, Integer pageSize, AssetsManage beanParam) {
        List<AssetsManage> assetsManageVos = assetsManageMapper.toList(beanParam);

        Integer userId = null;
        if(null == beanParam.getUserId()){
            if(null != beanParam.getWX_userId()){
                userId = beanParam.getWX_userId();
            }else{
                userId = sysUserService.getUser().getId();
            }
        }else{
            userId = beanParam.getUserId();
        }
        QueryWrapper<SysUserConfigAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("user_id",userId);
        List<SysUserConfigAuthority> list =  sysUserConfigAuthorityMapper.selectList(queryWrapper);
        List<AssetsManage> result = new ArrayList<>();
        assetsManageVos.forEach(x -> {
            if(null != x.getReceiver()){
                SysUser receiveUser = sysUserService.selectById(x.getReceiver());
                if(Base.notEmpty(receiveUser)){
                    x.setReceiverName(receiveUser.getName());
                }
            }
            if(null != x.getPurchase_company()){
                AssetsCompany assetsCompany = assetsCompanyService.selectById(x.getPurchase_company());
                if(Base.notEmpty(assetsCompany)){
                    x.setPurchaseCompanyName(assetsCompany.getCompany_name());
                }
            }
            //???????????? assetsStatusName
            if (Base.notEmpty(x.getAssets_status())) {
                AssetsStatus assetsStatus = assetsStatusService.selectById(x.getAssets_status());
                if (null != assetsStatus){
                    x.setAssetsStatusName(assetsStatus.getStatus_name());
                }

            }
            if (!CollectionUtils.isEmpty(list)){
                for (int i = 0 ; i < list.size(); i++){
                    if (x.getDept_id() == list.get(i).getDepartment_id()){
                        result.add(x);
                        break;
                    }
                }
            }

        });
        return AssetsManageService.getPageInfo(pageIndex,pageSize,result);
    }


    public List<AssetsManage> toAssetsManageList( AssetsManage beanParam) {
        List<AssetsManage> assetsManageVos = assetsManageMapper.toList(beanParam);

        Integer userId = null;
        if(null == beanParam.getUserId()){
            userId = sysUserService.getUser().getId();
        }else{
            userId = beanParam.getUserId();
        }
        QueryWrapper<SysUserConfigAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("user_id",userId);
        List<SysUserConfigAuthority> list =  sysUserConfigAuthorityMapper.selectList(queryWrapper);
        List<AssetsManage> result = new ArrayList<>();
        assetsManageVos.forEach(x -> {
            if(null != x.getReceiver()){
                SysUser receiveUser = sysUserService.selectById(x.getReceiver());
                if(Base.notEmpty(receiveUser)){
                    x.setReceiverName(receiveUser.getName());
                }
            }
            if(null != x.getPurchase_company()){
                AssetsCompany assetsCompany = assetsCompanyService.selectById(x.getPurchase_company());
                if(Base.notEmpty(assetsCompany)){
                    x.setPurchaseCompanyName(assetsCompany.getCompany_name());
                }
            }
            //???????????? assetsStatusName
            if (Base.notEmpty(x.getAssets_status())) {
                AssetsStatus assetsStatus = assetsStatusService.selectById(x.getAssets_status());
                if (null != assetsStatus){
                    x.setAssetsStatusName(assetsStatus.getStatus_name());
                }

            }
            if (!CollectionUtils.isEmpty(list)){
                for (int i = 0 ; i < list.size(); i++){
                    if (x.getDept_id() == list.get(i).getDepartment_id()){
                        result.add(x);
                        break;
                    }
                }
            }

        });
        return result;
    }

    public List<AssetsManage> getList(AssetsManage beanParam) {

        List<AssetsManage> assetsManageVos = assetsManageMapper.toList(beanParam);
        for (AssetsManage temp : assetsManageVos){
            if (null != temp.getAssets_status()){
                AssetsStatus assetsStatus = assetsStatusService.selectById(temp.getAssets_status());
                if (null != assetsStatus){
                    temp.setAssetsStatusName(assetsStatus.getStatus_name());
                }

            }
        }
        return assetsManageVos;
    }

    public ResultInfo getById(Integer id) {
        AssetsManage assetsManage = selectById(id);
        // ???????????? brandNamea
        if (Base.notEmpty(assetsManage.getBrand_id())) {
            AssetsBrand assetsBrand = assetsBrandService.selectById(assetsManage.getBrand_id());
            assetsManage.setBrandName(assetsBrand.getBrand_name());
        }
        // ???????????? useScopeName
        if (Base.notEmpty(assetsManage.getUse_scope_id())) {
            AssetsUseScope assetsUseScope = assetsUseScopeService.selectById(assetsManage.getUse_scope_id());
            assetsManage.setUseScopeName(assetsUseScope.getScope_name());
        }
        // IT?????? itStatusName
        if (Base.notEmpty(assetsManage.getIt_status())) {
            AssetsItStatus assetsItStatus = assetsItStatusService.selectById(assetsManage.getIt_status());
            assetsManage.setItStatusName(assetsItStatus.getStatus_name());
        }
        //???????????? assetsStatusName
        if (Base.notEmpty(assetsManage.getAssets_status())) {
            AssetsStatus assetsStatus = assetsStatusService.selectById(assetsManage.getAssets_status());
            assetsManage.setAssetsStatusName(assetsStatus.getStatus_name());
        }

        //???????????? purchaseCompanyName
        if (Base.notEmpty(assetsManage.getPurchase_company())) {
            AssetsCompany assetsCompany = assetsCompanyService.selectById(assetsManage.getPurchase_company());
            assetsManage.setPurchaseCompanyName(assetsCompany.getCompany_name());
        }

        // ??????????????? assetsTypeName
        if (Base.notEmpty(assetsManage.getAssets_type_id())) {
            AssetsType assetsType = assetsTypeService.selectById(assetsManage.getAssets_type_id());
            assetsManage.setAssetsTypeName(assetsType.getName());
        }
        // ??????????????? childAssetName
        if (Base.notEmpty(assetsManage.getChild_asset())) {
            AssetsChildren assetsChildren = assetsChildrenService.selectById(assetsManage.getChild_asset());
            assetsManage.setChildAssetName(assetsChildren.getName());
        }
        // ???????????? dept_name
        if (Base.notEmpty(assetsManage.getDept_id())) {
            SysDepartment department = sysDepartmentService.selectById(assetsManage.getDept_id());
            assetsManage.setDeptName(department.getName());
            assetsManage.setDept_name(department.getName());
        }
        // ??????????????? person_liable_name
        if (Base.notEmpty(assetsManage.getPerson_liable())) {
            SysUser sysUser = sysUserService.selectById(assetsManage.getPerson_liable());
            assetsManage.setPerson_liable_name(sysUser.getName());
        }
        // ??????????????? receiver_name
        if (Base.notEmpty(assetsManage.getReceiver())) {
            SysUser sysUser = sysUserService.selectById(assetsManage.getReceiver());
            assetsManage.setReceiver_name(sysUser.getName());
            assetsManage.setReceiverName(sysUser.getName());
        }
        //????????????
        if(Base.notEmpty(assetsManage.getOs_id())){
            AssetsOs assetsOs = assetsOsService.selectById(assetsManage.getOs_id());
            if(null != assetsOs){
                assetsManage.setOs_id_name(assetsOs.getOs_name());
            }
        }
        //OS?????????
        if(Base.notEmpty(assetsManage.getOs_licence_id())){
            AssetsOsLicence assetsOsLicence = assetsOsLicenceService.selectById(assetsManage.getOs_licence_id());
            if(null != assetsOsLicence){
                assetsManage.setOs_licence_name(assetsOsLicence.getOs_licence_name());
            }
        }
        //???????????????
        if(Base.notEmpty(assetsManage.getAssetsSoftwareLicence_id())){
            AssetsSoftwareLicence assetsSoftwareLicence = assetsSoftwareLicenceService.selectById(assetsManage.getAssetsSoftwareLicence_id());
            if(null != assetsSoftwareLicence){
                assetsManage.setSoftware_licence_name(assetsSoftwareLicence.getOs_licence_name());
            }
        }
        //??????
        if(Base.notEmpty(assetsManage.getCabinet_id())){
            AssetsManage cabinet = selectById(assetsManage.getCabinet_id());
            if(null != cabinet){
                assetsManage.setCabinet_name(cabinet.getAssets_name());
            }
        }
        //????????????
        if(Base.notEmpty(assetsManage.getStart_seat())){
            AssetsManage startSeat = selectById(assetsManage.getStart_seat());
            if(null != startSeat){
                assetsManage.setStart_seat_name(startSeat.getAssets_name());
            }
        }
        //????????????
        if(Base.notEmpty(assetsManage.getEnd_seat())){
            AssetsManage endSeat = selectById(assetsManage.getEnd_seat());
            if(null != endSeat){
                assetsManage.setEnd_seat_name(endSeat.getAssets_name());
            }
        }
        if (Base.notEmpty(assetsManage.getDatabaseName())){
            AssetsManage assetsManage1 = selectById(assetsManage.getDatabaseName());
            if (null != assetsManage1){
                assetsManage.setReal_data_base_Name(assetsManage1.getAssets_name());
            }
        }

        // ???????????????
        List<AssetsManageUser> contractList = assetsManageUserService.selectList(Wrappers.<AssetsManageUser>lambdaQuery().eq(AssetsManageUser::getAssets_id, id));
        assetsManage.setContractContactList(Collections.emptyList());
        if (!CollectionUtils.isEmpty(contractList)) {
            List<Integer> idList = contractList.stream().map(AssetsManageUser::getUser_id).collect(Collectors.toList());
            AssetsContractContact assetsContractContact = new AssetsContractContact();
            assetsContractContact.setIds(idList);
            assetsManage.setContractContactList(assetsContractContactService.getList(assetsContractContact));
        }

        // ???????????????
        List<AssetsManageContract> assetsManageContractList = assetsManageContractService.selectList(Wrappers.<AssetsManageContract>lambdaQuery().eq(AssetsManageContract::getAssets_id, id));
        assetsManage.setSupplierContractList(Collections.emptyList());
        if (!CollectionUtils.isEmpty(assetsManageContractList)) {
            List<Integer> idList = assetsManageContractList.stream().map(AssetsManageContract::getContract_id).collect(Collectors.toList());
            AssetsSupplierContract assetsSupplierContract = new AssetsSupplierContract();
            assetsSupplierContract.setIds(idList);
            assetsManage.setSupplierContractList(assetsSupplierContractService.getList(assetsSupplierContract));
        }

        // ??????????????????
        List<AssetsManageMap> assetsManageMapList = assetsManageMapService.selectList(Wrappers.<AssetsManageMap>lambdaQuery().eq(AssetsManageMap::getAssets_id, id));
        assetsManage.setAssetsTypeList(Collections.emptyList());
        if (!CollectionUtils.isEmpty(assetsManageMapList)) {
            List<Integer> idList = assetsManageMapList.stream().map(AssetsManageMap::getOther_assets_id).collect(Collectors.toList());
            AssetsManage assetsManage2 = new AssetsManage();
            assetsManage2.setIds(idList);
            assetsManage.setAssetsTypeList(getList(assetsManage2));
        }

        //??????
        List<FileManage> fileManages = assetsEnclosureService.getFileManageByType(id, 0);
        fileManages.forEach(x -> {
            String path = uploadPath + File.separator + x.getFile_name();
            File file = new File(path);
            String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
            x.setBase64(base64);
        });
        assetsManage.setFileManageList(fileManages);

        //??????
        List<FileManage> fileManages1 = assetsEnclosureService.getFileManageByType(id, 1);
        fileManages1.forEach(x -> {
            String path = uploadPath + File.separator + x.getFile_name();
            File file = new File(path);
            String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
            x.setBase64(base64);
        });
        assetsManage.setPictureList(fileManages1);

        // ??????

        //???????????????

        //?????????

        assetsManage.setQrCode(genratorQrCode(assetsManage));

        return ResultInfo.success(assetsManage);
    }

    /**
     * ??????
     */
    public ResultInfo addAssetsManage(AssetsManage bean, List<MultipartFile> files, List<MultipartFile> pictures) {
        // ????????????
        bean.setIs_del(String.valueOf(0));
        // ????????????
        bean.setCreate_time(new Date());
        // ???????????????????????????
        bean.setIs_inventory(1);
        //????????????
        bean.setIs_print("1");
        // ?????????
        bean.setCreate_user(null == bean.getUserId() ? sysUserService.getUser().getId() : bean.getUserId());
        //????????????
        bean.setAssets_number(createCode(bean.getAssets_type_id(), bean.getChild_asset()));
        insert(bean);


        //???????????????
        String contractIds = bean.getContractIds();
        if(StringUtils.isNotEmpty(contractIds)){
            List<String> contractList = Arrays.asList(contractIds.split(","));
            contractList.forEach(x -> {
                AssetsManageUser assetsManageUser = new AssetsManageUser();
                assetsManageUser.setAssets_id(bean.getId());
                assetsManageUser.setUser_id(Integer.parseInt(x));
                assetsManageUser.setCreate_time(new Date());
                assetsManageUser.setUpdate_time(new Date());
                assetsManageUser.setIs_del(GlobalData.ISDEL_NO);
                assetsManageUserService.insert(assetsManageUser);
            });
        }

        //?????????????????????
        String supplierIds = bean.getSupplierIds();
        if(StringUtils.isNotEmpty(supplierIds)){
            List<String> supplierList = Arrays.asList(supplierIds.split(","));
            supplierList.forEach(x -> {
                AssetsManageContract assetsManageContract = new AssetsManageContract();
                assetsManageContract.setAssets_id(bean.getId());
                assetsManageContract.setContract_id(Integer.parseInt(x));
                assetsManageContract.setCreate_time(new Date());
                assetsManageContract.setUpdate_time(new Date());
                assetsManageContractService.insert(assetsManageContract);
            });
        }

        //????????????????????????
        String assetsIds = bean.getAssetsIds();
        if(StringUtils.isNotEmpty(assetsIds)){
            List<String> assetsList = Arrays.asList(assetsIds.split(","));
            assetsList.forEach(x -> {
                AssetsManageMap assetsManageMap = new AssetsManageMap();
                assetsManageMap.setAssets_id(bean.getId());
                assetsManageMap.setOther_assets_id(Integer.parseInt(x));
                assetsManageMap.setCreate_time(new Date());
                assetsManageMap.setUpdate_time(new Date());
                assetsManageMapService.insert(assetsManageMap);
            });
        }

        //????????????
        assetsManageHistoryService.addAssetsManageHistory(bean);
        if(!CollectionUtils.isEmpty(files)){
            for(MultipartFile file : files){
                int id = fileManageService.fileUpload(file);
                AssetsEnclosure assetsEnclosure = new AssetsEnclosure();
                assetsEnclosure.setAssets_id(bean.getId());
                assetsEnclosure.setFile_id(id);
                assetsEnclosure.setIs_del(GlobalData.ISDEL_NO);
                assetsEnclosure.setFile_type(0);
                assetsEnclosureService.insert(assetsEnclosure);
            }
        }
        if(!CollectionUtils.isEmpty(pictures)){
            for(MultipartFile file : pictures){
                int id = fileManageService.fileUpload(file);
                AssetsEnclosure assetsEnclosure = new AssetsEnclosure();
                assetsEnclosure.setAssets_id(bean.getId());
                assetsEnclosure.setFile_id(id);
                assetsEnclosure.setIs_del(GlobalData.ISDEL_NO);
                assetsEnclosure.setFile_type(1);
                assetsEnclosureService.insert(assetsEnclosure);
            }
        }
        return ResultInfo.success();
    }

    /**
     * ??????
     */
    public ResultInfo editAssetsManage(AssetsManage bean, List<MultipartFile> files, List<MultipartFile> picture) {
        // ?????????
        bean.setUpdate_user(null == bean.getUserId() ? sysUserService.getUser().getId() : bean.getUserId());
        // ????????????
        bean.setUpdate_time(new Date());
        updateById(bean);
        //???????????????
        String contractIds = bean.getContractIds();
        if (Base.notEmpty(bean.getId())) {
            QueryWrapper<AssetsManageUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("assets_id", bean.getId());
            assetsManageUserService.delete(queryWrapper);
        }
        if(StringUtils.isNotEmpty(contractIds)){
            List<String> contractList = Arrays.asList(contractIds.split(","));
            contractList.forEach(x -> {
                AssetsManageUser assetsManageUser = new AssetsManageUser();
                assetsManageUser.setAssets_id(bean.getId());
                assetsManageUser.setUser_id(Integer.parseInt(x));
                assetsManageUser.setCreate_time(new Date());
                assetsManageUser.setUpdate_time(new Date());
                assetsManageUser.setIs_del(GlobalData.ISDEL_NO);
                assetsManageUserService.insert(assetsManageUser);
            });
        }

        //?????????????????????
        if (Base.notEmpty(bean.getId())) {
            QueryWrapper<AssetsManageContract> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("assets_id", bean.getId());
            assetsManageContractService.delete(queryWrapper);
        }
        String supplierIds = bean.getSupplierIds();
        if(StringUtils.isNotEmpty(supplierIds)){
            List<String> supplierList = Arrays.asList(supplierIds.split(","));
            supplierList.forEach(x -> {
                AssetsManageContract assetsManageContract = new AssetsManageContract();
                assetsManageContract.setAssets_id(bean.getId());
                assetsManageContract.setContract_id(Integer.parseInt(x));
                assetsManageContract.setCreate_time(new Date());
                assetsManageContract.setUpdate_time(new Date());
                assetsManageContractService.insert(assetsManageContract);
            });
        }

        //????????????????????????
        String assetsIds = bean.getAssetsIds();
        if (Base.notEmpty(bean.getId())) {
            QueryWrapper<AssetsManageMap> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("assets_id", bean.getId());
            assetsManageMapService.delete(queryWrapper);
        }
        if(StringUtils.isNotEmpty(assetsIds)){
            List<String> assetsList = Arrays.asList(assetsIds.split(","));
            assetsList.forEach(x -> {
                AssetsManageMap assetsManageMap = new AssetsManageMap();
                assetsManageMap.setAssets_id(bean.getId());
                assetsManageMap.setOther_assets_id(Integer.parseInt(x));
                assetsManageMap.setCreate_time(new Date());
                assetsManageMap.setUpdate_time(new Date());
                assetsManageMapService.insert(assetsManageMap);
            });
        }

        //????????????
        assetsManageHistoryService.addAssetsManageHistory(bean, "??????");
        //????????????
        // ????????????????????????
        String uploadedFiles = bean.getUploadedFiles();
        List<FileManage> fileManages = assetsEnclosureService.getFileManageByType(bean.getId(), 0);
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());

        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }

        if(!CollectionUtils.isEmpty(original)){
            LambdaUpdateWrapper<AssetsEnclosure> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(AssetsEnclosure::getIs_del, GlobalData.ISDEL_YES)
                    .eq(AssetsEnclosure::getAssets_id, bean.getId())
                    .in(AssetsEnclosure::getFile_id, original);
            assetsEnclosureService.update(null, wrapper);
        }
        if(!CollectionUtils.isEmpty(files)){
            for(MultipartFile file : files){
                int id = fileManageService.fileUpload(file);
                AssetsEnclosure assetsEnclosure = new AssetsEnclosure();
                assetsEnclosure.setAssets_id(bean.getId());
                assetsEnclosure.setFile_id(id);
                assetsEnclosure.setFile_type(0);
                assetsEnclosure.setIs_del(GlobalData.ISDEL_NO);
                assetsEnclosureService.insert(assetsEnclosure);
            }
        }

        //????????????
        // ????????????????????????
        String uploadedPictures = bean.getUploadedpictures();
        List<FileManage> pictureManages = assetsEnclosureService.getFileManageByType(bean.getId(), 1);
        List<String> originalPicture = pictureManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedPictures)){
            List<String> pictureIds = Arrays.asList(uploadedPictures.split(","));
            originalPicture.removeAll(pictureIds);
        }
        if(!CollectionUtils.isEmpty(originalPicture)){
            LambdaUpdateWrapper<AssetsEnclosure> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(AssetsEnclosure::getIs_del, GlobalData.ISDEL_YES)
                    .eq(AssetsEnclosure::getAssets_id, bean.getId())
                    .in(AssetsEnclosure::getFile_id, originalPicture);
            assetsEnclosureService.update(null, wrapper);
        }
        if(!CollectionUtils.isEmpty(picture)){
            for(MultipartFile file : picture){
                int id = fileManageService.fileUpload(file);
                AssetsEnclosure assetsEnclosure = new AssetsEnclosure();
                assetsEnclosure.setAssets_id(bean.getId());
                assetsEnclosure.setFile_id(id);
                assetsEnclosure.setFile_type(1);
                assetsEnclosure.setIs_del(GlobalData.ISDEL_NO);
                assetsEnclosureService.insert(assetsEnclosure);
            }
        }

        return ResultInfo.success();
    }

    /**
     * ??????
     */
    public ResultInfo delAssetsManage(AssetsManage bean) {
        AssetsManage dbBean = selectById(bean.getId());
        // ?????????
        dbBean.setUpdate_user(null == bean.getUserId() ? sysUserService.getUser().getId() : bean.getUserId());
        // ????????????
        dbBean.setUpdate_time(new Date());
        dbBean.setIs_del(GlobalData.ISDEL_YES);
        updateById(dbBean);
        return ResultInfo.success();
    }

    public Boolean delIds(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        LambdaUpdateWrapper<AssetsManage> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(AssetsManage::getIs_del, GlobalData.ISDEL_YES);
        wrapper.in(AssetsManage::getId, idList);
        if(update(null, wrapper) > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * ????????????
     *
     * @param response
     */
    public void downloadFile(HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String defFileName = "???????????????" + sdf.format(new Date());
        String fileName = "???????????????.xls";
        String filePath = fileUrl;
        try {
            // path????????????????????????????????????
            File file = new File(filePath + fileName);
            // ??????????????????
            String filename = file.getName();
            // ???????????????????????????
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // ??????????????????????????????
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath + fileName));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // ??????response
            response.reset();
            // ??????response???Header
            response.addHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(defFileName + ".xls", "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ????????????
     *
     * @param response
     */
    public ResultInfo exportFile(AssetsManage assetsManage,HttpServletResponse response) {
        List<Map<String, String>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "???????????????" + sdf.format(new Date());
        ExcelWriter writer = ExcelUtil.getWriter();

        PageInfo<AssetsManage> assetsManagePageInfo = toList(assetsManage.getPageNo(), assetsManage.getPageSize(), assetsManage);
        result = getAssetsManageData(assetsManagePageInfo);

        //ID,????????????,???????????????,????????????,????????????,??????,??????,?????????id,?????????id,??????,??????(??????)??????,????????????,????????????,????????????,????????????,????????????,IT??????,??????,??????,??????
//        writer.addHeaderAlias("ID", "ID");
        writer.addHeaderAlias("assets_number", "????????????");
        writer.addHeaderAlias("assets_name", "????????????");
        writer.addHeaderAlias("assets_type_id", "???????????????");
        writer.addHeaderAlias("child_asset", "????????????");
        writer.addHeaderAlias("dept_id", "??????");
//        writer.addHeaderAlias("person_liable", "?????????id");
        writer.addHeaderAlias("receiver", "?????????");

        writer.addHeaderAlias("purchase_company", "????????????");
        writer.addHeaderAlias("is_secrecy", "??????");
        writer.addHeaderAlias("assets_status", "????????????");

        writer.setOnlyAlias(true);
        writer.write(result, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }

    public ResultInfo saveEdit(MultipartFile fileApk) throws IOException {
        // 1.???????????????????????????
        InputStream inputStream = null;
        try {
            inputStream = fileApk.getInputStream();
        } catch (Exception e) {
            return ResultInfo.error("");
        }
// 2.??????HUtool ExcelUtil??????ExcelReader??????????????????sheet
        ExcelReader excelReader = ExcelUtil.getReader(inputStream, "??????????????????");
// ????????????????????????
// 3.????????????????????????????????????
        List<List<Object>> read = excelReader.read(1, excelReader.getRowCount());
        List<AssetsManage> list = new ArrayList<>();
        for (List<Object> objects : read) {
            AssetsManage am = new AssetsManage();
            am.setAssets_number(objects.get(0).toString());
            am.setAssets_type_id(toInt(objects.get(1)));
            am.setChild_asset(toInt(objects.get(2)));
            am.setAssets_name(toString(objects.get(3)));
            am.setDept_id(toInt(objects.get(4)));
            am.setPerson_liable(toString(objects.get(5)));
            am.setReceiver(toInt(objects.get(6)));
            am.setIs_secrecy(toInt(objects.get(7)));
            am.setPurchase_date(toDate(objects.get(8)));
            am.setFinance_number(toString(objects.get(9)));
            am.setSecrecy_number(toString(objects.get(10)));
            am.setPurchase_company(toInt(objects.get(11)));
            am.setUse_scope_id(toInt(objects.get(12)));
            am.setAssets_status(toInt(objects.get(13)));
            am.setIt_status(toInt(objects.get(14)));
            am.setPurpose(String.valueOf(toInt(objects.get(15))));
            am.setPosition(toString(objects.get(16)));
            am.setRemark(toString(objects.get(17)));
            list.add(am);
        }
        insertAll(list);
        return ResultInfo.success("");
    }

    private void insertAll(List<AssetsManage> list) {
        for (AssetsManage am : list) {
            addAssetsManage(am, null, null);
        }
    }

    private int toInt(Object obj) {
        int reault = 0;
        if (obj instanceof Number) {
            reault = ((Number) obj).intValue();
        }
        return reault;
    }

    private String toString(Object obj) {
        String reault = "";
        if (obj instanceof String) {
            reault = (String) obj;
        }
        return reault;

    }

    private Date toDate(Object obj) {
        Date reault = null;
        if (obj instanceof Date) {
            reault = (Date) obj;
        } else if (obj instanceof String) {
            SimpleDateFormat sdf = new SimpleDateFormat("");
            try {
                reault = DateUtils.parseDate((String) obj, dateFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return reault;
    }

    private List<Map<String, String>> getAssetsManageData(PageInfo<AssetsManage> assetsManagePageInfo) {
        List<Map<String, String>> result = new ArrayList<>();
        assetsManagePageInfo.getList().forEach(x -> {
            Map<String, String> map = new HashMap<>();

            map.put("assets_number", String.valueOf(x.getAssets_number()));
            map.put("assets_name", x.getAssets_name());
            map.put("assets_type_id", x.getAssetsTypeName());
            map.put("child_asset", String.valueOf(x.getChildAssetName()));

            if(StringUtils.isNotEmpty(x.getDeptName())){
                map.put("dept_id", x.getDeptName());
            }else {
                map.put("dept_id", "");
            }
                    if(StringUtils.isNotEmpty(x.getReceiverName())){
                        map.put("receiver",String.valueOf(x.getReceiverName()));
                    }else {
                        map.put("receiver","");
                    }

                    if(StringUtils.isNotEmpty(x.getPurchaseCompanyName())){
                        map.put("purchase_company", String.valueOf(x.getPurchaseCompanyName()));
                    }else {
                        map.put("purchase_company", "");
                    }


            if( x.getIs_secrecy() == 1 ){
                map.put("is_secrecy","??????");
            }else {
                map.put("is_secrecy","?????????");
            }
            map.put("assets_status", String.valueOf(x.getAssetsStatusName()));




            result.add(map);
        });

        return result;
    }

    /**
     * ????????????
     *
     * @param assetsManage
     * @return
     */
    public ResultInfo viewProperties(AssetsManage assetsManage) {
        AssetsManage dbAssetsManage = selectById(assetsManage.getId());
        return ResultInfo.success(dbAssetsManage);
    }

    /**
     * ???????????????
     *
     * @param assetsManage
     * @return
     */
    public ResultInfo viewContacts(AssetsManage assetsManage) {
        QueryWrapper<AssetsManageUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", GlobalData.ISDEL_YES);
        queryWrapper.eq("assets_id", assetsManage.getId());
        List<AssetsManageUser> dataList = assetsManageUserService.selectList(queryWrapper);
        return ResultInfo.success(dataList);
    }

    /**
     * ?????????????????????
     */
    public ResultInfo viewSupplierContract(AssetsManage assetsManage) {
        QueryWrapper<AssetsManageContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", GlobalData.ISDEL_YES);
        queryWrapper.eq("assets_id", assetsManage.getId());
        List<AssetsManageContract> dataList = assetsManageContractService.selectList(queryWrapper);
        return ResultInfo.success(dataList);

    }

    /**
     * ??????-???????????????
     * assets_manage_map
     */
    public ResultInfo viewAssetsConfig(AssetsManage assetsManage) {
        QueryWrapper<AssetsManageMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", GlobalData.ISDEL_YES);
        queryWrapper.eq("assets_id", assetsManage.getId());
        List<AssetsManageMap> dataList = assetsManageMapService.selectList(queryWrapper);
        return ResultInfo.success(dataList);
    }

    /**
     * ??????-?????????????????????
     *
     * @param assetsManage
     * @return
     */
    public ResultInfo viewEvent(AssetsManage assetsManage) {
        QueryWrapper<EventAssets> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", GlobalData.ISDEL_YES);
        queryWrapper.eq("assets_id", assetsManage.getId());
        List<EventAssets> dataList = eventAssetsService.selectList(queryWrapper);
        return ResultInfo.success(dataList);
    }

    /**
     * ??????-??????
     *
     * @param assetsManage
     * @return
     */
    public ResultInfo viewChange(AssetsManage assetsManage) {
        //change_assets
        QueryWrapper<ChangeAssets> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", GlobalData.ISDEL_YES);
        queryWrapper.eq("assets_id", assetsManage.getId());
        List<ChangeAssets> dataList = changeAssetsService.selectList(queryWrapper);
        return ResultInfo.success(dataList);
    }

    /**
     * ???????????????
     *
     * @param assetsManage
     * @return
     */
    public String genratorQrCode(AssetsManage assetsManage) {
        AssetsManage am = selectById(assetsManage.getId());
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(am);
        JSONObject qrCodeJson = new JSONObject();
        //???????????????AssetsManage ??????????????? ????????????data?????? ??????????????????
        qrCodeJson.put("assets_number",jsonObject.get("assets_number"));//     ????????????
        qrCodeJson.put("assets_type_id",jsonObject.get("assets_type_id")); //     ???????????????id
        qrCodeJson.put("child_asset",jsonObject.get("child_asset"));//     ????????????
        qrCodeJson.put("assets_name",jsonObject.get("assets_name"));//     ????????????
        qrCodeJson.put("dept_id",jsonObject.get("dept_id"));//     ??????
        qrCodeJson.put("person_liable",jsonObject.get("person_liable"));//     ?????????
        qrCodeJson.put("receiver",jsonObject.get("receiver"));//     ?????????
        qrCodeJson.put("assets_status",jsonObject.get("assets_status"));//     ????????????
        String qrCode = "??????????????????????????????";
        try {
            qrCode = QrCodeUtil.encode_QR_CODE(qrCodeJson.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qrCode;
    }

    //??????CI????????????????????????
    public List<OverviewVo> clGroupConfig() {
        return assetsManageMapper.clGroupConfig(null);
    }

    /**
     * ?????????????????????????????????
     *
     * @param bean
     * @return
     */
    public List<OverviewVo> getOverviewList(AssetsManage bean) {
        //??????CI????????????????????????
        List<OverviewVo> assetsManageVos = clGroupConfig();
        //???????????????????????????????????????????????????
        List<OverviewVo> getGroupSum = assetsManageMapper.getGroupSum(bean);
        Map<String, Integer> hashMap = new HashMap<>();
        for (OverviewVo vo : getGroupSum) {
            hashMap.put(String.valueOf(vo.getAssetsTypeId()), vo.getAssetsTypeSum());
        }

        for (OverviewVo vo : assetsManageVos) {
            Map<String, Integer> resultMap = new HashMap<>();
            String assetsTypeIds = StringUtils.trimToEmpty(vo.getAssetsTypeIds());
            String assetsType = StringUtils.trimToEmpty(vo.getAssetsTypeIds());
            if (!assetsTypeIds.contains(",")) {
                continue;
            }
            String[] idArr = StringUtils.split(assetsTypeIds, ",");
            String[] typeArr = StringUtils.split(assetsType, ",");
            for (String idStr : idArr) {
                resultMap.put(idStr, 0);//???????????????0?????????????????????
                Integer sum = hashMap.get(idStr);
                if (null != sum) {
                    resultMap.put(idStr, sum.intValue());
                }
            }
            vo.setResult(resultMap);
        }

        return assetsManageVos;
    }

    /**
     * ??????????????????
     *
     * @param id
     * @return
     */
    public ResultInfo getExpandInfo(Integer id) {
        QueryWrapper<AssetsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("id", id);
        AssetsType at = assetsTypeService.selectOne(queryWrapper);
        if (null != at && null != at.getForm_id() && at.getForm_id().intValue() > 0) {
            return assetsExpandFormService.getExpandSubList(at.getForm_id());
        }
        return ResultInfo.success();
    }

    /**
     * @param assetsTypeId ???????????????id
     * @param childAsset   ????????????
     */
    public List<AssetsManage> getAssetsManageByConfig(int assetsTypeId, int childAsset) {
        QueryWrapper<AssetsManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", GlobalData.ISDEL_YES);
        queryWrapper.eq("assets_type_id", assetsTypeId);
        queryWrapper.eq("child_asset", childAsset);
        List<AssetsManage> dataList = selectList(queryWrapper);

        return dataList;
    }

    /**
     * ?????????????????????????????????
     * @param code
     * @return
     */
    public List<AssetsManage> getAssetsByType(String code){
        LambdaUpdateWrapper<AssetsType> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetsType::getCode, code);
        AssetsType assetsType = assetsTypeService.selectOne(wrapper);
        LambdaUpdateWrapper<AssetsManage> amwrapper = new LambdaUpdateWrapper<>();
        if(StringUtils.isNotEmpty(code)){
            amwrapper.eq(AssetsManage::getAssets_type_id, assetsType.getId());
        }
        amwrapper.eq(AssetsManage::getIs_del, GlobalData.ISDEL_NO);
        return selectList(amwrapper);
    }

    /**
     * ????????????????????????
     * @param id
     * @return
     */
    public List<AssetsManage> getSeats(Integer id){
        LambdaUpdateWrapper<AssetsType> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AssetsType::getCode, AssetsTypeEnum.ASSETS_ENCLOSURE.getValue());
        AssetsType assetsType = assetsTypeService.selectOne(wrapper);
        LambdaUpdateWrapper<AssetsManage> amwrapper = new LambdaUpdateWrapper<>();
        amwrapper.eq(AssetsManage::getCabinet_id, id);
        amwrapper.eq(AssetsManage::getAssets_type_id, assetsType.getId());
        amwrapper.eq(AssetsManage::getIs_del, GlobalData.ISDEL_NO);
        return selectList(amwrapper);
    }

    /**
     * ??????????????????
     * ?????????PZ+?????????????????????+?????????????????????+??????+???????????????
     * @param assetsTypeId
     * @param assetsChildId
     * @return
     */
    private String createCode(Integer assetsTypeId, Integer assetsChildId){
        AssetsType assetsType = assetsTypeService.selectById(assetsTypeId);
        AssetsChildren assetsChildren = assetsChildrenService.selectById(assetsChildId);
        Long number = selectCount(new LambdaUpdateWrapper<AssetsManage>()
                .apply(true, "TO_DAYS(NOW())-TO_DAYS(create_time) = 0")
                .eq(AssetsManage::getAssets_type_id, assetsTypeId)
                .eq(AssetsManage::getChild_asset, assetsChildId))+1;
        return CODE_FIX + "-" + assetsType.getCode() + "-" + assetsChildren.getCode() + "-" + DataUtils.getToday() + number;
    }

    public ResultInfo changePrint(Integer id){
        AssetsManage assetsManage = assetsManageMapper.selectById(id);
        assetsManage.setIs_print("0");
        assetsManageMapper.updateById(assetsManage);
        return ResultInfo.success();
    }




    /**
    *????????????????????????
    * Author wzn
    * Date 2022/11/1 11:36
    */
    public ResultInfo exportAssetsExcelTemplate(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();

        //????????????
        QueryWrapper<AssetsType> queryWrapper = new QueryWrapper() ;
        queryWrapper.eq("is_del","0") ;
        List<AssetsType> assetsTypes = assetsTypeMapper.selectList(queryWrapper) ;

        //????????????
        AssetsChildren beanParam  = new AssetsChildren() ;
        List<AssetsChildren> assetsChildrenVos = assetsChildrenMapper.toList(beanParam);

        //??????
        List<SysDepartment> sysDepartmentList = sysDepartmentService.getAll() ;

        //????????????
        List<AssetsStatus> assetsStatusList = assetsStatusService.getAll() ;

        //IT??????
        List<AssetsItStatus> assetsItStatusList = statusService.getAll() ;

        List<SysUser> sysUsers = sysUserService.getUsers() ;
        row.put("???????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);
        row.put("??????(??????)??????", StringUtils.EMPTY);
        row.put("IT??????", StringUtils.EMPTY);

        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();

        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();
        //?????????????????? ??????????????????
        int firstRow = 1;
        //??????
        String[] bm = {"???", "???"};
//        String[] fblx = {"????????????", "????????????"};
        String[] statusList = assetsTypes.stream().map(AssetsType::getName).toArray(String[]::new);
        String[] statusList2 = assetsChildrenVos.stream().map(AssetsChildren::getName).toArray(String[]::new);
        String[] statusList3 = sysDepartmentList.stream().map(SysDepartment::getName).toArray(String[]::new);
        String[] statusList4 = assetsStatusList.stream().map(AssetsStatus::getStatus_name).toArray(String[]::new);
        String[] statusList5 = assetsItStatusList.stream().map(AssetsItStatus::getStatus_name).toArray(String[]::new);
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList, firstRow, 0));
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList2, firstRow, 1));
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList3, firstRow, 3));
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList4, firstRow, 4));
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList5, firstRow, 7));
        writer.addValidationData(setSelectCol(styleSet, sheet, bm, firstRow, 5));


        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("1234.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }


    /**
     * ??????????????????
     */
    private DataValidation setSelectCol(StyleSet styleSet, Sheet sheet, String[] capacityAvi, int firstRow, int firstCol) {

        CellStyle cellStyle = styleSet.getCellStyle();
        //????????????
        cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));

        DataValidationHelper helper = sheet.getDataValidationHelper();
        // ???????????????????????????
//        String[] capacityAvi = {"???", "???"};
        DataValidationConstraint capacityConstraint = helper.createExplicitListConstraint(capacityAvi);
        //????????????????????????????????????????????????
        CellRangeAddressList capacityList = new CellRangeAddressList(firstRow, 5000, firstCol, firstCol);
        return helper.createValidation(capacityConstraint, capacityList);
    }





    @Transactional
    public ResultInfo importAssetsList(MultipartFile file){
        try {
            //fileName ?????????
            String fileName = file.getOriginalFilename();
            boolean xlsx = fileName.endsWith(".xlsx");
            boolean xlsx2 = fileName.endsWith(".xls");
            if (xlsx == false && xlsx2 == false) {
                return ResultInfo.error("????????????.xlsx???????????????");
            }
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ExcelReader reader = ExcelUtil.getReader(inputStream,0);
            List<List<Object>> data = reader.read();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = data.size();

            List<AssetsManage> success = new ArrayList<>();
            List<AssetsManage> error = new ArrayList<>();
            List<AssetsManage> all  = new ArrayList<>();


            if(size >1){
                for(int i = 1; i < size; i++){
                    AssetsManage assetsManage = new AssetsManage();
                    List<Object> line = data.get(i);
                    if(StringUtils.isEmpty(line.get(0).toString()) || StringUtils.isEmpty(line.get(1).toString()) || StringUtils.isEmpty(line.get(2).toString())||
                            StringUtils.isEmpty(line.get(3).toString())||StringUtils.isEmpty(line.get(4).toString())||StringUtils.isEmpty(line.get(5).toString())||
                            StringUtils.isEmpty(line.get(6).toString()) || StringUtils.isEmpty(line.get(7).toString()) ){



                        assetsManage.setAssetsTypeName(line.get(0).toString());
                        assetsManage.setChildAssetName( line.get(1).toString());
                        assetsManage.setAssets_name( line.get(2).toString());
                        assetsManage.setDeptName( line.get(3).toString());


                        error.add(assetsManage);
                        assetsManage.setIs_success("??????");
                        all.add(assetsManage);
                    }else {
                        //????????????
                        QueryWrapper<AssetsType> queryWrapper = new QueryWrapper() ;
                        queryWrapper.eq("name",line.get(0).toString()) ;
                        List<AssetsType> assetsTypes = assetsTypeMapper.selectList(queryWrapper) ;


                        //????????????
                        QueryWrapper<AssetsChildren> queryWrapper2 = new QueryWrapper() ;
                        queryWrapper2.eq("name",line.get(1).toString()) ;
                        List<AssetsChildren> assetsChildrenList = assetsChildrenMapper.selectList(queryWrapper2) ;
                        if(!CollectionUtils.isEmpty(assetsChildrenList)){
                                    if(assetsTypes.get(0).getId() != assetsChildrenList.get(0).getAssets_type_id()){
                                        throw new CustomException("???"+i+"?????????,????????????,???????????????????????????!");
                                    }
                        }

                        //??????
                        QueryWrapper<SysDepartment> queryWrapper3 = new QueryWrapper() ;
                        queryWrapper3.eq("name",line.get(3).toString()) ;
                        List<SysDepartment> sysDepartmentList = sysDepartmentService.selectList(queryWrapper3) ;
                        //????????????
                        QueryWrapper<AssetsStatus> queryWrapper4 = new QueryWrapper() ;
                        queryWrapper4.eq("status_name",line.get(4).toString()) ;
                        List<AssetsStatus> assetsStatusList = assetsStatusService.selectList(queryWrapper4) ;
                        //IT??????
                        QueryWrapper<AssetsItStatus> queryWrapper5 = new QueryWrapper() ;
                        queryWrapper5.eq("status_name",line.get(7).toString()) ;
                        List<AssetsItStatus> assetsItStatusList = statusService.selectList(queryWrapper5) ;


                        assetsManage.setAssets_type_id(assetsTypes.get(0).getId());
                        assetsManage.setChild_asset(assetsChildrenList.get(0).getId());
                        assetsManage.setAssets_name( line.get(2).toString());
                        assetsManage.setDept_id( sysDepartmentList.get(0).getId());
                        assetsManage.setAssets_status( assetsStatusList.get(0).getId());
                        if("???".equals(line.get(5).toString())){
                            assetsManage.setIs_secrecy(1);
                        }else {
                            assetsManage.setIs_secrecy(0);
                        }
                        assetsManage.setPurchase_date(sdf.parse(line.get(6).toString()));
                        assetsManage.setIt_status(assetsItStatusList.get(0).getId());



                        // ????????????
                        assetsManage.setIs_del(String.valueOf(0));
                        // ????????????
                        assetsManage.setCreate_time(new Date());
                        // ???????????????????????????
                        assetsManage.setIs_inventory(1);
                        //????????????
                        assetsManage.setIs_print("1");
                        // ?????????
                        assetsManage.setCreate_user(sysUserService.getUser().getId());
                        //????????????
                        assetsManage.setAssets_number(createCode(assetsManage.getAssets_type_id(), assetsManage.getChild_asset()));

                        assetsManage.setIs_success("??????");
                        success.add(assetsManage);
                        all.add(assetsManage);
                        assetsManageMapper.insert(assetsManage);


                    }
                }
            }
            AssetsManage p =  new AssetsManage();
            p.setSuccess(success);
            p.setError(error);
            p.setAll(all);
            return ResultInfo.success(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("?????????????????????");

    }

}
