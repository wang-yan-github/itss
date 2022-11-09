package com.jsdc.itss.service;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsClGroupDao;
import com.jsdc.itss.mapper.AssetsClGroupMapper;
import com.jsdc.itss.mapper.AssetsManageMapper;
import com.jsdc.itss.mapper.AssetsTypeMapper;
import com.jsdc.itss.mapper.SysUserConfigAuthorityMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.OverviewVo;
import com.jsdc.itss.vo.ResultInfo;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Service
@Transactional
public class AssetsClGroupService extends BaseService<AssetsClGroupDao, AssetsClGroup> {

    @Autowired
    private AssetsClGroupMapper assetsClGroupMapper;
    @Autowired
    private AssetsClGroupDao assetsClGroupDao;

    @Autowired
    private AssetsTypeMapper assetsTypeMapper;

    @Autowired
    private AssetsManageMapper assetsManageMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserConfigAuthorityMapper sysUserConfigAuthorityMapper;

    @Autowired
    private FileManageService fileManageService;

    @Value("${file.upload-path-app}")
    private String appUploadPath;


    public PageInfo<AssetsClGroup> toList(Integer pageIndex, Integer pageSize, AssetsClGroup beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsClGroup> assetsClGroupVos = assetsClGroupMapper.toList(beanParam);

        PageInfo<AssetsClGroup> page = new PageInfo<>(assetsClGroupVos);

        return page;
    }
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsClGroup> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsClGroup assetsClGroup = selectOne(queryWrapper);
        return ResultInfo.success(assetsClGroup);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsClGroup(AssetsClGroup bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        if(null == bean.getUserId()){
            bean.setCreate_user(sysUserService.getUser().getId());
        }else{
            bean.setCreate_user(bean.getUserId());
        }

        insert(bean);
        return ResultInfo.success();
    }

    /**
     *  编辑
     */
    public ResultInfo editAssetsClGroup(AssetsClGroup bean) {
        // 修改者
        if(null == bean.getUserId()){
            bean.setUpdate_user(sysUserService.getUser().getId());
        }else{
            bean.setUpdate_user(bean.getUserId());
        }
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }

    public List<OverviewVo> getOverviewList(OverviewVo bean) {
        return new ArrayList<>();
    }

    public ResultInfo getAllList(AssetsClGroup beanParam) {
        List< Map<String,String>> list =   new ArrayList<>();
        List<AssetsClGroup> assetsClGroupVos = assetsClGroupMapper.toList(beanParam);
        for (AssetsClGroup acg:assetsClGroupVos){
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("value",String.valueOf(acg.getId()));
            hashMap.put("label", acg.getGroup_name());
            list.add(hashMap);
        }
        System.out.println(list.toString());
        return ResultInfo.success(list);
    }

    /**
     * 概览
     * @param beanParam
     * @return
     */
    public List<AssetsClGroup> overviewList(AssetsClGroup beanParam) {
        Integer userId = null;
        if(null == beanParam.getUserId()){
            userId = sysUserService.getUser().getId();
        }else{
            userId = beanParam.getUserId();
        }

        QueryWrapper<SysUserConfigAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("user_id",userId);
        List<SysUserConfigAuthority> list1 =  sysUserConfigAuthorityMapper.selectList(queryWrapper);

        List<AssetsClGroup> list = assetsClGroupMapper.selectList(Wrappers.<AssetsClGroup>lambdaQuery().eq(AssetsClGroup::getIs_del,"0"));

        List<AssetsClGroup> newList = new ArrayList<>();
        for (AssetsClGroup assetsClGroup : list){

            List<AssetsType> assetsTypes =  assetsTypeMapper.selectList(Wrappers.<AssetsType>lambdaQuery().eq(AssetsType::getIs_del,"0")
                    .like(StringUtils.isNotBlank(beanParam.getKeyword()), AssetsType::getName, beanParam.getKeyword())
                    .eq(AssetsType::getGroup_id,assetsClGroup.getId()));
            if(CollectionUtils.isEmpty(assetsTypes)){
                continue;
            }
            for (AssetsType assetsType : assetsTypes){
                if (null != assetsType.getFile_id()){
                    FileManage fileManage = fileManageService.selectById(assetsType.getFile_id());
                    if (null != fileManage){
                        assetsType.setPic_path(appUploadPath + fileManage.getFile_name());
                    }
                }

                List<AssetsManage> assetsManage = assetsManageMapper.selectList(Wrappers.<AssetsManage>lambdaQuery().eq(AssetsManage::getIs_del,"0")
                        .eq(AssetsManage::getAssets_type_id,assetsType.getId()));
                List<AssetsManage> result = new ArrayList<>();
                if (!CollectionUtils.isEmpty(assetsManage)){
                    for (int i = 0 ; i< assetsManage.size() ; i++){
                        for (int j = 0 ; j<list1.size() ; j++){
                            if (assetsManage.get(i).getDept_id() == list1.get(j).getDepartment_id()){
                                result.add(assetsManage.get(i));
                            }
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(result)){
                    assetsType.setNum((long) result.size());
                }else {
                    assetsType.setNum((long)0);
                }
            }
            assetsClGroup.setAssetsTypeList(assetsTypes);
            newList.add(assetsClGroup);
        }
        return newList;
    }
}
