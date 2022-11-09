package com.jsdc.itss.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jsdc.core.base.Base;
import com.jsdc.itss.common.aop.logaop.LogInfo;
import com.jsdc.itss.common.utils.MD5Utils;
import com.jsdc.itss.enums.LogEnums;
import com.jsdc.itss.exception.AjaxResult;
import com.jsdc.itss.model.SysDepartment;
import com.jsdc.itss.model.SysRole;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.service.SysDepartmentService;
import com.jsdc.itss.service.SysRoleService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.LogVo;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SysUserController
 * Description:
 * date: 2022/1/8 19:03
 *
 * @author wp
 */
@RestController
@RequestMapping("sysuser")
public class SysUserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private SysRoleService roleService;


    /**
     * create by xuaolong
     * description: 分页查询
     *
     * @param sysUser
     * @param pageIndex
     * @param pageSize
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("getPage.do")
    public ResultInfo getPage(SysUser sysUser,
                              @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                              @RequestParam(defaultValue = "10") Integer pageSize) {

        return userService.getuserPage(sysUser, pageIndex, pageSize);
    }

    /**
     * 得到服务群组下的用户集合
     * description: 分页查询
     */
    @RequestMapping("getGroupPage.do")
    public ResultInfo getGroupPage(SysUser sysUser,
                                   @RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {

        return userService.getGroupPage(sysUser, pageIndex, pageSize);
    }

    @RequestMapping(value = "toList.do", method = RequestMethod.POST)
    public ResultInfo toList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize, SysUser beanParam) {
        return userService.getPage(beanParam, pageIndex, pageSize);
    }

    /**
     * 用户全数据
     *
     * @return
     */
    @GetMapping(value = "getUserList.do")
    public ResultInfo getUserList() {
        return ResultInfo.success(userService.getUsers());
    }

    /**
     * create by xuaolong
     * description: 新增用户
     *
     * @param sysUserVo
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("save.do")

    public ResultInfo save(@RequestBody SysUserVo sysUserVo) {
        return userService.addSysUser(sysUserVo);
    }


    /**
     * create by xuaolong
     * description: 根据用户ID查询用户信息
     *
     * @param
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("getUser.do")
    public ResultInfo getUser(Integer ids) {
        return ResultInfo.success(userService.getUser(ids));
    }

    /**
     * 查询当前登录的用户信息
     *
     * @return
     */
    @RequestMapping("userInfo.do")
    @ResponseBody
//    @LogInfo(LogEnums.LOG_EVENT)
    public ResultInfo getUserInfo() {
        SysUser sysUser = userService.getUser();
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        Map<String, List<String>> map = userService.getPermissions(sysUser.getId());
        List<String> permission = map.get("menuStr");
        List<String> authorities = map.get("buttonStr");
        result.put("permissions", permission);
        result.put("authorities", authorities);
        result.put("username", sysUser.getUser_name());
        result.put("avatar", "https://i.gtimg.cn/club/item/face/img/8/15918_100.gif");
        return ResultInfo.success(result);
    }

    @RequestMapping("getAllPermission.do/{id}")
    @ResponseBody
    public ResultInfo getAllPermission(@PathVariable Integer id) {
        List<Integer> permission = roleService.getAuthorityByRole(id);
        return ResultInfo.success(permission);
    }

    /**
     * 获取当前登陆的用户信息
     *
     * @return
     */
    @RequestMapping("getCurrentLoginUserInfo.do")
    @ResponseBody
    public ResultInfo getCurrentLoginUserInfo() {
        SysUser sysUser = userService.getUser();
        sysUser.setPass("");//消除敏感数据
        if (Base.notEmpty(sysUser.getDepartment_id())) {
            SysDepartment sysDepartment = sysDepartmentService.selectById(sysUser.getDepartment_id());
            sysUser.setDepartmentName(sysDepartment.getName());
        }
        return ResultInfo.success(sysUser);
    }

    /**
     * create by xuaolong
     * description: 修改
     *
     * @param sysUserVo
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("edit.do")
    public ResultInfo edit(@RequestBody SysUserVo sysUserVo) {
        return userService.edit(sysUserVo);
    }


    /**
     * create by xuaolong
     * description: 删除用户信息
     *
     * @param ids
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("deleteUser.do")
    public ResultInfo deleteUser(Integer ids) {
        return ResultInfo.success(userService.deleteByUserId(ids));
    }


    /**
     * create by xuaolong
     * description: 设置角色
     *
     * @param
     * @return com.jsdc.zhtc.vo.ResultInfo
     */
    @RequestMapping("addUserRole.do")
    public ResultInfo addUserRole(@RequestBody SysUser sysUser) {
        List<SysRole> sysRoles = sysUser.getSysRoles();
        List<Integer> roleIdList = new ArrayList<>();
        for (SysRole temp : sysRoles) {
            Integer id = temp.getId();
            roleIdList.add(id);
        }
        return ResultInfo.success(userService.setUserRoleById(sysUser.getId(), roleIdList));
    }


    /**
     * create by xuaolong
     * description:获取用户对应的权限
     *
     * @param
     * @return
     */
    @RequestMapping("getSysAuthority.do")
    @ResponseBody
    public ResultInfo getSysAuthority(Integer ids) {
        return userService.selectSysAuthority(ids);
    }

    /**
     * create by xuaolong
     * description:用户导出excel
     *
     * @param response
     * @return
     */
    @PostMapping("exporUserExcel.do")
    public void exporUserExcel(HttpServletResponse response) {
        userService.exporUserExcel(response);
    }


    /**
     * create by xuaolong
     * description:角色导出excel
     *
     * @param response
     * @return
     */
    @PostMapping("exporUserRoleExcel.do")
    public void exporUserRoleExcel(HttpServletResponse response) {
        userService.exporUserRoleExcel(response);
    }


    /**
     * Excel用户导入模板下载
     * author xuaolong
     *
     * @param response
     * @return
     */
    @PostMapping("downloadUser")
    public void downloadUser(HttpServletResponse response) {
        String filename = "用户信息模板.xlsx";
        int len = 0;
        //5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        //通过response对象获取OutputStream流
        try {

            ClassPathResource classPathResource = new ClassPathResource("templates/用户信息模板.xlsx");
            InputStream in = classPathResource.getInputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            OutputStream out = response.getOutputStream();
            //将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Excel导入知识
     * author xuaolong
     *
     * @param file
     * @return
     */
    @RequestMapping("importUserList")
    @ResponseBody
    public ResultInfo importUserList(@RequestParam("file") MultipartFile file) {
        return userService.importUserList(file);
    }


    /**
     * 工程师下拉数据接口
     * Author wzn
     * Date 2022/3/22 9:54
     */
    @RequestMapping("/upUserData")
    @ResponseBody
    public AjaxResult upUserData() {
        return AjaxResult.success(userService.upUserData());
    }

    /**
     * 根据ids查询工程师
     * Author wzn
     * Date 2022/3/22 16:39
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public AjaxResult getUserList(String ids) {
        return AjaxResult.success(userService.getUserList(ids));
    }

    /**
     * 修改密码
     *
     * @param bean
     * @return
     */
    @RequestMapping("updatePass")
    @ResponseBody
    public ResultInfo importUserList(@RequestBody SysUserVo bean) {
        SysUser sysUser = userService.selectById(userService.getUser().getId());
        String oldPass = MD5Utils.getMD5(bean.getOldPass());
        if (!oldPass.equals(sysUser.getPass())) {
            return ResultInfo.error("旧密码错误！");
        } else if (oldPass.equals(MD5Utils.getMD5(bean.getNewPass()))) {
            return ResultInfo.error("新密码不能与旧密码一致！");
        } else if (!bean.getNewPass().equals(bean.getConfirmPass())) {
            return ResultInfo.error("新密码与确认密码不一致！");
        } else {
            sysUser.setPass(MD5Utils.getMD5(bean.getNewPass()));
            userService.updateById(sysUser);
            return ResultInfo.success("修改密码成功,请重新登录！");
        }
    }
}
