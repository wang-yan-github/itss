package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.ChangeMajorApproveDao;
import com.jsdc.itss.model.ChangeMajorApprove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ChangeMajorApproveMapper  extends BaseMapper<ChangeMajorApprove> {


    @SelectProvider(method = "getChangeMajorApproveList",type = ChangeMajorApproveDao.class)
    List<ChangeMajorApprove> getChangeMajorApproveList(Integer change_type_id );
}
