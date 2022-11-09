package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.SysBackgroundPic;
import com.jsdc.itss.utils.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Repository
public class SysBackgroundPicDao extends BaseDao<SysBackgroundPic> {

    public String toList(SysBackgroundPic beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT * ").append(" FROM ").append("sys_background_pic");
        sql.append(" where is_del=0 ");
        if (StringUtils.isNotEmpty(beanParam.getTitle())){
            sql.append(" and title like concat('%','"+beanParam.getTitle()+"','%')");
        }
        return sql.toString();
    }
}
