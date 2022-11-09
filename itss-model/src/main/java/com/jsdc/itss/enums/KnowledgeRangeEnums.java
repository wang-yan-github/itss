package com.jsdc.itss.enums;

import lombok.Getter;

/**
 * 作者： xuaolong
 * 描述： 知识范围枚举
 */
@Getter
public enum KnowledgeRangeEnums {
    KNOWLEDGE_RANGE_1("1","角色"),
    KNOWLEDGE_RANGE_2("2","部门"),
    KNOWLEDGE_RANGE_3("3","服务群组"),
    ;

    KnowledgeRangeEnums(String type, String desc) {
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
        KnowledgeRangeEnums[] knowledgeRangeEnums = values();
        for (KnowledgeRangeEnums knowledgeEnums : knowledgeRangeEnums) {
            if (knowledgeEnums.type().equals(type)) {
                return knowledgeEnums.desc();
            }
        }
        return null;
    }

    public static String getType(String desc) {
        KnowledgeRangeEnums[] knowledgeRangeEnums = values();
        for (KnowledgeRangeEnums knowledgeEnums : knowledgeRangeEnums) {
            if (knowledgeEnums.desc().equals(desc)) {
                return knowledgeEnums.type();
            }
        }
        return null;
    }



}
