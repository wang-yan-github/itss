package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 自动服务类别管理表
 *
 * @author wp
 */
@Entity
@TableName("event_service_category")
@Table(name = "event_service_category")
@Data
public class EventServiceCategory extends Model<EventServiceCategory> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 名称
    private String name;
    //3	状态0：禁用 1：启用
    private Integer is_use;

    //4	自动分派到服务群组ID
    private Integer group_id;
    @Transient
    @TableField(exist = false)
    private String group_name;//自动分派到服务群组

    //5	自动分派到工程师ID
    private Integer engineer_id;
    @Transient
    @TableField(exist = false)
    private String engineer_name;//自动分派到工程师

    //6	上级类别ID
    private Integer parent_type_id;
    @Transient
    @TableField(exist = false)
    private String parent_type_name;//上级类别名称

    //7	公司ID
    private Integer company_id;
    @Transient
    @TableField(exist = false)
    private String company_name;//公司名称

    //8	工单类别ID
    private Integer worker_order_id;
    @Transient
    @TableField(exist = false)
    private String bill_type_name;//工单类别名称

    //9	排序
    private Integer sort;
    //10 描述
    private String description;
    //11 服务表单默认标题
    private String from_title;
    //12 服务表单默认描述
    private String from_desc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    /**
     * 创建人id
     */
    private Integer create_user;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;

    /**
     * 更新人id
     */
    private Integer update_user;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    private String is_del;

    @Transient
    @TableField(exist = false)
    private List<EventServiceCategory> children;

    @Transient
    @TableField(exist = false)
    private List<EventServiceCategory> childMenus;

    @Transient
    @TableField(exist = false)
    private Integer useUser;


}
