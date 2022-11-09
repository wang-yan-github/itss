package com.jsdc.itss.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.itss.model.AssetsInventoryDetails;
import com.jsdc.itss.model.FileManage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: AssetsInventoryDetailsVo
 * Description:
 * date: 2022/3/22 16:55
 *
 * @author bn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetsInventoryDetailsVo extends AssetsInventoryDetails {

    private Integer id ;

    // 所属盘点期
    private String ai_name;
    // 所属盘点期 id
    private Integer pdqId;

    // 资产id
    private Integer am_id;

    // 资产编号
    private String am_assets_number;

    // 配置项类型id
    private Integer aty_id;

    // 配置项类型
    private String aty_name;

    // 配置子类id
    private Integer ac_id;

    // 配置子类
    private String ac_name;

    // 资产名称
    private String am_assets_name;

    // 部门id
    private Integer sd_id;

    // 部门
    private String sd_name;

    // 领用人id
    private Integer su_id;

    // 领用人
    private String su_name;

    // 资产状态
    private String ast_status_name;

    // it状态
    private String am_it_status;



    // 确认人
    private String suc_name;
    private Integer suc_id;

    /**
     * 处理人
      */
    private String suh_name;
    private Integer suh_id;
    /**
     * 负责人
     */
    private String person_liable;
    private Integer person_id;
    /**
     * 购置日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchase_date;
    /**
     * 财务编号
     */
    private String finance_number;
    /**
     * 购置公司
     */
    private String purchase_company;
    /**
     * cpu
     */
    private String cpu;
    /**
     * 内存
     */
    private String memory;
    /**
     * 硬盘
     */
    private String hard_disk;
    /**
     * 用途
     */
    private String purpose;
    /**
     * 序列号
     */
    private String sn;
    /**
     * 确认备注
     */
    private String confirm_remark;
    /**
     * 批量确认ids
     */
    private List<Integer> ids = new ArrayList<>();

    private Integer is_inventory;                           // 是否已生成资产盘点 0 生成 1.未生成

    private List<FileManage> assetsEnclosures;//附件
    private List<FileManage> assetsPhotos;//图片

    private String keywords;//关键字
}
