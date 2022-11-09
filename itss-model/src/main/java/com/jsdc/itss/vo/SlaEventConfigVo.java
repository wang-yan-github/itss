package com.jsdc.itss.vo;

import com.jsdc.itss.model.SlaEventConfig;
import lombok.Data;

/**
 * ClassName: SlaEventConfigVo
 * Description:
 * date: 2022/3/9 9:51
 *
 * @author bn
 */
@Data
public class SlaEventConfigVo extends SlaEventConfig {

    // 优先级名
    private String first_name;
    // 请求类型名
    private String request_type_name;


}
