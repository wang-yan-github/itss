package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.EventBillingDesignate;
import org.springframework.stereotype.Repository;

@Repository
public class EventBillingDesignateDao extends BaseDao<EventBillingDesignate> {

    public String selectByEventId(Integer eventId,String type){
        String sql = "SELECT\n" +
                "\tsu. NAME\n" +
                "FROM\n" +
                "\tevent_billing_designate ebd\n" +
                "LEFT JOIN sys_user su ON su.id = ebd.engineer_userid\n" +
                "WHERE \n" +
                "\tebd.service_management_id ="+eventId+" and ebd.type ='"+type+"'" ;
        return sql ;
    }

}
