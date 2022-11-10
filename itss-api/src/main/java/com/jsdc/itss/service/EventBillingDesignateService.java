package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventBillingDesignateDao;
import com.jsdc.itss.exception.CustomException;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author libin
 * @create 2022/4/20 17:18
 */
@Service
@Transactional
public class EventBillingDesignateService extends BaseService<EventBillingDesignateDao, EventBillingDesignate> {

    @Autowired
    SysUserService sysUserService;

    /**
     * 获取当前用户所在的服务群组
     *
     * @return
     */
    public List<Integer> getServiceIdByCurrentUser(Integer ...intArray) {
        //获取用户信息
        SysUser sysUser = null;
        if (intArray.length > 0 && null !=intArray[0]) {
            sysUser = sysUserService.selectById(intArray[0]);
        } else {
            sysUser = sysUserService.getUser();
        }
        List<Integer> userGroups = new ArrayList<>();
        QueryWrapper<EventBillingDesignate> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("engineer_userid", sysUser.getId());
        queryWrapper3.eq("is_del", "0");
        queryWrapper3.isNotNull("service_management_id");
        List<EventBillingDesignate> groups = selectList(queryWrapper3);
        groups.forEach(x -> {
            userGroups.add(x.getService_management_id());
        });
        return userGroups;
    }
}
