package com.jsdc.itss.enums;

/**
 * ClassName: DataType
 * Description:
 * date: 2022/3/14 14:22
 *
 * @author bn
 */
public enum DataType {

    /**
     * 变更
     */
    CHANGE_TYPE,
    /**
     *  问题
     */
    QUESTION_TYPE,
    /**
     *  发布
     */
    PUBLISH_TYPE,
    /**
     * 配置请求
     */
    ASSETS_RQ_TYPE;




    /**
     * 变更前缀
     */
    public static String change_no="REF";

    /**
     *  变更Excel
     */
    public static String change_excel="变更信息";

    /**
     *  资产盘点记录Excel
     */
    public static  String assets_inventory_excel="资产盘点记录";

    /**
     *  问题前缀
     */
    public static String question_no="REF";

    /**
     *  发布前缀
     */
    public static String publish_no="RM";

    /**
     *  配置请求前缀
     */
    public static String assets_rq_no="CIR";




}
