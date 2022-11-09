package com.jsdc.itss.controller;

import com.jsdc.itss.model.FileManage;
import com.jsdc.itss.service.FileManageService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileManageService fileManageService;

    /**
     * @description 文件上传
     * @param file
     * @param request
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/21
    */
    @RequestMapping("uploadFile.do")
    public ResultInfo fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return ResultInfo.success(fileManageService.fileUpload(file));
    }

    /**
     * @description 多文件上传
     * @param files
     * @param request
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/21
    */
    @RequestMapping("uploadFiles.do")
    public ResultInfo filesUpload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request){
        return fileManageService.filesUpload(files);
    }

    @RequestMapping("downloadFile.do")
    public void downloadFile(@RequestBody Integer fileId, HttpServletResponse response){
        fileManageService.fileDownload(fileId, response);
    }



    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getAllFiles(@RequestParam(defaultValue = "1",value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize,FileManage fileManage){
        return fileManageService.getAllFile(pageIndex, pageSize, fileManage);
    }
}
