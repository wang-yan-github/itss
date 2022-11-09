package com.jsdc.itss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.QuestionFile;
import org.apache.ibatis.annotations.Mapper;
import com.jsdc.itss.dao.QuestionFileDao;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 问题管理 附件
 *
 * @Author thr
 * @create 2022-04-14 14:12:37
 */
@Mapper
public interface QuestionFileMapper extends BaseMapper<QuestionFile> {

    @SelectProvider(method = "toList", type = QuestionFileDao.class)
    List<QuestionFile> toList(QuestionFile beanParam);

    @SelectProvider(method = "getFileManageByType", type = QuestionFileDao.class)
    List<FileManage> getFileManageByType(int questionId, int type);
}