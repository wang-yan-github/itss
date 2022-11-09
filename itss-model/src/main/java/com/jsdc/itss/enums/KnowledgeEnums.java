package com.jsdc.itss.enums;

import lombok.Getter;
/**
 * 作者： xuaolong
 * 描述： 知识状态枚举
 */
@Getter
public enum KnowledgeEnums {
    KNOWLEDGE_STATUS_0(0,"禁用"),
    KNOWLEDGE_STATUS_1(1,"使用"),
    ;
    KnowledgeEnums(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;

    private String desc;

    private Integer type() {
        return this.type;
    }

    private String desc() {
        return this.desc;
    }
    public static String getValue(Integer type) {
        KnowledgeEnums[] knowledgeEnums = values();
        for (KnowledgeEnums knowledge : knowledgeEnums) {
            if (knowledge.type()==type) {
                return knowledge.desc();
            }
        }
        return null;
    }

    public static Integer getType(String desc) {
        KnowledgeEnums[] knowledgeEnums = values();
        for (KnowledgeEnums knowledge : knowledgeEnums) {
            if (knowledge.desc().equals(desc)) {
                return knowledge.type();
            }
        }
        return null;
    }
}
