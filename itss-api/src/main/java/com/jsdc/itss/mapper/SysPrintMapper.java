package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysPrintDao;
import com.jsdc.itss.model.SysPrint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysPrintMapper extends BaseMapper<SysPrint> {

    @SelectProvider(method = "toList",type = SysPrintDao.class)
    List<SysPrint> toList(SysPrint beanParam);
}