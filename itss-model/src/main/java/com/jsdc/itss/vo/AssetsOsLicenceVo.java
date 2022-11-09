package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: AssetsOsLicenceVo
 * Description: 操作系统许可证表 拓展
 *
 * @author zhangdequan
 */
@Data
public class AssetsOsLicenceVo {

    private Integer id;
    private String os_licence_name; //名称
    private Integer deptId;        //部门
    private String description;     //描述
    private String use_limit;      //使用限制
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_time;        //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end_time;          //结束时间
    private String key_license;            //key
    private Integer is_limit; //1：有期限 0：无期限
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private Integer create_user;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;
    private Integer update_user;
    private String is_del;

    private String deptName;
    private String starTime;
    private String endTime;
}
