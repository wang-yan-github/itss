package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsTypeDao;
import com.jsdc.itss.mapper.AssetsClGroupMapper;
import com.jsdc.itss.mapper.AssetsTypeMapper;
import com.jsdc.itss.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

/**
 * @Author wh
 * @create 2022-03-08 14:15:25
 */
@Service
@Transactional
public class AssetsTypeService extends BaseService<AssetsTypeDao, AssetsType> {

    @Autowired
    private AssetsTypeMapper assetsTypeMapper;
    @Autowired
    private AssetsTypeDao assetsTypeDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AssetsClGroupMapper assetsClGroupMapper;
    @Autowired
    private AssetsChildrenService assetsChildrenService;
    @Autowired
    private AssetsExpandFormFieldService assetsExpandFormFieldService;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private SysExtendFormService sysExtendFormService;
    @Value("${file.upload-path}")
    private String uploadPath;
    @Value("${file.upload-path-app}")
    private String picUploadPath;

    public PageInfo<AssetsType> toList(Integer pageIndex, Integer pageSize, AssetsType assetsType) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsType> assetsTypes = selectList(Wrappers.<AssetsType>lambdaQuery()
                .like(StringUtils.isNotEmpty(assetsType.getName()),AssetsType::getName,assetsType.getName())
                .eq(AssetsType::getIs_del,String.valueOf(0))
        );

        for (AssetsType temp : assetsTypes){
            Integer groupId = temp.getGroup_id();
            AssetsClGroup assetsClGroup = assetsClGroupMapper.selectById(groupId);
            if (assetsClGroup != null){
                temp.setGroup_name(assetsClGroup.getGroup_name());
            }

            if(null != temp.getForm_id() && temp.getForm_id().intValue() >0 ) {
                List<AssetsExpandFormField> sysExtendFormSees = assetsExpandFormFieldService.getExpandFormFieldFormId(temp);
                if (!CollectionUtils.isEmpty(sysExtendFormSees)) {
                    temp.setSysExtendFormSees(sysExtendFormSees);
                }
            }

            if (null != temp.getForm_id()){
                SysExtendForm sysExtendForm =sysExtendFormService.selectById(temp.getForm_id());
                if (null != sysExtendForm){
                    temp.setFormName(sysExtendForm.getForm_name());
                }

            }

            List<FileManage> fileManageList = new ArrayList<>();
            if (null != temp.getFile_id()){
                FileManage fileManage = fileManageService.selectById(temp.getFile_id());
                if (null != fileManage){
                    String path = uploadPath + File.separator + fileManage.getFile_name();
                    File file = new File(path);
                    String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
                    fileManage.setBase64(base64);
                    fileManageList.add(fileManage);
                    temp.setFileManageList(fileManageList);
                }

            }

        }
        PageInfo<AssetsType> page = new PageInfo<>(assetsTypes);
        return page;
    }

    public List<AssetsType> getList(AssetsType assetsType) {
        QueryWrapper<AssetsType> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(assetsType.getName())){
            queryWrapper.like("name",assetsType.getName());
        }
        queryWrapper.eq("is_del",0);
        if (Base.notEmpty(assetsType.getIds())) {
            queryWrapper.in("id",assetsType.getIds());
        }
        List<AssetsType> assetsTypes = selectList(queryWrapper);
        List<AssetsType> result  = new ArrayList<>();
        for (AssetsType temp : assetsTypes){
            Integer groupId = temp.getGroup_id();
            AssetsClGroup assetsClGroup = assetsClGroupMapper.selectById(groupId);
            if (assetsClGroup != null){
                temp.setGroup_name(assetsClGroup.getGroup_name());
            }

            if(null != temp.getFile_id()){
                FileManage fileManage = fileManageService.selectById(temp.getFile_id());
                if (null != fileManage){

                    temp.setPic_path(picUploadPath+fileManage.getFile_name());

                }
            }

            result.add(temp);


            if(null != temp.getForm_id() && temp.getForm_id().intValue() >0 ) {
                //List<SysExtendFormSee> sysExtendFormSees = sysExtendFormSeeService.getByExtendId(temp.getForm_id());
                List<AssetsExpandFormField> sysExtendFormSees = assetsExpandFormFieldService.getExpandFormFieldFormId(temp);
                if (null != sysExtendFormSees) {
                    temp.setSysExtendFormSees(sysExtendFormSees);
                }
            }
        }
        return result;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsType> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsType assetsType = selectOne(queryWrapper);

        return ResultInfo.success(assetsType);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsType(AssetsType bean, MultipartFile files) {



        if (bean.getId() == null){
            // 通过这个方法添加的都是自定义类型 其它26种是写死在db中的 只能修改不能删除
            bean.setAdd_address("/pzgl/customManageAdd");
            bean.setCheck_address("/pzgl/customManageView");
            bean.setEdit_address("/pzgl/customManageEdit");
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);

            if(null != files){
                int id = fileManageService.fileUpload(files);
                bean.setFile_id(id);
                updateById(bean);

            }
            return ResultInfo.success();
        }else{
            // 修改者
            bean.setUpdate_user(sysUserService.getUser().getId());
            // 修改时间
            bean.setUpdate_time(new Date());

            if(null != files){
                int id = fileManageService.fileUpload(files);
                bean.setFile_id(id);
            }
            updateById(bean);
            return ResultInfo.success();
        }

    }

    /**
     *  编辑
     */

    public ResultInfo delAssetsType(Integer ids) {
        UpdateWrapper<AssetsType> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",ids);
        updateWrapper.set("is_del","1");
        assetsTypeMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }


    public ResultInfo getAssetsType( AssetsType assetsType) {
        QueryWrapper<AssetsType> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(assetsType.getName())){
            queryWrapper.like("name",assetsType.getName());
        }
        if(null != assetsType && null != assetsType.getId() && assetsType.getId().intValue() >0){
            queryWrapper.eq("id",assetsType.getId());
        }
        queryWrapper.eq("is_del",0);
        List<AssetsType> assetsTypes = selectList(queryWrapper);
        for (int i = 0; i < assetsTypes.size(); i++) {
            AssetsType temp =assetsTypes.get(i);
            System.out.println(temp.getName());
            AssetsChildren ac = new AssetsChildren();
            ac.setAssets_type_id(temp.getId());
            List<AssetsChildren> acLists = assetsChildrenService.getACListByAssetsTypeId(ac);
            if (acLists != null){
                temp.setAssetsChildrens(acLists);
            }
            if(null != temp.getForm_id() && temp.getForm_id().intValue() >0 ) {
//                List<SysExtendFormSee> sysExtendFormSees = assetsExpandFormFieldService.getByExtendId(temp.getForm_id());
                List<AssetsExpandFormField> sysExtendFormSees = assetsExpandFormFieldService.getExpandFormFieldFormId(temp);
                if (null != sysExtendFormSees) {
                    temp.setSysExtendFormSees(sysExtendFormSees);
                }
            }
        }
        return ResultInfo.success(assetsTypes);
    }

    public List<AssetsType> getAll(){
        LambdaQueryWrapper<AssetsType> wrapper = new LambdaQueryWrapper();
        wrapper.eq(AssetsType::getIs_del, GlobalData.ISDEL_NO);
        wrapper.eq(AssetsType::getIs_enable, GlobalData.ISDEL_NO);
        return selectList(wrapper);
    }
}
