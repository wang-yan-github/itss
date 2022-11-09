package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.dao.AssetsItStatusDao;
import com.jsdc.itss.model.AssetsItStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AssetsItStatusService extends BaseService<AssetsItStatusDao, AssetsItStatus> {
    /**
     * @description 查询所有的配置it状态

     * @return java.util.List<com.jsdc.itss.model.AssetsItStatus>
     * @author wp
     * @date 2022/4/7
    */
    public List<AssetsItStatus> getAll(){
        LambdaQueryWrapper<AssetsItStatus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AssetsItStatus::getIs_del, GlobalData.ISDEL_NO);
        wrapper.eq(AssetsItStatus::getIs_enable, GlobalData.IS_ENABLE_YES);
        return selectList(wrapper);
    }
}
