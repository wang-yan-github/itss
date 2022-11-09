package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.model.SysParameter;
import com.jsdc.itss.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysParameterMapper extends BaseMapper<SysParameter> {
}
