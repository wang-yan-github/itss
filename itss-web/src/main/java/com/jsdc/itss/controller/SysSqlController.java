package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysSql;
import com.jsdc.itss.service.SysSqlService;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysSqlVo;
import com.xiaoleilu.hutool.lang.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Controller
@RequestMapping("/sysSql")
public class SysSqlController extends BaseController {

    @Autowired
    private SysSqlService sysSqlService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestBody SysSql beanParam) {
        SysSqlVo vo = sysSqlService.toList(beanParam.getPageNo(), beanParam.getPageSize(), beanParam);
        return ResultInfo.success(vo);
    }

    //详情
    @RequestMapping(value = "details.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo details(@RequestBody SysSql bean) {
        return ResultInfo.success(sysSqlService.details(bean, bean.getPageNo(), bean.getPageSize()));
    }

    /**
     * ID查询
     */
    @RequestMapping(value = "getById.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(@RequestBody String id) {
        return ResultInfo.success(sysSqlService.getById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addSysSql(@RequestBody SysSql sysSql) {
        return sysSqlService.addSysSql(sysSql);
    }


    /**
     * 编辑
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSysSql(@RequestBody SysSql sysSql) {

        return sysSqlService.editSysSql(sysSql);
    }


    /**
     * 删除
     */
    @RequestMapping(value = "deleteSysSql.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteSysSql(@RequestBody Integer id) {
        Integer count = sysSqlService.deleteSysSql(id);
        if (count > 0) {
            return ResultInfo.success("删除成功！");
        } else {
            return ResultInfo.error("删除失败！");
        }
    }

    //禁用启用
    @RequestMapping(value = "stateUpd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo stateUpd(@RequestBody SysSql sysSql) {
        Integer count = sysSqlService.stateUpd(sysSql.getId(), sysSql.getIs_use());
        return ResultInfo.success(count);
    }

    //导出功能
    @PostMapping("sysSqlExport.do")
    public ResultInfo sysSqlExport(HttpServletResponse response, @RequestBody Integer id) {
        return ResultInfo.success(sysSqlService.sysSqlExport(response, id));
    }

}
