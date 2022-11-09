package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SlaEventImpactDao;
import com.jsdc.itss.mapper.SlaEventImpactMapper;
import com.jsdc.itss.model.SlaEventImpact;
import com.jsdc.itss.model.SlaEventImpactUrgent;
import com.jsdc.itss.model.SlaEventUrgent;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaEventImpactService
 * Description:
 * date: 2022/3/7 14:41
 *
 * @author bn
 */
@Transactional
@Service
public class SlaEventImpactService extends BaseService<SlaEventImpactDao, SlaEventImpact> {

    @Autowired
    private SlaEventImpactMapper slaEventImpactMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SlaEventUrgentService slaEventUrgentService;
    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;

    public PageInfo<SlaEventImpact> toList(Integer pageIndex, Integer pageSize, SlaEventImpact slaEventImpact) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper<SlaEventImpact> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(slaEventImpact.getImpact_name())) {
            queryWrapper.like("impact_name", slaEventImpact.getImpact_name());
        }
        if (StringUtils.isNotEmpty(slaEventImpact.getIs_use())) {
            queryWrapper.eq("is_use", slaEventImpact.getIs_use());
        }
        queryWrapper.eq("is_del", "0");

        queryWrapper.orderByAsc("sort");
        List<SlaEventImpact> list = selectList(queryWrapper);

        PageInfo<SlaEventImpact> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    public List<SlaEventImpact> getList(SlaEventImpact slaEventImpact) {
        QueryWrapper<SlaEventImpact> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(slaEventImpact.getImpact_name())) {
            queryWrapper.like("impact_name", slaEventImpact.getImpact_name());
        }
        if (StringUtils.isNotEmpty(slaEventImpact.getIs_use())) {
            queryWrapper.eq("is_use", slaEventImpact.getIs_use());
        }
        queryWrapper.eq("is_del", GlobalData.ISDEL_NO);
        queryWrapper.orderByAsc("sort");
        List<SlaEventImpact> list = selectList(queryWrapper);
        return list;
    }

    /**
     * 事件影响度下拉接口
     * Author wzn
     * Date 2022/3/26 14:09
     */
    public List<SlaEventImpact> upSlaEventImpact() {
        QueryWrapper<SlaEventImpact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<SlaEventImpact> list = selectList(queryWrapper);
        return list;
    }

    public boolean editDefaultValue(SlaEventImpact slaEventImpact) {
        if (StringUtils.isNotEmpty(slaEventImpact.getDefault_value()) &&
                "0".equals(slaEventImpact.getDefault_value())) {
            SlaEventImpact slaEventImpactData = selectOne(new QueryWrapper<SlaEventImpact>().
                    eq("is_del", "0").eq("default_value", "0"));
            if (slaEventImpactData != null) {
                slaEventImpactData.setDefault_value("1");
                if (!(updateById(slaEventImpactData) > 0)) {
                    return false;
                }
            }
        }

        return true;
    }

    public ResultInfo toEdit(SlaEventImpact slaEventImpact) {
        QueryWrapper<SlaEventImpact> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("is_del","0").eq("impact_name",slaEventImpact.getImpact_name()).ne("id",slaEventImpact.getId());
        Long count = slaEventImpactMapper.selectCount(queryWrapper1);
        if (count> 0){
            return ResultInfo.error("名称已存在");
        }


        slaEventImpact.setUpdate_time(new Date());
        slaEventImpact.setUpdate_user(sysUserService.getUser().getId());

        if (!editDefaultValue(slaEventImpact)) {
            return ResultInfo.error("修改默认值失败,修改失败!");
        }

        if (updateById(slaEventImpact) > 0) {
            //删除 事件影响度紧急度关系管理表
            if (slaEventImpact.getIs_del().equals(GlobalData.ISDEL_YES)) {
                QueryWrapper<SlaEventImpactUrgent> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("impact_id", slaEventImpact.getId()).eq("is_del", String.valueOf(0));
                List<SlaEventImpactUrgent> slaEventImpactUrgents = slaEventImpactUrgentService.selectList(queryWrapper);
                slaEventImpactUrgents.forEach(x -> {
                    x.setIs_del(String.valueOf(1));
                    slaEventImpactUrgentService.updateById(x);
                });
            }
            return ResultInfo.success("修改成功！");
        } else {
            return ResultInfo.error("修改失败！");
        }

    }

    @Transactional
    public ResultInfo toAdd(SlaEventImpact slaEventImpact) {
        QueryWrapper<SlaEventImpact> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("is_del","0").eq("impact_name",slaEventImpact.getImpact_name());
        Long count = slaEventImpactMapper.selectCount(queryWrapper1);
        if (count> 0){
            return ResultInfo.error("名称已存在");
        }


        slaEventImpact.setCreate_time(new Date());
        slaEventImpact.setCreate_user(sysUserService.getUser().getId());
        slaEventImpact.setIs_del(String.valueOf(0));
        if (!editDefaultValue(slaEventImpact)) {
            return ResultInfo.error("修改默认值失败,添加失败!");
        }

        if (insert(slaEventImpact) > 0) {
            QueryWrapper<SlaEventUrgent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_del", String.valueOf(0));
            List<SlaEventUrgent> slaEventUrgents = slaEventUrgentService.selectList(queryWrapper);
            slaEventUrgents.forEach(x -> {
                SlaEventImpactUrgent slaEventImpactUrgent = new SlaEventImpactUrgent();
                slaEventImpactUrgent.setImpact_id(slaEventImpact.getId());
                slaEventImpactUrgent.setUrgent_id(x.getId());
                slaEventImpactUrgent.setCreate_time(new Date());
                slaEventImpactUrgent.setCreate_user(sysUserService.getUser().getId());
                slaEventImpactUrgent.setIs_del(String.valueOf(0));
                slaEventImpactUrgentService.insert(slaEventImpactUrgent);
            });
            return ResultInfo.success("添加成功！");
        } else {
            return ResultInfo.error("添加失败！");
        }
    }
}
