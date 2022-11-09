package com.jsdc.itss.controller;


import com.jsdc.itss.model.SchemeClassification;
import com.jsdc.itss.service.SchemeClassificationService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schemeClassification")
public class SchemeClassificationController {

    @Autowired
    private SchemeClassificationService schemeClassificationService;


    /**
     * 新增方案分类
     * Author wzn
     * Date 2022/3/9 9:40
     */
    @RequestMapping("save.do")
    public ResultInfo save(@RequestBody SchemeClassification schemeClassification) {
        return schemeClassificationService.addSchemeClassification(schemeClassification);
    }


    /**
     * 方案分类修改接口
     * Author wzn
     * Date 2022/3/9 9:47
     */
    @RequestMapping("update.do")
    public ResultInfo update(@RequestBody SchemeClassification schemeClassification) {
        return schemeClassificationService.updateSchemeClassification(schemeClassification);
    }


    /**
     * schemeClassification
     * 方案分类删除接口
     * Author wzn
     * Date 2022/3/9 9:48
     */
    @RequestMapping("deleteSysCompany.do")
    public ResultInfo deleteSysCompany(Integer id) {
        return ResultInfo.success(schemeClassificationService.deleteSchemeClassification(id));
    }


    /**
     * 方案分类列表接口
     * Author wzn
     * Date f 10:56
     */
    @RequestMapping("tree")
    public ResultInfo tree() {
        return ResultInfo.success(schemeClassificationService.getDetailTree());
    }


    /**
     * 详情
     */
    @RequestMapping("view")
    public ResultInfo view(Integer id) {
        return ResultInfo.success(schemeClassificationService.getDetail(id));
    }


}
