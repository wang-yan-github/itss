package com.jsdc.itss.dao;

import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsManage;
import com.jsdc.itss.vo.AssetsStatisticsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Repository
public class AssetsManageDao extends BaseDao<AssetsManage> {

    public String toList(AssetsManage beanParam) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT  " +
                " am.id,  " +
                " am.assets_number,  " +
                " am.assets_name,  " +
                " am.is_secrecy,  " +
                " am.dept_id, " +
                " am.assets_status, " +
                " it.status_name as itStatusName,  " +
                " AT.NAME AS assetsTypeName,  " +
                " AT.add_address,  " +
                " AT.edit_address,  " +
                " AT.list_address,  " +
                " AT.check_address,  " +
                " ac.NAME AS childAssetName,  " +
                " sd.name AS deptName, " +
                " acompany.company_name AS purchaseCompanyName, " +
                " sy.name  AS receiverName, " +
                " ass.status_name  AS assetsStatusName " +
                " FROM  " +
                " assets_manage am  " +
                " LEFT JOIN assets_type AT ON am.assets_type_id = AT.id  " +
                " LEFT JOIN assets_children ac ON ac.id = am.child_asset  " +
                " LEFT JOIN sys_department sd on am.dept_id = sd.id  " +
                " LEFT JOIN assets_company acompany ON am.purchase_company = acompany.id " +
                " LEFT JOIN sys_user sy ON sy.id = am.receiver " +
                " LEFT JOIN assets_status ass ON am.assets_status = ass.id " +
                " LEFT JOIN assets_it_status it on it.id = am.it_status");
        sql.append(" where am.is_del=0  ");
        if (empty(beanParam)) {
            sql.append(" order by am.create_time desc");
            return sql.toString();
        }
        if (notEmpty(beanParam.getKeyword())) {
//            sql.append(" and am.assets_name like '%").append(beanParam.getKeyword()).append("%'");
            sql.append(" and am.assets_number like '%").append(beanParam.getKeyword()).append("%'");
        }
        if (notEmpty(beanParam.getAssets_name())) {
            sql.append(" and am.assets_name like '%").append(beanParam.getAssets_name()).append("%'");
        }
        if (notEmpty(beanParam.getAssets_type_id())) {
            sql.append(" and am.assets_type_id =  ").append(beanParam.getAssets_type_id().intValue());
        }
        if (notEmpty(beanParam.getChild_asset())) {
            sql.append(" and am.child_asset =  ").append(beanParam.getChild_asset().intValue());
        }
        if (notEmpty(beanParam.getDept_id())) {
            sql.append(" and am.dept_id =  ").append(beanParam.getDept_id());
        }
        if (notEmpty(beanParam.getReceiver())) {
            sql.append(" and am.receiver =  ").append(beanParam.getReceiver());
        }
        if (notEmpty(beanParam.getPerson_liable())) {
            sql.append(" and am.person_liable like '%").append(beanParam.getPerson_liable()).append("%'");
        }
        if (notEmpty(beanParam.getPurchase_company())) {
            sql.append(" and am.purchase_company = ").append(beanParam.getPurchase_company());
        }
        if (notEmpty(beanParam.getUse_scope_id())) {
            sql.append(" and am.use_scope_id = ").append(beanParam.getUse_scope_id());
        }
        if (notEmpty(beanParam.getPurchase_date_start())) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date startTime = null;
//            try {
//                startTime = format.parse(beanParam.getPurchase_date_start());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            String startTime = "'"+beanParam.getPurchase_date_start()+" 00:00:00"+"'";
            sql.append(" and am.purchase_date >= ").append(startTime);
        }
        if (notEmpty(beanParam.getPurchase_date_end())) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date endTime = null;
//            try {
//                endTime = format.parse(beanParam.getPurchase_date_end());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            String endTime = "'"+beanParam.getPurchase_date_end()+ " 23:59:59"+"'";
            sql.append(" and am.purchase_date <= ").append(endTime);
        }
        //it_status
        if (notEmpty(beanParam.getIt_status())) {
            sql.append(" and am.it_status =  ").append(beanParam.getIt_status());
        }
        if (notEmpty(beanParam.getIs_print())) {
            sql.append(" and am.is_print = '").append(beanParam.getIs_print()).append("'");
        }
        if (Base.notEmpty(beanParam.getIds()) && beanParam.getIds().size() > 0) {
            sql.append(" and am.id in (" + Joiner.on(",").join(beanParam.getIds()) + ") ");
        }
        sql.append(" order by am.create_time desc");
        return sql.toString();
    }

    /**
     * 获取ci分组和分组所对应的配置
     *
     * @param bean
     * @return
     */
    public String clGroupConfig(AssetsManage bean) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  ");
        sql.append(" l.id as ciId ,cl.en_name as ciEnName ,cl.group_name as ciName ,GROUP_CONCAT(tp.`name`) as assetsTypes ,GROUP_CONCAT(tp.id) as assetsTypeIds   ");
        sql.append("FROM  ");
        sql.append(" assets_cl_group cl  ");
        sql.append(" LEFT JOIN assets_type tp ON cl.id = tp.group_id   ");
        sql.append(" group by  cl.id,cl.en_name,cl.group_name  ");
        sql.append(" ");
        return sql.toString();
    }

    /**
     * 获取分组数量
     *
     * @param bean
     * @return
     */
    public String getGroupSum(AssetsManage bean) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT assets_type_id ,COUNT(id) as assets_config_sum ");
        sql.append("FROM  ");
        sql.append("  assets_manage  ");
        sql.append(" WHERE 1=1 ");
        if (notEmpty(bean)) {
            if (notEmpty(bean.getAssets_type_id())) {
                sql.append(" AND  assets_type_id = ").append(bean.getAssets_type_id().intValue());
            }
        }
        sql.append("GROUP BY assets_type_id");
        return sql.toString();
    }

    /**
     * 配置项类别统计
     * @param assetsStatisticsVo
     * @return
     */
    public String assetsTypeStatistics(AssetsStatisticsVo assetsStatisticsVo){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" 	count( am.id ) count, ");
        sql.append(" 	atp.NAME typeName ");
        sql.append(" FROM");
        sql.append(" 	assets_manage am");
        sql.append(" 	LEFT JOIN assets_type atp ON am.assets_type_id = atp.id ");
        sql.append(" WHERE 1=1");
        if(null != assetsStatisticsVo){
            if(null != assetsStatisticsVo.getAssets_type_id()){
                sql.append(" AND atp.id = " + assetsStatisticsVo.getAssets_type_id());
            }
            if(null != assetsStatisticsVo.getChild_asset()){
                sql.append(" AND am.child_asset = " + assetsStatisticsVo.getChild_asset());
            }
            if(null != assetsStatisticsVo.getIt_status()){
                sql.append(" AND am.it_status = " + assetsStatisticsVo.getIt_status());
            }
            if(null != assetsStatisticsVo.getReceiver()){
                sql.append(" AND am.receiver = " + assetsStatisticsVo.getReceiver());
            }
            if(null != assetsStatisticsVo.getPerson_liable()){
                sql.append(" AND am.person_liable = " + assetsStatisticsVo.getPerson_liable());
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getAssets_name())){
                sql.append(" AND am.assets_name like '%" + assetsStatisticsVo.getAssets_name() + "%'");
            }
            if(null != assetsStatisticsVo.getDept_id()){
                sql.append(" AND am.dept_id = " + assetsStatisticsVo.getDept_id());
            }
            if(null != assetsStatisticsVo.getPurchase_company()){
                sql.append(" AND am.purchase_company = " + assetsStatisticsVo.getPurchase_company());
            }
            if(null != assetsStatisticsVo.getUse_scope_id()){
                sql.append(" AND am.use_scope_id = " + assetsStatisticsVo.getUse_scope_id());
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getIs_print())){
                sql.append(" AND am.is_print = '" + assetsStatisticsVo.getIs_print() + "'");
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getStart_time())){
                sql.append(" AND am.purchase_date >= '" + assetsStatisticsVo.getStart_time() + "'");
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getEnd_time())){
                sql.append(" AND am.purchase_date <= '" + assetsStatisticsVo.getEnd_time() + "'");
            }
            if(null != assetsStatisticsVo.getAssets_status()){
                sql.append(" AND am.assets_status = " + assetsStatisticsVo.getAssets_status());
            }
        }
        sql.append(" GROUP BY");
        sql.append(" 	atp.id");
        sql.append(" 	ORDER BY count desc");
        return sql.toString();
    }

    /**
     * cl分组统计
     * @param assetsStatisticsVo
     * @return
     */
    public String clGroupStatistics(AssetsStatisticsVo assetsStatisticsVo){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" 	count( am.id ) value,");
        sql.append(" 	acg.group_name name ");
        sql.append(" FROM");
        sql.append(" 	assets_manage am");
        sql.append(" 	LEFT JOIN assets_type atp ON am.assets_type_id = atp.id");
        sql.append(" 	LEFT JOIN assets_cl_group acg ON atp.group_id = acg.id ");
        sql.append(" WHERE 1=1");
        if(null != assetsStatisticsVo){
            if(null != assetsStatisticsVo.getAssets_type_id()){
                sql.append(" AND atp.id = " + assetsStatisticsVo.getAssets_type_id());
            }
            if(null != assetsStatisticsVo.getChild_asset()){
                sql.append(" AND am.child_asset = " + assetsStatisticsVo.getChild_asset());
            }
            if(null != assetsStatisticsVo.getIt_status()){
                sql.append(" AND am.it_status = " + assetsStatisticsVo.getIt_status());
            }
            if(null != assetsStatisticsVo.getReceiver()){
                sql.append(" AND am.receiver = " + assetsStatisticsVo.getReceiver());
            }
            if(null != assetsStatisticsVo.getPerson_liable()){
                sql.append(" AND am.person_liable = " + assetsStatisticsVo.getPerson_liable());
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getAssets_name())){
                sql.append(" AND am.assets_name like '%" + assetsStatisticsVo.getAssets_name() + "%'");
            }
            if(null != assetsStatisticsVo.getDept_id()){
                sql.append(" AND am.dept_id = " + assetsStatisticsVo.getDept_id());
            }
            if(null != assetsStatisticsVo.getPurchase_company()){
                sql.append(" AND am.purchase_company = " + assetsStatisticsVo.getPurchase_company());
            }
            if(null != assetsStatisticsVo.getUse_scope_id()){
                sql.append(" AND am.use_scope_id = " + assetsStatisticsVo.getUse_scope_id());
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getIs_print())){
                sql.append(" AND am.is_print = '" + assetsStatisticsVo.getIs_print() + "'");
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getStart_time())){
                sql.append(" AND am.purchase_date >= '" + assetsStatisticsVo.getStart_time() + "'");
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getEnd_time())){
                sql.append(" AND am.purchase_date <= '" + assetsStatisticsVo.getEnd_time() + "'");
            }
            if(null != assetsStatisticsVo.getAssets_status()){
                sql.append(" AND am.assets_status = " + assetsStatisticsVo.getAssets_status());
            }
        }
        sql.append(" GROUP BY");
        sql.append(" 	acg.id ");
        sql.append(" ORDER BY");
        sql.append(" 	value DESC");
        return sql.toString();
    }

    public String assetsDeptStatistics(AssetsStatisticsVo assetsStatisticsVo){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" 	count( am.id ) value,");
        sql.append(" CASE");
        sql.append(" 		");
        sql.append(" 		WHEN dept.NAME IS NULL THEN");
        sql.append(" 		'未知'   ELSE dept.name ");
        sql.append(" 	END  as name");
        sql.append(" 	FROM");
        sql.append(" 		assets_manage am");
        sql.append(" 		LEFT JOIN sys_department dept ON am.dept_id = dept.id ");
        sql.append(" WHERE 1=1");
        if(null != assetsStatisticsVo){
            if(null != assetsStatisticsVo.getAssets_type_id()){
                sql.append(" AND am.assets_type_id = " + assetsStatisticsVo.getAssets_type_id());
            }
            if(null != assetsStatisticsVo.getChild_asset()){
                sql.append(" AND am.child_asset = " + assetsStatisticsVo.getChild_asset());
            }
            if(null != assetsStatisticsVo.getIt_status()){
                sql.append(" AND am.it_status = " + assetsStatisticsVo.getIt_status());
            }
            if(null != assetsStatisticsVo.getReceiver()){
                sql.append(" AND am.receiver = " + assetsStatisticsVo.getReceiver());
            }
            if(null != assetsStatisticsVo.getPerson_liable()){
                sql.append(" AND am.person_liable = " + assetsStatisticsVo.getPerson_liable());
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getAssets_name())){
                sql.append(" AND am.assets_name like '%" + assetsStatisticsVo.getAssets_name() + "%'");
            }
            if(null != assetsStatisticsVo.getDept_id()){
                sql.append(" AND am.dept_id = " + assetsStatisticsVo.getDept_id());
            }
            if(null != assetsStatisticsVo.getPurchase_company()){
                sql.append(" AND am.purchase_company = " + assetsStatisticsVo.getPurchase_company());
            }
            if(null != assetsStatisticsVo.getUse_scope_id()){
                sql.append(" AND am.use_scope_id = " + assetsStatisticsVo.getUse_scope_id());
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getIs_print())){
                sql.append(" AND am.is_print = '" + assetsStatisticsVo.getIs_print() + "'");
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getStart_time())){
                sql.append(" AND am.purchase_date >= '" + assetsStatisticsVo.getStart_time() + "'");
            }
            if(StringUtils.isNotEmpty(assetsStatisticsVo.getEnd_time())){
                sql.append(" AND am.purchase_date <= '" + assetsStatisticsVo.getEnd_time() + "'");
            }
            if(null != assetsStatisticsVo.getAssets_status()){
                sql.append(" AND am.assets_status = " + assetsStatisticsVo.getAssets_status());
            }
        }
        sql.append(" 	GROUP BY");
        sql.append(" 		dept.id ");
        sql.append(" 	ORDER BY value");
        sql.append(" DESC");
        return sql.toString();
    }
}
