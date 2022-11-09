package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsUseScope;
import com.jsdc.itss.service.AssetsUseScopeService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * ClassName: AssetsUseScopeController
 * Description: 使用范围管理控制器
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("assetsUseScope")
public class AssetsUseScopeController {
    @Autowired
    private AssetsUseScopeService assetsUseScopeService;

    @Autowired
    private SysUserService sysUserService;

    /**
     *  列表查询
     * @author wh
     * @param pageIndex
     * @param pageSize
     * @param assetsUseScope
     * @return
     */
    @RequestMapping(value = "toList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String scope_name,
                             AssetsUseScope assetsUseScope){
        assetsUseScope.setScope_name(scope_name);
        PageInfo<AssetsUseScope> assetsUseScopePageInfo = assetsUseScopeService.toList(pageIndex,pageSize,assetsUseScope);
        return ResultInfo.success(assetsUseScopePageInfo);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    public ResultInfo saveBean(@RequestBody AssetsUseScope assetsUseScope){
        if(null == assetsUseScope){
            return ResultInfo.error("非法入参");
        }
        if(null != assetsUseScope.getId() && null != assetsUseScopeService.selectById(assetsUseScope.getId())){

            return assetsUseScopeService.editAssetsCompany(assetsUseScope);
        }
        return assetsUseScopeService.addAssetsUseScope(assetsUseScope);
    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    public ResultInfo removeById(@RequestBody AssetsUseScope assetsUseScope){
        if(null == assetsUseScope){
            return ResultInfo.error("非法入参");
        }
        int result = assetsUseScopeService.update(null, Wrappers.<AssetsUseScope>lambdaUpdate()
                .set(AssetsUseScope::getIs_del, String.valueOf(1))
                .set(AssetsUseScope::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsUseScope::getUpdate_time, new Date())
                .eq(AssetsUseScope::getId, assetsUseScope.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }

    /**
     *  使用范围ID查询
     * @author wh
     * @param id
     * @return
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsUseScopeService.getById(id);
    }

    /**
     *  添加使用范围
     * @author wh
     * @param assetsUseScope
     * @return
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsUseScope(AssetsUseScope assetsUseScope){

        return assetsUseScopeService.addAssetsUseScope(assetsUseScope);
    }

    /**
     *  编辑使用范围
     * @author wh
     * @param assetsUseScope
     * @return
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsCompany(AssetsUseScope assetsUseScope){

        return assetsUseScopeService.editAssetsCompany(assetsUseScope);
    }

    @RequestMapping(value = "getAll.do")
    @ResponseBody
    public ResultInfo getAll(){

        return ResultInfo.success(assetsUseScopeService.getAll());
    }
}
