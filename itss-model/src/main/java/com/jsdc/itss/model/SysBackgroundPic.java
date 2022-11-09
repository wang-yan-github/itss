package com.jsdc.itss.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SysBackgroundPic
 * Description:
 *
 * @author zhangdequan
 * @date 2022/3/1013:57
 */
@Entity
@TableName("sys_background_pic")
@Table(name = "sys_background_pic")
@Data
public class SysBackgroundPic{

    /**
     * id 主键
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题 - 首页背景图标题
     */
    private String title;

    /**
     * 首页背景图描述
     */
    private String des;

    /**
     * （数据字典）是否使用 0停用 1使用
     */
    private String is_use;

    /**
     * 图片长度
     */
    private String pic_length;

    /**
     * 图片宽度
     */
    private String pic_width;

    /**
     * 排序
     */
    private Integer pic_sort;

    /**
     * 上传到服务器之后的图片ID
     */
    private Integer pic_id;

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
    private List<FileManage> fileManageList = new ArrayList<>();

}
