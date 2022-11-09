package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysExtendFormSeeDao;
import com.jsdc.itss.model.SysExtendFormSee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysExtendFormSeeMapper extends BaseMapper<SysExtendFormSee> {

    @SelectProvider(method = "toList",type = SysExtendFormSeeDao.class)
    List<SysExtendFormSee> toList(SysExtendFormSee beanParam);
}