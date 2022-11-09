package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SlaEventFirst;
import com.jsdc.itss.model.SlaQuestion;
import com.jsdc.itss.model.SlaQuestionFirst;
import com.jsdc.itss.service.SlaQuestionFirstService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SlaQuestionFirstController
 * Description:
 * date: 2022/3/7 14:25
 *
 * @author bn
 */
@Controller
@RequestMapping("slaQuestionFirst")
public class SlaQuestionFirstController extends BaseController {

    @Autowired
    private SlaQuestionFirstService slaQuestionFirstService;


    /**
     * 列表查询
     *
     * @param pageIndex
     * @param pageSize
     * @param slaQuestionFirst
     * @return
     * @author bn
     */
    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toList(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             SlaQuestionFirst slaQuestionFirst) {
        PageInfo<SlaQuestionFirst> page = slaQuestionFirstService.toList(pageIndex, pageSize, slaQuestionFirst);
        return ResultInfo.success(page);
    }


    /**
     * 列表查询
     *
     * @param slaQuestionFirst
     * @return
     * @author thr
     */
    @RequestMapping(value = "getList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getList(SlaQuestionFirst slaQuestionFirst) {

        List<SlaQuestionFirst> list = slaQuestionFirstService.getList(slaQuestionFirst);

        return ResultInfo.success(list);
    }

    /**
     * 添加
     *
     * @param slaQuestionFirst
     * @return
     */
    @RequestMapping(value = "toAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toAdd(SlaQuestionFirst slaQuestionFirst) {
        return slaQuestionFirstService.toAdd(slaQuestionFirst);
    }

    /**
     * 编辑
     *
     * @param slaQuestionFirst
     * @return
     */
    @RequestMapping(value = "toEdit.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo toEdit(SlaQuestionFirst slaQuestionFirst) {
        return slaQuestionFirstService.toEdit(slaQuestionFirst);
    }
}
