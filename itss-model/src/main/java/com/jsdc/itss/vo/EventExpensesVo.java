package com.jsdc.itss.vo;

import lombok.Data;

@Data
public class EventExpensesVo {
    private String name;//订单类型名称
    private Integer customer_contract_id; //合同ID
    private String expenses;//订单金额统计
}
