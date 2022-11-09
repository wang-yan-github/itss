package com.jsdc.itss.vo;

import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.model.Question;
import com.jsdc.itss.model.QuestionCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionStatisticsVo {

    //问题集合
    private List<Question> list = new ArrayList<>();
    //是否生成知识库
    private List<Question> knowledge = new ArrayList<>();
    //SLA统计
    private List<Question> isFitSla = new ArrayList<>();
    //问题类别集合
    private List<QuestionCategory>  questionCategoryList = new ArrayList<>();

   //问题状态集合
    private List<Question> statusList = new ArrayList<>();



}
