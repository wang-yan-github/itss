package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.service.AssetsConfigReqService;
import com.jsdc.itss.vo.AssetsConfigReqVo;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 配置请求
 *
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsConfigReq")
public class AssetsConfigReqController extends BaseController {

    @Autowired
    private AssetsConfigReqService assetsConfigReqService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsConfigReqVo beanParam) {
        PageInfo<AssetsConfigReqVo> page = assetsConfigReqService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * ID查询
     */
    @RequestMapping(value = "getAssetsConfigReq.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAssetsConfigReq(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.getAssetsConfigReq(beanParam);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsConfigReq(@RequestBody AssetsConfigReqVo beanParam) {

        return assetsConfigReqService.addAssetsConfigReq(beanParam);
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsConfigReq(@RequestBody AssetsConfigReqVo beanParam) {

        return assetsConfigReqService.editAssetsConfigReq(beanParam);
    }

    /**
     * 撤销
     *
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toCancel.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toCancel(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.toCancel(beanParam);
    }

    /**
     * 处理
     *
     * @param beanParam
     * @return
     */
    @RequestMapping(value = "toResult.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toResult(@RequestBody AssetsConfigReqVo beanParam) {
        return assetsConfigReqService.toResult(beanParam);
    }

}
