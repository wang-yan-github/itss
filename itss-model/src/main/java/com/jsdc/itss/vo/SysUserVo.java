package com.jsdc.itss.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.EventServiceManage;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.model.SysUserEventGroup;
import com.jsdc.itss.model.vo.EventServiceManageVo;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SysUserVo {

    /**
     * 编号
     */
    private  Integer id ;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 密码
     */
    private String pass;

    /**
     * 姓名
     */
    private String name;


    /**
     * 部门ID
     */
    private Integer department_id;

    private  String departName;


    /**
     * 职位
     */
    private  String position;

    /**
     * 位置
     */
    private  String location;


    /**
     * 电话
     */
    private  String telephone;


    /**
     *座机
     */
    private  String landline;


    /**
     *邮箱
     */
    private  String mailbox;


    /**
     *是否是VIP
     * 0否（默认） 1是
     */
    private  String is_vip;

    /**
     * 状态
     * 1活动 2暂停 3已禁止
     */
    private String status;

    /**
     *类型
     */
    private String user_type;


    /**
     * 是否接收邮箱通知
     * 0否 1是
     */
    private String is_emial;


    /**
     * 是否接受微信通知
     * 0否 1是
     */
    private String is_wechat;


    /**
     * 微信昵称
     */
    private String wechat_nickname;


    /**
     * 角色ID集合
     */
    private List<Integer> roleIdList =new ArrayList<>();

    /**
     * 角色集合
     */
    private List<SysRole> roleList =new ArrayList<>();

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 服务群组ID集合
     */
    private List<Integer> groupIdList =new ArrayList<>();

    private List<EventServiceManage> groupList =new ArrayList<>();



    /**
     * 用户事件按部门统计权限部门ID集合
     */
    private List<Integer> departmentIdList =new ArrayList<>();

    private List<SysDepartment> departmentList =new ArrayList<>();


    /**
     * 用户配置管理权限范围部门ID集合
     */
    private List<Integer> configIdList =new ArrayList<>();
    private List<SysDepartment> configList =new ArrayList<>();

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    private Boolean updatePassword = false;

    private String newPassword;

    //旧密码
    private String oldPass;
    //新密码
    private String newPass;
    //确定密码
    private String confirmPass;
}
