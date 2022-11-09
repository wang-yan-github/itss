package com.jsdc.itss.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 微信小程序
 * 树状图数据data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeDataVo implements Serializable {

    private Integer id;
    //名称
    private String name;
    //5	自动分派到工程师ID
    private Integer engineer_id;
    //6	上级类别ID
    private Integer parent_type_id;
    //8	工单类别ID
    private Integer worker_order_id;
    //子集
    private List<TreeDataVo> children;

}
