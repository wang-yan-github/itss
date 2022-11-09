package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.dao.KnowledgeInfoDao;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.model.vo.KnowledgeVo;
import com.jsdc.itss.vo.KnowledgeInfoVo;
import com.jsdc.itss.vo.KnowledgePageInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @Author xujian
 * @create 2022-03-07 17:53:56
 */
@Mapper
public interface KnowledgeInfoMapper extends BaseMapper<KnowledgeInfo> {


    @SelectProvider(method = "selectKnowledgeByPage",type = KnowledgeInfoDao.class)
    List<KnowledgePageInfoVo> selectKnowledgeByPage(KnowledgeInfo knowledgeInfo,int userId);

    @SelectProvider(method = "getStatusKnowledgeByPage",type = KnowledgeInfoDao.class)
    List<KnowledgePageInfoVo> getStatusKnowledgeByPage(KnowledgeInfo knowledgeInfo);


    @SelectProvider(method = "getKnowledgeById",type = KnowledgeInfoDao.class)
    KnowledgeInfoVo getKnowledgeById(int id );


    @SelectProvider(method = "contributionScoreStatistics",type = KnowledgeInfoDao.class)
    List<KnowledgeVo> contributionScoreStatistics();

    @SelectProvider(method = "departmentOfKnowledge",type = KnowledgeInfoDao.class)
    List<KnowledgeVo> departmentOfKnowledge();



}