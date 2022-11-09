package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysExtendFormDao;
import com.jsdc.itss.model.SysExtendForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysExtendFormMapper extends BaseMapper<SysExtendForm> {

    @SelectProvider(method = "toList",type = SysExtendFormDao.class)
    List<SysExtendForm> toList(SysExtendForm beanParam);
}