package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysAuthorityDao;
import com.jsdc.itss.dao.SysCompanyDao;
import com.jsdc.itss.mapper.SysAuthorityMapper;
import com.jsdc.itss.model.SysAuthority;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysDepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("ALL")
public class SysAuthorityService extends BaseService<SysAuthorityDao, SysAuthority> {


    @Autowired
    private SysAuthorityMapper sysAuthorityMapper;


    public ResultInfo getSysAuthorityByTree(SysAuthority sysAuthority1){
        QueryWrapper<SysAuthority> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        if (null != sysAuthority1){
            if (StringUtils.isNotEmpty(sysAuthority1.getName())){
                queryWrapper.like("name",sysAuthority1.getName());
            }
        }
        List<SysAuthority> list = sysAuthorityMapper.selectList(queryWrapper);
        List<SysAuthority> resultList = new ArrayList<>();

        for (int i = 0 ; i< list.size();i++){
            SysAuthority sysAuthority =list.get(i);
            if (sysAuthority.getParent_id()==-1){
                resultList.add(sysAuthority);
                setChildList(sysAuthority,list);
            }
        }
        return ResultInfo.success(resultList);
    }

    private void setChildList( SysAuthority sysAuthority, List<SysAuthority> list){
        for(SysAuthority temp:list){
            if(temp.getParent_id()==sysAuthority.getId()){
                sysAuthority.getChildren().add(temp);
                setChildList(temp,list);
            }
        }
    }
}
