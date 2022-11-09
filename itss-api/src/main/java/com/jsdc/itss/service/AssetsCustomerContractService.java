package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsCustomerContractDao;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.EventExpensesVo;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Service
@Transactional
public class AssetsCustomerContractService extends BaseService<AssetsCustomerContractDao, AssetsCustomerContract> {

    @Autowired
    private AssetsCustomerContractMapper assetsCustomerContractMapper;
    @Autowired
    private AssetsCustomerContractUserMapper customerContractUserMapper;
    @Autowired
    private AssetsCustomerContractMapMapper assetsCustomerContractMapMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SlaEventService slaEventService;
    @Autowired
    private AssetsManageService assetsManageService;
    @Autowired
    private AssetsContractContactService assetsContractContactService;
    @Autowired
    private SlaQuestionService slaQuestionService;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private AssetsCustomerContractFileService assetsCustomerContractFileService;
    @Autowired
    private EventCategoryMapper eventCategoryMapper;
    @Value("${file.upload-path}")
    private String uploadPath;

    public PageInfo<AssetsCustomerContract> toList(Integer pageIndex, Integer pageSize, AssetsCustomerContract beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        Map<Integer,String> eventExpenses = eventCategoryMapper.eventExpenses().stream().collect(Collectors.toMap(EventExpensesVo::getCustomer_contract_id,EventExpensesVo::getExpenses, (key1, key2) -> key2));
        List<AssetsCustomerContract> assetsCustomerContractVos = assetsCustomerContractMapper.toList(beanParam);
        for(AssetsCustomerContract member : assetsCustomerContractVos){
            member.setAmount_count(eventExpenses.get(member.getId()));
            //附件
            List<FileManage> fileManageList = new ArrayList<>();
            QueryWrapper<AssetsCustomerContractFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("contract_id",member.getId());
            queryWrapper.eq("is_del","0");
            //附件
            List<AssetsCustomerContractFile> fileManage1 = assetsCustomerContractFileService.selectList(queryWrapper);
            for (int i = 0 ;  i < fileManage1.size() ; i++){
                AssetsCustomerContractFile assetsCustomerContractFile = fileManage1.get(i);
                FileManage fileManage = fileManageService.selectById(assetsCustomerContractFile.getFile_id());
                String path = uploadPath + File.separator + fileManage.getFile_name();
                File file = new File(path);
                String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
                fileManage.setBase64(base64);
                fileManageList.add(fileManage);
            }
            member.setFileManageList(fileManageList);

            //得到联系人关联表
            List<AssetsCustomerContractUser> users = customerContractUserMapper.selectList(Wrappers.<AssetsCustomerContractUser>lambdaQuery()
                    .eq(AssetsCustomerContractUser::getContract_id, member.getId())
                    .eq(AssetsCustomerContractUser::getIs_del, String.valueOf(0))
            );
            if (!CollectionUtils.isEmpty(users)) {
                List<Integer> idList = users.stream().map(AssetsCustomerContractUser::getUser_id).collect(Collectors.toList());
                AssetsContractContact assetsContractContact = new AssetsContractContact();
                assetsContractContact.setIds(idList);
                member.setContractContacts(assetsContractContactService.getList(assetsContractContact));
            }
//            if(!CollectionUtils.isEmpty(users)){
//                member.setContractContacts(contractContactMapper.selectList(Wrappers.<AssetsContractContact>lambdaQuery()
//                        .in(AssetsContractContact::getId,
//                                users.stream().map(AssetsCustomerContractUser::getUser_id).collect(Collectors.toList()))));
//            }

            //得到关联配置项列表
            List<AssetsCustomerContractMap> maps = assetsCustomerContractMapMapper.selectList(Wrappers.<AssetsCustomerContractMap>lambdaQuery()
                    .eq(AssetsCustomerContractMap::getContract_id, member.getId())
                    .eq(AssetsCustomerContractMap::getIs_del, String.valueOf(0))
            );
            if (!CollectionUtils.isEmpty(maps)) {
                List<Integer> ids = maps.stream().map(AssetsCustomerContractMap::getAssets_id).collect(Collectors.toList());
                AssetsManage assetsManage2 = new AssetsManage();
                assetsManage2.setIds(ids);
                member.setAssetsManages(assetsManageService.getList(assetsManage2));
            }
//            if(!CollectionUtils.isEmpty(maps)){
//                member.setAssetsManages(assetsManageMapper.selectList(Wrappers.<AssetsManage>lambdaQuery()
//                        .in(AssetsManage::getId,
//                                maps.stream().map(AssetsCustomerContractMap::getAssets_id).collect(Collectors.toList()))));
//            }
            //事件SLA
            if (Base.notEmpty(member.getSla_id())) {
                member.setSla_event_name(slaEventService.selectById(member.getSla_id()).getSla_event_name());
            }
            //事件SLA
            if (Base.notEmpty(member.getSla_question_id())) {
                member.setSla_question_name(slaQuestionService.selectById(member.getSla_question_id()).getSla_question_name());
            }
        }

        PageInfo<AssetsCustomerContract> page = new PageInfo<>(assetsCustomerContractVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsCustomerContract> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsCustomerContract assetsCustomerContract = selectOne(queryWrapper);
        return ResultInfo.success(assetsCustomerContract);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsCustomerContract(AssetsCustomerContract bean,List<MultipartFile> files) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);

        //联系人关联
        if(!CollectionUtils.isEmpty(bean.getContractContacts())){
//            return ResultInfo.success();
            for (AssetsContractContact member : bean.getContractContacts()){
                AssetsCustomerContractUser customerContractUser = new AssetsCustomerContractUser();
                customerContractUser.setIs_del(String.valueOf(0));
                customerContractUser.setCreate_time(new Date());
                customerContractUser.setContract_id(bean.getId());
                customerContractUser.setUser_id(member.getId());
                customerContractUserMapper.insert(customerContractUser);
            }
        }

        //配置项关联
        if(!CollectionUtils.isEmpty(bean.getAssetsManages())){
//            return ResultInfo.success();
            for (AssetsManage member : bean.getAssetsManages()){
                AssetsCustomerContractMap customerContractMap = new AssetsCustomerContractMap();
                customerContractMap.setContract_id(bean.getId());
                customerContractMap.setIs_del(String.valueOf(0));
                customerContractMap.setCreate_time(new Date());
                customerContractMap.setCreate_user(sysUserService.getUser().getId());
                customerContractMap.setAssets_id(member.getId());
                assetsCustomerContractMapMapper.insert(customerContractMap);
            }
        }


        if(!CollectionUtils.isEmpty(files)){
            for(MultipartFile file : files){
                int id = fileManageService.fileUpload(file);
                AssetsCustomerContractFile assetsCustomerContractFile = new AssetsCustomerContractFile();
                assetsCustomerContractFile.setContract_id(bean.getId());
                assetsCustomerContractFile.setFile_id(id);
                assetsCustomerContractFile.setIs_del(GlobalData.ISDEL_NO);
                assetsCustomerContractFileService.insert(assetsCustomerContractFile);
            }
        }

        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editAssetsCustomerContract(AssetsCustomerContract bean,List<MultipartFile> files) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);

        //联系人关联
        customerContractUserMapper.update(null, Wrappers.<AssetsCustomerContractUser>lambdaUpdate()
                .set(AssetsCustomerContractUser::getIs_del, String.valueOf(1))
                .eq(AssetsCustomerContractUser::getContract_id, bean.getId()));
        if(!CollectionUtils.isEmpty(bean.getContractContacts())){
//            return ResultInfo.success();
            for (AssetsContractContact member : bean.getContractContacts()){
                AssetsCustomerContractUser customerContractUser = new AssetsCustomerContractUser();
                customerContractUser.setIs_del(String.valueOf(0));
                customerContractUser.setCreate_time(new Date());
                customerContractUser.setContract_id(bean.getId());
                customerContractUser.setUser_id(member.getId());
                customerContractUserMapper.insert(customerContractUser);
            }
        }


        //配置项关联处理
        assetsCustomerContractMapMapper.update(null, Wrappers.<AssetsCustomerContractMap>lambdaUpdate()
                .set(AssetsCustomerContractMap::getIs_del, String.valueOf(1))
                .eq(AssetsCustomerContractMap::getContract_id, bean.getId()));
        if(!CollectionUtils.isEmpty(bean.getAssetsManages())){
//            return ResultInfo.success();
            for (AssetsManage member : bean.getAssetsManages()){
                AssetsCustomerContractMap customerContractMap = new AssetsCustomerContractMap();
                customerContractMap.setContract_id(bean.getId());
                customerContractMap.setIs_del(String.valueOf(0));
                customerContractMap.setCreate_time(new Date());
                customerContractMap.setCreate_user(sysUserService.getUser().getId());
                customerContractMap.setAssets_id(member.getId());
                assetsCustomerContractMapMapper.insert(customerContractMap);
            }
        }




        UpdateWrapper<AssetsCustomerContractFile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del","1");
        updateWrapper.eq("contract_id",bean.getId());
        assetsCustomerContractFileService.update(null,updateWrapper);
        String uploadedFiles = bean.getUploadedFiles();
        if (StringUtils.isNotEmpty(uploadedFiles) ){
            String[] num = uploadedFiles.split(",");
            for (int i = 0 ; i< num.length ; i++){
                UpdateWrapper<AssetsCustomerContractFile> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper1.set("is_del","0");
                updateWrapper1.eq("contract_id",bean.getId());
                updateWrapper1.eq("file_id", Integer.parseInt(num[i]));
                assetsCustomerContractFileService.update(null,updateWrapper1);
            }
        }

        if(!CollectionUtils.isEmpty(files)){
            for(MultipartFile file : files){
                int id = fileManageService.fileUpload(file);
                AssetsCustomerContractFile assetsCustomerContractFile = new AssetsCustomerContractFile();
                assetsCustomerContractFile.setContract_id(bean.getId());
                assetsCustomerContractFile.setFile_id(id);
                assetsCustomerContractFile.setIs_del(GlobalData.ISDEL_NO);
                assetsCustomerContractFileService.insert(assetsCustomerContractFile);
            }
        }




        return ResultInfo.success();
    }

    /**
     * 删除数据,以及关联表数据
     */
    @Transactional
    public ResultInfo removeById(Integer id) {
        update(null, Wrappers.<AssetsCustomerContract>lambdaUpdate()
                .set(AssetsCustomerContract::getIs_del, String.valueOf(1))
                .set(AssetsCustomerContract::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsCustomerContract::getUpdate_time, new Date())
                .eq(AssetsCustomerContract::getId, id));
        assetsCustomerContractMapMapper.update(null, Wrappers.<AssetsCustomerContractMap>lambdaUpdate()
                .set(AssetsCustomerContractMap::getIs_del, String.valueOf(1))
                .eq(AssetsCustomerContractMap::getContract_id, id));
        customerContractUserMapper.update(null, Wrappers.<AssetsCustomerContractUser>lambdaUpdate()
                .set(AssetsCustomerContractUser::getIs_del, String.valueOf(1))
                .eq(AssetsCustomerContractUser::getContract_id, id));
        return ResultInfo.success();
    }
}
