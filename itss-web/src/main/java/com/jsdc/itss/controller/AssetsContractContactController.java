package com.jsdc.itss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.AssetsContractContact;
import com.jsdc.itss.service.AssetsContractContactService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 合同联系人管理 控制器
 * @Author libin
 * @create 2022-03-08 09:11:30
 */
@Controller
@RequestMapping("/assetsContractContact")
public class AssetsContractContactController extends BaseController {

    @Autowired
    private AssetsContractContactService assetsContractContactService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表查询
     *
     * zdq
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             AssetsContractContact beanParam) {
        PageInfo<AssetsContractContact> page = assetsContractContactService.toList(pageIndex, pageSize, beanParam);

        return ResultInfo.success(page);
    }

    /**
     * 保存功能 新增＋编辑
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toSave.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveBean(@RequestBody AssetsContractContact bean){
        if(null == bean){
            return ResultInfo.error("非法入参");
        }

        if(null != bean.getId() && null != assetsContractContactService.selectById(bean.getId())){

            return assetsContractContactService.editAssetsContractContact(bean);
        }
        return assetsContractContactService.addAssetsContractContact(bean);

    }

    /**
     * 删除
     * @author zdq
     * @date 2022/3/23
     */
    @RequestMapping(value = "toDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo removeById(@RequestBody AssetsContractContact bean){
        if(null == bean || null == bean.getId()){
            return ResultInfo.error("非法入参");
        }
        int result = assetsContractContactService.update(null, Wrappers.<AssetsContractContact>lambdaUpdate()
                .set(AssetsContractContact::getIs_del, String.valueOf(1))
                .set(AssetsContractContact::getUpdate_user, sysUserService.getUser().getId())
                .set(AssetsContractContact::getUpdate_time, new Date())
                .eq(AssetsContractContact::getId, bean.getId()));
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
        return assetsContractContactService.getById(id);
    }

    /**
     *  添加
     */
    @RequestMapping(value = "toAdd.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addAssetsContractContact(AssetsContractContact assetsContractContact){

        return assetsContractContactService.addAssetsContractContact(assetsContractContact);
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "toEdit.do",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editAssetsContractContact(AssetsContractContact assetsContractContact){

        return assetsContractContactService.editAssetsContractContact(assetsContractContact);
    }
}
