package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.jsdc.itss.model.KnowledgeCategory;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.model.KnowledgeRange;
import com.jsdc.itss.model.KnowledgeScoring;
import com.jsdc.itss.service.KnowledgeInfoService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author xuaolong
 */
@RestController
@RequestMapping("/knowledgeinfo")
public class KnowledgeInfoController {

    @Autowired
    private KnowledgeInfoService knowledgeInfoService;


    /**
     * create by xuaolong
     * description: 分页查询
     * @param knowledgeInfo
     * @param pageIndex
     * @param pageSize
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("getPage.do")
    public ResultInfo getInfoByPage(KnowledgeInfo knowledgeInfo,
                                    @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                    @RequestParam(defaultValue = "10") Integer pageSize){
        return knowledgeInfoService.getByPage(knowledgeInfo,pageIndex,pageSize);
    }

    /**
     * create by xuaolong
     * description: 添加我的知识
     * @return
     */
    @RequestMapping("addKnowledgeInfo.do")
    public ResultInfo addKnowledgeInfo(@RequestParam String body, @RequestParam List<MultipartFile> file ){
        KnowledgeInfo knowledgeInfo = JSON.parseObject(body, KnowledgeInfo.class);
        if (knowledgeInfo != null){
            return knowledgeInfoService.addKnowledgeInfo(knowledgeInfo, file);
        }else{
            return ResultInfo.error("请填写必填信息！！！");
        }
    }

    /**
     * create by xuaolong
     * description: 修改我的知识
     * @return
     */
    @RequestMapping("updateKnowledgeInfo.do")
    public ResultInfo updateKnowledgeInfo(@RequestParam String body, @RequestParam List<MultipartFile> file ){
        KnowledgeInfo knowledgeInfo = JSON.parseObject(body, KnowledgeInfo.class);
        if (knowledgeInfo != null){
            return knowledgeInfoService.updateKnowledgeInfo(knowledgeInfo, file);
        }else {
            return ResultInfo.error("请填写必填信息！！！");
        }
    }

    /**
     * create by xuaolong
     * description: 根据主键查询信息
     * @param id
     * @return
     */
    @RequestMapping("getKnowledgeInfoById.do")
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
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        return knowledgeInfoService.getStatusByPage(knowledgeInfo,pageIndex,pageSize);
    }

    /**
     * 描述： 知识审核（通过)
     * 作者： xuaolong
     * @return
     */
    @PostMapping("toExamineKnowledge.do")
    public ResultInfo toExamineKnowledge(@RequestParam String body, @RequestParam List<MultipartFile> file){
        KnowledgeInfo knowledgeInfo = JSON.parseObject(body, KnowledgeInfo.class);
        return knowledgeInfoService.toExamineKnowledge(knowledgeInfo, file);
    }


    /**
     * 描述： 知识审核（拒绝)
     * 作者： xuaolong
     * @param
     * @return
     */
    @RequestMapping("toRefuseExamineKnowledge.do")
    public ResultInfo toRefuseExamineKnowledge(@RequestParam String body, @RequestParam List<MultipartFile> file){
        KnowledgeInfo knowledgeInfo = JSON.parseObject(body, KnowledgeInfo.class);
        return knowledgeInfoService.toRefuseExamineKnowledge(knowledgeInfo,file);
    }


    /**
     * 描述： 内部知识分页查询
     * 作者： xuaolong
     * @param knowledgeInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping("getInsideKnowledge.do")
    public ResultInfo getInsideKnowledge(KnowledgeInfo knowledgeInfo,
                                         @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                         @RequestParam(defaultValue = "10") Integer pageSize){
        return knowledgeInfoService.getInsideKnowledge(knowledgeInfo,pageIndex,pageSize);
    }

    /**

     * description:内部知识导出excel
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @PostMapping("exporInsideKnowledgeExcel.do")
    public void exporInsideKnowledgeExcel(HttpServletResponse response,  KnowledgeInfo knowledgeInfo){
         knowledgeInfoService.exporInsideKnowledgeExcel(response,knowledgeInfo);
    }


    /**
     * 描述： 知识管理打分
     * 作者： xuaolong
     * @param knowledgeScoring
     * @return
     */
    @RequestMapping("getPoint.do")
    public ResultInfo getPoint( @RequestBody KnowledgeScoring knowledgeScoring){
        if (knowledgeScoring.getScore()>10 || knowledgeScoring.getScore()<1){
            return ResultInfo.error("分值应该在1-10之间！");
        }
        return  knowledgeInfoService.getPoint(knowledgeScoring);
    }


    /**
     * 描述： 知识管理删除知识
     * 作者： xuaolong
     * @return ResultInfo
     */
    @RequestMapping("deleteKnowledge.do")
    public  ResultInfo deleteKnowledge(int id){
        return  knowledgeInfoService.deleteKnowledge(id);
    }

    /**
     * 描述： 知识管理使用知识
     * 作者： xuaolong
     * @param id
     * @return
     */
    @RequestMapping("useKnowledge.do")
    public ResultInfo useKnowledge(Integer id){
        return knowledgeInfoService.useKnowledge(id,null);
    }

    /**
     * 描述： 知识管理禁用知识
     * 作者： xuaolong
     * @param id
     * @return
     */
    @RequestMapping("unUseKnowledge.do")
    public ResultInfo unUseKnowledge(Integer id){
        return knowledgeInfoService.unUseKnowledge(id,null);
    }

    /**
     * 描述： 知识管理分页查询
     * 作者： xuaolong
     * @param knowledgeInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("manageKnowledge.do")
    public ResultInfo manageKnowledge(KnowledgeInfo knowledgeInfo,
                                      @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        return knowledgeInfoService.manageKnowledge(knowledgeInfo,pageIndex,pageSize);
    }

    /**
     * 描述： 知识管理导出
     * 作者： xuaolong
     */
    @PostMapping("exporManageKnowledgeExcel.do")
    public void   exporManageKnowledgeExcel(HttpServletResponse response,KnowledgeInfo knowledgeInfo){
        knowledgeInfoService.exporManageKnowledgeExcel(response, knowledgeInfo);
    }

    /**
     * Excel知识导入模板下载
     * author xuaolong
     * @param response
     * @return
     */
    @RequestMapping("downloadKnowledge")
    public void downloadPublishExcel(HttpServletResponse response){
        knowledgeInfoService.exportKnowledgeTemplate(response);
    }

    /**
     * Excel导入知识
     * author xuaolong
     * @param file
     * @return
     */
    @RequestMapping("importKnowledgeList")
    @ResponseBody
    public ResultInfo importKnowledgeList(@RequestParam("file") MultipartFile file){
        return knowledgeInfoService.importKnowledgeList(file);
    }


    /**
     * 查询热点知识和推荐知识
     * @param type 1:热点知识 2：推荐知识
     * @return
     */
    @RequestMapping("/getHotOrRecommendKnow")
    @ResponseBody
    public ResultInfo getHotOrRecommendKnow(String type){
        PageHelper.startPage(1,10);
        List<KnowledgeInfo> knowledgeInfos=null;
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del","0");
        qw.eq("status","2");
        if("1".equals(type)){
            qw.orderByDesc("browse_num");
            knowledgeInfos = knowledgeInfoService.selectList(qw);
        }else if("2".equals(type)){
            qw.eq("is_recommend","1");
            knowledgeInfos = knowledgeInfoService.selectList(qw);
        }
        return ResultInfo.success(knowledgeInfos);
    }


    @RequestMapping("/getCategoryWithTree.do")
    public ResultInfo getCategoryWithTree(  KnowledgeCategory  knowledgeCategory){
        List<KnowledgeCategory> list =knowledgeInfoService.queryWithTree(knowledgeCategory);
        return ResultInfo.success(list);
    }

    @RequestMapping("/getCategoryWithTree1.do")
    public ResultInfo getCategoryWithTree1(@RequestBody  KnowledgeCategory  knowledgeCategory){
        List<KnowledgeCategory> list =knowledgeInfoService.queryWithTree(knowledgeCategory);
        return ResultInfo.success(list);
    }

    @RequestMapping("/getAllRange.do")
    public ResultInfo getRange(){
        List<KnowledgeRange> list = knowledgeInfoService.rangeList();
        return ResultInfo.success(list);
    }
    @RequestMapping("/getSearchFourKnowledge.do")
    public ResultInfo getSearchFourKnowledge(KnowledgeInfo knowledgeInfo){
        List<KnowledgeInfo> list = knowledgeInfoService.getSearchFourKnowledge(knowledgeInfo);
        return ResultInfo.success(list);
    }


    @RequestMapping("/contributionScoreStatistics")
    @ResponseBody
    public ResultInfo contributionScoreStatistics(){
        return ResultInfo.success(knowledgeInfoService.contributionScoreStatistics());
    }


    @RequestMapping("/departmentOfKnowledge")
    @ResponseBody
    public ResultInfo departmentOfKnowledge(){
        return ResultInfo.success(knowledgeInfoService.departmentOfKnowledge());
    }
}
