package com.jsdc.itss.service;

import com.jsdc.core.base.BaseService;
import com.jsdc.itss.dao.PublishNodesDao;
import com.jsdc.itss.mapper.PublishNodesMapper;
import com.jsdc.itss.model.PublishBasicInfo;
import com.jsdc.itss.model.PublishCategoryApprove;
import com.jsdc.itss.model.PublishNodes;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.jsdc.itss.vo.ResultInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Author wh
 * @create 2022-03-15 10:37:43
 */
@Service
@Transactional
public class PublishNodesService extends BaseService<PublishNodesDao, PublishNodes> {

    @Autowired
    private PublishNodesMapper publishNodesMapper;
    @Autowired
    private PublishNodesDao publishNodesDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PublishCategoryApproveService publishCategoryApproveService;

    public PageInfo<PublishNodes> toList(Integer pageIndex, Integer pageSize, PublishNodes beanParam) {
        PageHelper.startPage(pageIndex, pageSize);

        List<PublishNodes> publishNodesVos = publishNodesMapper.toList(beanParam);

        PageInfo<PublishNodes> page = new PageInfo<>(publishNodesVos);

        return page;
    }

    public List<PublishNodes> getByPublishId(Integer id) {
        QueryWrapper<PublishNodes> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0);
        queryWrapper.eq("publish_id",id);
        List<PublishNodes> list = selectList(queryWrapper);
        return list;
    }

    /**
     * 查询审批节点的当前时间信息
     */
    public PublishNodes getCurrentApproveNode(Integer id) {
        QueryWrapper<PublishNodes> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_del",0).eq("publish_id",id).eq("time_nodes",2).eq("time_status",1);
        List<PublishNodes> list = selectList(queryWrapper);
        if(list != null && list.size() > 0){
            if(list.size() > 1){
                Collections.sort(list, (o1, o2) -> {
                    if (o1.getSort() > o2.getSort()) {
                        return 1;
                    }
                    return -1;
                });
            }
            return list.get(0);
        }
        return null;
    }

    /**
     * 添加所有时间信息
     * @param bean
     * @return
     */
    public ResultInfo batchAddPublishNodes(PublishBasicInfo bean) {
        List<Integer> nlist = Arrays.asList(1);
        List<Integer> nodes = new ArrayList(nlist);
        //一般发布
        if("1".equals(bean.getType())){
            QueryWrapper<PublishCategoryApprove> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("publish_category_id",bean.getCategory()).eq("is_del",'0');
            List<PublishCategoryApprove> list = publishCategoryApproveService.selectList(queryWrapper);
            for (int i = 0; i < list.size(); i++) {
                nodes.add(2);
            }
            nodes.add(3);
            nodes.add(4);
        }
        //紧急发布
        else if("2".equals(bean.getType())){
            nodes.add(4);
        }
        int sort = 1;
        for(Integer node :nodes){
            PublishNodes publishNodes = new PublishNodes();
            if (null != bean.getUserId()){
                publishNodes.setUserId(bean.getUserId());
            }

            publishNodes.setPublish_id(bean.getId());
            publishNodes.setTime_nodes(node);
            if(node==1){
                publishNodes.setTime_status(0);
                publishNodes.setEnd_time(new Date());
            }else if(node==2){
                publishNodes.setTime_status(1);
                publishNodes.setSort(sort);
                sort++;
            }else {
                publishNodes.setTime_status(1);
            }
            addPublishNodes(publishNodes);

        }
        return ResultInfo.success();
    }

    /**
     *  添加
     */
    public void addPublishNodes(PublishNodes bean) {
        // 删除状态
        bean.setIs_del(String.valueOf(0));
        // 创建时间
        bean.setCreate_time(new Date());
        // 创建者
        bean.setCreate_user(null == bean.getUserId()? sysUserService.getUser().getId(): bean.getUserId());
        insert(bean);
    }

    /**
     *  编辑
     */
    public ResultInfo editPublishNodes(PublishNodes bean) {
        // 修改者
        bean.setUpdate_user(null == bean.getUserId() ? sysUserService.getUser().getId(): bean.getUserId());
        // 修改时间
        bean.setUpdate_time(new Date());
        updateById(bean);
        return ResultInfo.success();
    }
}
