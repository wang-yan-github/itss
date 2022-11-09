package com.jsdc.itss.dao;

import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.ChangeInfo;
import com.jsdc.itss.utils.DateUtils;
import com.jsdc.itss.vo.ChangeVo;
import org.springframework.stereotype.Repository;

/**
 * ClassName: ChangeInfoDao
 * Description:
 * date: 2022/3/14 9:27
 *
 * @author bn
 */
@Repository
public class ChangeInfoDao extends BaseDao<ChangeInfo> {

    public String toList(ChangeVo changeVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ci.*, ");
        sql.append("su.`name` approve_name, ");
        sql.append("ct.type_name classify_name,cim.start_time implement_start_time  ");
        sql.append("FROM change_info ci ");
        sql.append("LEFT JOIN change_type ct ON ci.change_classify = ct.id ");
        sql.append("LEFT JOIN change_programme_approve cpa ON ci.id=cpa.change_id ");
        sql.append("LEFT JOIN change_implement cim on ci.id=cim.change_id ");
        sql.append("LEFT JOIN change_nodes cn ON ci.id=cn.change_id and cn.is_del='0' ");
        sql.append("LEFT JOIN sys_user su ON ci.handler_user=su.id ");
        if (notEmpty(changeVo.getData_flag()) && changeVo.getData_flag().equals("3")) {
            //创建人、审批人、方案拟定人、方案审核人、实施人、实施后评审人、关单人其中包含我的数据
            sql.append("LEFT JOIN change_programme_draft cpd ON ci.id=cpd.change_id and cpd.is_del='0' ");
            sql.append("LEFT JOIN change_implement_approve cia ON ci.id = cia.change_id AND cia.is_del = '0' ");
            sql.append("LEFT JOIN change_close cc ON ci.id = cc.change_id AND cc.is_del = '0' ");
        }
        sql.append("WHERE ci.is_del = '0' ");
        if (notEmpty(changeVo.getKeyword())) {
            sql.append(" AND (ci.title like '%" + changeVo.getKeyword() + "%' ");
            sql.append(" or ci.change_no like '%" + changeVo.getKeyword() + "%' ");
            sql.append(" ) ");
        }
        if (notEmpty(changeVo.getCreate_start_time())) {
            sql.append(" AND ci.create_time>= '" + DateUtils.toDateStr(changeVo.getCreate_start_time(), "yyyy-MM-dd 00:00:00") + "'");
        }
        if (notEmpty(changeVo.getCreate_end_time())) {
            sql.append(" AND ci.create_time<= '" + DateUtils.toDateStr(changeVo.getCreate_end_time(), "yyyy-MM-dd 23:59:59") + "'");
        }
        if (notEmpty(changeVo.getStatus())) {
            sql.append(" AND ci.status='" + changeVo.getStatus() + "'");
        }
        if (notEmpty(changeVo.getChange_classify())) {
            sql.append(" AND ci.change_classify='" + changeVo.getChange_classify() + "'");
        }
        if (notEmpty(changeVo.getChange_type())) {
            sql.append(" AND ci.change_type='" + changeVo.getChange_type() + "'");
        }
        if (notEmpty(changeVo.getTitle())) {
            sql.append(" AND ci.title like '%" + changeVo.getTitle() + "%'");
        }
        if (notEmpty(changeVo.getChange_source())) {
            sql.append(" AND ci.change_source ='" + changeVo.getChange_source() + "'");
        }
        if (notEmpty(changeVo.getCn_approve_user())) {
            sql.append(" AND cn.approve_user = " + changeVo.getCn_approve_user());
        }
        if (notEmpty(changeVo.getCpa_approve_user())) {
            sql.append(" AND cpa.approve_user = " + changeVo.getCpa_approve_user());
        }

        if (notEmpty(changeVo.getStart_time())) {
            sql.append(" AND ci.start_time >= '" + DateUtils.toDateStr(changeVo.getStart_time(), "yyyy-MM-dd 00:00:00") + "'");
//            sql.append(" AND ci.end_time>='" + DateUtils.toDateStr(changeVo.getStart_time(), "yyyy-MM-dd 00:00:00") + "'");
        }
        if (notEmpty(changeVo.getEnd_time())) {
            sql.append(" AND ci.end_time<='" + DateUtils.toDateStr(changeVo.getEnd_time(), "yyyy-MM-dd 23:59:59") + "'");
//            sql.append(" AND ci.start_time<='" + DateUtils.toDateStr(changeVo.getEnd_time(), "yyyy-MM-dd 59:59:59") + "'");
        }

        if (notEmpty(changeVo.getImplement_start_time())) {
            sql.append(" AND cim.start_time >= '" + DateUtils.toDateStr(changeVo.getImplement_start_time(), "yyyy-MM-dd 00:00:00") + "'");
//            sql.append(" AND cim.end_time>='" + DateUtils.toDateStr(changeVo.getImplement_start_time(), "yyyy-MM-dd 00:00:00") + "'");
        }

        if (notEmpty(changeVo.getImplement_end_time())) {
            sql.append(" AND cim.end_time<='" + DateUtils.toDateStr(changeVo.getImplement_end_time(), "yyyy-MM-dd 23:59:59") + "'");
//            sql.append(" AND cim.start_time<='" + DateUtils.toDateStr(changeVo.getImplement_end_time(), "yyyy-MM-dd 59:59:59") + "'");
        }

        if (notEmpty(changeVo.getCreate_user())) {
            sql.append(" AND ci.create_user=" + changeVo.getCreate_user());
        }
        if (Base.notEmpty(changeVo.getIds()) && changeVo.getIds().size() > 0) {
            sql.append(" and ci.id in (" + Joiner.on(",").join(changeVo.getIds()) + ") ");
        }
        // 1.我的变更列表分页 2.变更管理列表分页 3我参与的
        if (notEmpty(changeVo.getData_flag()) && changeVo.getData_flag().equals("3")) {
           //创建人、审批人、方案拟定人、方案审核人、实施人、实施后评审人、关单人其中包含我的数据
            sql.append(" and (ci.create_user="+changeVo.getCanyu_user_id());
            sql.append(" OR cn.approve_user ="+changeVo.getCanyu_user_id());
            sql.append(" OR cpd.programme_user ="+changeVo.getCanyu_user_id());
            sql.append(" OR cpa.approve_user ="+changeVo.getCanyu_user_id());
            sql.append(" OR cim.implement_user ="+changeVo.getCanyu_user_id());
            sql.append(" OR cia.after_implement_user ="+changeVo.getCanyu_user_id());
            sql.append(" OR cc.close_user="+changeVo.getCanyu_user_id());
            sql.append(")");
        }
        sql.append(" order by ci.create_time desc");
        return sql.toString();
    }

    public String toNeedList(ChangeVo changeVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ci.*, ");
        sql.append("su.`name` approve_name, ");
        sql.append("ct.type_name classify_name ");
        sql.append("FROM change_info ci ");
        sql.append("LEFT JOIN change_type ct ON ci.change_classify = ct.id ");
        sql.append("LEFT JOIN change_programme_approve cpa ON ci.id=cpa.change_id ");
        sql.append("LEFT JOIN change_implement cim on ci.id=cim.change_id ");
        sql.append("LEFT JOIN change_nodes cn ON ci.id=cn.change_id ");
        sql.append("LEFT JOIN sys_user su ON ci.approve_user=su.id ");
        sql.append("WHERE ci.is_del = '0' ");
        if (notEmpty(changeVo.getKeyword())) {
            sql.append(" AND (ci.title like '%" + changeVo.getKeyword() + "%' ");
            sql.append(" or ci.change_no like '%" + changeVo.getKeyword() + "%' ");
            sql.append(" ) ");
        }
        if (notEmpty(changeVo.getCreate_start_time())) {
            sql.append(" AND ci.create_time>= '" + DateUtils.toDateStr(changeVo.getCreate_start_time(), "yyyy-MM-dd 00:00:00") + "'");
        }
        if (notEmpty(changeVo.getCreate_end_time())) {
            sql.append(" AND ci.create_time<= '" + DateUtils.toDateStr(changeVo.getCreate_end_time(), "yyyy-MM-dd 59:59:59") + "'");
        }
        if (notEmpty(changeVo.getLs_status())) {
            sql.append(" AND ci.status not in " + changeVo.getLs_status() + "");
        }
        if (notEmpty(changeVo.getStatus())) {
            sql.append(" AND ci.status='" + changeVo.getStatus() + "'");
        }
        if (notEmpty(changeVo.getChange_classify())) {
            sql.append(" AND ci.change_classify='" + changeVo.getChange_classify() + "'");
        }
        if (notEmpty(changeVo.getChange_type())) {
            sql.append(" AND ci.change_type='" + changeVo.getChange_type() + "'");
        }
        if (notEmpty(changeVo.getTitle())) {
            sql.append(" AND ci.title like '%" + changeVo.getTitle() + "%'");
        }
        if (notEmpty(changeVo.getChange_source())) {
            sql.append(" AND ci.change_source ='" + changeVo.getChange_source() + "'");
        }
        if (notEmpty(changeVo.getCn_approve_user())) {
            sql.append(" AND cn.approve_user = " + changeVo.getCn_approve_user());
        }
        if (notEmpty(changeVo.getCpa_approve_user())) {
            sql.append(" AND cpa.approve_user = " + changeVo.getCpa_approve_user());
        }

        if (notEmpty(changeVo.getStart_time())) {
            sql.append(" AND ci.start_time >= '" + DateUtils.toDateStr(changeVo.getStart_time(), "yyyy-MM-dd 00:00:00") + "'");
            sql.append(" AND ci.end_time>='" + DateUtils.toDateStr(changeVo.getStart_time(), "yyyy-MM-dd 00:00:00") + "'");
        }
        if (notEmpty(changeVo.getEnd_time())) {
            sql.append(" AND ci.end_time<='" + DateUtils.toDateStr(changeVo.getEnd_time(), "yyyy-MM-dd 59:59:59") + "'");
            sql.append(" AND ci.start_time<='" + DateUtils.toDateStr(changeVo.getEnd_time(), "yyyy-MM-dd 59:59:59") + "'");
        }

        if (notEmpty(changeVo.getImplement_start_time())) {
            sql.append(" AND cim.start_time >= '" + DateUtils.toDateStr(changeVo.getImplement_start_time(), "yyyy-MM-dd 00:00:00") + "'");
            sql.append(" AND cim.end_time>='" + DateUtils.toDateStr(changeVo.getImplement_start_time(), "yyyy-MM-dd 00:00:00") + "'");
        }

        if (notEmpty(changeVo.getImplement_end_time())) {
            sql.append(" AND cim.end_time<='" + DateUtils.toDateStr(changeVo.getImplement_end_time(), "yyyy-MM-dd 59:59:59") + "'");
            sql.append(" AND cim.start_time<='" + DateUtils.toDateStr(changeVo.getImplement_end_time(), "yyyy-MM-dd 59:59:59") + "'");
        }

        if (notEmpty(changeVo.getCreate_user())) {
            sql.append(" AND ci.create_user=" + changeVo.getCreate_user());
        }

        if (notEmpty(changeVo.getHandler_user())) {
            sql.append(" AND ci.handler_user=" + changeVo.getHandler_user());
        }

        sql.append(" order by ci.create_time desc");
        return sql.toString();


    }

    public String getById(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ci.*, ");
        sql.append("su.`name` approve_name, ");
        sql.append("ct.type_name classify_name ");
        sql.append("FROM change_info ci ");
        sql.append("LEFT JOIN change_type ct ON ci.change_classify = ct.id ");
        sql.append("LEFT JOIN sys_user su ON ci.approve_user=su.id ");
        sql.append("WHERE ci.is_del = '0' ");
        if (notEmpty(id)) {
            sql.append(" AND ci.id = " + id);
        }
        return sql.toString();
    }


    public String getChange(ChangeInfo changeInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ci.*, ");
        sql.append("ct.type_name classify_name ");
        sql.append("FROM change_info ci ");
        sql.append("LEFT JOIN change_type ct ON ci.change_classify = ct.id ");
        sql.append("WHERE ci.is_del = '0' ");
        if (notEmpty(changeInfo.getId())) {
            sql.append(" AND ci.id=" + changeInfo.getId());
        }
        return sql.toString();
    }


    public String toExcelList(ChangeVo changeVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ci.*, ");
        sql.append("ct.type_name classify_name, ");
        sql.append("cpd.change_programme cpd_change_programme,cpd.fallback_plan cpd_fallback_plan, ");
        sql.append("cpd.risk_assessment cpd_risk_assessment,cpd.programme_user cpd_programme_user, ");
        sql.append("cc.close_user cc_close_user,cc.close_time cc_close_time, ");
        sql.append("cpa.approve_user cpa_approve_user,cpa.approve_remark cpa_approve_remark,cpa.approve_time cpa_approve_time, ");
        sql.append("cim.implement_user cim_implement_user,cim.start_time cim_start_time,cim.implement_remark cim_implement_remark, ");
        sql.append("cia.after_implement_remark cia_after_implement_remark, ");
        sql.append("cia.after_implement_time cia_after_implement_time, ");
        sql.append("cia.after_implement_user cia_after_implement_user ");
        sql.append(" ");
        sql.append(" ");
        sql.append(" ");
        sql.append(" ");
        sql.append("from change_info ci ");
        sql.append("LEFT JOIN change_type ct ON ci.change_classify = ct.id  ");
        sql.append("LEFT JOIN change_programme_draft cpd ON ci.id = cpd.change_id ");
        sql.append("LEFT JOIN change_close cc ON ci.id = cc.change_id ");
        sql.append("LEFT JOIN change_programme_approve cpa ON ci.id=cpa.change_id ");
        sql.append("LEFT JOIN change_implement cim ON ci.id=cim.change_id ");
        sql.append("LEFT JOIN change_implement_approve cia ON ci.id = cia.change_id ");
        sql.append(" ");
        sql.append(" ");

        return sql.toString();
    }

}
