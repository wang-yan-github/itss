package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsExpandForm;
import com.jsdc.itss.service.AssetsExpandFormService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsExpandForm")
public class AssetsExpandFormController extends BaseController {

    @Autowired
    private AssetsExpandFormService assetsExpandFormService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsExpandForm beanParam) {
        PageInfo<AssetsExpandForm> page = assetsExpandFormService.toList(pageIndex, pageSize, beanParam);
        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsExpandFormService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsExpandForm(@RequestBody AssetsExpandForm assetsExpandForm){
        return assetsExpandFormService.addAssetsExpandForm(assetsExpandForm);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsExpandForm(AssetsExpandForm assetsExpandForm){

        return assetsExpandFormService.editAssetsExpandForm(assetsExpandForm);
    }

    @RequestMapping(value = "getAllList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllList(@RequestBody AssetsExpandForm assetsExpandForm){
        QueryWrapper<AssetsExpandForm> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        if (assetsExpandForm.getName()!=null){
            queryWrapper.eq("name",assetsExpandForm.getName());
        }
        List<AssetsExpandForm> list = assetsExpandFormService.selectList(queryWrapper);
        return ResultInfo.success(list);
    }


    @RequestMapping(value = "toTree.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toTree( AssetsExpandForm beanParam) {
        List<AssetsExpandForm> tree = assetsExpandFormService.getTree( beanParam);
        return ResultInfo.success(tree);
    }

    @RequestMapping(value = "toDelete.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toDelete(@RequestBody AssetsExpandForm assetsExpandForm) {
        Integer id = assetsExpandForm.getId();
        return assetsExpandFormService.deleteOneForm(id);
    }
}
