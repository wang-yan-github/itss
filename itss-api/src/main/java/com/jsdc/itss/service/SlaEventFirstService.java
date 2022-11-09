package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SlaEventFirstDao;
import com.jsdc.itss.mapper.SlaEventFirstMapper;
import com.jsdc.itss.model.SlaEventFirst;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaEventFirstService
 * Description:
 * date: 2022/3/7 14:45
 *
 * @author bn
 */
@Transactional
@Service
public class SlaEventFirstService extends BaseService<SlaEventFirstDao, SlaEventFirst> {
    @Autowired
    private SlaEventFirstMapper slaEventFirstMapper;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SlaEventFirst> toList(Integer pageIndex, Integer pageSize, SlaEventFirst slaEventFirst) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<SlaEventFirst> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(slaEventFirst.getFirst_name())) {
            queryWrapper.like("first_name", slaEventFirst.getFirst_name());
        }
        if (StringUtils.isNotEmpty(slaEventFirst.getIs_use())) {
            queryWrapper.eq("is_use", slaEventFirst.getIs_use());
        }
        queryWrapper.eq("is_del", "0");
        queryWrapper.orderByAsc("sort");
        List<SlaEventFirst> list = selectList(queryWrapper);

        PageInfo<SlaEventFirst> pageInfo = new PageInfo<>(list);

        return pageInfo;


    }

    public ResultInfo toAdd(SlaEventFirst slaEventFirst) {
        QueryWrapper<SlaEventFirst>   queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("first_name", slaEventFirst.getFirst_name());
        Long count = slaEventFirstMapper.selectCount(queryWrapper);
        if(count > 0){
            return ResultInfo.error("名称已经存在");
        }

        slaEventFirst.setCreate_time(new Date());
        slaEventFirst.setCreate_user(sysUserService.getUser().getId());
        slaEventFirst.setIs_del(String.valueOf(0));
//        if (!editVip(slaEventFirst)) {
//            return ResultInfo.error("修改默认值失败,添加失败!");
//        }

        if (insert(slaEventFirst) > 0) {
            editVip(slaEventFirst);
            return ResultInfo.success("添加成功！");
        } else {
            return ResultInfo.error("添加失败！");
        }
    }

    private boolean editVip(SlaEventFirst slaEventFirst) {
        if (StringUtils.isNotEmpty(slaEventFirst.getVip()) &&
                "1".equals(slaEventFirst.getVip())) {

            //是否默认0否 1是
            LambdaUpdateWrapper<SlaEventFirst> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(SlaEventFirst::getVip, "0")
                    .ne(SlaEventFirst::getId, slaEventFirst.getId());
            update(null, wrapper);

//            SlaEventFirst slaEventFirstData = selectOne(new QueryWrapper<SlaEventFirst>().
//                    eq("is_del", "0").eq("vip", "1"));
//            if (slaEventFirstData != null) {
//                slaEventFirstData.setVip("0");
//                if (!(updateById(slaEventFirstData) > 0)) {
//                    return false;
//                }
//            }
        }

        return true;

    }


    public ResultInfo toEdit(SlaEventFirst slaEventFirst) {

        QueryWrapper<SlaEventFirst>   queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0").eq("first_name", slaEventFirst.getFirst_name()).ne("id",slaEventFirst.getId());
        Long count = slaEventFirstMapper.selectCount(queryWrapper);
        if(count > 0){
            return ResultInfo.error("名称已经存在");
        }
        slaEventFirst.setUpdate_time(new Date());
        slaEventFirst.setUpdate_user(sysUserService.getUser().getId());
        if (!editVip(slaEventFirst)) {
            return ResultInfo.error("修改默认值失败,修改失败!");
        }

        if (updateById(slaEventFirst) > 0) {
            return ResultInfo.success("修改成功！");
        } else {
            return ResultInfo.error("修改失败！");
        }
    }
}
