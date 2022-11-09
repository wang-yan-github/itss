package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.QuestionSourceDao;
import com.jsdc.itss.mapper.QuestionSourceMapper;
import com.jsdc.itss.model.QuestionSource;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionSource
 * Description: 问题来源表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class QuestionSourceService extends BaseService<QuestionSourceDao, QuestionSource> {

    @Autowired
    private QuestionSourceMapper questionSourceMapper;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询
     */
    public PageInfo<QuestionSource> getPageList(Integer pageIndex, Integer pageSize, QuestionSource bean) {
        PageHelper.startPage(pageIndex, pageSize, "create_time desc");

        List<QuestionSource> list = selectList(Wrappers.<QuestionSource>lambdaQuery()
                .eq(QuestionSource::getIs_del, "0")
                .like(StringUtils.isNotEmpty(bean.getName()), QuestionSource::getName, bean.getName())
        );
        for (QuestionSource temp :list){
            temp.setUse_name(temp.getIs_use() == null?"":("0".equals(temp.getIs_use()) ? "禁用" : "启用"));
        }

        PageInfo<QuestionSource> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 列表查询
     */
    public List<QuestionSource> getList(QuestionSource bean) {
        QueryWrapper<QuestionSource> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(bean.getName())) {
            queryWrapper.like("name", bean.getName());
        }
        queryWrapper.eq("is_del", 0);
        List<QuestionSource> list = selectList(queryWrapper);
        return list;
    }

    /**
     * 保存/更新
     */
    public ResultInfo saveOrUpd(QuestionSource bean) {
        SysUser sysUser = sysUserService.getUser();
        if (Base.notEmpty(bean.getId())) {
            //修改
            // 修改者
            bean.setUpdate_user(sysUser.getId());
            // 修改时间
            bean.setUpdate_time(new Date());
            updateById(bean);
            return ResultInfo.success();
        } else {
            //新增
            bean.setIs_del("0");
            bean.setCreate_time(new Date());
            bean.setCreate_user(sysUser.getId());
            insert(bean);
            return ResultInfo.success();
        }
    }


    /**
     * 删除
     */
    public ResultInfo deleteSource (Integer ids){

        UpdateWrapper<QuestionSource> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",ids);
        updateWrapper.set("is_del","1");
        questionSourceMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }
}
