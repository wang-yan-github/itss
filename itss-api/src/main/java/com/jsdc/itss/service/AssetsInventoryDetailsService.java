package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsInventoryDetailsDao;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.mapper.*;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.DataUtils;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.AssetsInventoryDetailsVo;
import com.jsdc.itss.vo.AssetsInventoryVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.math.NumberUtils;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Service
@Transactional
public class AssetsInventoryDetailsService extends BaseService<AssetsInventoryDetailsDao, AssetsInventoryDetails> {

    @Value("${file.upload-path}")
    private String uploadPath;
    @Autowired
    private AssetsInventoryDetailsMapper assetsInventoryDetailsMapper;
    @Autowired
    private AssetsInventoryDetailsDao assetsInventoryDetailsDao;
    @Autowired
    private AssetsInventoryConfigService assetsInventoryConfigService;
    @Autowired
    private AssetsManageService assetsManageService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QuestionManageService questionManageService;

    @Autowired
    private AssetsManageMapper assetsManageMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private AssetsChildrenMapper assetsChildrenMapper;
    @Autowired
    private AssetsEnclosureService assetsEnclosureService;

    @Autowired
    private AssetsInventoryMapper assetsInventoryMapper;

    @Autowired
    private AssetsTypeMapper assetsTypeMapper;

    @Autowired
    private AssetsStatusMapper assetsStatusMapper;

    @Autowired
    private AssetsCompanyMapper assetsCompanyMapper;


    public PageInfo<AssetsInventoryDetailsVo> toList(Integer pageIndex, Integer pageSize, AssetsInventoryDetailsVo beanParam) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<AssetsInventoryDetailsVo> assetsInventoryDetailsVos = assetsInventoryDetailsMapper.toList(beanParam);
        return new PageInfo<>(assetsInventoryDetailsVos);
    }


    public AssetsInventoryDetailsVo getOne (Integer id){
        AssetsInventoryDetailsVo assetsInventoryDetailsVo = assetsInventoryDetailsMapper.getOne(id);
        return assetsInventoryDetailsVo;
    }

    private List<AssetsInventoryDetailsVo> getInventoryDetailsList(AssetsInventoryDetailsVo beanParam) {
        List<AssetsInventoryDetails> list = assetsInventoryDetailsMapper.selectList(Wrappers.<AssetsInventoryDetails>query().lambda()
                .eq(null != beanParam.getId(), AssetsInventoryDetails::getId, beanParam.getId())
                .eq(null != beanParam.getInventory_id(), AssetsInventoryDetails::getInventory_id, beanParam.getInventory_id())
                .eq(null != beanParam.getStatus(), AssetsInventoryDetails::getStatus, beanParam.getStatus())
                .eq(null != beanParam.getHandle_status(), AssetsInventoryDetails::getHandle_status, beanParam.getHandle_status())
                .eq(null != beanParam.getSuc_id(), AssetsInventoryDetails::getConfirm_user, beanParam.getSuc_id())
                .eq(null != beanParam.getSuh_id(), AssetsInventoryDetails::getHandle_user, beanParam.getSuh_id())
        );
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }

        Map<Integer, SysUser> userMap = getUserNameMap();
        List<AssetsInventoryDetailsVo> newList = new ArrayList<>();
        for (AssetsInventoryDetails temp : list){
            AssetsInventoryDetailsVo vo = new AssetsInventoryDetailsVo();
            vo.setId(temp.getId());
            vo.setAssets_id(temp.getAssets_id());
            vo.setHandle_status(temp.getHandle_status());
            vo.setStatus(temp.getStatus());
            vo.setHandle_result(temp.getHandle_result());
            vo.setConfirm_remark(temp.getConfirm_remark());
            vo.setConfirm_user(temp.getConfirm_user());
            vo.setConfirm_time(temp.getConfirm_time());
            vo.setHandle_user(temp.getHandle_user());
            vo.setCreate_time(temp.getCreate_time());
            vo.setUpdate_time(temp.getUpdate_time());
            vo.setCreate_user(temp.getCreate_user());
            vo.setUpdate_user(temp.getUpdate_user());
            vo.setInventory_id(temp.getInventory_id());

            AssetsInventory assetsInventory = assetsInventoryMapper.selectById(temp.getInventory_id());
            if(assetsInventory != null && null != assetsInventory.getName()){
                vo.setAi_name(assetsInventory.getName());
            }

            AssetsManage assetsManage = assetsManageMapper.selectById(temp.getAssets_id());
            if(assetsManage != null){
                vo.setAm_id(assetsManage.getId());
                vo.setAm_assets_number(assetsManage.getAssets_number());
                vo.setAm_assets_name(assetsManage.getAssets_name());
                vo.setAm_it_status(assetsManage.getIt_status() + "");
                vo.setCpu(assetsManage.getCpu());
                vo.setMemory(assetsManage.getMemory());
                vo.setHard_disk(assetsManage.getHard_disk());
                vo.setPurpose(assetsManage.getPurpose());
                vo.setFinance_number(assetsManage.getFinance_number());

                if(!CollectionUtils.isEmpty(userMap)){
                    SysUser user1 = userMap.get(assetsManage.getReceiver());
                    if(user1 != null){
                        vo.setSu_name(user1.getName());
                        vo.setSu_id(user1.getId());
                    }
                    SysUser user2 = userMap.get(vo.getConfirm_user());
                    if(user2 != null){
                        vo.setSuc_name(user2.getName());
                        vo.setSuc_id(user2.getId());
                    }
                    SysUser user3 = userMap.get(vo.getHandle_user());
                    if(user3 != null){
                        vo.setSuh_name(user3.getName());
                        vo.setSuh_id(user3.getId());
                    }

                    if(!StringUtils.isBlank(assetsManage.getPerson_liable())){
                        SysUser user5 = userMap.get(Integer.valueOf(assetsManage.getPerson_liable()));
                        if(user5 != null){
                            vo.setPerson_liable(user5.getName());
                        }
                    }
                }

                AssetsChildren assetsChildren = assetsChildrenMapper.selectById(assetsManage.getChild_asset());
                if(assetsChildren != null){
                    vo.setAc_name(assetsChildren.getName());
                    AssetsType assetsType = assetsTypeMapper.selectById(assetsChildren.getAssets_type_id());
                    if(assetsType != null){
                        vo.setAty_name(assetsType.getName());
                    }
                }
                SysDepartment dept = sysDepartmentMapper.selectById(assetsManage.getDept_id());
                if(dept != null){
                    vo.setSd_name(dept.getName());
                    vo.setSd_id(dept.getId());
                }

                AssetsStatus assetsStatus = assetsStatusMapper.selectById(assetsManage.getAssets_status());
                if(assetsStatus != null){
                    vo.setAst_status_name(assetsStatus.getStatus_name());
                }

                AssetsCompany company = assetsCompanyMapper.selectById(assetsManage.getPurchase_company());
                if(company != null){
                    vo.setPurchase_company(company.getCompany_name());
                }

            }
            newList.add(vo);
        }

        return newList;
    }

    public ResultInfo getInventoryDetails(AssetsInventoryDetailsVo beanParam) {

//        AssetsInventoryDetailsVo assetsInventoryDetails = assetsInventoryDetailsMapper.getInventoryDetails(beanParam);
        List<AssetsInventoryDetailsVo> list = getInventoryDetailsList(beanParam);
        if(CollectionUtils.isEmpty(list)){
            return ResultInfo.error("查询失败");
        }
        AssetsInventoryDetailsVo assetsInventoryDetails = list.get(0);

        List<FileManage> fileManages = assetsEnclosureService.getFileManage(assetsInventoryDetails.getAssets_id());
        if(null != fileManages){//附件
            List<FileManage> assetsEnclosures = new ArrayList<>();//附件
            List<FileManage> assetsPhotos =  new ArrayList<>();//图片
            for (FileManage fm: fileManages){
                String fileType = StringUtils.trimToEmpty(fm.getFile_type());
                if("png".equalsIgnoreCase(fileType) || "jpg".equalsIgnoreCase(fileType)){
                    assetsPhotos.add(fm);
                }else{
                    assetsEnclosures.add(fm);
                }
            }
            if(assetsEnclosures.size() > 0){
                assetsEnclosures.forEach(x -> {
                    String path = uploadPath + File.separator + x.getFile_name();
                    File file = new File(path);
                    String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
                    x.setBase64(base64);
                });
                assetsInventoryDetails.setAssetsEnclosures(assetsEnclosures);
            }
            if(assetsPhotos.size() > 0) {
                assetsPhotos.forEach(x -> {
                    String path = uploadPath + File.separator + x.getFile_name();
                    File file = new File(path);
                    String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
                    x.setBase64(base64);
                });
                assetsInventoryDetails.setAssetsPhotos(assetsPhotos);
            }

        }
        return ResultInfo.success(assetsInventoryDetails);
    }

    /**
     * 生成盘点资交数据
     *
     * @param bean
     * @return
     */
    protected List<AssetsInventoryDetails> getInventory(AssetsInventoryConfig bean) {
        // 盘点期与盘点配置合并
        AssetsInventoryVo vo = assetsInventoryConfigService.getAssetsInventory(bean);
        List<AssetsManage> amList = assetsManageService.selectList(Wrappers.<AssetsManage>lambdaQuery()
                .eq(AssetsManage::getAssets_type_id, bean.getAssets_type_id())
                .eq(AssetsManage::getChild_asset, bean.getAssets_children_id())
                .eq(AssetsManage::getIs_del, "0")
//                .eq(AssetsManage::getIs_inventory, "1")
                .eq(AssetsManage::getDept_id, bean.getDept_id())
        );
        List<AssetsInventoryDetails> list = new ArrayList<>();
        for (AssetsManage am : amList) {
            // 先改是否生成资产盘点的状态后添加资产盘点
            am.setIs_inventory(0);
            assetsManageService.updateById(am);

            AssetsInventoryDetails aid = new AssetsInventoryDetails();
            aid.setInventory_id(vo.getInventory_id());
            aid.setAssets_id(am.getId());
            list.add(aid);
        }
        return list;
    }

    public ResultInfo addAssetsInventoryDetails(AssetsInventoryConfig assetsInventoryConfig) {
        // 添加至盘点资产配置
        assetsInventoryConfigService.addAssetsInventoryConfig(assetsInventoryConfig);
        List<AssetsInventoryDetails> detailList = getInventory(assetsInventoryConfig);
        for (AssetsInventoryDetails d : detailList) {
            addAssetsInventoryDetail(d);
        }

        return ResultInfo.success();

    }

    /**
     * 添加
     */
    public ResultInfo addAssetsInventoryDetail(AssetsInventoryDetails bean) {
        // 默认处理状态
        bean.setHandle_status(0);
        // 默认状态未确认
        bean.setStatus(0);
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();
    }

    /**
     * 编辑
     */
    public ResultInfo editAssetsInventoryDetails(AssetsInventoryDetails bean) {
        if(null != bean && null != bean.getId() && bean.getId().intValue() >0 ){
            AssetsInventoryDetails dbBean = selectById(bean.getId());
            dbBean.setConfirm_remark(bean.getConfirm_remark());
            dbBean.setHandle_status(1);
            dbBean.setUpdate_user(sysUserService.getUser().getId());
            // 修改时间
            dbBean.setUpdate_time(new Date());
            updateById(dbBean);
            return ResultInfo.success();
        }
        // 修改者

        return ResultInfo.success();
    }

    /**
     * excel 导出
     *
     * @param beanParam
     * @param response
     */
    public ResultInfo exportExcel(AssetsInventoryDetailsVo beanParam, HttpServletResponse response) {
        List<AssetsInventoryDetailsVo> assetsInventoryDetailsVos = assetsInventoryDetailsMapper.toList(beanParam);

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("ai_name", "所属盘点期");
        writer.addHeaderAlias("aty_name", "配置项类型");
        writer.addHeaderAlias("am_assets_number", "资产编号");
        writer.addHeaderAlias("aty_name", "配置项类型");
        writer.addHeaderAlias("ac_name", "配置子类");
        writer.addHeaderAlias("am_assets_name", "资产名称");
        writer.addHeaderAlias("sd_name", "部门");
        writer.addHeaderAlias("su_name", "领用人");
        writer.addHeaderAlias("suc_name", "确认人");
        writer.addHeaderAlias("confirm_time", "确认时间");
        writer.addHeaderAlias("confirm_remark", "确认备注");
        writer.addHeaderAlias("suh_name", "处理人");
        writer.addHeaderAlias("handle_time", "处理时间");
        writer.addHeaderAlias("handle_result", "处理结果");
        writer.addHeaderAlias("id", "编号");

        writer.setOnlyAlias(true);
        writer.write(assetsInventoryDetailsVos, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition",
                    "attachment;filename=" + URLEncoder.encode(
                            String.format("%s%s.xls", DataType.assets_inventory_excel, DataUtils.getToday()), "UTF-8"));
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
     * 根据id查看详情
     * zdq
     */
    public ResultInfo getById(Integer id) {
        AssetsInventoryDetails details = selectById(id);
        if(null == details){
            return ResultInfo.error("非法入参");
        }
        //得到配置项信息
        AssetsManage assetsManage = assetsManageMapper.selectById(details.getAssets_id());
        //得到配置项子类信息
        AssetsChildren children = assetsChildrenMapper.selectOne(Wrappers.<AssetsChildren>lambdaQuery()
                .eq(AssetsChildren::getAssets_type_id, details.getAssets_id())
                .eq(AssetsChildren::getIs_del, String.valueOf(0)).last(" limit 1")
        );
        Map<Integer, SysDepartment> deptMap = getDeptMap();
        Map<Integer, SysUser> userMap = getUserNameMap();
        AssetsInventoryDetailsVo bean = AssetsInventoryDetailsVo.builder().build();

        bean.setAm_id(id);
        if(null != children){
            bean.setAc_id(children.getId());
            bean.setAc_name(children.getName());
        }
        if(null == assetsManage){
            return ResultInfo.success(bean);
        }
        bean.setAm_assets_name(assetsManage.getAssets_number());
        bean.setAty_id(assetsManage.getId());
        bean.setAty_name(assetsManage.getAssets_name());
        bean.setSd_id(assetsManage.getDept_id());
        if(!CollectionUtils.isEmpty(deptMap) && null != assetsManage.getDept_id()){
            bean.setSd_name(deptMap.get(assetsManage.getDept_id()).getName());
        }
        bean.setPerson_liable(assetsManage.getPerson_liable());
        bean.setSu_id(assetsManage.getReceiver());
        if(!CollectionUtils.isEmpty(userMap) && null != assetsManage.getReceiver()){
            bean.setSu_name(userMap.get(assetsManage.getReceiver()).getName());
        }
        bean.setPurchase_date(assetsManage.getPurchase_date());
        bean.setFinance_number(assetsManage.getFinance_number());
        bean.setPurchase_company(assetsManage.getPurchase_company() + "");
        bean.setAst_status_name(assetsManage.getAssets_status() + "");
        bean.setAm_it_status(assetsManage.getIt_status() + "");
        bean.setCpu(assetsManage.getCpu());
        bean.setMemory(assetsManage.getMemory());
        bean.setHard_disk(assetsManage.getHard_disk());
        bean.setPurpose(assetsManage.getPurpose());
        bean.setSn(assetsManage.getSn());

        return ResultInfo.success(bean  );
    }

    /**
     * 得到用户的键值对Map<id,SysUser>
     */
    private Map<Integer, SysUser> getUserNameMap() {
        List<SysUser> users = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIs_del, "0"));
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity(), (key1, key2) -> key2));
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
     * 批量确认方法
     * zdq
     */
    public ResultInfo editBatchDetails(AssetsInventoryDetailsVo assetsInventoryDetails) {
        Integer id = assetsInventoryDetails.getId();
        String des = assetsInventoryDetails.getConfirm_remark();
        List<Integer> ids = assetsInventoryDetails.getIds();
        if(CollectionUtils.isEmpty(ids)){
            return ResultInfo.error("非法入参");
        }
        int num = 0;
        for( Integer member : ids){
            AssetsInventoryDetails bean = assetsInventoryDetailsMapper.selectById(member);
            if(null == bean){
                continue;
            }
            bean.setConfirm_remark(des);
            bean.setUpdate_time(new Date());
            bean.setUpdate_user(sysUserService.getUser().getId());
            num += assetsInventoryDetailsMapper.updateById(bean);
        }
        if(num < 1){
            return ResultInfo.error("保存失败");
        }
        return ResultInfo.success();
    }

    /**
     * 删除
     * @param assetsInventoryDetails
     * @return
     */
    public ResultInfo doDeleteInventoryDetails(AssetsInventoryDetails assetsInventoryDetails) {
        AssetsInventoryDetails dbBean = selectById(assetsInventoryDetails.getId());
        dbBean.setUpdate_user(sysUserService.getUser().getId());
        dbBean.setIs_del("1");
        // 修改时间
        dbBean.setUpdate_time(new Date());
        updateById(dbBean);
        return ResultInfo.success();
    }

    /**
     * 盘点记录处理
     * @param assetsInventoryDetails
     * @return
     */
    public ResultInfo toDeal(AssetsInventoryDetails assetsInventoryDetails) {
        AssetsInventoryDetails dbBean = selectById(assetsInventoryDetails.getId());
        dbBean.setUpdate_user(null == assetsInventoryDetails.getUserId() ? sysUserService.getUser().getId() : assetsInventoryDetails.getUserId() );
        dbBean.setHandle_result(assetsInventoryDetails.getHandle_result());//处理结果
        dbBean.setHandle_user(null == assetsInventoryDetails.getUserId() ? sysUserService.getUser().getId() : assetsInventoryDetails.getUserId() );// 处理人
        dbBean.setHandle_time(new Date());
        // 修改时间
        dbBean.setUpdate_time(new Date());
        updateById(dbBean);
        return ResultInfo.success();
    }
    /**
     * 盘点记录确认
     * @param assetsInventoryDetails
     * @return
     */
    public ResultInfo toConfirm(AssetsInventoryDetails assetsInventoryDetails) {
        AssetsInventoryDetails dbBean = selectById(assetsInventoryDetails.getId());
        Integer currentUserId = null;
        if(null == assetsInventoryDetails.getUserId()){
            currentUserId = sysUserService.getUser().getId();
        }else {
            currentUserId = assetsInventoryDetails.getUserId();
        }

        dbBean.setUpdate_user(currentUserId);
        dbBean.setConfirm_user(currentUserId);
        dbBean.setConfirm_remark(assetsInventoryDetails.getConfirm_remark());
        dbBean.setConfirm_time(new Date());
        dbBean.setStatus(assetsInventoryDetails.getStatus());
        // 修改时间
        dbBean.setUpdate_time(new Date());
        updateById(dbBean);
        return ResultInfo.success();
    }

    public void delBatch(String ids) {
        String[] strArr = ids.split(",");
        for (int i = 0; i < strArr.length; i++) {
            AssetsInventoryDetails dbBean = selectById(NumberUtils.toInt(strArr[i]));
            dbBean.setUpdate_user(sysUserService.getUser().getId());
            dbBean.setIs_del("1");
            // 修改时间
            dbBean.setUpdate_time(new Date());
            updateById(dbBean);

            //得到配置项
            AssetsManage assetsManage = assetsManageMapper.selectById(dbBean.getAssets_id());
            if(null == assetsManage){
                continue;
            }
            //删除盘点期config
            assetsInventoryConfigService.delete(Wrappers.<AssetsInventoryConfig>lambdaQuery()
                    .eq(AssetsInventoryConfig::getInventory_id, dbBean.getInventory_id())
                    .eq(AssetsInventoryConfig::getAssets_type_id, assetsManage.getAssets_type_id())
                    .eq(AssetsInventoryConfig::getAssets_children_id, assetsManage.getChild_asset())
                    .eq(AssetsInventoryConfig::getDept_id, assetsManage.getDept_id())
            );
        }
    }

    /**
     * 批量确认
     * @param ids
     */
    public void confirmBatch(String ids) {
        String[] strArr = ids.split(",");
        for (int i = 0; i < strArr.length; i++) {
            AssetsInventoryDetails dbBean = selectById(NumberUtils.toInt(strArr[i]));
            dbBean.setUpdate_user(sysUserService.getUser().getId());
            dbBean.setConfirm_user(sysUserService.getUser().getId());
            dbBean.setConfirm_remark("");
            dbBean.setConfirm_time(new Date());
            dbBean.setStatus(1);
            // 修改时间
            dbBean.setUpdate_time(new Date());
            updateById(dbBean);
        }
    }
}


