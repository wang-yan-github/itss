package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsConfigReqProperty;
import com.jsdc.itss.service.AssetsConfigReqPropertyService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 配置请求资产中间程表
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsConfigReqProperty")
public class AssetsConfigReqPropertyController extends BaseController {

    @Autowired
    private AssetsConfigReqPropertyService assetsConfigReqPropertyService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsConfigReqProperty beanParam) {
        PageInfo<AssetsConfigReqProperty> page = assetsConfigReqPropertyService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * ID查询
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id) {
        return assetsConfigReqPropertyService.getById(id);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsConfigReqProperty(AssetsConfigReqProperty assetsConfigReqProperty) {

        return assetsConfigReqPropertyService.addAssetsConfigReqProperty(assetsConfigReqProperty);
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsConfigReqProperty(AssetsConfigReqProperty assetsConfigReqProperty) {

        return assetsConfigReqPropertyService.editAssetsConfigReqProperty(assetsConfigReqProperty);
    }
}
