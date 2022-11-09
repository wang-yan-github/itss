package com.jsdc.itss.vo;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

@Data
public class LogVo {

    //单据id
    private Integer bill_id;

    //标题
    private String title;

    //内容
    private String content;

    //操作时间
    private Date operate_time;

    private Integer userId;

    public LogVo(Integer bill_id, String title, String content) {
        this.bill_id = bill_id;
        this.title = title;
        this.content = StringUtils.isBlank(content) ? StringUtils.EMPTY : content;
        this.operate_time = new Date();
    }
    public LogVo(Integer userId, Integer bill_id, String title, String content) {
        this.bill_id = bill_id;
        this.title = title;
        this.content = StringUtils.isBlank(content) ? StringUtils.EMPTY : content;
        this.operate_time = new Date();
        this.userId = userId;
    }
}
