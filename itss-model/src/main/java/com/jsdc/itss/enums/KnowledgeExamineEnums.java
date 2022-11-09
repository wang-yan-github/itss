package com.jsdc.itss.enums;


import lombok.Getter;

/**
 * 作者： xuaolong
 * 描述： 知识审核枚举
 */
@Getter
public enum KnowledgeExamineEnums {

    KNOWLEDGE_EXAMINE_1("1","待审核"),
    KNOWLEDGE_EXAMINE_2("2","发布"),
    KNOWLEDGE_EXAMINE_3("3","审核拒绝"),
    ;
    KnowledgeExamineEnums(String type, String desc) {
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
        KnowledgeExamineEnums[] knowledgeExamineEnums = values();
        for (KnowledgeExamineEnums knowledgeEnums : knowledgeExamineEnums) {
            if (knowledgeEnums.type().equals(type)) {
                return knowledgeEnums.desc();
            }
        }
        return null;
    }

    public static String getType(String desc) {
        KnowledgeExamineEnums[] knowledgeExamineEnums = values();
        for (KnowledgeExamineEnums knowledgeEnums : knowledgeExamineEnums) {
            if (knowledgeEnums.desc().equals(desc)) {
                return knowledgeEnums.type();
            }
        }
        return null;
    }


}
