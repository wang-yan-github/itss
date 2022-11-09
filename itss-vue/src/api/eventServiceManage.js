import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eventServiceManage/toList.do',
    method: 'post',
    data,
  })
}

export function getAllList(data) {
  return request({
    url: '/eventServiceManage/getAllInfo',
    method: 'post',
    data,
  })
}

export function dropDownData() {
  return request({
    url: '/sysWorkingDays/dropDownData',
    method: 'post'
  })
}

export function upUserData() {
  return request({
    url: '/sysuser/upUserData',
    method: 'post'
  })
}

export function save(data) {

  return request({
    url: '/eventServiceManage/toAdd.do',
    method: 'post',
    data,
  })
}

export function edit(data) {

  return request({
    url: '/eventServiceManage/edit',
    method: 'post',
    data,
  })
}


export function userList(data) {

  return request({
    url: '/sysuser/getPage.do',
    method: 'post',
    data,
  })
}


export function getUserList(data) {
  return request({
    url: '/sysuser/getUserList?ids=' + data,
    method: 'post'
  })
}

export function info(data) {
  return request({
    url: '/eventServiceManage/info?id=' + data,
    method: 'post'
  })
}


export function del(data) {
  return request({
    url: '/eventServiceManage/del?id=' + data,
    method: 'post',
  })
}






