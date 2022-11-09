package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsManageUser;
import com.jsdc.itss.service.AssetsManageUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author libin
 * @create 2022-03-15 11:17:23
 */
@Controller
@RequestMapping("/assetsManageUser")
public class AssetsManageUserController extends BaseController {

    @Autowired
    private AssetsManageUserService assetsManageUserService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, AssetsManageUser beanParam) {
        PageInfo<AssetsManageUser> page = assetsManageUserService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }
     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsManageUserService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsManageUser(AssetsManageUser assetsManageUser){

        return assetsManageUserService.addAssetsManageUser(assetsManageUser);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsManageUser(AssetsManageUser assetsManageUser){

        return assetsManageUserService.editAssetsManageUser(assetsManageUser);
    }
}
