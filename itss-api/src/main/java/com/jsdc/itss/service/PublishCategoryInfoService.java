package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.PublishCategoryInfoDao;
import com.jsdc.itss.mapper.PublishCategoryInfoMapper;
import com.jsdc.itss.model.PublishCategoryApprove;
import com.jsdc.itss.model.PublishCategoryInfo;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.model.vo.PublishApproveInfo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("ALL")
public class PublishCategoryInfoService extends BaseService<PublishCategoryInfoDao, PublishCategoryInfo> {

    @Autowired
    private PublishCategoryInfoMapper publishCategoryInfoMapper;
    @Autowired
    private PublishCategoryInfoDao publishCategoryInfoDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PublishCategoryApproveService publishCategoryApproveService;

    /**
     * 发布类别管理展示数据
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageInfo<PublishCategoryInfo> toList(Integer pageIndex, Integer pageSize, PublishCategoryInfo publishCategoryInfo) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<PublishCategoryInfo> publishCategoryInfos = publishCategoryInfoMapper.toList(publishCategoryInfo);

        //评审人
        publishCategoryInfos.forEach(x -> {
            QueryWrapper<PublishCategoryApprove> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("publish_category_id", x.getId()).eq("is_del", GlobalData.ISDEL_NO);
            List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(queryWrapper);
            StringBuffer approverStr = new StringBuffer();
            publishCategoryApproves.forEach(a -> {
                SysUser sysUser = sysUserService.selectById(a.getApprove_user());
                if (Base.notEmpty(sysUser)) {
                    approverStr.append(sysUser.getName()).append(",");
                }
            });
            if (Base.notEmpty(approverStr)) {
                x.setApprover(StringUtils.isBlank(approverStr.toString())? "" :approverStr.deleteCharAt(approverStr.length() - 1).toString());
            }
        });
        PageInfo<PublishCategoryInfo> page = new PageInfo<>(publishCategoryInfos);
        return page;
    }


    /**
     * 获取所有在用发布类别
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<PublishCategoryInfo> getAllList(PublishCategoryInfo publishCategoryInfo) {
//        PageHelper.startPage(pageIndex, pageSize, "create_time desc");
        List<PublishCategoryInfo> publishCategoryInfos = publishCategoryInfoMapper.toList(publishCategoryInfo);

        //评审人
        publishCategoryInfos.forEach(x -> {
            QueryWrapper<PublishCategoryApprove> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("publish_category_id", x.getId()).eq("is_del", GlobalData.ISDEL_NO);
            List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(queryWrapper);
            StringBuffer approverStr = new StringBuffer();
            publishCategoryApproves.forEach(a -> {
                SysUser sysUser = sysUserService.selectById(a.getApprove_user());
                if (Base.notEmpty(sysUser)) {
                    approverStr.append(sysUser.getName()).append(",");
                }
            });
            if (Base.notEmpty(approverStr)) {
                x.setApprover(StringUtils.isBlank(approverStr.toString())? "" :approverStr.deleteCharAt(approverStr.length() - 1).toString());
            }
        });

        return publishCategoryInfos;
    }




    /**
    *发布类别下拉接口
    * Author wzn
    * Date 2022/10/31 10:51
    */
    public List<PublishCategoryInfo> getAllList() {
        PublishCategoryInfo publishCategoryInfo = new PublishCategoryInfo() ;
        publishCategoryInfo.setIs_use("1");
        List<PublishCategoryInfo> publishCategoryInfos = publishCategoryInfoMapper.toList(publishCategoryInfo);

        //评审人
        publishCategoryInfos.forEach(x -> {
            QueryWrapper<PublishCategoryApprove> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("publish_category_id", x.getId()).eq("is_del", GlobalData.ISDEL_NO);
            List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(queryWrapper);
            StringBuffer approverStr = new StringBuffer();
            publishCategoryApproves.forEach(a -> {
                SysUser sysUser = sysUserService.selectById(a.getApprove_user());
                if (Base.notEmpty(sysUser)) {
                    approverStr.append(sysUser.getName()).append(",");
                }
            });
            if (Base.notEmpty(approverStr)) {
                x.setApprover(StringUtils.isBlank(approverStr.toString())? "" :approverStr.deleteCharAt(approverStr.length() - 1).toString());
            }
        });
        return publishCategoryInfos;
    }



    /**
     * 添加发布类别
     *
     * @param publishCategoryInfo
     * @return
     */
    @Transactional
    public ResultInfo addPublishCategory(PublishCategoryInfo publishCategoryInfo) {
        List<PublishApproveInfo> publishApproveInfos = publishCategoryInfo.getPublishApproveInfos();
        // 删除状态
        publishCategoryInfo.setIs_del(GlobalData.ISDEL_NO);
        // 创建时间
        publishCategoryInfo.setCreate_time(new Date());
        // 创建者
        publishCategoryInfo.setCreate_user(sysUserService.getUser().getId());

        Long count = publishCategoryInfoMapper.selectCount(Wrappers.<PublishCategoryInfo>lambdaQuery().eq(PublishCategoryInfo::getName, publishCategoryInfo.getName()).eq(PublishCategoryInfo::getIs_del, GlobalData.ISDEL_NO));
        if(count > 0){
            return ResultInfo.error("名称已存在");
        }
        if (insert(publishCategoryInfo) > 0) {
            //添加发布类别评审表
            for (PublishApproveInfo publishApproveInfo : publishApproveInfos) {
                PublishCategoryApprove publishCategoryApprove = new PublishCategoryApprove();
                publishCategoryApprove.setPublish_category_id(publishCategoryInfo.getId());
                publishCategoryApprove.setApprove_user(publishApproveInfo.getId());
                publishCategoryApprove.setSort(publishApproveInfo.getSort());
                publishCategoryApproveService.addPublishCategoryApprove(publishCategoryApprove);
            }
            return ResultInfo.success("操作成功");
        } else {
            return ResultInfo.error("操作失败");
        }
    }

    /**
     * 编辑发布类别
     *
     * @param publishCategoryInfo
     * @return
     */
    @Transactional
    public ResultInfo editPublishCategory(PublishCategoryInfo publishCategoryInfo) {

        Long count = selectCount(Wrappers.<PublishCategoryInfo>lambdaQuery().eq(PublishCategoryInfo::getName, publishCategoryInfo.getName()).ne(PublishCategoryInfo::getId, publishCategoryInfo.getId()).eq(PublishCategoryInfo::getIs_del, GlobalData.ISDEL_NO));
        if(count > 0){
            return ResultInfo.error("名称已存在");
        }
        List<PublishApproveInfo> publishApproveInfos = new ArrayList<>();
        if (publishCategoryInfo.getIs_del() == null || publishCategoryInfo.getIs_del().equals(GlobalData.ISDEL_NO)) {
            publishApproveInfos = publishCategoryInfo.getPublishApproveInfos();
            //根据sort排序,判断非空
            publishApproveInfos = publishApproveInfos.stream().sorted((x, y) -> {
                if (x.getSort() == null) {
                    return 1;
                }
                if (y.getSort() == null) {
                    return -1;
                }
                return x.getSort() - y.getSort();
            }).collect(Collectors.toList());
            //评审人id
//            StringBuffer approverStr = new StringBuffer();
//            publishApproveInfos.forEach(x -> {
//                approverStr.append(x.getName()).append(",");
//            });
//            approverStr.deleteCharAt(approverStr.length() - 1);
//            publishCategoryInfo.setApprover(approverStr.toString());

        }
        // 修改者
        publishCategoryInfo.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        publishCategoryInfo.setUpdate_time(new Date());
        if (updateById(publishCategoryInfo) > 0) {
            // 逻辑删除之前的布类别评审数据
            UpdateWrapper<PublishCategoryApprove> wrapper = new UpdateWrapper<>();
            wrapper.lambda().set(PublishCategoryApprove::getIs_del, "1").
                    eq(PublishCategoryApprove::getPublish_category_id, publishCategoryInfo.getId());
            publishCategoryApproveService.update(null, wrapper);
            //编辑发布类别评审表
            for (PublishApproveInfo publishApproveInfo : publishApproveInfos) {
                if (publishApproveInfo.getCategory_approve_id() != null) {
                    //编辑
                    PublishCategoryApprove publishCategoryApprove = publishCategoryApproveService.selectById(publishApproveInfo.getCategory_approve_id());
                    publishCategoryApprove.setSort(publishApproveInfo.getSort());
                    publishCategoryApprove.setIs_del(GlobalData.ISDEL_NO);
                    publishCategoryApproveService.editPublishCategoryApprove(publishCategoryApprove);
                } else {
                    //新增
                    PublishCategoryApprove publishCategoryApprove = new PublishCategoryApprove();
                    publishCategoryApprove.setPublish_category_id(publishCategoryInfo.getId());
                    publishCategoryApprove.setApprove_user(publishApproveInfo.getId());
                    publishCategoryApprove.setSort(publishApproveInfo.getSort());
                    publishCategoryApproveService.addPublishCategoryApprove(publishCategoryApprove);
                }
            }
            return ResultInfo.success("操作成功");
        } else {
            return ResultInfo.error("操作失败");
        }
    }

    /**
     * 获取发布类别信息
     *
     * @param id
     * @return
     */
    public ResultInfo getPublishCategory(PublishCategoryInfo publishCategory) {
        PublishCategoryInfo publishCategoryInfo = this.selectById(publishCategory.getId());

        QueryWrapper<PublishCategoryApprove> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_category_id", publishCategory.getId()).eq("is_del", GlobalData.ISDEL_NO);
        List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(queryWrapper);
        List<PublishApproveInfo> publishApproveInfos = new ArrayList<>();
        for (PublishCategoryApprove publishCategoryApprove : publishCategoryApproves) {
            SysUser sysUser = sysUserService.selectById(publishCategoryApprove.getApprove_user());
            PublishApproveInfo publishApproveInfo = new PublishApproveInfo();
            publishApproveInfo.setUser_id(publishCategoryApprove.getApprove_user());
            publishApproveInfo.setCategory_approve_id(publishCategoryApprove.getId());
            publishApproveInfo.setUser_name(sysUser.getUser_name());
            publishApproveInfo.setName(sysUser.getName());
            publishApproveInfo.setStatus(Integer.valueOf(sysUser.getStatus()));
            publishApproveInfo.setTelephone(sysUser.getTelephone());
            publishApproveInfo.setUser_type(sysUser.getUser_type());
            publishApproveInfo.setSort(publishCategoryApprove.getSort());
            publishApproveInfos.add(publishApproveInfo);
        }

        SysUser user1 = sysUserService.selectById(publishCategoryInfo.getPublisher());
        publishCategoryInfo.setPublisherName(null == user1 ? "" : user1.getName());
        publishCategoryInfo.setPublishApproveInfos(publishApproveInfos);
        return ResultInfo.success(publishCategoryInfo);
    }

    public String getPublishCategoryString(PublishCategoryInfo publishCategory) {
        PublishCategoryInfo publishCategoryInfo = this.selectById(publishCategory.getId());

        QueryWrapper<PublishCategoryApprove> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("publish_category_id", publishCategory.getId()).eq("is_del", GlobalData.ISDEL_NO);
        List<PublishCategoryApprove> publishCategoryApproves = publishCategoryApproveService.selectList(queryWrapper);
        List<PublishApproveInfo> publishApproveInfos = new ArrayList<>();
        for (PublishCategoryApprove publishCategoryApprove : publishCategoryApproves) {
            SysUser sysUser = sysUserService.selectById(publishCategoryApprove.getApprove_user());
            PublishApproveInfo publishApproveInfo = new PublishApproveInfo();
            publishApproveInfo.setUser_id(publishCategoryApprove.getApprove_user());
            publishApproveInfo.setCategory_approve_id(publishCategoryApprove.getId());
            publishApproveInfo.setUser_name(sysUser.getUser_name());
            publishApproveInfo.setName(sysUser.getName());
            publishApproveInfo.setStatus(Integer.valueOf(sysUser.getStatus()));
            publishApproveInfo.setTelephone(sysUser.getTelephone());
            publishApproveInfo.setUser_type(sysUser.getUser_type());
            publishApproveInfo.setSort(publishCategoryApprove.getSort());
            publishApproveInfos.add(publishApproveInfo);
        }


String str = "" ;
                for(int i = 0 ;i<publishApproveInfos.size();i++){
                    str+= publishApproveInfos.get(i).getName() ;
                    if(i<publishApproveInfos.size()-1){
                        str+="," ;
                    }
                }


        return str;
    }


    /**
     * 获取发布类别评审信息
     *
     * @param publishCategoryInfo
     * @return
     */
    public ResultInfo getCategoryApproves(PublishCategoryInfo publishCategoryInfo) {
        List<PublishApproveInfo> categoryApproves = publishCategoryInfoMapper.getCategoryApproves(publishCategoryInfo.getId());
        return ResultInfo.success(categoryApproves);
    }
}
