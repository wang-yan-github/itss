package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsStatusDao;
import com.jsdc.itss.mapper.AssetsStatusMapper;
import com.jsdc.itss.model.AssetsStatus;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class AssetsStatusService extends BaseService<AssetsStatusDao, AssetsStatus> {

    @Autowired
    private AssetsStatusMapper assetsStatusMapper;
    @Autowired
    private AssetsStatusDao assetsStatusDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     *  资产状态展示数据
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageInfo<AssetsStatus> toList(Integer pageIndex, Integer pageSize, AssetsStatus assetsStatus) {
        PageHelper.startPage(pageIndex, pageSize);
        List<AssetsStatus> assetsStatuses = selectList(Wrappers.<AssetsStatus>lambdaQuery()
                .eq(AssetsStatus::getIs_del, String.valueOf(0))
                .eq(null != assetsStatus.getIs_enable(), AssetsStatus::getIs_enable, assetsStatus.getIs_enable())
                .like(StringUtils.isNotBlank(assetsStatus.getStatus_name()), AssetsStatus::getStatus_name, assetsStatus.getStatus_name()));
        PageInfo<AssetsStatus> page = new PageInfo<>(assetsStatuses);
        return page;
    }

    /**
     *  资产状态ID查询
     * @param id
     * @return
     */
    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsStatus> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsStatus assetsStatus = selectOne(queryWrapper);
        return ResultInfo.success(assetsStatus);
    }

    /**
     *  添加资产状态
     * @param assetsStatus
     * @return
     */
    public ResultInfo addAssetsStatus(AssetsStatus assetsStatus) {
        QueryWrapper<AssetsStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("status_name",assetsStatus.getStatus_name());
        Long count = assetsStatusMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        // 删除状态
        assetsStatus.setIs_del(String.valueOf(0));
        // 创建时间
        assetsStatus.setCreate_time(new Date());
        // 创建者
        assetsStatus.setCreate_user(sysUserService.getUser().getId());
        insert(assetsStatus);
        return ResultInfo.success();
    }

    /**
     *  编辑资产状态
     * @param assetsStatus
     * @return
     */
    public ResultInfo editAssetsStatus(AssetsStatus assetsStatus) {
        QueryWrapper<AssetsStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("status_name",assetsStatus.getStatus_name()).ne("id",assetsStatus.getId());
        Long count = assetsStatusMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        // 修改者
        assetsStatus.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        assetsStatus.setUpdate_time(new Date());
        updateById(assetsStatus);
        return ResultInfo.success();
    }

    /**
     * 查询所有配置状态
     * @return
     */
    public List<AssetsStatus> getAll(){
        LambdaQueryWrapper<AssetsStatus> wrapper = new LambdaQueryWrapper();
        wrapper.eq(AssetsStatus::getIs_del, GlobalData.ISDEL_NO);
        wrapper.eq(AssetsStatus::getIs_enable, GlobalData.IS_ENABLE_YES);
        return selectList(wrapper);
    }


}
