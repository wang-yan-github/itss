package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.AssetsExpandFormFieldDao;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.model.AssetsExpandFormField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Mapper
public interface AssetsExpandFormFieldMapper extends BaseMapper<AssetsExpandFormField> {

    @SelectProvider(method = "toList",type = AssetsExpandFormFieldDao.class)
    List<AssetsExpandFormField> toList(AssetsExpandFormField beanParam);

    @SelectProvider(method = "getExpandSubList",type = AssetsExpandFormFieldDao.class)
    List<AssetsExpandFormField> getExpandSubList(AssetsExpandForm beanParam);
}