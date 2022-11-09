package com.jsdc.itss.controller;

import com.github.pagehelper.PageInfo;
import com.jsdc.itss.model.ChangeNoticeConfig;
import com.jsdc.itss.service.ChangeNoticeConfigService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: ChangeNoticeConfig
 * Description: 变更类别表
 * date: 2022/03/07 15:10
 *
 * @author thr
 */
@RestController
@RequestMapping("changeNoticeConfig")
public class ChangeNoticeConfigController {
    @Autowired
    private ChangeNoticeConfigService changeNoticeConfigService;


    /**
     * 获取对象
     */
    @RequestMapping(value = "getOneByType.do", method = RequestMethod.POST)
    public ResultInfo getOneByType(@RequestBody  ChangeNoticeConfig changeNoticeConfig) {
        return changeNoticeConfigService.getOneByType(changeNoticeConfig);
    }



    /**
     * 更新
     */
    @RequestMapping(value = "saveOrUpd.do", method = RequestMethod.POST)
    public ResultInfo saveOrUpd(@RequestBody ChangeNoticeConfig bean) {
        return changeNoticeConfigService.saveOrUpd(bean);
    }

}
