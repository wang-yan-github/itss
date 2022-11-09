package com.jsdc.itss.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.AssetsStatus;
import com.jsdc.itss.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class AssetsStatusDao extends BaseDao<AssetsStatus> {


    public QueryWrapper<SysUser> queryByName(String name, String pass) {
        QueryWrapper<SysUser> queryHandler = new QueryWrapper<>();
        if (notEmpty(name)) {
            queryHandler.eq("login_name", name);
        }
        if (notEmpty(pass)) {
            queryHandler.eq("password", pass);
        }
        queryHandler.eq("is_del", "0");
        queryHandler.eq("status", "1");
        return queryHandler;
    }

}
