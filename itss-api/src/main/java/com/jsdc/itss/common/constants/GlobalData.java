package com.jsdc.itss.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GlobalData {
    // 1.我的变更列表分页 2.变更管理列表分页 3我参与的
    public static final String DATAFLAG_1 = "1";
    public static final String DATAFLAG_2 = "2";
    public static final String DATAFLAG_3 = "3";
    // 是否生成了知识
    public static final String IS_KNOWLEDGE_YES = "1";

    //逻辑删除 是
    public  String ISDEL_YES = "1";
    //逻辑删除 否
    public  String ISDEL_NO = "0";

    //启用
    public  String IS_ENABLE_YES = "1";
    //禁用
    public  String IS_ENABLE_NO = "0";

    /**
     * 发布状态
     */
    //0:已撤销
    public  String PUBLISH_STATUS_REVOKED = "0";
    //1:待评审
    public  String PUBLISH_STATUS_APPROVE = "1";
    //2:评审拒绝
    public  String PUBLISH_STATUS_APPROVE_REFUSE = "2";
    //3:待测试
    public  String PUBLISH_STATUS_TEST = "3";
    //4:待发布
    public  String PUBLISH_STATUS_PUB = "4";
    //5:发布成功
    public  String PUBLISH_STATUS_PUB_SUCCESS = "5";
    //6:发布失败
    public  String PUBLISH_STATUS_PUB_FAIL = "6";

    //事件状态 待响应
    public String EVENT_STATUS_DXY = "1";
    //事件状态 处理中
    public String EVENT_STATUS_CLZ = "2";
    //事件状态 暂停
    public String EVENT_STATUS_ZT = "3";
    //事件状态 已完成
    public String EVENT_STATUS_WC = "4";
    //事件状态 待评价
    public String EVENT_STATUS_DPJ = "5";
    //事件状态 已关闭
    public String EVENT_STATUS_YGB = "6";
    //事件状态 撤销
    public String EVENT_STATUS_CX = "7";

    public String EVENT = "0";
    public String QUESTION = "1";

    public String IS_COMPLETE_YES = "1";
    public String IS_COMPLETE_NO = "0";

    //权限: 顶级菜单
    public Short AUTHORITY_TOP_MENU = 1;
    //权限: 二级菜单
    public Short AUTHORITY_MENU = 2;
    //权限: 页面按钮
    public Short AUTHORITY_BUTTON = 3;
}
