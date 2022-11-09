package com.jsdc.itss.common.aop.logaop;

import lombok.Getter;

@Getter
public enum LogEnums {
    LOG_LOGIN("1","登录"),
    LOG_LOGOUT("2","登出"),
    ;


    LogEnums(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;

    private String desc;
}
