package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsConfigReq;
import com.jsdc.itss.utils.DateUtils;
import com.jsdc.itss.vo.AssetsConfigReqVo;
import org.springframework.stereotype.Repository;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsConfigReqDao extends BaseDao<AssetsConfigReq> {


    public String toList(AssetsConfigReqVo beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT acr.*, sd.`name` department_name, ");
        sql.append("arc.request_name,su.`name` create_name, ");
        sql.append("sud.`name` deal_user_name,sdi.label ");
        sql.append("FROM assets_config_req acr ");
        sql.append("LEFT JOIN sys_department sd ON acr.dw_id = sd.id ");
        sql.append("LEFT JOIN assets_request_config arc ON acr.type = arc.id ");
        sql.append("LEFT JOIN sys_user su ON acr.create_user = su.id ");
        sql.append("LEFT JOIN sys_user sud ON acr.deal_userId = sud.id ");
        sql.append("LEFT JOIN sys_dict sdi ON acr.`status` = sdi.`value` ");
        sql.append("AND sdi.dict_type = 'asset_request_status' ");
        sql.append("where acr.is_del='0' ");
        if (notEmpty(beanParam.getType())) {
            sql.append(" AND acr.type=" + beanParam.getType());
        }
        if (notEmpty(beanParam.getStatus())) {
            sql.append(" AND acr.status=" + beanParam.getStatus());
        }
        if (notEmpty(beanParam.getCreate_name())) {
            sql.append(" AND su.`name` like '%" + beanParam.getCreate_name() + "%'");
        }
        if (notEmpty(beanParam.getStart_time())) {
            sql.append(" AND acr.create_time>= '" + DateUtils.toDateStr(beanParam.getStart_time(), "yyyy-MM-dd 00:00:00") + "'");
        }
        if (notEmpty(beanParam.getEnd_time())) {
            sql.append(" AND acr.create_time<='" + DateUtils.toDateStr(beanParam.getEnd_time(), "yyyy-MM-dd 23:59:59") + "'");
        }
        if (notEmpty(beanParam.getDeal_user_name())) {
            sql.append(" AND sud.`name` like '%" + beanParam.getDeal_user_name() + "%'");
        }
        if (notEmpty(beanParam.getDeal_start_time())) {
            sql.append(" AND acr.deal_time>='" + DateUtils.toDateStr(beanParam.getDeal_start_time(), "yyyy-MM-dd 00:00:00") + "'");
        }
        if (notEmpty(beanParam.getDeal_end_time())) {
            sql.append(" AND acr.deal_time<='" + DateUtils.toDateStr(beanParam.getDeal_end_time(), "yyyy-MM-dd 23:59:59") + "'");
        }

        if (notEmpty(beanParam.getKeyword())) {
            sql.append(" AND acr.req_NO like '%" + beanParam.getKeyword() + "%'");
        }

        //创建人
        if (notEmpty(beanParam.getCreate_user())) {
            sql.append(" AND acr.create_user=" + beanParam.getCreate_user());
        }
        //处理人
        if (notEmpty(beanParam.getDeal_userId())) {
            sql.append(" AND acr.deal_userId=" + beanParam.getDeal_userId());
        }


        return sql.toString();
    }

    public String getAssetsConfigReq(AssetsConfigReqVo beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT acr.*, sd.`name` department_name, ");
        sql.append("arc.request_name,su.`name` create_name, ");
        sql.append("sud.`name` deal_user_name,sdi.label ");
        sql.append("FROM assets_config_req acr ");
        sql.append("LEFT JOIN sys_department sd ON acr.dw_id = sd.id ");
        sql.append("LEFT JOIN assets_request_config arc ON acr.type = arc.id ");
        sql.append("LEFT JOIN sys_user su ON acr.create_user = su.id ");
        sql.append("LEFT JOIN sys_user sud ON acr.deal_userId = sud.id ");
        sql.append("LEFT JOIN sys_dict sdi ON acr.`status` = sdi.`value` ");
        sql.append("AND sdi.dict_type = 'asset_request_status' ");
        sql.append("where acr.is_del='0' ");
        if (notEmpty(beanParam.getId())) {
            sql.append(" AND acr.id=" + beanParam.getId());
        }
        if (notEmpty(beanParam.getType())) {
            sql.append(" AND acr.type=" + beanParam.getType());
        }
        if (notEmpty(beanParam.getStatus())) {
            sql.append(" AND acr.status=" + beanParam.getStatus());
        }
        if (notEmpty(beanParam.getCreate_user())) {
            sql.append(" AND acr.create_user=" + beanParam.getCreate_user());
        }
        if (notEmpty(beanParam.getStart_time())) {
            sql.append(" AND acr.create_time>" + beanParam.getStart_time());
        }
        if (notEmpty(beanParam.getEnd_time())) {
            sql.append(" AND acr.create_time<" + beanParam.getEnd_time());
        }
        if (notEmpty(beanParam.getDeal_userId())) {
            sql.append(" AND acr.deal_userId=" + beanParam.getDeal_userId());
        }
        if (notEmpty(beanParam.getDeal_start_time())) {
            sql.append(" AND acr.deal_time>" + beanParam.getDeal_start_time());
        }
        if (notEmpty(beanParam.getDeal_end_time())) {
            sql.append(" AND acr.deal_time<" + beanParam.getDeal_end_time());
        }


        return sql.toString();
    }
}
