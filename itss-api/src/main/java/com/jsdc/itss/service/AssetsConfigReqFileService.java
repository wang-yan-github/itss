package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.AssetsConfigReqFileDao;
import com.jsdc.itss.dao.ChangeFileDao;
import com.jsdc.itss.mapper.AssetsConfigReqFileMapper;
import com.jsdc.itss.mapper.ChangeFileMapper;
import com.jsdc.itss.model.AssetsConfigReqFile;
import com.jsdc.itss.model.ChangeFile;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * ClassName: AssetsConfigReqFileService
 * Description:
 * date: 2022/4/18 8:50
 *
 * @author bn
 */
@Service
@Transactional
public class AssetsConfigReqFileService extends BaseService<AssetsConfigReqFileDao, AssetsConfigReqFile> {


    @Autowired
    AssetsConfigReqFileMapper assetsConfigReqFileMapper;

    @Autowired
    SysUserService sysUserService;


    public ResultInfo add(AssetsConfigReqFile bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(sysUserService.getUser().getId());
        insert(bean);
        return ResultInfo.success();

    }

    public List<FileManage> getFileManage(Integer id) {

        return  assetsConfigReqFileMapper.getFileManage(id);
    }

}
