package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsRequestConfig;
import com.jsdc.itss.service.AssetsRequestConfigService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 配置请求类型管理控制器
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Controller
@RequestMapping("/assetsRequestConfig")
public class AssetsRequestConfigController extends BaseController {

    @Autowired
    private AssetsRequestConfigService assetsRequestConfigService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询,无分页数据
     * @author zdq
     * @date 2022/3/24
     */
    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo list(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Integer status,
                             AssetsRequestConfig beanParam) {
        beanParam.setRequest_name(name);
        beanParam.setIs_enable(status);

        return ResultInfo.success(assetsRequestConfigService.selectList(Wrappers.<AssetsRequestConfig>lambdaQuery()
                .eq(AssetsRequestConfig::getIs_del,String.valueOf(0))
                .like(StringUtils.isNotBlank(name), AssetsRequestConfig::getRequest_name, name)
                .eq(null != status, AssetsRequestConfig::getIs_enable, status)
        ));
    }

    /**
     * 列表查询
     * @author zdq
     * @date 2022/3/24
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsRequestConfig beanParam) {
        PageInfo<AssetsRequestConfig> page = assetsRequestConfigService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }


    @RequestMapping(value = "getAll.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAll(AssetsRequestConfig beanParam) {



        return ResultInfo.success(assetsRequestConfigService.getAll(beanParam));
    }


    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestBody AssetsRequestConfig bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }

        if(null != bean.getId() && null != assetsRequestConfigService.selectById(bean.getId())){

            return assetsRequestConfigService.editAssetsRequestConfig(bean);
        }
        return assetsRequestConfigService.addAssetsRequestConfig(bean);

    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsRequestConfig bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }
        int result = assetsRequestConfigService.update(null, Wrappers.<AssetsRequestConfig>lambdaUpdate()
                .set(AssetsRequestConfig::getIs_del, String.valueOf(1))
                .set(AssetsRequestConfig::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsRequestConfig::getUpdate_time, new Date())
                .eq(AssetsRequestConfig::getId, bean.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }

     /**
     *  ID查询
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsRequestConfigService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsRequestConfig(AssetsRequestConfig assetsRequestConfig){

        return assetsRequestConfigService.addAssetsRequestConfig(assetsRequestConfig);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsRequestConfig(AssetsRequestConfig assetsRequestConfig){

        return assetsRequestConfigService.editAssetsRequestConfig(assetsRequestConfig);
    }
}
