package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SlaQuestionFirstDao;
import com.jsdc.itss.mapper.SlaQuestionFirstMapper;
import com.jsdc.itss.model.SlaQuestionFirst;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaQuestionFirstService
 * Description:
 * date: 2022/3/7 14:31
 *
 * @author bn
 */
@Transactional
@Service
public class SlaQuestionFirstService extends BaseService<SlaQuestionFirstDao, SlaQuestionFirst> {

    @Autowired
    SlaQuestionFirstMapper slaQuestionFirstMapper;
    @Autowired
    SysUserService sysUserService;

    public PageInfo<SlaQuestionFirst> toList(Integer pageIndex, Integer pageSize, SlaQuestionFirst slaQuestionFirst) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<SlaQuestionFirst> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", '0');
        if (StringUtils.isNotEmpty(slaQuestionFirst.getFirst_name())) {
            queryWrapper.like("first_name", slaQuestionFirst.getFirst_name());
        }
        if (StringUtils.isNotEmpty(slaQuestionFirst.getIs_use())) {
            queryWrapper.eq("is_use", slaQuestionFirst.getIs_use());
        }
        if (StringUtils.isNotEmpty(slaQuestionFirst.getSort())) {
            queryWrapper.eq("sort", slaQuestionFirst.getSort());
        }
        if (StringUtils.isNotEmpty(slaQuestionFirst.getRemark())) {
            queryWrapper.like("remark", slaQuestionFirst.getRemark());
        }
        queryWrapper.orderByAsc("sort");

        List<SlaQuestionFirst> list = selectList(queryWrapper);

        PageInfo<SlaQuestionFirst> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    public List<SlaQuestionFirst> getList(SlaQuestionFirst slaQuestionFirst) {
        QueryWrapper<SlaQuestionFirst> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", '0');
        if (StringUtils.isNotEmpty(slaQuestionFirst.getFirst_name())) {
            queryWrapper.like("first_name", slaQuestionFirst.getFirst_name());
        }
        if (StringUtils.isNotEmpty(slaQuestionFirst.getIs_use())) {
            queryWrapper.eq("is_use", slaQuestionFirst.getIs_use());
        }
        if (StringUtils.isNotEmpty(slaQuestionFirst.getSort())) {
            queryWrapper.eq("sort", slaQuestionFirst.getSort());
        }
        if (StringUtils.isNotEmpty(slaQuestionFirst.getRemark())) {
            queryWrapper.like("remark", slaQuestionFirst.getRemark());
        }
        queryWrapper.orderByAsc("sort");
        List<SlaQuestionFirst> list = selectList(queryWrapper);

        return list;
    }

    public ResultInfo toAdd(SlaQuestionFirst slaQuestionFirst) {
        QueryWrapper<SlaQuestionFirst> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", '0').eq("first_name", slaQuestionFirst.getFirst_name());
        Long count = slaQuestionFirstMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }

        slaQuestionFirst.setCreate_time(new Date());
        slaQuestionFirst.setCreate_user(sysUserService.getUser().getId());
        slaQuestionFirst.setIs_del(String.valueOf(0));
        if (insert(slaQuestionFirst) > 0) {
            return ResultInfo.success("添加成功！");
        } else {
            return ResultInfo.error("添加失败！");
        }

    }

    public ResultInfo toEdit(SlaQuestionFirst slaQuestionFirst) {
        QueryWrapper<SlaQuestionFirst> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", '0').eq("first_name", slaQuestionFirst.getFirst_name()).ne("id",slaQuestionFirst.getId());
        Long count = slaQuestionFirstMapper.selectCount(queryWrapper);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }


        slaQuestionFirst.setUpdate_time(new Date());
        slaQuestionFirst.setUpdate_user(sysUserService.getUser().getId());
        if (updateById(slaQuestionFirst) > 0) {
            return ResultInfo.success("修改成功！");
        } else {
            return ResultInfo.error("修改失败！");
        }

    }
}
