package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysAnnouncementSeeDao;
import com.jsdc.itss.model.SysAnnouncementSee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysAnnouncementSeeMapper extends BaseMapper<SysAnnouncementSee> {

    @SelectProvider(method = "toList",type = SysAnnouncementSeeDao.class)
    List<SysAnnouncementSee> toList(SysAnnouncementSee beanParam);
}