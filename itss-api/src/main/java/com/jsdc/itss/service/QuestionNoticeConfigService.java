package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionNoticeConfigDao;
import com.jsdc.itss.mapper.QuestionNoticeConfigMapper;
import com.jsdc.itss.model.QuestionNoticeConfig;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionNoticeConfig
 * Description: 问题通知配置表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class QuestionNoticeConfigService extends BaseService<QuestionNoticeConfigDao, QuestionNoticeConfig> {

    @Autowired
    private QuestionNoticeConfigMapper questionNoticeConfigMapper;
    @Autowired
    private SysUserService sysUserService;



    public ResultInfo getOneInfo(QuestionNoticeConfig questionNoticeConfig) {
        String type= questionNoticeConfig.getType();
        QueryWrapper<QuestionNoticeConfig> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("type",questionNoticeConfig.getType());
        QuestionNoticeConfig questionNoticeConfig1 =questionNoticeConfigMapper.selectOne(queryWrapper);
        return ResultInfo.success(questionNoticeConfig1);
    }

    /**
     * 保存/更新
     */
    public ResultInfo saveOrUpd(QuestionNoticeConfig bean) {
        SysUser sysUser = sysUserService.getUser();
        List<QuestionNoticeConfig> list = bean.getList();
        for (QuestionNoticeConfig temp : list) {
            temp.setUpdate_user(sysUser.getId());
            // 修改时间
            temp.setUpdate_time(new Date());
            updateById(temp);
        }
        return ResultInfo.success();

    }

}
