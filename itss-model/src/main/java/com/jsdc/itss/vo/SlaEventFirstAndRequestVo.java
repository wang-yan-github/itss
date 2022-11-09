package com.jsdc.itss.vo;

import lombok.Data;

/**
 * ClassName: SlaEventFirstAndRequestVo
 * Description:
 * date: 2022/3/23 19:04
 *
 * @author wh
 **/
@Data
public class SlaEventFirstAndRequestVo {
    //id
    private Integer id;
    // 优先级id
    private Integer first_id;
    // 请求类型id
    private Integer request_type_id;
    // 优先级名
    private String first_name;
    // 请求类型名
    private String request_type_name;
    // 响应时间(分钟数)
    private String response_time;
    // 解决时间(分钟数)
    private String solve_time;

}
