package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.ChangeInfoDao;
import com.jsdc.itss.dao.PublishBasicInfoDao;
import com.jsdc.itss.model.ChangeInfo;
import com.jsdc.itss.model.vo.ChangesOf;
import com.jsdc.itss.vo.ChangeExcelReport;
import com.jsdc.itss.vo.ChangeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ChangeInfoMapper extends BaseMapper<ChangeInfo> {
    @SelectProvider(method = "toList",value = ChangeInfoDao.class)
    List<ChangeVo> toList(ChangeVo changeVo);
    @SelectProvider(method = "getById",value = ChangeInfoDao.class)
    ChangeVo getById(Integer id);
    @SelectProvider(method = "getChange",value = ChangeInfoDao.class)
    ChangeVo getChange(ChangeInfo changeInfo);
    @SelectProvider(method = "toExcelList",value = ChangeInfoDao.class)
    List<ChangeExcelReport> toExcelList(ChangeVo changeVo);

    @SelectProvider(method = "toNeedList",value = ChangeInfoDao.class)
    List<ChangeVo> toNeedList(ChangeVo changeVo);




    @SelectProvider(method = "sourcesOfStatistics",type = PublishBasicInfoDao.class)
    List<ChangesOf> sourcesOfStatistics();

    @SelectProvider(method = "categoriesOfStatistical",type = PublishBasicInfoDao.class)
    List<ChangesOf> categoriesOfStatistical();

    @SelectProvider(method = "typesOfStatistical",type = PublishBasicInfoDao.class)
    List<ChangesOf> typesOfStatistical();

}
