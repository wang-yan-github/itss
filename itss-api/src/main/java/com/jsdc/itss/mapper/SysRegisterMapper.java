package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysRegisterDao;
import com.jsdc.itss.model.SysRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysRegisterMapper extends BaseMapper<SysRegister> {

    @SelectProvider(method = "toList",type = SysRegisterDao.class)
    List<SysRegister> toList(SysRegister beanParam);
}