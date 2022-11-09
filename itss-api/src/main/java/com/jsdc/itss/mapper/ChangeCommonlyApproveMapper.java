package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.ChangeCommonlyApproveDao;
import com.jsdc.itss.model.ChangeCommonlyApprove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ChangeCommonlyApproveMapper extends BaseMapper<ChangeCommonlyApprove> {

    @SelectProvider(method = "geteCommonlyApproveList",type = ChangeCommonlyApproveDao.class)
    List<ChangeCommonlyApprove> geteCommonlyApproveList(Integer change_type_id );
}
