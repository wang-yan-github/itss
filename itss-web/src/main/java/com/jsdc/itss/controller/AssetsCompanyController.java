package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.AssetsCompany;
import com.jsdc.itss.service.AssetsCompanyService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * ClassName: AssetsCompanyController
 * Description: 购置公司管理
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("assetsCompany")
public class AssetsCompanyController {
    @Autowired
    private AssetsCompanyService assetsCompanyService;

    @Autowired
    private SysUserService sysUserService;

    /**
     *  列表查询
     * @author wh
     * @param assetsCompany
     * @return
     */
    @RequestMapping(value = "toList.do",method = RequestMethod.POST)
    public ResultInfo toList(@RequestBody AssetsCompany assetsCompany){
        PageInfo<AssetsCompany> assetsCompanyPageInfo = assetsCompanyService.toList(
                assetsCompany.getPageNo(), assetsCompany.getPageSize(), assetsCompany);
        return ResultInfo.success(assetsCompanyPageInfo);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    public ResultInfo saveAssetsStatus(@RequestBody AssetsCompany assetsCompany){
        if(null == assetsCompany){
            return ResultInfo.error("非法入参");
        }
        if(null != assetsCompany.getId() && null != assetsCompanyService.selectById(assetsCompany.getId())){

            return assetsCompanyService.editAssetsCompany(assetsCompany);
        }
        return assetsCompanyService.addAssetsCompany(assetsCompany);
    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    public ResultInfo deleteAssetsStatus(@RequestBody AssetsCompany assetsCompany){
        if(null == assetsCompany){
            return ResultInfo.error("非法入参");
        }
        int result = assetsCompanyService.update(null, Wrappers.<AssetsCompany>lambdaUpdate()
                .set(AssetsCompany::getIs_del, String.valueOf(1))
                .set(AssetsCompany::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsCompany::getUpdate_time, new Date())
                .eq(AssetsCompany::getId, assetsCompany.getId()));
        if(result > 0){
            return ResultInfo.success("删除成功");
        }

        return ResultInfo.error("数据错误");
    }

    /**
     *  购置公司ID查询
     * @author wh
     * @param id
     * @return
     */
    @RequestMapping(value = "getById.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getById(Integer id){
        return assetsCompanyService.getById(id);
    }

    /**
     *  添加购置公司
     * @author wh
     * @param assetsCompany
     * @return
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsCompany(AssetsCompany assetsCompany){

        return assetsCompanyService.addAssetsCompany(assetsCompany);
    }

    /**
     *  编辑资产状态
     * @author wh
     * @param assetsCompany
     * @return
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsCompany(AssetsCompany assetsCompany){

        return assetsCompanyService.editAssetsCompany(assetsCompany);
    }

    @RequestMapping(value = "getAll.do")
    @ResponseBody
    public ResultInfo getAll(){
        return ResultInfo.success(assetsCompanyService.getAll());
    }
}
