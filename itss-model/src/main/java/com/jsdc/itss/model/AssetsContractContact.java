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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 合同联系人表(assets_contract_contact)
 *
 * @Author libin
 * @create 2022/3/7 15:18
 */

@Entity
@TableName("assets_contract_contact")
@Table(name = "assets_contract_contact")
@Data
public class AssetsContractContact extends Model<AssetsContractContact> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contact_name; //姓名
    private String company_name; //联系人公司
    private Integer company_id; // 联系公司id
    private Integer dept_id; //我司对接部门
    private Integer is_enable; //状态  状态 0禁用 1使用
    private String email; //电子邮箱
    private String phone; //手机
    private String telephone; //座机
    private Integer is_receive_notice; //是否接受通知   0接受通知  1不接受通知
    private String contact_address; //联系地址
    private String remark; //备注

    /**
     * 部门名称
     */
    @Transient
    @TableField(exist = false)
    private String deptName;

    @Transient
    @TableField(exist = false)
    private String deptId;

    /**
     * 排除的id
     */
    @Transient
    @TableField(exist = false)
    private List<Integer> excludeIds = new ArrayList<>();


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
    private List<Integer> ids;

}
