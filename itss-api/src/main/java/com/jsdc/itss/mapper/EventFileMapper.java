package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventFileDao;
import com.jsdc.itss.model.EventFile;
import com.jsdc.itss.model.FileManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface EventFileMapper extends BaseMapper<EventFile> {

    @SelectProvider(method = "getFileManageByType", type = EventFileDao.class)
    List<FileManage> getFileManageByType(int eventId, int type);

}
