package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysRoleDao  extends BaseDao<SysRole> {

    public String exportRoleAuthority(){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT r.name ,a.name authority_name,a.path from sys_role r ");
        sql.append(" INNER JOIN  sys_role_authority ON r.id = sys_role_authority.role_id ");
        sql.append(" INNER JOIN  sys_authority a ON sys_role_authority.authority_id = a.id ");
        sql.append(" WHERE r.is_del = '0' ");
        sql.append(" AND sys_role_authority.is_del = '0'  ");
        sql.append(" AND a.is_del = '0' ");
        return sql.toString();
    }


    public String exportRoleWithUser(){
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT r.id,r.name,s.user_name,s.name real_name, s.mailbox,c.name company_name,d.name department_name,s.`status` ");
        sql.append("  FROM sys_role r  ");
        sql.append("  LEFT JOIN sys_user_role ON r.id = sys_user_role.role_id  ");
        sql.append("  LEFT JOIN sys_user s ON s.id =sys_user_role.user_id  ");
        sql.append("  LEFT JOIN sys_department d ON d.id = s.department_id  ");
        sql.append("  LEFT JOIN sys_company c ON d.company_id = c.id  ");
        sql.append("  WHERE r.is_del = '0' ");
        return sql.toString();
    }


    public String getSysAuthority(int roleId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" sys_authority.id , sys_authority.`name`,sys_authority.parent_id,sys_authority.`level`,sys_authority.`path` from sys_authority ");
        sql.append(" INNER JOIN sys_role_authority ");
        sql.append(" ON sys_authority.id = sys_role_authority.authority_id ");
        sql.append(" WHERE sys_role_authority.role_id = "+roleId);
        sql.append(" and sys_authority.is_del = '0' ");
        sql.append(" and sys_role_authority.is_del = '0' ");
        return sql.toString();
    }

    public String getByKnowRangeId(int knowledgeRangeId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" b.* from knowledge_range_info a ");
        sql.append(" INNER join sys_role b  ");
        sql.append(" on a.range_type='1' and a.fk_id=b.id ");
        sql.append(" where a.knowledge_range_id= "+knowledgeRangeId);
        sql.append(" and b.is_del='0' ");
        return sql.toString();
    }


    public String getRoleAndUser(int roleId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT sys_user.id,sys_user.`name`,sys_user.user_name, ");
        sql.append(" sys_user.`status`,sys_user.telephone,sys_user.user_type FROM sys_user ");
        sql.append(" INNER JOIN  sys_user_role ON sys_user.id = sys_user_role.user_id ");
        sql.append(" INNER JOIN sys_role ON sys_role.id =sys_user_role.role_id ");
        sql.append(" WHERE sys_role.id = "+roleId);
        sql.append(" AND sys_user.is_del = '0' ");
        sql.append(" AND sys_role.is_del = '0' ");
        sql.append(" AND sys_user_role.is_del ='0' ");
        return sql.toString();
    }

    public String getAuthorityByRole(Integer roleId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" 	sa.id ");
        sql.append(" FROM");
        sql.append(" 	sys_role r");
        sql.append(" 	LEFT JOIN sys_role_authority sra ON r.id = sra.role_id");
        sql.append(" 	LEFT JOIN sys_authority sa ON sra.authority_id = sa.id ");
        sql.append(" WHERE");
        sql.append(" 	r.id = " + roleId);
        sql.append(" 	AND r.is_del = '0' ");
        sql.append(" 	AND sra.is_del = '0' ");
        sql.append(" 	AND sa.is_del = '0'");
        return sql.toString();
    }

    public String saveBatch(Integer id , List<Integer> authorityIdList){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append("`sys_role_authority` ");
        sql.append("( `authority_id`, `create_time`, `create_user`, `is_del`, `role_id`, `update_time`, `update_user`) ");
        sql.append(" VALUES ");
        for(Integer temp: authorityIdList){
            sql.append("( ");
            sql.append(temp);
            sql.append(",");
            sql.append("'2022-07-23 09:14:44'").append(",");
            sql.append(1).append(",");
            sql.append("'0'").append(",");
            sql.append(id).append(",");
            sql.append("'2022-07-23 09:14:44'").append(",");
            sql.append(1).append("),");
        }
        String s = sql.toString().substring(0, sql.toString().length()-1)+";";
        return s;
    }
}
