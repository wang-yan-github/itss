package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.ChangeNoticeConfigDao;
import com.jsdc.itss.mapper.ChangeNoticeConfigMapper;
import com.jsdc.itss.model.ChangeNoticeConfig;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ChangeNoticeConfig
 * Description: 变更类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class ChangeNoticeConfigService extends BaseService<ChangeNoticeConfigDao, ChangeNoticeConfig> {

    @Autowired
    private ChangeNoticeConfigMapper changeNoticeConfigMapper;
    @Autowired
    private SysUserService sysUserService;

    public ResultInfo getOneByType(ChangeNoticeConfig changeNoticeConfig){
        String type = changeNoticeConfig.getType();
        QueryWrapper <ChangeNoticeConfig> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("type",type);
        ChangeNoticeConfig changeNoticeConfig1 =changeNoticeConfigMapper.selectOne(queryWrapper);
        return ResultInfo.success(changeNoticeConfig1);
    }


    /**
     * 保存/更新
     */
    public ResultInfo saveOrUpd(ChangeNoticeConfig bean) {
        SysUser sysUser = sysUserService.getUser();
        List<ChangeNoticeConfig>  list =  bean.getList();
        for (ChangeNoticeConfig temp : list){
            // 修改者
            temp.setUpdate_user(sysUser.getId());
            // 修改时间
            temp.setUpdate_time(new Date());
            updateById(temp);
        }
        return ResultInfo.success();

    }

}
