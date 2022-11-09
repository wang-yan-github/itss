package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysParameterDao;
import com.jsdc.itss.dao.SysUserDao;
import com.jsdc.itss.mapper.SysParameterMapper;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.SysParameter;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.utils.FileUtils;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>添加描述信息<p>
 *
 * @author 徐傲龙
 * @since 2022年04月13日 15:39
 */
@Service
@Transactional
@SuppressWarnings("ALL")
public class SysParameterService extends BaseService<SysParameterDao, SysParameter> {

    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileManageService fileManageService;
    @Value("${file.upload-path}")
    private String uploadPath;


    public SysParameter getOneInfo(){
        QueryWrapper<SysParameter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        SysParameter sysParameter =  sysParameterMapper.selectOne(queryWrapper);
        List<FileManage> fileManageList1 = new ArrayList<>();
        List<FileManage> fileManageList2 = new ArrayList<>();
        List<FileManage> fileManageList3 = new ArrayList<>();
        List<FileManage> fileManageList4 = new ArrayList<>();
        if (null != sysParameter){
            if (null != sysParameter.getPrincipal_sheet_pic()){
                FileManage fileManage = getFileManage(sysParameter.getPrincipal_sheet_pic());
                fileManageList1.add(fileManage);
                sysParameter.setFileManageList1(fileManageList1);
            }
            if (null != sysParameter.getLand_logo_pic()){
                FileManage fileManage = getFileManage(sysParameter.getLand_logo_pic());
                fileManageList2.add(fileManage);
                sysParameter.setFileManageList2(fileManageList2);
            }
            if (null != sysParameter.getFavicon_pic()){
                FileManage fileManage = getFileManage(sysParameter.getFavicon_pic());
                fileManageList3.add(fileManage);
                sysParameter.setFileManageList3(fileManageList3);
            }
            if (null != sysParameter.getLand_code_pic()){
                FileManage fileManage = getFileManage(sysParameter.getLand_code_pic());
                fileManageList4.add(fileManage);
                sysParameter.setFileManageList4(fileManageList4);
            }
        }

        return sysParameter;
    }

    private FileManage getFileManage(Integer id) {
        FileManage fileManage = fileManageService.selectById(id);
        if(null != fileManage){
            String path = uploadPath + File.separator + fileManage.getFile_name();
            File file = new File(path);
            String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
            fileManage.setBase64(base64);
            return fileManage;
        }else {
            return new FileManage();
        }
    }


    public ResultInfo updateOne(SysParameter sysParameter, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4){

        if (null != sysParameter.getId()){
            sysParameter.setUpdate_time(new Date());
            sysParameter.setUpdate_user(sysUserService.getUser().getId());
            sysParameter.setIs_del("1");
            sysParameterMapper.updateById(sysParameter);
        }

        sysParameter.setId(null);
        sysParameter.setIs_del("0");
        if(null != file1){
            int id = fileManageService.fileUpload(file1);
            sysParameter.setPrincipal_sheet_pic(id);
        }
        if(null != file2){
            int id = fileManageService.fileUpload(file2);
            sysParameter.setLand_logo_pic(id);
        }
        if(null != file3){
            int id = fileManageService.fileUpload(file3);
            sysParameter.setFavicon_pic(id);
        }
        if(null != file4){
            int id = fileManageService.fileUpload(file4);
            sysParameter.setLand_code_pic(id);
        }

        sysParameterMapper.insert(sysParameter);
        return ResultInfo.success();

    }

}
