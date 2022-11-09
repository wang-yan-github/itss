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
     * description: 树状图展示知识分类
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
     * 描述： 分页查询我的知识
     * 作者： xuaolong
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
     * 描述： 添加我的知识
     * 作者： xuaolong
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
            return ResultInfo.success("添加成功");
        }
        return ResultInfo.error("新增失败！");
    }


    /**
     * 描述： 修改我的知识
     * 作者： xuaolong
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
        //附件上传 保存
        //属性信息 是否存在文件删除
        String uploadedFiles = knowledgeInfo.getUploadedFiles();
        // 得到文件信息 附件
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
        return ResultInfo.error("修改失败！");
    }


    public List<KnowledgeRange> rangeList(){

        QueryWrapper<KnowledgeRange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        List<KnowledgeRange> list1 =knowledgeRangeMapper.selectList(queryWrapper);
        for (int i = 0 ; i < list1.size() ; i++){
            // 得到用户信息
            Map<Integer, SysUser> userMap = getUserNameMap();
            // 得到角色键值对
            Map<Integer, SysRole> roleMap = getRoleMap();
            // 得到部门
            Map<Integer, SysDepartment> deptMap = getDeptMap();
            // 得到服务群组
            Map<Integer, EventServiceManage> serviceMap = getServiceMap();

            List<KnowledgeRangeInfo> list = knowledgeRangeInfoMapper.selectList(Wrappers.<KnowledgeRangeInfo>lambdaQuery()
                    .eq(KnowledgeRangeInfo::getKnowledge_range_id, list1.get(i).getId()));
            String role = "【角色】";
            String depart ="【部门】";
            String group = "【服务群组】";
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
     * description: 根据主键查询信息
     * @param id
     * @return
     */
    public ResultInfo getKnowledgeInfoById(Integer id){
        KnowledgeInfoVo knowledgeInfo = knowledgeInfoMapper.getKnowledgeById(id);
        if (null == knowledgeInfo){
            return ResultInfo.error("查询的用户不存在");
        }
        // 得到用户信息
        Map<Integer, SysUser> userMap = getUserNameMap();
        // 得到角色键值对
        Map<Integer, SysRole> roleMap = getRoleMap();
        // 得到部门
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        // 得到服务群组
        Map<Integer, EventServiceManage> serviceMap = getServiceMap();
        List<KnowledgeRangeInfo> list = knowledgeRangeInfoMapper.selectList(Wrappers.<KnowledgeRangeInfo>lambdaQuery()
                .eq(KnowledgeRangeInfo::getKnowledge_range_id, knowledgeInfo.getKnowledge_range_id()));
        String role = "【角色】";
        String depart ="【部门】";
        String group = "【服务群组】";
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
        if(role.equals("【角色】")){
            role = "【角色】暂无角色";
        }
        if (depart.equals("【部门】")){
            depart = "【部门】暂无部门";
        }
        if(group.equals("【服务群组】")){
            group = "【服务群组】暂无群组";
        }

        knowledgeInfo.setKnowledge_range("工程师可见（" + role + depart + group + "）");

        // 判断用户表是否存在数据
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

        // 得到文件信息 附件
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
     * 描述： 知识审核（通过)
     * 作者： xuaolong
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
        //附件上传 保存
        //属性信息 是否存在文件删除
        String uploadedFiles = knowledgeInfo.getUploadedFiles();
        // 得到文件信息 附件
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
        return ResultInfo.error("审核失败！");
    }

    /**
     * 描述： 知识审核（拒绝)
     * 作者： xuaolong
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo toRefuseExamineKnowledge(KnowledgeInfo knowledgeInfo, List<MultipartFile> multipartFiles){
        //添加审核人信息 并更改状态
        knowledgeInfo.setStatus(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_3.getType());
        knowledgeInfo.setApprover_id(null == knowledgeInfo.getUserId() ? sysUserService.getUser().getId() :  knowledgeInfo.getUserId());
        knowledgeInfo.setApproval_time(new Date());
        int result =  knowledgeInfoMapper.updateById(knowledgeInfo);

        //附件上传 保存
        //属性信息 是否存在文件删除
        String uploadedFiles = knowledgeInfo.getUploadedFiles();
        // 得到文件信息 附件
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
        return ResultInfo.error("拒绝失败！");
    }

    /**
     * 描述： 分页查询待审核知识
     * 作者： xuaolong
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
     * 私有方法
     * @param knowledgeInfo
     * @return
     */
    private List<KnowledgePageInfoVo> getKnowledgePageInfoVos(KnowledgeInfo knowledgeInfo) {
        //获取用户ID
        Integer userId = null;
        if (null == knowledgeInfo.getUserId()){
            userId = sysUserService.getUser().getId();
        }else {
            userId = knowledgeInfo.getUserId();
        }


        //如果状态未选中 默认选中待审核
//        if (StringUtils.isEmpty(knowledgeInfo.getStatus())){
//            knowledgeInfo.setStatus(KnowledgeExamineEnums.KNOWLEDGE_EXAMINE_1.getType());
//        }
        //查询所有知识
        List<KnowledgePageInfoVo> list = knowledgeInfoMapper.getStatusKnowledgeByPage(knowledgeInfo);
        List<KnowledgePageInfoVo> resultList = new ArrayList<>();
        //判断知识的知识类别的审核人是否包含当前用户
        for (KnowledgePageInfoVo temp: list){
            if(null == temp.getKnowledge_category_id()){
                continue;
            }
            int knowledge_category_id = temp.getKnowledge_category_id();
            QueryWrapper<KnowledgeApprover> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("knowledge_category_id",knowledge_category_id);
            List<KnowledgeApprover> knowledgeApproverList = knowledgeApproverMapper.selectList(queryWrapper);
            for (KnowledgeApprover temp1:knowledgeApproverList){
                //如果包含就把此结果保存到返回的集合中
                if (temp1.getApprover_id() == userId){
                    resultList.add(temp);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * pagehelper手动分页
     * @param currentPage 当前页
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
     * 描述： 内部知识分页查询
     * 作者： xuaolong
     * @param knowledgeInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultInfo getInsideKnowledge(KnowledgeInfo knowledgeInfo,int pageIndex, int pageSize){
        int  userId  = -1;
        //分页
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
     * 私有方法 查询内部知识集合
     * @param knowledgeInfo
     * @param userId
     * @return
     */
    private List<KnowledgePageInfoVo> getKnowledgePageInfoVos(KnowledgeInfo knowledgeInfo, int userId) {
        //查询所有知识
        List<KnowledgePageInfoVo> list =knowledgeInfoMapper.selectKnowledgeByPage(knowledgeInfo,userId);
        //返回数据
        List<KnowledgePageInfoVo> result = new ArrayList<>();

        //根据用户ID判断是否是内部知识
        if (null != knowledgeInfo.getUserId()){
            userId= knowledgeInfo.getUserId();
        }else {
            userId = sysUserService.getUser().getId();
        }

        //根据用户ID获得角色ID集合
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper =new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",userId);
        List <SysUserRole> sysUserRoleList =sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);

        //根据登陆用户获取部门ID
        int departmentId = sysUserMapper.selectById(userId).getDepartment_id();

        //根据登陆用户可查看服务群组ID
        QueryWrapper<EventBillingDesignate> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("engineer_userid",userId);
        List<EventBillingDesignate> eventBillingDesignateList =eventBillingDesignateMapper.selectList(queryWrapper);


        //遍历所有知识数据集合 获得匹配角色ID 部门ID 服务群组ID的集合
        for (KnowledgePageInfoVo temp :list ){
            //获取当前知识的范围ID
            if(null == knowledgeInfoMapper.selectById(temp.getId()) || null == knowledgeInfoMapper.selectById(temp.getId()).getKnowledge_range_id()){
                continue;
            }
            int knowledge_range_id =knowledgeInfoMapper.selectById(temp.getId()).getKnowledge_range_id();
            QueryWrapper<KnowledgeRangeInfo> knowledgeRangeInfoQueryWrapper =new QueryWrapper<>();
            knowledgeRangeInfoQueryWrapper.eq("knowledge_range_id",knowledge_range_id);
            List<KnowledgeRangeInfo> knowledgeRangeInfoList = knowledgeRangeInfoMapper.selectList(knowledgeRangeInfoQueryWrapper);
            //遍历当前知识对应的范围
            for (KnowledgeRangeInfo temp1 :knowledgeRangeInfoList ){
                //如果范围为角色
                int count =0 ;
                if (temp1.getRange_type().equals(KnowledgeRangeEnums.KNOWLEDGE_RANGE_1.getType())){
                    for (int i=0 ;i<sysUserRoleList.size();i++){
                        if (temp1.getFk_id().equals(sysUserRoleList.get(i).getRole_id()) ){
                            count++;
                            result.add(temp);
                            break;
                        }
                    }
                //如果范围为部门
                }else if (temp1.getRange_type().equals(KnowledgeRangeEnums.KNOWLEDGE_RANGE_2.getType())){
                    if (temp1.getFk_id() == departmentId){
                        count++;
                        result.add(temp);
                    }
                 //如果范围为部门服务群组
                }else {
                    for (int i=0 ;i<eventBillingDesignateList.size();i++){
                        if (temp1.getFk_id().equals(eventBillingDesignateList.get(i).getService_management_id())){
                            count++;
                            result.add(temp);
                            break;
                        }
                    }
                }
                //如果count>0 证明添加当前记录符合返回条件 跳出knowledgeRangeInfoList循环 进入知识数据集合循环
                if (count>0){
                    break;
                }
            }
        }
        return result;
    }


    /**
     * description:内部知识导出excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporInsideKnowledgeExcel(HttpServletResponse response,KnowledgeInfo knowledgeInfo) {
        int userId =-1 ;
        List<KnowledgePageInfoVo> list = getKnowledgePageInfoVos(knowledgeInfo, userId);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("departmentName", "部门名称");
        writer.addHeaderAlias("categoryName", "知识分类名称");
        writer.addHeaderAlias("createUserName", "创建人名字");
        writer.addHeaderAlias("approver_name", "审核人名字");
        writer.addHeaderAlias("score", "贡献分");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("is_full_search", "可被全员搜索");
        writer.addHeaderAlias("knowledge_status", "知识状态");
        writer.addHeaderAlias("sort_num", "排序号");
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
     * 描述： 知识管理打分
     * 作者： xuaolong
     * @param knowledgeScoring
     * @return
     */
    public ResultInfo getPoint(KnowledgeScoring knowledgeScoring){
        //首先根据知识Id判断以前是否打过分
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
        //如果没有记录 就插入数据
        if (k ==null){
            //设置评分人
            knowledgeScoring.setRater(userId);
            //设置评分时间
            knowledgeScoring.setScoring_time(new Date());
            knowledgeScoring.setCreate_time(new Date());
            knowledgeScoring.setCreate_user(userId);
            int result =knowledgeScoringMapper.insert(knowledgeScoring);
            if (result != 1){
                ResultInfo.error("评分失败！！！");
            }
            return  ResultInfo.success();
            //如果有记录 就修改数据
        }else{
            //设置评分人
            k.setRater(userId);
            //设置评分时间
            k.setScoring_time(new Date());
            k.setUpdate_time(new Date());
            k.setUpdate_user(userId);
            k.setKnowledge_info_id(knowledge_info_id);
            k.setScore(knowledgeScoring.getScore());
            k.setComment(knowledgeScoring.getComment());
            int result =knowledgeScoringMapper.updateById(k);
            if (result != 1){
                ResultInfo.error("评分失败！！！");
            }
            return  ResultInfo.success();
        }
    }

    /**
     * 描述： 知识管理删除知识
     * 作者： xuaolong
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

        //删除知识后 还需要删除评分表的数据
        UpdateWrapper<KnowledgeScoring> knowledgeScoringUpdateWrapper =new UpdateWrapper<>();
        knowledgeScoringUpdateWrapper.eq("knowledge_info_id",id);
        knowledgeScoringUpdateWrapper.set("is_del","1");
        knowledgeScoringMapper.update(null,knowledgeScoringUpdateWrapper);
        return ResultInfo.success();
    }

    /**
     * 描述： 知识管理使用知识
     * 作者： xuaolong
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
     * 描述： 知识管理禁用知识
     * 作者： xuaolong
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
     * 描述： 知识管理分页查询
     * 作者： xuaolong
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
     * 描述： 知识管理导出
     * 作者： xuaolong
     */
    public ResultInfo  exporManageKnowledgeExcel(HttpServletResponse response,KnowledgeInfo knowledgeInfo) {
        if (StringUtils.isEmpty(knowledgeInfo.getStatus())){
            knowledgeInfo.setStatus("-1");
        }
//        List<KnowledgePageInfoVo> list =getKnowledgePageInfoVos(knowledgeInfo);
        List<KnowledgePageInfoVo> list =knowledgeInfoMapper.getStatusKnowledgeByPage(knowledgeInfo);
        if(!CollectionUtils.isEmpty(list)){
            for(KnowledgePageInfoVo k:list){
                //状态 1：待审核 2：发布3：审核拒绝
                if("1".equals(k.getStatus())){
                    k.setStatus("待审核");
                }else if("2".equals(k.getStatus())){
                    k.setStatus("发布");
                }else {
                    k.setStatus("审核拒绝");
                }


                if(k.getIs_full_search() ==1){
                    k.setIs_full_search_name("是");
                }else {
                    k.setIs_full_search_name("否");
                }


                if(k.getKnowledge_status() == 1){
                    k.setKnowledge_status_name("启用");
                }else {
                    k.setKnowledge_status_name("禁用");
                }

            }


        }
        ExcelWriter writer = ExcelUtil.getWriter();
//        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("title", "标题");
        writer.addHeaderAlias("departmentName", "部门名称");
        writer.addHeaderAlias("categoryName", "知识类别");
        writer.addHeaderAlias("createUserName", "添加人");
        writer.addHeaderAlias("create_time", "添加时间");
        writer.addHeaderAlias("status", "审核状态");
        writer.addHeaderAlias("is_full_search_name", "可被全员搜索");
        writer.addHeaderAlias("knowledge_status_name", "知识状态");
        writer.addHeaderAlias("score", "贡献分");
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
            //fileName 文件名
            String fileName = file.getOriginalFilename();
            boolean xlsx = fileName.endsWith(".xlsx");
            if (!xlsx) {
                return ResultInfo.error("请上传以.xlsx结尾的文件");
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
                        return ResultInfo.error("请填写必填字段！");
                    }else {
                        //可被全员搜索
                        String search =line.get(0).toString();
                        String recommend =line.get(1).toString();

                        if (search.equals("是")){
                            knowledgeInfo.setIs_full_search("1");
                        }else {
                            knowledgeInfo.setIs_full_search("2");
                        }

                        //推荐知识
                        if (recommend.equals("不推荐")){
                            knowledgeInfo.setIs_recommend("0");
                        }else {
                            knowledgeInfo.setIs_full_search("1");
                        }


                        //标题
                        knowledgeInfo.setTitle(line.get(2).toString());



                        knowledgeInfo.setCreate_time(new Date());
                        knowledgeInfo.setCreate_user(sysUserService.getUser().getId());
                        knowledgeInfo.setIs_del("0");


                        knowledgeInfoMapper.insert(knowledgeInfo);
                    }
                }
                return ResultInfo.success("操作成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("操作失败！(填写的内容除理由列，其他全部必填)");

    }

    /**
     * 得到角色的键值对
     */
    public Map<Integer, SysRole> getRoleMap() {
        List<SysRole> users = sysRoleMapper.selectList(null);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysRole::getId, Function.identity(), (key1, key2) -> key2));
    }
    /**
     * 得到部门的key,value
     */
    private Map<Integer, SysDepartment> getDeptMap(){
        List<SysDepartment> departmentList = sysDepartmentMapper.selectList(null);
        if(CollectionUtils.isEmpty(departmentList)){
            return Collections.emptyMap();
        }
        return departmentList.stream().collect(Collectors.toMap(SysDepartment::getId, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 得到服务群组的key,value
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
     * 得到用户的键值对Map<id,SysUser>
     */
    public Map<Integer, SysUser> getUserNameMap() {
        List<SysUser> users = sysUserService.selectList(null);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity(), (key1, key2) -> key2));
    }


    /**
     * 主页搜索知识
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
    *知识导入模板
    * Author wzn
    * Date 2022/11/2 9:20
    */
    public ResultInfo exportKnowledgeTemplate(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();



        List<SysUser> sysUsers = sysUserService.getUsers() ;
        row.put("可被全员搜索", StringUtils.EMPTY);
        row.put("推荐知识", StringUtils.EMPTY);
        row.put("标题", StringUtils.EMPTY);


        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();

        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();
        //设置下拉数据 从第几行开始
        int firstRow = 1;
        //保密
        String[] isSearch = {"是", "否"};
        String[] recommend = {"不推荐", "推荐"};

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
     * 设置下拉选项
     */
    private DataValidation setSelectCol(StyleSet styleSet, Sheet sheet, String[] capacityAvi, int firstRow, int firstCol) {

        CellStyle cellStyle = styleSet.getCellStyle();
        //规定格式
        cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));

        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 设置辅修下拉框数据
//        String[] capacityAvi = {"是", "否"};
        DataValidationConstraint capacityConstraint = helper.createExplicitListConstraint(capacityAvi);
        //需要被设置为下拉数据的单元格范围
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
