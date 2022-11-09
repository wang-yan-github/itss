package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.PublishCategoryInfo;
import com.jsdc.itss.service.PublishCategoryInfoService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: PublishCategoryInfoController
 * Description:
 * date: 2022/3/7 15:03
 *
 * @author wh
 */
@RestController
@RequestMapping("publishCategory")
public class PublishCategoryInfoController {
    @Autowired
    private PublishCategoryInfoService publishCategoryInfoService;

    /**
     * 列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @author wh
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             PublishCategoryInfo publishCategoryInfo) {

        PageInfo<PublishCategoryInfo> publishCategoryInfoPageInfo = publishCategoryInfoService.toList(pageIndex, pageSize, publishCategoryInfo);
        return ResultInfo.success(publishCategoryInfoPageInfo);
    }


    /**
     * 获取所有在用发布类别
     * @return
     * @author wh
     */
    @RequestMapping(value = "getAllList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllList( @RequestBody PublishCategoryInfo publishCategoryInfo) {

        List<PublishCategoryInfo> publishCategoryInfoPageInfo = publishCategoryInfoService.getAllList( publishCategoryInfo);
        return ResultInfo.success(publishCategoryInfoPageInfo);
    }


    /**
     * 添加发布类别
     *
     * @param publishCategoryInfo
     * @return
     * @author wh
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addPublishCategory(@RequestBody PublishCategoryInfo publishCategoryInfo) {

        return publishCategoryInfoService.addPublishCategory(publishCategoryInfo);
    }

    /**
     * 编辑发布类别
     *
     * @param publishCategoryInfo
     * @return
     * @author wh
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editPublishCategory(@RequestBody PublishCategoryInfo publishCategoryInfo) {

        return publishCategoryInfoService.editPublishCategory(publishCategoryInfo);
    }

    /**
     * 获取发布类别评审信息
     *
     * @param publishCategoryInfo
     * @return
     * @author wh
     */
    @RequestMapping(value = "getPublishCategory.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getPublishCategory(@RequestBody PublishCategoryInfo publishCategoryInfo) {
        return publishCategoryInfoService.getPublishCategory(publishCategoryInfo);
    }
}
