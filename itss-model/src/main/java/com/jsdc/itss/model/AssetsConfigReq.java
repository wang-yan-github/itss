package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 配置请求(assets_config_req)
 *
 * @Author libin
 * @create 2022/3/15 11:04
 */
@Entity
@TableName("assets_config_req")
@Table(name = "assets_config_req")
@Data
public class AssetsConfigReq extends Model<AssetsConfigReq> implements Serializable {

    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String req_NO;//   请求编号
    private Integer type;//   类型
    private Integer dw_id;//   部门
    private String content;//   内容
    private String result;//   处理结果
    private Integer relevance_NO;//   关联变更id
    private Integer status;//状态 1待处理 2已完成 3已撤销 4拒绝
    //private Date  create_time    ;//   创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deal_time;//   处理时间
    private Integer user_id;//   创建人
    private Integer deal_userId;//   处理人

    //14 备注 longtext 富文本编辑
    @Column(columnDefinition = "longtext")
    private String remark;


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
}
