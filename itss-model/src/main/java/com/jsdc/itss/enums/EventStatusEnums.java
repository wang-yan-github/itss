package com.jsdc.itss.enums;

import lombok.Getter;

@Getter
public enum EventStatusEnums {
    PENDING_RESPONSE("1","待响应"),
    PROCESSING("2","处理中"),
    PAUSE("3","暂停"),
    PROCESSING_COMPLETED("4","处理完成"),
    COMMENT("5","待评价"),
    CLOSED("6","已关闭"),
    REVOKED("7","已撤销"),
    ;

    EventStatusEnums(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;

    private String desc;

    private String type() {
        return this.type;
    }

    private String desc() {
        return this.desc;
    }

    public static String getValue(String type) {
        EventStatusEnums[] carTypeEnums = values();
        for (EventStatusEnums carTypeEnum : carTypeEnums) {
            if (carTypeEnum.type().equals(type)) {
                return carTypeEnum.desc();
            }
        }
        return null;
    }

    public static String getType(String desc) {
        EventStatusEnums[] carTypeEnums = values();
        for (EventStatusEnums carTypeEnum : carTypeEnums) {
            if (carTypeEnum.desc().equals(desc)) {
                return carTypeEnum.type();
            }
        }
        return null;
    }
}
