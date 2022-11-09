package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.EventDao;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.vo.EventVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;


@Mapper
public interface EventMapper extends BaseMapper<Event> {

    @SelectProvider(method = "toList", type = EventDao.class)
    List<Event> toList(EventVo beanParam);

    /**
     * 我参与的
     *
     * @param beanParam
     * @return
     */
    @SelectProvider(method = "toMyParticipationList", type = EventDao.class)
    List<Event> toMyParticipationList(EventVo beanParam);

    /**
     * 组内
     *
     * @param beanParam
     * @return
     */
    @SelectProvider(method = "toMyGroupList", type = EventDao.class)
    List<Event> toMyGroupList(EventVo beanParam, List<Integer> serviceGroups);

    @SelectProvider(method = "getListByPage", type = EventDao.class)
    List<Event> getListByPage(Event event);


    /**
     * 用户历史参与
     *
     * @param beanParam
     * @return
     */
    @SelectProvider(method = "participationList", type = EventDao.class)
    List<Event> participationList(EventVo beanParam);

    /**
     * 主页统计
     * @param start_time
     * @param end_time
     * @return
     */
    @SelectProvider(method = "statistics", type = EventDao.class)
    Map statistics(String start_time, String end_time);
}
