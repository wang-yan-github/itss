package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysCompanyDao;
import com.jsdc.itss.model.SysCompany;
import com.jsdc.itss.vo.SysCompanyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SysCompanyMapper extends BaseMapper<SysCompany> {

    @SelectProvider(method = "getCompanyInfo",type = SysCompanyDao.class)
    List<SysCompanyVo> getCompanyInfo(SysCompany sysCompany);

}
