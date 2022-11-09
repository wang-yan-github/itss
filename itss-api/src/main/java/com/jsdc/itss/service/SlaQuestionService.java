package com.jsdc.itss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SlaQuestionDao;
import com.jsdc.itss.mapper.SlaQuestionConfigMapper;
import com.jsdc.itss.mapper.SlaQuestionMapper;
import com.jsdc.itss.model.SlaQuestion;
import com.jsdc.itss.model.SlaQuestionConfig;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SlaQuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SlaQuestionService
 * Description:
 * date: 2022/3/7 14:33
 *
 * @author bn
 */
@Transactional
@Service
public class SlaQuestionService extends BaseService<SlaQuestionDao, SlaQuestion> {

    @Autowired
    private SlaQuestionMapper slaQuestionMapper;

    @Autowired
    private SlaQuestionConfigService slaQuestionConfigService;

    @Autowired
    private SlaQuestionConfigMapper slaQuestionConfigMapper;
    @Autowired
    private SysUserService sysUserService;

    public PageInfo<SlaQuestion> toList(Integer pageIndex, Integer pageSize, SlaQuestion slaQuestion) {
        PageHelper.startPage(pageIndex, pageSize);
        QueryWrapper qw = new QueryWrapper<SlaQuestion>();
        if(StringUtils.isNotEmpty(slaQuestion.getSla_question_name())){
            qw.like("sla_question_name",slaQuestion.getSla_question_name());
        }
        qw.eq("is_del", "0");
        qw.orderByDesc("id");
        List<SlaQuestion> list = selectList(qw);

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Transactional
    public ResultInfo toAdd(String sla_question) {
        JSONObject jsonObject = JSON.parseObject(sla_question);
        SlaQuestionVo slaQuestionVo = JSONObject.
                parseObject(jsonObject.toString(), SlaQuestionVo.class);
        QueryWrapper<SlaQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0).eq("sla_question_name",slaQuestionVo.getSla_question_name());
        Long count = slaQuestionMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已经存在！");
        }
        SlaQuestion slaQuestion = new SlaQuestion();
        BeanUtils.copyProperties(slaQuestionVo, slaQuestion);
        slaQuestion.setCreate_user(sysUserService.getUser().getId());
        slaQuestion.setCreate_time(new Date());
        slaQuestion.setIs_del(String.valueOf(0));
        if (insert(slaQuestion) > 0) {
            slaQuestionVo.getSlaQuestionConfigVos().forEach(x -> {
                x.setQuestion_id(slaQuestion.getId());
                x.setCreate_time(new Date());
                x.setCreate_user(sysUserService.getUser().getId());
                x.setIs_del(String.valueOf(0));
                slaQuestionConfigService.insert(x);
            });
        }
        return ResultInfo.success();
    }


    public ResultInfo toEdit(String sla_question) {

        JSONObject jsonObject = JSON.parseObject(sla_question);

        SlaQuestionVo slaQuestionVo = JSONObject.
                parseObject(jsonObject.toString(), SlaQuestionVo.class);
        QueryWrapper<SlaQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", 0).eq("sla_question_name",slaQuestionVo.getSla_question_name()).ne("id",slaQuestionVo.getId());
        Long count = slaQuestionMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("名称已经存在！");
        }

        SlaQuestion slaQuestion = new SlaQuestion();
        BeanUtils.copyProperties(slaQuestionVo, slaQuestion);
        slaQuestion.setUpdate_user(sysUserService.getUser().getId());
        slaQuestion.setUpdate_time(new Date());
        if (updateById(slaQuestion) > 0) {
            // 逻辑删除之前的配置数据
            UpdateWrapper<SlaQuestionConfig> wrapper = new UpdateWrapper<>();
            wrapper.lambda().set(SlaQuestionConfig::getIs_del, "1").
                    eq(SlaQuestionConfig::getQuestion_id, slaQuestion.getId());
            slaQuestionConfigService.update(null, wrapper);

            slaQuestionVo.getSlaQuestionConfigVos().forEach(x -> {
                if (x.getId() != null) {
                    x.setUpdate_time(new Date());
                    x.setUpdate_user(sysUserService.getUser().getId());
                    x.setIs_del(String.valueOf(0));
                    slaQuestionConfigService.updateById(x);
                } else {
                    x.setQuestion_id(slaQuestion.getId());
                    x.setCreate_time(new Date());
                    x.setIs_del(String.valueOf(0));
                    slaQuestionConfigService.insert(x);
                }
            });
            return ResultInfo.success("操作成功");
        }
        return ResultInfo.error("操作失败");

    }

    public ResultInfo toDel(SlaQuestion slaQuestion) {
        slaQuestion.setUpdate_time(new Date());
        slaQuestion.setUpdate_user(sysUserService.getUser().getId());
        if (updateById(slaQuestion) > 0) {
            // 逻辑删除之前的配置数据
            QueryWrapper<SlaQuestionConfig> questionConfigQueryWrapper = new QueryWrapper<>();
            questionConfigQueryWrapper.eq("question_id", slaQuestion.getId());
            List<SlaQuestionConfig> slaQuestionConfigs = slaQuestionConfigService.selectList(questionConfigQueryWrapper);
            slaQuestionConfigs.forEach(x -> {
                x.setUpdate_time(new Date());
                x.setUpdate_user(sysUserService.getUser().getId());
                x.setIs_del(String.valueOf(1));
                slaQuestionConfigService.updateById(x);
            });
            return ResultInfo.success("操作成功");
        }
        return ResultInfo.error("操作失败");

    }

    public ResultInfo getSlaQuestion(SlaQuestion slaQuestion) {
        List<SlaQuestionVo> slaQuestionVos = slaQuestionMapper.getSlaQuestion(slaQuestion);

        slaQuestionVos.forEach(x -> {
            x.setSlaQuestionConfigVos(slaQuestionConfigMapper.getSlaQuestionConf(x));
        });

        return ResultInfo.success(slaQuestionVos);

    }

    public List<SlaQuestion> getSlaQuestionByCategoryId(Integer questionCategoryId){
        return slaQuestionMapper.getSlaQuestionByCategoryId(questionCategoryId);
    }
}
