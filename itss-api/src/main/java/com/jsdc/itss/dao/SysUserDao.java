package com.jsdc.itss.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserDao extends BaseDao<SysUser> {

    public String exporUserExcel() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.user_name,u.`status`,u.user_type,u.mailbox,u.telephone,u.name,u.landline, ");
        sql.append(" u.is_vip ,u.position , u.last_login_date, d.name department_name , g.name event_group_name, ");
        sql.append(" r.name role_name,u.is_emial,u.is_wechat,u.location,u.create_time,u.update_time from sys_user u ");
        sql.append(" LEFT JOIN sys_department d ON u.department_id = d.id ");
        sql.append(" LEFT JOIN sys_user_event_group ON u.id = sys_user_event_group.user_id ");
        sql.append(" LEFT JOIN event_service_manage g ON sys_user_event_group.event_group_id = g.id ");
        sql.append(" LEFT JOIN sys_user_role ON u.id =sys_user_role.user_id ");
        sql.append(" LEFT JOIN sys_role r ON sys_user_role.role_id = r.id ");
        sql.append(" WHERE u.is_del = '0' ");
        return sql.toString();

    }

    public String exporUserRoleExcel() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT sys_user.user_name ,sys_user.name,sys_user.mailbox,sys_user.location,sys_department.`name` dName,sys_user.`status` , ");
        sql.append(" sys_user_role.role_id rId,sys_role.`name` rName FROM sys_user ");
        sql.append(" INNER JOIN sys_department ON sys_user.department_id = sys_department.id ");
        sql.append(" INNER JOIN sys_user_role ON sys_user.id = sys_user_role.user_id ");
        sql.append(" INNER JOIN sys_role ON sys_user_role.role_id = sys_role.id ");
        sql.append(" WHERE sys_user.is_del = '0' ");
        sql.append(" AND sys_user_role.is_del = '0' ");
        sql.append(" AND sys_role.is_del = '0' ");
        return sql.toString();
    }


    public String getUserInfoByPage(SysUser sysUser) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT sys_user.id,sys_user.position,sys_user.location,sys_user.landline," +
                "sys_user.department_id,user_name,sys_user.name,sys_department.`name` departmentName," +
                "sys_company.name as companyName,telephone,mailbox,user_type,location as address," +
                "wechat_nickname,status,last_login_date,sys_user.create_time ");
        sql.append(" from sys_user LEFT JOIN sys_department on sys_user.department_id = sys_department.id ");
        sql.append("  LEFT JOIN sys_company on sys_department.company_id = sys_company.id ");
//        sql.append(" LEFT JOIN  sys_user_event_group on sys_user_event_group.user_id = sys_user.id ");
        sql.append(" WHERE sys_user.is_del = '0' ");
        if (sysUser != null) {
            if (StringUtils.isNotEmpty(sysUser.getName())) {
                sql.append(" AND sys_user.name  like concat ( '%','" + sysUser.getName() + "','%') ");
            }
            if (StringUtils.isNotEmpty(sysUser.getMailbox())) {
                sql.append(" AND sys_user.mailbox like '%" + sysUser.getMailbox() + "%' ");
            }
            if (sysUser.getDepartment_id() != null) {
                sql.append(" AND sys_user.department_id = " + sysUser.getDepartment_id());
            }
            if (StringUtils.isNotEmpty(sysUser.getDepartmentName())) {
                sql.append(" AND sys_department.name like '%" + sysUser.getDepartmentName() + "%' ");
            }
            if (sysUser.getStatus() != null) {
                sql.append(" AND sys_user.status = " + sysUser.getStatus());
            }
            if (StringUtils.isNotEmpty(sysUser.getUser_name())) {
                sql.append(" AND sys_user.user_name like concat ( '%','" + sysUser.getUser_name() + "','%') ");
            }
            if (StringUtils.isNotEmpty(sysUser.getUser_type())) {
                sql.append(" AND sys_user.user_type= '" + sysUser.getUser_type() + "' ");
            }
            if (sysUser.getIs_technology() != null) {
                sql.append(" AND sys_user.is_technology = " + sysUser.getIs_technology());
            }
            if (Base.notEmpty(sysUser.getIds()) && sysUser.getIds().size() > 0) {
                sql.append(" and sys_user.id in (" + Joiner.on(",").join(sysUser.getIds()) + ") ");
            }
//            if (Base.notEmpty(sysUser.getEventServiceGroupId())){
//                sql.append(" and sys_user_event_group.event_group_id = " + sysUser.getEventServiceGroupId() );
//            }
        }
        sql.append(" order by sys_user.id ASC ");
        return sql.toString();
    }

    public String getUserPage(SysUser sysUser) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT sys_user.id,sys_user.position,sys_user.location,sys_user.landline," +
                "sys_user.department_id,user_name,sys_user.name,sys_department.`name` departmentName," +
                "sys_company.name as companyName,telephone,mailbox,user_type,location as address," +
                "wechat_nickname,status,last_login_date,sys_user.create_time ");
        sql.append(" from sys_user LEFT JOIN sys_department on sys_user.department_id = sys_department.id ");
        sql.append("  LEFT JOIN sys_company on sys_department.company_id = sys_company.id ");
//        sql.append(" LEFT JOIN  sys_user_event_group on sys_user_event_group.user_id = sys_user.id ");
        sql.append(" WHERE sys_user.is_del = '0' ");
        if (sysUser != null) {
            if (StringUtils.isNotEmpty(sysUser.getName())) {
                sql.append(" AND sys_user.name  like concat ( '%','" + sysUser.getName() + "','%') ");
            }
            if (StringUtils.isNotEmpty(sysUser.getMailbox())) {
                sql.append(" AND sys_user.mailbox like '%" + sysUser.getMailbox() + "%' ");
            }
            if (sysUser.getDepartment_id() != null) {
                sql.append(" AND sys_user.department_id = " + sysUser.getDepartment_id());
            }
            if (StringUtils.isNotEmpty(sysUser.getDepartmentName())) {
                sql.append(" AND sys_department.name like '%" + sysUser.getDepartmentName() + "%' ");
            }
            if (sysUser.getStatus() != null) {
                sql.append(" AND sys_user.status = " + sysUser.getStatus());
            }
            if (StringUtils.isNotEmpty(sysUser.getUser_name())) {
                sql.append(" AND sys_user.user_name like concat ( '%','" + sysUser.getUser_name() + "','%') ");
            }
            if (StringUtils.isNotEmpty(sysUser.getUser_type())) {
                sql.append(" AND sys_user.user_type= '" + sysUser.getUser_type() + "' ");
            }
            if (sysUser.getIs_technology() != null) {
                sql.append(" AND sys_user.is_technology = " + sysUser.getIs_technology());
            }
            if (Base.notEmpty(sysUser.getIds()) && sysUser.getIds().size() > 0) {
                sql.append(" and sys_user.id in (" + Joiner.on(",").join(sysUser.getIds()) + ") ");
            }
//            if (Base.notEmpty(sysUser.getEventServiceGroupId())){
//                sql.append(" and sys_user_event_group.event_group_id = " + sysUser.getEventServiceGroupId() );
//            }
        }
        sql.append(" order by sys_user.id ASC ");
        return sql.toString();
    }


    public String getSysAuthority(int userId) {
        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT ");
//        sql.append(" sys_authority.`id`,sys_authority.`name`,sys_authority.`code`,sys_authority.parent_id,sys_authority.`level`,sys_authority.`path` from sys_authority ");
//        sql.append(" INNER JOIN sys_user_authority ");
//        sql.append(" ON sys_authority.id = sys_user_authority.authority_id ");
//        sql.append(" WHERE sys_user_authority.user_id = " + userId);
//        sql.append(" and sys_authority.is_del = '0' ");
        sql.append(" SELECT");
        sql.append(" 	DISTINCT sa.*");
        sql.append(" FROM");
        sql.append(" 	sys_user u");
        sql.append(" 	LEFT JOIN sys_user_role ur ON u.id = ur.user_id");
        sql.append(" 	LEFT JOIN sys_role_authority ra ON ur.role_id = ra.role_id");
        sql.append(" 	LEFT JOIN sys_authority sa ON ra.authority_id = sa.id");
        sql.append(" 	WHERE sa.id is not null");
        sql.append(" 	AND u.id = " + userId);
        sql.append("    AND ur.is_del = '0'");
        sql.append("    AND u.is_del = '0'");
        sql.append("    AND sa.is_del = '0'");
        sql.append("    AND ra.is_del = '0' ");
        sql.append(" 	order by sa.parent_id,sa.sort");
        return sql.toString();
    }



    public String getSysAuthorityOther(int roleId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT sys_authority.* FROM sys_authority ");
        sql.append(" LEFT JOIN  sys_role_authority ON sys_role_authority.authority_id = sys_authority.id ");
        sql.append(" where sys_role_authority.role_id = "+roleId);
        sql.append(" and sys_role_authority.is_del = '0' ");
        sql.append(" and sys_authority.is_del = '0' ");
        return sql.toString();
    }


    public String getUserDepartmentRole() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT sys_user.user_name,sys_user.`name`,sys_user.mailbox,location,sys_user.`status`,sys_department.`name` dName,sys_role.id rId,sys_role.`name` rName from `sys_user` ");
        sql.append(" INNER JOIN sys_department on sys_user.department_id = sys_department.id ");
        sql.append(" INNER JOIN sys_user_role ON sys_user.id =sys_user_role.user_id ");
        sql.append(" INNER JOIN sys_role ON sys_user_role.role_id  =sys_role.id ");
        sql.append(" WHERE sys_user.is_del = '0' ");
        sql.append(" AND sys_user_role.is_del = '0' ");
        sql.append(" AND sys_role.is_del = '0' ");
        sql.append(" AND sys_department.is_del = '0' ");
        return sql.toString();
    }


    public QueryWrapper<SysUser> queryByName(String name, String pass) {
        QueryWrapper<SysUser> queryHandler = new QueryWrapper<>();
        if (Base.notEmpty(name)) {
            queryHandler.eq("user_name", name);
        }
        if (notEmpty(pass)) {
            queryHandler.eq("pass", pass);
        }
        queryHandler.eq("is_del", "0");
        queryHandler.eq("status", "1");
        return queryHandler;
    }

    public String getRoles(int userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" sr.id,");
        sql.append(" sr.role_name");
        sql.append(" FROM");
        sql.append(" sys_user u");
        sql.append(" LEFT JOIN sys_user_role r ON u.id = r.user_id");
        sql.append(" LEFT JOIN sys_role sr ON sr.id = r.role_id");
        sql.append(" where 1=1");
        sql.append(" and u.id = " + userId);
        sql.append(" and r.is_del = '0' ");
        return sql.toString();
    }
}
