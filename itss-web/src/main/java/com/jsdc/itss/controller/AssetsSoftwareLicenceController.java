package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsSoftwareLicence;
import com.jsdc.itss.service.AssetsSoftwareLicenceService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.utils.DateUtils;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Controller
@RequestMapping("/assetsSoftwareLicence")
public class AssetsSoftwareLicenceController extends BaseController {

    @Autowired
    private AssetsSoftwareLicenceService assetsSoftwareLicenceService;

    @Autowired
    private SysUserService sysUserService;




    /**
     * 列表查询
     * @author zdq
     * @date 2022/3/24
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             AssetsSoftwareLicence beanParam) {
        beanParam.setOs_licence_name(name);
        PageInfo<AssetsSoftwareLicence> page = assetsSoftwareLicenceService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     *  全部数据
     * @param
     * @return
     */
    @RequestMapping(value = "getAllSoftWare.do")
    @ResponseBody
    public ResultInfo getAllSoftWare(){
        return ResultInfo.success(assetsSoftwareLicenceService.getAllSoftWare());
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestBody AssetsSoftwareLicence bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        try {
            if(StringUtils.isNotBlank(bean.getStarTime()) && bean.getStarTime().indexOf("T") != -1){
                String start_time = bean.getStarTime().substring(0, bean.getStarTime().indexOf("T"));
                bean.setStart_time(DateUtils.toStrDate(start_time, "yyyy-MM-dd"));
            }
            if(StringUtils.isNotBlank(bean.getEndTime()) && bean.getEndTime().indexOf("T") != -1){
                String end_time = bean.getEndTime().substring(0, bean.getEndTime().indexOf("T"));
                bean.setEnd_time(DateUtils.toStrDate(end_time, "yyyy-MM-dd"));
            }

            if(null != bean.getId() && null != assetsSoftwareLicenceService.selectById(bean.getId())){

                return assetsSoftwareLicenceService.editAssetsSoftwareLicence(bean);
            }
            return assetsSoftwareLicenceService.addAssetsSoftwareLicence(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  ResultInfo.error("错误插入");
    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsSoftwareLicence bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }
        int result = assetsSoftwareLicenceService.update(null, Wrappers.<AssetsSoftwareLicence>lambdaUpdate()
                .set(AssetsSoftwareLicence::getIs_del, String.valueOf(1))
                .set(AssetsSoftwareLicence::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsSoftwareLicence::getUpdate_time, new Date())
                .eq(AssetsSoftwareLicence::getId, bean.getId()));
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
        return assetsSoftwareLicenceService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsSoftwareLicence(AssetsSoftwareLicence assetsSoftwareLicence){

        return assetsSoftwareLicenceService.addAssetsSoftwareLicence(assetsSoftwareLicence);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsSoftwareLicence(AssetsSoftwareLicence assetsSoftwareLicence){

        return assetsSoftwareLicenceService.editAssetsSoftwareLicence(assetsSoftwareLicence);
    }

    /**
     * 查询软件
     */
    @RequestMapping(value = "getAssetsManageList.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAssetsManageList(){
        return ResultInfo.success(assetsSoftwareLicenceService.getAssetsManageList());
    }
}
