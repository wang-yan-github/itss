package com.jsdc.itss.dao;

import com.jsdc.core.base.BaseDao;
import com.jsdc.itss.model.PublishBasicInfo;
import org.springframework.stereotype.Repository;

/**
 * @Author wh
 * @create 2022-03-14 14:58:22
 */
@Repository
public class PublishBasicInfoDao extends BaseDao<PublishBasicInfo> {

    public String toList(PublishBasicInfo beanParam){
        StringBuilder sql=new StringBuilder();

        sql.append(" SELECT pb.*,su.NAME AS create_user_name ");
        sql.append(" FROM publish_basic_info pb LEFT JOIN sys_user su ON pb.create_user = su.id ");
        sql.append(" where pb.is_del='0' ");
        if (notEmpty(beanParam.getStatus())) {
            sql.append(" and pb.status = " + beanParam.getStatus() );
        }
        if (notEmpty(beanParam.getType())) {
            sql.append(" and pb.type = " + beanParam.getType() );
        }
        if (notEmpty(beanParam.getCreate_time_start())) {
            sql.append(" and pb.create_time >= '"+beanParam.getCreate_time_start()+" 00:00:00' ");
        }
        if (notEmpty(beanParam.getCreate_time_end())) {
            sql.append(" and pb.create_time <= '"+beanParam.getCreate_time_end()+" 23:59:59' ");
        }
        if (notEmpty(beanParam.getKeyword())) {
            sql.append(" and (pb.title like '%" + beanParam.getKeyword() +"%' or pb.issue_no like '%" + beanParam.getKeyword() +"%')" );
        }
        if (notEmpty(beanParam.getCreate_user())) {
            sql.append(" and pb.create_user = '" + beanParam.getCreate_user() +"'" );
        }
        sql.append(" order by pb.update_time,pb.create_time desc");

        return sql.toString();
    }



    /**
    *变更来源统计
    * Author wzn
    * Date 2022/11/2 14:49
    */
    public  String sourcesOfStatistics(){
        String sql  = "SELECT\n" +
                "\tcs.source_name name,count(1) value\n" +
                "FROM\n" +
                "\tchange_info  ci\n" +
                "left join change_source cs on cs.id = ci.change_source\n" +
                "GROUP BY\n" +
                "\tci.change_source" ;
        return sql ;
    }


    /**
    * 变更类别数量统计
    * Author wzn
    * Date 2022/11/2 15:17
    */
    public  String categoriesOfStatistical(){
        String sql  = "SELECT\n" +
                "\tcs.type_name name,count(1) value\n" +
                "FROM\n" +
                "\tchange_info  ci\n" +
                "left join change_type cs on cs.id = ci.change_classify\n" +
                "GROUP BY\n" +
                "\tci.change_type" ;
        return sql ;
    }


    /**
    * 变更类型数量统计
    * Author wzn
    * Date 2022/11/2 15:18
    */
    public  String typesOfStatistical(){
        String sql  = "SELECT\n" +
                "\tci.change_type name,count(1) value\n" +
                "FROM\n" +
                "\tchange_info  ci\n" +
                "\n" +
                "GROUP BY\n" +
                "\tci.change_type" ;
        return sql ;
    }


    /**
    *发布来源统计
    * Author wzn
    * Date 2022/11/3 10:05
    */
    public  String publishedSources(){
        String sql  = "SELECT\n" +
                "\tsu.name name,count(1) value\n" +
                "FROM\n" +
                "\tpublish_basic_info pbi\n" +
                "left join sys_user su on su.id = pbi.publisher\n" +
                "GROUP BY\n" +
                "\tpbi.publisher" ;
        return sql ;
    }


    /**
    *发布类别统计
    * Author wzn
    * Date 2022/11/3 10:07
    */
    public  String releaseTheCategory(){
        String sql  = "SELECT\n" +
                "\tsu.name name,count(1) value\n" +
                "FROM\n" +
                "\tpublish_basic_info pbi\n" +
                "left join publish_category_info su on su.id = pbi.category\n" +
                "GROUP BY\n" +
                "\tpbi.category" ;
        return sql ;
    }

    /**
    *发布类型数量统计
    * Author wzn
    * Date 2022/11/3 10:08
    */
    public  String publishType(){
        String sql  = "SELECT\n" +
                "\ttype NAME,\n" +
                "\tcount(1)\n" +
                "VALUE\n" +
                "\n" +
                "FROM\n" +
                "\tpublish_basic_info\n" +
                "GROUP BY\n" +
                "\ttype" ;
        return sql ;
    }
}
