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

@Entity
@TableName("file_manage")
@Table(name = "file_manage")
@Data
public class FileManage extends Model<FileManage> implements Serializable {
    /**
     * 编号
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文件名
     */
    private String file_name;

    /**
     * 文件路径
     */
    private String file_path;

    /**
     * 文件类型
     */
    private String file_type;

    /**
     * 文件大小
     */
    private String file_size;

    /**
     * 存储文件名
     */
    private String store_name;

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

    /**
     * 文件base64
     */
    @Transient
    @TableField(exist = false)
    private String base64;

    @Transient
    @TableField(exist = false)
    private String create_user_name;

    @Transient
    @TableField(exist = false)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String start_time;
    @Transient
    @TableField(exist = false)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String end_time;

    /**
     * 微信小程序 图片回显名称
     */
    @Transient
    @TableField(exist = false)
    private String name;
    @Transient
    @TableField(exist = false)
    private String url;
    @Transient
    @TableField(exist = false)
    private boolean isImage = true;
}
