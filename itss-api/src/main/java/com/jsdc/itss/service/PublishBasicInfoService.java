package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
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
import com.jsdc.itss.dao.PublishBasicInfoDao;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.mapper.ChangeInfoMapper;
import com.jsdc.itss.mapper.PublishApprovalInfoMapper;
import com.jsdc.itss.mapper.PublishBasicInfoMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.ChangesOf;
import com.jsdc.itss.model.vo.PublishApproveInfo;
import com.jsdc.itss.utils.DataUtils;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.LogVo;
import com.jsdc.itss.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

/**
 * @Author wh
 * @create 2022-03-14 14:58:22
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor =@_(@Autowired))
public class PublishBasicInfoService extends BaseService<PublishBasicInfoDao, PublishBasicInfo> {

    private final PublishBasicInfoMapper publishBasicInfoMapper;

    private final SysUserService sysUserService;

    private final PublishChangesService publishChangesService;

    private final PublishNodesService publishNodesService;

    private final PublishCategoryInfoService publishCategoryInfoService;

    private final PublishApprovalInfoService publishApprovalInfoService;

    private final PublishCategoryApproveService publishCategoryApproveService;

    private final ChangeInfoMapper changeInfoMapper;

    private final SysDictService sysDictService;

    private final FileManageService fileManageService;

    private final PublishFileService publishFileService;

    private final SysLogService sysLogService;



    @Value("${file.upload-path}")
    private String uploadPath;


    public PageInfo<PublishBasicInfo> toList(Integer pageIndex, Integer pageSize, PublishBasicInfo beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        beanParam.setCreate_user(beanParam.getUserId() == null ? sysUserService.getUser().getId() : beanParam.getUserId() );
        List<PublishBasicInfo> publishBasicInfoVos = publishBasicInfoMapper.toList(beanParam);
        // ?????????????????????
        Map<String, SysUser> userMap = getUserMap();
        for (PublishBasicInfo publishBasicInfo : publishBasicInfoVos) {
            if(StringUtils.isNotBlank(publishBasicInfo.getPublisher())){
                SysUser sysUser = userMap.get(publishBasicInfo.getPublisher());
                publishBasicInfo.setPublisher_name(null == sysUser?"":sysUser.getName());
            }
            if(StringUtils.isNotBlank(publishBasicInfo.getTester_id())){
                SysUser sysUser = userMap.get(publishBasicInfo.getTester_id());
                publishBasicInfo.setTester_name(null == sysUser?"":sysUser.getName());
            }
            // ????????????????????????
            List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(
                    Wrappers.<PublishCategoryApprove>lambdaQuery()
                            .eq(PublishCategoryApprove::getIs_del, GlobalData.ISDEL_NO)
                            .eq(PublishCategoryApprove::getPublish_category_id, publishBasicInfo.getCategory())
            );
            if(CollectionUtils.isEmpty(publishCategoryApproves)){
                continue;
            }
            publishCategoryApproves.forEach(x -> x.setApprove_user_name(null == userMap.get(x.getApprove_user() + "")?"":userMap.get(x.getApprove_user()+ "").getName()));
            publishBasicInfo.setApprover(publishCategoryApproves.stream().map(x->x.getApprove_user_name()).collect(Collectors.joining(",")));

        }
        PageInfo<PublishBasicInfo> page = new PageInfo<>(publishBasicInfoVos);

        return page;
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



    public PageInfo<PublishBasicInfo> toDbList(Integer pageIndex, Integer pageSize, PublishBasicInfo beanParam) {
        Integer userId = null;
        if (null == beanParam.getUserId()){
            userId = sysUserService.getUser().getId();
        }else {
            userId = beanParam.getUserId();
        }

        List<PublishBasicInfo>  result = getDbList(beanParam);
        List<PublishBasicInfo> resultList = new ArrayList<>();
        for (PublishBasicInfo temp : result){
            if (StringUtils.isNotEmpty(temp.getType())){
                if ("1".equals(temp.getType())){
                    temp.setType_name("????????????");
                }else {
                    temp.setType_name("????????????");
                }
            }
            //????????????????????????
            if("1".equals(temp.getStatus())){
               //???????????????????????????????????????
                QueryWrapper<PublishCategoryApprove> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("is_del","0").eq("publish_category_id",temp.getCategory()).orderByAsc("sort");
                List<PublishCategoryApprove> approveList = publishCategoryApproveService.selectList(queryWrapper1);
                //????????????????????????ID
                Integer pass_approve =  temp.getPass_approve();
                for (int i = 0 ; i<approveList.size() ; i++){
                    //????????????????????????null ??????????????????????????? ??????????????????????????????????????????????????????????????????????????????????????????
                    if (null == pass_approve){
                        if ( i==0 && approveList.get(i).getApprove_user().equals(userId) ){
                            resultList.add(temp);
                            break;
                        }
                    }else {
                        //?????????????????????????????? ???????????????????????????????????????????????????????????????????????????????????????????????????
                        //????????????????????????
                        if (approveList.get(i).getApprove_user().equals(pass_approve)){
                            //?????????????????????????????????????????????
                            if (approveList.get(i+1).getApprove_user().equals(userId)){
                                resultList.add(temp);
                                break;
                            }
                        }
                    }
                }
            }else {
                resultList.add(temp);
            }
        }

        return getPageInfo(pageIndex, pageSize, resultList);
    }

    /**
     * ??????????????????List
     */
    public List<PublishBasicInfo> getDbList(PublishBasicInfo beanParam){
        Integer id = null;
        if (null == beanParam.getUserId()){
            id = sysUserService.getUser().getId();
        }else {
            id = beanParam.getUserId();
        }
        // ?????????????????????
        Map<String, SysUser> userMap = getUserMap();
        List<PublishBasicInfo> publishBasicInfoVos = publishBasicInfoMapper.toList(beanParam);

        List<PublishBasicInfo> result = new ArrayList<>();
        for (PublishBasicInfo publishBasicInfo : publishBasicInfoVos) {
            // ??????????????????????????????????????????
            if(checkApproveNode(publishBasicInfo,id)) {
                if (CollectionUtils.isEmpty(userMap)){
                    continue;
                }
                if(StringUtils.isNotBlank(publishBasicInfo.getPublisher())){
                    SysUser sysUser = userMap.get(publishBasicInfo.getPublisher());
                    publishBasicInfo.setPublisher_name(null == sysUser?"":sysUser.getName());
                }
                if(StringUtils.isNotBlank(publishBasicInfo.getTester_id())){
                    SysUser sysUser = userMap.get(publishBasicInfo.getTester_id());
                    publishBasicInfo.setTester_name(null == sysUser?"":sysUser.getName());
                }
                // ????????????????????????
                List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(
                        Wrappers.<PublishCategoryApprove>lambdaQuery()
                                .eq(PublishCategoryApprove::getIs_del, GlobalData.ISDEL_NO)
                                .eq(PublishCategoryApprove::getPublish_category_id, publishBasicInfo.getCategory())
                );
                if(CollectionUtils.isEmpty(publishCategoryApproves)){
                    continue;
                }
                publishCategoryApproves.forEach(x -> x.setApprove_user_name(null == userMap.get(x.getApprove_user() + "")?"":userMap.get(x.getApprove_user()+ "").getName()));
                publishBasicInfo.setApprover(publishCategoryApproves.stream().map(x->x.getApprove_user_name()).collect(Collectors.joining(",")));
                result.add(publishBasicInfo);
            }
        }
        return result;
    }


    //???????????????????????????????????????????????????????????????????????????
    public  PageInfo<PublishBasicInfo> toManageList(Integer pageIndex, Integer pageSize, PublishBasicInfo beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<PublishBasicInfo> publishBasicInfoVos = publishBasicInfoMapper.toList(beanParam);
        // ?????????????????????
        Map<String, SysUser> userMap = getUserMap();
        for (PublishBasicInfo publishBasicInfo : publishBasicInfoVos) {
            if(StringUtils.isNotBlank(publishBasicInfo.getPublisher())){
                SysUser sysUser = userMap.get(publishBasicInfo.getPublisher());
                publishBasicInfo.setPublisher_name(null == sysUser?"":sysUser.getName());
            }
            if(StringUtils.isNotBlank(publishBasicInfo.getTester_id())){
                SysUser sysUser = userMap.get(publishBasicInfo.getTester_id());
                publishBasicInfo.setTester_name(null == sysUser?"":sysUser.getName());
            }
            // ????????????????????????
            List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(
                    Wrappers.<PublishCategoryApprove>lambdaQuery()
                            .eq(PublishCategoryApprove::getIs_del, GlobalData.ISDEL_NO)
                            .eq(PublishCategoryApprove::getPublish_category_id, publishBasicInfo.getCategory())
            );
            if(CollectionUtils.isEmpty(publishCategoryApproves)){
                continue;
            }
            publishCategoryApproves.forEach(x -> x.setApprove_user_name(null == userMap.get(x.getApprove_user() + "")?"":userMap.get(x.getApprove_user()+ "").getName()));
            publishBasicInfo.setApprover(publishCategoryApproves.stream().map(x->x.getApprove_user_name()).collect(Collectors.joining(",")));

        }
        PageInfo<PublishBasicInfo> page = new PageInfo<>(publishBasicInfoVos);

        return page;

    }


    /**
     * ?????????????????????????????????????????????????????????
     * @return
     */
    public boolean checkApproveNode(PublishBasicInfo publishBasicInfo,Integer userId) {
        String user = userId.toString();
        switch (publishBasicInfo.getStatus()) {
            case "1":
                // ?????????, ????????????????????????????????? GlobalData.PUBLISH_STATUS_APPROVE
                List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(
                        Wrappers.<PublishCategoryApprove>lambdaQuery()
                                .eq(PublishCategoryApprove::getIs_del, GlobalData.ISDEL_NO)
                                .eq(PublishCategoryApprove::getPublish_category_id, publishBasicInfo.getCategory())
                                .eq(PublishCategoryApprove::getApprove_user, userId)
                );
                return CollectionUtils.isEmpty(publishCategoryApproves)?false:true;
            case "3":
                // ?????????, ????????????????????????????????? GlobalData.PUBLISH_STATUS_TEST
                return user.equals(publishBasicInfo.getTester_id());
            case "4":
                // ?????????, ????????????????????????????????? GlobalData.PUBLISH_STATUS_PUB
                return  user.equals(publishBasicInfo.getPublisher());
            default:
                break;
        }
        return false;
    }

    /**
     * ?????????????????????key-value
     * @return
     */
    public Map<String,SysUser> getUserMap() {
        List<SysUser> sysUsers = sysUserService.selectList(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getIs_del, GlobalData.ISDEL_NO)
        );
        Map<String,SysUser> map = new HashMap<>();
        for (SysUser sysUser : sysUsers) {
            map.put(sysUser.getId().toString(),sysUser);
        }
        return map;
    }

    /**
     * ?????????????????????key-value
     * @return
     */
    public Map<String,SysUser> getUserMap1() {
        List<SysUser> sysUsers = sysUserService.selectList(
                Wrappers.<SysUser>lambdaQuery()
        );
        Map<String,SysUser> map = new HashMap<>();
        for (SysUser sysUser : sysUsers) {
            map.put(sysUser.getId().toString(),sysUser);
        }
        return map;
    }


    public ResultInfo  revoke (Integer id ,Integer userId) {
        PublishBasicInfo publishBasicInfo = publishBasicInfoMapper.selectById(id);
        publishBasicInfo.setStatus("0");
        publishBasicInfoMapper.updateById(publishBasicInfo);
        return  ResultInfo.success("???????????????",new LogVo(userId, id, "??????", StringUtils.EMPTY));
    }




    public ResultInfo getPublishBasic(PublishBasicInfo param) {
        if(null == param.getId() || null == selectById(param.getId())){
            return ResultInfo.error("????????????");
        }
        String operator = null ;
        if (null != param.getUserId()){
             operator = sysUserService.selectById(param.getUserId()).getName();
        }
        PublishBasicInfo beanParam = selectById(param.getId());
        QueryWrapper<PublishBasicInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",beanParam.getId());
        PublishBasicInfo publishBasicInfo = selectOne(queryWrapper);
        PublishCategoryInfo publishCategoryInfo = publishCategoryInfoService.selectById(publishBasicInfo.getCategory());
        publishBasicInfo.setStatus(publishBasicInfo.getStatus());
        publishBasicInfo.setCategory_name(publishCategoryInfo.getName());
        publishBasicInfo.setOperator( operator == null ? sysUserService.getUser().getName() : operator);
        publishBasicInfo.setCreate_user_name(sysUserService.selectById(publishBasicInfo.getCreate_user()).getName());
        if (StringUtils.isNotEmpty(publishBasicInfo.getPublisher())){
            publishBasicInfo.setPublisher_name(sysUserService.selectById(Integer.parseInt(publishBasicInfo.getPublisher())).getName());
        }

        if(StringUtils.isNotEmpty( publishBasicInfo.getTester_id())){
            publishBasicInfo.setTester_name(sysUserService.selectById(Integer.parseInt(publishBasicInfo.getTester_id())).getName());
        }

        QueryWrapper<PublishApprovalInfo> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("basic_info_id",param.getId());
        queryWrapper1.eq("is_del","0");
        List<PublishApprovalInfo> publishApprovalInfo = publishApprovalInfoService.selectList(queryWrapper1);
        if (!CollectionUtils.isEmpty(publishApprovalInfo)){
            String tempString = publishApprovalInfo.get(0).getApproval_opinions();
            publishBasicInfo.setApproval_opinions(tempString);
        }

        //?????????????????????
        PublishNodes publishNodes = publishNodesService.getCurrentApproveNode(beanParam.getId());
        if(publishNodes != null) {
            QueryWrapper<PublishCategoryApprove> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("is_del", GlobalData.ISDEL_NO).eq("publish_category_id", beanParam.getCategory()).eq("sort", publishNodes.getSort());
            PublishCategoryApprove publishCategoryApprove = publishCategoryApproveService.selectOne(queryWrapper2);
            if(publishCategoryApprove != null) {
                publishBasicInfo.setCurrent_approver(sysUserService.selectById(publishCategoryApprove.getApprove_user()).getName());
            }
//            publishBasicInfo.setCurrent_approver(null == sysUserService.selectById(publishCategoryApprove.getApprove_user())?"":sysUserService.selectById(publishCategoryApprove.getApprove_user()).getName());
        }
        List<PublishChanges> publishChangesList = publishChangesService.selectList(new QueryWrapper<PublishChanges>().eq("publish_id", publishBasicInfo.getId()).eq("is_del",'0'));
        List<ChangeVo> list = new ArrayList<>();
        publishChangesList.forEach(x->{
            ChangeVo changeVo = changeInfoMapper.getById(x.getChange_id());
            changeVo.setStatus_name(sysDictService.getDict("change_status", changeVo.getStatus()).getLabel());
            changeVo.setType_name(sysDictService.getDict("change_type", changeVo.getChange_type()).getLabel());
            list.add(changeVo);
        });
        publishBasicInfo.setChangeVoList(list);
        //??????????????????
//        QueryWrapper<PublishApprovalInfo> queryWrapper1 = new QueryWrapper<>();
//        List<PublishApprovalInfo> byPublishId = publishApprovalInfoService.getByPublishId(publishBasicInfo.getId());
//        PublishApprovalInfo publishApprovalInfo = publishApprovalInfoService.selectOne(new QueryWrapper<PublishApprovalInfo>().eq("is_del", '0').eq("basic_info_id", publishBasicInfo.getId()));
//        publishBasicInfo.setPublishApprovalInfo(byPublishId);

        //???????????? ??????
        List<FileManage> fileManages = publishFileService.getFileManageByType(beanParam.getId());
        fileManages.forEach(x -> {
            String path = uploadPath + File.separator + x.getFile_name();
            File file = new File(path);
            if (file.exists()) {
                String base64 = new BASE64Encoder().encode(FileUtils.fileToByte(file));
                x.setBase64(base64);
            } else {
                x.setBase64("");
            }
        });
        publishBasicInfo.setFileManages(fileManages);
        return ResultInfo.success(publishBasicInfo);
    }

    /**
     *  ??????
     */
    @Transactional
    public ResultInfo addPublishBasicInfo(PublishBasicInfo bean, List<MultipartFile> files) {
        //??????????????????
        String no = DataUtils.getNo(DataType.PUBLISH_TYPE);
        bean.setIssue_no(no);
        bean.setIs_del(GlobalData.ISDEL_NO);
        bean.setCreate_time(new Date());
        bean.setCreate_user(null == bean.getUserId() ? sysUserService.getUser().getId() : bean.getUserId());
        //????????????
        if("1".equals(bean.getType())){
            //?????????????????????????????????????????????
//            PublishCategoryInfo publishCategoryInfo = publishCategoryInfoService.selectById(bean.getCategory());
//            bean.setApprover(publishCategoryInfo.getApprover());
//            bean.setPublisher(sysUserService.selectById(publishCategoryInfo.getPublisher()).getName());
            //??????
            bean.setStatus(GlobalData.PUBLISH_STATUS_APPROVE);
        }
        //????????????
        else if("2".equals(bean.getType())){
            //??????
            bean.setStatus(GlobalData.PUBLISH_STATUS_PUB);
        }
        int insert = insert(bean);
        if(insert > 0){
            //????????????????????????
            if(bean.getChangeVoList()!=null && bean.getChangeVoList().size()>0){
                bean.getChangeVoList().forEach(x->{
                    PublishChanges publishChanges = new PublishChanges();
                    publishChanges.setPublish_id(bean.getId());
                    publishChanges.setChange_id(Integer.valueOf(x.getId()));
                    publishChanges.setUserId(bean.getUserId());
                    publishChangesService.addPublishChanges(publishChanges);
                });
            }
            //??????????????????
            publishNodesService.batchAddPublishNodes(bean);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                PublishFile publishFile = new PublishFile();
                publishFile.setType(1);
                publishFile.setPublish_id(bean.getId());
                publishFile.setFile_id(id);
                publishFile.setIs_del(GlobalData.ISDEL_NO);
                publishFile.setCreate_time(new Date());
                publishFile.setCreate_user(sysUserService.getUser().getId());
                publishFileService.insert(publishFile);
            }
        }
        if(null == bean.getUserId()){
            return ResultInfo.success("????????????",new LogVo(bean.getId(), "??????", StringUtils.EMPTY));
        }else {
            return ResultInfo.success("????????????",new LogVo(bean.getUserId(),bean.getId(), "??????", StringUtils.EMPTY));
        }

    }

    /**
     *  ??????
     */
    public ResultInfo editPublishBasicInfo(PublishBasicInfo bean, List<MultipartFile> files) {
        // ?????????
        bean.setUpdate_user(sysUserService.getUser().getId());
        if(Base.notEmpty(bean.getStatus())&&"0".equals(bean.getStatus())){
            bean.setStatus("1");//?????????
        }
        // ????????????
        bean.setUpdate_time(new Date());
        int insert = 0;
        if("1".equals(bean.getType())){
            //?????????????????????????????????????????????
//            PublishCategoryInfo publishCategoryInfo = publishCategoryInfoService.selectById(bean.getCategory());
//            bean.setApprover(publishCategoryInfo.getApprover());
//            bean.setPublisher(sysUserService.selectById(publishCategoryInfo.getPublisher()).getName());
            //??????
//            bean.setStatus(GlobalData.PUBLISH_STATUS_REVOKED);
            bean.setStatus("1");//?????????
            insert = updateById(bean);
        }
        //????????????
        else if("2".equals(bean.getType())){
            //??????
            bean.setStatus(GlobalData.PUBLISH_STATUS_PUB);
            insert = updateById(bean);
        }
        if(insert > 0){
            // ?????????????????????????????????
            UpdateWrapper<PublishChanges> wrapper = new UpdateWrapper<>();
            wrapper.lambda().set(PublishChanges::getIs_del, "1").
                    eq(PublishChanges::getPublish_id, bean.getId());
            publishChangesService.update(null, wrapper);
            //????????????????????????
            if(bean.getChangeVoList()!=null && bean.getChangeVoList().size()>0){
                bean.getChangeVoList().forEach(x->{
                    PublishChanges publishChanges = new PublishChanges();
                    publishChanges.setPublish_id(bean.getId());
                    publishChanges.setChange_id(Integer.valueOf(x.getId()));
                    publishChangesService.addPublishChanges(publishChanges);
                });
            }
            // ?????????????????????????????????
            UpdateWrapper<PublishNodes> publishNodesUpdateWrapper = new UpdateWrapper<>();
            publishNodesUpdateWrapper.lambda().set(PublishNodes::getIs_del, "1").
                    eq(PublishNodes::getPublish_id, bean.getId());
            publishNodesService.update(null, publishNodesUpdateWrapper);
            //??????????????????
            publishNodesService.batchAddPublishNodes(bean);
        }

        //???????????? ??????
        //???????????? ????????????????????????
        String uploadedFiles = bean.getUploadedFiles();
        List<FileManage> fileManages = publishFileService.getFileManageByType(bean.getId());
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<PublishFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(PublishFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(PublishFile::getPublish_id, bean.getId())
                    .in(PublishFile::getFile_id, original);
            publishFileService.update(null, wrapper);
        }
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                int id = fileManageService.fileUpload(file);
                PublishFile publishFile = new PublishFile();
                publishFile.setPublish_id(bean.getId());
                publishFile.setFile_id(id);
                //?????? 1?????????????????? 2??????????????????
                publishFile.setType(1);
                publishFile.setIs_del(GlobalData.ISDEL_NO);
                publishFile.setCreate_time(new Date());
                publishFile.setCreate_user(sysUserService.getUser().getId());
                publishFileService.insert(publishFile);
            }
        }
        return ResultInfo.success("????????????",new LogVo(bean.getId(), "??????", StringUtils.EMPTY));
    }

    /**
     *  ??????
     */
    public ResultInfo delPublishBasicInfo(PublishBasicInfo bean) {
        // ?????????
        bean.setUpdate_user(null == bean.getUserId() ? sysUserService.getUser().getId() : bean.getUserId());
        // ????????????
        bean.setUpdate_time(new Date());
        bean.setIs_del(GlobalData.ISDEL_YES);
        int insert = updateById(bean);
        if(insert > 0){
            // ?????????????????????????????????
            UpdateWrapper<PublishChanges> wrapper = new UpdateWrapper<>();
            wrapper.lambda().set(PublishChanges::getIs_del, "1").
                    eq(PublishChanges::getPublish_id, bean.getId());
            publishChangesService.update(null, wrapper);
            // ?????????????????????????????????
            UpdateWrapper<PublishNodes> publishNodesUpdateWrapper = new UpdateWrapper<>();
            publishNodesUpdateWrapper.lambda().set(PublishNodes::getIs_del, "1").
                    eq(PublishNodes::getPublish_id, bean.getId());
            publishNodesService.update(null, publishNodesUpdateWrapper);
        }
        return ResultInfo.success("????????????");
    }

    /**
     *  ??????
     */
    public ResultInfo revokePublish(PublishBasicInfo bean) {
        bean.setStatus(GlobalData.PUBLISH_STATUS_REVOKED);
        updateById(bean);
//        return ResultInfo.success("????????????");
        if (null == bean.getUserId()){
            return ResultInfo.success("???????????????",new LogVo(bean.getId(), "??????", StringUtils.EMPTY));
        }else {
            return ResultInfo.success("???????????????",new LogVo(bean.getUserId(), bean.getId(), "??????", StringUtils.EMPTY));
        }
    }

    /**
     *  ??????
     */
    @Transactional
    public ResultInfo approvePublish(PublishBasicInfo publishBasicInfo) {

        //??????????????????
        PublishApprovalInfo publishApprovalInfo = new PublishApprovalInfo();
        publishApprovalInfo.setBasic_info_id(publishBasicInfo.getId());
        publishApprovalInfo.setApprover(null == publishBasicInfo.getUserId() ?sysUserService.getUser().getId() : publishBasicInfo.getUserId());
        publishApprovalInfo.setApproval_opinions(publishBasicInfo.getApproval_opinions());
        publishApprovalInfo.setApproval_time(new Date());
        publishApprovalInfo.setStatus(publishBasicInfo.getIsPass());
        publishApprovalInfoService.addPublishApprovalInfo(publishApprovalInfo);
        //??????????????????
        PublishBasicInfo oldPublishBasicInfo = selectById(publishBasicInfo.getId());
        if(oldPublishBasicInfo.getTester_id() != publishBasicInfo.getTester_id()){
            oldPublishBasicInfo.setTester_id(publishBasicInfo.getTester_id());
        }
        //?????? ????????????????????????????????????
        QueryWrapper<PublishNodes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_id",publishBasicInfo.getId()).eq("time_nodes",2).eq("time_status",1).eq("is_del",'0');
        List<PublishNodes> publishNodes = publishNodesService.selectList(queryWrapper);
        if(publishNodes != null && publishNodes.size() > 1){
            //??????????????????
            List<Integer> list = publishNodes.stream().map(x -> x.getSort()).collect(Collectors.toList());
            Collections.sort(list);
            List<PublishNodes> publishNodesList = publishNodesService.selectList(new QueryWrapper<PublishNodes>().eq("publish_id", publishBasicInfo.getId()).eq("sort", list.get(0)));
            PublishNodes publishNodes1 = new PublishNodes();
            if (!CollectionUtils.isEmpty(publishNodesList)){
                 publishNodes1 = publishNodesList.get(0);
                 publishNodes1.setUserId(publishBasicInfo.getUserId());
            }
            //?????????????????? ??????????????????????????????????????????????????????????????????????????????
            if (publishBasicInfo.getIsPass().equals("1")) {
                //????????????????????????
                Integer userId = null;
                if (null == publishBasicInfo.getUserId()){
                    userId = sysUserService.getUser().getId();
                }else {
                    userId = publishBasicInfo.getUserId();
                }

                //?????????????????????????????????
                PublishBasicInfo  publishBasicInfo1  = publishBasicInfoMapper.selectById(publishBasicInfo.getId());
                Integer categoryId = Integer.parseInt(publishBasicInfo1.getCategory());
                //?????????????????????????????? ??????????????????????????????????????????????????????
                QueryWrapper<PublishCategoryApprove> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("is_del","0").eq("publish_category_id",categoryId).orderByAsc("sort");
                List<PublishCategoryApprove> approveList = publishCategoryApproveService.selectList(queryWrapper1);
                int count = 0 ;
                for (int i = 0 ; i<approveList.size() ; i++){
                    //?????????????????????????????????????????????,???????????????????????????????????????
                    if (approveList.get(i).getApprove_user().equals(userId)){
                        //??????????????????????????? ?????????????????????????????????
                        if (i == approveList.size()-1){
                            publishNodes1.setTime_status(0);
                            publishNodesService.editPublishNodes(publishNodes1);
                            publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_TEST);
                            count++;
                            break;
                        }

                    }
                }
                if (count == 0){
                    publishNodes1.setTime_status(1);
                    publishNodesService.editPublishNodes(publishNodes1);
                    publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_APPROVE);
                    publishBasicInfo.setPass_approve(userId);
                }

            }else if(publishBasicInfo.getIsPass().equals("2")){
                publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_APPROVE_REFUSE);
            }
        }else if(publishNodes != null && publishNodes.size() == 1){
            if(publishBasicInfo.getIsPass().equals("1")){
                publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_TEST);
                //??????????????????
                publishNodes.get(0).setTime_status(0);
                publishNodes.get(0).setUserId(publishBasicInfo.getUserId());
                publishNodesService.editPublishNodes(publishNodes.get(0));
            }else if(publishBasicInfo.getIsPass().equals("2")){
                publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_APPROVE_REFUSE);
            }
        }
        updateById(publishBasicInfo);

        if (null == publishBasicInfo.getUserId()){
            return ResultInfo.success("???????????????",new LogVo(publishBasicInfo.getId(), "??????", StringUtils.EMPTY));
        }else {
            return ResultInfo.success("???????????????",new LogVo(publishBasicInfo.getUserId(), publishBasicInfo.getId(), "??????", StringUtils.EMPTY));
        }


    }

    /**
     *  ??????
     */
    @Transactional
    public ResultInfo testPublish(PublishBasicInfo publishBasicInfo) {
        //??????????????????
//        publishBasicInfo.setTest_plan(test_plan);
//        publishBasicInfo.setTest_result(test_result);
        publishBasicInfo.setTest_time(new Date());
        publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_PUB);
        if(updateById(publishBasicInfo) > 0){
            //??????????????????
            QueryWrapper<PublishNodes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("publish_id",publishBasicInfo.getId()).eq("time_nodes",3).eq("time_status",1).eq("is_del",GlobalData.ISDEL_NO);
            PublishNodes publishNodes = publishNodesService.selectOne(queryWrapper);
            publishNodes.setTime_status(0);
            if (null != publishBasicInfo.getUserId()){
                publishNodes.setUserId(publishBasicInfo.getUserId());
            }
            publishNodesService.editPublishNodes(publishNodes);
            if(null == publishBasicInfo.getUserId()){
                return ResultInfo.success("???????????????",new LogVo(publishBasicInfo.getId(), "??????", StringUtils.EMPTY));
            }else{
                return ResultInfo.success("???????????????",new LogVo(publishBasicInfo.getUserId(), publishBasicInfo.getId(), "??????", StringUtils.EMPTY));
            }
        }
        return ResultInfo.error("???????????????");
    }

    /**
     *  ??????
     */
    @Transactional
    public ResultInfo doPublish(PublishBasicInfo publishBasicInfo) {
        //??????????????????
//        publishBasicInfo.setRemarks(remarks);
//        publishBasicInfo.setPublish_notice(publish_notice.toString());
        publishBasicInfo.setPublish_time(new Date());
        if(publishBasicInfo.getIsPass().equals("1")){
            publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_PUB_SUCCESS);
        }else if(publishBasicInfo.getIsPass().equals("2")){
            publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_PUB_FAIL);
        }
        //??????????????????
        QueryWrapper<PublishNodes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_id",publishBasicInfo.getId()).eq("time_nodes",4).eq("time_status",1).eq("is_del",GlobalData.ISDEL_NO);
        PublishNodes publishNodes = publishNodesService.selectOne(queryWrapper);
        if(publishBasicInfo.getIsPass().equals("1")){
            publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_PUB_SUCCESS);
            publishNodes.setTime_status(0);
            publishNodes.setUserId(publishBasicInfo.getUserId());
            publishNodesService.editPublishNodes(publishNodes);
        }else if(publishBasicInfo.getIsPass().equals("2")){
            publishBasicInfo.setStatus(GlobalData.PUBLISH_STATUS_PUB_FAIL);
        }
        if(updateById(publishBasicInfo)>0){
            if (null == publishBasicInfo.getUserId()){
                return ResultInfo.success("???????????????",new LogVo(publishBasicInfo.getId(), "??????", StringUtils.EMPTY));
            }else {
                return ResultInfo.success("???????????????",new LogVo(publishBasicInfo.getUserId(),publishBasicInfo.getId(), "??????", StringUtils.EMPTY));
            }

        }
        return ResultInfo.error("???????????????");
    }

    /**
     * ??????????????????excel
     */
    public ResultInfo exporPublishExcel(HttpServletResponse response,PublishBasicInfo beanParam) {

        PageInfo<PublishBasicInfo> publishBasicInfoPageInfo = toList(1, Integer.MAX_VALUE, beanParam);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("id", "??????");
        writer.addHeaderAlias("issue_no", "????????????");
        writer.addHeaderAlias("status", "??????");
        writer.addHeaderAlias("type", "????????????");
        writer.addHeaderAlias("category", "????????????");
        writer.addHeaderAlias("create_user", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("reason", "??????????????????");
        writer.addHeaderAlias("content", "????????????");
        writer.addHeaderAlias("approver_id", "?????????");
        writer.addHeaderAlias("publish_plan", "????????????");
        writer.addHeaderAlias("test_plan", "????????????");
        writer.addHeaderAlias("tester_id", "?????????");
        writer.addHeaderAlias("test_result", "????????????");
        writer.addHeaderAlias("test_time", "????????????");
        writer.addHeaderAlias("publisher", "?????????");
        writer.addHeaderAlias("publish_time", "????????????");
        writer.addHeaderAlias("remarks", "????????????");
        writer.setOnlyAlias(true);
        writer.write(publishBasicInfoPageInfo.getList(), true);
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
    public ResultInfo importPublishList(MultipartFile file){
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

            List<PublishBasicInfo> success = new ArrayList<>();
            List<PublishBasicInfo> error = new ArrayList<>();
            List<PublishBasicInfo> all  = new ArrayList<>();


            if(size >1){
                for(int i = 1; i < size; i++){
                    PublishBasicInfo publishBasicInfo = new PublishBasicInfo();
                    List<Object> line = data.get(i);
                    if(StringUtils.isEmpty(line.get(0).toString()) || StringUtils.isEmpty(line.get(1).toString()) || StringUtils.isEmpty(line.get(2).toString())||
                            StringUtils.isEmpty(line.get(3).toString())||StringUtils.isEmpty(line.get(4).toString())||StringUtils.isEmpty(line.get(5).toString())||
                            StringUtils.isEmpty(line.get(6).toString()) ){

                        publishBasicInfo.setCategory(line.get(0).toString());
                        publishBasicInfo.setType_name( line.get(1).toString());
                        publishBasicInfo.setTitle( line.get(2).toString());
                        publishBasicInfo.setReason( line.get(3).toString());
                        publishBasicInfo.setContent( line.get(4).toString());
                        publishBasicInfo.setPublish_plan( line.get(5).toString());
                        publishBasicInfo.setTester_id( line.get(6).toString());

                        error.add(publishBasicInfo);
                        publishBasicInfo.setIs_success("??????");
                        all.add(publishBasicInfo);
                    }else {
                        publishBasicInfo.setTitle( line.get(2).toString());
                        publishBasicInfo.setReason( line.get(3).toString());
                        publishBasicInfo.setContent( line.get(4).toString());
                        publishBasicInfo.setPublish_plan( line.get(5).toString());

                        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
                        queryWrapper.eq("user_name",StringUtils.isEmpty(line.get(6).toString()));
                        List<SysUser> sysUserList = sysUserService.selectList(queryWrapper);
                        if (!CollectionUtils.isEmpty(sysUserList)){
                            publishBasicInfo.setCreate_user(sysUserList.get(0).getId());
                            publishBasicInfo.setTester_id(sysUserList.get(0).getId()+"");
                        }
                        publishBasicInfo.setCreate_time(new Date());

                        //????????????
                        String temp = line.get(1).toString();
                        if ("????????????".equals(temp)){
                            publishBasicInfo.setType("1");
                        }else {
                            publishBasicInfo.setType("2");
                        }
                        publishBasicInfo.setType_name(temp);

                        //????????????
                        QueryWrapper<PublishCategoryInfo> queryWrapper2 = new QueryWrapper();
                        queryWrapper2.eq("name",line.get(0).toString());
                        List<PublishCategoryInfo> publishCategoryInfoList = publishCategoryInfoService.selectList(queryWrapper2) ;
                        if(!CollectionUtils.isEmpty(publishCategoryInfoList)){
                            publishBasicInfo.setCategory(publishCategoryInfoList.get(0).getId()+"");
                            PublishCategoryInfo publishCategoryInfo = new PublishCategoryInfo() ;
                            publishCategoryInfo.setId(Integer.valueOf(publishBasicInfo.getCategory()));
                            String psr = publishCategoryInfoService.getPublishCategoryString(publishCategoryInfo) ;
                            publishBasicInfo.setApprover(psr);
                            PublishCategoryInfo publishCategoryInfo2 = publishCategoryInfoService.selectById(Integer.valueOf(publishBasicInfo.getCategory()));
                            SysUser user1 = sysUserService.selectById(publishCategoryInfo2.getPublisher());
                            publishCategoryInfo.setPublisherName(null == user1 ? "" : user1.getName());
                            publishBasicInfo.setPublisher(user1.getId()+"");
                            publishBasicInfo.setStatus("1");
                        }






                        String no = DataUtils.getNo(DataType.PUBLISH_TYPE);
                        publishBasicInfo.setIssue_no(no);
                        publishBasicInfo.setIs_del(GlobalData.ISDEL_NO);

                        publishBasicInfo.setIs_success("??????");
                        success.add(publishBasicInfo);
                        all.add(publishBasicInfo);

                        publishBasicInfoMapper.insert(publishBasicInfo);


                    }
                }
            }
            PublishBasicInfo p =  new PublishBasicInfo();
            p.setSuccess(success);
            p.setError(error);
            p.setAll(all);
            return ResultInfo.success(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("?????????????????????");

    }


    /**
     * description?????????????????????excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    public ResultInfo exporMyPublishExcel(HttpServletResponse response) {
        Integer userId = sysUserService.getUser().getId();
        PublishBasicInfo publishBasicInfo = new PublishBasicInfo();
        publishBasicInfo.setCreate_user(userId);
        List<PublishBasicInfo> list = publishBasicInfoMapper.toList(publishBasicInfo);
        for (PublishBasicInfo temp : list){
            if ("1".equals(temp.getType())){
                temp.setCategory_name("????????????");
            }else {
                temp.setCategory_name("????????????");
            }
            if (null != temp.getTester_id()){
                SysUser sysUser = sysUserService.selectById(temp.getTester_id());
                if (null != sysUser){
                    temp.setTester_name(sysUser.getUser_name());
                }
            }
            if (null != temp.getPublisher()){
                SysUser sysUser = sysUserService.selectById(temp.getPublisher());
                if (null != sysUser){
                    temp.setPublisher_name(sysUser.getUser_name());
                }
            }
            if (null != temp.getStatus()){
                if ("0".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("1".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("2".equals(temp.getStatus())){
                    temp.setStatus("????????????");
                }else if ("3".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("4".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("5".equals(temp.getStatus())){
                    temp.setStatus("????????????");
                }else {
                    temp.setStatus("????????????");
                }

            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("issue_no", "????????????");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("create_user_name", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("category_name", "????????????");
        writer.addHeaderAlias("approver", "?????????");
        writer.addHeaderAlias("tester_name", "?????????");
        writer.addHeaderAlias("publisher_name", "?????????");
        writer.addHeaderAlias("status", "??????");
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

    public ResultInfo exportPublishExcel(HttpServletResponse response) {
        PublishBasicInfo publishBasicInfo = new PublishBasicInfo();
        List<PublishBasicInfo> list = publishBasicInfoMapper.toList(publishBasicInfo);
        for (PublishBasicInfo temp : list){
            if ("1".equals(temp.getType())){
                temp.setCategory_name("????????????");
            }else {
                temp.setCategory_name("????????????");
            }
            if (null != temp.getTester_id()){
                SysUser sysUser = sysUserService.selectById(temp.getTester_id());
                if (null != sysUser){
                    temp.setTester_name(sysUser.getUser_name());
                }
            }
            if (null != temp.getPublisher()){
                SysUser sysUser = sysUserService.selectById(temp.getPublisher());
                if (null != sysUser){
                    temp.setPublisher_name(sysUser.getUser_name());
                }
            }
            if (null != temp.getStatus()){
                if ("0".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("1".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("2".equals(temp.getStatus())){
                    temp.setStatus("????????????");
                }else if ("3".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("4".equals(temp.getStatus())){
                    temp.setStatus("?????????");
                }else if ("5".equals(temp.getStatus())){
                    temp.setStatus("????????????");
                }else {
                    temp.setStatus("????????????");
                }

            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("issue_no", "????????????");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("create_user_name", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("category_name", "????????????");
        writer.addHeaderAlias("approver", "?????????");
        writer.addHeaderAlias("tester_name", "?????????");
        writer.addHeaderAlias("publisher_name", "?????????");
        writer.addHeaderAlias("status", "??????");
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

    public ResultInfo exportDbPublishExcel(HttpServletResponse response){
        List<PublishBasicInfo> publishBasicInfoVos = getDbList(new PublishBasicInfo());
        String [] statusTypes = {"?????????","?????????","????????????","?????????","?????????","????????????","????????????"};
        for (PublishBasicInfo temp : publishBasicInfoVos){
            temp.setCategory_name("1".equals(temp.getType()) ? "????????????" : "????????????");
            try {
                temp.setStatus(statusTypes[Integer.parseInt(temp.getStatus())]);
            } catch (NumberFormatException e) {
                temp.setStatus(statusTypes[6]);
            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("issue_no", "????????????");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("create_user_name", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("category_name", "????????????");
        writer.addHeaderAlias("approver", "?????????");
        writer.addHeaderAlias("tester_name", "?????????");
        writer.addHeaderAlias("publisher_name", "?????????");
        writer.addHeaderAlias("status", "??????");
        writer.setOnlyAlias(true);
        writer.write(publishBasicInfoVos, true);
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

    public ResultInfo exportPublishExcelTemplate(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();

        List<PublishCategoryInfo> publishCategoryInfos =  publishCategoryInfoService.getAllList();
        List<SysUser> sysUsers = sysUserService.getUsers() ;
        row.put("????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("??????", StringUtils.EMPTY);
        row.put("??????????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("????????????", StringUtils.EMPTY);
        row.put("?????????", StringUtils.EMPTY);

        list.add(row);

        ExcelWriter writer = ExcelUtil.getWriter();

        StyleSet styleSet = writer.getStyleSet();
        Sheet sheet = writer.getSheet();
        //?????????????????? ??????????????????
        int firstRow = 1;
        String[] fblx = {"????????????", "????????????"};
        String[] statusList = publishCategoryInfos.stream().map(PublishCategoryInfo::getName).toArray(String[]::new);
        String[] statusList2 = sysUsers.stream().map(SysUser::getUser_name).toArray(String[]::new);
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList, firstRow, 0));
        writer.addValidationData(setSelectCol(styleSet, sheet, fblx, firstRow, 1));
        writer.addValidationData(setSelectCol(styleSet, sheet, statusList2, firstRow, 7));



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


    /**
     * ???????????? - ?????????????????????
     */
    public PageInfo<PublishBasicInfo> getPublishListByParticipate(Integer pageIndex, Integer pageSize, PublishBasicInfo beanParam) {
        Integer userId = sysUserService.getUser().getId();
        Map<String, SysUser> userMap = getUserMap1();
        // ????????????
        List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(
                Wrappers.<PublishCategoryApprove>lambdaQuery().eq(PublishCategoryApprove::getIs_del, GlobalData.ISDEL_NO)
                        .eq(PublishCategoryApprove::getApprove_user, userId)
        );
        // ????????????????????????
        List<Integer> publishCategoryIds = publishCategoryApproves.stream().distinct().map(PublishCategoryApprove::getPublish_category_id).collect(Collectors.toList());
        PageHelper.startPage(pageIndex, pageSize);
        // ??????????????????
        List<PublishBasicInfo> publishBasicInfos = selectList(Wrappers.<PublishBasicInfo>lambdaQuery()
                .eq(PublishBasicInfo::getIs_del, GlobalData.ISDEL_NO)
                .and(StringUtils.isNotBlank(beanParam.getKeyword()), i ->
                        i.like(PublishBasicInfo::getTitle, beanParam.getKeyword())
                        .or()
                        .like(PublishBasicInfo::getIssue_no, beanParam.getKeyword())
                )

                .and(StringUtils.isNotBlank(beanParam.getStatus()), i ->
                        i.eq(PublishBasicInfo::getStatus, beanParam.getStatus()))

                .and(StringUtils.isNotBlank(beanParam.getType()), i ->
                        i.eq(PublishBasicInfo::getType, beanParam.getType()))


                .and(StringUtils.isNotBlank(beanParam.getType()), i ->
                        i.eq(PublishBasicInfo::getType, beanParam.getType()))

                .and(StringUtils.isNotBlank(beanParam.getCreate_time_start()), i ->
                        i.gt(PublishBasicInfo::getCreate_time, beanParam.getCreate_time_start()+ " 00:00:00"))

                .and(StringUtils.isNotBlank(beanParam.getCreate_time_end()), i ->
                        i.lt(PublishBasicInfo::getCreate_time, beanParam.getCreate_time_end()+ " 23:59:59"))

                .and(wrapper -> wrapper.eq(PublishBasicInfo::getCreate_user, userId)
                        .or()
                        .in(!CollectionUtils.isEmpty(publishCategoryIds), PublishBasicInfo::getCategory, publishCategoryIds.stream().map(String::valueOf).collect(Collectors.toList()))
                        .eq(PublishBasicInfo::getType, "1")
                        .or()
                        .eq(PublishBasicInfo::getTester_id, userId).eq(PublishBasicInfo::getType, "1")
                        .or()
                        .eq(PublishBasicInfo::getPublisher, userId)
                )
        );
        for (PublishBasicInfo publishBasicInfo : publishBasicInfos) {
            publishBasicInfo.setCreate_user_name(null == userMap.get(publishBasicInfo.getCreate_user().toString())?"":userMap.get(publishBasicInfo.getCreate_user().toString()).getName());
            publishBasicInfo.setTester_name(null == userMap.get(publishBasicInfo.getTester_id())? "":userMap.get(publishBasicInfo.getTester_id()).getName());
            publishBasicInfo.setPublisher_name(null == userMap.get(publishBasicInfo.getPublisher())?"":userMap.get(publishBasicInfo.getTester_id()).getName());
            // ????????????????????????
            List<PublishCategoryApprove> temps = publishCategoryApproveService.selectList(
                    Wrappers.<PublishCategoryApprove>lambdaQuery()
                            .eq(PublishCategoryApprove::getIs_del, GlobalData.ISDEL_NO)
                            .eq(PublishCategoryApprove::getPublish_category_id, publishBasicInfo.getCategory())
            );
            if(CollectionUtils.isEmpty(temps)){
                continue;
            }
            temps.forEach(x -> x.setApprove_user_name(null == userMap.get(x.getApprove_user() + "")?"":userMap.get(x.getApprove_user()+ "").getName()));
            publishBasicInfo.setApprover(temps.stream().map(x->x.getApprove_user_name()).collect(Collectors.joining(",")));

        }
        return new PageInfo<>(publishBasicInfos);
    }

    public ResultInfo exportPublishParticipateExcel(HttpServletResponse response) {
        PageInfo<PublishBasicInfo> pageInfo = getPublishListByParticipate(1, 10000000, new PublishBasicInfo());
        List<PublishBasicInfo> list = pageInfo.getList();
        String [] statusTypes = {"?????????","?????????","????????????","?????????","?????????","????????????","????????????"};
        for (PublishBasicInfo temp : list){
            temp.setCategory_name("1".equals(temp.getType()) ? "????????????" : "????????????");
            try {
                temp.setStatus(statusTypes[Integer.parseInt(temp.getStatus())]);
            } catch (NumberFormatException e) {
                temp.setStatus(statusTypes[6]);
            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("issue_no", "????????????");
        writer.addHeaderAlias("title", "??????");
        writer.addHeaderAlias("create_user_name", "?????????");
        writer.addHeaderAlias("create_time", "????????????");
        writer.addHeaderAlias("category_name", "????????????");
        writer.addHeaderAlias("approver", "?????????");
        writer.addHeaderAlias("tester_name", "?????????");
        writer.addHeaderAlias("publisher_name", "?????????");
        writer.addHeaderAlias("status", "??????");
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


    public List<SysLog> getLogInfo(PublishBasicInfo publishBasicInfo){
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("module", LogEnums.LOG_PUBLISH.getValue());
        queryWrapper.eq("bill_id",publishBasicInfo.getId());
        queryWrapper.orderByDesc("operate_time");
        List<SysLog> list = sysLogService.selectList(queryWrapper);
        return list;
    }


    public List<ChangesOf> publishedSources(){
        return publishBasicInfoMapper.publishedSources();
    }

    public List<ChangesOf> releaseTheCategory(){
        return publishBasicInfoMapper.releaseTheCategory();
    }


    public List<ChangesOf> publishType(){
        return publishBasicInfoMapper.publishType();
    }
}
