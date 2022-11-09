package com.jsdc.itss.service;

import cn.hutool.cron.CronUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.dao.EventScheduleDao;
import com.jsdc.itss.dao.EventScheduleSonDao;
import com.jsdc.itss.mapper.EventEemplateMapper;
import com.jsdc.itss.mapper.EventScheduleMapper;
import com.jsdc.itss.mapper.EventScheduleSonMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class EventScheduleSonService extends BaseService<EventScheduleSonDao, EventScheduleSon> {

    @Autowired
    private EventScheduleSonMapper eventScheduleSonMapper ;

    public void add(EventScheduleSon eventScheduleSon){
        eventScheduleSon.setIs_del("0");
        eventScheduleSonMapper.insert(eventScheduleSon) ;
    }


}
