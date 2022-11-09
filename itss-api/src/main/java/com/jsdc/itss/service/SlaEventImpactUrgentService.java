package com.jsdc.itss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SlaEventImpactUrgentDao;
import com.jsdc.itss.mapper.SlaEventImpactUrgentMapper;
import com.jsdc.itss.model.SlaEventImpact;
import com.jsdc.itss.model.SlaEventImpactUrgent;
import com.jsdc.itss.vo.ChangeVo;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SlaEventImpactUrgentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaEventImpactUrgentService
 * Description:
 * date: 2022/3/7 14:38
 *
 * @author bn
 */
@Transactional
@Service
public class SlaEventImpactUrgentService extends BaseService<SlaEventImpactUrgentDao,SlaEventImpactUrgent> {

    @Autowired
    private SlaEventImpactUrgentMapper slaEventImpactUrgentMapper;
    @Autowired
    private SysUserService sysUserService;

    public List toList(SlaEventImpactUrgent slaEventImpactUrgent) {
        List<SlaEventImpactUrgentVo> list=slaEventImpactUrgentMapper.toList(slaEventImpactUrgent);
        return list;
    }

    public SlaEventImpactUrgentVo getFirst(SlaEventImpactUrgent slaEventImpactUrgent) {
        List<SlaEventImpactUrgentVo> slaEventImpactUrgentVos = slaEventImpactUrgentMapper.toList(slaEventImpactUrgent);
        if(slaEventImpactUrgentVos != null && slaEventImpactUrgentVos.size() > 0){
            return slaEventImpactUrgentVos.get(0);
        }
        return null;
    }

    public ResultInfo toEdit(String event_impact_urgent) {

        JSONObject jsonObject=JSONObject.parseObject(event_impact_urgent);
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("list").toString());
        List<SlaEventImpactUrgentVo> slaEventImpactUrgents = jsonArray.toJavaList(SlaEventImpactUrgentVo.class);
        slaEventImpactUrgents.forEach(x->{
            SlaEventImpactUrgent slaEventImpactUrgent = selectById(x.getId());
            slaEventImpactUrgent.setFirst_id(x.getFirst_id());
            slaEventImpactUrgent.setUpdate_time(new Date());
            slaEventImpactUrgent.setUpdate_user(sysUserService.getUser().getId());
            updateById(slaEventImpactUrgent);
        });
        return ResultInfo.success("操作成功");
    }
}
