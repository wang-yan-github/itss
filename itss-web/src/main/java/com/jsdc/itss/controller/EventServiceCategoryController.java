package com.jsdc.itss.controller;


import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.EventServiceCategory;
import com.jsdc.itss.service.EventServiceCategoryService;
import com.jsdc.itss.vo.EventServiceCategoryVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: EventServiceCategoryController
 * Description:
 *
 * @author xuaolong
 */

@RestController
@RequestMapping("eventservicecategory")
public class EventServiceCategoryController {

    @Autowired
    private EventServiceCategoryService eventServiceCategoryService;

//    /**
//     * create by xuaolong
//     * description:自动服务类别管理查询
//     * @return ResultInfo
//     */
//    @RequestMapping(value = "selectList.do")
//    @ResponseBody
//    public ResultInfo selectList(){
//        return eventServiceCategoryService.getList();
//    }


    /**
     * create by xuaolong
     * description:添加自动服务类别管理
     *
     * @param eventServiceCategoryVo
     * @return
     */
    @RequestMapping(value = "addServiceCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addServiceCategory(@RequestBody EventServiceCategoryVo eventServiceCategoryVo) {
        return eventServiceCategoryService.addServiceCategory(eventServiceCategoryVo);
    }


    /**
     * create by xuaolong
     * description:根据ID查询自动服务类别管理
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "selectServiceCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo selectOneServiceCategory(int id) {
        return eventServiceCategoryService.getOneServiceCategory(id);
    }


    /**
     * create by xuaolong
     * description:修改自动服务类别管理
     *
     * @param eventServiceCategoryVo
     * @return
     */
    @RequestMapping(value = "updateServiceCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateServiceCategory(@RequestBody EventServiceCategoryVo eventServiceCategoryVo) {
        return eventServiceCategoryService.updateServiceCategory(eventServiceCategoryVo);
    }

    /**
     * create by xuaolong
     * description:递归删除自动服务类别管理
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteServiceCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteServiceCategory(int id) {
        return eventServiceCategoryService.deleteServiceCategory(id);
    }

    /**
     * description:自动服务类别管理导出excel
     *
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping(value = "exporEventServiceCategoryExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public void exporEventServiceCategoryExcel(HttpServletResponse response) {
        eventServiceCategoryService.exporEventServiceCategoryExcel(response);
    }


    /**
     * 自助服务类别  树形结构  列表接口
     * Author wzn
     * Date 2022/3/23 14:29
     */
    @RequestMapping(value = "tree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult treeInfo(Integer id) {
        return AjaxResult.success(eventServiceCategoryService.tree(id));
    }

    /**
     * 自助服务类别  树形结构  列表接口 根据登录人
     * Author wzn
     * Date 2022/3/23 14:29
     */
    @RequestMapping(value = "treeByUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult treeByUser(EventServiceCategory eventServiceCategory) {
        return AjaxResult.success(eventServiceCategoryService.treeByUser(eventServiceCategory, null));
    }


}
