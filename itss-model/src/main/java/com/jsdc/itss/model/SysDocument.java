package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * ClassName: SysDocument
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1014:58
 */
@Entity
@TableName("sys_document")
@Table(name = "sys_document")
@Data
public class SysDocument{

    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文件编号
     */
    private Integer document_num;

    /**
     * 文件名
     */
    private String document_name;

    /**
     * 拓展名
     */
    private String document_expand;

    /**
     * 上传日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date upload_time;

    /**
     * 文件大小
     */
    private String document_size;

    /**
     * 来源
     */
    private String origin;

    /**
     * 文件夹
     */
    private String file_folder;

    /**
     * 上传人ID
     */
    private Integer user_id;

    /**
     * ManagerKey
     */
    private String manager_key;

    /**
     * 关联单号
     */
    private String order_num;

    /**
     * 状态  0停用 1使用中
     */
    private Integer is_use;

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
