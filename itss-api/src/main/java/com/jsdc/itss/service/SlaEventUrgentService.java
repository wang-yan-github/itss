package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.SlaEventUrgentDao;
import com.jsdc.itss.mapper.SlaEventUrgentMapper;
import com.jsdc.itss.model.SlaEventImpact;
import com.jsdc.itss.model.SlaEventImpactUrgent;
import com.jsdc.itss.model.SlaEventUrgent;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * ClassName: SlaEventUrgentService
 * Description:
 * date: 2022/3/7 14:36
 *
 * @author bn
 */
@Service
@Transactional
public class SlaEventUrgentService extends BaseService<SlaEventUrgentDao,SlaEventUrgent> {

    @Autowired
    private SlaEventUrgentMapper slaEventUrgentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SlaEventImpactService slaEventImpactService;
    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;

    public PageInfo<SlaEventUrgent> toList(Integer pageIndex, Integer pageSize, SlaEventUrgent slaEventUrgent) {
        PageHelper.startPage(pageIndex,pageSize);
        QueryWrapper<SlaEventUrgent> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(slaEventUrgent.getUrgent_name())){
            queryWrapper.like("urgent_name",slaEventUrgent.getUrgent_name());
        }
        if (StringUtils.isNotEmpty(slaEventUrgent.getIs_use())){
            queryWrapper.eq("is_use",slaEventUrgent.getIs_use());
        }
        if (StringUtils.isNotEmpty(slaEventUrgent.getSort())){
            queryWrapper.eq("sort",slaEventUrgent.getSort());
        }
        queryWrapper.eq("is_del","0");
        queryWrapper.orderByAsc("sort");
        List<SlaEventUrgent> list=selectList(queryWrapper);

        PageInfo<SlaEventUrgent> pageInfo=new PageInfo<>(list);

        return pageInfo;

    }

    public List<SlaEventUrgent> getList(SlaEventUrgent slaEventUrgent) {
        QueryWrapper<SlaEventUrgent> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(slaEventUrgent.getUrgent_name())){
            queryWrapper.like("urgent_name",slaEventUrgent.getUrgent_name());
        }
        if (StringUtils.isNotEmpty(slaEventUrgent.getIs_use())){
            queryWrapper.eq("is_use",slaEventUrgent.getIs_use());
        }
        if (StringUtils.isNotEmpty(slaEventUrgent.getSort())){
            queryWrapper.eq("sort",slaEventUrgent.getSort());
        }
        queryWrapper.eq("is_use", "1");
        queryWrapper.eq("is_del",GlobalData.ISDEL_NO);
        queryWrapper.orderByAsc("sort");
        List<SlaEventUrgent> list=selectList(queryWrapper);
        return list;

    }

    @Transactional
    public ResultInfo toAdd(SlaEventUrgent slaEventUrgent) {
        QueryWrapper<SlaEventUrgent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("is_del","0").eq("urgent_name",slaEventUrgent.getUrgent_name());
        Long count = slaEventUrgentMapper.selectCount(queryWrapper1);
        if (count>0){
            return ResultInfo.error("名称已存在");
        }


        slaEventUrgent.setCreate_time(new Date());
        slaEventUrgent.setCreate_user(sysUserService.getUser().getId());
        slaEventUrgent.setIs_del(String.valueOf(0));
        if (!editDefaultValue(slaEventUrgent)){
            return ResultInfo.error("修改默认值失败,添加失败!");
        }

        if (insert(slaEventUrgent)>0){
            QueryWrapper<SlaEventImpact> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_del",String.valueOf(0));
            List<SlaEventImpact> slaEventImpacts = slaEventImpactService.selectList(queryWrapper);
            slaEventImpacts.forEach(x->{
                SlaEventImpactUrgent slaEventImpactUrgent = new SlaEventImpactUrgent();
                slaEventImpactUrgent.setImpact_id(x.getId());
                slaEventImpactUrgent.setUrgent_id(slaEventUrgent.getId());
                slaEventImpactUrgent.setCreate_time(new Date());
                slaEventImpactUrgent.setCreate_user(sysUserService.getUser().getId());
                slaEventImpactUrgent.setIs_del(String.valueOf(0));
                slaEventImpactUrgentService.insert(slaEventImpactUrgent);
            });
            return ResultInfo.success("添加成功！");
        }else {
            return ResultInfo.error("添加失败！");
        }
    }

    public boolean editDefaultValue(SlaEventUrgent slaEventUrgent){
        if (StringUtils.isNotEmpty(slaEventUrgent.getDefault_value())&&
                "0".equals(slaEventUrgent.getDefault_value())){
            SlaEventUrgent slaEventUrgentData=selectOne(new QueryWrapper<SlaEventUrgent>().
                    eq("is_del","0").eq("default_value","0"));
            if (slaEventUrgentData!=null){
                slaEventUrgentData.setDefault_value("1");
                if (!(updateById(slaEventUrgentData)>0)){
                   return false;
                }
            }
        }

        return true;
    }

    public ResultInfo toEdit(SlaEventUrgent slaEventUrgent) {
        QueryWrapper<SlaEventUrgent> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("is_del","0").eq("urgent_name",slaEventUrgent.getUrgent_name()).ne("id",slaEventUrgent.getId());
        Long count = slaEventUrgentMapper.selectCount(queryWrapper1);
        if (count>0){
            return ResultInfo.error("名称已存在");
        }

        slaEventUrgent.setUpdate_time(new Date());
        slaEventUrgent.setUpdate_user(sysUserService.getUser().getId());

        if (!editDefaultValue(slaEventUrgent)){
            return ResultInfo.error("修改默认值失败,修改失败!");
        }
        if (updateById(slaEventUrgent)>0){
            //删除 事件影响度紧急度关系管理表
            if(slaEventUrgent.getIs_del().equals(GlobalData.ISDEL_YES)){
                QueryWrapper<SlaEventImpactUrgent> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("urgent_id",slaEventUrgent.getId()).eq("is_del",String.valueOf(0));
                List<SlaEventImpactUrgent> slaEventImpactUrgents = slaEventImpactUrgentService.selectList(queryWrapper);
                slaEventImpactUrgents.forEach(x->{
                    x.setIs_del(String.valueOf(1));
                    slaEventImpactUrgentService.updateById(x);
                });
            }
            return ResultInfo.success("修改成功！");
        }else {
            return ResultInfo.error("修改失败！");
        }
    }

    public ResultInfo toDel(SlaEventUrgent slaEventUrgent) {
        slaEventUrgent.setUpdate_time(new Date());
        slaEventUrgent.setUpdate_user(sysUserService.getUser().getId());

        if (!editDefaultValue(slaEventUrgent)){
            return ResultInfo.error("修改默认值失败,修改失败!");
        }

        if (updateById(slaEventUrgent)>0){
            QueryWrapper<SlaEventImpactUrgent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("urgent_id",slaEventUrgent.getId()).eq("is_del",String.valueOf(0));
            List<SlaEventImpactUrgent> slaEventImpactUrgents = slaEventImpactUrgentService.selectList(queryWrapper);
            slaEventImpactUrgents.forEach(x->{
                x.setIs_del(String.valueOf(1));
                slaEventImpactUrgentService.updateById(x);
            });
            return ResultInfo.success("修改成功！");
        }else {
            return ResultInfo.error("修改失败！");
        }
    }


    public List<SlaEventUrgent> upSlaEventImpactUrgent(){
        QueryWrapper<SlaEventUrgent> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del","0");
        return slaEventUrgentMapper.selectList(queryWrapper) ;
    }
}
