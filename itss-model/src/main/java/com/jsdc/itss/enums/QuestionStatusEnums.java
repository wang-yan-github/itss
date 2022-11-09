package com.jsdc.itss.enums;

import lombok.Getter;

@Getter
public enum QuestionStatusEnums {
    STATUS_IDENT("1", "待鉴定"),
    STATUS_AUDIT("2", "待审核"),
    STATUS_SOLVE("3", "待解决"),
    STATUS_EXAMINATION_PASSED("4", "审核通过"),
    STATUS_CLOSE("5", "关闭"),
    STATUS_RESCINDED("6", "已撤销"),
    STATUS_IDENT_REFUSE("7", "鉴定拒绝 "),
    STATUS_AUDIT_REFUSE("8", "审核拒绝"),
    STATUS_HANG("9", "挂起");

    QuestionStatusEnums(String type, String desc) {
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
        QuestionStatusEnums[] carTypeEnums = values();
        for (QuestionStatusEnums carTypeEnum : carTypeEnums) {
            if (carTypeEnum.type().equals(type)) {
                return carTypeEnum.desc();
            }
        }
        return null;
    }

    public static String getType(String desc) {
        QuestionStatusEnums[] carTypeEnums = values();
        for (QuestionStatusEnums carTypeEnum : carTypeEnums) {
            if (carTypeEnum.desc().equals(desc)) {
                return carTypeEnum.type();
            }
        }
        return null;
    }
}
