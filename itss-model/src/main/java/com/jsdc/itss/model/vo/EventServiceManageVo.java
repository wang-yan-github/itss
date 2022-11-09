package com.jsdc.itss.model.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.itss.model.EventServiceManage;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.SysUserPageVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventServiceManageVo extends Model<EventServiceManageVo> implements Serializable {


    private EventServiceManage eventServiceManage;

    //开单、指派、转单工程师
    private List<SysUser> type1 = new ArrayList<>();
    //处理工程师
    private List<SysUser> type2 = new ArrayList<>();
    //负责人
    private List<SysUser> type3 = new ArrayList<>();

    private List<SysUserPageVo> sysUserList1 = new ArrayList<>();
    private List<SysUserPageVo> sysUserList2 = new ArrayList<>();
    private List<SysUserPageVo> sysUserList3 = new ArrayList<>();


}
