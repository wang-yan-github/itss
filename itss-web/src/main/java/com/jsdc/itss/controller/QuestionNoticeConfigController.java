package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.QuestionNoticeConfig;
import com.jsdc.itss.service.QuestionNoticeConfigService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: QuestionNoticeConfig
 * Description: 问题通知配置表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@RestController
@RequestMapping("questionNoticeConfig")
public class QuestionNoticeConfigController {
    @Autowired
    private QuestionNoticeConfigService questionNoticeConfigService;



    /**
     * 获取类型对应的数据
     */
    @RequestMapping(value = "getOneInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getOneInfo(@RequestBody QuestionNoticeConfig questionNoticeConfig) {
        return questionNoticeConfigService.getOneInfo(questionNoticeConfig);
    }



    /**
     * 保存/更新
     */
    @RequestMapping(value = "saveOrUpd.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveOrUpd(@RequestBody QuestionNoticeConfig bean) {
        return questionNoticeConfigService.saveOrUpd(bean);
    }

}
