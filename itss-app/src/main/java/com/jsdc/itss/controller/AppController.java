package com.jsdc.itss.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsdc.core.base.Base;
import com.jsdc.itss.model.SysAuthority;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.vo.ResultInfo;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String loginPost(String userName, String passWord, String openid) {
        JSONObject jsonObject = new JSONObject();
        if (Base.empty(userName)) {
            jsonObject.put("success", false);
            jsonObject.put("msg", "用户名不能为空");
        }
        if (Base.empty(passWord)) {
            jsonObject.put("success", false);
            jsonObject.put("msg", "密码不能为空");
        }
        if (Base.notEmpty(userName) && Base.notEmpty(passWord)) {
            SysUser sysUser = sysUserService.login(userName, passWord, openid);
            if (null != sysUser) {
                List<String> list = sysUserService.getAppPermissions(sysUser.getId());
                jsonObject.put("code", 200);
                jsonObject.put("success", true);
                jsonObject.put("msg", "登录成功");
                jsonObject.put("authority", list);
                jsonObject.put("user", sysUser);
            } else {
                jsonObject.put("success", false);
                jsonObject.put("msg", "用户名或密码错误");
            }
        }

        return jsonObject.toJSONString();
    }

    /**
     * 获取用户信息
     *
     * @param openid
     * @return
     */
    @RequestMapping("getUserinfo.do")
    @ResponseBody
    public JSONObject getUserinfo(String openid) {
        SysUser sysUser = sysUserService.getUserByOpenid(openid);
        List<String> list = sysUserService.getAppPermissions(sysUser.getId());
        JSONObject result = new JSONObject();
        result.put("userinfo", sysUser);
        result.put("authority", list);
        return result;
    }

    @SneakyThrows
    @RequestMapping("wxLogin.do")
    @ResponseBody
    public String wxLogin(@RequestParam(name = "code") String code) {
        System.out.println("code" + code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wxde0dd7d23b24b0eb";//自己的appid
        url += "&secret=ce29ea5532520bcb70c6e3f3c6a68ae2";//自己的appSecret
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);    //GET方式
        CloseableHttpResponse response = null;
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(false).build();           // 将上面的配置信息 运用到这个Get请求里
        httpget.setConfig(requestConfig);                         // 由客户端执行(发送)Get请求
        response = httpClient.execute(httpget);                   // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + res);
        }
        // 释放资源
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        JSONObject jo = JSON.parseObject(res);
        String openid = jo.getString("openid");
        System.out.println("openid" + openid);
        return openid;
    }

    /**
     * 检验微信账号是否登录过
     *
     * @param openId
     * @return
     */
    @RequestMapping("checkOpenid.do")
    @ResponseBody
    public ResultInfo checkOpenid(String openId) {
        return sysUserService.checkOpenid(openId);
    }

    /**
     * 登出
     */
    @RequestMapping("logout.do")
    @ResponseBody
    public ResultInfo logout() {
        return sysUserService.logout();
    }

}
