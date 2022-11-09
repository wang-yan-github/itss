package com.jsdc.itss.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.FileManageDao;
import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.model.SysBackgroundPic;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.vo.ResultInfo;
import lombok.Cleanup;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileManageService extends BaseService<FileManageDao, FileManage> {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * @description 文件上传
     * @param file
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/21
    */
    public Integer fileUpload(MultipartFile file){

        //如果父文件夹不存在 则新建
        File parentFile = new File(uploadPath);
        if (!parentFile.exists() && !parentFile.isDirectory()) {
            parentFile.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String path = uploadPath+ File.separator+fileName;
        File targetFile = new File(path);
        try{
            @Cleanup FileOutputStream outputStream = new FileOutputStream(targetFile);
            outputStream.write(file.getBytes());
        }catch (Exception e){

        }
        long fileSize = file.getSize();
        SysUser sysUser = userService.getUser();
        FileManage fileManage = new FileManage();
        fileManage.setFile_name(fileName);
        fileManage.setFile_size(fileSize == 0 ? "0" : String.valueOf(fileSize));
        fileManage.setFile_type(suffix);
        fileManage.setCreate_user(sysUser.getId());
        fileManage.setCreate_time(new Date());
        fileManage.setUpdate_user(sysUser.getId());
        fileManage.setUpdate_time(new Date());
        fileManage.setIs_del("0");
        if(insert(fileManage) > 0){
            return fileManage.getId();
        }else{
            return null;
        }
    }

    /**
     * @description 多文件上传
     * @param files
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/21
    */
    public ResultInfo filesUpload(MultipartFile[] files){
        for(MultipartFile file : files){
            fileUpload(file);
        }
        return ResultInfo.success();
    }

    public void fileDownload(Integer fileId, HttpServletResponse response){


        FileManage fileManage = selectById(fileId);
        String path = uploadPath + File.separator + fileManage.getFile_name();
        File file = new File(path);
        try {
            @Cleanup FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            byte[] data = os.toByteArray();
            response.resetBuffer();
            response.resetBuffer();
            response.setHeader("Content-Disposition", "attachment");
            response.addHeader("file-name", URLEncoder.encode(file.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data,response.getOutputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private Map<Integer, SysUser> getUserNameMap() {
        List<SysUser> users = sysUserService.selectList(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIs_del, "0"));
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity(), (key1, key2) -> key2));
    }

    public ResultInfo getAllFile(Integer pageIndex,Integer pageSize , FileManage fileManage){
        QueryWrapper<FileManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        if (!StringUtils.isEmpty(fileManage.getFile_type())){
            queryWrapper.like("file_type",fileManage.getFile_type());
        }

        if (!StringUtils.isEmpty(fileManage.getFile_name())){
            queryWrapper.like("file_name",fileManage.getFile_name());
        }


        if (!StringUtils.isEmpty(fileManage.getStart_time())){
            queryWrapper.gt("create_time",fileManage.getStart_time()+" 00:00:00 ");
        }

        if (!StringUtils.isEmpty(fileManage.getEnd_time()) ){
            queryWrapper.lt("create_time",fileManage.getEnd_time()+" 23:59:59");
        }


        PageHelper.startPage(pageIndex, pageSize);
        List<FileManage> list = this.selectList(queryWrapper);
        Map<Integer, SysUser> userMap = getUserNameMap();
        for (FileManage temp : list){
            if (null != temp.getCreate_user()){
                SysUser sysUser  = userMap.get(temp.getCreate_user());
                if (null != sysUser){
                    temp.setCreate_user_name(sysUser.getName());
                }
            }
        }

        PageInfo<FileManage> page = new PageInfo<>(list);
        return ResultInfo.success(page);
    }
}
