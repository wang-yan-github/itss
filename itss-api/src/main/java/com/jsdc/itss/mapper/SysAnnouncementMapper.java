package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.SysAnnouncementDao;
import com.jsdc.itss.model.SysAnnouncement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Mapper
public interface SysAnnouncementMapper extends BaseMapper<SysAnnouncement> {

    @SelectProvider(method = "toList",type = SysAnnouncementDao.class)
    List<SysAnnouncement> toList(SysAnnouncement beanParam);
}