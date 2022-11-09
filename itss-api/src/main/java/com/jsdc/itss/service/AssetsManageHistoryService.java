package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsManageHistoryDao;
import com.jsdc.itss.mapper.AssetsManageHistoryMapper;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.model.AssetsManageHistory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.Date;
import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author libin
 * @create 2022-03-16 11:42:47
 */
@Service
@Transactional
public class AssetsManageHistoryService extends BaseService<AssetsManageHistoryDao, AssetsManageHistory> {

    @Autowired
    private AssetsManageHistoryMapper assetsManageHistoryMapper;
    @Autowired
    private AssetsManageHistoryDao assetsManageHistoryDao;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<AssetsManageHistory> toList(Integer pageIndex, Integer pageSize, AssetsManageHistory beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<AssetsManageHistory> assetsManageHistoryVos = assetsManageHistoryMapper.toList(beanParam);

        PageInfo<AssetsManageHistory> page = new PageInfo<>(assetsManageHistoryVos);

        return page;
    }

    public List<AssetsManageHistory> getAppList( AssetsManageHistory beanParam) {
        List<AssetsManageHistory> assetsManageHistoryVos = assetsManageHistoryMapper.toList(beanParam);
        return assetsManageHistoryVos;
    }

    public ResultInfo getById(Integer id) {
        QueryWrapper<AssetsManageHistory> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        AssetsManageHistory assetsManageHistory = selectOne(queryWrapper);
        return ResultInfo.success(assetsManageHistory);
    }

    /**
     *  添加
     */
    public ResultInfo addAssetsManageHistory(AssetsManage bean) {
        return addAssetsManageHistory(bean,"新增");
    }

    public ResultInfo addAssetsManageHistory(AssetsManage bean,String type) {
        AssetsManageHistory amh =  new AssetsManageHistory();
        amh.setAssets_id(bean.getId());
        amh.setOption_type(type); //操作类型
        amh.setOption_userId(sysUserService.getUser().getId());//操作人
        amh.setOption_date(new Date());//操作时间
        amh.setAssets_number(bean.getAssets_number());                         //     资产编号
        amh.setAssets_type_id(bean.getAssets_type_id());                         //     配置项类型id
        amh.setChild_asset(bean.getChild_asset());                            //     配置子类
        amh.setAssets_name(bean.getAssets_name());                             //     资产名称
        amh.setDept_id(bean.getDept_id());                                //     部门
        amh.setReceiver(bean.getReceiver());                                //     领用人
        amh.setPurchase_company(bean.getPurchase_company());                       //     购置公司
        amh.setIs_secrecy(bean.getIs_secrecy());                             //     保密
        amh.setAssets_status(bean.getAssets_status());                          //     资产状态
        amh.setIt_status(bean.getIt_status());                              //     IT状态
        return addAssetsManageHistory(amh);
    }
    /**
     *  添加
     */
    public ResultInfo addAssetsManageHistory(AssetsManageHistory bean) {
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
     *  编辑
     */
    public ResultInfo editAssetsManageHistory(AssetsManageHistory bean) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
