package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.ChangeUrgentApproveDao;
import com.jsdc.itss.model.ChangeUrgentApprove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ChangeUrgentApproveMapper  extends BaseMapper<ChangeUrgentApprove> {

    @SelectProvider(method = "getChangeUrgentApproveList",type = ChangeUrgentApproveDao.class)
    List<ChangeUrgentApprove> getChangeUrgentApproveList(Integer change_type_id );
}
