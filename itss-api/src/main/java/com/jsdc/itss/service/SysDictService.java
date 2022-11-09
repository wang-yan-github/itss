package com.jsdc.itss.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysDictDao;
import com.jsdc.itss.init.RedisDataInit;
import com.jsdc.itss.model.SysDict;
import com.jsdc.itss.model.SysUser;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysDictService extends BaseService<SysDictDao, SysDict> {

    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisDataInit redisDataInit;

    /**
     * @description 新增字典
     * @param sysDict
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/18
    */
    public ResultInfo add(SysDict sysDict){
        if(null == sysDict){
            return ResultInfo.error("字典不能为空!");
        }
        if(StringUtils.isEmpty(sysDict.getDict_type())
            || StringUtils.isEmpty(sysDict.getValue())
            || StringUtils.isEmpty(sysDict.getLabel())){
            return ResultInfo.error("必填字段不能为空!");
        }
        SysUser sysUser = userService.getUser();
        sysDict.setCreate_time(new Date());
        sysDict.setUpdate_time(new Date());
        sysDict.setCreate_user(sysUser.getId());
        sysDict.setUpdate_user(sysUser.getId());
        sysDict.setIs_del("0");
        if(insert(sysDict) > 0){
            redisDataInit.updateDictCache(sysDict);
            return ResultInfo.success();
        }else{
            return ResultInfo.error("系统错误!");
        }
    }

    /**
     * @description 更新字典
     * @param sysDict
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/18
    */
    public ResultInfo edit(SysDict sysDict){
        if(null == sysDict){
            return ResultInfo.error("字典不能为空!");
        }
        if(null == sysDict.getId()){
            return ResultInfo.error("字典id不能为空!");
        }
        if(StringUtils.isEmpty(sysDict.getDict_type())
                || StringUtils.isEmpty(sysDict.getValue())
                || StringUtils.isEmpty(sysDict.getLabel())){
            return ResultInfo.error("必填字段不能为空!");
        }
        SysDict original = selectById(sysDict.getId());
        BeanUtil.copyProperties(sysDict, original, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        SysUser sysUser = userService.getUser();
        original.setUpdate_time(new Date());
        original.setUpdate_user(sysUser.getId());
        if(updateById(original) > 0){
            redisDataInit.updateDictCache(original);
            return ResultInfo.success();
        }else{
            return ResultInfo.error("系统错误!");
        }
    }
    
    /**
     * @description 删除字典
     * @param dictId
     * @return com.jsdc.itss.vo.ResultInfo
     * @author wp
     * @date 2022/3/18
    */
    public ResultInfo del(Integer dictId){
        if(deleteById(dictId) > 0){
            return ResultInfo.success();
        }else{
            return ResultInfo.error("系统错误!");
        }
    }
    /**
     * @description 根据字典类型及键值查询label值
     * @param type 字典类型
     * @param value 字典key
     * @return com.jsdc.itss.model.SysDict
     * @author wp
     * @date 2022/3/18
    */
    public SysDict getDict(String type, String value){
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDict_type, type);
        wrapper.eq(SysDict::getValue, value);
        wrapper.eq(SysDict::getIs_del, "0");
        return selectOne(wrapper);
    }

    /**
     * @description 根据字典类型查询字典列表
     * @param type
     * @return java.util.List com.jsdc.itss.model.SysDict
     * @author wp
     * @date 2022/3/18
    */
    public List<SysDict> getDicts(String type){
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDict_type, type);
        wrapper.eq(SysDict::getIs_del, "0");
        return selectList(wrapper);
    }

    /**
     * @description 查询所有字典数据
     * @return java.util.List com.jsdc.itss.model.SysDict
     * @author wp
     * @date 2022/3/23
    */
    public List<SysDict> getAllDicts(){
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getIs_del, "0");
        return selectList(wrapper);
    }
}
