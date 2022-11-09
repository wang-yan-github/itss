package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.model.KnowledgeScoring;
import com.jsdc.itss.service.KnowLedgeEsService;
import com.jsdc.itss.service.KnowledgeInfoService;
import com.jsdc.itss.vo.KnowledgeEsVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/app/knowledge")
public class AppKnowledgeController {

    @Autowired
    private KnowledgeInfoService knowledgeInfoService;
    @Autowired
    private KnowLedgeEsService knowLedgeEsService;

    /**
     * 我的知识 查看知识库
     */
    @RequestMapping("getMyKnowledgeByPage.do")
    public ResultInfo getMyKnowledgeByPage(KnowledgeInfo knowledgeInfo,
                                           @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           Integer userId){
        knowledgeInfo.setUserId(userId);
        if (StringUtils.isNotBlank(knowledgeInfo.getStartTime())){
           knowledgeInfo.setStartTime(knowledgeInfo.getStartTime()+" 00:00:00");
        }
        if (StringUtils.isNotBlank(knowledgeInfo.getEndTime())){
            knowledgeInfo.setEndTime(knowledgeInfo.getEndTime()+" 23:59:59");
        }

        return knowledgeInfoService.getByPage(knowledgeInfo,pageIndex,pageSize);
    }


    /**
     * create by xuaolong
     * description: 根据主键查询信息
     * @param id
     * @return
     */
    @RequestMapping("getKnowledgeInfoById.do")
    @ResponseBody
    public ResultInfo getKnowledgeInfoById(Integer id){
        return knowledgeInfoService.getKnowledgeInfoById(id);
    }



    /**
     * 描述： 分页查询待审核知识
     * 作者： xuaolong
     * @param knowledgeInfo
     * @return
     */
    @PostMapping("getStatusByPage.do")
    public ResultInfo getStatusByPage(KnowledgeInfo knowledgeInfo,
                                      @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      Integer userId){
        knowledgeInfo.setUserId(userId);
        return knowledgeInfoService.getStatusByPage(knowledgeInfo,pageIndex,pageSize);
    }

    /**
     * 知识审核 审核
     */
    @PostMapping("toExamineKnowledge.do")
    @ResponseBody
    public ResultInfo toExamineKnowledge(@RequestBody KnowledgeInfo knowledgeInfo){
        return knowledgeInfoService.toExamineKnowledge(knowledgeInfo, null);
    }

    /**
     * 知识审核 审核
     */
    @PostMapping("toRefuseExamineKnowledge.do")
    @ResponseBody
    public ResultInfo toRefuseExamineKnowledge(@RequestBody KnowledgeInfo knowledgeInfo){
        return knowledgeInfoService.toRefuseExamineKnowledge(knowledgeInfo,null);
    }

    /**
     * 知识审核 知识管理 打分
     */
    @RequestMapping("getPoint.do")
    @ResponseBody
    public ResultInfo getPoint( @RequestBody KnowledgeScoring knowledgeScoring){
        if (knowledgeScoring.getScore()>10 || knowledgeScoring.getScore()<1){
            return ResultInfo.error("分值应该在1-10之间！");
        }

        return  knowledgeInfoService.getPoint(knowledgeScoring);
    }

    /**
     * 内部知识 查看知识库
     */
    @PostMapping("getInsideKnowledge.do")
    public ResultInfo getInsideKnowledge(KnowledgeInfo knowledgeInfo,
                                         @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         Integer userId){
        knowledgeInfo.setUserId(userId);
        return knowledgeInfoService.getInsideKnowledge(knowledgeInfo,pageIndex,pageSize);
    }

    /**
     * 知识管理 查看知识库
     */
    @RequestMapping("manageKnowledge.do")
    public ResultInfo manageKnowledge(KnowledgeInfo knowledgeInfo,
                                      @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        return knowledgeInfoService.manageKnowledge(knowledgeInfo,pageIndex,pageSize);
    }

    /**
     * 知识管理 删除知识
     */
    @RequestMapping("deleteKnowledge.do")
    @ResponseBody
    public  ResultInfo deleteKnowledge(Integer id){
        return  knowledgeInfoService.deleteKnowledge(id);
    }

    /**
     * 知识管理 使用知识
     */
    @RequestMapping("useKnowledge.do")
    @ResponseBody
    public ResultInfo useKnowledge(Integer id,Integer userId){
        return knowledgeInfoService.useKnowledge(id,userId);
    }

    /**
     * 知识管理 禁用知识
     */
    @RequestMapping("unUseKnowledge.do")
    @ResponseBody
    public ResultInfo unUseKnowledge(Integer id,Integer userId){
        return knowledgeInfoService.unUseKnowledge(id,userId);
    }

    /**
     * el知识搜索
     */
    @RequestMapping("/queryDoc")
    @ResponseBody
    public ResultInfo queryDoc(@RequestBody KnowledgeEsVo knowledgeEsVo){
        ResultInfo resultInfo = knowLedgeEsService.queryDoc1(knowledgeEsVo);
        return resultInfo;
    }
}
