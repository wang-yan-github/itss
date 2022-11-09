import request from '@/utils/request'

//获取知识配置详情
export function getConfigDetail(data) {
  return request({
    url: '/knowledgeConfig/knowledgeConfig/getDetail',
    method: 'post',
    data,
  })
}

//修改知识配置详情
export function addOrUpdateDetail(data) {
  return request({
    url: '/knowledgeConfig/knowledgeConfig/addOrUpdate',
    method: 'post',
    data,
  })
}

//知识类别详情
export function getCategoryDetail(data) {
  return request({
    url: '/knowledgeConfig/category/getDetail?id=' + data,
    method: 'post',
  })
}

//获取知识类别列表
export function categoryGetList(data) {
  return request({
    url: '/knowledgeConfig/category/getList',
    method: 'post',
    data,
  })
}

//新增知识类别
export function categoryAdd(data) {
  return request({
    url: '/knowledgeConfig/category/add',
    method: 'post',
    data,
  })
}

//删除知识类别
export function categoryDelete(data) {
  return request({
    url: '/knowledgeConfig/category/del',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}


//获取查询范围
export function getKnowledgeRangeList(data) {
  return request({
    url: '/knowledgeConfig/knowledgeRange/getList',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//删除知识查询范围
export function delKnowledgeRange(data) {
  return request({
    url: '/knowledgeConfig/knowledgeRange/del',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//查询服务群组主
export function getServiceGroupList(data) {
  return request({
    url: '/eventServiceManage/toList.do',
    method: 'post',
    data,
  })
}


//新增查询范围
export function rangeAdd(data) {
  return request({
    url: '/knowledgeConfig/knowledgeRange/add',
    method: 'post',
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsBackup/toSave.do',
    method: 'post',
    data,
  })
}

