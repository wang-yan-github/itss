package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsOsLicence;
import com.jsdc.itss.service.AssetsOsLicenceService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.utils.DateUtils;
import com.jsdc.itss.vo.AssetsOsLicenceVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
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
@RequestMapping("/assetsOsLicence")
public class AssetsOsLicenceController extends BaseController {

    @Autowired
    private AssetsOsLicenceService assetsOsLicenceService;

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             AssetsOsLicence beanParam) {
        beanParam.setOs_licence_name(name);
        PageInfo<AssetsOsLicenceVo> page = assetsOsLicenceService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestBody AssetsOsLicenceVo bean){
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

            AssetsOsLicence assetsOsLicence = new AssetsOsLicence();
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(assetsOsLicence, bean);
            if(null != bean.getId() && null != assetsOsLicenceService.selectById(bean.getId())){

                return assetsOsLicenceService.editAssetsOsLicence(assetsOsLicence);
            }
            return assetsOsLicenceService.addAssetsOsLicence(assetsOsLicence);
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
    public ResultInfo removeById(@RequestBody AssetsOsLicence bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }
        int result = assetsOsLicenceService.update(null, Wrappers.<AssetsOsLicence>lambdaUpdate()
                .set(AssetsOsLicence::getIs_del, String.valueOf(1))
                .set(AssetsOsLicence::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsOsLicence::getUpdate_time, new Date())
                .eq(AssetsOsLicence::getId, bean.getId()));
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
        return assetsOsLicenceService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsOsLicence(AssetsOsLicence assetsOsLicence){

        return assetsOsLicenceService.addAssetsOsLicence(assetsOsLicence);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsOsLicence(AssetsOsLicence assetsOsLicence){

        return assetsOsLicenceService.editAssetsOsLicence(assetsOsLicence);
    }

    @RequestMapping("getAll.do")
    @ResponseBody
    public ResultInfo getAll(){
        return ResultInfo.success(assetsOsLicenceService.getAll());
    }
}
