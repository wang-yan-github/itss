package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsManageHistory;
import com.jsdc.itss.service.AssetsManageHistoryService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author libin
 * @create 2022-03-16 11:42:47
 */
@Controller
@RequestMapping("/assetsManageHistory")
public class AssetsManageHistoryController extends BaseController {

    @Autowired
    private AssetsManageHistoryService assetsManageHistoryService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody AssetsManageHistory beanParam) {
        PageInfo<AssetsManageHistory> page = assetsManageHistoryService.toList(beanParam.getPageNo(), beanParam.getPageSize(),beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsManageHistoryService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsManageHistory(AssetsManageHistory assetsManageHistory){

        return assetsManageHistoryService.addAssetsManageHistory(assetsManageHistory);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsManageHistory(AssetsManageHistory assetsManageHistory){

        return assetsManageHistoryService.editAssetsManageHistory(assetsManageHistory);
    }
}
