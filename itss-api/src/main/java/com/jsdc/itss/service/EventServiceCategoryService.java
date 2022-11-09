package com.jsdc.itss.service;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.core.base.Base;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.EventServiceCategoryDao;
import com.jsdc.itss.mapper.EventServiceCategoryMapper;
import com.jsdc.itss.mapper.EventServiceCategoryModularMapper;
import com.jsdc.itss.mapper.SysCompanyMapper;
import com.jsdc.itss.mapper.SysDepartmentMapper;
import com.jsdc.itss.model.*;
import com.jsdc.itss.model.vo.EventServiceManageVo;
import com.jsdc.itss.model.vo.TreeDataVo;
import com.jsdc.itss.utils.StringUtils;
import com.jsdc.itss.vo.EventServiceCategoryVo;
import com.jsdc.itss.vo.GetEventServiceCategoryVo;
import com.jsdc.itss.vo.ResultInfo;
import com.jsdc.itss.vo.SysDepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("ALL")
public class EventServiceCategoryService extends BaseService<EventServiceCategoryDao, EventServiceCategory> {

    @Autowired
    private EventServiceCategoryMapper eventServiceCategoryMapper;
    @Autowired
    private EventServiceCategoryModularMapper eventServiceCategoryModularMapper;

    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private SysCompanyService sysCompanyService;

    @Autowired
    private EventServiceManageService eventServiceManageService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

//    /**
//     * create by xuaolong
//     * description:自动服务类别管理查询
//     * @return ResultInfo
//     */
//    public ResultInfo getList(){
//        List<GetEventServiceCategoryVo> list =eventServiceCategoryMapper.selectList();
//        for (GetEventServiceCategoryVo temp : list){
//            int id = temp.getId();
//            QueryWrapper<EventServiceCategoryModular> queryWrapper =new QueryWrapper<>();
//            queryWrapper.eq("event_category_id",id);
//            List<EventServiceCategoryModular> modularList =eventServiceCategoryModularMapper.selectList(queryWrapper);
//            String modular_id ="";
//            for (int i=0 ;i<modularList.size();i++){
//                modular_id += modularList.get(i).getModular_id()+"";
//                if (i != modularList.size()-1){
//                    modular_id += ",";
//                }
//            }
//            temp.setModular_id(modular_id);
//        }
//        List<GetEventServiceCategoryVo> resultList = new ArrayList<>();
//        for (int i = 0 ; i< list.size();i++){
//            GetEventServiceCategoryVo getEventServiceCategoryVo =list.get(i);
//            if (getEventServiceCategoryVo.getParent_type_id()==-1){
//                resultList.add(getEventServiceCategoryVo);
//                setChildList(getEventServiceCategoryVo,list);
//            }
//        }
//
//
//        return ResultInfo.success(resultList);
//    }

    private void setChildList(GetEventServiceCategoryVo getEventServiceCategoryVo, List<GetEventServiceCategoryVo> list) {
        for (GetEventServiceCategoryVo temp : list) {
            if (temp.getParent_type_id() == getEventServiceCategoryVo.getId()) {
                getEventServiceCategoryVo.getList().add(temp);
                setChildList(temp, list);
            }
        }
    }

    /**
     * create by xuaolong
     * description:添加自动服务类别管理
     */
    public ResultInfo addServiceCategory(EventServiceCategoryVo eventServiceCategoryVo) {
        QueryWrapper<EventServiceCategory> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("is_del","0").eq("name",eventServiceCategoryVo.getName());
        Long count = eventServiceCategoryMapper.selectCount(queryWrapper1);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        EventServiceCategory eventServiceCategory = new EventServiceCategory();
        BeanUtils.copyProperties(eventServiceCategoryVo, eventServiceCategory);
        eventServiceCategory.setIs_del("0");
        if (null == eventServiceCategory.getParent_type_id()) {
            eventServiceCategory.setParent_type_id(0);
        }
        eventServiceCategoryMapper.insert(eventServiceCategory);
        String ids = eventServiceCategoryVo.getIds();
        if (StringUtils.isNotEmpty(ids)) {
            String[] idList = ids.split(",");
            for (String temp : idList) {
                int tempId = Integer.parseInt(temp);
                EventServiceCategoryModular eventServiceCategoryModular = new EventServiceCategoryModular();
                eventServiceCategoryModular.setEvent_category_id(eventServiceCategory.getId());
                eventServiceCategoryModular.setModular_id(tempId);
                eventServiceCategoryModularMapper.insert(eventServiceCategoryModular);
            }
        }
        return ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description:根据ID查询自动服务类别管理
     */
    public ResultInfo getOneServiceCategory(int id) {
        EventServiceCategory eventServiceCategory = eventServiceCategoryMapper.selectById(id);
        if (null != eventServiceCategory) {
            EventCategory eventCategory = eventCategoryService.selectById(eventServiceCategory.getWorker_order_id());
            if (null != eventCategory) {
                eventServiceCategory.setBill_type_name(eventCategory.getName());
            }
            //5	自动分派到工程师ID
            if (Base.notEmpty(eventServiceCategory.getEngineer_id())) {
                SysUser user = sysUserService.selectById(eventServiceCategory.getEngineer_id());
                if (Base.notEmpty(user)) {
                    eventServiceCategory.setEngineer_name(user.getName());
                }
            }
        }
        EventServiceCategory eventServiceCategory2 = eventServiceCategoryMapper.selectById(eventServiceCategory.getParent_type_id());
        if (null != eventServiceCategory2) {
            eventServiceCategory.setParent_type_name(eventServiceCategory2.getName());
        }

        EventServiceCategoryVo eventServiceCategoryVo = new EventServiceCategoryVo();
        BeanUtils.copyProperties(eventServiceCategory, eventServiceCategoryVo);
        QueryWrapper<EventServiceCategoryModular> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_category_id", id);
        List<EventServiceCategoryModular> list = eventServiceCategoryModularMapper.selectList(queryWrapper);
        String ids = "";
        for (int i = 0; i < list.size(); i++) {
            int modular_id = list.get(i).getModular_id();
            ids += modular_id;
            if (i != list.size() - 1) {
                ids += ",";
            }
        }
        eventServiceCategoryVo.setIds(ids);
        return ResultInfo.success(eventServiceCategoryVo);
    }

    /**
     * create by xuaolong
     * description:修改自动服务类别管理
     */
    public ResultInfo updateServiceCategory(EventServiceCategoryVo eventServiceCategoryVo) {
        QueryWrapper<EventServiceCategory> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("is_del","0").eq("name",eventServiceCategoryVo.getName()).ne("id",eventServiceCategoryVo.getId());
        Long count = eventServiceCategoryMapper.selectCount(queryWrapper1);
        if (count > 0){
            return ResultInfo.error("名称已存在");
        }
        EventServiceCategory eventServiceCategory = new EventServiceCategory();
        BeanUtils.copyProperties(eventServiceCategoryVo, eventServiceCategory);
        eventServiceCategoryMapper.updateById(eventServiceCategory);
        String ids = eventServiceCategoryVo.getIds();
        if (StringUtils.isNotEmpty(ids)) {
            String[] idList = ids.split(",");
            QueryWrapper<EventServiceCategoryModular> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("event_category_id", eventServiceCategoryVo.getId());
            eventServiceCategoryModularMapper.delete(queryWrapper);
            for (int i = 0; i < idList.length; i++) {
                int modular_id = Integer.parseInt(idList[i]);
                EventServiceCategoryModular eventServiceCategoryModular = new EventServiceCategoryModular();
                eventServiceCategoryModular.setEvent_category_id(eventServiceCategoryVo.getId());
                eventServiceCategoryModular.setModular_id(modular_id);
                eventServiceCategoryModularMapper.insert(eventServiceCategoryModular);
            }
        }
        return ResultInfo.success();
    }


    //存储要删除的ID集合
    private List<Integer> ids = new ArrayList<>();

    /**
     * create by xuaolong
     * description:递归删除自动服务类别管理
     */
    public ResultInfo deleteServiceCategory(int id) {
//        ids.add(id);
//        method1(id);
//        for (Integer temp : ids){
//            eventServiceCategoryMapper.deleteById(temp);
//            QueryWrapper<EventServiceCategoryModular> queryWrapper =new QueryWrapper<>();
//            queryWrapper.eq("event_category_id",temp);
//            eventServiceCategoryModularMapper.delete(queryWrapper);
//        }
//        ids.clear();
        EventServiceCategory eventServiceCategory = new EventServiceCategory();
        eventServiceCategory.setId(id);
        eventServiceCategory.setIs_del("1");
        eventServiceCategoryMapper.updateById(eventServiceCategory);
        return ResultInfo.success();
    }

    /**
     * create by xuaolong
     * description: 私有方法 服务递归删除 把自动服务类别管理的Id存储到ids集合
     *
     * @param id
     */
    private void method1(Integer id) {
        QueryWrapper<EventServiceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<EventServiceCategory> list = eventServiceCategoryMapper.selectList(queryWrapper);
        if (!list.isEmpty()) {
            for (EventServiceCategory temp : list) {
                ids.add(temp.getId());
                method1(temp.getId());
            }
        }
    }

    /**
     * description:自动服务类别管理导出excel
     */
    public ResultInfo exporEventServiceCategoryExcel(HttpServletResponse response) {

        QueryWrapper<EventServiceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        List<EventServiceCategory> list = eventServiceCategoryMapper.selectList(queryWrapper);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("name", "名称");
        writer.addHeaderAlias("is_use", "状态");
        writer.addHeaderAlias("company_id", "公司");
        writer.addHeaderAlias("group_id", "自动分派到服务群组");
        writer.addHeaderAlias("engineer_id", "自动分派到工程师");
        writer.addHeaderAlias("description", "描述");
        writer.addHeaderAlias("id", "ID");
        writer.setOnlyAlias(true);
        writer.write(list, true);
        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("123.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            writer.flush(outputStream, true);
            outputStream.flush();
            outputStream.close();
            return ResultInfo.success(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(null);
    }

    /**
     * 自助服务类别列表递归接口
     * Author wzn
     * Date 2022/3/23 16:18
     */
    public List<EventServiceCategory> tree(Integer id) {
        QueryWrapper<EventServiceCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", "0");
        if (Base.notEmpty(id)) {
            wrapper.notIn("id", id);
            wrapper.notIn("parent_type_id", id);
        }
        List<EventServiceCategory> fileCatalogs = eventServiceCategoryMapper.selectList(wrapper);
        List<EventServiceCategory> lists = new ArrayList<>();
        for (EventServiceCategory fileCatalog : fileCatalogs) {
            EventServiceCategory vo = new EventServiceCategory();
            BeanUtils.copyProperties(fileCatalog, vo);
            if (Base.notEmpty(vo.getCompany_id())) {
                SysCompany sysCompany = sysCompanyService.selectById(vo.getCompany_id());
                //公司名称
                vo.setCompany_name(sysCompany.getName());
            }
            if (Base.notEmpty(vo.getGroup_id())) {
                EventServiceManageVo eventServiceManage = eventServiceManageService.selectEventDetailById(vo.getGroup_id());
                //自动分派到服务群组
                vo.setGroup_name(eventServiceManage.getEventServiceManage().getName());
            }
            if (Base.notEmpty(vo.getEngineer_id())) {
                SysUser sysUser = sysUserService.selectById(vo.getEngineer_id());
                //自动分派到工程师
                vo.setEngineer_name(sysUser.getName());
            }
            lists.add(vo);
        }
        List<EventServiceCategory> tree = createTree(lists, 0);
        return tree;
    }

    /**
     * 递归建立树形结构 （算法）
     */
    private List<EventServiceCategory> createTree(List<EventServiceCategory> lists, int pid) {
        List<EventServiceCategory> tree = new ArrayList<>();
        for (EventServiceCategory catelog : lists) {
            if (catelog.getParent_type_id() == pid) {
                catelog.setChildren(createTree(lists, catelog.getId()));
                catelog.setChildMenus(createTree(lists, catelog.getId()));
//                if (Base.notEmpty(catelog.getCompany_id())) {
//                    SysCompany sysCompany = sysCompanyService.selectById(catelog.getCompany_id());
//                    //公司名称
//                    catelog.setCompany_name(sysCompany.getName());
//                }
//                if (Base.notEmpty(catelog.getGroup_id())) {
//                    EventServiceManageVo eventServiceManage = eventServiceManageService.selectEventDetailById(catelog.getGroup_id());
//                    //自动分派到服务群组
//                    catelog.setGroup_name(eventServiceManage.getEventServiceManage().getName());
//                }
                tree.add(catelog);
            }
        }
        return tree;
    }

    /**
     * 微信小程序
     * 自助服务类别列表递归接口
     * Author thr
     * Date 2022/8/18 16:18
     */
    public List<TreeDataVo> tree2() {
        QueryWrapper<EventServiceCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", "0");
        List<EventServiceCategory> eventServiceCategoryList = eventServiceCategoryMapper.selectList(wrapper);

        List<TreeDataVo> lists = new ArrayList<>();
        for (EventServiceCategory eventServiceCategory : eventServiceCategoryList) {
            TreeDataVo vo = new TreeDataVo();
            BeanUtils.copyProperties(eventServiceCategory, vo);
            lists.add(vo);
        }
        List<TreeDataVo> tree = createTree2(lists, 0);
        return tree;
    }

    /**
     * 微信小程序
     * 递归建立树形结构 （算法）
     */
    private List<TreeDataVo> createTree2(List<TreeDataVo> lists, int pid) {
        List<TreeDataVo> tree = new ArrayList<>();
        for (TreeDataVo vo : lists) {
            if (vo.getParent_type_id() == pid) {
                vo.setChildren(createTree2(lists, vo.getId()));
                tree.add(vo);
            }
        }
        return tree;
    }

    /**
     * 自助服务类别列表递归接口
     * @param id
     * @return
     */
    public List<EventServiceCategory> treeByUser(EventServiceCategory category, Integer userId) {
        Integer id = category.getId();
        QueryWrapper<EventServiceCategory> wrapper = new QueryWrapper<>();
        if (category.getUseUser() == null){
            // 当前的user
            int currentUserId = null == sysUserService.getUser()?userId:sysUserService.getUser().getId();
            SysUser user = sysUserService.selectById(currentUserId);
            // 得到部门
            SysDepartment dept = sysDepartmentMapper.selectById(user.getDepartment_id());
            if(dept == null || null == dept.getCompany_id()){
                return Collections.emptyList();
            }
            wrapper.eq("company_id", dept.getCompany_id());
        }



        wrapper.eq("is_del", "0");
        if (Base.notEmpty(id)) {
            wrapper.notIn("id", id);
            wrapper.notIn("parent_type_id", id);
        }
        List<EventServiceCategory> fileCatalogs = eventServiceCategoryMapper.selectList(wrapper);
        List<EventServiceCategory> lists = new ArrayList<>();
        for (EventServiceCategory fileCatalog : fileCatalogs) {
            EventServiceCategory vo = new EventServiceCategory();
            BeanUtils.copyProperties(fileCatalog, vo);
            if (Base.notEmpty(vo.getCompany_id())) {
                SysCompany sysCompany = sysCompanyService.selectById(vo.getCompany_id());
                //公司名称
                vo.setCompany_name(sysCompany.getName());
            }
            if (Base.notEmpty(vo.getGroup_id())) {
                EventServiceManageVo eventServiceManage = eventServiceManageService.selectEventDetailById(vo.getGroup_id());
                //自动分派到服务群组
                vo.setGroup_name(eventServiceManage.getEventServiceManage().getName());
            }
            if (Base.notEmpty(vo.getEngineer_id())) {
                SysUser sysUser = sysUserService.selectById(vo.getEngineer_id());
                //自动分派到工程师
                vo.setEngineer_name(sysUser.getName());
            }
            lists.add(vo);
        }
        List<EventServiceCategory> tree = createTree(lists, 0);
        return tree;
    }
}
