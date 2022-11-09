package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsBackup;
import com.jsdc.itss.service.AssetsBackupService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 备份方式管理 控制器
 * @Author libin
 * @create 2022-03-08 12:19:43
 */
@Controller
@RequestMapping("/assetsBackup")
public class AssetsBackupController extends BaseController {

    @Autowired
    private AssetsBackupService assetsBackupService;

    @Autowired
    private SysUserService sysUserService;


    /**
     * 列表查询
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             AssetsBackup beanParam) {
        beanParam.setBackup_name(name);
        PageInfo<AssetsBackup> page = assetsBackupService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestBody AssetsBackup bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }

        if(null != bean.getId() && null != assetsBackupService.selectById(bean.getId())){

            return assetsBackupService.editAssetsBackup(bean);
        }
        return assetsBackupService.addAssetsBackup(bean);

    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsBackup bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }
        int result = assetsBackupService.update(null, Wrappers.<AssetsBackup>lambdaUpdate()
                .set(AssetsBackup::getIs_del, String.valueOf(1))
                .set(AssetsBackup::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsBackup::getUpdate_time, new Date())
                .eq(AssetsBackup::getId, bean.getId()));
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
        return assetsBackupService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsBackup(AssetsBackup assetsBackup){

        return assetsBackupService.addAssetsBackup(assetsBackup);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsBackup(AssetsBackup assetsBackup){

        return assetsBackupService.editAssetsBackup(assetsBackup);
    }
}
