package com.jsdc.itss.enums;

import lombok.Getter;

@Getter
public enum LogEnums {
    LOG_EVENT("1","事件"),
    LOG_QUESTION("2","问题"),
    LOG_CHANGE("3","变更"),
    LOG_PUBLISH("4","发布"),
    LOG_LOGOUT("5","配置管理"),
    LOG_GZT("6","工作台"),
    LOG_Schedule("7","定时任务"),
    LOG_APP_QUESTION("8","APP问题"),
    ;


    LogEnums(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;

    private String desc;
}
