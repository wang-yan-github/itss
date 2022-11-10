package com.jsdc.itss.dao;

import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.Event;
import com.jsdc.itss.model.vo.EventVo;
import com.jsdc.itss.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class EventDao extends BaseDao<Event> {


    public String getListByPage(Event event) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT event.*,sys_user.`name` create_user_name, ");
        sql.append(" event_category.`name` category_name,event_service_manage.`name` service_name, ");
        sql.append(" event.`handler`,sla_event_first.`first_name` priority_name,event.`status` ,event.* ");
        sql.append(" FROM event  ");
        sql.append(" LEFT JOIN sys_user ON event.`create_user` = sys_user.`id` ");
        sql.append(" LEFT JOIN event_category ON event.`event_category_id` = event_category.`id` ");
        sql.append(" LEFT JOIN event_service_manage ON event.`service_groups_id` = event_service_manage.`id` ");
        sql.append(" LEFT JOIN sla_event_first ON event.`priority_id` = sla_event_first.`id` ");
        sql.append(" WHERE event.`is_del` = '0' ");
        sql.append(" AND (event.`status` = '1' OR event.`status` = '2' OR event.`status` = '3') ");
        if (Base.notEmpty(event.getUser_type())) {
            if (1 == event.getUser_type()) {
                sql.append(" and event.user_type = '" + event.getUser_type() + "' ");
            } else {
                sql.append(" and (event.user_type is null or event.user_type='2') ");
            }
        }
        if (event.getId() != null) {
            sql.append(" AND event.id = '" + event.getId() + "' ");
        }
        if (StringUtils.isNotEmpty(event.getStatus())) {
            sql.append(" AND event.status = '" + event.getStatus() + "' ");
        }
        if (event.getUser_id() != null) {
            sql.append(" AND event.user_id = '" + event.getUser_id() + "' ");
        }
        if (event.getCreate_user() != null) {
            sql.append(" AND event.create_user = " + event.getCreate_user());
        }
        if (event.getHandler() != null) {
            sql.append(" AND event.handler = " + event.getHandler());
        }
        if (event.getSource_id() != null) {
            sql.append(" AND event.source_id = " + event.getSource_id());
        }
        if (event.getService_groups_id() != null) {
            sql.append(" AND event.service_groups_id = " + event.getService_groups_id());
        }

        if (event.getDepartment() != null) {
            sql.append(" AND event.department = " + event.getDepartment());
        }

        if (event.getEvent_category_id() != null) {
            sql.append(" AND event.event_category_id = " + event.getEvent_category_id());
        }

        if (event.getPriority_id() != null) {
            sql.append(" AND event.priority_id = " + event.getPriority_id());
        }

        if (null != event.getCreate_time_start()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sql.append(" And event.create_time > '" + simpleDateFormat.format(event.getCreate_time_start()) + "' ");
        }
        if (null != event.getCreate_time_end()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sql.append(" And event.create_time < '" + simpleDateFormat.format(event.getCreate_time_end()) + "' ");
        }

        if (StringUtils.isNotEmpty(event.getExpenses())) {
            sql.append(" And event.expenses = '" + event.getExpenses() + "' ");
        }

        if (StringUtils.isNotEmpty(event.getTitle())) {
            sql.append(" and event.title like concat('%','" + event.getTitle() + "','%') ");
        }

        sql.append(" ORDER BY event.create_time DESC ");
        return sql.toString();
    }

    /**
     * 全部事件管理
     * 分页查询
     */
    public String toList(EventVo beanParam) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT e.*, ");
        sql.append(" userIdUser.`name` as user_id_name, ");
        sql.append(" createUser.`name` as create_user_name,esm.`name` as service_name, ");
        sql.append(" ec.`name` as category_name,handlerUser.`name` as handler_name," +
                "sef.first_name as urgency_degree_name,e.description ");
        sql.append(" FROM event e ");
        sql.append(" left join event_category ec on e.event_category_id = ec.id ");
        sql.append(" left join event_service_manage esm on esm.id = e.service_groups_id ");
        sql.append(" left join sys_user createUser on createUser.id = e.create_user ");
        sql.append(" left join sys_user userIdUser on userIdUser.id = e.user_id ");
        sql.append(" left join sys_user handlerUser on handlerUser.id = e.`handler` ");
        sql.append(" left join sla_event_first sef on sef.id = e.priority_id ");
        sql.append(" left join sla_event_urgent urg on urg.id = e.urgency_degree_id ");
        sql.append(" left join event_solution_type sty on sty.id = e.solve_type ");
//        sql.append(" left join sla_event_urgent  urg  on es.event_id = e.id ");

        sql.append(" where e.is_del=0 ");

        if (Base.empty(beanParam)) {
            return sql.toString();
        }
        if (Base.notEmpty(beanParam.getUser_type())) {
            if (1 == beanParam.getUser_type()) {
                sql.append(" and e.user_type = '" + beanParam.getUser_type() + "' ");
            } else {
                sql.append(" and (e.user_type is null or e.user_type='2') ");
            }
        }
        if (Base.notEmpty(beanParam.getBillStartTime())) {
            sql.append(" and e.create_time >= '" + beanParam.getBillStartTime() + "'");
        } else if (Base.notEmpty(beanParam.getBillEndTime())) {
            sql.append(" and e.create_time <= '" + beanParam.getBillEndTime() + "'");
        }
        if (StringUtils.isNotBlank(beanParam.getTitle())) {
            sql.append(" and e.title like '%" + beanParam.getTitle() + "%'");
        }

        //ID
        if (Base.notEmpty(beanParam.getId())) {
            sql.append(" and e.id = '" + beanParam.getId() + "' ");
        }
        //状态
        if (null != beanParam.getStatus_list() && !CollectionUtils.isEmpty(beanParam.getStatus_list())) {
            sql.append(" and e.`status`in (");
            for (String member : beanParam.getStatus_list()) {
                sql.append(" '" + member + "'");
                if (beanParam.getStatus_list().size() - 1 != beanParam.getStatus_list().indexOf(member)) {
                    sql.append(",");
                }
            }
            sql.append(")  ");
        }
        //开单人
        if (Base.notEmpty(beanParam.getCreate_user())) {
            sql.append(" and e.create_user = '" + beanParam.getCreate_user() + "' ");
        }
        //用户
        if (Base.notEmpty(beanParam.getUser_id())) {
            sql.append(" and e.user_id = '" + beanParam.getUser_id() + "' ");
        }
        //处理人
        if (Base.notEmpty(beanParam.getHandler())) {
            sql.append(" and e.`handler` = '" + beanParam.getHandler() + "' ");
        }

        //服务群组
        if (Base.notEmpty(beanParam.getService_groups_id())) {
            sql.append(" and e.service_groups_id = '" + beanParam.getService_groups_id() + "' ");
        }
        //部门
        if (Base.notEmpty(beanParam.getDepartment())) {
            sql.append(" and e.department = '" + beanParam.getDepartment() + "' ");
        }
        //来源
        if (Base.notEmpty(beanParam.getSource_id())) {
            sql.append(" and e.source_id = '" + beanParam.getSource_id() + "' ");
        }
        //请求类型
        if (Base.notEmpty(beanParam.getRequest_type())) {
            sql.append(" and e.request_type = '" + beanParam.getRequest_type() + "' ");
        }
        //工单类别
        if (Base.notEmpty(beanParam.getEvent_category_id())) {
            sql.append(" and e.event_category_id = '" + beanParam.getEvent_category_id() + "' ");
        }
        //紧急度
        if (Base.notEmpty(beanParam.getUrgency_degree_id())) {
            sql.append(" and urg.id = '" + beanParam.getUrgency_degree_id() + "' ");
        }
        //方案类别
        if (Base.notEmpty(beanParam.getProgramme_type())) {
            sql.append(" and e.programme_type = '" + beanParam.getProgramme_type() + "' ");
        }
        //满意度
        if (Base.notEmpty(beanParam.getEvent_satisfaction())) {
            sql.append(" and e.event_satisfaction = '" + beanParam.getEvent_satisfaction() + "' ");
        }
        //解决工程师id
        if (Base.notEmpty(beanParam.getSolve_user_id())) {
            sql.append(" and e.solve_user_id = '" + beanParam.getSolve_user_id() + "' ");
        }
        //关单时间
        if (Base.notEmpty(beanParam.getCloseStartTime())) {
            sql.append(" and e.closeTime >= '" + beanParam.getCloseStartTime() + "'");
        } else if (Base.notEmpty(beanParam.getCloseEndTime())) {
            sql.append(" and e.closeTime <= '" + beanParam.getCloseEndTime() + "'");
        }

        if (Base.notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0) {
            sql.append(" and e.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
        }
        // 费用开始时间
        if (Base.notEmpty(beanParam.getExpenses_start())) {
            sql.append(" and e.expenses >= '" + new BigDecimal(beanParam.getExpenses_start()) + "' ");
        }
        // 费用结束时间
        if (Base.notEmpty(beanParam.getExpenses_end())) {
            sql.append(" and e.expenses <= '" + new BigDecimal(beanParam.getExpenses_end()) + "' ");
        }
        // 响应人,
        if (Base.notEmpty(beanParam.getResponse_user())) {
            sql.append(" and e.response_user = '" + beanParam.getResponse_user() + "' ");
        }
        // 符合响应sla
        if (StringUtils.isNotBlank(beanParam.getMeetResponseSLA())) {
            sql.append(" and e.meetResponseSLA = '" + beanParam.getMeetResponseSLA() + "' ");
        }
        //优先级
        if (Base.notEmpty(beanParam.getPriority_id())) {
            sql.append(" and sef.id = '" + beanParam.getPriority_id() + "' ");
        }
        // 解决方式
        if (Base.notEmpty(beanParam.getSolve_type())) {
            sql.append(" and sty.id = '" + beanParam.getSolve_type() + "' ");
        }
        // 状态
        if (Base.notEmpty(beanParam.getStatus())) {
            sql.append(" and e.status = '" + beanParam.getStatus() + "' ");
        }

        //服务群组
        if (null != beanParam.getService_groups_type() && 1 == beanParam.getService_groups_type()) {
            if (beanParam.getService_groups_list().size() > 0) {
                String service_groups_id = "";
                for (Integer id : beanParam.getService_groups_list()) {
                    service_groups_id += id + ",";
                }
                String str = service_groups_id.substring(0, service_groups_id.length() - 1);
                sql.append(" and e.service_groups_id in (" + str + ") ");
            } else {
                sql.append(" and e.service_groups_id in ('') ");
            }
        }
        return sql.toString();
    }

    /**
     * 组内的
     *
     * @param beanParam
     * @return
     */
    public String toMyGroupList(EventVo beanParam, List<Integer> serviceGroups) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT e.*, ");
        sql.append(" userIdUser.`name` as user_id_name, ");
        sql.append(" createUser.`name` as create_user_name,esm.`name` as service_name, ");
        sql.append(" ec.`name` as category_name,handlerUser.`name` as handler_name,sef.first_name as urgency_degree_name ");
        sql.append(" FROM event e ");
        sql.append(" left join event_category ec on e.event_category_id = ec.id ");
        sql.append(" left join event_service_manage esm on esm.id = e.service_groups_id ");
        sql.append(" left join sys_user createUser on createUser.id = e.create_user ");
        sql.append(" left join sys_user userIdUser on userIdUser.id = e.user_id ");
        sql.append(" left join sys_user handlerUser on handlerUser.id = e.`handler` ");
        sql.append(" left join sla_event_first sef on sef.id = e.urgency_degree_id ");
        sql.append(" left join event_satisfaction es on es.event_id = e.id ");
        sql.append(" where e.is_del=0 ");

        if (null == beanParam) {
            return sql.toString();
        }

        //服务群组
        sql.append(" and e.service_groups_id in (" + Joiner.on(",").join(serviceGroups) + ") ");

//        if (null != beanParam.getBillStartTime()) {
//            sql.append(" and e.create_time >= '" + beanParam.getBillStartTime() + "'");
//        } else if (null != beanParam.getBillEndTime()) {
//            sql.append(" and e.create_time <= '" + beanParam.getBillEndTime() + "'");
//        }
        if(null != beanParam.getCreate_time_start()){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(beanParam.getCreate_time_start());
            sql.append(" and e.create_time >= '" + time + "'");
        }
        if(null != beanParam.getCreate_time_end()){
            // getCreate_end_time 日期增加一天
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(org.apache.commons.lang.time.DateUtils.addDays(beanParam.getCreate_time_end(), 1));
            sql.append(" and e.create_time <= '" + time  + "'");
        }
        if (StringUtils.isNotBlank(beanParam.getTitle())) {
            sql.append(" and e.title like '%" + beanParam.getTitle() + "%'");
        }

        if (StringUtils.isNotBlank(beanParam.getMeetResponseSLA())){
            sql.append(" and e.meetResponseSLA = " + beanParam.getMeetResponseSLA() );

        }

        if (null != beanParam.getId()) {
            sql.append(" and e.id = '" + beanParam.getId() + "' ");
        }
        if (null != beanParam.getStatus_list() && !CollectionUtils.isEmpty(beanParam.getStatus_list())) {
            sql.append(" and e.`status`in (");
            for (String member : beanParam.getStatus_list()) {
                sql.append(" '" + member + "'");
                if (beanParam.getStatus_list().size() - 1 != beanParam.getStatus_list().indexOf(member)) {
                    sql.append(",");
                }
            }
            sql.append(")  ");
        }
        if (StringUtils.isNotBlank(beanParam.getStatus())) {
            sql.append(" and e.status = '" + beanParam.getStatus() + "' ");
        }
        if(null != beanParam.getSolve_type()){
            sql.append(" and e.solve_type = '" + beanParam.getSolve_type() + "' ");
        }
        if (null != beanParam.getCreate_user()) {
            sql.append(" and e.create_user = '" + beanParam.getCreate_user() + "' ");
        }
        if (null != beanParam.getUser_id()) {
            sql.append(" and e.user_id = '" + beanParam.getUser_id() + "' ");
        }
        if (null != beanParam.getHandler()) {
            sql.append(" and e.`handler` = '" + beanParam.getHandler() + "' ");
        }

        if (null != beanParam.getDepartment()) {
            sql.append(" and e.department = '" + beanParam.getDepartment() + "' ");
        }
        if (null != beanParam.getSource_id()) {
            sql.append(" and e.source_id = '" + beanParam.getSource_id() + "' ");
        }
        if (null != beanParam.getRequest_type() && StringUtils.isNotBlank(beanParam.getRequest_type())) {
            sql.append(" and e.request_type = '" + beanParam.getRequest_type() + "' ");
        }
        if (null != beanParam.getEvent_category_id()) {
            sql.append(" and e.event_category_id = '" + beanParam.getEvent_category_id() + "' ");
        }
        if (null != beanParam.getUrgency_degree_id()) {
            sql.append(" and e.urgency_degree_id = '" + beanParam.getUrgency_degree_id() + "' ");
        }
        if (null != beanParam.getProgramme_type()) {
            sql.append(" and e.programme_type = '" + beanParam.getProgramme_type() + "' ");
        }
        if (null != beanParam.getEvent_satisfaction()) {
            sql.append(" and es.event_satisfaction = '" + beanParam.getEvent_satisfaction() + "' ");
        }
        if (null != beanParam.getSolve_user_id()) {
            sql.append(" and e.solve_user_id = '" + beanParam.getSolve_user_id() + "' ");
        }


        if (StringUtils.isNotBlank(beanParam.getCloseStartTime()) && StringUtils.isNotBlank(beanParam.getCloseEndTime())) {
            sql.append(" and e.closeTime between '" + beanParam.getCloseStartTime() + "' and '" + beanParam.getCloseEndTime() + "'");
        } else if (StringUtils.isNotBlank(beanParam.getCloseStartTime())) {
            sql.append(" and e.closeTime >= '" + beanParam.getCloseStartTime() + "'");
        } else if (StringUtils.isNotBlank(beanParam.getCloseEndTime())) {
            sql.append(" and e.closeTime <= '" + beanParam.getCloseEndTime() + "'");
        }


        if (Base.notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0) {
            sql.append(" and e.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
        }

        // 响应人,   符合响应sla,   符合解决sla
        if (Base.notEmpty(beanParam.getResponse_user())) {
            sql.append(" and e.response_user = '" + beanParam.getResponse_user() + "' ");
        }
        // 服务群组
        if (Base.notEmpty(beanParam.getService_groups_id())) {
            sql.append(" and e.service_groups_id = '" + beanParam.getService_groups_id() + "' ");
        }
        // 费用开始时间
        if (Base.notEmpty(beanParam.getExpenses_start())) {
            sql.append(" and e.expenses >= '" + new BigDecimal(beanParam.getExpenses_start()) + "' ");
        }
        // 费用结束时间
        if (Base.notEmpty(beanParam.getExpenses_end())) {
            sql.append(" and e.expenses <= '" + new BigDecimal(beanParam.getExpenses_end()) + "' ");
        }
        sql.append(" order by e.update_time,e.create_time desc ");
        return sql.toString();
    }

    /**
     * 我参与的
     *
     * @param beanParam
     * @return
     */
    public String toMyParticipationList(EventVo beanParam) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT e.*, ");
        sql.append(" userIdUser.`name` as user_id_name, ");
        sql.append(" createUser.`name` as create_user_name,esm.`name` as service_name, ");
        sql.append(" ec.`name` as category_name,handlerUser.`name` as handler_name,sef.first_name as urgency_degree_name ");
        sql.append(" FROM event e ");
        sql.append(" left join event_category ec on e.event_category_id = ec.id ");
        sql.append(" left join event_service_manage esm on esm.id = e.service_groups_id ");
        sql.append(" left join sys_user createUser on createUser.id = e.create_user ");
        sql.append(" left join sys_user userIdUser on userIdUser.id = e.user_id ");
        sql.append(" left join sys_user handlerUser on handlerUser.id = e.`handler` ");
        sql.append(" left join sla_event_first sef on sef.id = e.urgency_degree_id ");
//        sql.append(" left join event_satisfaction es on es.event_id = e.id ");
        sql.append(" where e.is_del=0 ");

        if (null == beanParam) {
            return sql.toString();
        }

        if (Base.notEmpty(beanParam.getBillStartTime())) {
            sql.append(" and  date_format(e.create_time,'%Y-%m-%d') >= '" + beanParam.getBillStartTime() + "'");
        }
        if (Base.notEmpty(beanParam.getBillEndTime())) {
            sql.append(" and date_format(e.create_time,'%Y-%m-%d') <= '" + beanParam.getBillEndTime() + "'");
        }
        if (StringUtils.isNotBlank(beanParam.getTitle())) {
            sql.append(" and e.title like '%" + beanParam.getTitle() + "%'");
        }

        if (null != beanParam.getId()) {
            sql.append(" and e.id = '" + beanParam.getId() + "' ");
        }
        if (null != beanParam.getStatus_list() && !CollectionUtils.isEmpty(beanParam.getStatus_list())) {
            sql.append(" and e.`status`in (");
            for (String member : beanParam.getStatus_list()) {
                sql.append(" '" + member + "'");
                if (beanParam.getStatus_list().size() - 1 != beanParam.getStatus_list().indexOf(member)) {
                    sql.append(",");
                }
            }
            sql.append(")  ");
        }
        if (StringUtils.isNotBlank(beanParam.getStatus())) {
            sql.append(" and e.status = '" + beanParam.getStatus() + "' ");
        }
        if(null != beanParam.getSolve_type()){
            sql.append(" and e.solve_type = '" + beanParam.getSolve_type() + "' ");
        }

        sql.append(" and ( ");
        //开单人
        sql.append(" e.create_user = '" + beanParam.getCreate_user() + "' ");
        //解决工程师
        sql.append(" or e.solve_user_id = '" + beanParam.getSolve_user_id() + "' ");
        //处理人
        sql.append(" or e.`handler` = '" + beanParam.getHandler() + "' ");

        sql.append(" ) ");

        if (null != beanParam.getUser_id()) {
            sql.append(" and e.user_id = '" + beanParam.getUser_id() + "' ");
        }

        // 费用开始时间
        if (Base.notEmpty(beanParam.getExpenses_start())) {
            sql.append(" and e.expenses >= '" + new BigDecimal(beanParam.getExpenses_start()) + "' ");
        }
        // 费用结束时间
        if (Base.notEmpty(beanParam.getExpenses_end())) {
            sql.append(" and e.expenses <= '" + new BigDecimal(beanParam.getExpenses_end()) + "' ");
        }
        if (beanParam.getPriority_id() != null) {
            sql.append(" AND e.priority_id = " + beanParam.getPriority_id());
        }
        if (null != beanParam.getService_groups_id()) {
            sql.append(" and e.service_groups_id = '" + beanParam.getService_groups_id() + "' ");
        }
        if (null != beanParam.getDepartment()) {
            sql.append(" and e.department = '" + beanParam.getDepartment() + "' ");
        }
        if (null != beanParam.getSource_id()) {
            sql.append(" and e.source_id = '" + beanParam.getSource_id() + "' ");
        }
        if (null != beanParam.getRequest_type() && StringUtils.isNotBlank(beanParam.getRequest_type())) {
            sql.append(" and e.request_type = '" + beanParam.getRequest_type() + "' ");
        }
        if (null != beanParam.getEvent_category_id()) {
            sql.append(" and e.event_category_id = '" + beanParam.getEvent_category_id() + "' ");
        }
        if (null != beanParam.getUrgency_degree_id()) {
            sql.append(" and e.urgency_degree_id = '" + beanParam.getUrgency_degree_id() + "' ");
        }
        if (null != beanParam.getProgramme_type()) {
            sql.append(" and e.programme_type = '" + beanParam.getProgramme_type() + "' ");
        }
        if (null != beanParam.getEvent_satisfaction()) {
            sql.append(" and e.event_satisfaction = '" + beanParam.getEvent_satisfaction() + "' ");
        }


        if (StringUtils.isNotBlank(beanParam.getCloseStartTime()) && StringUtils.isNotBlank(beanParam.getCloseEndTime())) {
            sql.append(" and e.closeTime between '" + beanParam.getCloseStartTime() + "' and '" + beanParam.getCloseEndTime() + "'");
        } else if (StringUtils.isNotBlank(beanParam.getCloseStartTime())) {
            sql.append(" and e.closeTime >= '" + beanParam.getCloseStartTime() + "'");
        } else if (StringUtils.isNotBlank(beanParam.getCloseEndTime())) {
            sql.append(" and e.closeTime <= '" + beanParam.getCloseEndTime() + "'");
        }

        if (null != beanParam.getResponse_user()) {
            sql.append(" and e.response_user = '" + beanParam.getResponse_user() + "' ");
        }
        if (Base.notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0) {
            sql.append(" and e.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
        }

        // 响应人,   符合响应sla,   符合解决sla

        sql.append(" order by e.update_time, e.create_time desc ");

        return sql.toString();
    }


    public String participationList(EventVo beanParam) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT e.*, ");
        sql.append(" userIdUser.`name` as user_id_name, ");
        sql.append(" createUser.`name` as create_user_name,esm.`name` as service_name, ");
        sql.append(" ec.`name` as category_name,handlerUser.`name` as handler_name,sef.first_name as urgency_degree_name ");
        sql.append(" FROM event e ");
        sql.append(" left join event_category ec on e.event_category_id = ec.id ");
        sql.append(" left join event_service_manage esm on esm.id = e.service_groups_id ");
        sql.append(" left join sys_user createUser on createUser.id = e.create_user ");
        sql.append(" left join sys_user userIdUser on userIdUser.id = e.user_id ");
        sql.append(" left join sys_user handlerUser on handlerUser.id = e.`handler` ");
        sql.append(" left join sla_event_first sef on sef.id = e.urgency_degree_id ");
        sql.append(" where e.is_del=0 ");
        if (null != beanParam.getUser_id()) {
            sql.append(" and e.user_id = '" + beanParam.getUser_id() + "' ");
        }
        return sql.toString();
    }

    public String statistics(String start_time, String end_time) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 1 THEN 1 ELSE 0 END), 0) dx, ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 2 THEN 1 ELSE 0 END), 0) cl, ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 3 THEN 1 ELSE 0 END), 0) zt, ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 4 THEN 1 ELSE 0 END), 0) wc, ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 5 THEN 1 ELSE 0 END), 0) dp, ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 6 THEN 1 ELSE 0 END), 0) gb, ");
        sql.append(" IFNULL( SUM( CASE e.`status` WHEN 7 THEN 1 ELSE 0 END), 0) cd, ");
        sql.append(" IFNULL( SUM( CASE e.`meetResponseSLA` WHEN 0 THEN 1 ELSE 0 END), 0) xyy, ");
        sql.append(" IFNULL( SUM( CASE e.`meetResponseSLA` WHEN 1 THEN 1 ELSE 0 END), 0) xyn, ");
        sql.append(" IFNULL( SUM( CASE e.`meetProcessingSLAs` WHEN 0 THEN 1 ELSE 0 END), 0) cly, ");
        sql.append(" IFNULL( SUM( CASE e.`meetProcessingSLAs` WHEN 1 THEN 1 ELSE 0 END), 0) cln, ");
        sql.append(" IFNULL( SUM( CASE WHEN e.`meetResponseSLA` = 0 and e.`meetProcessingSLAs` = 0 THEN 1 ELSE 0 END), 0) cs, ");
        sql.append(" IFNULL( SUM( e.numberOfRestarts), 0)  ck, ");
        sql.append(" IFNULL( SUM(e.expenses), 0) fy, ");
        sql.append(" COUNT( 1 ) gs ");
        sql.append(" FROM `event` e ");
        sql.append(" WHERE is_del = 0 ");
        if (StringUtils.isNotBlank(start_time)) {
            sql.append(" AND e.create_time >= '" + start_time + " 00:00:00' ");
        }
        if (StringUtils.isNotBlank(end_time)) {
            sql.append(" AND e.create_time <= '" + end_time + " 23:59:59' ");
        }
        return sql.toString();
    }
}
