import request from '@/utils/request'

export function getList(data) {
  if (data == undefined) {
    return request({
      url: '/eventservicecategory/tree',
      method: 'post'
    })
  } else {
    return request({
      url: '/eventservicecategory/tree?id=' + data,
      method: 'post'
    })
  }
}

export function treeByUser(data) {
  return request({
    url: '/eventservicecategory/treeByUser',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
  })
}

export function add(data) {
  return request({
    url: '/eventservicecategory/addServiceCategory.do',
    method: 'post',
    data,
  })
}

export function upList() {
  return request({
    url: '/eventServiceManage/upList',
    method: 'post'
  })
}

//工单类别 删除
export function doDelete(data) {
  return request({
    url: '/eventCategory/del.do?id=' + data,
    method: 'post'
  })
}

//工单类别 树状图
export function tree(data) {
  return request({
    url: '/eventCategory/tree',
    method: 'post',
    data
  })
}

export function upCompany() {
  return request({
    url: '/syscompany/upCompany',
    method: 'post'
  })
}

//详情 自动服务类别管理表
export function info(data) {
  return request({
    url: '/eventservicecategory/selectServiceCategory.do?id=' + data,
    method: 'post'
  })
}

//修改 自动服务类别管理表
export function edit(data) {
  return request({
    url: '/eventservicecategory/updateServiceCategory.do',
    method: 'post',
    data

  })
}

//删除 自动服务类别管理表
export function del(data) {
  return request({
    url: '/eventservicecategory/deleteServiceCategory.do?id=' + data,
    method: 'post'
  })
}


















