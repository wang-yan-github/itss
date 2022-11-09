package com.jsdc.itss.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.itss.enums.DataType;
import com.jsdc.itss.model.AssetsConfigReq;
import com.jsdc.itss.model.ChangeInfo;
import com.jsdc.itss.model.PublishBasicInfo;
import com.jsdc.itss.model.Question;
import com.jsdc.itss.service.AssetsConfigReqService;
import com.jsdc.itss.service.ChangeInfoService;
import com.jsdc.itss.service.PublishBasicInfoService;
import com.jsdc.itss.service.QuestionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: DataUtils
 * Description: 数据工具类
 * date: 2022/3/14 14:42
 *
 * @author bn
 */
@Component
public class DataUtils {


    private static ChangeInfoService changeInfoService;

    private static PublishBasicInfoService publishBasicInfoService;

    private static QuestionManageService questionManageService;

    private static AssetsConfigReqService assetsConfigReqService;



    public DataUtils(@Autowired ChangeInfoService changeInfoService,
                     @Autowired QuestionManageService questionManageService,
                     @Autowired PublishBasicInfoService publishBasicInfoService,
                     @Autowired AssetsConfigReqService assetsConfigReqService) {
        DataUtils.changeInfoService = changeInfoService;
        DataUtils.questionManageService = questionManageService;
        DataUtils.publishBasicInfoService = publishBasicInfoService;
        DataUtils.assetsConfigReqService =assetsConfigReqService;
    }

    /**
     *  时间格式化yyyyMMdd
     * @return
     */
    public static String getToday(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    /**
     * 生成编号
     *
     * @param dataType
     * @return
     */
    public static String getNo(DataType dataType) {

        String itss_no = null;

        switch (dataType) {
            case CHANGE_TYPE:
                itss_no = String.format("%s%s%03d", DataType.change_no,getToday() ,
                        changeInfoService.selectCount(new QueryWrapper<ChangeInfo>().
                                apply(true, "TO_DAYS(NOW())-TO_DAYS(create_time) = 0")) + 1);
                break;
            case QUESTION_TYPE:
                itss_no = String.format("%s%s%03d", DataType.question_no, getToday() ,
                        questionManageService.selectCount(new QueryWrapper<Question>().
                                apply(true, "TO_DAYS(NOW())-TO_DAYS(create_time) = 0")) + 1);
                break;
            case PUBLISH_TYPE:
                itss_no = String.format("%s%s%03d", DataType.publish_no, getToday(),
                        publishBasicInfoService.selectCount(new QueryWrapper<PublishBasicInfo>().
                                apply(true, "TO_DAYS(NOW())-TO_DAYS(create_time) = 0")) + 1);
                break;
            case ASSETS_RQ_TYPE:
                itss_no= String.format("%s%s%03d", DataType.assets_rq_no, getToday(),
                        assetsConfigReqService.selectCount(new QueryWrapper<AssetsConfigReq>().
                                apply(true, "TO_DAYS(NOW())-TO_DAYS(create_time) = 0")) + 1);
                break;

        }

        return itss_no;
    }

    public static void main(String[] args) {
        System.out.println(getNo(DataType.CHANGE_TYPE));
    }
}
