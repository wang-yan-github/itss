package com.jsdc.itss.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.alibaba.fastjson.JSONObject;
import com.jsdc.core.base.BaseController;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MainController
 * Description:
 * date: 2022/3/12 9:53
 *
 * @author bn
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String loginPost(@RequestBody Map<String, Object> params) {
        String username = (String) params.get("username");
        String password=(String) params.get("password");
        JSONObject jsonObject = new JSONObject();
        if (empty(username)) {
            jsonObject.put("success", false);
            jsonObject.put("msg", "用户名不能为空");
        }
        if (empty(password)) {
            jsonObject.put("success", false);
            jsonObject.put("msg", "密码不能为空");
        }
        if (notEmpty(username) && notEmpty(password)) {
            SaTokenInfo token = userService.login(username, password);
            if (null != token) {

                System.out.println(token.getTokenValue());
                SysUser user = userService.getUser();
                Map<String, List<String>> map = userService.getPermissions(user.getId());
                jsonObject.put("code",200);
                jsonObject.put("success", true);
                jsonObject.put("msg", "登录成功");
                jsonObject.put("token", token);
                jsonObject.put("authority", map);
                jsonObject.put("user", user);
            } else {
                jsonObject.put("success", false);
                jsonObject.put("msg", "用户名或密码错误");
            }
        }

        return jsonObject.toJSONString();
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping("logout.do")
    @ResponseBody
    public ResultInfo logout(){
        return userService.logout();
    }

}
