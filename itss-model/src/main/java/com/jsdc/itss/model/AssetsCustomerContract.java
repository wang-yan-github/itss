package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户合同表(assets_customer_contract)
 *
 * @Author libin
 * @create 2022/3/7 15:22
 */
@Entity
@TableName("assets_customer_contract")
@Table(name = "assets_customer_contract")
@Data
public class AssetsCustomerContract extends Model<AssetsCustomerContract> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customer_name;//客户名称
    private String contract_name;//合同名称
    private Integer dept_id;//部门
    private String remark;//备注
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_time;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_time;//结束时间
    private String amount;//金额
    private Integer currency;//币种  字典：0人民币、1美元
    private String costing_threshold;//成本阈值
    private Integer status;//状态  字典：0执行中、1已结束

    //关联事件sla配置id
    private Integer sla_id;
    //关联问题sla配置id
    private Integer sla_question_id;
    //关联sla配置
    @Transient
    @TableField(exist = false)
    private String sla_event_name;
    @Transient
    @TableField(exist = false)
    private String sla_question_name;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date renew_time;//续约提醒时间

    @Transient
    @TableField(exist = false)
    private String deptName; //部门名称
    @Transient
    @TableField(exist = false)
    private String amount_count; //累计成本

    /**
     * 得到联系人信息
     */
    @Transient
    @TableField(exist = false)
    private List<AssetsContractContact> contractContacts = new ArrayList<>();

    /**
     * 得到关联想信息
     */
    @Transient
    @TableField(exist = false)
    private List<AssetsManage> assetsManages = new ArrayList<>();

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
    private String uploadedFiles;
    @Transient
    @TableField(exist = false)
    private List<FileManage> fileManageList = new ArrayList<>();
}
