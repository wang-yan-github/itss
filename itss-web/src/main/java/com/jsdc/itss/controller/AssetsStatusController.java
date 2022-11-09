package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsStatus;
import com.jsdc.itss.service.AssetsStatusService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * ClassName: AssetsStatusController
 * Description: 资产状态管理
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("assetsStatus")
public class AssetsStatusController {
    @Autowired
    private AssetsStatusService assetsStatusService;

    @Autowired
    private SysUserService sysUserService;


    /**
     *  列表查询
     * @author wh
     * @return
     */
    @RequestMapping(value = "toList.do",method = RequestMethod.POST)
    public ResultInfo toList(@RequestBody AssetsStatus assetsStatus){

        PageInfo<AssetsStatus> publishCategoryInfoPageInfo = assetsStatusService.toList(
                assetsStatus.getPageNo(), assetsStatus.getPageSize(), assetsStatus);
        return ResultInfo.success(publishCategoryInfoPageInfo);
    }

    /**
     *  资产状态ID查询
     * @author wh
     * @param id
     * @return
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsStatusService.getById(id);
    }

    /**
     *  添加资产状态
     * @author wh
     * @param assetsStatus
     * @return
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsStatus(AssetsStatus assetsStatus){

        return assetsStatusService.addAssetsStatus(assetsStatus);
    }

    /**
     *  编辑资产状态
     * @author wh
     * @param assetsStatus
     * @return
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsStatus(AssetsStatus assetsStatus){

        return assetsStatusService.editAssetsStatus(assetsStatus);
    }

    /**
     * 保存功能
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    public ResultInfo saveAssetsStatus(@RequestBody AssetsStatus assetsStatus){
        if(null == assetsStatus){
            return ResultInfo.error("非法入参");
        }
        if(null != assetsStatus.getId() && null != assetsStatusService.selectById(assetsStatus.getId())){

            return assetsStatusService.editAssetsStatus(assetsStatus);
        }
        return assetsStatusService.addAssetsStatus(assetsStatus);
    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    public ResultInfo deleteAssetsStatus(@RequestBody AssetsStatus assetsStatus){
        if(null == assetsStatus){
            return ResultInfo.error("非法入参");
        }
        int result = assetsStatusService.update(null, Wrappers.<AssetsStatus>lambdaUpdate()
                .set(AssetsStatus::getIs_del, String.valueOf(1))
                .set(AssetsStatus::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsStatus::getUpdate_time, new Date())
                .eq(AssetsStatus::getId, assetsStatus.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }

    /**
     * 查询所有配置状态
     * @return
     */
    @RequestMapping("getAll.do")
    public ResultInfo getAll(){
        return ResultInfo.success(assetsStatusService.getAll());
    }
}
