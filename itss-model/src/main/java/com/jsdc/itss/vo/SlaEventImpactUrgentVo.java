package com.jsdc.itss.vo;

import com.jsdc.itss.model.SlaEventImpactUrgent;
import lombok.Data;

import java.util.List;

/**
 * ClassName: SlaEventImpactUrgentVo
 * Description:
 * date: 2022/3/7 17:12
 *
 * @author bn
 */
@Data
public class SlaEventImpactUrgentVo extends SlaEventImpactUrgent {

    //事件影响度名称
    private String impact_name;
    //事件紧急度名称
    private String urgent_name;
    //事件优先级名称
    private String first_name;

    private List<SlaEventImpactUrgentVo> slaEventImpactUrgentVos;



}
