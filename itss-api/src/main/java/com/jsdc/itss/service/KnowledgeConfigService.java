package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.KnowledgeConfigDao;
import com.jsdc.itss.model.KnowledgeConfig;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Service
@Transactional
public class KnowledgeConfigService extends BaseService<KnowledgeConfigDao, KnowledgeConfig> {

    /**
     * 获取详情
     * @return
     */
    public ResultInfo getDetail(){
        List<KnowledgeConfig> configs = this.selectList(null);
        if(configs != null && configs.size() > 0){
            return ResultInfo.success(configs.get(0));
        }
        return ResultInfo.success();
    }


    /**
     * 新增 or 修改
     * @param knowledgeConfig
     * @return
     */
    public ResultInfo addOrUpdate(KnowledgeConfig knowledgeConfig){
        if(knowledgeConfig.getId() != null){
            knowledgeConfig.updateById();
        }else{
            knowledgeConfig.insert();
        }
        return ResultInfo.success();
    }

}
