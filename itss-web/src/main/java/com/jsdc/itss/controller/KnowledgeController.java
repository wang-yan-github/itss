package com.jsdc.itss.controller;

import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.service.KnowLedgeEsService;
import com.jsdc.itss.vo.KnowledgeEsVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xujian
 * @create 2022-03-08 12:19:43
 */
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {
    @Autowired
    private KnowLedgeEsService knowLedgeEsService;

    @RequestMapping("/createIndex")
    public ResultInfo createIndex(){
       return knowLedgeEsService.createIndex();
    }

    @RequestMapping("/deleteIndex")
    public ResultInfo deleteIndex(){
        return knowLedgeEsService.deleteIndex();
    }

    @RequestMapping("/addDoc")
    public ResultInfo addDoc(KnowledgeInfo knowledgeInfo){
        knowledgeInfo = new KnowledgeInfo();
        knowledgeInfo.setId(1);
        knowledgeInfo.setDept_id(1);
        knowledgeInfo.setKnowledge_category_id(1);
        knowledgeInfo.setIs_full_search("1");
        knowledgeInfo.setIs_recommend("1");
        knowledgeInfo.setKnowledge_range_id(1);
        knowledgeInfo.setTitle("测试标题11111oneone");
        knowledgeInfo.setContent("测试内容11111oneone");
        knowledgeInfo.setKeyword("测试，one");
        knowLedgeEsService.addOrUpdateDoc(knowledgeInfo);
        knowledgeInfo = new KnowledgeInfo();
        knowledgeInfo.setId(2);
        knowledgeInfo.setDept_id(2);
        knowledgeInfo.setKnowledge_category_id(2);
        knowledgeInfo.setIs_full_search("2");
        knowledgeInfo.setIs_recommend("2");
        knowledgeInfo.setKnowledge_range_id(2);
        knowledgeInfo.setTitle("测试标题22222twotwo");
        knowledgeInfo.setContent("测试内容22222twotwo");
        knowledgeInfo.setKeyword("测试，two");
        knowLedgeEsService.addOrUpdateDoc(knowledgeInfo);
        knowledgeInfo = new KnowledgeInfo();
        knowledgeInfo.setId(3);
        knowledgeInfo.setDept_id(3);
        knowledgeInfo.setKnowledge_category_id(1);
        knowledgeInfo.setIs_full_search("3");
        knowledgeInfo.setIs_recommend("3");
        knowledgeInfo.setKnowledge_range_id(3);
        knowledgeInfo.setTitle("测试标题33333threethree");
        knowledgeInfo.setContent("测试内容33333threethree");
        knowledgeInfo.setKeyword("测试，three"  );
       return knowLedgeEsService.addOrUpdateDoc(knowledgeInfo);
    }

    @RequestMapping("/queryDoc")
    public ResultInfo queryDoc(@RequestBody KnowledgeEsVo knowledgeEsVo){
        ResultInfo resultInfo = knowLedgeEsService.queryDoc(knowledgeEsVo);
       return resultInfo;
    }

    @RequestMapping("/deleteDoc")
    public ResultInfo deleteDoc(Integer id){
       return knowLedgeEsService.deleteDoc(id);
    }

}
