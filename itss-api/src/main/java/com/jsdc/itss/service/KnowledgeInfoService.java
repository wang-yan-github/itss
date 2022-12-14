package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.KnowledgeInfoDao;
import com.jsdc.itss.enums.KnowledgeEnums;
import com.jsdc.itss.enums.KnowledgeExamineEnums;
import com.jsdc.itss.enums.KnowledgeRangeEnums;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.KnowledgeVo;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.KnowledgeInfoVo;
import com.jsdc.itss.vo.KnowledgePageInfoVo;
import com.jsdc.itss.vo.ResultInfo;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Service
@Transactional
public class KnowledgeInfoService extends BaseService<KnowledgeInfoDao, KnowledgeInfo> {

    @Autowired
    private KnowledgeInfoMapper knowledgeInfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserService  sysUserService;
    @Autowired
    private KnowledgeScoringMapper knowledgeScoringMapper;
    @Autowired
    private KnowledgeApproverMapper knowledgeApproverMapper;
    @Autowired
    private  SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private  EventBillingDesignateMapper eventBillingDesignateMapper;
    @Autowired
    private KnowledgeRangeInfoMapper knowledgeRangeInfoMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private  SysDepartmentMapper sysDepartmentMapper ;

    @Autowired
    private EventServiceManageMapper eventServiceManageMapper;
    @Autowired
    private KnowledgeCategoryMapper knowledgeCategoryMapper;
    @Autowired
    private KnowledgeRangeMapper knowledgeRangeMapper;
    @Autowired
    private KnowledgeFileMapper knowledgeFileMapper;
    @Autowired
    private KnowLedgeEsService knowLedgeEsService;
    @Autowired
    private FileManageService fileManageService;

    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * create by xuaolong
     * description: ???????????????????????????
     * @return resultList
     */
    public List<KnowledgeCategory> queryWithTree(KnowledgeCategory knowledgeCategory) {
        QueryWrapper<KnowledgeCategory> queryWrapper = new QueryWrapper<>();
        if (knowledgeCategory.getName() != null && !knowledgeCategory.getName().equals("") ){
            queryWrapper.like("name",knowledgeCategory.getName());
        }
        queryWrapper.eq("is_del","0");
        List<KnowledgeCategory> list = knowledgeCategoryMapper.selectList(queryWrapper);
        List<KnowledgeCategory> resultList = new ArrayList<>();
        for (int i = 0 ; i< list.size();i++){
            KnowledgeCategory knowledgeCategory1 =list.get(i);
            if (knowledgeCategory1.getParent_id()==0){
                resultList.add(knowledgeCategory1);
                setChildList(knowledgeCategory1,list);
            }
        }
        return resultList;
    }
    private void setChildList( KnowledgeCategory knowledgeCategory, List<KnowledgeCategory> knowledgeCategoryList){
        for(KnowledgeCategory temp:knowledgeCategoryList){
            if(temp.getParent_id()==knowledgeCategory.getId()){
                knowledgeCategory.getChildren().add(temp);
                setChildList(temp,knowledgeCategoryList);
            }
        }
    }



    /**
     * ????????? ????????????????????????
     * ????????? xuaolong
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo getByPage(KnowledgeInfo knowledgeInfo,int pageIndex, int pageSize){
        Integer  userId = null ;
        if (null != knowledgeInfo.getUserId()){
            userId = knowledgeInfo.getUserId();
        }else {
            userId = sysUserService.getUser().getId();
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<KnowledgePageInfoVo> list = knowledgeInfoMapper.selectKnowledgeByPage(knowledgeInfo,userId);
        Map<Integer, SysUser> userMap = getUserNameMap();

        if(!CollectionUtils.isEmpty(userMap)){
            for (KnowledgePageInfoVo temp : list){
                temp.setApprover_name(StringUtils.EMPTY);
                if(null == temp.getApprover_id() || userMap.get(temp.getApprover_id()) == null){
                    continue;
                }
                temp.setApprover_name(userMap.get(temp.getApprover_id()).getUser_name());
            }
        }

        PageInfo<KnowledgePageInfoVo> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(pageInfo);
    }
    /**
     * ????????? ??????????????????
     * ????????? xuaolong
     * @param knowledgeInfo
     * @return
     */
    public  ResultInfo addKnowledgeInfo(KnowledgeInfo knowledgeInfo, List<MultipartFile> files){
        knowledgeInfo.setCreate_time(new Date());
        knowledgeInfo.setCreate_user(sysUserService.getUser().getId());
        knowledgeInfo.setIs_del("0");
        if (null != knowledgeInfo.getStatus()) {
            knowledgeInfo.setStatus("2");
        }else {
            knowledgeInfo.setStatus("1");
        }

        knowledgeInfo.setKnowledge_status(1);
        int result = knowledgeInfoMapper.insert(knowledgeInfo);
        if ("2".equals( knowledgeInfo.getStatus()) && "1".equals(knowledgeInfo.getIs_full_search())) {
            knowLedgeEsService.addOrUpdateDoc(knowledgeInfo);
        }

        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                KnowledgeFile knowledgeFile = new KnowledgeFile();
                knowledgeFile.setKnowledge_id(knowledgeInfo.getId());
                knowledgeFile.setFile_id(id);
                knowledgeFileMapper.insert(knowledgeFile);
            }
        }
        if (result == 1){
            return ResultInfo.success("????????????");
        }
        return ResultInfo.error("???????????????");
    }


    /**
     * ????????? ??????????????????
     * ????????? xuaolong
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo updateKnowledgeInfo(KnowledgeInfo knowledgeInfo, List<MultipartFile> multipartFiles){
        knowledgeInfo.setUpdate_user(sysUserService.getUser().getId());
        knowledgeInfo.setUpdate_time(new Date());
        int result =  knowledgeInfoMapper.updateById(knowledgeInfo);

//        UpdateWrapper<KnowledgeFile> updateWrapper =new UpdateWrapper<>();
//        updateWrapper.eq("knowledge_id",knowledgeInfo.getId());
//        updateWrapper.set("is_del","1");
//        knowledgeFileMapper.update(null,updateWrapper);
        if ("2".equals(knowledgeInfo.getStatus()) && "1".equals(knowledgeInfo.getIs_full_search())){
            knowLedgeEsService.addOrUpdateDoc(knowledgeInfo);
        }
//        List<Integer> fileIds = knowledgeInfo.getFileIds();
//        for (Integer temp :fileIds){
//            KnowledgeFile knowledgeFile =new KnowledgeFile();
//            knowledgeFile.setKnowledge_id(knowledgeInfo.getId());
//            knowledgeFile.setFile_id(temp);
//            knowledgeFile.setCreate_time(new Date());
//            knowledgeFile.setCreate_user(sysUserService.getUser().getId());
//            knowledgeFile.setIs_del("0");
//            knowledgeFileMapper.insert(knowledgeFile);
//        }
        //???????????? ??????
        //???????????? ????????????????????????
        String uploadedFiles = knowledgeInfo.getUploadedFiles();
        // ?????????????????? ??????
        List<KnowledgeFile> files = knowledgeFileMapper.selectList(Wrappers.<KnowledgeFile>lambdaQuery()
                .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId()));
        if (!CollectionUtils.isEmpty(files)){
            List<FileManage> fileManages = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery()
                    .in(FileManage::getId, files.stream().map(KnowledgeFile::getFile_id).collect(Collectors.toList()))
            );
            List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
            if (Base.notEmpty(uploadedFiles)) {
                List<String> ids = Arrays.asList(uploadedFiles.split(","));
                original.removeAll(ids);
            }
            if (!CollectionUtils.isEmpty(original)) {
                knowledgeFileMapper.update(null, Wrappers.<KnowledgeFile>lambdaUpdate()
                        .set(KnowledgeFile::getIs_del, GlobalData.ISDEL_YES)
                        .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId())
                        .in(KnowledgeFile::getFile_id, original));
            }
        }


        if (!CollectionUtils.isEmpty(multipartFiles)) {
            for (MultipartFile file : multipartFiles) {
                int id = fileManageService.fileUpload(file);
                KnowledgeFile knowledgeFile = new KnowledgeFile();
                knowledgeFile.setKnowledge_id(knowledgeInfo.getId());
                knowledgeFile.setFile_id(id);
                knowledgeFileMapper.insert(knowledgeFile);
            }
        }
        if (result == 1){
            return ResultInfo.success();
        }
        return ResultInfo.error("???????????????");
    }


    public List<KnowledgeRange> rangeList(){

        QueryWrapper<KnowledgeRange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<KnowledgeRange> list1 =knowledgeRangeMapper.selectList(queryWrapper);
        for (int i = 0 ; i < list1.size() ; i++){
            // ??????????????????
            Map<Integer, SysUser> userMap = getUserNameMap();
            // ?????????????????????
            Map<Integer, SysRole> roleMap = getRoleMap();
            // ????????????
            Map<Integer, SysDepartment> deptMap = getDeptMap();
            // ??????????????????
            Map<Integer, EventServiceManage> serviceMap = getServiceMap();

            List<KnowledgeRangeInfo> list = knowledgeRangeInfoMapper.selectList(Wrappers.<KnowledgeRangeInfo>lambdaQuery()
                    .eq(KnowledgeRangeInfo::getKnowledge_range_id, list1.get(i).getId()));
            String role = "????????????";
            String depart ="????????????";
            String group = "??????????????????";
            for (KnowledgeRangeInfo temp: list){
                if( null == temp.getRange_type()){
                    continue;
                }
                switch (temp.getRange_type()){
                    case "1":
                        if(!CollectionUtils.isEmpty(roleMap) && null != roleMap.get(temp.getFk_id())){
                            role += roleMap.get(temp.getFk_id()).getName() + ";";
                        }
                        break;
                    case "2":
                        if(!CollectionUtils.isEmpty(deptMap) && null != deptMap.get(temp.getFk_id())){
                            depart += deptMap.get(temp.getFk_id()).getName() + ";";
                        }
                        break;
                    case "3":
                        if(!CollectionUtils.isEmpty(serviceMap) && null != serviceMap.get(temp.getFk_id())){
                            group += serviceMap.get(temp.getFk_id()).getName() + ";";
                        }
                        break;
                    default:
                        break;
                }
            }
            list1.get(i).setDes(role + depart + group );
        }


        return list1;

    }

    /**
     * create by xuaolong
     * description: ????????????????????????
     * @param id
     * @return
     */
    public ResultInfo getKnowledgeInfoById(Integer id){
        KnowledgeInfoVo knowledgeInfo = knowledgeInfoMapper.getKnowledgeById(id);
        if (null == knowledgeInfo){
            return ResultInfo.error("????????????????????????");
        }
        // ??????????????????
        Map<Integer, SysUser> userMap = getUserNameMap();
        // ?????????????????????
        Map<Integer, SysRole> roleMap = getRoleMap();
        // ????????????
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        // ??????????????????
        Map<Integer, EventServiceManage> serviceMap = getServiceMap();
        List<KnowledgeRangeInfo> list = knowledgeRangeInfoMapper.selectList(Wrappers.<KnowledgeRangeInfo>lambdaQuery()
                .eq(KnowledgeRangeInfo::getKnowledge_range_id, knowledgeInfo.getKnowledge_range_id()));
        String role = "????????????";
        String depart ="????????????";
        String group = "??????????????????";
        for (KnowledgeRangeInfo temp: list){
            if( null == temp.getRange_type()){
                continue;
            }
            switch (temp.getRange_type()){
                case "1":
                    if(!CollectionUtils.isEmpty(roleMap) && null != roleMap.get(temp.getFk_id())){
                        role += roleMap.get(temp.getFk_id()).getName() + ";";
                    }
                    break;
                case "2":
                    if(!CollectionUtils.isEmpty(deptMap) && null != deptMap.get(temp.getFk_id())){
                        depart += deptMap.get(temp.getFk_id()).getName() + ";";
                    }
                    break;
                case "3":
                    if(!CollectionUtils.isEmpty(serviceMap) && null != serviceMap.get(temp.getFk_id())){
                        group += serviceMap.get(temp.getFk_id()).getName() + ";";
                    }
                    break;
                default:
                    break;
            }
        }
        if(role.equals("????????????")){
            role = "????????????????????????";
        }
        if (depart.equals("????????????")){
            depart = "????????????????????????";
        }
        if(group.equals("??????????????????")){
            group = "??????????????????????????????";
        }

        knowledgeInfo.setKnowledge_range("??????????????????" + role + depart + group + "???");

        // ?????????????????????????????????
        if(!CollectionUtils.isEmpty(userMap)){
            if (null != knowledgeInfo.getApprover_id() && null != userMap.get(knowledgeInfo.getApprover_id())){
                knowledgeInfo.setApprover_name(userMap.get(knowledgeInfo.getApprover_id()).getName());
            }
            if (null != knowledgeInfo.getUpdate_user() && null != userMap.get(knowledgeInfo.getUpdate_user())){
                knowledgeInfo.setUpdateUserName(userMap.get(knowledgeInfo.getUpdate_user()).getName());
            }
            if (null != knowledgeInfo.getRater() && null != userMap.get(knowledgeInfo.getRater())){
                knowledgeInfo.setRater_name(userMap.get(knowledgeInfo.getRater()).getName());
            }
        }

        // ?????????????????? ??????
        List<KnowledgeFile> files = knowledgeFileMapper.selectList(Wrappers.<KnowledgeFile>lambdaQuery()
                .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId()));
        if(CollectionUtils.isEmpty(files)){
            return ResultInfo.success(knowledgeInfo);
        }

        List<FileManage> fileManages1 = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery()
                .in(FileManage::getId, files.stream().map(KnowledgeFile::getFile_id).collect(Collectors.toList()))
        );
        fileManages1.forEach(x -> {
            String path = uploadPath + File.separator + x.getFile_name();
            File file = new File(path);
            if (file.exists()) {
                String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                x.setBase64(base64);
            } else {
                x.setBase64("");
            }
        });
        knowledgeInfo.setFiles(fileManages1);
        return ResultInfo.success(knowledgeInfo);
    }

    /**
     * ????????? ?????????????????????)
     * ????????? xuaolong
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo toExamineKnowledge(KnowledgeInfo knowledgeInfo, List<MultipartFile> multipartFiles){
        knowledgeInfo.setStatus(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_2.getType());
        knowledgeInfo.setApprover_id(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId(): knowledgeInfo.getUserId());
        knowledgeInfo.setApproval_time(new Date());
        knowledgeInfo.setUpdate_time(new Date());
        knowledgeInfo.setUpdate_user(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId(): knowledgeInfo.getUserId());
        int result = knowledgeInfoMapper.updateById(knowledgeInfo);
        //???????????? ??????
        //???????????? ????????????????????????
        String uploadedFiles = knowledgeInfo.getUploadedFiles();
        // ?????????????????? ??????
        List<KnowledgeFile> files = knowledgeFileMapper.selectList(Wrappers.<KnowledgeFile>lambdaQuery()
                .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId()));
        if (!CollectionUtils.isEmpty(files)){
            List<FileManage> fileManages = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery()
                    .in(FileManage::getId, files.stream().map(KnowledgeFile::getFile_id).collect(Collectors.toList()))
            );
            List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
            if (Base.notEmpty(uploadedFiles)) {
                List<String> ids = Arrays.asList(uploadedFiles.split(","));
                original.removeAll(ids);
            }
            if (!CollectionUtils.isEmpty(original)) {
                knowledgeFileMapper.update(null, Wrappers.<KnowledgeFile>lambdaUpdate()
                        .set(KnowledgeFile::getIs_del, GlobalData.ISDEL_YES)
                        .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId())
                        .in(KnowledgeFile::getFile_id, original));
            }
        }

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            for (MultipartFile file : multipartFiles) {
                int id = fileManageService.fileUpload(file);
                KnowledgeFile knowledgeFile = new KnowledgeFile();
                knowledgeFile.setKnowledge_id(knowledgeInfo.getId());
                knowledgeFile.setFile_id(id);
                knowledgeFileMapper.insert(knowledgeFile);
            }
        }

        KnowledgeInfo temp = knowledgeInfoMapper.selectById(knowledgeInfo.getId());
        if("1".equals(temp.getIs_full_search())){
            knowLedgeEsService.addOrUpdateDoc(temp);
        }

        KnowledgeScoring knowledgeScoring =new KnowledgeScoring();
        knowledgeScoring.setKnowledge_info_id(knowledgeInfo.getId());
        knowledgeScoring.setRater(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId(): knowledgeInfo.getUserId());
        knowledgeScoring.setScoring_time(new Date());
        knowledgeScoring.setScore(knowledgeInfo.getScore());
        knowledgeScoring.setComment(knowledgeInfo.getComment());
        knowledgeScoring.setCreate_time(new Date());
        knowledgeScoring.setCreate_user(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId(): knowledgeInfo.getUserId());
        knowledgeScoring.setIs_del("0");
        QueryWrapper<KnowledgeScoring> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("knowledge_info_id",knowledgeInfo.getId());
        KnowledgeScoring knowledgeScoring1 =knowledgeScoringMapper.selectOne(queryWrapper);
        int addScoring = 0;
        if (knowledgeScoring1 == null){
            addScoring = knowledgeScoringMapper.insert(knowledgeScoring);
        }else {
            int id =knowledgeScoring1.getId();
            knowledgeScoring.setId(id);
            addScoring = knowledgeScoringMapper.updateById(knowledgeScoring);
        }
        if (result == 1 && addScoring ==1){
            return ResultInfo.success();
        }
        return ResultInfo.error("???????????????");
    }

    /**
     * ????????? ?????????????????????)
     * ????????? xuaolong
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo toRefuseExamineKnowledge(KnowledgeInfo knowledgeInfo, List<MultipartFile> multipartFiles){
        //????????????????????? ???????????????
        knowledgeInfo.setStatus(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_3.getType());
        knowledgeInfo.setApprover_id(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId() :  knowledgeInfo.getUserId());
        knowledgeInfo.setApproval_time(new Date());
        int result =  knowledgeInfoMapper.updateById(knowledgeInfo);

        //???????????? ??????
        //???????????? ????????????????????????
        String uploadedFiles = knowledgeInfo.getUploadedFiles();
        // ?????????????????? ??????
        List<KnowledgeFile> files = knowledgeFileMapper.selectList(Wrappers.<KnowledgeFile>lambdaQuery()
                .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId()));
        if (!CollectionUtils.isEmpty(files)){
            List<FileManage> fileManages = fileManageService.selectList(Wrappers.<FileManage>lambdaQuery()
                    .in(FileManage::getId, files.stream().map(KnowledgeFile::getFile_id).collect(Collectors.toList()))
            );
            List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
            if (Base.notEmpty(uploadedFiles)) {
                List<String> ids = Arrays.asList(uploadedFiles.split(","));
                original.removeAll(ids);
            }
            if (!CollectionUtils.isEmpty(original)) {
                knowledgeFileMapper.update(null, Wrappers.<KnowledgeFile>lambdaUpdate()
                        .set(KnowledgeFile::getIs_del, GlobalData.ISDEL_YES)
                        .eq(KnowledgeFile::getKnowledge_id, knowledgeInfo.getId())
                        .in(KnowledgeFile::getFile_id, original));
            }
        }

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            for (MultipartFile file : multipartFiles) {
                int id = fileManageService.fileUpload(file);
                KnowledgeFile knowledgeFile = new KnowledgeFile();
                knowledgeFile.setKnowledge_id(knowledgeInfo.getId());
                knowledgeFile.setFile_id(id);
                knowledgeFileMapper.insert(knowledgeFile);
            }
        }
        KnowledgeScoring knowledgeScoring =new KnowledgeScoring();
        knowledgeScoring.setKnowledge_info_id(knowledgeInfo.getId());
        knowledgeScoring.setRater(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId() :  knowledgeInfo.getUserId());
        knowledgeScoring.setScoring_time(new Date());
        knowledgeScoring.setScore(knowledgeInfo.getScore());
        knowledgeScoring.setComment(knowledgeInfo.getComment());
        knowledgeScoring.setCreate_time(new Date());
        knowledgeScoring.setCreate_user(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId() :  knowledgeInfo.getUserId());
        knowledgeScoring.setIs_del("0");
        QueryWrapper<KnowledgeScoring> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("knowledge_info_id",knowledgeInfo.getId());
        KnowledgeScoring knowledgeScoring1 =knowledgeScoringMapper.selectOne(queryWrapper);
        int addScoring = 0;
        if (knowledgeScoring1 == null){
            addScoring = knowledgeScoringMapper.insert(knowledgeScoring);
        }else {
            int id =knowledgeScoring1.getId();
            knowledgeScoring.setId(id);
            addScoring = knowledgeScoringMapper.updateById(knowledgeScoring);
        }
        if (result == 1 && addScoring ==1){
            return ResultInfo.success();
        }
        return ResultInfo.error("???????????????");
    }

    /**
     * ????????? ???????????????????????????
     * ????????? xuaolong
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo getStatusByPage(KnowledgeInfo knowledgeInfo,int pageIndex, int pageSize){
//
//        PageHelper.startPage(pageIndex, pageSize);
        List<KnowledgePageInfoVo> resultList = getKnowledgePageInfoVos(knowledgeInfo);
        List<KnowledgePageInfoVo> result = new ArrayList<>();
        for (KnowledgePageInfoVo temp : resultList){
            if (temp.getStatus().equals(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_1.getType())){
                result.add(temp);
            }
        }
        return ResultInfo.success(getPageInfo(pageIndex, pageSize, result));
    }
    public Integer count(KnowledgeInfo knowledgeInfo){
        List<KnowledgePageInfoVo> resultList = getKnowledgePageInfoVos(knowledgeInfo);
        List<KnowledgePageInfoVo> result = new ArrayList<>();
        for (KnowledgePageInfoVo temp : resultList){
            if (temp.getStatus().equals(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_1.getType())){
                result.add(temp);
            }
        }
        return result.size();
    }


    /**
     * ????????????
     * @param knowledgeInfo
     * @return
     */
    private List<KnowledgePageInfoVo> getKnowledgePageInfoVos(KnowledgeInfo knowledgeInfo) {
        //????????????ID
        Integer userId = null;
        if (null == knowledgeInfo.getUserId()){
            userId = sysUserService.getUser().getId();
        }else {
            userId = knowledgeInfo.getUserId();
        }


        //????????????????????? ?????????????????????
//        if (StringUtils.isEmpty(knowledgeInfo.getStatus())){
//            knowledgeInfo.setStatus(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_1.getType());
//        }
        //??????????????????
        List<KnowledgePageInfoVo> list = knowledgeInfoMapper.getStatusKnowledgeByPage(knowledgeInfo);
        List<KnowledgePageInfoVo> resultList = new ArrayList<>();
        //???????????????????????????????????????????????????????????????
        for (KnowledgePageInfoVo temp: list){
            if(null == temp.getKnowledge_category_id()){
                continue;
            }
            int knowledge_category_id = temp.getKnowledge_category_id();
            QueryWrapper<KnowledgeApprover> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("knowledge_category_id",knowledge_category_id);
            List<KnowledgeApprover> knowledgeApproverList = knowledgeApproverMapper.selectList(queryWrapper);
            for (KnowledgeApprover temp1:knowledgeApproverList){
                //??????????????????????????????????????????????????????
                if (temp1.getApprover_id() == userId){
                    resultList.add(temp);
                    break;
                }
            }
        }
        return resultList;
    }

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


    /**
     * ????????? ????????????????????????
     * ????????? xuaolong
     * @param knowledgeInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultInfo getInsideKnowledge(KnowledgeInfo knowledgeInfo,int pageIndex, int pageSize){
        int  userId  = -1;
        //??????
//        PageHelper.startPage(pageIndex, pageSize);
        List<KnowledgePageInfoVo> result = getKnowledgePageInfoVos(knowledgeInfo, userId);
        List<KnowledgePageInfoVo> resultList = new ArrayList<>();
        for (KnowledgePageInfoVo temp : result){
            if ("2".equals(temp.getStatus())){
                resultList.add(temp);
            }
        }
        return ResultInfo.success(getPageInfo(pageIndex, pageSize, resultList));
    }

    /**
     * ???????????? ????????????????????????
     * @param knowledgeInfo
     * @param userId
     * @return
     */
    private List<KnowledgePageInfoVo> getKnowledgePageInfoVos(KnowledgeInfo knowledgeInfo, int userId) {
        //??????????????????
        List<KnowledgePageInfoVo> list =knowledgeInfoMapper.selectKnowledgeByPage(knowledgeInfo,userId);
        //????????????
        List<KnowledgePageInfoVo> result = new ArrayList<>();

        //????????????ID???????????????????????????
        if (null != knowledgeInfo.getUserId()){
            userId= knowledgeInfo.getUserId();
        }else {
            userId = sysUserService.getUser().getId();
        }

        //????????????ID????????????ID??????
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper =new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",userId);
        List <SysUserRole> sysUserRoleList =sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);

        //??????????????????????????????ID
        int departmentId = sysUserMapper.selectById(userId).getDepartment_id();

        //???????????????????????????????????????ID
        QueryWrapper<EventBillingDesignate> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("engineer_userid",userId);
        List<EventBillingDesignate> eventBillingDesignateList =eventBillingDesignateMapper.selectList(queryWrapper);


        //?????????????????????????????? ??????????????????ID ??????ID ????????????ID?????????
        for (KnowledgePageInfoVo temp :list ){
            //???????????????????????????ID
            if(null == knowledgeInfoMapper.selectById(temp.getId()) || null == knowledgeInfoMapper.selectById(temp.getId()).getKnowledge_range_id()){
                continue;
            }
            int knowledge_range_id =knowledgeInfoMapper.selectById(temp.getId()).getKnowledge_range_id();
            QueryWrapper<KnowledgeRangeInfo> knowledgeRangeInfoQueryWrapper =new QueryWrapper<>();
            knowledgeRangeInfoQueryWrapper.eq("knowledge_range_id",knowledge_range_id);
            List<KnowledgeRangeInfo> knowledgeRangeInfoList = knowledgeRangeInfoMapper.selectList(knowledgeRangeInfoQueryWrapper);
            //?????????????????????????????????
            for (KnowledgeRangeInfo temp1 :knowledgeRangeInfoList ){
                //?????????????????????
                int count =0 ;
                if (temp1.getRange_type().equals(KnowledgeRangeEnums.KNOWLEDGE_RANGE_1.getType())){
                    for (int i=0 ;i<sysUserRoleList.size();i++){
                        if (temp1.getFk_id().equals(sysUserRoleList.get(i).getRole_id()) ){
                            count++;
                            result.add(temp);
                            break;
                        }
                    }
                //?????????????????????
                }else if (temp1.getRange_type().equals(KnowledgeRangeEnums.KNOWLEDGE_RANGE_2.getType())){
                    if (temp1.getFk_id() == departmentId){
                        count++;
                        result.add(temp);
                    }
                 //?????????????????????????????????
                }else {
                    for (int i=0 ;i<eventBillingDesignateList.size();i++){
                        if (temp1.getFk_id().equals(eventBillingDesignateList.get(i).getService_management_id())){
                            count++;
                            result.add(temp);
                            break;
                        }
                    }
                }
                //??????count>0 ?????????????????????????????????????????? ??????knowledgeRangeInfoList?????? ??????????????????????????????
                if (count>0){
                    break;
                }
            }
        }
        return result;
    }


    /**
     * description:??????????????????excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporInsideKnowledgeExcel(HttpServletResponse response,KnowledgeInfo knowledgeInfo) {
        int userId =-1 ;
        List<KnowledgePageInfoVo> list = getKnowledgePageInfoVos(knowledgeInfo, userId);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("departmentName", "????????????");
        writer.addHeaderAlias("categoryName", "??????????????????");
        writer.addHeaderAlias("createUserName", "???????????????");
        writer.addHeaderAlias("approver_name", "???????????????");
        writer.addHeaderAlias("score", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("status", "??????");
        writer.addHeaderAlias("is_full_search", "??????????????????");
        writer.addHeaderAlias("knowledge_status", "????????????");
        writer.addHeaderAlias("sort_num", "?????????");
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
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }


    /**
     * ????????? ??????????????????
     * ????????? xuaolong
     * @param knowledgeScoring
     * @return
     */
    public ResultInfo getPoint(KnowledgeScoring knowledgeScoring){
        //??????????????????Id???????????????????????????
        int knowledge_info_id = knowledgeScoring.getKnowledge_info_id();
        QueryWrapper<KnowledgeScoring> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("knowledge_info_id",knowledge_info_id);
        Integer userId = null ;
        if (null == knowledgeScoring.getUserId()){
            userId= sysUserService.getUser().getId();
        }else {
            userId = knowledgeScoring.getUserId();
        }

        KnowledgeScoring k = knowledgeScoringMapper.selectOne(queryWrapper);
        //?????????????????? ???????????????
        if (k ==null){
            //???????????????
            knowledgeScoring.setRater(userId);
            //??????????????????
            knowledgeScoring.setScoring_time(new Date());
            knowledgeScoring.setCreate_time(new Date());
            knowledgeScoring.setCreate_user(userId);
            int result =knowledgeScoringMapper.insert(knowledgeScoring);
            if (result != 1){
                ResultInfo.error("?????????????????????");
            }
            return  ResultInfo.success();
            //??????????????? ???????????????
        }else{
            //???????????????
            k.setRater(userId);
            //??????????????????
            k.setScoring_time(new Date());
            k.setUpdate_time(new Date());
            k.setUpdate_user(userId);
            k.setKnowledge_info_id(knowledge_info_id);
            k.setScore(knowledgeScoring.getScore());
            k.setComment(knowledgeScoring.getComment());
            int result =knowledgeScoringMapper.updateById(k);
            if (result != 1){
                ResultInfo.error("?????????????????????");
            }
            return  ResultInfo.success();
        }
    }

    /**
     * ????????? ????????????????????????
     * ????????? xuaolong
     * @return ResultInfo
     */
    public  ResultInfo deleteKnowledge(int id){
        UpdateWrapper<KnowledgeInfo> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("is_del","1");
        knowledgeInfoMapper.update(null,updateWrapper);

        KnowledgeInfo knowledgeInfo = knowledgeInfoMapper.selectById(id);
        if ("1".equals(knowledgeInfo.getIs_full_search())){
            knowLedgeEsService.deleteDoc(id);
        }

        //??????????????? ?????????????????????????????????
        UpdateWrapper<KnowledgeScoring> knowledgeScoringUpdateWrapper =new UpdateWrapper<>();
        knowledgeScoringUpdateWrapper.eq("knowledge_info_id",id);
        knowledgeScoringUpdateWrapper.set("is_del","1");
        knowledgeScoringMapper.update(null,knowledgeScoringUpdateWrapper);
        return ResultInfo.success();
    }

    /**
     * ????????? ????????????????????????
     * ????????? xuaolong
     * @param id
     * @return
     */
    public ResultInfo useKnowledge(int id,Integer userId){
        KnowledgeInfo knowledgeInfo = knowledgeInfoMapper.selectById(id);
        if(KnowledgeEnums.KNOWLEDGE_STATUS_0.getType().equals( knowledgeInfo.getKnowledge_status())){
            knowledgeInfo.setKnowledge_status(KnowledgeEnums.KNOWLEDGE_STATUS_1.getType());
            knowLedgeEsService.addOrUpdateDoc(knowledgeInfo);
        }
        knowledgeInfo.setKnowledge_status(KnowledgeEnums.KNOWLEDGE_STATUS_1.getType());
        knowledgeInfo.setUpdate_time(new Date());
        knowledgeInfo.setUpdate_user(null == userId ? sysUserService.getUser().getId(): userId);
        knowledgeInfoMapper.updateById(knowledgeInfo);
        return ResultInfo.success();
    }

    /**
     * ????????? ????????????????????????
     * ????????? xuaolong
     * @param id
     * @return
     */
    public ResultInfo unUseKnowledge(Integer id,Integer userId){
        KnowledgeInfo knowledgeInfo =knowledgeInfoMapper.selectById(id);
        if(KnowledgeEnums.KNOWLEDGE_STATUS_1.getType().equals( knowledgeInfo.getKnowledge_status())){
            knowLedgeEsService.deleteDoc(knowledgeInfo.getId());
        }
        knowledgeInfo.setKnowledge_status(KnowledgeEnums.KNOWLEDGE_STATUS_0.getType());
        knowledgeInfo.setUpdate_time(new Date());
        knowledgeInfo.setUpdate_user(null == userId ? sysUserService.getUser().getId(): userId);
        knowledgeInfoMapper.updateById(knowledgeInfo);
        return ResultInfo.success();
    }

    /**
     * ????????? ????????????????????????
     * ????????? xuaolong
     * @param knowledgeInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultInfo manageKnowledge(KnowledgeInfo knowledgeInfo,int pageIndex, int pageSize){
        if (knowledgeInfo.getStatus() == null ||knowledgeInfo.getStatus().equals("") ){
            knowledgeInfo.setStatus("-1");
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<KnowledgePageInfoVo> list =knowledgeInfoMapper.getStatusKnowledgeByPage(knowledgeInfo);
        PageInfo<KnowledgePageInfoVo> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(pageInfo);
    }

    /**
     * ????????? ??????????????????
     * ????????? xuaolong
     */
    public ResultInfo  exporManageKnowledgeExcel(HttpServletResponse response,KnowledgeInfo knowledgeInfo) {
        if (StringUtils.isEmpty(knowledgeInfo.getStatus())){
            knowledgeInfo.setStatus("-1");
        }
//        List<KnowledgePageInfoVo> list =getKnowledgePageInfoVos(knowledgeInfo);
        List<KnowledgePageInfoVo> list =knowledgeInfoMapper.getStatusKnowledgeByPage(knowledgeInfo);
        if(!CollectionUtils.isEmpty(list)){
            for(KnowledgePageInfoVo k:list){
                //?????? 1???????????? 2?????????3???????????????
                if("1".equals(k.getStatus())){
                    k.setStatus("?????????");
                }else if("2".equals(k.getStatus())){
                    k.setStatus("??????");
                }else {
                    k.setStatus("????????????");
                }


                if(k.getIs_full_search() ==1){
                    k.setIs_full_search_name("???");
                }else {
                    k.setIs_full_search_name("???");
                }


                if(k.getKnowledge_status() == 1){
                    k.setKnowledge_status_name("??????");
                }else {
                    k.setKnowledge_status_name("??????");
                }

            }


        }
        ExcelWriter writer = ExcelUtil.getWriter();
//        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("departmentName", "????????????");
        writer.addHeaderAlias("categoryName", "????????????");
        writer.addHeaderAlias("createUserName", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("status", "????????????");
        writer.addHeaderAlias("is_full_search_name", "??????????????????");
        writer.addHeaderAlias("knowledge_status_name", "????????????");
        writer.addHeaderAlias("score", "?????????");
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
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }


    @Transactional
    public ResultInfo importKnowledgeList(MultipartFile file){
        try {
            //fileName ?????????
            String fileName = file.getOriginalFilename();
            boolean xlsx = fileName.endsWith(".xlsx");
            if (!xlsx) {
                return ResultInfo.error("????????????.xlsx???????????????");
            }
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ExcelReader reader = ExcelUtil.getReader(inputStream,0);
            List<List<Object>> data = reader.read();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int size = data.size();
            if(size >1){
                for(int i = 1; i < size; i++){
                    KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
                    List<Object> line = data.get(i);
                    if(StringUtils.isEmpty(line.get(0).toString()) || StringUtils.isEmpty(line.get(1).toString()) || StringUtils.isEmpty(line.get(2).toString())){
                        return ResultInfo.error("????????????????????????");
                    }else {
                        //??????????????????
                        String search =line.get(0).toString();
                        String recommend =line.get(1).toString();

                        if (search.equals("???")){
                            knowledgeInfo.setIs_full_search("1");
                        }else {
                            knowledgeInfo.setIs_full_search("2");
                        }

                        //????????????
                        if (recommend.equals("?????????")){
                            knowledgeInfo.setIs_recommend("0");
                        }else {
                            knowledgeInfo.setIs_full_search("1");
                        }


                        //??????
                        knowledgeInfo.setTitle(line.get(2).toString());



                        knowledgeInfo.setCreate_time(new Date());
                        knowledgeInfo.setCreate_user(sysUserService.getUser().getId());
                        knowledgeInfo.setIs_del("0");


                        knowledgeInfoMapper.insert(knowledgeInfo);
                    }
                }
                return ResultInfo.success("???????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("???????????????(????????????????????????????????????????????????)");

    }

    /**
     * ????????????????????????
     */
    public Map<Integer, SysRole> getRoleMap() {
        List<SysRole> users = sysRoleMapper.selectList(null);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysRole::getId, Function.identity(), (key1, key2) -> key2));
    }
    /**
     * ???????????????key,value
     */
    private Map<Integer, SysDepartment> getDeptMap(){
        List<SysDepartment> departmentList = sysDepartmentMapper.selectList(null);
        if(CollectionUtils.isEmpty(departmentList)){
            return Collections.emptyMap();
        }
        return departmentList.stream().collect(Collectors.toMap(SysDepartment::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ?????????????????????key,value
     * @return
     */
    private Map<Integer, EventServiceManage> getServiceMap(){
        List<EventServiceManage> list = eventServiceManageMapper.selectList(null);
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(EventServiceManage::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * ????????????????????????Map<id,SysUser>
     */
    public Map<Integer, SysUser> getUserNameMap() {
        List<SysUser> users = sysUserService.selectList(null);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity(), (key1, key2) -> key2));
    }


    /**
     * ??????????????????
     */
    public List<KnowledgeInfo> getSearchFourKnowledge(KnowledgeInfo knowledgeInfo){
        QueryWrapper<KnowledgeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("is_recommend","1");
        queryWrapper.eq("knowledge_status",1);
        queryWrapper.eq("status","2");
        queryWrapper.like("title",knowledgeInfo.getKeyword());
        List<KnowledgeInfo> list = knowledgeInfoMapper.selectList(queryWrapper);
        List<KnowledgeInfo> resultList = new ArrayList<>();
        if (list.size() > 4){
           for (int i = 0 ; i < list.size() ; i++ ){
               if (i<4){
                   resultList.add(list.get(i));
               }
            }
        }else {
            for (int i = 0 ; i < list.size() ; i++ ){
                resultList.add(list.get(i));

            }
        }
        return resultList;

    }





    /**
    *??????????????????
    * Author wzn
    * Date 2022/11/2 9:20
    */
    public ResultInfo exportKnowledgeTemplate(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();



        List<SysUser> sysUsers = sysUserService.getUsers() ;
        row.put("??????????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);


        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();

        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();
        //?????????????????? ??????????????????
        int firstRow = 1;
        //??????
        String[] isSearch = {"???", "???"};
        String[] recommend = {"?????????", "??????"};

        writer.addValidationData(setSelectCol(styleSet, sheet, isSearch, firstRow, 0));
        writer.addValidationData(setSelectCol(styleSet, sheet, recommend, firstRow, 1));


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


    public List<KnowledgeVo> contributionScoreStatistics() {
        return knowledgeInfoMapper.contributionScoreStatistics() ;
    }


    public List<KnowledgeVo> departmentOfKnowledge() {
        return knowledgeInfoMapper.departmentOfKnowledge() ;
    }


}
