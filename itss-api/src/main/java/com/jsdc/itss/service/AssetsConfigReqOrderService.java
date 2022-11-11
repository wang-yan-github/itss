package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigReqOrderDao;
import com.jsdc.itss.mapper.AssetsConfigReqOrderMapper;
import com.jsdc.itss.model.AssetsConfigReqOrder;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 配置请求关联事件工单中间表
 * ClassName: AssetsConfigReqOrderService
 * Description:
 * date: 2022/3/24 10:03
 *
 * @author bn
 */
@Service
@Transactional
public class AssetsConfigReqOrderService extends BaseService<AssetsConfigReqOrderDao, AssetsConfigReqOrder> {

    @Autowired
    AssetsConfigReqOrderMapper assetsConfigReqOrderMapper;
    @Autowired
    SysUserService sysUserService;

    public ResultInfo add(AssetsConfigReqOrder assetsConfigReqOrder, Integer... intArray) {
        //获取用户信息
        SysUser user = null;
        if (intArray.length > 0 && null != intArray[0]) {
            user = sysUserService.selectById(intArray[0]);
        } else {
            user = sysUserService.getUser();
        }
        assetsConfigReqOrder.setCreate_time(new Date());
        assetsConfigReqOrder.setCreate_user(user.getId());
        assetsConfigReqOrder.setIs_del("0");
        insert(assetsConfigReqOrder);
        return ResultInfo.success();
    }

    public ResultInfo edit(AssetsConfigReqOrder assetsConfigReqOrder) {
        assetsConfigReqOrder.setUpdate_user(sysUserService.getUser().getId());
        assetsConfigReqOrder.setUpdate_time(new Date());

        updateById(assetsConfigReqOrder);

        return ResultInfo.success();
    }
}
