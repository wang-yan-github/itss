package com.jsdc.itss.model.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import java.io.Serializable;

/**
 * ClassName: PublishApproveInfo
 * Description: 发布类别评审人信息
 * date: 2022/3/15 15:01
 *
 * @author wh
 **/
@Data
public class PublishApproveInfo extends Model<PublishApproveInfo> implements Serializable {
    //用户id
    private Integer id;
    //发布类别审批表id
    private Integer category_approve_id;
    //用户名ID
    private Integer user_id;
    //用户名
    private String user_name;
    //姓名
    private String name;
    //状态
    private Integer status;
    //电话
    private String telephone;
    //类型
    private String user_type;
    //排序
    private Integer sort;
}
