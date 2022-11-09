package com.jsdc.itss.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.itss.common.constants.GlobalData;
import com.jsdc.itss.model.*;
import com.jsdc.itss.service.*;
import com.jsdc.itss.utils.TimeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class EventSlaSchedule {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventTimesService eventTimesService;
    @Autowired
    private SlaEventService slaEventService;
    @Autowired
    private SlaEventConfigService slaEventConfigService;
    @Autowired
    private SlaEventImpactUrgentService slaEventImpactUrgentService;
    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private OperateNodeService operateNodeService;
    @Autowired
    private EventParamConfigService eventParamConfigService;
    /**
     * 每分钟执行
     * Todo 更新工单SLA相关记录
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void slaEventTask(){
        //查询类别对应的SLA
        List<SlaEvent> slaByEventCategory = slaEventService.getSlaByEventCategory(null);
        HashMap<Integer,SlaEvent> slaMap = new HashMap();
        slaByEventCategory.forEach(x->slaMap.put(x.getEventCategoryId(),x));
        //查询待响应、待处理的工单
        List<Event> eventList = eventService.selectList(new QueryWrapper<Event>().eq("is_del","0").in("status", Arrays.asList("1","2","3")));
        for (Event event : eventList) {
            //根据类型获取对应合同的SLA
            SlaEvent slaEvent = slaMap.get(event.getEvent_category_id());
            if(slaEvent == null){
                continue;
            }
            //查询详细SLA配置
            QueryWrapper<SlaEventConfig> qw = new QueryWrapper<>();
            qw.eq("sla_event_id",slaEvent.getId());
            qw.eq("is_del","0");
            if("1".equals(slaEvent.getIs_first())){
                //查询优先级
                SlaEventImpactUrgent seiu = slaEventImpactUrgentService.selectOne(new QueryWrapper<SlaEventImpactUrgent>().eq("is_del","0").eq("impact_id",event.getEffect_degree()).eq("urgent_id",event.getUrgency_degree_id()));
                qw.eq("first_id",seiu.getFirst_id());
            }
            if("1".equals(slaEvent.getIs_request())){
                qw.eq("request_type_id",event.getRequest_type());
            }
            SlaEventConfig slaEventConfig = slaEventConfigService.selectOne(qw);
            if(slaEventConfig == null){
                continue;
            }
            //判断是否已经超时
            //查询事件时间表
            EventTimes eventTimes = eventTimesService.selectOne(new QueryWrapper<EventTimes>().eq("event_id",event.getId()));
            //响应SLA
            if("1".equals(event.getStatus())){
                eventTimes.setResponse_times(eventTimes.getResponse_times()+1);
                if("0".equals(event.getMeetResponseSLA())){
                    //记录超时时间
                    eventTimes.setResponse_exceed_times(eventTimes.getResponse_times()-Integer.parseInt(slaEventConfig.getResponse_time()));
                }else{
                    //响应SLA时间<响应累积时间 超时
                    if(Integer.parseInt(slaEventConfig.getResponse_time()) < eventTimes.getResponse_times()){
                        event.setMeetResponseSLA("0");//响应SLA超时
                        eventTimes.setResponse_exceed_times(eventTimes.getResponse_times()-Integer.parseInt(slaEventConfig.getResponse_time()));
                    }
                }
            }else if("2".equals(event.getStatus()) || "3".equals(event.getStatus())){//3:暂停 暂停不影响sla时间计算
                eventTimes.setHandle_times(eventTimes.getHandle_times()+1);
                if("0".equals(event.getMeetProcessingSLAs())){
                    //记录超时时间
                    eventTimes.setHandle_exceed_times(eventTimes.getHandle_times()-Integer.parseInt(slaEventConfig.getSolve_time()));
                }else{
                    //处理SLA时间<处理累积时间 超时
                    if(Integer.parseInt(slaEventConfig.getSolve_time()) < eventTimes.getHandle_times()){
                        event.setMeetProcessingSLAs("0");//处理SLA超时
                        eventTimes.setHandle_exceed_times(eventTimes.getHandle_times()-Integer.parseInt(slaEventConfig.getSolve_time()));
                    }
                }
            }
            //判断配置，是否工单超时自动升级
            EventParamConfig eventParamConfig = eventParamConfigService.selectById(1);
            if("1".equals(eventParamConfig.getIs_auto_update())){
                //判断服务群组是否需要升级处理人
                if(event.getService_groups_id() != null){
                    EventServiceManage eventServiceManage = eventServiceManageService.selectOne(new QueryWrapper<EventServiceManage>().eq("id",event.getService_groups_id()).eq("is_use","1").eq("is_del","0"));
                    if(eventServiceManage != null) {
                        if ("1".equals(event.getStatus())){
                            if(eventServiceManage.getSla_upgrade() != null || eventTimes.getResponse_exceed_times() == Integer.parseInt(eventServiceManage.getSla_upgrade())){
                                if(eventServiceManage.getTimeout_user_id() != null){
                                    if(null == event.getResponse_upgrade() || 0 == event.getResponse_upgrade()){
                                        event.setHandler(eventServiceManage.getTimeout_user_id());
                                        event.setResponse_upgrade(1);
                                        eventService.updateById(event);
                                    }
//                                event.setHandler(eventServiceManage.getTimeout_user_id());
                                }
                            }
                        }else if ("2".equals(event.getStatus())){
                            if(eventServiceManage.getSla_solve() != null || eventTimes.getHandle_exceed_times() == Integer.parseInt(eventServiceManage.getSla_solve())){
                                if(eventServiceManage.getTimeout_user_id() != null){
                                    if(null == event.getProcess_upgrade() || 0 == event.getProcess_upgrade()){
                                        event.setHandler(eventServiceManage.getTimeout_user_id());
                                        event.setProcess_upgrade(1);
                                        eventService.updateById(event);
                                    }
//                                event.setHandler(eventServiceManage.getTimeout_user_id());
                                }
                            }
                        }
            }



                }
            }
            eventTimes.updateById();
            event.updateById();
        }
    }

    @Autowired
    private QuestionManageService  questionManageService;
    @Autowired
    private SlaQuestionService  slaQuestionService;
    @Autowired
    private SlaQuestionConfigService slaQuestionConfigService;

    /**
     * 每分钟执行
     * Todo 更新问题SLA相关记录
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void slaQuestionTask(){
        //查询问题SLA
        List<SlaQuestion> slaQuestions = slaQuestionService.getSlaQuestionByCategoryId(null);
        HashMap<Integer,SlaQuestion> slaQuestionMap = new HashMap();
        slaQuestions.forEach(x->slaQuestionMap.put(x.getQuestionCategoryId(),x));
        //遍历待处理的问题单
        List<Question> questions = questionManageService.selectList(new QueryWrapper<Question>().eq("is_del", "0").eq("status", "3"));
        //判断是否超时
        for (Question question : questions) {
            SlaQuestion slaQuestion = slaQuestionMap.get(question.getCategory_id());
            if(slaQuestion != null){
                QueryWrapper<SlaQuestionConfig> qw = new QueryWrapper();
                qw.eq("question_id",slaQuestion.getId());
                qw.eq("is_del","0");
                //判断是否启用优先级
                if("1".equals(slaQuestion.getIs_first())){
                    qw.eq("first_id",question.getPriority_id());
                }
                SlaQuestionConfig slaQuestionConfig = slaQuestionConfigService.selectOne(qw);
                if(slaQuestionConfig == null){
                    continue;
                }
                question.setSla_time(question.getSla_time()+1);
                if("0".equals(question.getIsFitSla())){
                    question.setSla_exceed_time(question.getSla_exceed_time()+1);
                }else{
                    if(Integer.parseInt(slaQuestionConfig.getSolve_time()) < question.getSla_time()){
                        question.setIsFitSla("0");
                        question.setSla_exceed_time(question.getSla_time() - Integer.parseInt(slaQuestionConfig.getSolve_time()));
                    }
                }
                question.updateById();
            }
        }
    }

    /**
     * @param :
     * @return void
     * @author wp
     * @description 定时任务执行OLA超时升级
     * @date 2022/7/15 14:38
     */
    @Scheduled(cron = "30 */1 * * * ?")
    public void olaUpgrade(){
        List<Event> eventList = eventService.selectList(Wrappers.<Event>lambdaQuery().eq(Event::getIs_del, GlobalData.ISDEL_NO)
                .in(Event::getStatus, Arrays.asList(GlobalData.EVENT_STATUS_DXY, GlobalData.EVENT_STATUS_CLZ)));
        if(CollectionUtils.isNotEmpty(eventList)){
            eventList.forEach(x -> {
                Integer serviceGroupId = x.getService_groups_id();
                if(null != serviceGroupId){
                    EventServiceManage eventServiceManage = eventServiceManageService.selectOne(Wrappers.<EventServiceManage>lambdaQuery().eq(EventServiceManage::getId, serviceGroupId).eq(EventServiceManage::getIs_use, GlobalData.IS_ENABLE_NO));
                    if(null != eventServiceManage && null != eventServiceManage.getOla_time_out() && null != eventServiceManage.getOla_font_upgrade() && null != eventServiceManage.getOla_after_upgrade()){
                        //待响应事件
                        if(StringUtils.equals(x.getStatus(), GlobalData.EVENT_STATUS_DXY)){
                            OperateNode operateNode = operateNodeService.selectOne(Wrappers.<OperateNode>lambdaQuery().eq(OperateNode::getBill_id, x.getId()).eq(OperateNode::getBill_type, GlobalData.EVENT)
                                    .eq(OperateNode::getIs_complete, GlobalData.IS_COMPLETE_NO).eq(OperateNode::getOperate_type, GlobalData.EVENT_STATUS_DXY));
                            if(null != operateNode){
                                int timeSpent = TimeUtils.computeMinute(operateNode.getTiming_start(), new Date());
                                int compact = new BigDecimal(timeSpent).compareTo(new BigDecimal(eventServiceManage.getOla_time_out()).add(new BigDecimal(eventServiceManage.getOla_font_upgrade())));
                                if(compact > 0){ // 超时
                                    x.setHandler(eventServiceManage.getTimeout_user_id());
                                    x.setUpdate_time(new Date());
                                    eventService.updateById(x);
                                }
                            }
                        }else if(StringUtils.equals(x.getStatus(), GlobalData.EVENT_STATUS_CLZ)){
                            OperateNode operateNode = operateNodeService.selectOne(Wrappers.<OperateNode>lambdaQuery().eq(OperateNode::getBill_id, x.getId()).eq(OperateNode::getBill_type, GlobalData.EVENT)
                                    .eq(OperateNode::getIs_complete, GlobalData.IS_COMPLETE_NO).eq(OperateNode::getOperate_type, GlobalData.EVENT_STATUS_CLZ));
                            if(null != operateNode){
                                int timeSpent = TimeUtils.computeMinute(operateNode.getTiming_start(), new Date());
                                int compact = new BigDecimal(timeSpent).compareTo(new BigDecimal(eventServiceManage.getOla_time_out()).add(new BigDecimal(eventServiceManage.getOla_after_upgrade())));
                                if(compact > 0){ // 超时
                                    x.setHandler(eventServiceManage.getTimeout_user_id());
                                    x.setUpdate_time(new Date());
                                    eventService.updateById(x);
                                }
                            }
                        }
                    }

                }
            });
        }
    }



}
