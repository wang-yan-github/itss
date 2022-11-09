package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsSupplierContractDao;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.vo.AssetsSupplierContractVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Service
@Transactional
public class AssetsSupplierContractService extends BaseService<AssetsSupplierContractDao, AssetsSupplierContract> {

    @Autowired
    private AssetsSupplierContractMapper assetsSupplierContractMapper;
    @Autowired
    private AssetsContractContactMapper contractContactMapper;
    @Autowired
    private AssetsSupplierContractUserMapper assetsSupplierContractUserMapper;
    @Autowired
    private AssetsSupplierContractDao assetsSupplierContractDao;
    @Autowired
    private AssetsSupplierContractMapMapper assetsSupplierContractMapMapper;
    @Autowired
    private AssetsManageMapper assetsManageMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private AssetsEnclosureService assetsEnclosureService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private AssetsTypeService assetsTypeService;
    @Autowired
    private AssetsChildrenService assetsChildrenService;
    @Autowired
    private AssetsCompanyService assetsCompanyService;
    @Autowired
    private AssetsStatusService assetsStatusService;
    @Autowired
    private AssetsSupplierContractFileMapper assetsSupplierContractFileMapper;


    @Value("${file.upload-path}")
    private String uploadPath;


    public PageInfo<AssetsSupplierContractVo> toList(Integer pageIndex, Integer pageSize, AssetsSupplierContract beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsSupplierContractVo> assetsSupplierContractVos = assetsSupplierContractMapper.toList(beanParam);

        for(AssetsSupplierContractVo member : assetsSupplierContractVos){
            List<FileManage> fileManageList = new ArrayList<>();

            QueryWrapper<AssetsSupplierContractFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("supplier_id",member.getId());
            queryWrapper.eq("is_del","0");
            //附件
            List<AssetsSupplierContractFile> fileManage1 = assetsSupplierContractFileMapper.selectList(queryWrapper);
            for (int i = 0 ;  i < fileManage1.size() ; i++){
                AssetsSupplierContractFile AssetsSupplierContractFile = fileManage1.get(i);
                FileManage fileManage = fileManageService.selectById(AssetsSupplierContractFile.getFile_id());
                String path = uploadPath + File.separator + fileManage.getFile_name();
                File file = new File(path);
                String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
                fileManage.setBase64(base64);
                fileManageList.add(fileManage);
            }
            member.setFileManageList(fileManageList);
//            fileManages.forEach(x -> {
//                String path = uploadPath + File.separator + x.getFile_name();
//                File file = new File(path);
//                String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
//                x.setBase64(base64);
//            });
//            assetsManage.setFileManageList(fileManages);

            //得到联系人关联表
            List<AssetsSupplierContractUser> users = assetsSupplierContractUserMapper.selectList(Wrappers.<AssetsSupplierContractUser>lambdaQuery()
                    .eq(AssetsSupplierContractUser::getSupplier_id, member.getId())
                    .eq(AssetsSupplierContractUser::getIs_del, String.valueOf(0))
            );
            if(!CollectionUtils.isEmpty(users)){
                member.setContractContacts(contractContactMapper.selectList(Wrappers.<AssetsContractContact>lambdaQuery()
                        .in(AssetsContractContact::getId,
                                users.stream().map(AssetsSupplierContractUser::getUser_id).collect(Collectors.toList()))));
            }

            for (int i = 0 ; i< member.getContractContacts().size() ; i++){
                if (null != member.getContractContacts().get(i).getDept_id()){
                     SysDepartment sysDepartment = sysDepartmentService.selectById(member.getContractContacts().get(i).getDept_id());
                     if (null != sysDepartment){
                         member.getContractContacts().get(i).setDeptName(sysDepartment.getName());
                     }
                }
            }

            //得到关联配置项列表
            List<AssetsSupplierContractMap> maps = assetsSupplierContractMapMapper.
                    selectList(Wrappers.<AssetsSupplierContractMap>lambdaQuery()
                            .eq(AssetsSupplierContractMap::getSupplier_id, member.getId())
                            .eq(AssetsSupplierContractMap::getIs_del, String.valueOf(0))
                    );
            if(!CollectionUtils.isEmpty(maps)){
                member.setAssetsManages(assetsManageMapper.selectList(Wrappers.<AssetsManage>lambdaQuery()
                        .in(AssetsManage::getId,
                                maps.stream().map(AssetsSupplierContractMap::getAssets_id).collect(Collectors.toList()))));
            }
            for (int i = 0 ; i < member.getAssetsManages().size() ; i++){
                AssetsManage assetsManage = member.getAssetsManages().get(i);
                // 配置项类型 assetsTypeName
                if (Base.notEmpty(assetsManage.getAssets_type_id())) {
                    AssetsType assetsType = assetsTypeService.selectById(assetsManage.getAssets_type_id());
                    assetsManage.setAssetsTypeName(assetsType.getName());
                }
                // 配置项子类 childAssetName
                if (Base.notEmpty(assetsManage.getChild_asset())) {
                    AssetsChildren assetsChildren = assetsChildrenService.selectById(assetsManage.getChild_asset());
                    assetsManage.setChildAssetName(assetsChildren.getName());
                }
                // 部门名称 dept_name
                if (Base.notEmpty(assetsManage.getDept_id())) {
                    SysDepartment department = sysDepartmentService.selectById(assetsManage.getDept_id());
                    assetsManage.setDeptName(department.getName());
                    assetsManage.setDept_name(department.getName());
                }

                //购置公司 purchaseCompanyName
                if (Base.notEmpty(assetsManage.getPurchase_company())) {
                    AssetsCompany assetsCompany = assetsCompanyService.selectById(assetsManage.getPurchase_company());
                    assetsManage.setPurchaseCompanyName(assetsCompany.getCompany_name());
                }
                //资产状态 assetsStatusName
                if (Base.notEmpty(assetsManage.getAssets_status())) {
                    AssetsStatus assetsStatus = assetsStatusService.selectById(assetsManage.getAssets_status());
                    assetsManage.setAssetsStatusName(assetsStatus.getStatus_name());
                }
            }

        }

        PageInfo<AssetsSupplierContractVo> page = new PageInfo<>(assetsSupplierContractVos);

        return page;
    }

    public List<AssetsSupplierContractVo> getList(AssetsSupplierContract beanParam) {

        List<AssetsSupplierContractVo> assetsSupplierContractVos = assetsSupplierContractMapper.toList(beanParam);


        for(AssetsSupplierContractVo member : assetsSupplierContractVos){
            //得到联系人关联表
            List<AssetsSupplierContractUser> users = assetsSupplierContractUserMapper.selectList(Wrappers.<AssetsSupplierContractUser>lambdaQuery()
                    .eq(AssetsSupplierContractUser::getSupplier_id, member.getId())
                    .eq(AssetsSupplierContractUser::getIs_del, String.valueOf(0))
            );
            if(!CollectionUtils.isEmpty(users)){
                member.setContractContacts(contractContactMapper.selectList(Wrappers.<AssetsContractContact>lambdaQuery()
                        .in(AssetsContractContact::getId,
                                users.stream().map(AssetsSupplierContractUser::getUser_id).collect(Collectors.toList()))));
            }

            //得到关联配置项列表
            List<AssetsSupplierContractMap> maps = assetsSupplierContractMapMapper.
                    selectList(Wrappers.<AssetsSupplierContractMap>lambdaQuery()
                            .eq(AssetsSupplierContractMap::getSupplier_id, member.getId())
                            .eq(AssetsSupplierContractMap::getIs_del, String.valueOf(0))
                    );
            if(!CollectionUtils.isEmpty(maps)){
                member.setAssetsManages(assetsManageMapper.selectList(Wrappers.<AssetsManage>lambdaQuery()
                        .in(AssetsManage::getId,
                                maps.stream().map(AssetsSupplierContractMap::getAssets_id).collect(Collectors.toList()))));
            }

        }

        return assetsSupplierContractVos;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsSupplierContract> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsSupplierContract assetsSupplierContract = selectOne(queryWrapper);
        return ResultInfo.success(assetsSupplierContract);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsSupplierContract(AssetsSupplierContractVo bean, List<MultipartFile> files) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);

        if(CollectionUtils.isEmpty(bean.getContractContacts())){
            return ResultInfo.success();
        }
        for (AssetsContractContact member : bean.getContractContacts()){
            AssetsSupplierContractUser supplierContractUser = new AssetsSupplierContractUser();
            supplierContractUser.setIs_del(String.valueOf(0));
            supplierContractUser.setCreate_time(new Date());
            supplierContractUser.setSupplier_id(bean.getId());
            supplierContractUser.setUser_id(member.getId());
            assetsSupplierContractUserMapper.insert(supplierContractUser);
        }

        //配置项关联
        if(CollectionUtils.isEmpty(bean.getAssetsManages())){
            return ResultInfo.success();
        }
        for (AssetsManage member : bean.getAssetsManages()){
            AssetsSupplierContractMap customerContractMap = new AssetsSupplierContractMap();
            customerContractMap.setSupplier_id(bean.getId());
            customerContractMap.setIs_del(String.valueOf(0));
            customerContractMap.setCreate_time(new Date());
            customerContractMap.setCreate_user(sysUserService.getUser().getId());
            customerContractMap.setAssets_id(member.getId());
            assetsSupplierContractMapMapper.insert(customerContractMap);
        }


        if(!CollectionUtils.isEmpty(files)){
            for(MultipartFile file : files){
                int id = fileManageService.fileUpload(file);
                AssetsSupplierContractFile assetsSupplierContractFile = new AssetsSupplierContractFile();
                assetsSupplierContractFile.setSupplier_id(bean.getId());
                assetsSupplierContractFile.setFile_id(id);
                assetsSupplierContractFile.setIs_del(GlobalData.ISDEL_NO);
                assetsSupplierContractFile.setCreate_time(new Date());
                assetsSupplierContractFile.setCreate_user(sysUserService.getUser().getId());
                assetsSupplierContractFileMapper.insert(assetsSupplierContractFile);
            }
        }
        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editAssetsSupplierContract(AssetsSupplierContractVo bean,List<MultipartFile> files) {

        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);

        //联系人关联
        assetsSupplierContractUserMapper.update(null, Wrappers.<AssetsSupplierContractUser>lambdaUpdate()
                .set(AssetsSupplierContractUser::getIs_del, String.valueOf(1))
                .eq(AssetsSupplierContractUser::getSupplier_id, bean.getId()));
        if(CollectionUtils.isEmpty(bean.getContractContacts())){
            return ResultInfo.success();
        }
        for (AssetsContractContact member : bean.getContractContacts()){
            AssetsSupplierContractUser supplierContractUser = new AssetsSupplierContractUser();
            supplierContractUser.setIs_del(String.valueOf(0));
            supplierContractUser.setCreate_time(new Date());
            supplierContractUser.setSupplier_id(bean.getId());
            supplierContractUser.setUser_id(member.getId());
            assetsSupplierContractUserMapper.insert(supplierContractUser);
        }


        //配置项关联处理
        assetsSupplierContractMapMapper.update(null, Wrappers.<AssetsSupplierContractMap>lambdaUpdate()
                .set(AssetsSupplierContractMap::getIs_del, String.valueOf(1))
                .eq(AssetsSupplierContractMap::getSupplier_id, bean.getId()));
        if(CollectionUtils.isEmpty(bean.getAssetsManages())){
            return ResultInfo.success();
        }
        for (AssetsManage member : bean.getAssetsManages()){
            AssetsSupplierContractMap customerContractMap = new AssetsSupplierContractMap();
            customerContractMap.setSupplier_id(bean.getId());
            customerContractMap.setIs_del(String.valueOf(0));
            customerContractMap.setCreate_time(new Date());
            customerContractMap.setCreate_user(sysUserService.getUser().getId());
            customerContractMap.setAssets_id(member.getId());
            assetsSupplierContractMapMapper.insert(customerContractMap);
        }

        String uploadedFiles = bean.getUploadedFiles();
        List<FileManage> fileManages = assetsSupplierContractFileMapper.getFileManage(bean.getId());
        List<String> original = fileManages.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
        if (Base.notEmpty(uploadedFiles)) {
            List<String> ids = Arrays.asList(uploadedFiles.split(","));
            original.removeAll(ids);
        }
        if (!CollectionUtils.isEmpty(original)) {
            LambdaUpdateWrapper<AssetsSupplierContractFile> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(AssetsSupplierContractFile::getIs_del, GlobalData.ISDEL_YES)
                    .eq(AssetsSupplierContractFile::getSupplier_id, bean.getId())
                    .in(AssetsSupplierContractFile::getFile_id, original);
            assetsSupplierContractFileMapper.update(null, wrapper);
        }

        if(!CollectionUtils.isEmpty(files)){
            for(MultipartFile file : files){
                int id = fileManageService.fileUpload(file);
                AssetsSupplierContractFile assetsSupplierContractFile = new AssetsSupplierContractFile();
                assetsSupplierContractFile.setSupplier_id(bean.getId());
                assetsSupplierContractFile.setFile_id(id);
                assetsSupplierContractFile.setIs_del(GlobalData.ISDEL_NO);
                assetsSupplierContractFile.setCreate_time(new Date());
                assetsSupplierContractFile.setCreate_user(sysUserService.getUser().getId());
                assetsSupplierContractFileMapper.insert(assetsSupplierContractFile);
            }
        }


        return ResultInfo.success();



    }


    @Transactional
    public ResultInfo removeById(Integer id) {
        update(null, Wrappers.<AssetsSupplierContract>lambdaUpdate()
                .set(AssetsSupplierContract::getIs_del, String.valueOf(1))
                .set(AssetsSupplierContract::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsSupplierContract::getUpdate_time, new Date())
                .eq(AssetsSupplierContract::getId, id));
        assetsSupplierContractMapMapper.update(null, Wrappers.<AssetsSupplierContractMap>lambdaUpdate()
                .set(AssetsSupplierContractMap::getIs_del, String.valueOf(1))
                .eq(AssetsSupplierContractMap::getSupplier_id, id));
        assetsSupplierContractUserMapper.update(null, Wrappers.<AssetsSupplierContractUser>lambdaUpdate()
                .set(AssetsSupplierContractUser::getIs_del, String.valueOf(1))
                .eq(AssetsSupplierContractUser::getSupplier_id, id));
        return ResultInfo.success();
    }
}
