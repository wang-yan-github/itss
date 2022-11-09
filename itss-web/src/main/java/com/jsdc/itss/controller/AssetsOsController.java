package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsOs;
import com.jsdc.itss.service.AssetsOsService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: AssetsOsController
 * Description: 操作系统管理控制器
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("assetsOs")
public class AssetsOsController {
    @Autowired
    private AssetsOsService assetsOsService;

    @Autowired
    private SysUserService sysUserService;

    /**
     *  操作系统列表查询
     * @author wh
     * @param pageIndex
     * @param pageSize
     * @param assetsOs
     * @return
     */
    @RequestMapping(value = "toList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             AssetsOs assetsOs){
        assetsOs.setOs_name(name);
        PageInfo<AssetsOs> assetsOsPageInfo = assetsOsService.toList(pageIndex,pageSize,assetsOs);
        return ResultInfo.success(assetsOsPageInfo);
    }

    /**
     *  操作系统列表查询-下拉框
     * @author wh
     * @return
     */
    @RequestMapping(value = "getList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(){
       List<AssetsOs> list =  assetsOsService.selectList(new QueryWrapper<AssetsOs>().eq("is_del","0").orderByAsc("sort"));
       return ResultInfo.success(list);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    public ResultInfo saveBean(@RequestBody AssetsOs bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        if(null != bean.getId() && null != assetsOsService.selectById(bean.getId())){

            return assetsOsService.editAssetsOs(bean);
        }
        return assetsOsService.addAssetsOs(bean);
    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    public ResultInfo removeById(@RequestBody AssetsOs bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        int result = assetsOsService.update(null, Wrappers.<AssetsOs>lambdaUpdate()
                .set(AssetsOs::getIs_del, String.valueOf(1))
                .set(AssetsOs::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsOs::getUpdate_time, new Date())
                .eq(AssetsOs::getId, bean.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }

    /**
     *  操作系统ID查询
     * @author wh
     * @param id
     * @return
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsOsService.getById(id);
    }

    /**
     *  添加操作系统
     * @author wh
     * @param assetsOs
     * @return
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsOs(AssetsOs assetsOs){

        return assetsOsService.addAssetsOs(assetsOs);
    }

    /**
     *  编辑操作系统
     * @author wh
     * @param assetsOs
     * @return
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsOs(AssetsOs assetsOs){

        return assetsOsService.editAssetsOs(assetsOs);
    }
}
