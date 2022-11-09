package com.jsdc.itss.model.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.itss.model.*;
import com.jsdc.itss.vo.SysUserPageVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventCategoryVo extends Model<EventCategoryVo> implements Serializable {

    //工单类别管理表
    private EventCategory eventCategory;

    //负责人
    private List<SysUser> type1 = new ArrayList<>();
    //协查人
    private List<SysUser> type2 = new ArrayList<>();
    //负责人
    private List<SysUserPageVo> sysUserList1 = new ArrayList<>();
    //协查人
    private List<SysUserPageVo> sysUserList2 = new ArrayList<>();

}
