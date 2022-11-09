package com.jsdc.itss.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.SysSqlDao;
import com.jsdc.itss.mapper.SysSqlMapper;
import com.jsdc.itss.mapper.SysSqlSeeMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.utils.DcCacheDataUtil;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysSqlVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author zhangdequan
 * @create 2022-03-11 10:05:27
 */
@Service
@Transactional
public class SysSqlService extends BaseService<SysSqlDao, SysSql> {

    @Autowired
    private SysSqlMapper sysSqlMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSqlSeeMapper sysSqlSeeMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //分页功能
    public SysSqlVo toList(Integer pageIndex, Integer pageSize, SysSql beanParam) {
        PageHelper.startPage(pageIndex, pageSize);
        return dictShowData(sysSqlMapper.toList(beanParam));
    }

    //列表字典取值
    public SysSqlVo dictShowData(List<SysSql> list) {
        //SQL报表显示方式
        HashMap<String, SysDict> sqlDisplayMode = DcCacheDataUtil.getMapDicts("sql_display_mode");
        //SQL报表所属模块
        HashMap<String, SysDict> sqlModule = DcCacheDataUtil.getMapDicts("sql_module");
        //SQL报表时间类型
        HashMap<String, SysDict> sqlTimechoose = DcCacheDataUtil.getMapDicts("sql_time_choose");
        //SQL报表可见范围
        HashMap<String, SysDict> sqlScope = DcCacheDataUtil.getMapDicts("sql_scope");
        //SQL报表状态
        HashMap<String, SysDict> sqlIsUse = DcCacheDataUtil.getMapDicts("sql_is_use");
        list.forEach(x -> {
            if (null != sqlDisplayMode && null != x.getDisplay_mode()) {
                x.setDisplay_mode(sqlDisplayMode.get(x.getDisplay_mode()).getLabel());
            }
            if (null != sqlModule && null != x.getModule()) {
                x.setModule(sqlModule.get(x.getModule()).getLabel());
            }
            if (null != sqlTimechoose && null != x.getTime_choose()) {
                x.setTime_choose(sqlTimechoose.get(x.getTime_choose()).getLabel());
            }
            if (null != sqlScope && null != x.getScope()) {
                x.setScope(sqlScope.get(x.getScope()).getLabel());
            }
            if (null != sqlIsUse && null != x.getIs_use()) {
                x.setIs_use_name(sqlIsUse.get(x.getIs_use()).getLabel());
            }
        });
        SysSqlVo vo = new SysSqlVo();
        vo.setPage(new PageInfo<>(list));
        vo.setSqlDisplayMode(sqlDisplayMode);
        vo.setSqlIsUse(sqlIsUse);
        vo.setSqlTimechoose(sqlTimechoose);
        vo.setSqlScope(sqlScope);
        vo.setSqlModule(sqlModule);
        return vo;
    }

    //根据id查询数据
    public JSONObject getById(String id) {
        JSONObject object = new JSONObject();
        //SQL报表显示方式
        HashMap<String, SysDict> sqlDisplayMode = DcCacheDataUtil.getMapDicts("sql_display_mode");
        //SQL报表所属模块
        HashMap<String, SysDict> sqlModule = DcCacheDataUtil.getMapDicts("sql_module");
        //SQL报表时间类型
        HashMap<String, SysDict> sqlTimechoose = DcCacheDataUtil.getMapDicts("sql_time_choose");
        //SQL报表可见范围
        HashMap<String, SysDict> sqlScope = DcCacheDataUtil.getMapDicts("sql_scope");
        //SQL报表状态
        HashMap<String, SysDict> sqlIsUse = DcCacheDataUtil.getMapDicts("sql_is_use");
        //修改
        if (Base.notEmpty(id) && !"0".equals(id)) {
            QueryWrapper<SysSql> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_del", 0);
            queryWrapper.eq("id", id);
            SysSql sysSql = selectOne(queryWrapper);
            //获取人员数据
            List<SysSqlSee> userSqlSees = sysSqlSeeMapper.selectList(Wrappers.<SysSqlSee>lambdaQuery().eq(SysSqlSee::getSql_id, id)
                    .eq(SysSqlSee::getVisual_type, "0").eq(SysSqlSee::getIs_del, "0"));
            if (!CollectionUtils.isEmpty(userSqlSees)) {
                List<SysUser> list = new ArrayList<>();
                userSqlSees.forEach(a -> {
                    SysUser sysUser = sysUserService.selectById(a.getVisual_id());
                    if (Base.notEmpty(sysUser)) {
                        list.add(sysUser);
                    }
                });
                sysSql.setSysUserList(list);
            }
            //获取角色数据
            List<SysSqlSee> roleSqlSees = sysSqlSeeMapper.selectList(Wrappers.<SysSqlSee>lambdaQuery().eq(SysSqlSee::getSql_id, id)
                    .eq(SysSqlSee::getVisual_type, "1").eq(SysSqlSee::getIs_del, "0"));
            if (!CollectionUtils.isEmpty(roleSqlSees)) {
                List<SysRole> roleList = new ArrayList<>();
                roleSqlSees.forEach(a -> {
                    SysRole sysRole = sysRoleService.selectById(a.getVisual_id());
                    System.out.println(sysRole);
                    if (Base.notEmpty(sysRole)) {
                        roleList.add(sysRole);
                    }
                });
                sysSql.setSysRoleList(roleList);
            }
            object.put("sysSql", sysSql);
        }
        object.put("sqlDisplayMode", sqlDisplayMode);
        object.put("sqlModule", sqlModule);
        object.put("sqlTimechoose", sqlTimechoose);
        object.put("sqlIsUse", sqlIsUse);
        object.put("sqlScope", sqlScope);
        return object;
    }

    /**
     * 添加
     */
    public ResultInfo addSysSql(SysSql bean) {
        String sqlbean = verification(bean.getSql_content());
        if ("-1".equals(sqlbean)) {
            return ResultInfo.error("编写SQL不支持新增、修改、增加语句");
        } else if ("1".equals(sqlbean)) {
            return ResultInfo.error("编写SQL语句语法不正确");
        } else {
            // 删除状态
            bean.setIs_del(String.valueOf(0));
            // 创建时间
            bean.setCreate_time(new Date());
            // 创建者
            bean.setCreate_user(sysUserService.getUser().getId());
            insert(bean);
            List<Integer> users = bean.getUsers();
            if (!CollectionUtils.isEmpty(users)) {
                for (Integer temp : users) {
                    SysSqlSee sysSqlSee = new SysSqlSee();
                    sysSqlSee.setCreate_time(new Date());
                    sysSqlSee.setIs_del("0");
                    sysSqlSee.setCreate_user(sysUserService.getUser().getId());
                    sysSqlSee.setSql_id(bean.getId());
                    sysSqlSee.setVisual_id(temp);
                    sysSqlSee.setVisual_type(0);
                    sysSqlSeeMapper.insert(sysSqlSee);
                }
            }
            if (!CollectionUtils.isEmpty(bean.getRoles())) {
                for (Integer temp : bean.getRoles()) {
                    SysSqlSee sysSqlSee = new SysSqlSee();
                    sysSqlSee.setCreate_time(new Date());
                    sysSqlSee.setIs_del("0");
                    sysSqlSee.setCreate_user(sysUserService.getUser().getId());
                    sysSqlSee.setSql_id(bean.getId());
                    sysSqlSee.setVisual_id(temp);
                    sysSqlSee.setVisual_type(1);
                    sysSqlSeeMapper.insert(sysSqlSee);
                }
            }
            return ResultInfo.success();
        }
    }

    /**
     * 编辑
     */
    public ResultInfo editSysSql(SysSql bean) {
        String sqlbean = verification(bean.getSql_content());
        if ("-1".equals(sqlbean)) {
            return ResultInfo.error("编写SQL不支持新增、修改、增加语句");
        } else if ("1".equals(sqlbean)) {
            return ResultInfo.error("编写SQL语句语法不正确");
        } else {
            // 修改者
            bean.setUpdate_user(sysUserService.getUser().getId());
            // 修改时间
            bean.setUpdate_time(new Date());
            updateById(bean);
            if (!CollectionUtils.isEmpty(bean.getUsers())) {
                UpdateWrapper<SysSqlSee> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("sql_id", bean.getId());
                updateWrapper.eq("visual_type", 0);
                updateWrapper.set("is_del", "1");
                sysSqlSeeMapper.update(null, updateWrapper);
                for (Integer temp : bean.getUsers()) {
                    SysSqlSee sysSqlSee = new SysSqlSee();
                    sysSqlSee.setUpdate_time(new Date());
                    sysSqlSee.setIs_del("0");
                    sysSqlSee.setUpdate_user(sysUserService.getUser().getId());
                    sysSqlSee.setSql_id(bean.getId());
                    sysSqlSee.setVisual_id(temp);
                    sysSqlSee.setVisual_type(0);
                    sysSqlSeeMapper.insert(sysSqlSee);
                }
            }
            if (!CollectionUtils.isEmpty(bean.getRoles())) {
                UpdateWrapper<SysSqlSee> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("sql_id", bean.getId());
                updateWrapper.eq("visual_type", 1);
                updateWrapper.set("is_del", "1");
                sysSqlSeeMapper.update(null, updateWrapper);
                for (Integer temp : bean.getRoles()) {
                    SysSqlSee sysSqlSee = new SysSqlSee();
                    sysSqlSee.setUpdate_time(new Date());
                    sysSqlSee.setIs_del("0");
                    sysSqlSee.setUpdate_user(sysUserService.getUser().getId());
                    sysSqlSee.setSql_id(bean.getId());
                    sysSqlSee.setVisual_id(temp);
                    sysSqlSee.setVisual_type(1);
                    sysSqlSeeMapper.insert(sysSqlSee);
                }
            }
            return ResultInfo.success();
        }
    }

    //删除功能
    public Integer deleteSysSql(Integer id) {
        UpdateWrapper<SysSql> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", "1");
        updateWrapper.eq("id", id);
        int count = sysSqlMapper.update(null, updateWrapper);
        if (count > 0) {
            UpdateWrapper<SysSqlSee> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.set("is_del", "1");
            updateWrapper1.eq("sql_id", id);
            sysSqlSeeMapper.update(null, updateWrapper1);
        }
        return count;
    }

    //禁用启用
    public Integer stateUpd(Integer id, String is_use) {
        UpdateWrapper<SysSql> updateWrapper = new UpdateWrapper<>();
        if ("0".equals(is_use)) {
            updateWrapper.set("is_use", 1);
        } else {
            updateWrapper.set("is_use", 0);
        }
        updateWrapper.eq("id", id);
        return sysSqlMapper.update(null, updateWrapper);
    }

    /**
     * 动态读取sql语句，进行查询数据
     *
     * @param bean
     * @return
     */
    public JSONObject details(SysSql bean, Integer pageNo, Integer pageSize) {
        JSONObject object = new JSONObject();
        SysSql sysSql = sysSqlMapper.selectById(bean.getId());
        //sql语句不为空
        if (Base.notEmpty(sysSql.getSql_content())) {
            PageHelper.startPage(pageNo, pageSize);
            bean.setSql_content(sysSql.getSql_content());
            List<LinkedHashMap<String, Object>> maps = sysSqlMapper.parametSql(bean);
            PageInfo<LinkedHashMap<String, Object>> page = new PageInfo<>(maps);
            if (page.getTotal() > 0) {
                object.put("ls", new ArrayList<>(maps.get(0).keySet()));
            } else {
                object.put("ls", new ArrayList<>());
            }
            object.put("page", page);

        } else {
            object.put("page", 0);
        }
        return object;
    }

    //sql语句验证
    public String verification(String sql) {
        //转小写
        String code = sql.toLowerCase();
        //判断是否包含修改、删除、新增关键字
        if (code.contains("update") || code.contains("delete") || code.contains("insert")) {
            return "-1";
        } else {
            try {
                List<LinkedHashMap<String, Object>> maps = sysSqlMapper.parametSql(new SysSql(sql));
            } catch (Exception ex) {
                return "1";
            }
        }
        return "0";
    }

    //导出
    public String sysSqlExport(HttpServletResponse response, Integer id) {
        //根据id查询sql语句
        SysSql sentence = sysSqlMapper.selectById(id);
        //查询sql数据信息
        List<LinkedHashMap<String, Object>> maps = sysSqlMapper.parametSql(sentence);
        ExcelWriter writer = ExcelUtil.getWriter();
        for (String name : maps.get(0).keySet()) {
            writer.addHeaderAlias(name, name);
        }
        writer.setOnlyAlias(true);
        writer.write(maps, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return "0";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }

}
