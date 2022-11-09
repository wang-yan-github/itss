package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysBackgroundPicDao;
import com.jsdc.itss.mapper.SysBackgroundPicMapper;
import com.jsdc.itss.mapper.SysBackgroundPicSettingMapper;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.SysBackgroundPic;
import com.jsdc.itss.model.SysBackgroundPicSetting;
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
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysBackgroundPicService extends BaseService<SysBackgroundPicDao, SysBackgroundPic> {

    @Autowired
    private SysBackgroundPicMapper sysBackgroundPicMapper;
    @Autowired
    private SysBackgroundPicDao sysBackgroundPicDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysBackgroundPicSettingMapper sysBackgroundPicSettingMapper;
    @Autowired
    private FileManageService fileManageService;
    @Value("${file.upload-path}")
    private String uploadPath;

    public PageInfo<SysBackgroundPic> toList(Integer pageIndex, Integer pageSize, SysBackgroundPic beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<SysBackgroundPic> sysBackgroundPicVos = sysBackgroundPicMapper.toList(beanParam);

        for (SysBackgroundPic temp : sysBackgroundPicVos){
            List<FileManage> fileManageList = new ArrayList<>();
            if (null != temp.getPic_id()){
                FileManage fileManage = fileManageService.selectById(temp.getPic_id());
                String path = uploadPath + File.separator + fileManage.getFile_name();
                File file = new File(path);
                String base64 = null == FileUtils.fileToByte(file)?"":new BASE64Encoder().encode(FileUtils.fileToByte(file));
                fileManage.setBase64(base64);
                fileManageList.add(fileManage);
                temp.setFileManageList(fileManageList);
            }

        }


        PageInfo<SysBackgroundPic> page = new PageInfo<>(sysBackgroundPicVos);

        return page;
    }


    public ResultInfo getById(Integer id) {
        QueryWrapper<SysBackgroundPic> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("id",id);
        SysBackgroundPic sysBackgroundPic = selectOne(queryWrapper);
        return ResultInfo.success(sysBackgroundPic);
    }

    /**
     *  添加
     */
    public ResultInfo addSysBackgroundPic(SysBackgroundPic bean, MultipartFile files) {
        if (bean.getId() == null || bean .getId().equals("")){
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);

            if(null != files){
                int id = fileManageService.fileUpload(files);
                bean.setPic_id(id);
                updateById(bean);

            }

            return ResultInfo.success();
        }else{
            return editSysBackgroundPic(bean,files);
        }

    }

    /**
     *  编辑
     */
    public ResultInfo editSysBackgroundPic(SysBackgroundPic bean,MultipartFile files) {
        // 修改者
        bean.setUpdate_user(sysUserService.getUser().getId());
        // 修改时间
        bean.setUpdate_time(new Date());
        if(null != files){
            int id = fileManageService.fileUpload(files);
            bean.setPic_id(id);
        }
        updateById(bean);
        return ResultInfo.success();
    }

    /**
     * 删除
     */
    public ResultInfo deleteSysBackgroundPic(Integer id) {
        UpdateWrapper<SysBackgroundPic> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("is_del","1");
        sysBackgroundPicMapper.update(null,updateWrapper);
        return ResultInfo.success();
    }

    public  ResultInfo showSetting(){
        QueryWrapper<SysBackgroundPicSetting> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        SysBackgroundPicSetting sysBackgroundPicSetting = sysBackgroundPicSettingMapper.selectOne(queryWrapper);
        return ResultInfo.success(sysBackgroundPicSetting);
    }

    public  ResultInfo updateSetting(SysBackgroundPicSetting sysBackgroundPicSetting){
        sysBackgroundPicSetting.setUpdate_user(sysUserService.getUser().getId());
        sysBackgroundPicSetting.setUpdate_time(new Date());
        sysBackgroundPicSettingMapper.updateById(sysBackgroundPicSetting);
        return ResultInfo.success();
    }
}
