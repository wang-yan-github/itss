package com.jsdc.itss.init;

import com.jsdc.itss.model.SysDict;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.service.SysDictService;
import com.jsdc.itss.service.SysUserService;
import com.jsdc.itss.utils.RedisUtils;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisDataInit {

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysUserService userService;

    /**
     * @description 初始化数据

     * @return void
     * @author wp
     * @date 2022/3/23
    */
    public void init(){
        dictInit();
        userInit();
    }
    /**
     * @description 初始化字典数据，缓存redis
     * @return void
     * @author wp
     * @date 2022/3/23
    */
    public void dictInit(){
        List<SysDict> dicts = sysDictService.getAllDicts();
        Map<String, HashMap> dicData = new HashMap<>();
        dicts.forEach(x->{
            if(dicData.containsKey(x.getDict_type())){
                HashMap hashMap = dicData.get(x.getDict_type());
                hashMap.put(x.getValue(),x);
            }else{
                HashMap hashMap = new HashMap();
                hashMap.put(x.getValue(),x);
                dicData.put(x.getDict_type(),hashMap);
            }
        });
        RedisUtils.setBeanValue("dictData",dicData);
    }

    /**
     * @description 更新字典缓存
     * @param sysDict
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/23
    */
    public ResultInfo updateDictCache(SysDict sysDict){
        try{
            //获取缓存数据
            Map<String,HashMap> dictData = (Map<String, HashMap>) RedisUtils.getBeanValue("dictData");
            if(dictData.containsKey(sysDict.getDict_type())){
                if("1".equals(sysDict.getIs_del())){
                    HashMap dictMap = dictData.get(sysDict.getDict_type());
                    dictMap.remove(sysDict.getValue());
                }else{
                    HashMap dictMap = dictData.get(sysDict.getDict_type());
                    dictMap.put(sysDict.getValue(),sysDict);
                }

            }else{
                HashMap hashMap = new HashMap();
                hashMap.put(sysDict.getValue(),sysDict);
                dictData.put(sysDict.getDict_type(),hashMap);
            }
            RedisUtils.setBeanValue("dictData",dictData);
        }catch (Exception e){
            e.printStackTrace();
            return ResultInfo.error("字典缓存更新失败！");
        }

        return ResultInfo.success();
    }

    /**
     * @description 用户信息缓存

     * @return void
     * @author wp
     * @date 2022/3/23
    */
    public void userInit(){
        List<SysUser> userList = userService.getUsers();
        Map<Integer, SysUser> map = new HashMap<>();
        userList.forEach(x -> {
            int userId = x.getId();
            map.put(userId, x);
        });
        RedisUtils.setBeanValue("userData", map);
    }


    /**
     * @description 用户信息缓存更新
     * @param user
     * @return void
     * @author wp
     * @date 2022/3/23
    */
    public void updateUserCache(SysUser user){
        Map<Integer, SysUser> userData = (Map<Integer, SysUser>) RedisUtils.getBeanValue("userData");
        if("1".equals(user.getIs_del())){
            userData.remove(user.getId());
        }else{
            userData.put(user.getId(), user);
        }
        RedisUtils.setBeanValue("userData", userData);
    }


}
