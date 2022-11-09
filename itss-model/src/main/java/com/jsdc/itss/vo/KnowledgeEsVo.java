package com.jsdc.itss.vo;

import lombok.Data;

/**
 * @ClassName KnowledgeEsVo
 * @Description TODO 全文检索查询条件
 * @Author xujian
 * @Date 2022/3/14 16:37
 * @Version 1.0
 */
@Data
public class KnowledgeEsVo {
    private Integer pageIndex;
    private Integer pageSize;
    private String keyWord;
    private Integer is_full_search;
}
