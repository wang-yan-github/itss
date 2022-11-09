package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.model.AssetsExpandFormField;
import com.jsdc.itss.model.AssetsType;
import com.jsdc.itss.service.AssetsExpandFormFieldService;
import com.jsdc.itss.service.AssetsExpandFormService;
import com.jsdc.itss.service.AssetsTypeService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsExpandFormField")
public class AssetsExpandFormFieldController extends BaseController {

    @Autowired
    private AssetsExpandFormFieldService assetsExpandFormFieldService;
    @Autowired
    private AssetsExpandFormService expandFormService;
    @Autowired
    private AssetsTypeService assetsTypeService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsExpandFormField beanParam) {
        PageInfo<AssetsExpandFormField> page = assetsExpandFormFieldService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsExpandFormFieldService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsExpandFormField( @RequestBody AssetsExpandFormField assetsExpandFormField){

        return assetsExpandFormFieldService.addAssetsExpandFormField(assetsExpandFormField);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsExpandFormField(AssetsExpandFormField assetsExpandFormField){

        return assetsExpandFormFieldService.editAssetsExpandFormField(assetsExpandFormField);
    }

    /**
     * 根据formId，查询所有的field
     * @param assetsTypeId
     * @return
     */
    @RequestMapping("getFormByType.do")
    @ResponseBody
    public ResultInfo getFormByType(Integer assetsTypeId){
        AssetsType assetsType = assetsTypeService.selectById(assetsTypeId);
        List<AssetsExpandForm> assetsExpandForms = expandFormService.getAllExpandFormByFormId(assetsType.getForm_id());
        List<AssetsExpandFormField> assetsExpandFormFields = assetsExpandFormFieldService.getAllFields(assetsExpandForms);
        return ResultInfo.success(assetsExpandFormFields);
    }

    @RequestMapping("delFormField.do")
    @ResponseBody
    public ResultInfo delFormField(@RequestBody AssetsExpandFormField assetsExpandFormField){
        return assetsExpandFormFieldService.delFormFiled(assetsExpandFormField.getId());
    }
}
