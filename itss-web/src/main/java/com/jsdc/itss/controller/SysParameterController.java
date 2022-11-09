package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSON;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysBackgroundPic;
import com.jsdc.itss.model.SysParameter;
import com.jsdc.itss.service.SysParameterService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>添加描述信息<p>
 *
 * @author 徐傲龙
 * @since 2022年04月13日 15:41
 */
@Controller
@RequestMapping("/sysParameter")
public class SysParameterController extends BaseController {

    @Autowired
    private SysParameterService sysParameterService;

    @RequestMapping(value = "getOneInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getOneInfo(){
        SysParameter sysParameter = sysParameterService.getOneInfo();
        return ResultInfo.success(sysParameter);
    }

    @RequestMapping(value = "updateOne.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateOne(@RequestParam String body, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4){
        SysParameter sysParameter = JSON.parseObject(body, SysParameter.class);
        return sysParameterService.updateOne(sysParameter,file1,file2,file3,file4);
    }

}
